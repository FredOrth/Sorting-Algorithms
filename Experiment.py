from typing import List, Dict, Tuple
import numpy as np # type: ignore
from typing import List
import csv
import os
import subprocess

def run_java(jar: str, arg: str, input: str)->str:
    args = arg.split()
    p = subprocess.Popen(['java','-Xmx8g', '-jar',jar] + args, 
        stdin=subprocess.PIPE, 
        stdout=subprocess.PIPE)
    (output,_) = p.communicate(input.encode('utf-8'))
    return output.decode('utf-8') 

csv.field_size_limit(100000000)
# INPUT_DATA_INTEGER: str = ""
# INPUT_DATA_STRING: str = ""
# INPUT_DATA_PREFIX: str = ""
# INPUT_DATA_INTEGER_W_PRESORTED: str = ""

integer_data: Dict[int,List[List[int]]] = {}

with open("HorseRaceData.csv", "r") as r:
    reader = csv.DictReader(r)

    for row in reader:
        n = row["n"]  # Keep n as a string
        values = list(map(int, row["values"].split()))
        if n not in integer_data:
            integer_data[n] = []
        integer_data[n].append(values)
print("done")


# with open("RandomInputString.csv", "r") as r:
#     reader = csv.DictReader(r)

#     for row in reader:
#         n = str(row["n"])
#         prefix = "algos"
#         values = row["values"].split()
#         INPUT_DATA_STRING += f"{n}\n"
#         INPUT_DATA_STRING += " ".join(values) + "\n"

#         prefixed_values = [prefix + str(value)[5:] for value in values]
#         INPUT_DATA_PREFIX += f"{n}\n"
#         INPUT_DATA_PREFIX += " ".join(prefixed_values) + "\n"
# print("done")

# # n,presortedness,values
# with open("PresortedRandomInput.csv", "r") as r:
#     reader = csv.DictReader(r)

#     for row in reader:
#         n = str(row["n"]) 
#         presortedness = str(row["presortedness"])  # The degree of presortedness 
#         values = row["values"].split()
#         # INPUT_DATA_INTEGER_W_PRESORTED += f"{n}\n"
#         # INPUT_DATA_INTEGER_W_PRESORTED += f"{presortedness}" + "\n"
#         # INPUT_DATA_INTEGER_W_PRESORTED += " ".join(values) + "\n"
#         INPUT_DATA_INTEGER_W_PRESORTED += f"{n}\n{presortedness}\n{' '.join(values)}\n"
# print("Presorted done")

# def benchmark(algorithm: str, jar: str)-> \
#     List[Tuple[int,float, int]]: ## remove extra int if needed
#     results: List[Tuple[int,float,int, int]] = []

#     if "INTEGERS" in algorithm:
#         data = INPUT_DATA_INTEGER
#     elif "STRINGS" in algorithm or "OBJECTS" in algorithm:
#         data = INPUT_DATA_STRING
#     elif "PRESORTED" in algorithm:
#         data = INPUT_DATA_INTEGER_W_PRESORTED
#     else:
#         data = INPUT_DATA_PREFIX
#     results_strings = run_java(jar, algorithm, data)
#     print(results_strings)

#     ##output parsing
#     for line in results_strings.strip().split("\n"):
#         split = line.strip().split()
#         if len(split) == 2:  # n and time only
#             n, time = int(split[0]), float(split[1])
#             results.append((n, time, -1, -1))  # Default -1 for unused values
#         elif len(split) == 3:  # n, time, comparisons
#             n, time, comparisons = int(split[0]), float(split[1]), int(split[2])
#             results.append((n, time, comparisons, -1))  # Default -1 for presortedness
#         elif len(split) == 4:  # n, time, comparisons, presortedness, cutoff
#             n, time, comparisons, presortedness = (
#                 int(split[0]),
#                 float(split[1]),
#                 int(split[2]),
#                 int(split[3]),
#             )
#             results.append((n, time, comparisons, presortedness))
#         elif len(split) == 5:  # n, time, presortedness, comparisons, cutoff
#             n, time, presortedness, comparisons, cutoff = (
#                 int(split[0]),
#                 float(split[1]),
#                 int(split[2]),
#                 int(split[3]),
#                 int(split[4]),
#             )
#             results.append((n, presortedness, time, comparisons, cutoff))
#     return results


def benchmark(algorithm: str, jar: str)-> \
    List[Tuple[int,float, int]]: ## remove extra int if needed
    results: List[Tuple[int,float,int, int]] = []

    if "INTEGERS" in algorithm:
        data = integer_data
        print("Hello")
    elif "STRINGS" in algorithm or "OBJECTS" in algorithm:
        data = INPUT_DATA_STRING
    elif "PRESORTED" in algorithm:
        data = INPUT_DATA_INTEGER_W_PRESORTED
    else:
        data = INPUT_DATA_PREFIX
    for n in data.keys():
        print(n)
        for integers in data[n]:
            input_string = str(n) + " " + " ".join(map(str, integers)).strip()
            print(f"Running algorithm {algorithm}")
            results_string = run_java(jar, algorithm, input_string)
            split = results_string.strip().split()
            if len(split) == 2:  # n and time only
                n, time = int(split[0]), float(split[1])
                results.append((n, time, -1, -1))  # Default -1 for unused values
            elif len(split) == 3:  # n, time, comparisons
                n, time, comparisons = int(split[0]), float(split[1]), int(split[2])
                results.append((n, time, comparisons, -1))  # Default -1 for presortedness
            elif len(split) == 4:  # n, time, comparisons, presortedness, cutoff
                n, time, comparisons, presortedness = (
                    int(split[0]),
                    float(split[1]),
                    int(split[2]),
                    int(split[3]),
                )
                results.append((n, time, comparisons, presortedness))
            elif len(split) == 5:  # n, time, presortedness, comparisons, cutoff
                n, time, presortedness, comparisons, cutoff = (
                    int(split[0]),
                    float(split[1]),
                    int(split[2]),
                    int(split[3]),
                    int(split[4]),
                )
                results.append((n, presortedness, time, comparisons, cutoff))
    return results

    

INSTANCES_C: List[Tuple[str, str]] = {
    ("iterativeMergeSort Cutoff STRINGS", "SortingVariations/app/build/libs/app.jar"),
    # ("iterativeMergeSort Cutoff INTEGERS", "SortingVariations/app/build/libs/app.jar"),
    # ("insertionMergeSort Cutoff INTEGERS", "SortingVariations/app/build/libs/app.jar"),
    ("insertionMergeSort Cutoff STRINGS", "SortingVariations/app/build/libs/app.jar")
}

INSTANCES_C_LevelSort_BSort: List[Tuple[str, str]] = {
    ("levelSort Cutoff INTEGERS", "SortingVariations/app/build/libs/app.jar"),
    # ("binomialSort Cutoff INTEGERS", "SortingVariations/app/build/libs/app.jar")
}

INSTANCES_PRESORTED : List[Tuple[str, str]] = {
    ("binomialSort Cutoff PRESORTED Adaptive", "SortingVariations/app/build/libs/app.jar"),
    # ("levelSort Cutoff PRESORTED NonAdaptive", "SortingVariations/app/build/libs/app.jar"),
    # ("binomialSort Cutoff PRESORTED NonAdaptive", "SortingVariations/app/build/libs/app.jar"),
    # ("insertionMergeSort Cutoff PRESORTED NonAdaptive", "SortingVariations/app/build/libs/app.jar")
}

INSTANCES_MERGESORT_BASECASE: List[Tuple[str,str]]= {
    ("recursiveMergeSort BaseCase INTEGERS", "SortingVariations/app/build/libs/app.jar"),
    ("recursiveMergeSort BaseCase STRINGS", "SortingVariations/app/build/libs/app.jar"),
    ("recursiveMergeSort BaseCase PREFIX", "SortingVariations/app/build/libs/app.jar"),
    ("recursiveMergeSort BaseCase OBJECT", "SortingVariations/app/build/libs/app.jar"),
}

INSTANCES_HORSERACE: List[Tuple[str,str]]= {
    ("recursiveMergeSort HorseRace INTEGERS NonAdaptive", "SortingVariations/app/build/libs/app.jar"),
    #We unfortunately have to keep a placeholder to keep our architecture in main
    ("recursiveMergeSort HorseRace INTEGERS Arrays.sort", "SortingVariations/app/build/libs/app.jar"),
    ("levelSort HorseRace INTEGERS Adaptive", "SortingVariations/app/build/libs/app.jar"),
    ("binomialSort HorseRace INTEGERS Adaptive", "SortingVariations/app/build/libs/app.jar"),
    ("levelSort HorseRace INTEGERS NonAdaptive", "SortingVariations/app/build/libs/app.jar"),
    ("binomialSort HorseRace INTEGERS NonAdaptive", "SortingVariations/app/build/libs/app.jar"),
    ("insertionMergeSort HorseRace INTEGERS NonAdaptive", "SortingVariations/app/build/libs/app.jar"),
    ("parallelRecursiveMergeSort HorseRace INTEGERS NonAdaptive Parallel", "SortingVariations/app/build/libs/app.jar")
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

    # This is for running the BaseCase test for recursive mergesort
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

    # This is for testing cutoff values with strings
    # with open("resultsCutoffValues.csv", "w") as f:  ##'resultsCutoffValues.csv'
    #     print("Done done")
    #     writer = csv.DictWriter(f, 
    #         fieldnames = ['algorithm','n','time', 'comparisons', 'cutoff'])
    #     writer.writeheader()
    #     for algorithm, jar in INSTANCES_C:
    #         results: List[Tuple[int,float]] = []
    #         for cutoff in LIST_OF_CUTOFFVALUES:
    #             for value in benchmark(f"{algorithm} {cutoff}",jar):
    #                 writer.writerow({ 
    #                     'algorithm' : algorithm,
    #                     'n' : value[0],
    #                     'time' : value[1],
    #                     'comparisons' : value[2],
    #                     'cutoff' : cutoff
    #                 })
    with open("resultsCutoffValues_LevelBSort2.csv", "w") as f:  ##'resultsCutoffValues.csv'
        print("Done done")
        writer = csv.DictWriter(f, 
            fieldnames = ['algorithm','n','time', 'comparisons', 'cutoff'])
        writer.writeheader()
        for algorithm, jar in INSTANCES_C_LevelSort_BSort:
            results: List[Tuple[int,float]] = []
            for cutoff in LIST_OF_CUTOFFVALUES:
                for value in benchmark(f"{algorithm} {cutoff}",jar):
                    writer.writerow({ 
                        'algorithm' : algorithm,
                        'n' : value[0],
                        'time' : value[1],
                        'comparisons' : value[2],
                        'cutoff' : cutoff
                    })

    # with open("BiolvlPresortedResults.csv", "w", newline='') as f:
    #     print("Starting CSV write...")
    #     writer = csv.DictWriter(
    #         f,
    #         fieldnames=[
    #             "algorithm",
    #             "n",
    #             "time",
    #             "presortedDegree",
    #             "comparisons",
    #             "cutoff",
    #         ],)
    #     writer.writeheader() 

    #     for algorithm, jar in INSTANCES_PRESORTED:
    #         for cutoff in LIST_OF_CUTOFFVALUES:
    #             # Build the full command to run the Java program
    #             command = f"java -jar {jar} {algorithm} {cutoff}"

    #             # result = subprocess.run(command, shell=True, capture_output=True, text=True)
    #             # print(f"Java command output: {result.stdout}")

    #             # Execute the Java command using os.system (this runs the Java program)
    #             print(f"Running command: {command}")
    #             result_string = subprocess.run(command)  # Captures the output of the command
    #             print(f"Result from command:\n{result_string}")

    #             for line in result_string.strip().split("\n"):
    #                 values = (line.split(''))
    #                 n = int(values[0])  # 'n' value
    #                 time = float(values[1])  # Time value
    #                 presortedDegree = int(values[2]) # Presorted degree
    #                 comparisons = int(values[3])  # Comparisons value
    #                 cutoff = int(values[4]) # cutoff

    #                 # Write the result row into the CSV file
    #                 writer.writerow(
    #                     {
    #                         "algorithm": algorithm,
    #                         "n": n,
    #                         "time": time,
    #                         "presortedDegree": presortedDegree,
    #                         "comparisons": comparisons,
    #                         "cutoff": cutoff,
    #                     }
    #                 )

    # with open("HorseRace.csv", "w") as f:  ##'resultsCutoffValues.csv'
    #     print("Done done")
    #     writer = csv.DictWriter(f,
    #         fieldnames = ['algorithm','n','time'])
    #     writer.writeheader()
    #     for algorithm, jar in INSTANCES_HORSERACE:
    #             for value in benchmark(f"{algorithm}",jar):
    #                 writer.writerow({
    #                     'algorithm' : algorithm,
    #                     'n' : value[0],
    #                     'time' : value[1],
    #                 })