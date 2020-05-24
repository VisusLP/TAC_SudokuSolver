from sudoku import Sudoku
import pathlib
import os
import signal
import sys

path = 'py-sudokus/Size/'

def generateSudoku(x, y, z):
    
    n = int(x)*int(y)
    size_folder = path +x+ 'x'+ y
    if not os.path.exists(size_folder):
        os.mkdir(size_folder)
    puzzle = Sudoku(int(x), int(y), seed=int(z)).difficulty(0.66)
    puzzle.show()
    filename = 'Sudoku'+z+"_"+x+"x"+y
    final_path = os.path.join(size_folder, filename)
    with open(final_path, 'w') as f:
        for row in range(n):
            for col in range(n):
                value = puzzle.board[row][col]
                if value == None: value = 0
                print(value,file=f, end="")
                if col == n - 1:
                    print(file=f, end="\n")
                else:
                    print(file=f, end=" ")

generateSudoku(sys.argv[1], sys.argv[2], sys.argv[3])