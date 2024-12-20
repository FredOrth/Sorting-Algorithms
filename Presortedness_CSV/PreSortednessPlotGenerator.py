import pandas as pd
import matplotlib.pyplot as plt

# File information
file_info = {
    "Binomial_Adaptive.csv": ("Binomial", "Adaptive"),
    "Binomial_Non-Adaptive.csv": ("Binomial", "Non-Adaptive"),
    "levelSort_Adaptive.csv": ("levelSort", "Adaptive"),
    "levelSort_Non-Adaptive.csv": ("levelSort", "Non-Adaptive")
}

# Load and preprocess data
dataframes = []
for filename, (algo, mode) in file_info.items():
    df = pd.read_csv(filename, skipinitialspace=True)
    df['durationNs'] = df['durationNs'].str.replace(' ns', '', regex=False).astype(int)
    df['comparisons'] = df['comparisons'].astype(int)  # Ensure comparisons are numeric
    df['algorithm'] = algo
    df['mode'] = mode
    dataframes.append(df)

df = pd.concat(dataframes, ignore_index=True)

# Filter by n and cutoff
fixed_n = 1600
fixed_c = 20
subset = df[(df['n'] == fixed_n) & (df['cutoff'] == fixed_c)].copy()

# Add duration in ms and combined column for algo and mode
subset['duration_ms'] = subset['durationNs'] / 1_000_000.0
subset['algo_mode'] = subset['algorithm'] + "_" + subset['mode']

# Debug: Check subset for Binomial algorithms
print("Subset for Binomial algorithms:")
print(subset[subset['algorithm'] == "Binomial"])

# Group by algo_mode and presortedness, compute mean
grouped = subset.groupby(['algo_mode', 'presortedness'], as_index=False)[['duration_ms', 'comparisons']].mean()

# Debug: Check grouped data
print("Grouped Data:")
print(grouped)

# List of algorithms and modes
algo_modes = grouped['algo_mode'].unique()

# Plot
fig, (ax1, ax2) = plt.subplots(2, 1, figsize=(10, 12), sharex=True)

# Plot Average Runtime vs Presortedness
for am in algo_modes:
    data = grouped[grouped['algo_mode'] == am].sort_values('presortedness')
    if not data.empty:
        ax1.plot(data['presortedness'], data['duration_ms'], marker='o', label=am)

ax1.set_ylabel("Average Runtime (ms)")
ax1.set_title(f"Average Runtime vs Presortedness (cutoff={fixed_c})")
ax1.grid(True)
ax1.legend()

# Plot Average Comparisons vs Presortedness
for am in algo_modes:
    data = grouped[grouped['algo_mode'] == am].sort_values('presortedness')
    if not data.empty:
        ax2.plot(data['presortedness'], data['comparisons'], marker='o', label=am)

ax2.set_xlabel("Presortedness")
ax2.set_ylabel("Average Comparisons")
ax2.set_title(f"Average Comparisons vs Presortedness (cutoff={fixed_c})")
ax2.grid(True)
ax2.legend()

plt.tight_layout()
plt.show()