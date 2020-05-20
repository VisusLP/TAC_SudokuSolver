output="test.csv"
python3 sudokuSolverX.py "Sudokus/Difficulty/Beginner/Sudoku1" 3 3 "NULL"
for a in Sudokus/Size/2x2/*
    do
        python3 sudokuSolverX.py $a 2 2 $output
    done

for a in Sudokus/Size/2x3/*
    do
        python3 sudokuSolverX.py $a 2 3 $output
    done

for a in Sudokus/Size/2x4/*
    do
        python3 sudokuSolverX.py $a 2 4 $output
    done

for a in Sudokus/Size/3x3/*
    do
        python3 sudokuSolverX.py $a 3 3 $output
    done
