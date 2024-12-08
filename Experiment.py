from typing import List, Dict, Tuple
import numpy as np # type: ignore
from typing import List
import time
import csv

import subprocess
#Timeout 
TIMEOUT = 35
SEED = 42
#How many different values of M
I_MAX = 30
#How many repetitions per m
M = 5

rng = np.random.default_rng(SEED)
NS: List[int] = [int(1300 * 1.37**i) \
    for i in range(I_MAX)]

def run_java(jar: str, arg: str, input: str)->str:
    p = subprocess.Popen(['java','-Xmx8g', '-jar',jar,arg], 
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

    for n in NS:
        try: 
            result_n: List[Tuple[int,float, int]] = list()
            for i in range(M):
                input: List[int] = INPUT_DATA[n][i]
                diff, comp = measure(algorithm,jar,
                    input)
                result_n.append((n,diff, comp))
            results += result_n
        except subprocess.TimeoutExpired:
            break
    return results

# def build_java_project():
#     subprocess.run(['./gradlew', 'build'], check=True)
    
INSTANCES: List[Tuple[str,str]] = {
    ("recursiveMergeSort", "SortingVariations/app/build/libs/app.jar"),
    # ("insertionMergeSort", "SortingVariations/app/build/libs/app.jar")
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