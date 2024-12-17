from typing import List, Dict, Tuple
import numpy as np # type: ignore
from typing import List
import time
import csv
import string

import subprocess
#Timeout 
TIMEOUT = 60

def run_java(jar: str, arg: str, input: str)->str:
    args = arg.split()
    p = subprocess.Popen(['java','-Xmx8g', '-jar',jar] + args, 
        stdin=subprocess.PIPE, 
        stdout=subprocess.PIPE)
    (output,_) = p.communicate(input.encode('utf-8'), 
        timeout=TIMEOUT)
    return output.decode('utf-8') 

csv.field_size_limit(100000000)
INPUT_DATA_INTEGER: Dict[int, List[List[int]]] ={}
INPUT_DATA_STRING: Dict[int, List[List[str]]] ={}
INPUT_DATA_PREFIX: Dict[int, List[List[str]]] ={}

with open("RandomInputIntegers.csv", "r") as r:
    reader = csv.DictReader(r)
    
    for row in reader:
        n = int(row["n"])
        values = list(map(int, row["values"].split()))
        if n not in INPUT_DATA_INTEGER:
                INPUT_DATA_INTEGER[n] = []
        INPUT_DATA_INTEGER[n].append(values)
        
with open("RandomInputString.csv", "r") as r:
    reader = csv.DictReader(r)
    
    for row in reader:
        n = int(row["n"])
        prefix = "algos"
        values = row["values"].split()
        if n not in INPUT_DATA_STRING:
                INPUT_DATA_STRING[n] = []
        INPUT_DATA_STRING[n].append(values)
        if n not in INPUT_DATA_PREFIX:
            INPUT_DATA_PREFIX[n] = []
        prefixed_values = [prefix + str(value) for value in values]
        INPUT_DATA_PREFIX[n].append(prefixed_values)
print("done")

def measure(algorithm: str, jar: str, 
    input: List[int])->float:
    input_string: str = f'{len(input)}\n' + \
        ' '.join(map(str,input))
    start: float = time.time()
    result_string: str = run_java(jar, algorithm, 
        input_string)
    end: float = time.time()
    return end - start, result_string
    


def benchmark(algorithm: str, jar: str)-> \
    List[Tuple[int,float, int]]:
    results: List[Tuple[int,float,int]] = list()
    if(algorithm.split()[1] == "INTEGERS"):
        data = INPUT_DATA_INTEGER
    elif(algorithm.split()[1] == "STRINGS"):
        data = INPUT_DATA_STRING
    else:
        data = INPUT_DATA_PREFIX

    for key, valueList in data.items():
        for value in valueList:
            try: 
                diff, comp = measure(algorithm,jar,
                        value)
                results.append((int(key), float(diff), int(comp)))
            except subprocess.TimeoutExpired:
                break
    return results

# def build_java_project():
#     subprocess.run(['./gradlew', 'build'], check=True)
    


INSTANCES_C: List[Tuple[str,str]]= {
    ("IterativeMergeSort cutoff", "SortingVariations/app/build/libs/app.jar"),
    ("insertionMergeSort cutoff", "SortingVariations/app/build/libs/app.jar"),
    ("BinomialSortAdaptive cutoff", "SortingVariations/app/build/libs/app.jar"),
    ("BinomialSortNonAdaptive cutoff", "SortingVariations/app/build/libs/app.jar")
}

INSTANCES_MERGESORT_BASECASE: List[Tuple[str,str]]= {
    ("recursiveMergeSort INTEGERS", "SortingVariations/app/build/libs/app.jar"),
    ("recursiveMergeSort STRINGS", "SortingVariations/app/build/libs/app.jar"),
    ("recursiveMergeSort PREFIX", "SortingVariations/app/build/libs/app.jar"),
}

LIST_OF_CUTOFFVALUES: list[int] = {
    1,#Cutoff-value 1 is equal to the normal sorting algorithms. We should probably just refactor the other experiment to take this as well... oh well...
    2,
    4,
    8,
    16
}

if __name__ == '__main__':
    
    #build_java_project
    
    with open('MergeSortBaseCase.csv','w') as f:
        writer = csv.DictWriter(f, 
            fieldnames = ['algorithm','n','time', 'comparisons'])
        writer.writeheader()
        for algorithm, jar in INSTANCES_MERGESORT_BASECASE:
            results: List[Tuple[int,float]] = []
            print("done")
            for n,t,c in benchmark(f"{algorithm}",jar):
                writer.writerow({ 
                    'algorithm' : algorithm,
                    'n' : n,
                    'time' : t,
                    'comparisons' : c
                })
    
    # with open('resultsCutoffValues.csv','w') as f:
    #     writer = csv.DictWriter(f, 
    #         fieldnames = ['algorithm','n','time', 'comparisons', 'cutoff'])
    #     writer.writeheader()
    #     for algorithm, jar in INSTANCES_C:
    #         results: List[Tuple[int,float]] = []
    #         for cutoff in LIST_OF_CUTOFFVALUES:
    #             for n,t,c in benchmark(f"{algorithm} {cutoff}",jar, True):
    #                 writer.writerow({ 
    #                     'algorithm' : algorithm,
    #                     'n' : n,
    #                     'time' : t,
    #                     'comparisons' : c,
    #                     'cutoff' : cutoff
    #                 })
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