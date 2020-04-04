import pandas as pd
import numpy as np
import sys

df = pd.read_csv(sys.argv[1])
a = np.array(df)
print(a[0].size)
print(int(a.size/a[0].size))
for col in df.columns.values:
    print(col)
for ind in df.index:
    print(ind)
for i in range(int(a.size / a[0].size)):
    for j in range(a[0].size):
        print(a[i][j])