from sudoku import Sudoku
import pathlib
import os


path = 'py-sudokus/Difficulty/'

def generateSudoku(d):
    d = d/100
    x = "3"
    y = "3"
    n = int(x)*int(y)
    size_folder = path +str(d)
    if not os.path.exists(size_folder):
        os.mkdir(size_folder)
    for i in range(1, 101):
        puzzle = Sudoku(int(x),int(y), seed=i).difficulty(d)
        puzzle.show()
        filename = 'Sudoku'+str(i)+"_"+str(d)
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

for d in range(5, 100, 5):
    generateSudoku(d)