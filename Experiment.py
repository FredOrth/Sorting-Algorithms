from typing import List, Dict, Tuple
import numpy as np # type: ignore
from typing import List
import time
import csv

import subprocess
# Timeout
TIMEOUT = 45

# NS: List[int] = [int(850 * 1.45**i) \
#     for i in range(I_MAX)]

def run_java(jar: str, arg: str, input: str)->str:
    p = subprocess.Popen(['java','-Xmx8g', '-jar',jar,arg], 
        stdin=subprocess.PIPE, 
        stdout=subprocess.PIPE)
    (output,_) = p.communicate(input.encode('utf-8'), 
        timeout=TIMEOUT)
    return output.decode('utf-8') 

csv.field_size_limit(100000000)
INPUT_DATA: Dict[int, List[List[int]]] ={}


with open("RandomInput.csv", "r") as r:
    reader = csv.DictReader(r)
    #Make sure size limit for csv is big enough
    
    for row in reader:
        print(row["n"])
        n = int(row["n"])
        values = list(map(int, row["values"].split()))
        if n not in INPUT_DATA:
                INPUT_DATA[n] = []
        INPUT_DATA[n].append(values)



def measure(algorithm: str, jar: str, 
    input: List[int])->float:
    input_string: str = f'{len(input)}\n' + \
        ' '.join(map(str,input))
    start: float = time.time()
    result_string: str = run_java(jar, algorithm, 
        input_string)
    end: float = time.time()
    # assert result_string.strip() == 'null'
    return end - start, result_string

def benchmark(algorithm: str, jar: str)-> \
    List[Tuple[int,float, int]]:
    results: List[Tuple[int,float,int]] = list()

    for key, valueList in INPUT_DATA.items():
        for value in valueList:
            try: 
                diff, comp = measure(algorithm,jar,
                        value)
                results.append((key,diff, comp))
            except subprocess.TimeoutExpired:
                break
    return results

# def build_java_project():
#     subprocess.run(['./gradlew', 'build'], check=True)

INSTANCES: List[Tuple[str,str]] = {
    ("recursiveMergeSort", "SortingVariations/app/build/libs/app.jar"),
    # ("insertionMergeSort", "SortingVariations/app/build/libs/app.jar"),
    # ("iterativeMergeSort", "SortingVariations/app/build/libs/app.jar")
}

INSTANCES_C: List[Tuple[str,str]]= {
    ("RecursiveMergeSortCutoff", "SortingVariations/app/build/libs/app.jar"),
    ("insertionMergeSortCutoff", "SortingVariations/app/build/libs/app.jar"),
    # ("iterativeMergeSort", "SortingVariations/app/build/libs/app.jar")
}

if __name__ == '__main__':
    
    #build_java_project
    
    with open('resultsMergesortTest.csv','w') as f:
        writer = csv.DictWriter(f, 
            fieldnames = ['algorithm','n','time', 'comparisons'])
        writer.writeheader()
        for algorithm, jar in INSTANCES:
            results: List[Tuple[int,float]] = \
                benchmark(algorithm,jar)
            for (n,t,c) in results:
                # print(f"This is n: {n}, this is the time {t}, this is number of comparisons {c}")
                writer.writerow({ 
                    'algorithm' : algorithm,
                    'n' : n,
                    'time' : t,
                    'comparisons' : c
                })
    # with open('resultsCutoffValues.csv','w') as f:
    #     writer = csv.DictWriter(f, 
    #         fieldnames = ['algorithm','n','time', 'comparisons'])
    #     writer.writeheader()
    #     for algorithm, jar in INSTANCES:
    #         results: List[Tuple[int,float]] = \
    #             benchmark(algorithm,jar)
    #         for (n,t,c,cu) in results:
    #             # print(f"This is n: {n}, this is the time {t}, this is number of comparisons {c}")
    #             writer.writerow({ 
    #                 'algorithm' : algorithm,
    #                 'n' : n,
    #                 'time' : t,
    #                 'comparisons' : c,
    #                 'cutofff' : cu
    #             })
