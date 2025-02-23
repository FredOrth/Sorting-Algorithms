import pandas as pd
import matplotlib.pyplot as plt

#Generate plot for mergesort basecase and different types


#Generate plots for cutoff values for insertion and recursive mergesort

import pandas as pd
import matplotlib.pyplot as plt

# Load the data from CSV file
df = pd.read_csv('CutoffValues.csv')

# Create a plot
plt.figure(figsize=(10, 6))

# Loop through each algorithm and plot its median time for each cutoff
for algorithm in df['algorithm'].unique():
    # Group the data by 'cutoff' and calculate the median 'time' for each cutoff
    subset = df[df['algorithm'] == algorithm].groupby('cutoff')['time'].median()
    
    # Plot the median values for the algorithm
    plt.plot(subset.index, subset.values, label=f'Algorithm {algorithm}', marker='o')

# Adding labels and title
plt.xlabel('Cutoff')
plt.ylabel('Median Time')
plt.title('Median Time vs Cutoff for Different Algorithms')
plt.legend()

# Show the plot
plt.grid(True)
plt.show()


#Generate plot for level- and biosort 
df = pd.read_csv("LevelAndBioSort.csv")

df = df.sort_values(by=["degree of presortedness"])

#Algorithm + cutoff
df["label"] = df["algorithm"] + " (cutoff=" + df["cutoff"].astype(str) + ")"

# Median
median_df = df.groupby(["label", "degree of presortedness"])["comparisons"].median().reset_index()

# Plot
plt.figure(figsize=(10, 6))

for label, group in median_df.groupby("label"):
    plt.plot(group["degree of presortedness"], group["comparisons"], marker="o", label=label)


plt.xlabel("Degree of Presortedness")
plt.ylabel("Median Comparisons")
plt.title("Algorithm Performance by Presortedness and Cutoff (Median Comparisons)")

plt.xticks([0, 1, 2, 3])

plt.legend()
plt.grid(True)
plt.savefig("Plot for presortedness", dpi=300, bbox_inches="tight")

#Horse race plot


# Load the CSV file
df = pd.read_csv("HorseRaceResults.csv")

# Compute the median time for each algorithm at each n
median_times = df.groupby(["n", "algorithm"])["time"].median().reset_index()

# Get the list of unique algorithms
algorithms = median_times["algorithm"].unique()

# Plot
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

# Show plot
plt.show()
