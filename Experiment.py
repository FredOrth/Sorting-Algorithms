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

#Benchmark method
def benchmark(algorithm: str, jar: str, data: str)-> \
    List[List]: #List[Tuple[int,float, int]]
    results: List[List] = []
    
    for n in data.keys():
            for integers in data[n]:
                input_string = str(n) + " " + " ".join(map(str, integers)).strip()
                print(f"Running algorithm {algorithm}")
                results_string = run_java(jar, algorithm, input_string)
                print(results_string)
                split = results_string.strip().split()
                listOfElms = []
                for elm in split:
                    listOfElms.append(elm)
                results.append(listOfElms)
    return results   

def generatePlots(plotName: str) -> Dict[int, List[List]]:
    with open(plotName, "r") as r:
        reader = csv.DictReader(r)
        
        dictToReturn: Dict[int,List[List]] = {}
        
        for row in reader:
            n = row["n"]
            values = list(map(str, row["values"].split()))
            if n not in dictToReturn:
                dictToReturn[n] = []
            dictToReturn[n].append(values)
    return dictToReturn
        
def createPrefix(dataset):
    prefix = "algos"
    prefixDataSet = dataset
    for key, outerList in prefixDataSet.items():
        for innerList in outerList:
            for string in innerList:
                string = prefix + string[:-5]
    return prefixDataSet
    
    
#Test N*Log(N), DataTypes-test, c-value

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

#Instances:


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


    #Done Done Done Done Done Done Done Done Done Done Done Done Done Done Done Done Done Done Done Done Done
if __name__ == '__main__':
    INSTANCES_MERGESORT_BASECASE: List[Tuple[str,str]]= {
    ("recursiveMergeSort BaseCase INTEGERS", "SortingVariations/app/build/libs/app.jar"),
    ("recursiveMergeSort BaseCase STRINGS", "SortingVariations/app/build/libs/app.jar"),
    ("recursiveMergeSort BaseCase PREFIX", "SortingVariations/app/build/libs/app.jar"),
    ("recursiveMergeSort BaseCase OBJECT", "SortingVariations/app/build/libs/app.jar"),
}
    #Datasets for Basecase datasets
    listOfDatasets = [
    generatePlots("RandomInputString.csv"),\
    generatePlots("RandomInputIntegers.csv"), \
    createPrefix(generatePlots("RandomInputString.csv"))
    ]
    
    dictOfDataSets = {
        "INTEGERS" : listOfDatasets[1],
        "STRINGS" : listOfDatasets[0],
        "OBJECT" : listOfDatasets[0],
        "PREFIX" : listOfDatasets[2]
    }
    
    #Done Done Done Done Done Done Done Done Done Done Done Done Done Done Done Done Done Done Done Done Done
    #BaseCase for n*log(n) test of MergeSort
    with open('MergeSortBaseCase.csv','w') as f:
        writer = csv.DictWriter(f,
            fieldnames = ['algorithm','n','time', 'comparisons'])
        writer.writeheader()
        for algorithm, jar in INSTANCES_MERGESORT_BASECASE:
            results: List[Tuple[int,float]] = []
            for value in benchmark(f"{algorithm}",jar, dictOfDataSets[algorithm.split()[2]]):
                writer.writerow({
                    'algorithm' : algorithm,
                    'n' : value[0],
                    'time' : value[1],
                    'comparisons' : value[2]
                })

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
    # with open("resultsCutoffValues_LevelBSort2.csv", "w") as f:  ##'resultsCutoffValues.csv'
    #     print("Done done")
    #     writer = csv.DictWriter(f, 
    #         fieldnames = ['algorithm','n','time', 'comparisons', 'cutoff'])
    #     writer.writeheader()
    #     for algorithm, jar in INSTANCES_C_LevelSort_BSort:
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