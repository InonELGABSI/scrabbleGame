package test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Board {
	private static Board b=null;
	public static char[][]scoreBoard={
			{'r',' ',' ','l',' ',' ',' ','r',' ',' ',' ','l',' ',' ','r'},
			{' ','y',' ',' ',' ','b',' ',' ',' ','b',' ',' ',' ','y',' '},
			{' ',' ','y',' ',' ',' ','l',' ','l',' ',' ',' ','y',' ',' '},
			{'l',' ',' ','y',' ',' ',' ','l',' ',' ',' ','y',' ',' ','l'},
			{' ',' ',' ',' ','y',' ',' ',' ',' ',' ','y',' ',' ',' ',' '},
			{' ','b',' ',' ',' ','b',' ',' ',' ','b',' ',' ',' ','b',' '},
			{' ',' ','l',' ',' ',' ','l',' ','l',' ',' ',' ','l',' ',' '},
			{'r',' ',' ','l',' ',' ',' ',' ',' ',' ',' ','l',' ',' ','r'},
			{' ',' ','l',' ',' ',' ','l',' ','l',' ',' ',' ','l',' ',' '},
			{' ','b',' ',' ',' ','b',' ',' ',' ','b',' ',' ',' ','b',' '},
			{' ',' ',' ',' ','y',' ',' ',' ',' ',' ','y',' ',' ',' ',' '},
			{'l',' ',' ','y',' ',' ',' ','l',' ',' ',' ','y',' ',' ','l'},
			{' ',' ','y',' ',' ',' ','l',' ','l',' ',' ',' ','y',' ',' '},
			{' ','y',' ',' ',' ','b',' ',' ',' ','b',' ',' ',' ','y',' '},
			{'r',' ',' ','l',' ',' ',' ','r',' ',' ',' ','l',' ',' ','r'}
			};
	
	List<Word>wordsList;	
	Tile[][]tilesBoard;
	
 	private Board(){
		this.wordsList=new ArrayList<Word>();
		this.tilesBoard=new Tile[15][15];
	}
	
 	
	public static Board getBoard(){
		if (Board.b==null)
		{
			Board.b=new Board();
		}
		return Board.b;
	}
	
	
	public Tile[][] getTiles() {
		Tile[][]t=new Tile[15][15];
		for (int i=0;i<15;i++) {
            System.arraycopy(this.tilesBoard[i], 0, t[i], 0, 15);
		}
		return t;	
	}
	
	
	private boolean isWordGetInBoard(Word w) {
        return w.getCol() >= 0 && w.getEndCol() <= 14 && w.getRow() >= 0 && w.getEndRow() <= 14;
    }
	
	
	private boolean isLettersPatternFit(Word w) {
		if (this.wordsList.isEmpty())
			return true;
		
	    boolean squareMatch = false;
	    boolean neighborExist = false;

	    if (w.getVertical()) {
	        int j = w.getCol();

	        for (int i = w.getRow(); i < w.getEndRow(); i++) {
	            if (w.getTiles()[i-w.getRow()]==null) {
	            	if(this.tilesBoard[i][j]!=null) {
	            		if(!squareMatch) {
	            			squareMatch=true;
	            		}
	            		else 
	            			return false;
	            	}
	            	else
	            		return false;
	            }
	            else {
	            	if(this.tilesBoard[i][j]!=null)
	            		return false;
	            }


	            if (j > 0) {
	                if (this.tilesBoard[i][j - 1] != null) {
	                    neighborExist = true;
	                }
	            }

	            if (j < 14) {
	                if (this.tilesBoard[i][j + 1] != null) {
	                    neighborExist = true;
	                }
	            }
	        }
	    } 
	    else {
	        int i = w.getRow();

	        for (int j = w.getCol(); j < w.getEndCol(); j++) {
	            if (w.getTiles()[j-w.getCol()]==null) {
	            	if(this.tilesBoard[i][j]!=null) {
	            		if(!squareMatch) {
	            			squareMatch=true;
	            		}
	            		else 
	            			return false;
	            	}
	            	else
	            		return false;
	            }
	            else {
	            	if(this.tilesBoard[i][j]!=null)
	            		return false;
	            }

	            if (i > 0) {
	                if (this.tilesBoard[i - 1][j] != null) {
	                    neighborExist = true;
	                }
	            }

	            if (i < 14) {
	                if (this.tilesBoard[i + 1][j] != null) {
	                    neighborExist = true;
	                }
	            }
	        }
	    }

	    return squareMatch || neighborExist;
	}

	
	public boolean boardLegal(Word w) {
		
		if (this.wordsList.isEmpty() && !w.isOnSquare(7,7)) {
			return false;
		}

        return isWordGetInBoard(w) && isLettersPatternFit(w);
	}
	
	
	public boolean dictionaryLegal(Word w) {
		return true;
	}
	
	
	private Word getSomeWord(Tile[][]includeWord,int someRow,int someCol,boolean v) {
		LinkedList<Tile> ts=new LinkedList<Tile>();
		ts.add(includeWord[someRow][someCol]);
		int startRow=someRow;
		int startCol=someCol;
		if (v) {
			int i=someRow-1;
			while(i>=0 && includeWord[i][someCol]!=null)
			{
				ts.addFirst(includeWord[i][someCol]);
				i--;
			}
			startRow=i+1;
			i=someRow+1;
			while(i<=14 && includeWord[i][someCol]!=null)
			{
				ts.add(includeWord[i][someCol]);
				i++;
			}
		}
		else {
			int j=someCol-1;
			while(j>=0 && includeWord[someRow][j]!=null)
			{
				ts.addFirst(includeWord[someRow][j]);
				j--;
			}
			startCol=j+1;
			j=someCol+1;
			while(j<=14 && includeWord[someRow][j]!=null)
			{
				ts.add(includeWord[someRow][j]);
				j++;
			}
		}
		
		Tile[] t = ts.toArray(new Tile[ts.size()]);
		
		return new Word(t,startRow,startCol,v);
	}
	  
	
	private Word completeWord(Word w){
		if (w.vertical) {
			int j=w.getCol();
			for (int i=w.getRow();i<w.getEndRow();i++) {
				if (w.getTiles()[i-w.getRow()]==null && this.tilesBoard[i][j]!=null)
				{
					Tile[]t=new Tile[w.getTiles().length];
					for (int x=0;x<w.getTiles().length;x++) {
						t[x]= w.getTiles()[x];
					}
					
					t[i-w.getRow()]=this.tilesBoard[i][j];
					return new Word(t,w.getRow(),w.getCol(),w.getVertical());
				}
			}
		}
		else {
			int i=w.getRow();
			for (int j=w.getCol();j<w.getEndCol();j++) {
				if (w.getTiles()[j-w.getCol()]==null && this.tilesBoard[i][j]!=null)
				{
					Tile[]t=new Tile[w.getTiles().length];
					for (int x=0;x<w.getTiles().length;x++) {
						t[x]= w.getTiles()[x];
					}
					
					t[j-w.getCol()]=this.tilesBoard[i][j];
					return new Word(t,w.getRow(),w.getCol(),w.getVertical());
				}
			}
		}
		return w;
	}
	
	
	public ArrayList<Word> getWords(Word w){
		ArrayList<Word>newWords=new ArrayList<Word>();
		
		Word n = this.completeWord(w);
		newWords.add(n);
		if (this.wordsList.isEmpty())
			return newWords;
		
		Tile[][]includeWord=this.getTiles();
		int wordCounter=0;
		
		if (w.vertical) {
			int j=w.getCol();
			for (int i=w.getRow();i<w.getEndRow();i++) {
				if (w.getTiles()[wordCounter]!=null) {
					includeWord[i][j]=w.getTiles()[wordCounter];
				}
				wordCounter++;
			}
		}
		else {
			int i=w.getRow();
			for (int j=w.getCol();j<w.getEndCol();j++) {
				if (w.getTiles()[wordCounter]!=null) {
				includeWord[i][j]=w.getTiles()[wordCounter];
				}
				wordCounter++;
			}
		}
		
		
		if (w.getVertical()) {
			int j = w.getCol();
	        for (int i = w.getRow(); i < w.getEndRow(); i++) {
	        	if (w.getTiles()[i-w.getRow()]!=null) {
	        	    if (j > 0) {
		                if (includeWord[i][j - 1] != null) {
		                    newWords.add(getSomeWord(includeWord,i,j,false));
		                    continue;
		                }
		            }
	        	    if (j < 14) {
		                if (includeWord[i][j + 1] != null) {
		                    newWords.add(getSomeWord(includeWord,i,j,false));
		                }
		            }
	        	}
	        }
		}
		else {
			int i = w.getRow();
	        for (int j = w.getCol(); j < w.getEndCol(); j++) {
	        	if (w.getTiles()[j-w.getCol()]!=null) {
	        	    if (i > 0) {
		                if (this.tilesBoard[i - 1][j] != null) {
		                    newWords.add(getSomeWord(includeWord,i,j,true));
		                    continue;
		                }
		            }
	        	    if (i < 14) {
		                if (this.tilesBoard[i + 1][j] != null) {
		                    newWords.add(getSomeWord(includeWord,i,j,true));
		                }
		            }
	        	}
	        }
		}
		
		return newWords;
	}
	
	private int calculateLetterScore(int i, int j,int[]yCount,int[]rCount,Word w,int wordCounter){
		int letterscore=0;
		if (Board.scoreBoard[i][j] ==' ') {
			letterscore+=w.tiles[wordCounter].score;
		}
		else {
			if (Board.scoreBoard[i][j]=='b')
				letterscore+=(w.tiles[wordCounter].score)*3;

			else if(Board.scoreBoard[i][j]=='l')
				letterscore+=(w.tiles[wordCounter].score)*2;

			else if(Board.scoreBoard[i][j]=='y') {
				letterscore+=w.tiles[wordCounter].score;
				yCount[0]+=1;
			}

			else if(Board.scoreBoard[i][j]=='r') {
				letterscore+=w.tiles[wordCounter].score;
				rCount[0]+=1;
			}
		}
		return letterscore;
	}
	public int getScore(Word w) {
		int totalScore=0;
		int wordCounter=0;

		int[]yCount={0};
		int[]rCount={0};
		
		if (w.getVertical()) {
			int j=w.getCol();
			for(int i=w.getRow();i<w.getEndRow();i++) {
				totalScore += calculateLetterScore(i,j,yCount,rCount,w,wordCounter);
				wordCounter++;
			}
		}
		else {
			int i=w.getRow();
			for(int j=w.getCol();j<w.getEndCol();j++) {
				totalScore += calculateLetterScore(i,j,yCount,rCount,w,wordCounter);
				wordCounter++;
			}
		}
		
		if (this.wordsList.isEmpty())
			yCount[0]++;
		
		if (yCount[0]>0) {
            totalScore*= (int) Math.pow(2, yCount[0]);
        }
	
		if (rCount[0]>0) {
            totalScore*= (int) Math.pow(3, rCount[0]);
        }
		
		return totalScore;	
	}
	
	
	private void addWordToBoard(Word w) {
		this.wordsList.add(w);
		int wordCounter=0;
		
		if(w.getVertical()) {
			int j=w.getCol();
			for (int i=w.getRow();i<w.getEndRow();i++) {
				if(this.tilesBoard[i][j]==null)
					this.tilesBoard[i][j]=w.getTiles()[wordCounter];
				wordCounter++;
			}
		}
		else {
			int i=w.getRow();
			for (int j=w.getCol();j<w.getEndCol();j++) {
				if(this.tilesBoard[i][j]==null)
					this.tilesBoard[i][j]=w.getTiles()[wordCounter];
				wordCounter++;
		    }
		}
	}
	
	
	public int tryPlaceWord(Word w) {
		if (boardLegal(w))
		{
			int sumScore=0;
			ArrayList<Word>words=this.getWords(w);
			
			for (Word i:words) {
				if(!this.dictionaryLegal(i))
					return 0;
			}
			
			for (Word i:words) {
				sumScore+= this.getScore(i);
				this.addWordToBoard(i);
			}
			return sumScore;
		}
		return 0;
	}
	
	
}
