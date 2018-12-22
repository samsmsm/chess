package pieces;

import java.util.ArrayList;

import chess.ChessBoard;
import chess.Square;
import javafx.scene.paint.Color;

public class Knight extends ChessPiece{

	public Knight(Color c) {
		super(c);
	}

	public ArrayList<Square> returnPath(Square sqr, Color c){
		ArrayList<Square> sqrArr = new ArrayList<Square>();
		int sqrX = sqr.getX();
		int sqrY = sqr.getY();

		if(sqrY-2 >= 0 && sqrX+1 <= 7) {
			Square s = ChessBoard.map.get(sqrY-2).get(sqrX+1);
			sqrArr.add(s);
		}
			if(sqrY-1 >= 0 && sqrX+2 <= 7) {
				Square s = ChessBoard.map.get(sqrY-1).get(sqrX+2);
				sqrArr.add(s);
		}
		if(sqrY+1 <= 7 && sqrX+2 <= 7) {
			Square s = ChessBoard.map.get(sqrY+1).get(sqrX+2);
			sqrArr.add(s);
		}
			if(sqrY+2 <= 7 && sqrX+1 <= 7) {
				Square s = ChessBoard.map.get(sqrY+2).get(sqrX+1);
				sqrArr.add(s);
		}
		if(sqrY+2 <= 7 && sqrX-1 >= 0) {
			Square s = ChessBoard.map.get(sqrY+2).get(sqrX-1);
			sqrArr.add(s);
		}
			if(sqrY+1 <= 7 && sqrX-2 >= 0) {
				Square s = ChessBoard.map.get(sqrY+1).get(sqrX-2);
				sqrArr.add(s);
		}
		if(sqrY-1 >= 0 && sqrX-2 >= 0) {
			Square s = ChessBoard.map.get(sqrY-1).get(sqrX-2);
			sqrArr.add(s);
		}
			if(sqrY-2 >= 0 && sqrX-1 >= 0) {
				Square s = ChessBoard.map.get(sqrY-2).get(sqrX-1);
				sqrArr.add(s);
		}
		return sqrArr;
	}
}