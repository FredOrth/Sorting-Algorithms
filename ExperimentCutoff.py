from typing import List, Dict, Tuple
import numpy as np # type: ignore
from typing import List
import time
import csv
import string

import subprocess
# Timeout
TIMEOUT = 10000

def run_java(jar: str, arg: str, input: str)->str:
    args = arg.split()
    p = subprocess.Popen(['java','-Xmx8g', '-jar',jar] + args, 
        stdin=subprocess.PIPE, 
        stdout=subprocess.PIPE)
    (output,_) = p.communicate(input.encode('utf-8'), 
        timeout=TIMEOUT)
    return output.decode('utf-8') 

csv.field_size_limit(100000000)
INPUT_DATA_INTEGER: str = ""
INPUT_DATA_STRING: str = ""
INPUT_DATA_PREFIX: str = ""
INPUT_DATA_INTEGER_W_PRESORTED: str = ""

with open("RandomInputIntegers.csv", "r") as r:
    reader = csv.DictReader(r)
    
    for row in reader:
        n = str(row["n"])
        values = list(map(str, row["values"].split()))
        INPUT_DATA_INTEGER += f"{n}\n"
        INPUT_DATA_INTEGER += " ".join(values) + "\n"

with open("RandomInputString.csv", "r") as r:
    reader = csv.DictReader(r)
    
    for row in reader:
        n = str(row["n"])
        prefix = "algos"
        values = row["values"].split()
        INPUT_DATA_STRING += f"{n}\n"
        INPUT_DATA_STRING += " ".join(values) + "\n"
        
        prefixed_values = [prefix + str(value)[5:] for value in values]
        INPUT_DATA_PREFIX += f"{n}\n"
        INPUT_DATA_PREFIX += " ".join(prefixed_values) + "\n"
        # if INPUT_DATA_STRING.endswith("\n"):
        #     INPUT_DATA_STRING = INPUT_DATA_STRING.rstrip("\n")

        # if INPUT_DATA_PREFIX.endswith("\n"):
        #    INPUT_DATA_PREFIX = INPUT_DATA_PREFIX.rstrip("\n")
print("done")

with open("RandomInputIntegersWithPresortedness.csv", "r") as r:
    reader = csv.DictReader(r)

    for row in reader:
        n = str(row["n"]) 
        presortedness = str(row["presortedness"])  # The degree of presortedness 
        values = list(map(str, row["values"].split()))

        INPUT_DATA_INTEGER_W_PRESORTED += f"{n} {presortedness}\n"
        INPUT_DATA_INTEGER_W_PRESORTED += " ".join(values) + "\n"
print("Presorted done")

def measure(algorithm: str, jar: str, 
    input: List[int]):
    input_string: str = f'{len(input)}\n' + \
        ' '.join(map(str,input))
    start: float = time.time()
    result_string: str = run_java(jar, algorithm, 
        input_string)
    end: float = time.time()
    return end - start, result_string


# def benchmark(algorithm: str, jar: str)-> \
#     List[Tuple[int,float, int]]:
#     results: List[Tuple[int,float,int]] = list()
#     if(algorithm.split()[2] == "INTEGERS"):
#         data = INPUT_DATA_INTEGER
#     elif(algorithm.split()[2] == "STRINGS"):
#         data = INPUT_DATA_STRING
#     else:
#         data = INPUT_DATA_PREFIX

#     for key, valueList in data.items():
#         for value in valueList:
#             try:
#                 diff, comp = measure(algorithm,jar,
#                         value)
#                 results.append((int(key), float(diff), int(comp)))
#             except subprocess.TimeoutExpired:
#                 break
#     return results

def benchmark(algorithm: str, jar: str)-> \
    List[Tuple[int,float, int]]:
    results: List[Tuple[int,float,int]] = list()
    if(algorithm.split()[2] == "INTEGERS"):
        data = INPUT_DATA_INTEGER
    elif(algorithm.split()[2] == "STRINGS" or algorithm.split()[2] == "OBJECTS"):
        data = INPUT_DATA_STRING
    elif (algorithm.split()[2] == "PRESORTED"):
        data = INPUT_DATA_INTEGER_W_PRESORTED
    else:
        data = INPUT_DATA_PREFIX
    result_string = run_java(jar,algorithm,
            data)
    for line in result_string.split("\n"):
        split = line.strip().split()
        if len(split) == 3:
            results.append((int(split[0]), float(split[1]), int(split[2])))
    return results

# def build_java_project():
#     subprocess.run(['./gradlew', 'build'], check=True)


INSTANCES_C: List[Tuple[str, str]] = {
    # ("IterativeMergeSort Cutoff STRINGS", "SortingVariations/app/build/libs/app.jar"),
    # ("IterativeMergeSort Cutoff INTEGERS", "SortingVariations/app/build/libs/app.jar"),
    # ("insertionMergeSort Cutoff INTEGERS", "SortingVariations/app/build/libs/app.jar"),
    # ("insertionMergeSort Cutoff STRINGS", "SortingVariations/app/build/libs/app.jar")
    ("IterativeMergeSort Cutoff PRESORTED", "SortingVariations/app/build/libs/app.jar"),
    ("insertionMergeSort Cutoff PRESORTED", "SortingVariations/app/build/libs/app.jar"),
}

INSTANCES_MERGESORT_BASECASE: List[Tuple[str,str]]= {
    ("recursiveMergeSort BaseCase INTEGERS", "SortingVariations/app/build/libs/app.jar"),
    ("recursiveMergeSort BaseCase STRINGS", "SortingVariations/app/build/libs/app.jar"),
    ("recursiveMergeSort BaseCase PREFIX", "SortingVariations/app/build/libs/app.jar"),
    ("recursiveMergeSort BaseCase OBJECT", "SortingVariations/app/build/libs/app.jar"),
}

LIST_OF_CUTOFFVALUES: list[int] = {
    1,#Cutoff-value 1 is equal to the normal sorting algorithms. We should probably just refactor the other experiment to take this as well... oh well...
    2,
    4,
    8,
    16,
    20,
    32,
    64
}

if __name__ == '__main__':

    # with open('MergeSortBaseCase.csv','w') as f:
    #     writer = csv.DictWriter(f,
    #         fieldnames = ['algorithm','n','time', 'comparisons'])
    #     writer.writeheader()
    #     for algorithm, jar in INSTANCES_MERGESORT_BASECASE:
    #         results: List[Tuple[int,float]] = []
    #         for value in benchmark(f"{algorithm}",jar):
    #             writer.writerow({
    #                 'algorithm' : algorithm,
    #                 'n' : value[0],
    #                 'time' : value[1],
    #                 'comparisons' : value[2]
    #             })

    # with open("resultsCutoffValues.csv", "w") as f:  ##'resultsCutoffValues.csv'
    #     print("Done done")
    #     writer = csv.DictWriter(f, 
    #         fieldnames = ['algorithm','n','time', 'comparisons', 'cutoff'])
    #     writer.writeheader()
    #     for algorithm, jar in INSTANCES_C:
    #         results: List[Tuple[int,float]] = []
    #         for cutoff in LIST_OF_CUTOFFVALUES:
    #             for n,t,c in benchmark(f"{algorithm} {cutoff}",jar):
    #                 writer.writerow({ 
    #                     'algorithm' : algorithm,
    #                     'n' : n,
    #                     'time' : t,
    #                     'comparisons' : c,
    #                     'cutoff' : cutoff
    #                 })

    with open("IteraInsertPresortedResults.csv", "w") as f:
        print("Done done")
        writer = csv.DictWriter(
            f,
            fieldnames=[
                "algorithm",
                "n",
                "presortedDegree",
                "time",
                "comparisons",
                "cutoff",
            ],)
        writer.writeheader() 
        for algorithm, jar in INSTANCES_C:
            for cutoff in LIST_OF_CUTOFFVALUES:
                for n, t, c in benchmark(f"{algorithm} {presortedness}", jar):
                    writer.writerow(
                        {
                            "algorithm": algorithm,
                            "n": n,
                            "presortedDegree": presortedness,
                            "time": t,
                            "comparisons": c,
                            "cutoff": cutoff,
                        }
                    )
    # with open("resultsCutOffValuesString.csv", "w") as f:
    #     writer = csv.DictWriter(f,
    #         fieldnames = ['algorithm','n','time', 'comparisons', 'cutoff'])
    #     writer.writeheader()
    #     for algorithm, jar in INSTANCES_C:
    #         results: List[Tuple[int,float]] = []
    #         for cutoff in LIST_OF_CUTOFFVALUES:
    #             for n,t,c in benchmark(f"{algorithm} {cutoff}",jar, False):
    #                 writer.writerow({
    #                     'algorithm' : algorithm,
    #                     'n' : n,
    #                     'time' : t,
    #                     'comparisons' : c,
    #                     'cutoff' : cutoff
    #                 })
