# Sorting Algorithm Variants & Performance Analysis

An implementation and empirical comparison of merge-based sorting algorithms, written primarily in Java with a Python-based benchmarking and visualization pipeline.

The project explores how different merge strategies and algorithmic optimizations affect sorting performance across varying input sizes and levels of presortedness.

## Overview

The implementations are evaluated against a custom recursive MergeSort used as a baseline.

The baseline is intentionally kept relatively simple: it recursively divides the input and performs standard linear merging without galloping or other adaptive optimizations. This provides a useful reference point when comparing more advanced merge-based sorting strategies.

The experiments investigate both execution time and comparison count, allowing the algorithms to be compared not only by theoretical complexity, but by their practical behaviour on different kinds of input.

## Implementations

The Java portion of the project contains the sorting algorithm implementations and variations.

The experiments include comparisons of merge-based algorithms and investigate factors such as:

- input size
- degree of presortedness
- merge strategy
- algorithm cut-off values
- number of comparisons
- execution time
- parallel execution and thread scaling

The repository also contains a custom recursive MergeSort implementation used as the experimental baseline.

## Experimental Pipeline

The benchmarking pipeline is written in Python and handles input generation, experiment execution and visualization of the results.

The workflow is:

1. Generate input datasets:

   ```bash
   python GenerateRandomInput.py
