package test;

import java.util.Arrays;

public class Word {
	Tile[]tiles;
	int row,col;
	boolean vertical;
	
	private final int length;
	private int EndCol;
	private int EndRow;

	
	public Word(Tile[] tiles, int row, int col, boolean vertical) {
		this.tiles = tiles;
		this.length=tiles.length;
		
		this.row = row;
		this.col = col;
		this.vertical = vertical;
		
		this.EndCol=col;
		this.EndRow=row;
		if(vertical) {
			this.EndRow+=this.length;
		}
		else {
			this.EndCol+=this.length;
		}
	}
	
	
	public boolean getVertical() {
		return vertical;
	}
	
	
	public int getCol() {
		return col;
	}
	
	
	public int getRow() {
		return row;
	}
	
	
	public int getEndCol() {
		return EndCol;
	} 
	
	
	public int getEndRow() {
		return EndRow;
	}
	
	
	public Tile[] getTiles() {
		return tiles;
	}
	
	
	public int getLength() {
		return length;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Word other = (Word) obj;
		return EndCol == other.EndCol && EndRow == other.EndRow && col == other.col && length == other.length
				&& row == other.row && Arrays.equals(tiles, other.tiles) && vertical == other.vertical;
	}

	
	public boolean isOnSquare(int squareRow,int squareCol)
	{
		if (this.vertical && squareCol==this.col && squareCol==this.EndCol)
		{
			if(squareRow>=this.row && squareRow<this.EndRow)
				return true;
		}
		
		if (!this.vertical && squareRow==this.row && squareRow==this.EndRow)
		{
			if(squareCol>=this.col && squareCol<this.EndCol)
				return true;
		}
		return false;
	}

}
