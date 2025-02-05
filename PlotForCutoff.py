import pandas as pd
import matplotlib.pyplot as plt

# Load the data
file_path = 'resultsCutoffValues_LevelBSort.csv'
data = pd.read_csv(file_path)

# Ensure 'cutoff' column has valid values
data = data.dropna(subset=['n', 'time', 'cutoff'])

# Group data by 'cutoff' and calculate the average time for each cutoff
average_time_per_cutoff = data.groupby('cutoff')['time'].mean().reset_index()

# Plot: Time vs Cutoff
plt.figure(figsize=(10, 6))
plt.plot(average_time_per_cutoff['cutoff'], average_time_per_cutoff['time'], marker='o', linestyle='-', color='b', label='Average Time')
plt.title("Average Time vs Cutoff")
plt.xlabel("Cutoff")
plt.ylabel("Average Time (seconds)")
plt.grid(True)
plt.legend()
plt.tight_layout()
plt.show()
