output="results_knuth_difficulty_computer.csv"
python3 sudokuSolverX.py "Sudokus/Difficulty/Beginner/Sudoku1" 3 3 "NULL"

for a in py-sudokus/Difficulty/*
	do
	for b in $a/*
		do
		python3 sudokuSolverX.py $b 3 3 $output
		done
	done
		