package chess;

import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import pieces.ChessPiece;
import pieces.King;
import pieces.Pawn;
import pieces.Queen;

public class Square extends Group {

	public static double SIZE = 50;
	private Rectangle r;
	private Color bgColor;
	private boolean selected = false;
	public static Square selectedSquare;
	private ChessPiece cp;
	private Color pieceColor;
	private int xpos;
	private int ypos;
	private Circle cir;
	private Circle castleCir;
	public static ArrayList<Square> pathArr = new ArrayList<Square>();
	public static ArrayList<Square> castleArr = new ArrayList<Square>();
	public static Color turn = Color.WHITE;
	public static Boolean chess = false;
	public static Boolean won = false;
	public static Boolean whiteKingHasMoved = false;
	public static Boolean blackKingHasMoved = false;

	public Square(int x, int y, Color c) {
		r = new Rectangle(SIZE, SIZE);
		r.setFill(c);
		bgColor = c;

		this.setTranslateX(x * SIZE);
		xpos = x;
		this.setTranslateY(y * SIZE);
		ypos = y;

		this.getChildren().add(r);

		this.setOnMouseClicked(event -> {

			if (won == true)
				return;
			if (hasPiece() && getPieceColor() == turn) {
				if (hasCastlePath()) {
					doCastling();
					removeAllPath();
					resetSquare(selectedSquare);
				} else if (!selected) {
					removeAllPath();
					resetSquare(selectedSquare);
					r.setFill(Color.ORANGE);
					selected = true;
					selectedSquare = this;
					if (cp instanceof King) {
						ArrayList<Square> sqrArr = ((King) cp).tryCastle(this);
						for (Square square : sqrArr) {
							square.addCastlePath();
						}
					}
					ArrayList<Square> sqrArr = cp.returnPath(this, selectedSquare.getPieceColor());
					for (Square square : sqrArr) {
						if (square.getPieceColor() != selectedSquare.getPieceColor()) {
							square.addPath();
						}
					}
				} else {
					removeAllPath();
					resetSquare(this);
				}
			} else if (hasPath()) {
				if (!isSameColor() || this.getPieceColor() == null) {
					if (cp instanceof King) {
						won = true;
						if (turn == Color.WHITE) {
							System.out.println("White Won");
						} else if (turn == Color.BLACK) {
							System.out.println("Black Won");
						}
					}
					removePiece();
					movePiece(selectedSquare, this);
					changeTurn();
					removeAllPath();
					resetSquare(selectedSquare);
				}
			} else if (selectedSquare != null) {
				resetSquare(selectedSquare);
				removeAllPath();
			}
		});
	}

	public boolean hasPiece() {
		return this.cp != null;
	}

	public void addPiece(ChessPiece pcs, Color c) {
		if (((c == Color.BLACK && ypos == 7) || (c == Color.WHITE && ypos == 0)) && pcs instanceof Pawn)
			pcs = new Queen(c);
		this.getChildren().add(pcs);
		this.cp = pcs;
		pieceColor = c;
	}

	public void movePiece(Square fromSqr, Square toSqr) {
		if (fromSqr.getPiece() instanceof King) {
			if (turn == Color.WHITE)
				whiteKingHasMoved = true;
			else if (turn == Color.BLACK)
				blackKingHasMoved = true;
		}
		toSqr.addPiece(fromSqr.getPiece(), fromSqr.getPieceColor());
		fromSqr.removePiece();
	}

	public void changeTurn() {
		if (won == true)
			return;
		if (check() == Color.BLACK)
			System.out.println("Check, Black King");
		if (check() == Color.WHITE)
			System.out.println("Check, White King");
		if (turn == Color.WHITE) {
			turn = Color.BLACK;
		} else if (turn == Color.BLACK) {
			turn = Color.WHITE;
		}
	}
	public ChessPiece getPiece() {
		return this.cp;
	}
	public int getX() {
		return this.xpos;
	}
	public int getY() {
		return this.ypos;
	}
	public Color getPieceColor() {
		return this.pieceColor;
	}
	public void addPath() {
		cir = new Circle(10);
		cir.setTranslateX(SIZE / 2);
		cir.setTranslateY(SIZE / 2);
		cir.setFill(Color.RED);
		this.getChildren().add(cir);
		pathArr.add(this);
	}
	public boolean hasPath() {
		return this.cir != null;
	}
	public void removeIndPath() {
		this.getChildren().remove(cir);
		cir = null;
	}
	public void removeIndCastlePath() {
		this.getChildren().remove(castleCir);
		castleCir = null;
	}
	public void removeAllPath() {
		for (Square sqr : pathArr) {
			sqr.removeIndPath();
		}
		pathArr.clear();
		for (Square sqr : castleArr) {
			sqr.removeIndCastlePath();
		}
		castleArr.clear();
	}
	public void removePiece() {
		this.getChildren().remove(cp);
		cp = null;
		pieceColor = null;
	}
	public void resetSquare(Square sqr) {
		if (sqr == null)
			return;
		sqr.r.setFill(sqr.bgColor);
		if (selectedSquare != null) {
			selectedSquare.selected = false;
			selectedSquare = null;
		}
	}
	public void resetBoard() {
		for (ArrayList<Square> list : ChessBoard.map) {
			for (Square s : list) {
				resetSquare(s);
			}
		}
	}

	public boolean isSameColor() {
		if (getPieceColor() == selectedSquare.getPieceColor()) {
			return true;
		} else
			return false;
	}
	public Color check() {
		Color king = null;
		for (ArrayList<Square> list : ChessBoard.map) {
			for (Square s : list) {
				if (s.hasPiece()) {
					ArrayList<Square> s2 = s.getPiece().returnPath(s, s.getPieceColor());
					for (Square s3 : s2) {
						if ((s3.getPiece() instanceof King) && (s3.getPieceColor() != s.getPieceColor()))
							king = s3.getPieceColor();
					}
				}
			}
		}
		return king;
	}
	public Boolean isKingCheck(Color c) {
		if (c == check())
			return true;
		else
			return false;
	}
	public Boolean isSquareCheck() {
		Boolean bool = false;
		for (ArrayList<Square> list : ChessBoard.map) {
			for (Square s : list) {
				if (s.hasPiece() && s.getPieceColor() != turn) {
					ArrayList<Square> s2 = s.getPiece().returnPath(s, s.getPieceColor());
					for (Square s3 : s2) {
						if ((s3 == this)) {
							bool = true;
						}
					}
				}
			}
		}
		return bool;
	}
	public void addCastlePath() {
		castleCir = new Circle(10);
		castleCir.setTranslateX(SIZE / 2);
		castleCir.setTranslateY(SIZE / 2);
		castleCir.setFill(Color.BLUE);
		this.getChildren().add(castleCir);
		castleArr.add(this);
	}
	public boolean hasCastlePath() {
		return this.castleCir != null;
	}
	public void doCastling() {
		if (xpos + 4 < 7) {
			if (ChessBoard.map.get(ypos).get(xpos + 4).getPiece() instanceof King
					&& ChessBoard.map.get(ypos).get(xpos + 4).getPieceColor() == turn) {
				movePiece(ChessBoard.map.get(ypos).get(xpos + 4), ChessBoard.map.get(ypos).get(xpos + 2));
				movePiece(this, ChessBoard.map.get(ypos).get(xpos + 3));
				changeTurn();
				removeAllPath();
			}
		}
		if (xpos - 3 > 0) {
			if (ChessBoard.map.get(ypos).get(xpos - 3).getPiece() instanceof King
					&& ChessBoard.map.get(ypos).get(xpos - 3).getPieceColor() == turn) {
				movePiece(ChessBoard.map.get(ypos).get(xpos - 3), ChessBoard.map.get(ypos).get(xpos - 1));
				movePiece(this, ChessBoard.map.get(ypos).get(xpos - 2));
				changeTurn();
				removeAllPath();
			}
		}
	}
}