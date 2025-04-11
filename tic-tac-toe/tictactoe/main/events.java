package main;

import java.util.*;

public class events {
	
	winpe wp;

	int currentPlayer = 0;
	
	// List used to set the initial position and player's move
	List<Integer> playerMoves = new ArrayList<>();
	List<Integer> clickedSquares = new ArrayList<>();
	
	// This matrix is the one that is used to check which values match
	/* -1 is used to not be confused with the good values
	 *  0 = X and 1 = O
	 */
	int[][] gameBoard = {{-1, -1, -1}, 
				         {-1, -1, -1}, 
				         {-1, -1, -1}};
	
	public events(winpe wp) {
		this.wp = wp;
	}
	
	// Handles the square clicked by the user
	public void handleSquareClicked() {
		
		for(int i = 0; i < wp.grid.rect.length; i++) {
			
			if(wp.grid.rect[i].intersects(wp.mp.hitbox)) {
				
				if(!clickedSquares.contains(i)) { 
					wp.grid.moves++;
					clickedSquares.add(i);
					// Add the currentPlayer to the playerMoves list
					playerMoves.add(currentPlayer);
					// Call the markMoveInBoard() function and put the currentPlayer in the parameter
					markMoveInBoard(currentPlayer, i);
					
					// Current player switch
					if(currentPlayer == 0) {
						currentPlayer = 1;
					}
					else if(currentPlayer == 1) {
						currentPlayer = 0;
					}
				}
			}
		}
	}
	
	// Resets all value to their original state
	public void resetGameState() {
		
		if(wp.grid.rbtnArea.intersects(wp.mp.hitbox)) {
			
			if(wp.grid.outcome > -1) {
				wp.grid.round++;
			}
			
			if(wp.grid.outcome == 0 && wp.grid.outcome != 2) {
				wp.grid.score0+=1;
			}else if(wp.grid.outcome == 1 && wp.grid.outcome != 2) {
				wp.grid.score1+=1;
			}
			
			clickedSquares.clear();
			playerMoves.clear();
			wp.grid.isGameDone = false;
			wp.grid.outcome = -1;
			wp.grid.moves = 0;
			gameBoard = new int[3][3];
			
			for(int i = 0; i < 3; i++) {
				for(int j = 0; j < 3; j++) {
					gameBoard[i][j] = -1;
				}
			}
		}
	}
	
	// Marks the move in the gameBoard
	public void markMoveInBoard(int value, int currentSquare) {
		
		int index = 0;
		
		for(int row = 0; row < gameBoard.length; row++) {
			for(int col = 0; col < gameBoard[row].length; col++) {
				if(index == currentSquare) {
					gameBoard[row][col] = value;
				}
				index++;
			}
		}
		index = 0;
	}
	
	
	
	
}
