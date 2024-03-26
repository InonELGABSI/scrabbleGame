package test;
import java.util.Objects;
import java.util.Random;


public class Tile{
	public final char letter;
	public final int score;
	
	private Tile(char letter, int score) {
		this.letter = letter;
		this.score = score;
	}
	
	
	@Override
	public int hashCode() {
		return Objects.hash(letter, score);
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tile other = (Tile) obj;
		return letter == other.letter && score == other.score;
	}
	
	
	public static class Bag{
		
		static int[]tilesCountMax = {9,2,2,4,12,2,3,2,9,1,1,4,2,6,8,2,1,6,4,6,4,2,2,1,2,1};
		private static Bag b=null;
		
		int[]tilesCount;
		Tile[] tilesList;
		
		private Bag() {
			this.tilesCount=new int[26];
			this.tilesCount= tilesCountMax.clone();
			
			int[]tilesScore= {1,3,3,2,1,4,2,4,1,8,5,1,3,1,1,3,10,1,1,1,1,4,4,8,4,10};
			this.tilesList=new Tile[26];
			for(int i=0;i<26;i++)
			{
				this.tilesList[i]=new Tile((char)('A'+i),tilesScore[i]);
			}
			
		}
		
		public static Bag getBag() {
			if (Bag.b==null)
			{
				Bag.b=new Bag();
			}
			return Bag.b;
		}
		
		public Tile getRand() {
			Random r= new Random();
			int i = r.nextInt(26);
			int j=i;
			if (this.tilesCount[i] == 0)
			{
				i++;
				while(this.tilesCount[i] == 0 && i!=j)
				{
					i++;
					i=i%26;
				}
				if (i==j)
					return null;
			}
			this.tilesCount[i]--;
			return this.tilesList[i];	
		}
		
		public Tile getTile(char c) {
			int place=(int)c - (int)'A';
			if ((int)c>(int)'Z' || (int)c<(int)'A')
				return null;
			if(this.tilesCount[place]==0)
			{
				return null;
			}
			this.tilesCount[place]--;
			return this.tilesList[place];
		}
		
		public void put(Tile t) {
			int place=(int)t.letter -(int)'A';
			if (this.tilesCount[place] < Bag.tilesCountMax[place])
			{
				this.tilesCount[place]++;
			}
		}
		
		public int size() {
			int sum=0;
			for (int s:this.tilesCount)
			{
				sum+=s;
			}
			return sum;
		}
		
		public int[] getQuantities() {
			return this.tilesCount.clone();
		}
		
		
		
	}

}


