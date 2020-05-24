output="results_backtracking_difficulty_computer.csv"
javac sudokuSolver.java

for a in py-sudokus/Difficulty/*
	do
	for b in $a/*
		do
		java sudokuSolver $b 3 3 $output
		done
	done