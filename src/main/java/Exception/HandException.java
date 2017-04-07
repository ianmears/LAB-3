package Exception;

import pkgPokerBLL.Hand;

public class HandException extends Exception{
	
	private Hand h;

	public HandException(Hand h) {
		super();
		this.h = h;
		System.out.println("Must have five cards to evaluate a hand!");
	}

	public Hand getH() {
		return h;
	}

	public void setH(Hand h) {
		this.h = h;
	}
}

