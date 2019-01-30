/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

// imports necessary libraries for Java swing
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.BorderFactory;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class Game extends JFrame implements Runnable{
	boolean firstOpen = true;
	int lastSelectedX = 0;
	int lastSelectedY = 0;

	
	public void updateButtons(Board court, JButton[][] buttons) {
		for (int i=0; i<9; i++) {
			for(int j=0; j<9; j++) {
				String s = court.getCurrentBoard()[i][j];
				if(s.equals(" ")) {
					buttons[i][j].setBackground(null);
					buttons[i][j].setBorder(BorderFactory.createBevelBorder(1));
					buttons[i][j].setText(court.getCurrentBoard()[i][j]);
				}
				else {
					//buttons[i][j].setBackground(Color.BLACK);
					buttons[i][j].setText(court.getCurrentBoard()[i][j]);
				}
			}
		}
	} 
	
	public void showHighScores(){
		try {
			FileReader fr = new FileReader("files/highscore");
			BufferedReader br = new BufferedReader(fr);
			String a = br.readLine();
			JFrame hs = new JFrame("High scores!");
			hs.setLayout(new GridLayout(4,2));
			String[] highscores = new String[3];
			hs.setSize(300, 300);
			int ct=0;
			System.out.println(a);
			while(ct<3) {
				if(a==null) {
					highscores[ct] = "No one yet!";
				}
				else {
					highscores[ct] = "On " +  a.split(";")[1] + ", " + a.split(";")[0] + " won in " + a.split(";")[2] + " seconds!";
				}
				ct+=1;
				a = br.readLine();
			}
			JLabel firstPlace = new JLabel(highscores[0]);
			JLabel secondPlace = new JLabel(highscores[1]);
			JLabel thirdPlace = new JLabel(highscores[2]);
			firstPlace.setBorder(new EmptyBorder(10,10,10,10));
			secondPlace.setBorder(new EmptyBorder(10,10,10,10));
			thirdPlace.setBorder(new EmptyBorder(10,10,10,10));
			hs.add(firstPlace);
			hs.add(secondPlace);
			hs.add(thirdPlace);
			hs.setVisible(true);
		}
		catch (IOException e) {
			System.out.println("NO SUCH FILE");
		}
		
	}
	public void check(JButton[][] btns, Board board, JLabel status, JLabel time, Timer tmr) {
		for (int i=0; i<9; i++) {
			for(int j=0; j<9; j++) {
				if(board.check(i, j)==-1) {
					btns[i][j].setBorder(BorderFactory.createLineBorder(Color.RED));
				}
				else if (board.check(i, j)==1){
					btns[i][j].setBorder(BorderFactory.createLineBorder(Color.GREEN));
				}
			}
		}
		//board.resetUserInput();
		for (int i=0; i<9; i++) {
			for (int j=0; j<9; j++) {
				//System.out.println(board.check(i, j));
				if (board.check(i, j)!=1) {
					incrementTime(time, 10);
					return;
				}
			}
		}
		status.setText("WINNER");
		tmr.stop();
		JFrame enterName = new JFrame("Enter your name for the scores list");
		JTextField textField = new JTextField(20);
		textField.addKeyListener(new java.awt.event.KeyAdapter() {
	        public void keyTyped(java.awt.event.KeyEvent evt) {

	         if(!(Character.isLetter(evt.getKeyChar()))){
	                evt.consume();
	            }
	        }
	    });
		JButton enter = new JButton("Submit");
		enter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	int currentTime = 60 * Integer.parseInt(time.getText().split(":")[0]) + Integer.parseInt(time.getText().split(":")[1]);
            	enterName.dispatchEvent(new WindowEvent(enterName, WindowEvent.WINDOW_CLOSING));
            	System.out.println(textField.getText());
            	enterHighScore(textField.getText(), currentTime);
            }
        });
		enterName.add(enter, BorderLayout.EAST);
		enterName.add(textField);
		enterName.setSize(300, 100);
		enterName.setVisible(true);
		
		System.out.println("WINNER");
	}
	
	public void enterHighScore(String enteredName, int currentTime) {
		try {
			FileReader fr = new FileReader("files/highscore");
			BufferedReader reader = new BufferedReader(fr);
			String a = reader.readLine();
			ArrayList<String> names = new ArrayList<String>();
			ArrayList<String> dates = new ArrayList<String>();
			ArrayList<Integer> times = new ArrayList<Integer>();
			while(a!=null) {
				if(a.split(";").length<3) {
					throw new IOException("Incorrectly formatted highscore file -- clear it");
				}
				String name = a.split(";")[0];
				String date = a.split(";")[1];
				int timescore = Integer.parseInt(a.split(";")[2]);
				names.add(name);
				dates.add(date);
				times.add(timescore);
				a = reader.readLine();
			}
			
			int ct=0;
			while(ct<times.size() && currentTime>times.get(ct)) {
				ct+=1;
			}
			BufferedWriter output = new BufferedWriter(new FileWriter("files/highscore"));  //clears file every time
			for (int i=0; i<ct; i++) {
				output.append(names.get(i) + ";" + dates.get(i) + ";" + times.get(i));
				output.newLine();
			}
			Calendar today = Calendar.getInstance();
		    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/YYYY");
		    String currdate = sdf.format(today.getTime());
			output.append(enteredName + ";" + currdate + ";" + currentTime);
			output.newLine();
			for (int i=ct; i<names.size(); i++) {
				output.append(names.get(i) + ";" + dates.get(i) + ";" + times.get(i));
				output.newLine();
			}
			output.close();
		}
		catch (IOException e) {
			System.out.println("caught an io exception -- file not found");
		}
	}
	
	public void incrementTime(JLabel time, int secs) {
		String init = time.getText();
		int a = Integer.parseInt(init.split(":")[0]);
		int b = Integer.parseInt(init.split(":")[1]);
		a+= secs/60;
		b+= secs % 60;
		if(b>=60) {
			a+=1;
			b-=60;
		}
		String firstPart = String.valueOf(a);
		if (a<10) {
			firstPart = "0" + a;
		}
		String secondPart = String.valueOf(b);
		if (b<10) {
			secondPart = "0" + b;
		}
		time.setText(firstPart + ":" + secondPart);
	}
	
	static JComponent createHorizontalSeparator() {
        JSeparator x = new JSeparator(SwingConstants.HORIZONTAL);
        x.setPreferredSize(new Dimension(50,3));
        return x;
    }
	
    public void run() {
        // NOTE : recall that the 'final' keyword notes immutability even for local variables.

        // Top-level frame in which game components live
        // Be sure to change "TOP LEVEL FRAME" to the name of your game
        final JFrame frame = new JFrame("Sudoku");
        frame.setVisible(false);
        frame.setLocation(300, 300);
        frame.setResizable(false);
        //KeyListener lstnr = new KeyListenerTester("hi", frame);
       
        
        //frame.addKeyListener(lstnr);
        //new KeyListenerTester("Key Listener Tester");
        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Time's ticking...");
        status_panel.add(status);
        final JButton highscore = new JButton("High Scores");
        highscore.setFocusable(false);
        highscore.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	showHighScores();
            }
        });
        status_panel.add(highscore);

        // Main playing area
        final Board court = new Board();
//        int[][] test = court.getSolution();
//        test[3][7]=0;
//        test[5][5]=0;
//        for(int i=0; i<3; i++) {
//        	for(int j=0; j<3; j++) {
//        		test[i][j]=0;
//        	}
//        }
//        System.out.println(Board.isSolvable(test));
        JPanel board = new SudokuBoardLines();
        board.setLayout(new GridLayout(9,9));
        String[][] initial = court.getCurrentBoard();
        JButton[][] buttons = new JButton[9][9];
        for (int i=0; i<9; i++) {
        	for (int j=0; j<9; j++) {
        		JButton entry;
    			entry = new JButton(initial[i][j]); 
    			entry.setBorder(BorderFactory.createBevelBorder(1));
    			entry.setPreferredSize(new Dimension(40, 40));
    			entry.setFocusable(false);
    			if(initial[i][j]!=" ") {
        			entry.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        			entry.setFont(new  Font("Arial", Font.BOLD, 20));
//        			entry.setBackground(Color.GRAY);
//        			entry.setOpaque(true);
        			//entry.setBorderPainted(false);
    			}
    			else {
    				entry.setFont(new  Font("Arial", Font.PLAIN, 20));
    			}
        		buttons[i][j] = entry;
        		board.add(entry);
        	}
        }
        
        for (int i=0; i<9; i++) {
        	for (int j=0; j<9; j++) {
        			final JButton thisButton = buttons[i][j];
        			final Integer xval = i;
        			final Integer yval = j;
        			if(initial[i][j]==" ") {
	        			thisButton.addActionListener(new ActionListener() {
	        				public void actionPerformed(ActionEvent e) {
	                        	lastSelectedX = xval;
	                        	lastSelectedY = yval;
	                        	updateButtons(court, buttons);
	                            //court.reset();
	                        }
	        			});
        			}
        			else {
        				
        			}
        	}
        }

        frame.add(board, BorderLayout.WEST);
        
        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);
        
        // Note here that when we add an action listener to the reset button, we define it as an
        // anonymous inner class that is an instance of ActionListener with its actionPerformed()
        // method overridden. When the button is pressed, actionPerformed() will be called.
        final JButton reset = new JButton("Reset");
        reset.setFocusable(false);
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	court.resetUserInput();
            	updateButtons(court, buttons);
            }
        });
        control_panel.add(reset);
        
        final JLabel time = new JLabel("00:00");
        
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                //...Perform a task...

            	incrementTime(time, 1);
            }
        };
        final Timer timer = new Timer(1000, taskPerformer);
        
        final JButton check = new JButton("Check");
        check.setFocusable(false);
        check.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               check(buttons, court, status, time, timer);
            }
        });
        control_panel.add(check);
        
        final JButton newgame = new JButton("New Game");
        newgame.setFocusable(false);
        newgame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
	           //setVisible(false); //you can't see me!
	           run();
            }
        });
        control_panel.add(newgame);
        
        
        control_panel.add(time);
        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(false);
        frame.addKeyListener(new KeyListener(){
        	   @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyTyped(KeyEvent e) {
            	System.out.print(e.getKeyChar());
            	if(lastSelectedX<0 || lastSelectedY<0) {
            		System.out.println("wrongselection");
            		return;
            	}
            	if(e.getKeyChar() == e.VK_BACK_SPACE) {
            		court.delete(lastSelectedX, lastSelectedY);
            		updateButtons(court, buttons);
            	}
                if(e.getKeyChar()>=49 && e.getKeyChar()<=57) {
                	JButton b = buttons[lastSelectedX][lastSelectedY];
                	court.enter(lastSelectedX, lastSelectedY, Integer.parseInt(Character.toString(e.getKeyChar())));
                	if(court.getUncovered()[lastSelectedX][lastSelectedY]==0) {
                		b.setBorder(null);
                	}
                	updateButtons(court, buttons);
                }
            }	

            @Override
            public void keyReleased(KeyEvent e) {
                updateButtons(court, buttons);
            }
    });
        // Start game
        //court.reset();
        if (firstOpen) {
	        final JFrame instructions = new JFrame("Instructions");
	        JLabel txt = new JLabel("<html>Welcome to Sudoku! This is built like the classic sudoku game. You are given a "
	        		+ "set of initial uncovered spots on a 9x9 grid. Your goal is to determine the numerical value (1-9)"
	        		+ " of the rest of the elements of the grid, based on the following rules:"
	        		+ "<br> 1) Every row must have the numbers 1 through 9 exactly once."
	        		+ "<br> 2) Every column must have the numbers 1 through 9 exactly once." +
	        		"<br> 3) Each mini-square must have the numbers 1 through 9 exactly once."
	        		+ "<br> <br> Input or delete numbers by clicking on the square you want to enter, then using the keyboard"
	        		+ "<br> <br> You can use the check button to see if what you've put in is right, but beware, it adds 10 seconds to the timer."
	        		+ " How fast can you solve the puzzle?  </html>");
	        txt.setBorder(new EmptyBorder(10,10,10,10));
	        instructions.add(txt);
	        instructions.setSize(500, 400);
	        
	        final JButton begin = new JButton("Begin");
	        begin.setSize(1000, 50);
	        begin.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                timer.start(); 
	            	frame.setVisible(true);
	            }
	        });
	        instructions.add(begin, BorderLayout.SOUTH);
	        
	        instructions.setVisible(true);
	        firstOpen = false;
        }
        else {
        	timer.start();
        	frame.setVisible(true);
        }
        
        
    }
    
    public void paint(Graphics g) {
        super.paint(g);
        g.drawString("Hello", 10, 10);
    }
    
    
    /**
     * Main method run to start and run the game. Initializes the GUI elements specified in Game and
     * runs it. IMPORTANT: Do NOT delete! You MUST include this in your final submission.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }
}