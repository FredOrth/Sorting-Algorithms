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
INPUT_DATA_INTEGER: str = ""
INPUT_DATA_STRING: str = ""
INPUT_DATA_PREFIX: str = ""
INPUT_DATA_INTEGER_W_PRESORTED = ""

# with open("RandomInputIntegers.csv", "r") as r:
#     reader = csv.DictReader(r)

#     for row in reader:
#         n = str(row["n"])
#         values = list(map(str, row["values"].split()))
#         INPUT_DATA_INTEGER += f"{n}\n"
#         INPUT_DATA_INTEGER += " ".join(values) + "\n"

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
with open("PresortedRandomInput.csv", "r") as r:
    reader = csv.DictReader(r)

    for row in reader:
        n = str(row["n"]) 
        presortedness = str(row["presortedness"])  # The degree of presortedness 
        values = row["values"].split()
        # INPUT_DATA_INTEGER_W_PRESORTED += f"{n}\n"
        # INPUT_DATA_INTEGER_W_PRESORTED += f"{presortedness}" + "\n"
        # INPUT_DATA_INTEGER_W_PRESORTED += " ".join(values) + "\n"
        INPUT_DATA_INTEGER_W_PRESORTED += f"{n}\n{presortedness}\n{' '.join(values)}\n"
print("Presorted done")

def benchmark(algorithm: str, jar: str)-> \
    List[Tuple[int,float, int]]: ## remove extra int if needed
    results: List[Tuple[int,float,int, int]] = []

    if "INTEGERS" in algorithm:
        data = INPUT_DATA_INTEGER
    elif "STRINGS" in algorithm or "OBJECTS" in algorithm:
        data = INPUT_DATA_STRING
    elif "PRESORTED" in algorithm:
        data = INPUT_DATA_INTEGER_W_PRESORTED
    else:
        data = INPUT_DATA_PREFIX

    results_strings = run_java(jar, algorithm, data)
    print(results_strings)

    ##output parsing
    for line in results_strings.strip().split("\n"):
        split = line.strip().split()
        if len(split) == 2:  # n and time only
            n, time = int(split[0]), float(split[1])
            results.append((n, time, -1, -1))  # Default -1 for unused values
        elif len(split) == 3:  # n, time, comparisons
            n, time, comparisons = int(split[0]), float(split[1]), int(split[2])
            results.append((n, time, comparisons, -1))  # Default -1 for presortedness
        elif len(split) == 4:  # n, time, comparisons, presortedness
            n, time, comparisons, presortedness = (
                int(split[0]),
                float(split[1]),
                int(split[2]),
                int(split[3]),
            )
            results.append((n, time, comparisons, presortedness))
    return results

    # if(algorithm.split()[2] == "INTEGERS"):
    #     data = INPUT_DATA_INTEGER
    # elif(algorithm.split()[2] == "STRINGS" or algorithm.split()[2] == "OBJECTS"):
    #     data = INPUT_DATA_STRING
    # elif (algorithm.split()[2] == "PRESORTED"):
    #     data = INPUT_DATA_INTEGER_W_PRESORTED
    # else:
    #     data = INPUT_DATA_PREFIX
    # result_string = run_java(jar,algorithm,
    #         data)
    # print(result_string)
    # # for line in result_string.split("\n"):
    # #     split = line.strip().split()
    # #     if len(split) == 2:
    # #         results.append((int(split[0]), float(split[1])))
    # #     if len(split) == 3:
    # #         results.append((int(split[0]), float(split[1]), int(split[2])))
    # #     if len(split) == 4:
    # #         results.append((int(split[0]), float(split[1]), int(split[2]), str(split[3])))
    # # return results


INSTANCES_C: List[Tuple[str, str]] = {
    ("iterativeMergeSort Cutoff STRINGS", "SortingVariations/app/build/libs/app.jar"),
    # ("iterativeMergeSort Cutoff INTEGERS", "SortingVariations/app/build/libs/app.jar"),
    # ("insertionMergeSort Cutoff INTEGERS", "SortingVariations/app/build/libs/app.jar"),
    ("insertionMergeSort Cutoff STRINGS", "SortingVariations/app/build/libs/app.jar")
}

INSTANCES_PRESORTED : List[Tuple[str, str]] = {
    ("iterativeMergeSort Cutoff PRESORTED", "SortingVariations/app/build/libs/app.jar"),
    ("insertionMergeSort Cutoff PRESORTED", "SortingVariations/app/build/libs/app.jar")
}

INSTANCES_MERGESORT_BASECASE: List[Tuple[str,str]]= {
    ("recursiveMergeSort BaseCase INTEGERS", "SortingVariations/app/build/libs/app.jar"),
    ("recursiveMergeSort BaseCase STRINGS", "SortingVariations/app/build/libs/app.jar"),
    ("recursiveMergeSort BaseCase PREFIX", "SortingVariations/app/build/libs/app.jar"),
    ("recursiveMergeSort BaseCase OBJECT", "SortingVariations/app/build/libs/app.jar"),
}

INSTANCES_HORSERACE: List[Tuple[str,str]]= {
    ("recursiveMergeSort HorseRace INTEGERS", "SortingVariations/app/build/libs/app.jar"),
    #We unfortunately have to keep a placeholder to keep our architecture in main
    ("recursiveMergeSort HorseRace INTEGERS Arrays.sort", "SortingVariations/app/build/libs/app.jar"),
    ("levelSort HorseRace INTEGERS", "SortingVariations/app/build/libs/app.jar"),
    ("binomialSort HorseRace INTEGERS", "SortingVariations/app/build/libs/app.jar"),
    ("insertionMergeSort HorseRace INTEGERS", "SortingVariations/app/build/libs/app.jar")
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
    #             for value in benchmark(f"{algorithm} {cutoff}",jar):
    #                 writer.writerow({
    #                     'algorithm' : algorithm,
    #                     'n' : value[0],
    #                     'time' : value[1],
    #                     'comparisons' : value[2],
    #                     'cutoff' : cutoff
    #                 })

    with open("IteraInsertPresortedResults.csv", "w", newline='') as f:
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
        # Iterate through each instance in INSTANCES_PRESORTED
    for algorithm, jar in INSTANCES_PRESORTED:
        # Iterate through each cutoff value in LIST_OF_CUTOFFVALUES
        for cutoff in LIST_OF_CUTOFFVALUES:
            # Build the full command to run the Java program
            command = f"java -jar {jar} {algorithm} {cutoff}"

            # result = subprocess.run(command, shell=True, capture_output=True, text=True)
            # print(f"Java command output: {result.stdout}")

            # Execute the Java command using os.system (this runs the Java program)
            print(f"Running command: {command}")
            result_string = os.popen(
                command).read()  # Capture the output of the command

            # Process the result (assuming it's in the format you expect)
            for line in result_string.strip().split("\n"):
                # Extract values from the result line
                # This assumes the result string is in a format where you can split and map the values
                values = (
                    line.split()
                )  # This might need adjustment depending on your output format
                n = int(values[0])  # 'n' value
                time = float(values[1])  # Time value
                comparisons = int(values[2])  # Comparisons value
                presortedDegree = int(
                    values[3]
                )  # Presorted degree (make sure this matches your output)

                # Write the result row into the CSV file
                writer.writerow(
                    {
                        "algorithm": algorithm,
                        "n": n,
                        "presortedDegree": presortedDegree,
                        "time": time,
                        "comparisons": comparisons,
                        "cutoff": cutoff,
                    }
                )
    # with open("HorseRace.csv", "w") as f:  ##'resultsCutoffValues.csv'
    #     print("Done done")
    #     writer = csv.DictWriter(f,
    #         fieldnames = ['algorithm','n','time', 'cutoff'])
    #     writer.writeheader()
    #     for algorithm, jar in INSTANCES_HORSERACE:
    #             for value in benchmark(f"{algorithm}",jar):
    #                 writer.writerow({
    #                     'algorithm' : algorithm,
    #                     'n' : value[0],
    #                     'time' : value[1],
    #                 })
