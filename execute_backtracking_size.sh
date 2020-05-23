output="results_backtracking_size.csv"
javac sudokuSolver.java
for a in Sudokus/Size/2x2/*
    do
        java sudokuSolver $a 2 2 $output
    done

for a in Sudokus/Size/2x3/*
    do
        java sudokuSolver $a 2 3 $output
    done

for a in Sudokus/Size/2x4/*
    do
        java sudokuSolver $a 2 4 $output
    done

for a in Sudokus/Size/3x3/*
    do
        java sudokuSolver $a 3 3 $output
    done

for a in Sudokus/Size/2x5/*
    do
        java sudokuSolver $a 2 5 $output
    done

for a in Sudokus/Size/3x4/*
    do
        java sudokuSolver $a 3 4 $output
    done

for a in Sudokus/Size/3x5/*
    do
        java sudokuSolver $a 3 5 $output
    done

for a in Sudokus/Size/4x4/*
    do
        java sudokuSolver $a 4 4 $output
    done