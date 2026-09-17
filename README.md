# Sorting Algorithms — Implementation & Performance Analysis

An implementation and empirical comparison of merge-based sorting algorithms, written primarily in Java with a Python-based benchmarking and visualization pipeline.

The project explores how different merge strategies and algorithmic optimizations affect sorting performance across varying input sizes and levels of presortedness.

## Overview

The implementations are evaluated against a custom recursive MergeSort used as a baseline.

The baseline is intentionally kept simple: it recursively divides the input and performs standard linear merging without galloping or other adaptive optimizations. This provides a useful reference point when comparing more advanced merge-based sorting strategies.

The experiments investigate both execution time and comparison count, making it possible to compare the algorithms not only by theoretical complexity, but also by their practical behavior on different types of input.

## Implementations

The Java portion of the project contains the sorting algorithm implementations and variations.

The experiments investigate factors such as:

- Input size
- Degree of presortedness
- Merge strategy
- Algorithm cut-off values
- Number of comparisons
- Execution time
- Parallel execution and thread scaling

The repository also contains a custom recursive MergeSort implementation used as the experimental baseline.

## Experimental Pipeline

The benchmarking pipeline is written in Python and handles input generation, experiment execution, data collection, and visualization of the results.

The workflow is:

1. Generate the input datasets:

   ```bash
   python GenerateRandomInput.py
   ```

2. Run the experiments:

   ```bash
   python Experiment.py
   ```

   Some experiment configurations may take a while to complete.

3. Generate the plots:

   ```bash
   python createPlots.py
   ```

The repository also contains generated plots, allowing the results to be inspected without rerunning the full experiment suite.

## Technologies

- **Java** — sorting algorithm implementations
- **Python** — experiment orchestration and data processing
- **Matplotlib** — performance visualization
- **CSV** — generated experimental data

## What I Explored

The main focus of the project was not simply implementing sorting algorithms, but investigating how implementation choices affect real-world performance.

In particular, the project explores the relationship between theoretical algorithm design and observed behavior, including how merge strategies perform as the structure and degree of presortedness of the input changes.

The work involved implementing the algorithms, designing reproducible experiments, collecting performance data, and analyzing the resulting measurements.
