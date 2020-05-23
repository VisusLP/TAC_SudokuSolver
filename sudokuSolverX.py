#!/usr/bin/env python3

# Author: Ali Assaf <ali.assaf.mail@gmail.com>
# Copyright: (C) 2010 Ali Assaf
# License: GNU General Public License <http://www.gnu.org/licenses/>

from itertools import product
import time
import sys

# Al despejar los tiempos constantes, queda esta complejidad:
# 9 + 10N^2 + 14N^3 + N^3(7 + 32N) + N^2 (10 + 32N + 4N^2 + 8(1 + 4N)(N^2 - r))(N^2 - r)
# Asumiendo el peor caso, un sudoku vacío (r = 0)
# 32N^7 + 12N^6 + 32N^5 + 42N^4 + 21N^3 + 10N^2 + 9
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
    R, C = size # 1 + 1
    N = R * C # 1 + 1
    X = ([("rc", rc) for rc in product(range(N), range(N))] +
         [("rn", rn) for rn in product(range(N), range(1, N + 1))] +
         [("cn", cn) for cn in product(range(N), range(1, N + 1))] +
         [("bn", bn) for bn in product(range(N), range(1, N + 1))]) # N^2 * 4
    Y = dict() # c
    for r, c, n in product(range(N), range(N), range(1, N + 1)): # N^3 
        b = (r // R) * R + (c // C)  # 1 + 1 + 1 + 1 + 1   # Box number
        Y[(r, c, n)] = [
            ("rc", (r, c)),
            ("rn", (r, n)),
            ("cn", (c, n)),
            ("bn", (b, n))] # 1 + 4
    X, Y = exact_cover(X, Y) # 1 + O(exact_cover)
    for i, row in enumerate(grid): # N^2
        for j, n in enumerate(row): # N
            if n:
                select(X, Y, (i, j, n)) # O(select())
    for solution in solve(X, Y, []): # 1 + O(solve)
        for (r, c, n) in solution: # N^2
            grid[r][c] = n # 1
        # yield grid
        return grid # 1

def exact_cover(X, Y): # 4N^3 + 4N^2 + 1
    X = {j: set() for j in X} # N^2 * 4
    for i, row in Y.items(): # N^3
        for j in row: # 4
            X[j].add(i) # 1
    return X, Y # 1

def solve(X, Y, solution):
    # Se ejecuta N^2 - r + 1 veces, siendo r el número de casillas llenas del sudoku inicial. La última se ejecuta el if, el resto el else
    # (4N^2 + 32N + 10 + 8(1 + 4N)(N^2 - r) - l)*(N^2 - r) + 2
    # l es la disminución de longitud de X, la cual varía. Por lo que para simplificar, asumimos que siempre se da el peor caso, l = 0.
    # (4N^2 + 32N + 10 + 8(4N + 1)(N^2 - r))*(N^2 - r) + 2
    if not X: # 1
        yield list(solution) # 1
    else:
        c = min(X, key=lambda c: len(X[c])) # 1 + ((N^2 * 4) - l) * c      La lista X se va reduciendo poco a poco, por lo que su longitud es N^2*4 - l
        for r in list(X[c]): # 1
            solution.append(r) # c
            cols = select(X, Y, r) # O(Select)
            for s in solve(X, Y, solution): # 1
                yield s # 1
            deselect(X, Y, r, cols) # O(deselect)
            solution.pop() # c

def select(X, Y, r): # 16N + 16Nc + 4c + 2
    cols = [] # 1
    for j in Y[r]: # 4
        for i in X[j]: # N
            for k in Y[i]: # 4
                if k != j: # 1
                    X[k].remove(i) # c
        cols.append(X.pop(j)) # c
    return cols # 1

def deselect(X, Y, r, cols):
    # Siendo r el número de casillas llenas
    # 4(c + 1)(4N + 1)(N^2 - r)
    # Despejando la c: 8(4N+1)(N^2-r)
    print("deselecteo")
    # Se ejecuta como máximo (N^2 - r) * 4. Siendo r el número de casillas llenas en ese momento
    for j in reversed(Y[r]): # (N^2 - r) * 4
        X[j] = cols.pop() # 1 + c
        for i in X[j]: # N
            for k in Y[i]: # 4
                if k != j: # 1
                    X[k].add(i) # c

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
    exists = f.exists()
    with open(output, 'a') as f:
        if(not exists): print("file, time_ms", file=f)
        print(filename, ",",((final_time - start_time)*1000), file=f) 