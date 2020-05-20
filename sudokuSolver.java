import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class sudokuSolver {
	int [][] sudoku;
	boolean [][] holi;
	int iter;
	Scanner reader;
	boolean solved = false;
	boolean repeat = false;
	boolean correct = true;
	
	public sudokuSolver() {
		sudoku = new int[9][9];
		holi = new boolean[9][9];
		
		iter = 0;
		reader = new Scanner(System.in);
	}
	
	private void readSudoku(String file) throws FileNotFoundException, IOException {
	      String read;
	      FileReader f = new FileReader(file);
	      int i=0;
	      int j;
	      BufferedReader b = new BufferedReader(f);
	      System.out.println("Loading sudoku from file...");
	      while((read = b.readLine())!=null) {
			  read = read.replaceAll(" ", "");
	          if(read.length()==9){
	        	  for(j=0;j<9;j++){
	        		sudoku[i][j]=(int)(read.charAt(j)-48);
	        		if(sudoku[i][j]!=0)holi[i][j] = true;
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
	      for(j=0;j<9;j++){
	    	  for(i=0;i<9;i++){
					System.out.print(sudoku[j][i]+" ");
					if(i==2 || i==5){
						System.out.print("| ");
					}
				}
				System.out.println();
				if(j==2 || j==5){
					System.out.print("------+-------+------");
					System.out.println();
				}
	    	  
	      }
	      
	}
	
	private boolean goodCheck(int row, int column){
		if (checkRow(row) && checkColumn(column) && checkRegion(row, column)) return true;
		else return false;
	}
	
	private boolean checkRow(int row){
		if (row >= 0 && row < 9){
			for (int ii = 0; ii<sudoku.length; ii++){
				for (int jj = ii+1; jj<sudoku.length; jj++){
					if (sudoku[row][ii] == sudoku[row][jj] && sudoku[row][ii] != 0) return false;
				}
			}
			return true;
		}
		return false;
	}
	
	private boolean checkColumn(int column){
		if (column >= 0 && column < 9){
			for (int ii = 0; ii<sudoku.length; ii++){
				for (int jj = ii+1; jj<sudoku.length; jj++){
					if (sudoku[ii][column] == sudoku[jj][column] && sudoku[ii][column] != 0) return false;
				}
			}
			return true;
		}
		return false;
	}
	
	private boolean checkRegion(int row, int column){
		if(row>=0 && row < 9 && column>=0 && column < 9){
			
			if (row<3 && column <3){//Region 0
				for (int ii=0; ii<3; ii++){
					for (int jj=0; jj<3; jj++){
						if (ii!=row || jj!=column){
							if (sudoku[row][column] == sudoku[ii][jj] && sudoku[row][column] != 0)return false;
						}
					}
				}
			}
			else if (row<3 && column>2 && column<6){//Region 1
				for (int ii=0; ii<3; ii++){
					for (int jj=3; jj<6; jj++){
						if (ii!=row || jj!=column){
							if (sudoku[row][column] == sudoku[ii][jj] && sudoku[row][column] != 0)return false;
						}
					}
				}
			}
			else if (row<3 && column>5){//Region 2
				for (int ii=0; ii<3; ii++){
					for (int jj=6; jj<9; jj++){
						if (ii!=row || jj!=column){
							if (sudoku[row][column] == sudoku[ii][jj] && sudoku[row][column] != 0)return false;
						}
					}
				}
			}
			else if (row>2 && row <6 && column<3){//Region 3
				for (int ii=3; ii<6; ii++){
					for (int jj=0; jj<3; jj++){
						if (ii!=row || jj!=column){
							if (sudoku[row][column] == sudoku[ii][jj] && sudoku[row][column] != 0)return false;
						}
					}
				}
			}
			else if (row>2 && row <6 && column>2 && column<6){//Region 4
				for (int ii=3; ii<6; ii++){
					for (int jj=3; jj<6; jj++){
						if (ii!=row || jj!=column){
							if (sudoku[row][column] == sudoku[ii][jj] && sudoku[row][column] != 0)return false;
						}
					}
				}
			}
			else if (row>2 && row<6 && column>5){//Region 5
				for (int ii=3; ii<6; ii++){
					for (int jj=6; jj<9; jj++){
						if (ii!=row || jj!=column){
							if (sudoku[row][column] == sudoku[ii][jj] && sudoku[row][column] != 0)return false;
						}
					}
				}
			}
			else if (row>5 && column<3){//Region 6
				for (int ii=6; ii<9; ii++){
					for (int jj=0; jj<3; jj++){
						if (ii!=row || jj!=column){
							if (sudoku[row][column] == sudoku[ii][jj] && sudoku[row][column] != 0)return false;
						}
					}
				}
			}
			else if (row>5 && column>2 && column<6){//Region 7
				for (int ii=6; ii<9; ii++){
					for (int jj=3; jj<6; jj++){
						if (ii!=row || jj!=column){
							if (sudoku[row][column] == sudoku[ii][jj] && sudoku[row][column] != 0)return false;
						}
					}
				}
			}
			else if (row>5 && column>5){//Region 8
				for (int ii=6; ii<9; ii++){
					for (int jj=6; jj<9; jj++){
						if (ii!=row || jj!=column){
							if (sudoku[row][column] == sudoku[ii][jj] && sudoku[row][column] != 0)return false;
						}
					}
				}
			}
			return true;
		}
		return false;
	}
	
	private boolean solve(int row, int column){
//		showSudoku();
		iter++;
//		System.out.println(iter);	
		for (int ii=1; ii<=9; ii++){
			if (!solved){
				if (holi[row][column] == false){
					sudoku[row][column] = ii;				
				}
				if (sudoku[row][column] == 9 && !goodCheck(row, column) && holi[row][column] == false){
					sudoku[row][column] = 0;
					repeat = true;
				}
				if (repeat == true && holi[row][column]==true){
				}
				else if (goodCheck(row, column) && sudoku[row][column]!=0){
					repeat = false;
					if (column<8){
						solve(row, column+1);
					}
					else if (row<8){
						solve(row+1, 0);
					}
				}
			}
			if (column == 8 && row == 8 && goodCheck(row, column)){
				solved = true;
//				for (int checkRow=0; checkRow<sudoku.length; checkRow++){
//					for (int checkCol=0; checkCol<sudoku.length; checkCol++){
//						if (sudoku[checkRow][checkCol] == 0) correct = false;
//						if (!goodCheck(checkRow, checkCol)) correct = false;
//					}
//				}		
			}
			if (!solved && holi[row][column]==false){
				sudoku[row][column] = 0;
			}
		}	
		return true;
	}

	private void writeFile(int iter, String file) throws FileNotFoundException, IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter("results_backtracking.csv", true));
		writer.append(file+","+iter+"\n");
		
		writer.close();
	}
	
//	boolean goodAttempt = false;
//	if (holi[row][column] == true && sudoku[row][column+1]!=0 && column<8 && row>0){
//		sudoku[row][column+1]=0;
//		if (column == 0){
//			solve(row-1, 8);
//		}
//		else solve (row, column-1);
//	}
//	else if (holi[row][column] == true && sudoku[row+1][0]!=0 && column==8 && row>0){
//		sudoku[row][column+1]=0;
//		solve (row, column-1);
//	}
//	if (holi[row][column] == false){
//		while(!goodAttempt){
//			if(sudoku[row][column]<9){				
//				sudoku[row][column] = sudoku[row][column]+1;
//			}
//			if (goodCheck(row, column)){
//				goodAttempt = true;
//			}
//			else if (!goodCheck(row, column) && column>0 && sudoku[row][column]==9){
//				sudoku[row][column]=0;
//				solve(row,column-1);
//			}
//			else if (!goodCheck(row, column) && column==0 && sudoku[row][column]==9 && row>0){
//				sudoku[row][column]=0;
//				solve(row-1, 8);
//			}
//		}
//	}
//	
//	if (column<8){
//		solve(row, column+1);
//	}
//	else if (row<8){
//		solve(row+1, 0);
//	}
//	else if (column==8 && row==8){
//		solved = true;
//	}
	
//	for (int ii = 0; ii<size; ii++){
//	if(!solved){
//		if (column == size-1 && !conflict(ii,column)){
//			solved = true;
//		}
//		if (ii>0){
//			table[ii-1][column] = false;
//		}
//		table[ii][column] = true;
//		if (conflict(ii, column)){
//			table[ii][column] = false;
//		}
//		else {
//			solve(column+1);
//		}
//	}
//}

	public static void main(String[] args) throws IOException{
		sudokuSolver sS = new sudokuSolver();
		sS.readSudoku(args[0]);
		System.out.println("Solving...");
		if(sS.solve(0,0)) System.out.println("Sudoku solved in "+sS.iter+" steps. End");
		else System.out.println("Sudoku was not solved in  "+sS.iter+" steps. End");
		sS.showSudoku();
		sS.writeFile(sS.iter, args[0]);

	}
}

