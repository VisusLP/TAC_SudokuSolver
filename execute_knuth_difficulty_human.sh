output="results_knuth_difficulty_human.csv"
python3 sudokuSolverX.py "Sudokus/Difficulty/Beginner/Sudoku1" 3 3 "NULL"

for a in Sudokus/Difficulty/*
	do
	for b in $a/*
		do
		python3 sudokuSolverX.py $b 3 3 $output
		done
	done
		