/* Nimsys.java
 * This class controls the overall Nim game process
 * Author: Yuting Feng
 * Student ID:896336
 * Created on 29/4/18
 */
import java.util.Scanner;

public class Nimsys {
	NimPlayer[] playerList = new NimPlayer[100];	
	Scanner kb = new Scanner(System.in);
	Boolean isPlaying = true;
	
	enum Cmd{
		ADDPLAYER,
		REMOVEPLAYER,
		EDITPLAYER,
		RESETSTATS,
		DISPLAYPLAYER,
		RANKINGS,
		STARTGAME,
		EXIT
	}
	
	public static void main(String[]args) {
		Nimsys nimsys = new Nimsys();
		nimsys.playGame();
	}
	
	public void playGame() {
		System.out.println("Welcome to Nim");
		while(isPlaying) {
			System.out.print("\n$");
			//input command
			String inputLine = kb.nextLine();
			//cut down the String to several short String
			String[] lineSplit = inputLine.split(" ");
			Cmd inputCmd = Cmd.valueOf(lineSplit[0].toUpperCase());
			switch(inputCmd) {
				case ADDPLAYER:{
					addPlayer(lineSplit[1]);
					break;
				}
				case REMOVEPLAYER:{
					if(lineSplit.length == 1) {
						System.out.println("Are you sure you want to remove all players? (y/n)");
						if(kb.nextLine().equals("y")) {
							for(int i = 0; i < playerList.length; i++) {
								if(playerList[i] != null) {
									removePlayer(playerList[i].getUserName());
								}
							}
						}
						break;
					}
					else{
						removePlayer(lineSplit[1]);
					}
					break;
				}
				case EDITPLAYER:{
					editPlayer(lineSplit[1]);
					break;
				}
				case RESETSTATS:{
					if(lineSplit.length == 1) {
						System.out.println("Are you sure you want to reset all player statistics? (y/n)");
						if(kb.nextLine().equals("y")) {
							for(int i = 0; i < playerList.length; i++) {
								if(playerList[i] != null) {
									resetStats(playerList[i].getUserName());
								}
							}
						}
						break;
					}
					else {
						resetStats(lineSplit[1]);
					}
					break;
				}
				case DISPLAYPLAYER:{
					if(lineSplit.length == 1) {
						for(int i = 0; i < playerList.length - 1; i++) {
							if(playerList[i] != null) {
								displayPlayer(playerList[i].getUserName());
							}
						}
						break;
					}
					else {
						displayPlayer(lineSplit[1]);
					}
					break;
				}
				case RANKINGS:{
					if(lineSplit.length == 1 || lineSplit[1].equals("desc")) {
						rankingsDesc();
					}
					else {
						rankingsAsc();
					}
					break;
				}
				case STARTGAME:{
					startGame(lineSplit[1]);
					break;
				}
				case EXIT:{
					System.out.println();
					System.exit(0);
					break;
				}
				default:{
					break;
				}
			}
		}
	}
	
	//judge if user input exists in player list
	public boolean userExist(String userName, String User) {
		if(userName.equals(User)) {
			return true;
		}
		return false;
	}
	//judge if a user name exists in a user array
	public boolean userNameExist(String[] userName, String User) {
		for(int i = 0; i < userName.length; i++) {
			if(userName[i] != null){
				if(userName[i].equals(User))
						return true;
			}
		}
		return false;
	}

	//add a player
	public void addPlayer(String lineSplit) {
		String[] newUser = lineSplit.split(",");
		for(int i = 0; i < playerList.length; i++) {
			if(playerList[i] == null) {
				playerList[i] = new NimPlayer();
				playerList[i].setUserName(newUser[0]);
				playerList[i].setFamilyName(newUser[1]);
				playerList[i].setGivenName(newUser[2]);
				break;
			}
			else {
				if(userExist(playerList[i].getUserName(), newUser[0])) {
					System.out.println("The player already exists.");
					return;
				}
			}
		}
		
		//sort the players' list alphabetically
		NimPlayer mediate = new NimPlayer();
		for(int i = 0; i < playerList.length - 1; i++) {
			for(int j = 0; j < playerList.length - 1 - i; j++) {
				if(playerList[j] != null && playerList[j + 1] != null) {
					if(playerList[j + 1].getUserName().compareTo(playerList[j].getUserName()) < 0) {
						mediate = playerList[j + 1];
						playerList[j + 1] = playerList[j];
						playerList[j] = mediate;
					}
				}
			}
		}
	}
	
	//remove a player
	public void removePlayer(String lineSplit) {
		for(int i = 0; i < playerList.length; i++) {
			if(playerList[i] != null && userExist(playerList[i].getUserName(), lineSplit)) {
					playerList[i] = null;
					break;
			}
			else {
				if(i == playerList.length - 1) {
					System.out.println("The player does not exist.");
				}
			}
		}
	}
	 
	//edit a player
	public void editPlayer(String lineSplit) {
		String[] newName = lineSplit.split(",");
		for(int i = 0; i < playerList.length; i++) {
			if(playerList[i] != null && userExist(playerList[i].getUserName(), newName[0])) {
					playerList[i].setFamilyName(newName[1]);
					playerList[i].setGivenName(newName[2]);
					break;
			}
			else {
				if(i == playerList.length -1) {
					System.out.println("The player does not exist.");
				}
			}
		}
	}
	
	//reset player's statistics
	public void resetStats(String lineSplit) {
		for(int i = 0; i < playerList.length; i++) {
			if(playerList[i] != null && userExist(playerList[i].getUserName(), lineSplit)) {
					playerList[i].resetStatistics();
					break;
			}
			else {
				if(i == playerList.length -1) {
					System.out.println("The player does not exist.");
				}
			}
		}
	}
	
	//display a user
	public void displayPlayer(String lineSplit) {
		for(int i = 0; i < playerList.length; i++) {
			if(playerList[i] != null && userExist(playerList[i].getUserName(), lineSplit)) {
				System.out.println(playerList[i].getUserName() + "," 		
								+ playerList[i].getGivenName() + "," 
								+ playerList[i].getFamilyName() + "," 									
								+ playerList[i].getGamePlayed() + " games," 
								+ playerList[i].getGameWon() + " wins");
				break;
				}
			else {
				if(playerList[i] != null && userExist(playerList[i].getUserName(), lineSplit)) {
					if(i == playerList.length - 1) {
				  		System.out.println("The player does not exist.");
					}
				}
			}
		}
	}
	
	//rank all users in descending order
	public void rankingsDesc() {
		NimPlayer mediate = new NimPlayer();
		String percentage;
		//sort the playerList[]
		for(int i = 0; i < playerList.length - 1; i++) {
			for(int j = 0; j < playerList.length - 1 - i; j++) {
				if(playerList[j + 1] != null && playerList[j] != null) {
					if(playerList[j + 1].percentage() > playerList[j].percentage()) {
						mediate = playerList[j + 1];
						playerList[j + 1] = playerList[j];
						playerList[j] = mediate;
					}
					else if(playerList[j + 1].percentage() == playerList[j].percentage()) {
						if(playerList[j + 1].getUserName().compareTo(playerList[j].getUserName()) < 0) {
							mediate = playerList[j + 1];
							playerList[j + 1] = playerList[j];
							playerList[j] = mediate;
						}
					}
				}
			}
		}
		//limit the number of displaying the players
		if(playerList.length <= 10) {
			for(int i = 0; i < playerList.length; i++) {
				if(playerList[i] != null) {
					percentage = playerList[i].percentageOfGameWon() + "%";
					System.out.println(String.format("%-5s", percentage) + "| " 
									+ String.format("%02d", playerList[i].getGamePlayed()) + " games | " 
									+ playerList[i].getGivenName() 
									+ " " + playerList[i].getFamilyName());
				}
			}
		}
		else {
			for(int i = 0; i < 10; i++) {
				if(playerList[i] != null) {
					percentage = playerList[i].percentageOfGameWon() + "%";
					System.out.println(String.format("%-5s", percentage) + "| " 
									+ String.format("%02d", playerList[i].getGamePlayed()) + " games | " 
									+ playerList[i].getGivenName() 
									+ " " + playerList[i].getFamilyName());
				}
			}
		}
	}
	
	//rank all users in ascending order
	public void rankingsAsc() {
		NimPlayer mediate = new NimPlayer();
		String percentage;
		//sort the playerList[]
		for(int i = 0; i < playerList.length - 1; i++) {
			for(int j = 0; j < playerList.length - 1 - i; j++) {
				if(playerList[j + 1] != null && playerList[j] != null) {
					if(playerList[j + 1].percentage() < playerList[j].percentage()) {
						mediate = playerList[j + 1];
						playerList[j + 1] = playerList[j];
						playerList[j] = mediate;
					}
					else if(playerList[j + 1].percentage() == playerList[j].percentage()) {
						if(playerList[j + 1].getUserName().compareTo(playerList[j].getUserName()) < 0) {
							mediate = playerList[j + 1];
							playerList[j + 1] = playerList[j];
							playerList[j] = mediate;
						}
					}
				}
			}
		}
		//limit the number of displaying the players
		if(playerList.length <= 10) {
			for(int i = 0; i < playerList.length; i++) {
				if(playerList[i] != null) {
					percentage = playerList[i].percentageOfGameWon() + "%";
					System.out.println(String.format("%-5s", percentage) + "| " 
									+ String.format("%02d", playerList[i].getGamePlayed()) + " games | " 
									+ playerList[i].getGivenName() 
									+ " " + playerList[i].getFamilyName());
				}
			}
		}
		else {
			for(int i = 0; i < 10; i++) {
				if(playerList[i] != null) {
					percentage = playerList[i].percentageOfGameWon() + "%";
					System.out.println(String.format("%-5s", percentage) + "| " 
									+ String.format("%02d", playerList[i].getGamePlayed()) + " games | "
									+ playerList[i].getGivenName() 
									+ " " + playerList[i].getFamilyName());
				}
			}
		}
	}

	//start game
	public void startGame(String lineSplit) {
		NimGame gamePlaying = new NimGame();
		int stone = 0;
		int upperBound = 0;
		boolean isPlayer1Turn = true;	
		String[] playingUser = lineSplit.split(",");
		String[] userList = new String[playerList.length];
		for(int i = 0; i < playerList.length; i++) {
			if(playerList[i] != null) {
				userList[i] = playerList[i].getUserName();
			}
		}
		if(userNameExist(userList, playingUser[2]) && userNameExist(userList, playingUser[3])) {
			//set the initial stone number
			stone = Integer.parseInt(playingUser[0]);
			gamePlaying.setTheRestOfStoneCount(stone);
			//set the bound of stone
			upperBound = Integer.parseInt(playingUser[1]);
			gamePlaying.setUpperBound(upperBound);
			//match the player 1 and 2 in players' list
			for(int i = 0; i < playerList.length; i++) {
				if(playerList[i] != null && playerList[i].getUserName().equals(playingUser[2])) {
					gamePlaying.setPlayer1(i);
				}
				if(playerList[i] != null && playerList[i].getUserName().equals(playingUser[3])) {
					gamePlaying.setPlayer2(i);
				}
			}
			//print the information of the game
			System.out.println();
			System.out.println("Initial stone count: " + gamePlaying.getTheRestOfStoneCount());
			System.out.println("Maximum stone removal: " + gamePlaying.getUpperBound());
			System.out.println("Player 1: " + playerList[gamePlaying.getPlayer1()].getGivenName() + " " + playerList[gamePlaying.getPlayer1()].getFamilyName());
			System.out.println("Player 2: " + playerList[gamePlaying.getPlayer2()].getGivenName() + " " + playerList[gamePlaying.getPlayer2()].getFamilyName());
			
			//game begins
			while(gamePlaying.getTheRestOfStoneCount() > 0) {
				System.out.println();
				System.out.print(gamePlaying.getTheRestOfStoneCount() + " stones left:");
				gamePlaying.displayStone(gamePlaying.getTheRestOfStoneCount());
				System.out.println();
				if(isPlayer1Turn) {
					System.out.println(playerList[gamePlaying.getPlayer1()].getGivenName() + "'s turn - remove how many?");
					isPlayer1Turn = !gamePlaying.correctStone(kb.nextInt());
				}
				else {
					System.out.println(playerList[gamePlaying.getPlayer2()].getGivenName() + "'s turn - remove how many?");
					isPlayer1Turn = gamePlaying.correctStone(kb.nextInt());
				}
				String junk = kb.nextLine();//to get rid of '\n'
			}
			//game has finished, print result and reset the statistics
			System.out.println();
			System.out.println("Game Over");
			if(isPlayer1Turn) {
				playerList[gamePlaying.getPlayer1()].win();
				playerList[gamePlaying.getPlayer2()].lose();
			}
			else {
				playerList[gamePlaying.getPlayer2()].win();
				playerList[gamePlaying.getPlayer1()].lose();
			}
		}
		else {
			System.out.println("One of the players does not exist.");
		}
	}	
}
