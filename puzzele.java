import java.util.*;

public class puzzele {
	
	//We value the variables as global data
		
		static int puzzleSize;
		static int numberOfTiles;
		static int[] array;
		static int[] winnerArray;

		static boolean win ;
		static boolean gameOver ;
		
		static char numberOfMenu;
		static char movement;
		static char inputMenuChar;
		static String logMessage = " ";

		static int counterOfMovement;
		static int counterOfPlaying=1;
		static long currentTime;
		static long startTime;

		// ----end------
	
   //entry of program
	static public void main(String[] arrg) {
		menu();
	}
	//menu section
	static void menu() {
		
		menuDisplay();
		
		Scanner reader = new Scanner(System.in);
		numberOfMenu= reader.next().charAt(0);
		clearScreen();
		switch(numberOfMenu) {
		case'1':{
			playGame();
			//counterOfPlaying++;
			break;
			}
		case'2':
			profile();
			break;
		case'3':
			System.out.println("Goodbye");
			break;
		default:
			{logMessage="COMMAND WAS NOT VALID";
			System.out.println(logMessage);
			logMessage=" ";
			menu();
			}
		}
		
		
	}
	// We only design the display
	static void menuDisplay() {
		clearScreen();
		System.out.println("Enter menu number");
		System.out.println("1: Statrt gaming");
		System.out.println("2: See your profile");
		System.out.println("3: Exit ");
	}
	// This section displays your information
	static void profile() {
		System.out.println("Name: Masum Shaykhan");
		System.out.println("Student Number: 990122680038");
		
		System.out.println("Enter any key back to the menu:");
		Scanner reader = new Scanner(System.in);
		inputMenuChar = reader.next().charAt(0);
		
		menu();
	}
	
	 //We start the game with playGame method in our main method.	
	static void playGame() {
		setGlobal();
		setStartTime();
		setGameBoard();
		winningArray();
		render();
		while (!gameOver) {
			gameLoop();
		}

		if (win) {
			System.out.println(" you won.");
			System.out.println("yourScore: "+score());
		} else if (gameOver) {
			System.out.println(" see you later. ");
			}
		
		
		System.out.println("Enter any key back to the menu:");
		Scanner reader = new Scanner(System.in);
		inputMenuChar = reader.next().charAt(0);
		
		menu();
	}

	static void setGlobal() {
		 counterOfMovement = 0;
		 currentTime = 0;
		 startTime = 0;
		 win = false;
		 gameOver = false;
			

	}
	//Get input to build a random array.
	static void setGameBoard() {
		Scanner scn = new Scanner(System.in);

		System.out.print("Please Enter a number: ");
		puzzleSize = scn.nextInt();

		numberOfTiles = puzzleSize * puzzleSize;
		array = new int[numberOfTiles];

		randomArrayBuilder(numberOfTiles);
		
	}

//This method randomizes custom puzzle tiles.
	static void randomArrayBuilder(int puzzleSize) {
		Random rand = new Random();
		int saver;
		for (int i = 0; i < numberOfTiles; i++) {
			saver = rand.nextInt(numberOfTiles) + 1;
			for (int j = 0; j < i; j++) {
				while (array[j] == saver) {
					saver = rand.nextInt(numberOfTiles) + 1;
					j = 0;
				}
			}
			array[i] = saver;
		}
	}
	
	//This method consists of the winning mode of even and odd arrays.
	static void winningArray() {
		if(puzzleSize%2==0)
		evenWiningArray();
		else
		oddWiningArray();
	}
	
	//If our arrays had even dimensions,this is the condition for winning them.
	static void evenWiningArray() {
		 winnerArray = new int[numberOfTiles];
		int answerForEven=0;
		int answerForOdd=-1;
		for(int i=0;i<numberOfTiles;i++) {
			 if(puzzleSize%2==0) {
				 if(i<((numberOfTiles)/2)-1) {
			          answerForEven+=2;
			          winnerArray[i]=answerForEven;
			     }
			    else {
			          answerForOdd+=2;
			          winnerArray[i]=answerForOdd;
			     }
			    if(i==(numberOfTiles)-1)
			          winnerArray[i]=numberOfTiles;
			     }
		   }
	}
	
	//If our arrays had odd dimensions,this is the condition for winning them.
	static void oddWiningArray() {
		winnerArray = new int[numberOfTiles];
		int answerForEven=0;
		int answerForOdd=-1;
		for(int i=0;i<numberOfTiles;i++) {
			 if(puzzleSize%2!=0) {
				 if(i<((numberOfTiles)/2)) {
			          answerForEven+=2;
			          winnerArray[i]=answerForEven;
			     }
			    else {
			          answerForOdd+=2;
			          winnerArray[i]=answerForOdd;
			     }
			     if(i==(numberOfTiles)-1)
			           winnerArray[i]=numberOfTiles;
			     }
		   }
	}
	
//With this method we get the input character and move with the sour internal method(move`s method in update).
	static void gameLoop() {
		input();
		update();
		render();
	}

//Check if the input character is capital or small
	static void input() {
		System.out.println("Move: L U R D | Back : B\n?: ");
		Scanner reader = new Scanner(System.in);
		movement = reader.next().charAt(0);

		switch (movement) {
		case 'l':
			movement = 'L';
			break;
		case 'r':
			movement = 'R';
			break;
		case 'u':
			movement = 'U';
			break;
		case 'd':
			movement = 'D';
			break;
		case 'b':
			movement = 'B';
			break;
		}
	}

//We enter the characters with which we command the movement into the move method.
	static void update() {
		switch (movement) {
		case 'B':
			gameOver = true;
			counterOfPlaying++;
			break;
		case 'L':
		case 'R':
		case 'U':
		case 'D':
			move();
			break;
		default:
			logMessage = "* COMMAND WAS NOT VALID!";
		}
		gameOverCheck();
	}

//Now each character has entered to its own method.
	static void move() {
		switch (movement) {
		case 'R':
			moveRight();
			break;
		case 'L':
			moveLeft();
			break;
		case 'U':
			moveUp();
			break;
		case 'D':
			moveDown();
			break;
		}
	}

//And the numbers inside the arrays move like puzzle tiles.
	static void moveRight() {
		int saver;
		for (int i = 0; i < numberOfTiles; i++) {
			while (array[i] == numberOfTiles && i % puzzleSize!= 0) {
				saver = array[i - 1];
				array[i - 1] = array[i];
				array[i] = saver;
				counterOfMovement++;
			}
		}
	}

	static void moveLeft() {
		int saver;
		for(int i=numberOfTiles-1;i>=0;i--) {
			while(array[i]==numberOfTiles&& i%puzzleSize!=puzzleSize-1) {
				saver=array[i+1];
				array[i+1]=array[i];
				array[i]=saver;
				counterOfMovement++;
			}
		}
		
	}

	static void moveUp() {
		int saver;
		for (int i =numberOfTiles-1; i>=0; i--) {
			while (array[i] == numberOfTiles && i < numberOfTiles - puzzleSize) {
				saver = array[i +puzzleSize];
				array[i + puzzleSize] = array[i];
				array[i] = saver;
				counterOfMovement++;
			}
		}
	}

	static void moveDown() {
		int saver;
		for (int i = 0; i < numberOfTiles; i++) {
			while (array[i] == numberOfTiles && i >=puzzleSize) {
				saver = array[i - puzzleSize];
				array[i - puzzleSize] = array[i];
				array[i] = saver;
				counterOfMovement++;
			}
		}
	}

//This method calculates the player score using the Java library and the time method.
	static int score() {
		double logMovement = Math.log(counterOfMovement);
		long timeRoot = (long) Math.cbrt(timeOfGaming());
		return (int) ((1000 * numberOfTiles * numberOfTiles) / (logMovement * timeRoot));
	}

//Time to start the game.
	static void setStartTime() {
		startTime = System.nanoTime();
	}

//Time to end the game.
	static void setCurrentTime() {
		currentTime = System.nanoTime();
	}

//Duration of the game.
	static long timeOfGaming() {
		setCurrentTime();
		long duration = (currentTime - startTime);
		long totalTime = duration / 1000000000L;
		return totalTime;
	}

//This method says if we win then the game is over.
	static void gameOverCheck() {
		
		 winCheck();
		if (win) {
			gameOver = true;
			counterOfPlaying++;
		}
		
	}	
//The function of this method is checking the condition of our game.
	static void winCheck() {
		win=true;
	    for(int i=0;i<numberOfTiles;i++) {
	        if(array[i]!=winnerArray[i])
	           win=false;
	    }
	}
	    
//And this is the game display section
	static void render() {
		clearScreen();
		topPannel(logMessage);
		printBoard();
		bottomPannel();
	}

	static void clearScreen() {
//		System.out.print("\033[h\033[2J");
//		System.out.flush();
		for (int i=0; i<20; i++)
			System.out.println("");
	}

	static void topPannel(String logMessage) {
		System.out.println("\nPUZZELE GAME");
		if (logMessage != "") {
			System.out.printf("%s\n", logMessage);
			logMessage = "";
		}
	}

	static void printBoard() {
		for (int i = 0; i < numberOfTiles; i++) {
			if (array[i] == numberOfTiles)
				System.out.print("#\t");
			else
				System.out.print(array[i] + "\t");

			if (i % puzzleSize == puzzleSize - 1)
				System.out.print("\n");
		}
	}

	static void bottomPannel() {
		
		System.out.print("Game's Number: " +counterOfPlaying);
		System.out.print("\nNumber of yuor movements: " + counterOfMovement);
		System.out.printf("\nTime:%d\n", timeOfGaming());
	}

}
