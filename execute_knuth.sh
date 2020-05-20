output="results_Knuth.csv"
python3 sudokuSolverX.py "Sudokus/Difficulty/Beginner/Sudoku1" 3 3 "NULL"
for a in Sudokus/Difficulty/Beginner/*
    do
        python3 sudokuSolverX.py $a 3 3 $output
    done
    
for a in Sudokus/Difficulty/Easy/*
    do
        python3 sudokuSolverX.py $a 3 3 $output
    done

for a in Sudokus/Difficulty/Medium/*
    do
        python3 sudokuSolverX.py $a 3 3 $output
    done

for a in Sudokus/Difficulty/Tricky/*
    do
        python3 sudokuSolverX.py $a 3 3 $output
    done

for a in Sudokus/Difficulty/Fiendish/*
    do
        python3 sudokuSolverX.py $a 3 3 $output
    done

for a in Sudokus/Difficulty/Diabolical/*
    do
        python3 sudokuSolverX.py $a 3 3 $output
    done