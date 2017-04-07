package pkgPokerEnum;

public enum eRank {
	TWO(2), 
	THREE(3), 
	FOUR(4), 
	FIVE(5), 
	SIX(6), 
	SEVEN(7), 
	EIGHT(8), 
	NINE(9), 
	TEN(10), 
	JACK(11), 
	QUEEN(12), 
	KING(13), 
	ACE(14),
	JOKER(99,true);
	

	private int iRankNbr;
	private boolean wild;

	public boolean isWild() {
		return wild;
	}
	public void setWild(boolean wild) {
		this.wild = wild;
	}
	private eRank(int iRankNbr) {
		this.iRankNbr = iRankNbr;
	}
	private eRank(int iRankNbr,boolean wild) {
		this.iRankNbr = iRankNbr;
		this.wild=wild;
	}
	public int getiRankNbr() {
		return iRankNbr;
	}
}
