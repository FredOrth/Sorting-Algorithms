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
        plt.scatter(mean_data["cutoff"], mean_data["time"], label=algorithm, alpha=0.7)

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

    generate_scatter_plot(
        "resultsCutoffValues.csv",
        "C vs comparisons",
        "cutoff",
        "Number of Comparisons",
    )

    generate_scatter_plot2(
        "resultsCutoffValues.csv",
        "C vs time",
        "cutoff",
        "time",
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
