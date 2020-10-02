package application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**The class is used to calculate the game steps**/

public class Board{
	private int board[][];	//an array of the used board
	private int CorrectBoard[][];	//an array of the result board
	private List<IntPair> tiles;	//slices of the board
	private IntPair value[];		//the value of each place in array


	public Board(int size){	//a construtor
		this.board= new int[size][size];
		this.CorrectBoard=new int[size][size];
		this.value=new IntPair[size*size];		
		int count=1;	//counts the number of inserted  values
		for (int j = 0; j < size; j++){
			for(int i=0; i<size; i++) {
				if(i==0 && j==0)	//the first place is empty
				{
					this.board[i][j]=0;
					this.CorrectBoard[i][j]=0;
				}
				else{
					this.board[i][j]=count;
					this.CorrectBoard[i][j]=count;
					this.value[count]=new IntPair(i, j);
					count++;
				}

			}
		}			
	}

	public void Shuffle()	//a method which shuffle the whole board (reset)
	{
		tiles=Arrays.asList(value);
		Collections.shuffle(tiles);
		int j=0, i=0, val, x, y;
		for (int k = 0; k < tiles.size(); k++)	//run on the list of tiles
		{
			if(j==3) 
			{
				i++;
				j=0;
			}
			if(tiles.get(k)!=null)	//if the tile is not empty, put in into the board
			{
				x=tiles.get(k).getI();
				y=tiles.get(k).getJ();
				val=CorrectBoard[x][y];
				board[i][j]=val;
			}
			else board[i][j]=0;	//if the slice is empty, put 0 value into the board
			j++;
		}

	}

	public void ShuffleOnePlace()	//a method which choose one slice to move randomly
	{
		List<IntPair> list = new ArrayList<IntPair>();
		IntPair p;
		for(int i=0; i<possibleMoves().size(); i++)	//check the possible moves and put it into a temporary list
			list.add(possibleMoves().get(i));
		Random rand = new Random();
		int n = rand.nextInt(list.size());	//pick a number of slice randomly which will be moved
		p=list.get(n);
		move(p);		
	}


	IntPair getEmpty()	//a method which find the empty place and return it
	{
		int i=0, j=0;
		boolean flag=true;
		while(i<board.length && flag){
			j=0;
			while( j<board.length && flag)
			{
				if(board[i][j]==0)
				{
					flag=false;
					break;
				}
				j++;
			}
			if(flag)
				i++;
			else break;

		}

		return new IntPair(i, j);
	}

	int get(IntPair p)	//a method which return the value of the wanted place
	{
		return board[p.getI()][p.getJ()];
	}

	void move(IntPair p)	//a method which move the wanted pait
	{
		IntPair empty=getEmpty();	//find the empty place
		int occupied=board[p.getI()][p.getJ()];
		int emptyField=board[empty.getI()][empty.getJ()];
		//swap the values
		board[p.getI()][p.getJ()]=emptyField;
		board[empty.getI()][empty.getJ()]=occupied;		
	}

	boolean isDone()	//a method which check if the board is done
	{
		for(int i=0; i<board.length; i++){
			for(int j=0; j<board.length; j++){
				if(CorrectBoard[i][j]!=board[i][j])	//compare between the board of the game to the wanted board
					return false;
			}
		}
		return true;
	}


	List<IntPair> possibleMoves()	//a method which return a list of the possible moves
	{
		List<IntPair> list = new ArrayList<IntPair>();
		int check_dI, check_dJ;
		//find the indexes of the empty place
		int emptyI=getEmpty().getI();
		int emptyJ=getEmpty().getJ();
		for(int i=0; i<board.length; i++){
			for(int j=0; j<board.length; j++)	//check which slice is next to the empty place
			{
				check_dI=i-emptyI;
				check_dJ=j-emptyJ;
				if(check_dI==-1 &&check_dJ==0)
					list.add(new IntPair(i, j));
				if(check_dI==1 &&check_dJ==0)
					list.add(new IntPair(i, j));
				if(check_dI==0 &&check_dJ==1)
					list.add(new IntPair(i, j));
				if(check_dI==0 &&check_dJ==-1)
					list.add(new IntPair(i, j));

			}
		}

		return list;	
	}

}