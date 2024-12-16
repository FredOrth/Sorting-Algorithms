from typing import List, Dict, Tuple
import numpy as np # type: ignore
from typing import List
import time
import csv
import string
import random

import subprocess
#Timeout 
TIMEOUT = 4
SEED = 42
#How many different values of M
I_MAX = 30
#How many repetitions per m
M = 5

rng = np.random.default_rng(SEED)
NS: List[int] = [int(1250* 1.39**i) \
    for i in range(I_MAX)]

def run_java(jar: str, arg: str, input: str)->str:
    args = arg.split()
    p = subprocess.Popen(['java','-Xmx8g', '-jar',jar] + args, 
        stdin=subprocess.PIPE, 
        stdout=subprocess.PIPE)
    (output,_) = p.communicate(input.encode('utf-8'), 
        timeout=TIMEOUT)
    return output.decode('utf-8') 


INPUT_DATA: Dict[int, List[List[int]]] = {
    n : [rng.integers(1, 2**28, n) \
        for _ in range(M)] \
for n in NS
}

letters = string.ascii_lowercase

def generateRandomLetters():
    output = ""
    length = rng.integers(1,20)
    output = [letters[rng.integers(0, 26)] for _ in range(length)] 
    return ''.join(output)

INPUT_DATA_STRINGS: Dict[int, List[List[str]]] = {
    n: [generateRandomLetters() for _ in range(M)] for n in NS
}

def measure(algorithm: str, jar: str, 
    input: List[int])->float:
    input_string: str = f'{len(input)}\n' + \
        ' '.join(map(str,input))
    start: float = time.time()
    result_string: str = run_java(jar, algorithm, 
        input_string)
    end: float = time.time()
    return end - start, result_string
    
def benchmark(algorithm: str, jar: str, integers: bool)-> \
    List[Tuple[int,float, int]]:
    results: List[Tuple[int,float,int]] = list()

    for n in NS:
        try: 
            result_n: List[Tuple[int,float, int]] = list()
            for i in range(M):
                if integers:
                    input: List[int] = INPUT_DATA[n][i]
                else:
                    input: List[str] = INPUT_DATA_STRINGS[n][i]
                diff, comp = measure(algorithm,jar,
                    input)
                result_n.append((float(n),float(diff),float(comp)))
            results += result_n
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

LIST_OF_CUTOFFVALUES: list[int] = {
    1,#Cutoff-value 1 is equal to the normal sorting algorithms. We should probably just refactor the other experiment to take this as well... oh well...
    2,
    4,
    8,
    16
}

if __name__ == '__main__':
    
    #build_java_project
    
    with open('resultsCutoffValues.csv','w') as f:
        writer = csv.DictWriter(f, 
            fieldnames = ['algorithm','n','time', 'comparisons', 'cutoff'])
        writer.writeheader()
        for algorithm, jar in INSTANCES_C:
            results: List[Tuple[int,float]] = []
            for cutoff in LIST_OF_CUTOFFVALUES:
                for n,t,c in benchmark(f"{algorithm} {cutoff}",jar, True):
                    writer.writerow({ 
                        'algorithm' : algorithm,
                        'n' : n,
                        'time' : t,
                        'comparisons' : c,
                        'cutoff' : cutoff
                    })
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