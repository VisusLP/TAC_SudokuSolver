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
	int x, y, n;
	int iter;
	Scanner reader;
	boolean solved = false;
	boolean repeat = false;
	boolean correct = true;

	public sudokuSolver() {
		iter = 0;
		reader = new Scanner(System.in);
	}

	private void readSudoku(String file) throws FileNotFoundException, IOException {
		String read;
		FileReader f = new FileReader(file);
		int i = 0;
		int j;
		BufferedReader b = new BufferedReader(f);
		System.out.println("Loading sudoku from file...");
		while ((read = b.readLine()) != null) {
			read = read.replaceAll(" ", "");
			if (read.length() == n) {
				for (j = 0; j < n; j++) {
					sudoku[i][j] = (int) (read.charAt(j) - 48);
					if (sudoku[i][j] != 0)
						final_value[i][j] = true;
				}
			}
			i++;
		}
		b.close();
		System.out.println("sudoku loaded!!!");
	}

	private void showSudoku() {
		int i;
		int j;
		System.out.println("showing sudoku...");
		for (i = 0; i < n; i++) {
			for (j = 0; j < n; j++) {
				System.out.print(sudoku[i][j] + " ");
				if ((j+1)%y == 0 && j != (x*y)-1 && j != 0) {
					System.out.print("| ");
				}
			}
			System.out.println();
			if ((i+1)%x == 0 && i != (x*y)-1 && i != 0) {
				for(int h = 0; h < x; h++){
					for(int w = 0; w < (y*2); w++){
						System.out.print("-");
					}
					if( h > 0 && h < x-1){
						System.out.print("-");
					}
					if( h != x-1){
						System.out.print("+");
					}
				}
				System.out.println();
			}

		}
		System.out.println();

	}

	private boolean goodCheck(int row, int column) {
		if (checkRow(row, column) && checkColumn(row, column) && checkRegion(row, column))
			return true;
		else
			return false;
	}

	private boolean checkRow(int row, int column) {
		for (int ii = 0; ii < n; ii++) {
			if (sudoku[row][column] == sudoku[row][ii] && column != ii)
				return false;
		}
		return true;
	}

	private boolean checkColumn(int row, int column) {
		for (int ii = 0; ii < n; ii++) {
			if (sudoku[row][column] == sudoku[ii][column] && row != ii)
				return false;
		}
		return true;
	}

	private boolean checkRegion(int row, int column) {
		int row_region = row / x;
		int column_region = column / y;
		for (int i = row_region*x; i < (row_region*x)+x; i++) {
			for (int j = column_region*y; j < (column_region*y)+y; j++) {
				if (sudoku[row][column] == sudoku[i][j] && (row != i && column != j))
					return false;
			}
		}
		return true;
	}

	private boolean solve(int row, int column) {
		showSudoku();
		if (final_value[row][column] == false) {
			for (int i = 1; i <= n; i++) {
				iter++;
				// System.out.println(iter);
				if (!solved) {
					sudoku[row][column] = i;
					if (goodCheck(row, column)) {
						if (column < n - 1) {
							solve(row, column + 1);
						} else if (row < n - 1) {
							solve(row + 1, 0);
						}
					}
				}
				if (column == n - 1 && row == n - 1 && goodCheck(row, column)) {
					solved = true;
					showSudoku();
					return true;
				}
			}
			if (!solved && final_value[row][column] == false) {
				sudoku[row][column] = 0;
			}
		} else {
			if (column < n - 1) {
				solve(row, column + 1);
			} else if (row < n - 1) {
				solve(row + 1, 0);
			}
		}
		if(solved){
			return true;
		}
		return false;
	}

	private void writeFile(int iter, String file) throws FileNotFoundException, IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter("results_backtracking.csv", true));
		writer.append(file + "," + iter + "\n");

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
		if (sS.solve(0, 0)){
			System.out.println("Sudoku solved in " + sS.iter + " steps. End");
			sS.showSudoku();
		}
		else
			System.out.println("Sudoku was NOT solved in  " + sS.iter + " steps. End");
		sS.writeFile(sS.iter, args[0]);

	}
}
