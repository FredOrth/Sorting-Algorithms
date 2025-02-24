import numpy as np
import pandas as pd
import matplotlib.pyplot as plt

def load_data(filename):
    # Load the data from CSV file
    df = pd.read_csv("CutoffValues.csv")
    return pd.read_csv(filename)

# Generate plot for mergesort basecase and different types
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt

import numpy as np
import pandas as pd
import matplotlib.pyplot as plt

def plot_merge_sort_base_case(df, output_file=None):

    # Set up the plot
    plt.figure(figsize=(10, 6))

    # Get unique algorithms
    algorithms = df['algorithm'].unique()

    # Plot each algorithm's empirical data
    for algorithm in algorithms:
        # Group by 'n' and calculate the median time for each algorithm
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

    # Labels and title
    plt.xlabel('n')
    plt.ylabel('Median Time (s)')
    plt.title("Empirical Median Time vs n for MergeSort Base Case (By Algorithm)")
    plt.legend()
    plt.grid(True)

    # Save the plot if an output file is provided, otherwise show it
    if output_file:
        plt.savefig(output_file, dpi=300, bbox_inches="tight")
        print(f"Plot saved as {output_file}")
    else:
        plt.show()  # Display the plot if no output file is given

# Example usage:
df = pd.read_csv("mergeSortBaseCase.csv")
plot_merge_sort_base_case(df)  # This will display the plot
plot_merge_sort_base_case(df, "merge_sort_comparison.png")  # This will save the plot as 'merge_sort_comparison.png'



def plot_time_vs_comparisons_by_type(df, output_file=None):
    """
    Plot median time vs. median comparisons for each algorithm type.
    Each algorithm (e.g., INTEGERS, STRINGS, OBJECT, PREFIX) is plotted with a distinct marker/color.
    """
    plt.figure(figsize=(10, 6))

    # Define marker and color options for variety
    markers = ['o', 's', '^', 'D', 'v', 'P', '*']
    colors = ['blue', 'red', 'green', 'purple', 'orange', 'cyan', 'magenta']

    # Get the unique algorithm types
    unique_algorithms = df['algorithm'].unique()

    # For each algorithm type, group by n and compute the median values
    for idx, algorithm in enumerate(unique_algorithms):
        df_algo = df[df['algorithm'] == algorithm]
        median_df = df_algo.groupby('n').agg({'time': 'median', 'comparisons': 'median'}).reset_index()

        # Scatter plot: x-axis is median comparisons, y-axis is median time
        plt.scatter(median_df['comparisons'], median_df['time'],
                    label=algorithm,
                    marker=markers[idx % len(markers)],
                    color=colors[idx % len(colors)])

        # Optionally, add a linear fit line for this algorithm type
        coeffs = np.polyfit(median_df['comparisons'], median_df['time'], 1)
        poly_eqn = np.poly1d(coeffs)
        x_vals = np.linspace(median_df['comparisons'].min(), median_df['comparisons'].max(), 100)
        plt.plot(x_vals, poly_eqn(x_vals),
                 linestyle='--', color=colors[idx % len(colors)],
                 alpha=0.7)

    plt.xlabel("Median Comparisons")
    plt.ylabel("Median Time (s)")
    plt.title("Empirical Running Time vs. Number of Comparisons by Algorithm Type")
    plt.legend(title="Algorithm")
    plt.grid(True)

    if output_file:
        plt.savefig(output_file, dpi=300, bbox_inches="tight")
        print(f"Plot saved as {output_file}")
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
    title = "Median Time vs Cutoff for Various Algorithms"
    plt.title(title)
    plt.legend()
    # Show the plot
    plt.grid(True)
    # plt.savefig(output_file, dpi=300, bbox_inches="tight")
    plt.savefig(f"{title}.png")

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
    title = "Algorithm Performance by Presortedness and Cutoff (Median Comparisons)"
    plt.title(title)
    plt.xticks([0, 1, 2, 3])
    plt.legend()
    plt.grid(True)
    #plt.savefig(output_file, dpi=300, bbox_inches="tight")
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
    #plt.savefig(output_file, dpi=300, bbox_inches="tight")
    plt.savefig(f"{title}.png")


if __name__ == "__main__":
    # Load the CSV data (assuming 'MergeSortBaseCase.csv' is in your working directory)
    # df_merge = load_data('MergeSortBaseCase.csv')
    # plot_merge_sort_base_case(df_merge)

    # df_cutoff = load_data('CutoffValues.csv')
    # plot_cutoff_values(df_cutoff)

    # df_lvlbio = load_data('LevelAndBioSort.csv')
    # plot_level_biosort(df_lvlbio)

    # df_horse_race = load_data('HorseRaceResults.csv')
    # plot_horse_race(df_horse_race)

    df = pd.read_csv("mergeSortBaseCase.csv")
    plot_time_vs_comparisons_by_type(df)