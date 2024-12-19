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


def generate_scatter_plot(
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
        plt.scatter(group[firstGroup], group[sndGroup], label=algorithm)

    plt.xlabel(x_label)
    plt.ylabel(y_label)
    plt.title(title)
    plt.legend()
    plt.grid(True)
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
        "cutoff",
        "comparisons",
    )

    generate_scatter_plot(
        "resultsCutoffValues.csv",
        "C vs time",
        "cutoff",
        "time",
        "cutoff",
        "time",
    )

    # generate_basecase_plot(
    #     "MergeSortBaseCase.csv",
    #     "Mergesort DataType Performance",
    #     "Comparisons",
    #     "Time",
    #     "comparisons",
    #     "time",
    # )
