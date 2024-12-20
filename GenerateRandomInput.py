from typing import List, Dict, Tuple
import numpy as np  # type: ignore
from typing import List
import time
import csv
import string

# Seed for reproducibility
SEED = 42
# Maximum iterations for scaling `NS`
I_MAX = 15
# Number of repetitions per value of `n`
M = 50

rng = np.random.default_rng(SEED)

NS: List[int] = [int(1600*1.55**i) for i in range(I_MAX)]

letters = string.ascii_lowercase


def generateRandomLetters():
    output = ""
    length = rng.integers(1, 20)
    output = [letters[rng.integers(0, 26)] for _ in range(length)]
    return "".join(output)


def generatePresortedArray(array_size: int, presortedness: int) -> List[int]:
    # Generate a random array of integers
    array = rng.integers(1, 2**28, size=array_size).tolist()

    if presortedness == 0:  ##unsorted
        # Completely unsorted array (no modification)
        rng.shuffle(array)
    elif presortedness == 1:  ##"partially_sorted"
        # Partially sorted array (shuffle 50% of it)
        sorted_part = sorted(array)
        partial_array = sorted_part[: array_size // 2]  # Keep first half sorted
        shuffled_part = rng.choice(array, size=array_size // 2, replace=False)
        array = partial_array + shuffled_part.tolist()
        rng.shuffle(array)  # aleast some disorder
    elif presortedness == 2:  ##"nearly_sorted"
        # Nearly sorted (sorted with a few inversions)
        array.sort()
        for _ in range(5):  # Introduce 5 random inversions
            idx1 = rng.integers(0, array_size)
            idx2 = rng.integers(0, array_size)
            array[idx1], array[idx2] = array[idx2], array[idx1]
    elif presortedness == 3:  ## "sorted"
        array.sort()
    return array


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

    # Header
    writer.writerow(["n", "values"])

    # I_MAX M times per value and create a random input of ints
    for i in range(I_MAX):
        for _ in range(M):
            writer.writerow([NS[i], " ".join(str(rng.integers(1, 2**28)) for _ in range(NS[i]))])

with open("PresortedRandomInput.csv", "w", newline='') as f:
    writer = csv.writer(f)

    # Header including presortedness level
    writer.writerow(["n", "presortedness", "values"])

    for i in range(I_MAX):
        for _ in range(M):
            # Generate inputs with varying presortedness
            for presortedness in [
                0,
                1,
                2,
                3,
            ]:
                array = generatePresortedArray(NS[i], presortedness)
                writer.writerow([NS[i], presortedness, " ".join(map(str, array))])