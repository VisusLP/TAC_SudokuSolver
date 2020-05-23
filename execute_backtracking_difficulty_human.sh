output="results_backtracking_difficulty_human.csv"
javac sudokuSolver.java
for a in Sudokus/Difficulty/Beginner/*
    do
        java sudokuSolver $a 3 3 $output
    done
    
for a in Sudokus/Difficulty/Easy/*
    do
        java sudokuSolver $a 3 3 $output
    done

for a in Sudokus/Difficulty/Medium/*
    do
        java sudokuSolver $a 3 3 $output
    done

for a in Sudokus/Difficulty/Tricky/*
    do
        java sudokuSolver $a 3 3 $output
    done

for a in Sudokus/Difficulty/Fiendish/*
    do
        java sudokuSolver $a 3 3 $output
    done

for a in Sudokus/Difficulty/Diabolical/*
    do
        java sudokuSolver $a 3 3 $output
    done