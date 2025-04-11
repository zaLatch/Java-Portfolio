package main;

// I dont even want to comment this since it speak for itself
public class validator {
	
	winpe wp;
	
	public validator(winpe wp) {
		this.wp = wp;
	}
	
	public int diagonal() {
		
		int outcome = -1;
		
		if(wp.grid.evt.gameBoard[1][1] == 0 &&
		(wp.grid.evt.gameBoard[0][0] == 0 && wp.grid.evt.gameBoard[2][2] == 0
		||
		wp.grid.evt.gameBoard[0][2] == 0 && wp.grid.evt.gameBoard[2][0] == 0))
		{
			outcome = 0;
		}
		
		if(wp.grid.evt.gameBoard[1][1] == 1 &&
		(wp.grid.evt.gameBoard[0][0] == 1 && wp.grid.evt.gameBoard[2][2] == 1
		||
		wp.grid.evt.gameBoard[0][2] == 1 && wp.grid.evt.gameBoard[2][0] == 1))
		{
			outcome = 1;
		}
		
		return outcome;
	}	
	
	public int horizontal() {
		
		int outcome = -1;
		
		for(int i = 0; i < 3;i++) {
			
			if(wp.grid.evt.gameBoard[i][0] == 0 && wp.grid.evt.gameBoard[i][1] == 0 && wp.grid.evt.gameBoard[i][2] == 0) {
				outcome = 0;
			}
			else if(wp.grid.evt.gameBoard[i][0] == 1 && wp.grid.evt.gameBoard[i][1] == 1 && wp.grid.evt.gameBoard[i][2] == 1) {
				outcome = 1;
			}
		}
		
		return outcome;
	}
	
	public int vertical() {
		
		int outcome = -1;
		
		for(int i = 0; i < 3; i++) {
			
			if(wp.grid.evt.gameBoard[0][i] == 0 && wp.grid.evt.gameBoard[1][i] == 0 && wp.grid.evt.gameBoard[2][i] == 0) {
				outcome = 0;
			}
			else if(wp.grid.evt.gameBoard[0][i] == 1 && wp.grid.evt.gameBoard[1][i] == 1 && wp.grid.evt.gameBoard[2][i] == 1) {
				outcome = 1;
			}
		}
		
		return outcome;
	}
	
	public int getOutcome() {
		
		int outcome = -1;
		
		if(diagonal() > -1 && outcome < 0) {
			outcome = diagonal();
		}
		if(horizontal() > -1 && outcome < 0) {
			outcome = horizontal();
		}
		if(vertical() > -1 && outcome < 0) {
			outcome = vertical();
		}
		
		return outcome;
	}
}
