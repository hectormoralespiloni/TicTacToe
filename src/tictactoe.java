/*-------------------------------------------------
 TicTacToe
 This is an implementation of the well known
 Noughts & Crosses game using a Cutoff algorithm
 Alpha-Beta pruning: we will use a two-ply
 game tree(to make it easier to play)
 
 Author: Héctor Morales Piloni, MSc
 Date:   September 26, 2004
 -----------------------------------------------*/

import java.awt.*;
import java.net.*;
import java.awt.Color;
import java.util.Random;
import java.awt.image.BufferedImage;

public class tictactoe extends javax.swing.JApplet 
{
	private Image board;
    private Image cross;
    private Image circle;
    private Image diag1;
    private Image diag2;
    private Image horz;
    private Image vert;
    private Image start;
    private Point coords[];
    private int player_score,cpu_score;
    private int theBoard[];
    private int BestPos;
    private int CutOff;
    private int difficulty;
    private int nowPlaying;
    private boolean gameStarted;
    private boolean showError;
    private boolean winR1,winR2,winR3;
    private boolean winC1,winC2,winC3;
    private boolean winD1,winD2;
    final private int CPU = 0;
    final private int PLAYER = 1;
    final private int NONE = 2;
    final private int EMPTY = 9;
    
    //initializes the applet tictactoe
    public void init() 
    {
    	initComponents();
        initCoords();
        
        BestPos = 0;
        CutOff = 0;
        nowPlaying = NONE;
        gameStarted = false;
        
        //initializes the Board
        theBoard = new int[9];                
        for(int i=0; i<9; i++)
            theBoard[i] = EMPTY;

        //sets board size
        this.setSize(300,380);
        
        //load images
        board = getImage(getCodeBase(),"images/board.gif");
        cross = getImage(getCodeBase(),"images/cross.gif");
        circle= getImage(getCodeBase(),"images/circle.gif");
        diag1 = getImage(getCodeBase(),"images/diag1.gif");
        diag2 = getImage(getCodeBase(),"images/diag2.gif");
        horz  = getImage(getCodeBase(),"images/horz.gif");
        vert  = getImage(getCodeBase(),"images/vert.gif");
        start = getImage(getCodeBase(),"images/button_start.gif");
    }
    
    private void initComponents() 
    {
    	getContentPane().setLayout(null);

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tictactoe.this.mouseClicked(evt);
            }
        });

    }

    /*----------------------------------
     Initializes the coordinates array
     each one represent a position of 
     an image in the board
     ---------------------------------*/
    private void initCoords()
    {
        coords = new Point[9];

        coords[0] = new Point(30,30);
        coords[1] = new Point(118,30);
        coords[2] = new Point(205,30);
        coords[3] = new Point(30,115);
        coords[4] = new Point(118,115);
        coords[5] = new Point(205,115);
        coords[6] = new Point(30,200);
        coords[7] = new Point(118,200);
        coords[8] = new Point(205,200);
    }
    
    private void newGame()
    {
        Random rand = new Random();
        
        BestPos = 0;
        CutOff = 0;
        
        difficulty = java.lang.Math.abs(rand.nextInt()%2)+2;
        
        if(player_score == 99 || cpu_score == 99)
        	player_score = cpu_score = 0;
        
        //initializes the winning flags
        winR1=winR2=winR3=false;
        winC1=winC2=winC3=false;
        winD1=winD2=false;
        
        //initializes the Board
        theBoard = new int[9];                
        for(int i=0; i<9; i++)
            theBoard[i] = EMPTY;
        
        showError = false;
        gameStarted = true;
        repaint();
        
        nowPlaying = java.lang.Math.abs(rand.nextInt()%2);
        if(nowPlaying==CPU)
            CPUmove();
    }
    
	private void mouseClicked(java.awt.event.MouseEvent evt) 
	{
        boolean error = false;
        
        //check if user is trying to start a new game
        if(evt.getY()>300 && evt.getY()<350){
            if(!gameStarted)
                newGame();
            return;
        }
        
        //if computer is moving, or there's no one playing... exit!
        if(nowPlaying==CPU || nowPlaying==NONE)
            return;
        
        //Check Row 1
        if((evt.getY()>15) && (evt.getY()<100))
        {
           if((evt.getX()>15) && (evt.getX()<100)){
                if(theBoard[0]!=EMPTY){
                    showError();
                    error = true;
               	}
                else
                    place(PLAYER,0);
           }
           else
           if((evt.getX()>118) && (evt.getX()<182)){
               if(theBoard[1]!=EMPTY){
                   showError();
                   error = true;
               }
               else
                   place(PLAYER,1);
           }
           else
           if((evt.getX()>200) && (evt.getX()<280)){
              if(theBoard[2]!=EMPTY){
                  showError();
                  error = true;
              }
              else
                  place(PLAYER,2);
           }
           else
           	   error = true;			
        }
        //Check Row 2
        else
        if((evt.getY()>115) && (evt.getY()<180))
        {
            if((evt.getX()>15) && (evt.getX()<100)){
                if(theBoard[3]!=EMPTY){
                    showError();
                    error = true;
                }
                else
                    place(PLAYER,3);
            }
            else
            if((evt.getX()>118) && (evt.getX()<182)){
                if(theBoard[4]!=EMPTY){
                    showError();
                    error = true;
                }
                else
                    place(PLAYER,4);
            }
            else
            if((evt.getX()>200) && (evt.getX()<280)){
                if(theBoard[5]!=EMPTY){
                    showError();
                    error = true;
                }
                else
                    place(PLAYER,5);
            }
           	else
           	   error = true;			
        }
        //Check Row 3
        else
        if(((evt.getY()>200) && (evt.getY()<280)))
        {   
            if((evt.getX()>15) && (evt.getX()<100)){
                if(theBoard[6]!=EMPTY){
                    showError();
                    error = true;
                }
                else
                    place(PLAYER,6);
            }
            else
            if((evt.getX()>118) && (evt.getX()<182)){
                if(theBoard[7]!=EMPTY){
                    showError();
                    error = true;
                }
                else
                    place(PLAYER,7);
            }
            else
            if((evt.getX()>200) && (evt.getX()<280)){
                if(theBoard[8]!=EMPTY){
                    showError();
                    error = true;
                }
                else
                    place(PLAYER,8);
            }
            else
           	   error = true;			
        }
        else
        	error = true;

        //change turn to CPU
        if((!error) && (nowPlaying!=NONE))
            CPUmove();
    }

    /*---------------------------------------
     This function places a cross or a circle
     at a specified position
     who can be wither PLAYER or CPU
     ----------------------------------------*/
    private void place(int who, int position)
    {
        //mark the position as busy
        theBoard[position] = who;
        repaint();
        
        //check for a winner
        testWinner();        
    }
   
    /*------------------------------
     Calculates the best move
     for CPU, it uses the 
     Alpha-Beta prunning algorithm
     ------------------------------*/
    private void CPUmove()
    {
        MaxValue(CPU,-100000,100000);
		place(CPU,BestPos);
        
        //change turn to player
        if(nowPlaying!=NONE)
            nowPlaying=PLAYER;
    }
    
    /*------------------------------------------
    This function checks for a terminal state
    We have a terminal state if there's a winner
    or the game is a tie
     -----------------------------------------*/
    private boolean Terminal()
    {
        //rows
		int r1,r2,r3;
        //columns
		int c1,c2,c3;
        //diagonals
		int d1,d2;

		r1 = theBoard[0] + theBoard[1] + theBoard[2];
		r2 = theBoard[3] + theBoard[4] + theBoard[5];
		r3 = theBoard[6] + theBoard[7] + theBoard[8];
		c1 = theBoard[0] + theBoard[3] + theBoard[6];
		c2 = theBoard[1] + theBoard[4] + theBoard[7];
		c3 = theBoard[2] + theBoard[5] + theBoard[8];
		d1 = theBoard[0] + theBoard[4] + theBoard[8];
		d2 = theBoard[2] + theBoard[4] + theBoard[6];

		if(r1==0 || r2==0 || r3==0 || c1==0 || c2==0 || c3==0 || d1==0 || d2==0)
            return true;
		else
		if(r1==3 || r2==3 || r3==3 || c1==3 || c2==3 || c3==3 || d1==3 || d2==3)
            return true;
		else{
            for(int i=0; i<9; i++)
                if(theBoard[i] == EMPTY)
                    return false;
		}

		//tie game
		return true;
    }

    private boolean Cut_Off()
    {
        //check if the state is terminal
		if(Terminal())
            return true;

		//check if we're beyond the X ply limit
		//possible values are 1,2 or 3 (easy, hard or impossible)
		if(CutOff > difficulty)
            return true;

		return false;                
    }
    
    private int Utility()
    {
        //Utility is calculated as follows
		//1 X or O together = 10
		//2 X or O together = 100
		//3 X or O together = 1000

		int i,j,col;
		int count_Player = 1, count_CPU = 1;
		int utility = 0;

		//check utility in rows
		for(i=0; i<9; i++)
		{
            if(theBoard[i] == CPU)
                count_CPU*=10;
            else
            if(theBoard[i] == PLAYER)
                count_Player*=10;

            //is at the end of the row?
            if(i%3 == 2){
                utility += count_CPU - count_Player;
                count_Player=1;
                count_CPU=1;
            }		
		}

		count_Player=1;
		count_CPU=1;

		//check utility in columns
		for(i=0,col=-2,j=0; i<9; i++,j+=3)
		{
            if(theBoard[j] == CPU)
                count_CPU*=10;
            else
            if(theBoard[j] == PLAYER)
                count_Player*=10;

            //is at the end of the column?
            if(j/3 == 2){
                j=col++;
                utility += count_CPU - count_Player;
				count_Player=1;
				count_CPU=1;
            }		
		}

		count_Player=1;
		count_CPU=1;

		//check utility in diagonals
		if(theBoard[0] == CPU)
            count_CPU*=10;
		else
		if(theBoard[0] == PLAYER)
            count_Player*=10;

		if(theBoard[4] == CPU)
            count_CPU*=10;
		else
		if(theBoard[4] == PLAYER)
            count_Player*=10;

		if(theBoard[8] == CPU)
            count_CPU*=10;
		else
		if(theBoard[8] == PLAYER)
            count_Player*=10;

		utility += count_CPU - count_Player;
		count_Player=1;
		count_CPU=1;
			
		if(theBoard[2] == CPU)
            count_CPU*=10;
		else
		if(theBoard[2] == PLAYER)
            count_Player*=10;

		if(theBoard[4] == CPU)
            count_CPU*=10;
		else
		if(theBoard[4] == PLAYER)
            count_Player*=10;

		if(theBoard[6] == CPU)
            count_CPU*=10;
		else
		if(theBoard[6] == PLAYER)
            count_Player*=10;

		utility += count_CPU - count_Player;

		return utility;
    }   
    
    private int MaxValue(int who, int alpha, int beta)
    {
        int i,oldAlpha;
		int pos = 0;

		//increment the deep of the search
		CutOff++;

		//check if we're in a terminal state or we have reached 2 ply
		if(Cut_Off()){
            CutOff--;
            return Utility();
		}
		else{
            for(i=0; i<9; i++)
            {
                if(theBoard[i] == 9)
                {
                    //tries a new move
                    theBoard[i] = who;
                    oldAlpha = alpha;				
                    alpha = java.lang.Math.max(alpha,MinValue((who==PLAYER)?CPU:PLAYER,alpha,beta));
                    
                    //if there's a change in alpha,
                    //then it is the best position
                    if(alpha > oldAlpha)
                        pos = i;
                    theBoard[i] = 9;
                    if(alpha >= beta){
                        CutOff--;
                        return beta;
                    }//if alpha>beta
				}//if theBoards==9
            }//for
        }//else
		BestPos = pos;
		CutOff--;
		return alpha;
    }

    private int MinValue(int who,int alpha, int beta)
    {
		int i;

		CutOff++;
		if(Cut_Off()){
            CutOff--;
            return Utility();
		}
		else{
            for(i=0; i<9; i++)
            {
                if(theBoard[i] == 9)
                {
                    //tries a new move
                    theBoard[i] = who;
                    beta = java.lang.Math.min(beta,MaxValue((who==PLAYER)?CPU:PLAYER,alpha,beta));
                    theBoard[i] = 9;		
                    if(beta <= alpha){
                        CutOff--;
						return alpha;
                    }//if beta<alpha
				}//if theBoard == 9
            }//for
        }//else
		CutOff--;
		return beta;
	}
    
    /*-------------------------------
     This function does a test on 
     every position of the board to
     detect if we have a winner
     -------------------------------*/
    private void testWinner()
    {
        //rows
		int r1,r2,r3;
        //columns
		int c1,c2,c3;
        //diagonals
		int d1,d2;

        //add rows and columns to check if we have a winner
		r1 = theBoard[0] + theBoard[1] + theBoard[2];
		r2 = theBoard[3] + theBoard[4] + theBoard[5];
		r3 = theBoard[6] + theBoard[7] + theBoard[8];
		c1 = theBoard[0] + theBoard[3] + theBoard[6];
		c2 = theBoard[1] + theBoard[4] + theBoard[7];
		c3 = theBoard[2] + theBoard[5] + theBoard[8];
		d1 = theBoard[0] + theBoard[4] + theBoard[8];
		d2 = theBoard[2] + theBoard[4] + theBoard[6];

        //variables used to know what row or column has won
        winR1 = (r1==0 || r1==3)?true:false;
        winR2 = (r2==0 || r2==3)?true:false;
        winR3 = (r3==0 || r3==3)?true:false;
        winC1 = (c1==0 || c1==3)?true:false;
        winC2 = (c2==0 || c2==3)?true:false;
        winC3 = (c3==0 || c3==3)?true:false;
        winD1 = (d1==0 || d1==3)?true:false;
        winD2 = (d2==0 || d2==3)?true:false;
        
        //if sum is zero, CPU has won
		if(r1==0 || r2==0 || r3==0 || c1==0 || c2==0 || c3==0 || d1==0 || d2==0)
		{
            //javax.swing.JOptionPane.showMessageDialog(this,"Computer wins!");
            cpu_score++;
            nowPlaying = NONE;
            gameStarted = false;
            repaint();
        }
		//if sum is exactly 3 PLAYER has won (because PLAYER = 1)
        else
		if(r1==3 || r2==3 || r3==3 || c1==3 || c2==3 || c3==3 || d1==3 || d2==3)
		{
            //javax.swing.JOptionPane.showMessageDialog(this,"You win!");
            player_score++;
            nowPlaying = NONE;
            gameStarted = false;
            repaint();
        }
		//if sum < 9 (ie no empty slots) there's a tie
        else
		if(r1+r2+r3 < 9)
		{
            //javax.swing.JOptionPane.showMessageDialog(this,"Tie game!");
            nowPlaying = NONE;
            gameStarted = false;
            repaint();
        }
    }
    
    /*----------------------------------
     Shows an error msg if player
     is trying to make an illegal move
     ----------------------------------*/
    private void showError(){
        //javax.swing.JOptionPane.showMessageDialog(this,"¡Movimiento Ilegal!");
        showError = true;
        repaint();
    }
    
    public void paint(Graphics g) 
    {
        Dimension dimension = getSize();
        BufferedImage bufferedimage = new BufferedImage(dimension.width, dimension.height, 1);
        Graphics2D g2 = (Graphics2D)bufferedimage.getGraphics();

        g2.drawImage(board,0,0,this);

        //draw O and X
        for(int i=0; i<9; i++)
        {
            switch(theBoard[i]){
                case CPU:   
                    g2.drawImage(circle,(int)coords[i].getX(),(int)coords[i].getY(),this);
                    break;
                case PLAYER:
                    g2.drawImage(cross,(int)coords[i].getX(),(int)coords[i].getY(),this);
                    break;
            }//switch
        }//for i
        
        //draw a line if we have a winner
        g2.setColor(new Color(255,50,50));
        if(winR1)
        	g2.drawImage(horz,15,55,this);
        else if(winR2)
        	g2.drawImage(horz,15,145,this);
        else if(winR3)
        	g2.drawImage(horz,15,225,this);
        else if(winC1)
        	g2.drawImage(vert,55,10,this);
        else if(winC2)
        	g2.drawImage(vert,145,10,this);
        else if(winC3)
        	g2.drawImage(vert,225,10,this);
        else if(winD1)
        	g2.drawImage(diag1,45,45,this);
        else if(winD2)
        	g2.drawImage(diag2,45,45,this);
        
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial",Font.BOLD,18));
        if(!gameStarted)
			g2.drawString("Presiona el botón para comenzar",5,370);
		else {
			if(!showError)
				g2.drawString("¡El juego ha comenzado!",35,370);
			else{
				g2.drawString("¡Error, movimiento Ilegal!",35,370);
				showError = false;
			}
		}

        g2.setColor(new Color(196,139,46));
        g2.setFont(new Font("Arial",Font.BOLD,48));
		
		if(!gameStarted)
			g2.drawImage(start,111,293,this);
			
		//display score when game starts
		if(player_score < 10)
   	    	g2.drawString(" "+player_score,215,335);
        else 
   	    	g2.drawString(""+player_score,215,335);
        
        if(cpu_score < 10)
   	    	g2.drawString(" "+cpu_score,25,335);
       	else
       		g2.drawString(""+cpu_score,25,335);        	
        	
        g.drawImage(bufferedimage, 0, 0, this);
    }    
}
