import matplotlib.pyplot as plt
import pandas as pd

# test setup to ensure it is working
# plt.plot([1, 2, 3], [4, 5, 6])
# plt.title("Test Plot")
# plt.savefig("test_plot.png")  # Save to a file to test non-GUI usage
# plt.show()

def generate_plot(csv_file: str, title: str, x_label: str, y_label: str):

    data = pd.read_csv(csv_file)
    grouped = data.groupby("algorithm")

    plt.figure(figsize=(10, 6))
    for algorithm, group in grouped:
        plt.plot(group["n"], group["time"], label=algorithm, marker="o")

    plt.xlabel(x_label)
    plt.ylabel(y_label)
    plt.title(title)
    plt.legend()
    plt.grid(True)
    plt.savefig(f"{title}")
    plt.show()


if __name__ == '__main__':
    generate_plot(
        "resultsMergesort.csv",
        "Mergesort Performance",
        "Input Size (n)",
        "Time (seconds)",
    )

# # Generate plots for Cutoff Values results
# generate_plot(
#     "resultsCutoffValues.csv",
#     "Cutoff Values Impact",
#     "Input Size (n)",
#     "Time (seconds)",
# )
