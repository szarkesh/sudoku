import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Font;
import java.util.*;

public class Board extends JPanel{
	
	private int[][] sol;
	private int[][] user = new int[9][9];
	private int[][] uncovered = new int[9][9];
	
	
	public Board() {
		Random r = new Random();
		boolean foundBoard = false;
		int[][] solution = new int[9][9];
		while(foundBoard == false) {
			foundBoard=true;
			solution = new int[9][9];
			outerloop:
			for (int i=0; i<9; i++) {
				for(int j=0; j<9; j++) {
					solution[i][j] = r.nextInt(9)+1;
					int ct=0;
					while(!validBoard(solution) && ct<50) {
						solution[i][j] = r.nextInt(9)+1;
						ct++;
					}
					if(ct==50) {
						foundBoard=false;
						break outerloop;
					}
				}
			}
			
		}
		sol=solution;
		System.out.println("foundBoard " + foundBoard);
		System.out.println("IS IT VALID?" + validBoard(solution));
//		while(!isSolvable(this.getUncoveredIs0())) {
//			int randX = r.nextInt(9);
//			int randJ = r.nextInt(9);
//			uncovered[randX][randJ]=1;
//		}
		for (int i=0; i<9; i++) {
			for (int j=0; j<9; j++) {
				if(r.nextInt(100)>40) {
					uncovered[i][j]=1;
				}
			}
		}
		
		for (int i=0; i<9; i++) {
			for(int j=0; j<9; j++) {
				System.out.print(solution[i][j]);
				
			}
			System.out.println();
		}
	}
	
	public static boolean isSolvable(int[][] board) {
		boolean isFullyFilled=true;
		for (int i=0; i<9; i++) {
			for (int j=0; j<9; j++) {
				if(board[i][j]==0) {
					isFullyFilled=false;
				}
			}
		}
		if(isFullyFilled) {return true;}
		for (int i=0; i<9; i++) {
			for (int j=0; j<9; j++) {
				if(board[i][j]==0) {
					int rightNum = 0;
					int correctPossibilities =0;
					for (int k=0; k<9; k++) {
						rightNum = k+1;
						int[][] theoreticalBoard = new int[9][9];
						for(int a=0; a<board.length; a++)
							  for(int b=0; b<board[i].length; b++)
							    theoreticalBoard[a][b]=board[a][b];
						theoreticalBoard[i][j]=k+1;
						//printBoard(theoreticalBoard);
						if(validBoard(theoreticalBoard)) {
							correctPossibilities+=1;
							rightNum=k+1;
						}
					}
					if(correctPossibilities==0) {
						System.out.println("NANI THE FUCK");
					}
					if(correctPossibilities==1) {
						int[][] oneStepCloser = new int[9][9];
						for(int a=0; a<board.length; a++)
							  for(int b=0; b<board[i].length; b++)
							    oneStepCloser[a][b]=board[a][b];
						oneStepCloser[i][j] = rightNum;
						return isSolvable(oneStepCloser);
					}
				}
			}
		}
		return false;
	}
	
	public static void printBoard(int[][] arr) {
		for (int i=0; i<9; i++) {
			for(int j=0; j<9; j++) {
				System.out.print(arr[i][j]);
			}
			System.out.println();
		}
	}
	public static boolean validBoard(int[][] arr) {
		for (int i=0; i<9; i++) {
			Set<Integer> setContained = new TreeSet<Integer>();
			for (int j=0; j<9; j++) {
				if(arr[i][j]!=0) {
					if(setContained.contains(arr[i][j])) {
						return false;
					}
					setContained.add(arr[i][j]);
				}
			}
		}
		
		
		
		for (int j=0; j<9; j++) {
			Set<Integer> setContained = new TreeSet<Integer>();
			for (int i=0; i<9; i++) {
				if(arr[i][j]!=0) {
					if(setContained.contains(arr[i][j])) {
						return false;
					}
					setContained.add(arr[i][j]);
				}
			}
		}

		
		for (int k=0; k<3; k++) {
			for (int l=0; l<3; l++) {
				Set<Integer> setContained = new TreeSet<Integer>();
				for (int i=0; i<3; i++) {
					for (int j=0; j<3; j++) {
						int x = 3*k+i;
						int y = 3*l+j;
						if(arr[x][y]!=0) {
							//System.out.println(setContained.toString());
							if(setContained.contains(arr[x][y])) {
								return false;
							}
							setContained.add(arr[x][y]);
						}
					}
					
				}
				
			}
		}
		
		//System.out.println("GOT TO C");
		return true;
		
		
	}
	public int check(int i, int j) {
		if (user[i][j]!=0 && sol[i][j]!=user[i][j]) {
			return -1;
		}
		else if(uncovered[i][j]==0 && user[i][j]==0) {
			return 0;
		}
		return 1;
	}
	public void draw(Graphics g){
		JPanel panel = new JPanel();
		JLabel label = new JLabel("hello there");
		
	}
	
	public String stringRep(int[] arr) {
		String str = "";
		for (int i=0; i<arr.length; i++) {
			str = str + arr[i] + "  ";
		}
		return str;
	}
	
	public void resetUserInput() {
		user = new int[9][9];
	}
	
	public String[][] getCurrentBoard() {
		String[][] currState = new String[9][9];
		for (int i=0; i<9; i++) {
			for (int j=0; j<9; j++) {
				if (uncovered[i][j]==1) {
					currState[i][j]= String.valueOf(sol[i][j]);
				}
				else if (user[i][j]!=0) {
					currState[i][j]= String.valueOf(user[i][j]);
				}
				else {
					currState[i][j]= " ";
				}
			}
		}
		return currState;
	}
	
	public int[][] getUncoveredIs0() {
		int[][] currState = new int[9][9];
		for (int i=0; i<9; i++) {
			for (int j=0; j<9; j++) {
				if (uncovered[i][j]==1) {
					currState[i][j]= sol[i][j];
				}
				else {
					currState[i][j] = 0;
				}
			}
		}
		return currState;
	}
	
	public int[][] getUncovered(){
		return uncovered;
	}
	
	public int[][] getSolution(){
		return sol;
	}
	
	public void enter(int i, int j, int in) {
		if(in>0 && in<10) {
			user[i][j]=in;
		}
		System.out.print("UPDATED");
	}
	
	public void delete(int i, int j) {
		if(uncovered[i][j]==1) {
			return;
		}
		else {
			user[i][j]=0;
		}
	}
	
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
//        JPanel panel = new JPanel();
//		JLabel label = new JLabel("hello there");
//		panel.add(label);
//		
//		//g.drawString("hello there", 0, 0);
//		g.setFont(new Font("TimesRoman", Font.PLAIN, 20)); 
//		for (int i=0; i<9; i++) {
//			g.drawString(stringRep(sol[i]), 20, 20+20*i);
//		}
//		g.drawLine(75, 0, 75, 180);
//		g.drawLine(134, 0, 134, 180);
//		g.drawLine(10, 63, 190, 63);
//		g.drawLine(10, 123, 190, 123);
    }
}
