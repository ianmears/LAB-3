package Exception;

import pkgPokerBLL.Hand;

public class exHand extends Exception {
	
	private Hand hand1;
	private Hand hand2;
	
	public exHand(Hand hand1, Hand hand2) {
		super();
		this.hand1 = hand1;
		this.hand2 = hand2;
		System.out.println("TIE");
	}

	public Hand getHand1() {
		return hand1;
	}

	public void setHand1(Hand hand1) {
		this.hand1 = hand1;
	}

	public Hand getHand2() {
		return hand2;
	}

	public void setHand2(Hand hand2) {
		this.hand2 = hand2;
	}
	
}