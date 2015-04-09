
Tic Tac Toe Game (Sep 2002)
---------------------------

1. SUMMARY 
	This is a port of the classic pen and paper Tic Tac Toe game (aka Nuts & Crosses)
	it runs as a java applet on any PC or Mac.

2. REQUIREMENTS TO RUN THE APPLET
	-Java 2 Runtime Environment
	-A web browser
	
3. HOW TO PLAY
	- Compiled class files are located in the "build" folder, just open tictactoe.html
	- Click on the start button to begin a new game. 
	
4. HOW TO COMPILE
	-The easiest way to go is download the Netbeans IDE from: netbeans.org
	There's already an nbproject folder for netbeans you just have to 
	select the tictactoe folder in netbeans to open it.

5. CODE STURCTURE
	-The images folder contains all the jpg used in the game.
	-There is only 1 class: tictactoe which extends JApplet. 
	The click coordinates of the board are set manually and the magic and most
	interesting feature of this game is the CPU AI! Actually it is an excelent
	AI demo.
	The algorithm used for the CPU move is the alpha-beta pruning which always 
	chooses the best move, so it's impossible to win (?), however 
	it has a cutoff function and checks how deep in the search tree we are
	currently searching, if we are already beyond the X ply limit, it returns
	the best move found so far (which could be not the very best one)




