output="demo_results_knuth_size.csv"
python3 sudokuSolverX.py "Sudokus/Difficulty/Beginner/Sudoku1" 3 3 "NULL"

timeout 120s python3 sudokuSolverX.py py-sudokus/Size/2x2/Sudoku1_2x2 2 2 $output

timeout 120s python3 sudokuSolverX.py py-sudokus/Size/2x3/Sudoku1_2x3 2 3 $output

timeout 120s python3 sudokuSolverX.py py-sudokus/Size/2x4/Sudoku1_2x4 2 4 $output

timeout 120s python3 sudokuSolverX.py py-sudokus/Size/3x3/Sudoku1_3x3 3 3 $output

timeout 120s python3 sudokuSolverX.py py-sudokus/Size/2x5/Sudoku1_2x5 2 5 $output

timeout 120s python3 sudokuSolverX.py py-sudokus/Size/3x4/Sudoku1_3x4 3 4 $output

timeout 120s python3 sudokuSolverX.py py-sudokus/Size/3x5/Sudoku1_3x5 3 5 $output

timeout 120s python3 sudokuSolverX.py py-sudokus/Size/4x4/Sudoku1_4x4 4 4 $output

timeout 120s python3 sudokuSolverX.py py-sudokus/Size/4x5/Sudoku37_4x5 4 5 $output

timeout 120s python3 sudokuSolverX.py py-sudokus/Size/5x5/Sudoku45_5x5 5 5 $output