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

# Benchmark method
def benchmark(algorithm: str, jar: str, data)-> \
    List[List]: #List[Tuple[int,float, int]]
    results: List[List] = []
    
    for n in data.keys():
            for integers in data[n]:
                input_string = str(n) + "\n" + " ".join(map(str, integers)).strip()
                print(f"Running algorithm {algorithm}")
                results_string = run_java(jar, algorithm, input_string)
                print(results_string)
                split = results_string.strip().split()
                listOfElms = []
                for elm in split:
                    listOfElms.append(elm)
                results.append(listOfElms)
    return results


def generatePlots(plotName: str) -> Dict[int, List[int]]:
    with open(plotName, "r") as r:
        reader = csv.DictReader(r)
        
        dictToReturn: Dict[int,List[int]] = {}
        
        for row in reader:
            n = row["n"]
            values = list(map(str, row["values"].split()))
            if n not in dictToReturn:
                dictToReturn[n] = []
            dictToReturn[n].append(values)
    return dictToReturn

# Method for creating strings with a prefix of "algos"
def createPrefix(dataset):
    prefix = "algos"
    prefixDataSet = dataset
    for key, outerList in prefixDataSet.items():
        for innerList in outerList:
            for i in range(len(innerList)):
                innerList[i] = prefix + innerList[i][:-5]  # Modify the element in the list
    return prefixDataSet

def generatePresortedPlots() -> Dict[int, Dict[int, List[List[int]]]]:
    dictToReturn: Dict[int, Dict[int, List[List[int]]]] = {}

    with open("PresortedRandomInput.csv", "r") as r:
        reader = csv.DictReader(r)

        for row in reader:
            x = int(row["presortedness"])
            n = int(row["n"])
            values = list(map(int, row["values"].split()))

            if x not in dictToReturn:
                dictToReturn[x] = {}

            if n not in dictToReturn[x]:
                dictToReturn[x][n] = []

            dictToReturn[x][n].append(values)

    return dictToReturn


# Tests for mergesort base case with the types: Integers, Strings, Objects and prefix Strings
INSTANCES_MERGESORT_BASECASE: List[Tuple[str,str]]= {
# ("recursiveMergeSort BaseCase INTEGERS", "SortingVariations/app/build/libs/app.jar"),
("recursiveMergeSort BaseCase STRINGS", "SortingVariations/app/build/libs/app.jar"),
("recursiveMergeSort BaseCase PREFIX", "SortingVariations/app/build/libs/app.jar"),
("recursiveMergeSort BaseCase OBJECT", "SortingVariations/app/build/libs/app.jar"),
}

# Initial test for testing c-values for iterative and insertion mergesort
INSTANCES_C: List[Tuple[str, str]] = {
    ("iterativeMergeSort Cutoff INTEGERS", "SortingVariations/app/build/libs/app.jar"),
    ("insertionMergeSort Cutoff INTEGERS", "SortingVariations/app/build/libs/app.jar"),
}

# Tests for levelsort and binomial sort on diffrently sorted input for adaptive and non-adaptive algorithms
INSTANCES_preSorted_Adaptive: List[Tuple[str, str]] = {
    ("levelSort Presort INTEGERS Adaptive", "SortingVariations/app/build/libs/app.jar"),
    ("binomialSort Presort INTEGERS Adaptive", "SortingVariations/app/build/libs/app.jar"),
    ("levelSort Presort INTEGERS NonAdaptive", "SortingVariations/app/build/libs/app.jar"),
    ("binomialSort Presort INTEGERS NonAdaptive", "SortingVariations/app/build/libs/app.jar")
}

# #Horse race
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

# Cutoff values:
LIST_OF_CUTOFFVALUES: list[int] = {
    1,  # Cutoff-value 1 is equal to the normal sorting algorithms. We should probably just refactor the other experiment to take this as well... oh well...
    2,
    4,
    8,
    16,
    20,
    22,
    24,
    26,
    28,
    32,
    64,
}

if __name__ == '__main__':
    
    # # Datasets for Basecase datasets
    # listOfDatasets = [
    # generatePlots("RandomInputString.csv"),\
    # generatePlots("RandomInputIntegers.csv"), \
    # createPrefix(generatePlots("RandomInputString.csv"))
    # ]
    
    # dictOfDataSets = {
    #     "INTEGERS" : listOfDatasets[1],
    #     "STRINGS" : listOfDatasets[0],
    #     "OBJECT" : listOfDatasets[0],
    #     "PREFIX" : listOfDatasets[2]
    # }
    
    # # BaseCase for n*log(n) test of MergeSort
    # with open('MergeSortBaseCase.csv','w') as f:
    #     writer = csv.DictWriter(f,
    #         fieldnames = ['algorithm','n','time', 'comparisons'])
    #     writer.writeheader()
    #     for algorithm, jar in INSTANCES_MERGESORT_BASECASE:
    #         results: List[Tuple[int,float]] = []
    #         for value in benchmark(f"{algorithm}",jar, dictOfDataSets[algorithm.split()[2]]):
    #             writer.writerow({
    #                 'algorithm' : algorithm,
    #                 'n' : value[0],
    #                 'time' : value[1],
    #                 'comparisons' : value[2]
    #             })
       
    # listOfDatasets = [
    # generatePresortedPlots()
    # ]
    # print("done")         

    # # Experiment for testing different cutoff values for recursive mergesort
    # with open("cutoffValues.csv", "w") as f:
    #     writer = csv.DictWriter(f, 
    #         fieldnames = ['algorithm','n','time', 'comparisons', 'cutoff'])
    #     writer.writeheader()
    #     for algorithm, jar in INSTANCES_C:
    #         results: List[Tuple[int,float]] = []
    #         for cutoff in LIST_OF_CUTOFFVALUES:
    #             for presortedness, dict in listOfDatasets[0].items():
    #                 if presortedness == 0: #Here we will only use the not-sorted values
    #                     for value in benchmark(f"{algorithm} {cutoff}",jar, dict):
    #                         writer.writerow(
    #                         { 
    #                             'algorithm' : algorithm,
    #                             'n' : value[0],
    #                             'time' : value[1],
    #                             'comparisons' : value[2],
    #                             'cutoff' : cutoff,
    #                         }
    #                             )
    
    # # Experiment for testing binomial sort and level sort with different cutoff values
    # with open("LevelAndBioSort.csv", "w") as f:
    #     writer = csv.DictWriter(f, 
    #         fieldnames = ['algorithm','n','time', 'comparisons', 'cutoff', 'degree of presortedness'])
    #     writer.writeheader()
    #     for algorithm, jar in INSTANCES_preSorted_Adaptive:
    #         results: List[Tuple[int,float]] = []
    #         for cutoff in LIST_OF_CUTOFFVALUES:
    #             for presortedness, dict in listOfDatasets[0].items():
    #                 for value in benchmark(f"{algorithm} {cutoff}",jar, dict):
    #                     writer.writerow(
    #                     { 
    #                         'algorithm' : algorithm,
    #                         'n' : value[0],
    #                         'time' : value[1],
    #                         'comparisons' : value[2],
    #                         'cutoff' : cutoff,
    #                         'degree of presortedness' : presortedness
    #                     }
    #                         )
                
    #List of datasets used in horserace experiment
    listOfDatasets = [
    generatePlots("HorseRace.csv")
    ]
    #Experiment for horse race
    with open("HorseRaceResults.csv", "w") as f:
        writer = csv.DictWriter(f,
            fieldnames = ['algorithm','n','time'])
        writer.writeheader()
        for algorithm, jar in INSTANCES_HORSERACE:
                for value in benchmark(f"{algorithm}",jar, listOfDatasets[0]):
                    writer.writerow({
                        'algorithm' : algorithm,
                        'n' : value[0],
                        'time' : value[1],
                    })
