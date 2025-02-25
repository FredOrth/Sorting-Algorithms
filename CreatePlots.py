import numpy as np
import pandas as pd
import matplotlib.pyplot as plt

def load_data(filename):
    # Load the data from CSV file
    df = pd.read_csv("CutoffValues.csv")
    return pd.read_csv(filename)

def plot_merge_sort_base_case(df, output_file=None):
    plt.figure(figsize=(10, 6))
    # Get unique algorithms
    algorithms = df['algorithm'].unique()
    for algorithm in algorithms:
        median_times = df[df['algorithm'] == algorithm].groupby('n')['time'].median()
        # Plot empirical median times for the current algorithm
        plt.plot(median_times.index, median_times.values, label=f'{algorithm} (Empirical)', marker='o')
    # Calculate the theoretical n*log(n) values for the given n-values (same for all algorithms)
    n_values = np.array(df['n'].unique(), dtype=float)
    theoretical = n_values * np.log(n_values)

    # Scale the theoretical curve to match the empirical data.
    # Here we scale it so the first value of the theoretical curve equals the first empirical median time.
    scaling_factor = df.groupby('n')['time'].median().values[0] / theoretical[0]
    theoretical_scaled = theoretical * scaling_factor
    # Plot the single theoretical runtime curve
    plt.plot(n_values, theoretical_scaled, label='n log n (theoretical, scaled)', linestyle='--', color='red')

    plt.xlabel('n')
    plt.ylabel('Median Time (s)')
    title = "Empirical Median Time vs n for MergeSort Base Case (By Algorithm)"
    plt.title(title)
    plt.legend()
    plt.grid(True)
    plt.savefig(f"{title}.png")


def plot_time_vs_comparisons_by_type(df, output_file=None):
    """
    Plot median time vs. median comparisons for each algorithm type.
    Each algorithm (e.g., INTEGERS, STRINGS, OBJECT, PREFIX) is plotted with a distinct marker/color.
    """
    plt.figure(figsize=(10, 6))
    # Define marker and color options for variety
    markers = ['o', 's', '^', 'D', 'v', 'P', '*']
    colors = ['blue', 'red', 'green', 'purple', 'orange', 'cyan', 'magenta']
    unique_algorithms = df['algorithm'].unique()
    for idx, algorithm in enumerate(unique_algorithms):
        df_algo = df[df['algorithm'] == algorithm]
        median_df = df_algo.groupby('n').agg({'time': 'median', 'comparisons': 'median'}).reset_index()
        #x-axis is median comparisons, y-axis is median time
        plt.scatter(median_df['comparisons'], median_df['time'],
                    label=algorithm,
                    marker=markers[idx % len(markers)],
                    color=colors[idx % len(colors)])
        # add a linear fit line for this algorithm type
        coeffs = np.polyfit(median_df['comparisons'], median_df['time'], 1)
        poly_eqn = np.poly1d(coeffs)
        x_vals = np.linspace(median_df['comparisons'].min(), median_df['comparisons'].max(), 100)
        plt.plot(x_vals, poly_eqn(x_vals),
                linestyle='--', color=colors[idx % len(colors)],
                alpha=0.7)
    plt.xlabel("Median Comparisons")
    plt.ylabel("Median Time (s)")
    title2 = "Empirical Running Time vs. Number of Comparisons by Algorithm Type"
    plt.title(title2)
    plt.legend(title="Algorithm")
    plt.grid(True)
    #plt.savefig("Comparisons vs time MergeSort", dpi=300, bbox_inches="tight")
    plt.savefig(f"{title2}.png")


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
    title = "Median Time vs Cutoff for Various Algorithms"
    plt.title(title)
    plt.legend()
    plt.grid(True)
    # plt.savefig(output_file, dpi=300, bbox_inches="tight")
    plt.savefig(f"{title}.png")


def plot_level_biosort_subplots(df, output_file=None):
    """Create two subplots stacked vertically:
    1. Degree of presortedness vs. average comparisons
    2. Degree of presortedness vs. median time
    """
    # Sort data
    df = df.sort_values(by=["degree of presortedness"])

    # Group by label and degree of presortedness
    avg_comparisons_df = (
        df.groupby(["algorithm", "degree of presortedness"])["comparisons"]
        .mean()
        .reset_index()
    )
    median_time_df = (
        df.groupby(["algorithm", "degree of presortedness"])["time"]
        .median()
        .reset_index()
    )
    # Create subplots (stacked vertically)
    fig, axes = plt.subplots(2, 1, figsize=(10, 12)) ## (2, 1) vertical (1, 2) for side by side

    for algorithm, group in avg_comparisons_df.groupby("algorithm"):
        axes[0].plot(
            group["degree of presortedness"],
            group["comparisons"],
            marker="o",
            label=algorithm.replace("_", " ").title(),
        )
    axes[0].set_xlabel("Degree of Presortedness")
    axes[0].set_ylabel("Average Comparisons")
    axes[0].set_title("Average Comparisons vs. Degree of Presortedness")
    axes[0].set_xticks([0, 1, 2, 3])
    axes[0].grid(True)
    axes[0].legend()

    for algorithm, group in median_time_df.groupby("algorithm"):
        axes[1].plot(
            group["degree of presortedness"],
            group["time"],
            marker="o",
            label=algorithm.replace("_", " ").title(),
        )
    axes[1].set_xlabel("Degree of Presortedness")
    axes[1].set_ylabel("Median Time")
    axes[1].set_title("Median Time vs. Degree of Presortedness")
    axes[1].set_xticks([0, 1, 2, 3])
    axes[1].grid(True)
    axes[1].legend()
    plt.savefig("presortedness.png")


def plot_level_biosort_by_cutoff(df, output_file=None):
    """Plot algorithm performance with cutoff on the x-axis and median time on the y-axis,
    showing only four lines for level and bionomial sort (adaptive and non-adaptive)."""
    df = df.sort_values(by=["cutoff"])
    median_df = df.groupby(["algorithm", "cutoff"])["time"].median().reset_index()

    plt.figure(figsize=(10, 6))
    for algorithm, group in median_df.groupby("algorithm"):
        plt.plot(
            group["cutoff"],
            group["time"],
            marker="o",
            label=algorithm.replace("_", " ").title(),
        )

    plt.xlabel("Cutoff")
    plt.ylabel("Median Time")
    title = "Level and Bionomial Sort Performance (Median Time)"
    plt.title(title)
    plt.legend()
    plt.grid(True)
    plt.savefig(f"{title}.png")


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
    title = "Algorithm Performance Comparison"
    plt.title(title)
    plt.legend(title="Algorithm")
    plt.grid(True)
    plt.savefig(f"{title}.png")

def plot_presortedness(df, output_file = None):


    df["cutoff"] = df["cutoff"]
    df["time"] = df["time"]

    median_times = df.groupby(["algorithm", "degree of presortedness", "cutoff"])["time"].median().reset_index()

    group_1 = ["binomialSort Presort INTEGERS Adaptive", "binomialSort Presort INTEGERS NonAdaptive"]
    group_2 = ["levelSort Presort INTEGERS NonAdaptive", "levelSort Presort INTEGERS Adaptive"]

    fig, axes = plt.subplots(1, 2, figsize=(16, 8), sharey=True)

    # Plot for group 1
    ax = axes[0]
    for (algorithm, presortedness), group in median_times[median_times["algorithm"].isin(group_1)].groupby(["algorithm", "degree of presortedness"]):
        ax.plot(group["cutoff"], group["time"], marker='o', linestyle='-', label=f"{algorithm}, {presortedness}")
    ax.set_xlabel("Cutoff")
    ax.set_ylabel("Median Time")
    ax.set_title("BinomialSort Algorithms")
    ax.legend()
    ax.grid(True)

    # Plot for group 2
    ax = axes[1]
    for (algorithm, presortedness), group in median_times[median_times["algorithm"].isin(group_2)].groupby(["algorithm", "degree of presortedness"]):
        ax.plot(group["cutoff"], group["time"], marker='o', linestyle='-', label=f"{algorithm}, {presortedness}")
    ax.set_xlabel("Cutoff")
    ax.set_title("LevelSort Algorithms")
    ax.legend()
    ax.grid(True)

    plt.tight_layout()
    plt.savefig("PresortednessLevelAndBioSort.png")

def plot_presortednessComparisons(df, output_file = None):


    df["cutoff"] = df["cutoff"]
    df["time"] = df["time"]

    median_times = df.groupby(["algorithm", "degree of presortedness", "cutoff"])["comparisons"].median().reset_index()

    group_1 = ["binomialSort Presort INTEGERS Adaptive", "binomialSort Presort INTEGERS NonAdaptive"]
    group_2 = ["levelSort Presort INTEGERS NonAdaptive", "levelSort Presort INTEGERS Adaptive"]

    fig, axes = plt.subplots(1, 2, figsize=(16, 8), sharey=True)

    # Plot for group 1
    ax = axes[0]
    for (algorithm, presortedness), group in median_times[median_times["algorithm"].isin(group_1)].groupby(["algorithm", "degree of presortedness"]):
        ax.plot(group["cutoff"], group["comparisons"], marker='o', linestyle='-', label=f"{algorithm}, {presortedness}")
    ax.set_xlabel("Cutoff")
    ax.set_ylabel("Median Time")
    ax.set_title("BinomialSort Algorithms")
    ax.legend()
    ax.grid(True)

    # Plot for group 2
    ax = axes[1]
    for (algorithm, presortedness), group in median_times[median_times["algorithm"].isin(group_2)].groupby(["algorithm", "degree of presortedness"]):
        ax.plot(group["cutoff"], group["comparisons"], marker='o', linestyle='-', label=f"{algorithm}, {presortedness}")
    ax.set_xlabel("Cutoff")
    ax.set_title("LevelSort Algorithms comparisons")
    ax.legend()
    ax.grid(True)

    plt.tight_layout()
    plt.savefig("PresortednessLevelAndBioSortComparisons.png")


if __name__ == "__main__":
    # df_merge = load_data('MergeSortBaseCase.csv')
    # plot_merge_sort_base_case(df_merge)

    # df_comp = pd.read_csv("mergeSortBaseCase.csv")
    # plot_time_vs_comparisons_by_type(df_comp)

    # df_cutoff = load_data('CutoffValues.csv')
    # plot_cutoff_values(df_cutoff)

    # df_lvlbio_presort = load_data('LevelAndBioSort.csv')
    # plot_level_biosort_subplots(df_lvlbio_presort)
    
    df_lvlbio_presort = load_data('LevelAndBioSort.csv')
    plot_presortedness(df_lvlbio_presort)
    
    df_lvlbio_presort = load_data('LevelAndBioSort.csv')
    plot_presortednessComparisons(df_lvlbio_presort)

    # df_lvlbio_cutoff= load_data("LevelAndBioSort.csv") #useless now that we have the other one. Convert to c based on presortedness
    # plot_level_biosort_by_cutoff(df_lvlbio_cutoff)

    # df_horse_race = load_data('HorseRaceResults.csv')
    # plot_horse_race(df_horse_race)
