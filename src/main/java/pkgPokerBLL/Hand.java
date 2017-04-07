package pkgPokerBLL;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Collections;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

import Exception.HandException;
import Exception.exHand;
import pkgPokerEnum.eCardNo;
import pkgPokerEnum.eHandStrength;
import pkgPokerEnum.eHandStrength;
import pkgPokerEnum.eRank;
import pkgPokerEnum.eSuit;

public class Hand {

	private UUID HandID;
	private boolean bIsScored;
	private HandScore HS;
	private ArrayList<Card> CardsInHand = new ArrayList<Card>();

	public Hand() {

	}

	public void AddCardToHand(Card c) {
		CardsInHand.add(c);
	}

	public ArrayList<Card> getCardsInHand() {
		return CardsInHand;
	}

	public HandScore getHandScore() {
		return HS;
	}

	public Hand EvaluateHand() throws HandException {
		Hand h = Hand.EvaluateHand(this);
		return h;
	}
	
	public ArrayList<Hand> isJokerInHand(Hand H){
		ArrayList<Hand> handList = new ArrayList<Hand>();
		ArrayList<Card> cardList = new ArrayList<Card>();
		Collections.sort(H.getCardsInHand());
		cardList = H.getCardsInHand();
		Card card1 = cardList.get(0);
		Card card2 = cardList.get(1);
		Card card3 = cardList.get(2);
		Card card4 = cardList.get(3);
		Card card5 = cardList.get(4);
		
		if(card1.geteRank()==eRank.JOKER){
			if(card2.geteRank()==eRank.JOKER){
				if(card3.geteRank()==eRank.JOKER){
					if(card4.geteRank()==eRank.JOKER){
						if(card5.geteRank()==eRank.JOKER){
							for (eSuit suit : eSuit.values()) {
								for (eRank rank : eRank.values()) {
										Card c = new Card(rank,suit);
										Hand h = new Hand();
										h.AddCardToHand(c);
										h.AddCardToHand(c);
										h.AddCardToHand(c);
										h.AddCardToHand(c);
										h.AddCardToHand(c);
										handList.add(h);
								}
							}
						
					}
					else{
						for (eSuit suit : eSuit.values()) {
							for (eRank rank : eRank.values()) {
									Card c = new Card(rank,suit);
									Hand h = new Hand();
									h.AddCardToHand(c);
									h.AddCardToHand(c);
									h.AddCardToHand(c);
									h.AddCardToHand(c);
									h.AddCardToHand(card5);
									handList.add(h);
							}
						}
					}
				}
				else{
					for (eSuit suit : eSuit.values()) {
						for (eRank rank : eRank.values()) {
								Card c = new Card(rank,suit);
								Hand h = new Hand();
								h.AddCardToHand(c);
								h.AddCardToHand(c);
								h.AddCardToHand(c);
								h.AddCardToHand(card4);
								h.AddCardToHand(card5);
								handList.add(h);
						}
					}
				}
			}
			else{
				for (eSuit suit : eSuit.values()) {
					for (eRank rank : eRank.values()) {
							Card c = new Card(rank,suit);
							Hand h = new Hand();
							h.AddCardToHand(c);
							h.AddCardToHand(c);
							h.AddCardToHand(card3);
							h.AddCardToHand(card4);
							h.AddCardToHand(card5);
							handList.add(h);
					}
				}
			}
		}
		else{
			for (eSuit suit : eSuit.values()) {
				for (eRank rank : eRank.values()) {
						Card c = new Card(rank,suit);
						Hand h = new Hand();
						h.AddCardToHand(c);
						h.AddCardToHand(card2);
						h.AddCardToHand(card3);
						h.AddCardToHand(card4);
						h.AddCardToHand(card5);
						handList.add(h);
					}
				}
			}
		}
		return handList;
		
	}
	
	
	public static Hand PickBestHand(ArrayList<Hand> Hands) throws exHand{
		Hand bestHand = null;
		for(int i = 0; i<Hands.size();i++){
			//couldn't figure out how to compare two hands properly
			if(Hands.get(i).getHandScore() >= bestHand.getHandScore()){
				bestHand=Hands.get(i);
				
			}
			else if(Hands.get(i).getHandScore() == bestHand.getHandScore()){
				throw new exHand(Hands.get(i),bestHand);
			}
		}
	}

	private static Hand EvaluateHand(Hand h) throws HandException {

		Collections.sort(h.getCardsInHand());

		// Another way to sort
		// Collections.sort(h.getCardsInHand(), Card.CardRank);

		HandScore hs = new HandScore();
		try {
			Class<?> c = Class.forName("pkgPokerBLL.Hand");

			for (eHandStrength hstr : eHandStrength.values()) {
				Class[] cArg = new Class[2];
				cArg[0] = pkgPokerBLL.Hand.class;
				cArg[1] = pkgPokerBLL.HandScore.class;

				Method meth = c.getMethod(hstr.getEvalMethod(), cArg);
				Object o = meth.invoke(null, new Object[] { h, hs });

				// If o = true, that means the hand evaluated- skip the rest of
				// the evaluations
				if ((Boolean) o) {
					break;
				}
			}

			h.bIsScored = true;
			h.HS = hs;

		} catch (ClassNotFoundException x) {
			x.printStackTrace();
		} catch (IllegalAccessException x) {
			x.printStackTrace();
		} catch (NoSuchMethodException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return h;
	}

	// TODO: Implement This Method
	public static boolean isHandRoyalFlush(Hand h, HandScore hs) {
		ArrayList<Card> kickers = new ArrayList<Card>();
		boolean isHandRoyalFlush = false;
		if ((h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == eRank.ACE)
				&& (h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()).geteRank() == eRank.TEN)
				&& (isHandStraightFlush(h, hs) == true)) {
			isHandRoyalFlush = true;
			hs.setHandStrength(eHandStrength.RoyalFlush);
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank());
		}
		return isHandRoyalFlush;
	}

	// TODO: Implement This Method
	public static boolean isHandStraightFlush(Hand h, HandScore hs) {
		boolean isHandStraightFlush = false;
		ArrayList<Card> kickers = new ArrayList<Card>();
		if ((isHandFlush(h, hs) == true) && (isHandStraight(h, hs) == true)) {

			isHandStraightFlush = true;
			hs.setHandStrength(eHandStrength.StraightFlush);
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank());
		}
		return isHandStraightFlush;
	}
	
	public static boolean isHandFiveOfAKind(Hand h, HandScore hs) {

		boolean isHandFiveOfAKind = false;

		ArrayList<Card> kickers = new ArrayList<Card>();
		
		if (isHandFourOfAKind(h,hs) == true && (h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == eRank.JOKER)){
			isHandFiveOfAKind = true;
		}
		if (isHandThreeOfAKind(h,hs) == true && (h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank() == eRank.JOKER)){
			isHandFiveOfAKind = true;
		}

		if (isHandFiveOfAKind) {
			hs.setHandStrength(eHandStrength.FiveOfAKind);
			hs.setLoHand(null);
			hs.setKickers(kickers);
		}

		return isHandFiveOfAKind;
	}
	



	// TODO: Implement This Method
	public static boolean isHandFourOfAKind(Hand h, HandScore hs) {
		boolean isHandFourOfAKind = false;

		ArrayList<Card> kickers = new ArrayList<Card>();
		if ((h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.FourthCard.getCardNo()).geteRank())) {
			isHandFourOfAKind = true;
			hs.getKickers().add(h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()));
			hs.setHandStrength(eHandStrength.FourOfAKind);
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank());
		} else if ((h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.FifthCard.getCardNo()).geteRank())) {
			isHandFourOfAKind = true;
			hs.getKickers().add(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()));
			hs.setHandStrength(eHandStrength.FourOfAKind);
			hs.setHiHand(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank());
		}
		return isHandFourOfAKind;
	}

	// TODO: Implement This Method
	public static boolean isHandFlush(Hand h, HandScore hs) {
		boolean isHandFlush = false;

		ArrayList<Card> kickers = new ArrayList<Card>();
		if ((h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteSuit() == h.getCardsInHand()
				.get(eCardNo.SecondCard.getCardNo()).geteSuit())
				&& (h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteSuit() == h.getCardsInHand()
						.get(eCardNo.ThirdCard.getCardNo()).geteSuit())
				&& (h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteSuit() == h.getCardsInHand()
						.get(eCardNo.FourthCard.getCardNo()).geteSuit())
				&& (h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteSuit() == h.getCardsInHand()
						.get(eCardNo.FifthCard.getCardNo()).geteSuit())) {
			isHandFlush = true;
			hs.setHandStrength(eHandStrength.Flush);
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank());
		}
		return isHandFlush;
	}

	// TODO: Implement This Method
	public static boolean isHandStraight(Hand h, HandScore hs) {
		boolean isStraight = false;
		if (h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() != eRank.ACE) {
			if ((h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank().ordinal()
					- h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()).geteRank().ordinal()) == 4) {
				if (h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() != h.getCardsInHand()
						.get(eCardNo.SecondCard.getCardNo()).geteRank()
						&& h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank() != h.getCardsInHand()
								.get(eCardNo.ThirdCard.getCardNo()).geteRank()
						&& h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank() != h.getCardsInHand()
								.get(eCardNo.FourthCard.getCardNo()).geteRank()
						&& h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank() != h.getCardsInHand()
								.get(eCardNo.FifthCard.getCardNo()).geteRank()) {
					isStraight = true;
					hs.setHandStrength(eHandStrength.Straight);
				}
			}
		} else {
			if (h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank() == eRank.KING
					&& h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()).geteRank() == eRank.TEN
					&& h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() != h.getCardsInHand()
							.get(eCardNo.SecondCard.getCardNo()).geteRank()
					&& h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank() != h.getCardsInHand()
							.get(eCardNo.ThirdCard.getCardNo()).geteRank()
					&& h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank() != h.getCardsInHand()
							.get(eCardNo.FourthCard.getCardNo()).geteRank()
					&& h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank() != h.getCardsInHand()
							.get(eCardNo.FifthCard.getCardNo()).geteRank()) {
				isStraight = true;
				hs.setHandStrength(eHandStrength.Straight);

			} else if (h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank() == eRank.FIVE
					&& h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()).geteRank() == eRank.TWO
					&& h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() != h.getCardsInHand()
							.get(eCardNo.SecondCard.getCardNo()).geteRank()
					&& h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank() != h.getCardsInHand()
							.get(eCardNo.ThirdCard.getCardNo()).geteRank()
					&& h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank() != h.getCardsInHand()
							.get(eCardNo.FourthCard.getCardNo()).geteRank()
					&& h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank() != h.getCardsInHand()
							.get(eCardNo.FifthCard.getCardNo()).geteRank()) {
				isStraight = true;
				hs.setHandStrength(eHandStrength.Straight);

			}
		}
		/*
		 * get first card and last card, if they are 5 (diff of 4) apart and
		 * none of them are equal its a straight get first card and last card,
		 * if A is last card and first & 2nd to last card are 4 apart (diff of
		 * 3) and none are equal, its a stright
		 */
		return isStraight;
	}

	// TODO: Implement This Method
	public static boolean isHandThreeOfAKind(Hand h, HandScore hs) {

		boolean isHandThreeOfAKind = false;

		ArrayList<Card> kickers = new ArrayList<Card>();
		if ((h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.ThirdCard.getCardNo()).geteRank())) {
			isHandThreeOfAKind = true;
			hs.getKickers().add(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()));
			hs.getKickers().add(h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()));
			hs.setHandStrength(eHandStrength.ThreeOfAKind);
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank());
		} else if ((h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.FourthCard.getCardNo()).geteRank())) {
			isHandThreeOfAKind = true;
			hs.getKickers().add(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()));
			hs.getKickers().add(h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()));
			hs.setHandStrength(eHandStrength.ThreeOfAKind);
			hs.setHiHand(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank());
		} else if ((h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.FifthCard.getCardNo()).geteRank())) {
			isHandThreeOfAKind = true;
			hs.getKickers().add(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()));
			hs.getKickers().add(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()));
			hs.setHandStrength(eHandStrength.ThreeOfAKind);
			hs.setHiHand(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank());

		}
		return isHandThreeOfAKind;
	}

	// TODO: Implement This Method
	public static boolean isHandTwoPair(Hand h, HandScore hs) {
		boolean isTwoPair = false;

		ArrayList<Card> kickers = new ArrayList<Card>();
		if ((h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.SecondCard.getCardNo()).geteRank())
				&& (h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank() == h.getCardsInHand()
						.get(eCardNo.FourthCard.getCardNo()).geteRank())) {
			isTwoPair = true;
			hs.getKickers().add(h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()));
			hs.setHandStrength(eHandStrength.TwoPair);
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank());
			hs.setLoHand(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank());
		} else if ((h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.SecondCard.getCardNo()).geteRank())
				&& (h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank() == h.getCardsInHand()
						.get(eCardNo.FifthCard.getCardNo()).geteRank())) {
			isTwoPair = true;
			hs.getKickers().add(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()));
			hs.setHandStrength(eHandStrength.TwoPair);
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank());
			hs.setLoHand(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank());
		} else if ((h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.ThirdCard.getCardNo()).geteRank())
				&& (h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank() == h.getCardsInHand()
						.get(eCardNo.FifthCard.getCardNo()).geteRank())) {
			isTwoPair = true;
			hs.getKickers().add(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()));
			hs.setHandStrength(eHandStrength.TwoPair);
			hs.setHiHand(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank());
			hs.setLoHand(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank());
		}
		return isTwoPair;
	}

	// TODO: Implement This Method
	public static boolean isHandPair(Hand h, HandScore hs) {
		boolean isPair = false;

		ArrayList<Card> kickers = new ArrayList<Card>();
		if ((h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.SecondCard.getCardNo()).geteRank())) {
			isPair = true;
			hs.setHandStrength(eHandStrength.Pair);
			hs.getKickers().add(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()));
			hs.getKickers().add(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()));
			hs.getKickers().add(h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()));
			hs.setHiHand(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank());
			hs.setLoHand(null);
		} else if ((h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.ThirdCard.getCardNo()).geteRank())) {
			isPair = true;
			hs.setHandStrength(eHandStrength.Pair);
			hs.getKickers().add(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()));
			hs.getKickers().add(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()));
			hs.getKickers().add(h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()));
			hs.setHiHand(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank());
			hs.setLoHand(null);
		} else if ((h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.FourthCard.getCardNo()).geteRank())) {
			isPair = true;
			hs.setHandStrength(eHandStrength.Pair);
			hs.getKickers().add(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()));
			hs.getKickers().add(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()));
			hs.getKickers().add(h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()));
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank());
			hs.setLoHand(null);
		} else if ((h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.FifthCard.getCardNo()).geteRank())) {
			isPair = true;
			hs.getKickers().add(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()));
			hs.getKickers().add(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()));
			hs.getKickers().add(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()));
			hs.setHandStrength(eHandStrength.Pair);
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()).geteRank());
			hs.setLoHand(null);
		}

		return isPair;

	}

	// TODO: Implement This Method
	public static boolean isHandHighCard(Hand h, HandScore hs) {
		boolean isHighCard = false;
		if (Hand.isHandRoyalFlush(h, hs) == false && Hand.isHandStraightFlush(h, hs) == false
				&& Hand.isHandFourOfAKind(h, hs) == false && Hand.isHandFullHouse(h, hs) == false
				&& Hand.isHandFlush(h, hs) == false && Hand.isHandStraight(h, hs) == false
				&& Hand.isHandThreeOfAKind(h, hs) == false && Hand.isHandTwoPair(h, hs) == false
				&& Hand.isHandPair(h, hs) == false && Hand.isAcesAndEights(h, hs) == false) {
			isHighCard = true;
		}
		return isHighCard;
	}

	// TODO: Implement This Method
	public static boolean isAcesAndEights(Hand h, HandScore hs) {
		boolean isAcesAndAces = false;
		if (Hand.isHandTwoPair(h, hs)) {
			if ((hs.getHiHand() == eRank.ACE) && (hs.getLoHand() == eRank.EIGHT)) {
				isAcesAndAces = true;
			} else if ((hs.getHiHand() == eRank.EIGHT) && (hs.getLoHand() == eRank.ACE)) {
				isAcesAndAces = true;
			}
		}
		return isAcesAndAces;
	}

	public static boolean isHandFullHouse(Hand h, HandScore hs) {

		boolean isFullHouse = false;

		ArrayList<Card> kickers = new ArrayList<Card>();
		if ((h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.ThirdCard.getCardNo()).geteRank())
				&& (h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank() == h.getCardsInHand()
						.get(eCardNo.FifthCard.getCardNo()).geteRank())) {
			isFullHouse = true;
			hs.setHandStrength(eHandStrength.FullHouse);
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank());
			hs.setLoHand(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank());
		} else if ((h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.SecondCard.getCardNo()).geteRank())
				&& (h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank() == h.getCardsInHand()
						.get(eCardNo.FifthCard.getCardNo()).geteRank())) {
			isFullHouse = true;
			hs.setHandStrength(eHandStrength.FullHouse);
			hs.setHiHand(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank());
			hs.setLoHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank());
		}

		return isFullHouse;

	}
}
