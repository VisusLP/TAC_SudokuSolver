from sudoku import Sudoku
import pathlib
import os


path = 'py-sudokus/Size/'

def generateSudoku(x, y, z):
    n = int(x)*int(y)
    size_folder = path +x+ 'x'+ y
    if not os.path.exists(size_folder):
        os.mkdir(size_folder)
    for i in range(1, z):
        if(not(x==3 and y == 5 and i == 52)):
            puzzle = Sudoku(int(x),int(y), seed=i).difficulty(0.66)
            puzzle.show()
            filename = 'Sudoku'+str(i)+"_"+x+"x"+y
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

generateSudoku('2', '2', 101)
generateSudoku('2', '3', 101)
generateSudoku('2', '4', 101)
generateSudoku('3', '3', 101)
generateSudoku('2', '5', 101)
generateSudoku('3', '4', 101)
generateSudoku('3', '5', 51)
generateSudoku('4', '4', 51)