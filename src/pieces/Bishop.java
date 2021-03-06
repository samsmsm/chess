package pieces;

import java.util.ArrayList;

import chess.ChessBoard;
import chess.Square;
import javafx.scene.paint.Color;

public class Bishop extends ChessPiece{

	public Bishop(Color c) {
		super(c);
	}

	public ArrayList<Square> returnPath(Square sqr, Color c) {
		ArrayList<Square> sqrArr = new ArrayList<Square>();
		int sqrX = sqr.getX();
		int sqrY = sqr.getY();
		for (int i = 1; i < 8; i++) {
			if(sqrY-i < 0 || sqrX-i < 0) break;
			Square s = ChessBoard.map.get(sqrY-i).get(sqrX-i);
			sqrArr.add(s);
			if(s.hasPiece()) break;
		}
		for (int i = 1; i < 8; i++) {
			if(sqrY-i < 0 || sqrX+i > 7) break;
			Square s = ChessBoard.map.get(sqrY-i).get(sqrX+i);
			sqrArr.add(s);
			if(s.hasPiece()) break;
		}
		for (int i = 1; i < 8; i++) {
			if(sqrY+i > 7 || sqrX+i > 7) break;
			Square s = ChessBoard.map.get(sqrY+i).get(sqrX+i);
			sqrArr.add(s);
			if(s.hasPiece()) break;
		}
		for (int i = 1; i < 8; i++) {
			if(sqrY+i > 7 || sqrX-i < 0) break;
			Square s = ChessBoard.map.get(sqrY+i).get(sqrX-i);
			sqrArr.add(s);
			if(s.hasPiece()) break;
		}
		return sqrArr;
	}
}