from typing import List, Dict, Tuple
import numpy as np # type: ignore
from typing import List
import time
import csv
import string

# Timeout for subprocesses
TIMEOUT = 60
# Seed for reproducibility
SEED = 42
# Maximum iterations for scaling `NS`
I_MAX = 35
# Number of repetitions per value of `n`
M = 3

rng = np.random.default_rng(SEED)

NS: List[int] = [int(30 * 1.41**i) for i in range(I_MAX)]

letters = string.ascii_lowercase

def generateRandomLetters():
    output = ""
    length = rng.integers(1,20)
    output = [letters[rng.integers(0, 26)] for _ in range(length)] 
    return ''.join(output)


with open('RandomInputString.csv', 'w', newline='') as f:
    writer = csv.writer(f) 

    #Header
    writer.writerow(["n", "values"])

    # I_MAX M times per value and create a random input of ints
    for i in range(I_MAX):
        for _ in range(M):  
            writer.writerow([NS[i], " ".join(str(generateRandomLetters()) for _ in range(NS[i]))])

with open('RandomInputIntegers.csv', 'w', newline='') as f:
    writer = csv.writer(f) 

    #Header
    writer.writerow(["n", "values"])

    # I_MAX M times per value and create a random input of ints
    for i in range(I_MAX):
        for _ in range(M):  
            writer.writerow([NS[i], " ".join(str(rng.integers(1, 2**28)) for _ in range(NS[i]))])

