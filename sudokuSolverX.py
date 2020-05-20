#!/usr/bin/env python3

# Author: Ali Assaf <ali.assaf.mail@gmail.com>
# Copyright: (C) 2010 Ali Assaf
# License: GNU General Public License <http://www.gnu.org/licenses/>

from itertools import product
import time
import sys

def solve_sudoku(size, grid):
    """ An efficient Sudoku solver using Algorithm X.

    >>> grid = [
    ...     [0,0,0,0,0,0,0,0,0],
    ...     [0,0,0,0,0,3,0,8,5],
    ...     [0,0,1,0,2,0,0,0,0],
    ...     [0,0,0,5,0,7,0,0,0],
    ...     [0,0,4,0,0,0,1,0,0],
    ...     [0,9,0,0,0,0,0,0,0],
    ...     [5,0,0,0,0,0,0,7,3],
    ...     [0,0,2,0,1,0,0,0,0],
    ...     [0,0,0,0,4,0,0,0,9]]
    >>> for solution in solve_sudoku((3, 3), grid):
    ...     print(*solution, sep='\\n')
    [5, 3, 4, 6, 7, 8, 9, 1, 2]
    [6, 7, 2, 1, 9, 5, 3, 4, 8]
    [1, 9, 8, 3, 4, 2, 5, 6, 7]
    [8, 5, 9, 7, 6, 1, 4, 2, 3]
    [4, 2, 6, 8, 5, 3, 7, 9, 1]
    [7, 1, 3, 9, 2, 4, 8, 5, 6]
    [9, 6, 1, 5, 3, 7, 2, 8, 4]
    [2, 8, 7, 4, 1, 9, 6, 3, 5]
    [3, 4, 5, 2, 8, 6, 1, 7, 9]
    """
    R, C = size
    N = R * C
    X = ([("rc", rc) for rc in product(range(N), range(N))] +
         [("rn", rn) for rn in product(range(N), range(1, N + 1))] +
         [("cn", cn) for cn in product(range(N), range(1, N + 1))] +
         [("bn", bn) for bn in product(range(N), range(1, N + 1))])
    Y = dict()
    for r, c, n in product(range(N), range(N), range(1, N + 1)):
        b = (r // R) * R + (c // C) # Box number
        Y[(r, c, n)] = [
            ("rc", (r, c)),
            ("rn", (r, n)),
            ("cn", (c, n)),
            ("bn", (b, n))]
    X, Y = exact_cover(X, Y)
    for i, row in enumerate(grid):
        for j, n in enumerate(row):
            if n:
                select(X, Y, (i, j, n))
    for solution in solve(X, Y, []):
        for (r, c, n) in solution:
            grid[r][c] = n
        # yield grid
        return grid

def exact_cover(X, Y):
    X = {j: set() for j in X}
    for i, row in Y.items():
        for j in row:
            X[j].add(i)
    return X, Y

def solve(X, Y, solution):
    if not X:
        yield list(solution)
    else:
        c = min(X, key=lambda c: len(X[c]))
        for r in list(X[c]):
            solution.append(r)
            cols = select(X, Y, r)
            for s in solve(X, Y, solution):
                yield s
            deselect(X, Y, r, cols)
            solution.pop()

def select(X, Y, r):
    cols = []
    for j in Y[r]:
        for i in X[j]:
            for k in Y[i]:
                if k != j:
                    X[k].remove(i)
        cols.append(X.pop(j))
    return cols

def deselect(X, Y, r, cols):
    for j in reversed(Y[r]):
        X[j] = cols.pop()
        for i in X[j]:
            for k in Y[i]:
                if k != j:
                    X[k].add(i)

def showSudoku(grid, x, y):
    for i in range(0,x*y):
        for j in range(0,x*y):
            print(grid[i][j], end = ' ')
            if((j+1)%y == 0 and j != (x*y)-1 and j != 0):
                print("|", end=" ")
        print("")
        if((i+1)%x == 0 and i != (x*y)-1 and i != 0):
            for h in range(x):
                for _ in range(y*2):
                    print("-",end="")
                if h > 0 and h < x-1:
                    print("-",end="")
                if h != x-1:
                    print("+",end="")
            print("")
    print("")


""" if __name__ == "__main__":
    import doctest
    doctest.testmod() """

""" grid = [
     [0,0,0,0,0,0,0,0,0],
     [0,0,0,0,0,3,0,8,5],
     [0,0,1,0,2,0,0,0,0],
     [0,0,0,5,0,7,0,0,0],
     [0,0,4,0,0,0,1,0,0],
     [0,9,0,0,0,0,0,0,0],
     [5,0,0,0,0,0,0,7,3],
     [0,0,2,0,1,0,0,0,0],
     [0,0,0,0,4,0,0,0,9]]

grid3 = [
     [4,1,0,0,0,0,0,5,2],
     [5,0,8,0,0,0,4,0,1],
     [0,0,7,0,0,0,0,3,6],
     [0,2,0,0,9,3,1,0,0],
     [1,0,9,8,0,7,2,0,5],
     [0,0,6,4,2,0,0,9,0],
     [6,8,0,0,0,0,3,0,0],
     [9,0,5,0,0,0,6,0,7],
     [3,7,0,0,0,0,0,2,9]]

grid4 = [
     [0,0,0,3,0,8,0,0,2],
     [2,0,9,0,0,0,0,6,0],
     [0,4,1,2,0,0,0,0,0],
     [0,0,8,0,0,3,0,0,5],
     [0,3,0,0,8,0,0,7,0],
     [4,0,0,5,0,0,3,0,0],
     [0,0,0,0,0,9,1,3,0],
     [0,7,0,0,0,0,8,0,6],
     [9,0,0,8,0,7,0,0,0]] """

filename = sys.argv[1]
x = int(sys.argv[2])
y = int(sys.argv[3])
output = sys.argv[4]

file = open(filename, 'r') 
Lines = file.readlines()
grid = []
for line in Lines:
    line = line.strip("\n")
    row = line.split(" ")
    for i in range(0,x*y):
        row[i] = int(row[i])
    grid.append(row)
# print(grid)
start_time = time.time()
solution = solve_sudoku((x, y), grid)
final_time = time.time()

print("%s\n--- %s milliseconds ---\n" % (filename, (final_time - start_time)*1000))
showSudoku(solution, x, y)

    
if(output != "NULL"):
    with open(output, 'a') as f:
        print(filename, ",",((final_time - start_time)*1000), file=f) 