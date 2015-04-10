package swingme;

public class Board {
	public final int M;
	public final int N;
	int board[][];

	public Board(int M) {
		this.M = M;
		this.N = M;
		board = new int[M][M];

	}
	
	public boolean isItFull(){
		for ( int i = 0 ; i < M ; i++)
			for (int j = 0 ; j < M ; j++)
				if (board[i][j] == 0)
					return false;
		return true;
	}
	
}
