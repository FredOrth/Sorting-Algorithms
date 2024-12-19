import matplotlib.pyplot as plt
import pandas as pd

# test setup to ensure it is working
# plt.plot([1, 2, 3], [4, 5, 6])
# plt.title("Test Plot")
# plt.savefig("test_plot.png")  # Save to a file to test non-GUI usage
# plt.show()


def simplify_algorithm_name(algorithm: str) -> str:
    """
    Simplify the algorithm name based on specific rules.
    """
    if "recursiveMergeSort BaseCase" in algorithm:
        return algorithm.replace("recursiveMergeSort BaseCase", "MergeSort")
    return algorithm

def rename_algorithm(row):
    """
    Rename the algorithm field to fix the Arrays.sort bug and distinguish between different variants.
    """
    if "recursiveMergeSort" in row["algorithm"] and "Arrays.sort" in row["algorithm"]:
        return "Arrays.sort"
    elif "recursiveMergeSort" in row["algorithm"] and "Arrays.sort" not in row["algorithm"]:
        return "recursiveMergeSort"
    if "binomialSort" in row["algorithm"] and "NonAdaptive" in row["algorithm"]:
        return "Binomial Sort Non-Adaptive"
    if "binomialSort" in row["algorithm"] and "Adaptive" in row["algorithm"]:
        return "Binomial Sort Adaptive"
    if "insertionMergeSort" in row["algorithm"]:
        return "Insertion Merge Sort"
    if "levelSort" in row["algorithm"] and "NonAdaptive" in row["algorithm"]:
        return "Level Sort Non-Adaptive"
    if "levelSort" in row["algorithm"] and "Adaptive" in row["algorithm"]:
        return "Level Sort Adaptive"

    return row["algorithm"]

def generate_basecase_plot(
    csv_file: str,
    title: str,
    x_label: str,
    y_label: str,
    firstGroup: str,
    sndGroup: str,
):

    data = pd.read_csv(csv_file)
    data["algorithm"] = data["algorithm"].apply(simplify_algorithm_name)
    grouped = data.groupby("algorithm")

    plt.figure(figsize=(10, 6))
    for algorithm, group in grouped:
        plt.plot(group[firstGroup], group[sndGroup], label=algorithm)

    plt.xlabel(x_label)
    plt.ylabel(y_label)
    plt.title(title)
    plt.legend()
    plt.grid(True)
    plt.savefig(f"{title}.png")
    plt.show()


def generate_scatter_plot(csv_file: str, title: str, x_label: str, y_label: str):
    # Load the CSV file
    data = pd.read_csv(csv_file)

    # Strip whitespace from column names
    data.columns = data.columns.str.strip()

    # Ensure numeric columns are properly formatted
    data["cutoff"] = pd.to_numeric(data["cutoff"], errors="coerce")
    data["time"] = pd.to_numeric(data["time"], errors="coerce")
    data["comparisons"] = pd.to_numeric(data["comparisons"], errors="coerce")

    # Drop rows with invalid numeric values
    data = data.dropna(subset=["cutoff", "time", "comparisons"])

    # Group by algorithm and cutoff, then calculate mean
    grouped = (
        data.groupby(["algorithm", "cutoff"])[["time", "comparisons"]]
        .mean()
        .reset_index()
    )

    # Debug: Print the grouped DataFrame
    print("Grouped Data:\n", grouped)

    # Create the plot
    plt.figure(figsize=(10, 6))

    # Iterate over unique algorithms
    for algorithm in grouped["algorithm"].unique():
        # Filter data for the current algorithm
        mean_data = grouped[grouped["algorithm"] == algorithm]

        # Debug: Print data for the current algorithm
        print(f"Data for {algorithm}:\n", mean_data)

        # Scatter plot for the current algorithm
        plt.scatter(mean_data["cutoff"], mean_data["comparisons"], label=algorithm, alpha=0.7)

    # Add labels, title, and grid
    plt.xlabel(x_label)
    plt.ylabel(y_label)
    plt.title(title)
    plt.legend(title="Algorithm")
    plt.grid(True)

    # Save and display the plot
    plt.savefig(f"{title}.png")
    plt.show()


def horseracePlotter(
        csv_file: str, title: str, x_label: str, y_label: str, log_y: bool = False
):
    """
    Generate a comparison plot for all algorithms based on runtime.
    """
    # Load the CSV file
    data = pd.read_csv(csv_file)

    # Fix algorithm names
    data["algorithm"] = data.apply(rename_algorithm, axis=1)

    # Ensure columns are properly formatted
    data["n"] = pd.to_numeric(data["n"], errors="coerce")
    data["time"] = pd.to_numeric(data["time"], errors="coerce")

    # Drop rows with invalid numeric values
    data = data.dropna(subset=["n", "time"])

    # Group data by algorithm and size
    grouped = data.groupby(["algorithm", "n"])["time"].median().reset_index()

    # Debug: Print grouped data
    print("Grouped Data:\n", grouped)

    # Create the plot
    plt.figure(figsize=(12, 6))

    # Iterate over unique algorithms
    for algorithm in grouped["algorithm"].unique():
        # Filter data for the current algorithm
        algo_data = grouped[grouped["algorithm"] == algorithm]

        # Plot data for the current algorithm
        plt.plot(
            algo_data["n"],
            algo_data["time"],
            label=algorithm,
            marker="o",
            alpha=0.8,
        )

    # Log-transform the y-axis if enabled
    if log_y:
        plt.yscale("log")
        y_label += " (Log Scale)"

    # Add labels, title, legend, and grid
    plt.xlabel(x_label)
    plt.ylabel(y_label)
    plt.title(title)
    plt.legend(title="Algorithm")
    plt.grid(True)

    # Save and display the plot
    plt.savefig(f"{title.replace(' ', '_')}.png")
    plt.show()
    plt.show()


def generate_plot(
    csv_file: str,
    title: str,
    x_label: str,
    y_label: str,
    firstGroup: str,
    sndGroup: str,
):

    data = pd.read_csv(csv_file)
    grouped = data.groupby("algorithm")

    plt.figure(figsize=(10, 6))
    for algorithm, group in grouped:
        plt.plot(group[firstGroup], group[sndGroup], label=algorithm)

    plt.xlabel(x_label)
    plt.ylabel(y_label)
    plt.title(title)
    plt.legend()
    plt.grid(True)
    plt.savefig(f"{title}.png")
    plt.show()


def generate_scatter_plot2(csv_file: str, title: str, x_label: str, y_label: str):
    # Load the CSV file
    data = pd.read_csv(csv_file)

    # Strip whitespace from column names
    data.columns = data.columns.str.strip()

    # Ensure numeric columns are properly formatted
    data["cutoff"] = pd.to_numeric(data["cutoff"], errors="coerce")
    data["time"] = pd.to_numeric(data["time"], errors="coerce")
    data["comparisons"] = pd.to_numeric(data["comparisons"], errors="coerce")

    # Drop rows with invalid numeric values
    data = data.dropna(subset=["cutoff", "time", "comparisons"])

    # Group by algorithm and cutoff, then calculate mean
    grouped = (
        data.groupby(["algorithm", "cutoff"])[["time", "comparisons"]]
        .mean()
        .reset_index()
    )

    # Debug: Print the grouped DataFrame
    print("Grouped Data:\n", grouped)

    # Create the plot
    plt.figure(figsize=(10, 6))

    # Iterate over unique algorithms
    for algorithm in grouped["algorithm"].unique():
        # Filter data for the current algorithm
        mean_data = grouped[grouped["algorithm"] == algorithm]

        # Debug: Print data for the current algorithm
        print(f"Data for {algorithm}:\n", mean_data)

        # Scatter plot for the current algorithm
        plt.scatter(mean_data["algorithm"], mean_data["time"], label=algorithm, alpha=0.7)

    # Add labels, title, and grid
    plt.xlabel(x_label)
    plt.ylabel(y_label)
    plt.title(title)
    plt.legend(title="Algorithm")
    plt.grid(True)

    # Save and display the plot
    plt.savefig(f"{title}.png")
    plt.show()


if __name__ == "__main__":
    # generate_plot(
    #     "resultsMergesort.csv",
    #     "Mergesort Performance (Comparisons)",
    #     "Number of Comparisons",
    #     "Time (seconds)",
    # )

    # generate_scatter_plot(
    #     "resultsCutoffValues.csv",
    #     "C vs comparisons",
    #     "cutoff",
    #     "Number of Comparisons",
    # )

    # generate_scatter_plot2(
    #     "resultsCutoffValues.csv",
    #     "C vs time",
    #     "cutoff",
    #     "time",
    # )

    # generate_scatter_plot2(
    #     "HorseRace.csv",
    #     "Horse Race",
    #     "Size",
    #     "Time",
    # )

    horseracePlotter(
        "HorseRace.csv",
        "Horse Race: Algorithm Comparison",
        "Input Size (n)",
        "Runtime (seconds)",
        # log_y=True,  # Set to True for a log-transformed y-axis
    )

    # generate_scatter_plot(
    #     "resultsCutoffValues.csv",
    #     "C vs time",
    #     "cutoff",
    #     "time",
    #     "cutoff",
    #     "time",
    # )

    # generate_basecase_plot(
    #     "MergeSortBaseCase.csv",
    #     "Mergesort DataType Performance",
    #     "Comparisons",
    #     "Time",
    #     "comparisons",
    #     "time",
    # )
