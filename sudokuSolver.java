import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class sudokuSolver {
	int[][] sudoku;
	boolean[][] final_value;
	int numbers_given = 0;
	int x, y, n;
	double iter;
	Scanner reader;
	boolean solved = false;
	boolean repeat = false;
	boolean correct = true;
	static double start_time;
	static double final_time;

	public sudokuSolver() {
		iter = 0;
		reader = new Scanner(System.in);
	}

	private void readSudoku(String file) throws FileNotFoundException, IOException {
		String read;
		String[] buffer;
		FileReader f = new FileReader(file);
		int i = 0;
		int j;
		BufferedReader b = new BufferedReader(f);
		System.out.println("Loading sudoku from file " + file + "...");
		while ((read = b.readLine()) != null) {
			buffer = read.split(" ");
			for (j = 0; j < n; j++) {
				sudoku[i][j] = (Integer.parseInt(buffer[j]));
				if (sudoku[i][j] != 0) {
					final_value[i][j] = true;
					numbers_given++;
				}
			}
			i++;
		}
		b.close();
		// System.out.println("sudoku loaded!!!");
	}

	private void showSudoku() {
		int i;
		int j;
		// System.out.println("showing sudoku...");
		for (i = 0; i < n; i++) {
			for (j = 0; j < n; j++) {
				System.out.print(sudoku[i][j] + " ");
				if ((j + 1) % y == 0 && j != (x * y) - 1 && j != 0) {
					System.out.print("| ");
				}
			}
			System.out.println();
			if ((i + 1) % x == 0 && i != (x * y) - 1 && i != 0) {
				for (int h = 0; h < x; h++) {
					for (int w = 0; w < (y * 2); w++) {
						System.out.print("-");
					}
					if (h > 0 && h < x - 1) {
						System.out.print("-");
					}
					if (h != x - 1) {
						System.out.print("+");
					}
				}
				System.out.println();
			}

		}
		System.out.println();

	}

	private boolean goodCheck(int row, int column) { // 7n + 7 + 1
		if (checkRow(row, column) && checkColumn(row, column) && checkRegion(row, column)) // O(checkRow()) +
																							// O(checkColumn()) +
																							// O(checkRegion())
			return true; // 1
		else
			return false; // 1
	}

	private boolean checkRow(int row, int column) { // 2n + 1
		for (int i = 0; i < n; i++) { // n
			if (sudoku[row][column] == sudoku[row][i] && column != i) // 1 + 1
				return false; // 1
		}
		return true; // 1
	}

	private boolean checkColumn(int row, int column) { // 2n + 1
		for (int i = 0; i < n; i++) { // n
			if (sudoku[row][column] == sudoku[i][column] && row != i) // 1 + 1
				return false; // 1
		}
		return true; // 1
	}

	private boolean checkRegion(int row, int column) { // 3n + 5
		int row_region = row / x; // 1 + 1
		int column_region = column / y; // 1 + 1
		for (int i = row_region * x; i < (row_region * x) + x; i++) { // x
			for (int j = column_region * y; j < (column_region * y) + y; j++) { // y
				if (sudoku[row][column] == sudoku[i][j] && (row != i && column != j)) // 1 + (1 + 1)
					return false; // 1
			}
		}
		return true; // 1
	}

	private boolean solve(int pos) { // 14n^2 + 32n + 4 + n*O(solve)
		// Last cell: 14n^2 + 30n + 1
		// showSudoku();
		int row = pos / n;
		int column = pos % n;
		if (final_value[row][column] == false) { // 1
			for (int i = 1; i <= n; i++) { // n
				iter++; // 1
				// System.out.println(iter);
				if (!solved) { // 1
					sudoku[row][column] = i; // 1
					if (goodCheck(row, column)) { // 1 + O(goodCheck)
						// Se ejecuta (n - max(row, column)) veces como máximo
						// Worst case: 6 + O(solve)
						if (pos == (n * n) - 1) { // 2 + 2
							solve(pos + 1);
						}
					}
				}
				if (pos == (n * n) - 1 && goodCheck(row, column)) { // 2 + 2 + O(goodCheck)
					solved = true; // 1
					// showSudoku();
					return true; // 1
				}
			}
			if (!solved && final_value[row][column] == false) { // 1 + 1
				sudoku[row][column] = 0; // 1
			}
		} else {
			if (pos == (n * n) - 1) { // 1 + 1
				solved = true; // 1
				// showSudoku();
				return true; // 1
			}
			solve(pos + 1);
		}
		if (solved) { // 1
			return true; // 1
		}
		return false; // 1
	}

	private void writeFile(double iter, String file) throws FileNotFoundException, IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter("results_backtracking_size.csv", true));
		writer.append(file + "," + numbers_given + "," + iter + "," + (final_time / 1000000) + "\n");

		writer.close();
	}

	public static void main(String[] args) throws IOException {
		sudokuSolver sS = new sudokuSolver();
		sS.x = Integer.parseInt(args[1]);
		sS.y = Integer.parseInt(args[2]);
		sS.n = sS.x * sS.y;
		sS.sudoku = new int[sS.n][sS.n];
		sS.final_value = new boolean[sS.n][sS.n];
		sS.readSudoku(args[0]);
		System.out.println("Solving...");
		start_time = System.nanoTime();
		if (sS.solve(0)) {
			final_time = System.nanoTime() - start_time;
			System.out.println("Sudoku solved in " + sS.iter + " steps. End");
			sS.showSudoku();
		} else
			System.out.println("Sudoku was NOT solved in  " + sS.iter + " steps. End");
		sS.writeFile(sS.iter, args[0]);

	}
}
