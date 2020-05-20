output = "test.csv"
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
