import numpy as np
import pandas as pd
import matplotlib.pyplot as plt

def load_data(filename):
    # Load the data from CSV file
    df = pd.read_csv("CutoffValues.csv")
    return pd.read_csv(filename)

# Generate plot for mergesort basecase and different types

def plot_merge_sort_base_case(df, output_file=None):

    plt.figure(figsize=(10, 6))

    # Group by 'n' and calculate the median time
    median_times = df.groupby('n')['time'].median()

    # Plot empirical median times
    plt.plot(median_times.index, median_times.values, label='Empirical Time', marker='o')

    # Calculate the theoretical n*log(n) values for the given n-values.
    n_values = np.array(median_times.index, dtype=float)
    theoretical = n_values * np.log(n_values)

    # Scale the theoretical curve to match the empirical data.
    # Here we scale it so the first value of the theoretical curve equals the first empirical median time.
    if theoretical[0] != 0:
        scaling_factor = median_times.values[0] / theoretical[0]
    else:
        scaling_factor = 1
    theoretical_scaled = theoretical * scaling_factor

    # Plot the theoretical runtime curve
    plt.plot(n_values, theoretical_scaled, label='n log n (theoretical, scaled)',
             linestyle='--', color='red')

    plt.xlabel('n')
    plt.ylabel('Median Time (s)')
    plt.title('Empirical Median Time vs n for MergeSort Base Case')
    plt.legend()
    plt.grid(True)

    if output_file:
        plt.savefig(output_file, dpi=300, bbox_inches="tight")
    else:
        plt.show()


# Generate plots for cutoff values for insertion and recursive mergesort
def plot_cutoff_values(df, output_file=None):
    """Plot median time vs cutoff for various algorithms."""
    plt.figure(figsize=(10, 6))
    # Loop through each algorithm and plot its median time for each cutoff
    for algorithm in df['algorithm'].unique():
        # Group the data by 'cutoff' and calculate the median 'time' for each cutoff
        subset = df[df['algorithm'] == algorithm].groupby('cutoff')['time'].median()
        # Plot the median values for the algorithm
        plt.plot(subset.index, subset.values, label=f'Algorithm {algorithm}', marker='o')
    plt.xlabel('Cutoff')
    plt.ylabel('Median Time')
    plt.title('Median Time vs Cutoff for Various Algorithms')
    plt.legend()
    # Show the plot
    plt.grid(True)
    plt.savefig(output_file, dpi=300, bbox_inches="tight")

def plot_level_biosort(df, output_file=None):
    """Plot algorithm performance by presortedness and cutoff (median comparisons)."""
    # Generate plot for level- and biosort
    # df = pd.read_csv("LevelAndBioSort.csv")
    df = df.sort_values(by=["degree of presortedness"])

    # Algorithm + cutoff
    df["label"] = df["algorithm"] + " (cutoff=" + df["cutoff"].astype(str) + ")"

    # Median
    median_df = df.groupby(["label", "degree of presortedness"])["comparisons"].median().reset_index()
    plt.figure(figsize=(10, 6))

    for label, group in median_df.groupby("label"):
        plt.plot(group["degree of presortedness"], group["comparisons"], marker="o", label=label)
    plt.xlabel("Degree of Presortedness")
    plt.ylabel("Median Comparisons")
    plt.title("Algorithm Performance by Presortedness and Cutoff (Median Comparisons)")
    plt.xticks([0, 1, 2, 3])
    plt.legend()
    plt.grid(True)
    if output_file:
        plt.savefig(output_file, dpi=300, bbox_inches="tight")
    else:
        plt.show()


# Horse race plot
def plot_horse_race(df, output_file=None):
    #df = pd.read_csv("HorseRaceResults.csv")

    # Compute the median time for each algorithm at each n
    median_times = df.groupby(["n", "algorithm"])["time"].median().reset_index()

    # Get the list of unique algorithms
    algorithms = median_times["algorithm"].unique()

    plt.figure(figsize=(10, 6))
    for algo in algorithms:
        subset = median_times[median_times["algorithm"] == algo]
        plt.plot(subset["n"], subset["time"], marker="o", label=algo)

    # Labels and title
    plt.xlabel("n")
    plt.ylabel("Median Time")
    plt.title("Algorithm Performance Comparison")
    plt.legend(title="Algorithm")
    plt.grid(True)
    if output_file:
        plt.savefig(output_file, dpi=300, bbox_inches="tight")
    else:
        plt.show()


if __name__ == "__main__":
    # Load the CSV data (assuming 'MergeSortBaseCase.csv' is in your working directory)
    df_merge = pd.read_csv('MergeSortBaseCase.csv')
    plot_merge_sort_base_case(df_merge)

    # df_cutoff = load_data('CutoffValues.csv')
    # plot_cutoff_values(df_cutoff)

    # df_lvlbio = load_data('LevelAndBioSort.csv')
    # plot_level_biosort(df_lvlbio)

    # df_horse_race = load_data('HorseRaceResults.csv')
    # plot_horse_race(df_horse_race)

