import matplotlib.pyplot as plt

threads = [1, 2, 4, 6, 8, 16]

# No parallel merging, threshold=200
prms_no_1000 = [249537.2, 231148.3, 237863.4, 308353.7, 376468.0, 397730.1]
prms_no_10000 = [2783251.8, 1787646.7, 1381086.3, 1537392.5, 1631607.7, 1753305.7]
prms_no_100000 = [35704965.1, 23201645.6, 17910894.6, 24152767.9, 26731677.3, 27416627.3]
prms_no_1000000 = [324585595.7, 246152958.2, 260921135.6, 438600449.8, 467512279.4, 471781366.7]

arrays_1000_no = 34893.2
arrays_10000_no = 366868.2
arrays_100000_no = 3369529.8
arrays_1000000_no = 40368698.4

# With parallel merging, threshold=1600
prms_yes_1000 = [89394.9, 90677.6, 92791.5, 90736.0, 91632.2, 90505.6]
prms_yes_10000 = [1156392.2, 962614.9, 943042.4, 1164158.8, 1177780.5, 1273178.2]
prms_yes_100000 = [14951665.5, 13304417.9, 13310496.3, 22250028.9, 23039429.4, 24165871.0]
prms_yes_1000000 = [198618241.6, 188841758.2, 224629302.3, 410343687.5, 412778318.8, 422560262.3]

arrays_1000_yes = 36331.8
arrays_10000_yes = 362530.1
arrays_100000_yes = 3288158.3
arrays_1000000_yes = 47781463.0

fig, axes = plt.subplots(2, 2, figsize=(12, 10))
(ax1, ax2), (ax3, ax4) = axes

# Plot for n=1000
ax1.plot(threads, prms_no_1000, marker='o', color='blue', label='No parallel merging (thr=200)')
ax1.plot(threads, prms_yes_1000, marker='s', color='red', label='With parallel merging (thr=1600)')
ax1.axhline(y=arrays_1000_no, color='green', linestyle='--', label='Arrays.parallelSort')
ax1.set_title('n=1,000')
ax1.set_xlabel('Threads')
ax1.set_ylabel('Time (ns)')
ax1.set_yscale('log')
ax1.grid(True, which="both", linestyle="--", linewidth=0.5)
ax1.legend()

# Plot for n=10,000
ax2.plot(threads, prms_no_10000, marker='o', color='blue', label='No parallel merging (thr=200)')
ax2.plot(threads, prms_yes_10000, marker='s', color='red', label='With parallel merging (thr=3200)')
ax2.axhline(y=arrays_10000_no, color='green', linestyle='--', label='Arrays.parallelSort')
ax2.set_title('n=10,000')
ax2.set_xlabel('Threads')
ax2.set_ylabel('Time (ns)')
ax2.set_yscale('log')
ax2.grid(True, which="both", linestyle="--", linewidth=0.5)
ax2.legend()

# Plot for n=100,000
ax3.plot(threads, prms_no_100000, marker='o', color='blue', label='No parallel merging (thr=200)')
ax3.plot(threads, prms_yes_100000, marker='s', color='red', label='With parallel merging (thr=20000)')
ax3.axhline(y=arrays_100000_no, color='green', linestyle='--', label='Arrays.parallelSort')
ax3.set_title('n=100,000')
ax3.set_xlabel('Threads')
ax3.set_ylabel('Time (ns)')
ax3.set_yscale('log')
ax3.grid(True, which="both", linestyle="--", linewidth=0.5)
ax3.legend()

# Plot for n=1,000,000
ax4.plot(threads, prms_no_1000000, marker='o', color='blue', label='No parallel merging (thr=200)')
ax4.plot(threads, prms_yes_1000000, marker='s', color='red', label='With parallel merging (thr=20000)')
ax4.axhline(y=arrays_1000000_no, color='green', linestyle='--', label='Arrays.parallelSort')
ax4.set_title('n=1,000,000')
ax4.set_xlabel('Threads')
ax4.set_ylabel('Time (ns)')
ax4.set_yscale('log')
ax4.grid(True, which="both", linestyle="--", linewidth=0.5)
ax4.legend()

plt.tight_layout()
plt.show()