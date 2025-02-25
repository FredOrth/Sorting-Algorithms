import matplotlib.pyplot as plt
import pandas as pd

def generate_plot(csv_files: list, labels: list, title: str, x_label: str, y_label: str, log_x=False, log_y=False):
    plt.figure(figsize=(10, 6))

    for csv_file, label in zip(csv_files, labels):
        # Read the CSV file
        data = pd.read_csv(csv_file, delimiter=",")
        # Strip whitespace from column names
        data.columns = data.columns.str.strip()

        # Standardize column names
        if "Deviation (ns)" in data.columns:
            data.rename(columns={"Deviation (ns)": "Variation (ns)"}, inplace=True)

        # Debugging: Print the standardized column names
        print(f"Standardized Columns in {csv_file}: {data.columns}")

        # Plot the line
        plt.plot(
            data["Cutoff"],
            data["Time (ns)"],
            label=label,
            marker="o",
            linestyle="-",
        )

        plt.errorbar(
            data["Cutoff"],
            data["Time (ns)"],
            yerr=data["Variation (ns)"], 
            fmt="none", 
            capsize=5,  
            color=plt.gca().lines[-1].get_color(), 
        )

    plt.xlabel(x_label)
    plt.ylabel(y_label)
    plt.title(title)
    plt.legend()
    plt.grid(True)

    if log_x:
        plt.xscale("log") 
    if log_y:
        plt.yscale("log") 

    # Save and show the plot
    plt.savefig(f"{title}.png")
    plt.show()

if __name__ == '__main__':
    generate_plot(
        csv_files=[
            "ParallelRecursiveMergeSortCutoffInsertionSortBaseCase.csv",
            "ParallelRecursiveMergeSortCutoffMergeSortBaseCase.csv",
            "ParallelRecursiveMergeSortCutoffInsertionSortBaseCaseParallelMerge.csv",
            "ParallelRecursiveMergeSortCutoffMergeSortBaseCaseParallelMerge.csv"
        ],
        labels=[
            "Insertion Sort Base Case",
            "Merge Sort Base Case",
            "Insertion Sort Parallel Merge",
            "Merge Sort Parallel Merge"
        ],
        title="Parallel Recursive Merge Sort Cutoff Experiments with Variations",
        x_label="Cutoff Value",
        y_label="Time (ns)",
        log_x=True,  # Enable log scale for x-axis
        log_y=True   # Enable log scale for y-axis
    )