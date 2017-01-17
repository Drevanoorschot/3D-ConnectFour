package model;

public enum Mark {

	X, O, EMPTY;
	public Mark next(Mark m) {
		switch (m) {
		case O:
			return X;
		case X:
			return O;
		default:
			return EMPTY;

		}
	}
}
