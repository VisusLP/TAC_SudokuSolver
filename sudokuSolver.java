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
		for (j = 0; j < 9; j++) {
			for (i = 0; i < 9; i++) {
				System.out.print(sudoku[j][i] + " ");
				if (i == 2 || i == 5) {
					System.out.print("| ");
				}
			}
			System.out.println();
			if (j == 2 || j == 5) {
				System.out.print("------+-------+------");
				System.out.println();
			}

		}

	}

	private boolean goodCheck(int row, int column) {
		if (checkRow(row, column) && checkColumn(row, column) && checkRegion(row, column))
			return true;
		else
			return false;
	}

	private boolean checkRow(int row, int column) {
		if (row >= 0 && row < n) {
			for (int ii = 0; ii < sudoku.length; ii++) {
				if (sudoku[row][column] == sudoku[row][ii])
					return false;
			}
			return true;
		}
		return false;
	}

	private boolean checkColumn(int row, int column) {
		if (column >= 0 && column < n) {
			for (int ii = 0; ii < sudoku.length; ii++) {
				if (sudoku[row][column] == sudoku[ii][column])
					return false;
			}
			return true;
		}
		return false;
	}

	private boolean checkRegion(int row, int column) {
		int row_region = row / x;
		int column_region = column / y;
		for (int i = row_region; i < row_region + x; i++) {
			for (int j = column_region; j < column_region + y; j++) {
				if (sudoku[row][column] == sudoku[i][j] && sudoku[row][column] != 0)
					return false;
			}
		}
		return true;
	}

	private boolean solve(int row, int column) {
		// showSudoku();
		iter++;
		// System.out.println(iter);
		if (final_value[row][column] == false) {
			for (int i = 1; i <= n; i++) {
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
		return true;
	}

	private void writeFile(int iter, String file) throws FileNotFoundException, IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter("results_backtracking.csv", true));
		writer.append(file + "," + iter + "\n");

		writer.close();
	}

	// boolean goodAttempt = false;
	// if (final_value[row][column] == true && sudoku[row][column+1]!=0 && column<8
	// &&
	// row>0){
	// sudoku[row][column+1]=0;
	// if (column == 0){
	// solve(row-1, 8);
	// }
	// else solve (row, column-1);
	// }
	// else if (final_value[row][column] == true && sudoku[row+1][0]!=0 && column==8
	// &&
	// row>0){
	// sudoku[row][column+1]=0;
	// solve (row, column-1);
	// }
	// if (final_value[row][column] == false){
	// while(!goodAttempt){
	// if(sudoku[row][column]<9){
	// sudoku[row][column] = sudoku[row][column]+1;
	// }
	// if (goodCheck(row, column)){
	// goodAttempt = true;
	// }
	// else if (!goodCheck(row, column) && column>0 && sudoku[row][column]==9){
	// sudoku[row][column]=0;
	// solve(row,column-1);
	// }
	// else if (!goodCheck(row, column) && column==0 && sudoku[row][column]==9 &&
	// row>0){
	// sudoku[row][column]=0;
	// solve(row-1, 8);
	// }
	// }
	// }
	//
	// if (column<8){
	// solve(row, column+1);
	// }
	// else if (row<8){
	// solve(row+1, 0);
	// }
	// else if (column==8 && row==8){
	// solved = true;
	// }

	// for (int ii = 0; ii<size; ii++){
	// if(!solved){
	// if (column == size-1 && !conflict(ii,column)){
	// solved = true;
	// }
	// if (ii>0){
	// table[ii-1][column] = false;
	// }
	// table[ii][column] = true;
	// if (conflict(ii, column)){
	// table[ii][column] = false;
	// }
	// else {
	// solve(column+1);
	// }
	// }
	// }

	public static void main(String[] args) throws IOException {
		sudokuSolver sS = new sudokuSolver();
		sS.x = Integer.parseInt(args[1]);
		sS.y = Integer.parseInt(args[2]);
		sS.n = sS.x * sS.y;
		sS.sudoku = new int[sS.n][sS.n];
		sS.final_value = new boolean[sS.n][sS.n];
		sS.readSudoku(args[0]);
		System.out.println("Solving...");
		if (sS.solve(0, 0))
			System.out.println("Sudoku solved in " + sS.iter + " steps. End");
		else
			System.out.println("Sudoku was not solved in  " + sS.iter + " steps. End");
		sS.showSudoku();
		sS.writeFile(sS.iter, args[0]);

	}
}
