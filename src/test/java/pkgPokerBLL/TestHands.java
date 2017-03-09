package pkgPokerBLL;

import static org.junit.Assert.*;

import org.junit.Test;

import pkgPokerEnum.eHandStrength;
import pkgPokerEnum.eRank;
import pkgPokerEnum.eSuit;

public class TestHands {

	@Test
	public void TestFullHouse() {

		Hand h = new Hand();
		h.AddCardToHand(new Card(eRank.THREE, eSuit.CLUBS));
		h.AddCardToHand(new Card(eRank.THREE, eSuit.DIAMONDS));
		h.AddCardToHand(new Card(eRank.FOUR, eSuit.CLUBS));
		h.AddCardToHand(new Card(eRank.FOUR, eSuit.DIAMONDS));
		h.AddCardToHand(new Card(eRank.FOUR, eSuit.SPADES));
		h.EvaluateHand();

		// Hand better be a full house
		assertEquals(eHandStrength.FullHouse.getHandStrength(), h.getHandScore().getHandStrength().getHandStrength());

		// HI hand better be 'Four'
		assertEquals(eRank.FOUR.getiRankNbr(), h.getHandScore().getHiHand().getiRankNbr());

		// LO hand better be 'Three'
		assertEquals(eRank.THREE.getiRankNbr(), h.getHandScore().getLoHand().getiRankNbr());

		// Full House has no kickers.
		assertEquals(0, h.getHandScore().getKickers().size());

	}

	@Test
	public void TestPair() {

		Hand h = new Hand();
		h.AddCardToHand(new Card(eRank.TWO, eSuit.CLUBS));
		h.AddCardToHand(new Card(eRank.TWO, eSuit.DIAMONDS));
		h.AddCardToHand(new Card(eRank.FIVE, eSuit.CLUBS));
		h.AddCardToHand(new Card(eRank.EIGHT, eSuit.DIAMONDS));
		h.AddCardToHand(new Card(eRank.TEN, eSuit.SPADES));
		h.EvaluateHand();

		// Pair
		assertEquals(eHandStrength.Pair.getHandStrength(), h.getHandScore().getHandStrength().getHandStrength());

		// HI Hand
		assertEquals(eRank.TWO.getiRankNbr(), h.getHandScore().getHiHand().getiRankNbr());
		// Lo Hand *No Lo Hand... set to null
		assertEquals(null, h.getHandScore().getLoHand());

		// Kickers
		assertEquals(3, h.getHandScore().getKickers().size());

	}

	@Test
	public void TestRoyalFlush() {

		Hand h = new Hand();
		h.AddCardToHand(new Card(eRank.ACE, eSuit.SPADES));
		h.AddCardToHand(new Card(eRank.KING, eSuit.SPADES));
		h.AddCardToHand(new Card(eRank.QUEEN, eSuit.SPADES));
		h.AddCardToHand(new Card(eRank.JACK, eSuit.SPADES));
		h.AddCardToHand(new Card(eRank.TEN, eSuit.SPADES));
		h.EvaluateHand();

		assertEquals(eHandStrength.RoyalFlush.getHandStrength(), h.getHandScore().getHandStrength().getHandStrength());

		assertEquals(eRank.ACE.getiRankNbr(), h.getHandScore().getHiHand().getiRankNbr());

		assertEquals(0, h.getHandScore().getKickers().size());

	}

	@Test
	public void TestStraightFlush() {

		Hand h = new Hand();
		h.AddCardToHand(new Card(eRank.NINE, eSuit.SPADES));
		h.AddCardToHand(new Card(eRank.KING, eSuit.SPADES));
		h.AddCardToHand(new Card(eRank.QUEEN, eSuit.SPADES));
		h.AddCardToHand(new Card(eRank.JACK, eSuit.SPADES));
		h.AddCardToHand(new Card(eRank.TEN, eSuit.SPADES));
		h.EvaluateHand();

		assertEquals(eHandStrength.StraightFlush.getHandStrength(),
				h.getHandScore().getHandStrength().getHandStrength());

		assertEquals(eRank.KING.getiRankNbr(), h.getHandScore().getHiHand().getiRankNbr());

		assertEquals(0, h.getHandScore().getKickers().size());
	}

	@Test
	public void TestFlush() {

		Hand h = new Hand();
		h.AddCardToHand(new Card(eRank.EIGHT, eSuit.SPADES));
		h.AddCardToHand(new Card(eRank.KING, eSuit.SPADES));
		h.AddCardToHand(new Card(eRank.QUEEN, eSuit.SPADES));
		h.AddCardToHand(new Card(eRank.JACK, eSuit.SPADES));
		h.AddCardToHand(new Card(eRank.TEN, eSuit.SPADES));
		h.EvaluateHand();

		assertEquals(eHandStrength.Flush.getHandStrength(), h.getHandScore().getHandStrength().getHandStrength());

		assertEquals(eRank.KING.getiRankNbr(), h.getHandScore().getHiHand().getiRankNbr());

		assertEquals(0, h.getHandScore().getKickers().size());
	}

	@Test
	public void TestStraight() {

		Hand h = new Hand();
		h.AddCardToHand(new Card(eRank.ACE, eSuit.SPADES));
		h.AddCardToHand(new Card(eRank.TWO, eSuit.HEARTS));
		h.AddCardToHand(new Card(eRank.THREE, eSuit.CLUBS));
		h.AddCardToHand(new Card(eRank.FOUR, eSuit.SPADES));
		h.AddCardToHand(new Card(eRank.FIVE, eSuit.DIAMONDS));
		h.EvaluateHand();

		assertEquals(eHandStrength.Straight.getHandStrength(), h.getHandScore().getHandStrength().getHandStrength());

	}

	@Test
	public void TestTwoPair() {

		Hand h = new Hand();
		h.AddCardToHand(new Card(eRank.NINE, eSuit.DIAMONDS));
		h.AddCardToHand(new Card(eRank.THREE, eSuit.SPADES));
		h.AddCardToHand(new Card(eRank.THREE, eSuit.HEARTS));
		h.AddCardToHand(new Card(eRank.FIVE, eSuit.DIAMONDS));
		h.AddCardToHand(new Card(eRank.FIVE, eSuit.SPADES));
		h.EvaluateHand();

		assertEquals(eHandStrength.TwoPair.getHandStrength(), h.getHandScore().getHandStrength().getHandStrength());
		// HI hand
		assertEquals(eRank.FIVE.getiRankNbr(), h.getHandScore().getHiHand().getiRankNbr());

		// LO hand
		assertEquals(eRank.THREE.getiRankNbr(), h.getHandScore().getLoHand().getiRankNbr());

		// kickers.
		assertEquals(2, h.getHandScore().getKickers().size());
	}

	@Test
	public void TestFourOfKind() {

		Hand h = new Hand();
		h.AddCardToHand(new Card(eRank.THREE, eSuit.DIAMONDS));
		h.AddCardToHand(new Card(eRank.THREE, eSuit.SPADES));
		h.AddCardToHand(new Card(eRank.THREE, eSuit.HEARTS));
		h.AddCardToHand(new Card(eRank.THREE, eSuit.CLUBS));
		h.AddCardToHand(new Card(eRank.TEN, eSuit.SPADES));
		h.EvaluateHand();

		assertEquals(eHandStrength.FourOfAKind.getHandStrength(), h.getHandScore().getHandStrength().getHandStrength());
		// HI hand
		assertEquals(eRank.THREE.getiRankNbr(), h.getHandScore().getHiHand().getiRankNbr());

		// kickers.
		assertEquals(1, h.getHandScore().getKickers().size());
	}

	@Test
	public void TestThreeOfKind() {

		Hand h = new Hand();
		h.AddCardToHand(new Card(eRank.THREE, eSuit.DIAMONDS));
		h.AddCardToHand(new Card(eRank.THREE, eSuit.SPADES));
		h.AddCardToHand(new Card(eRank.THREE, eSuit.HEARTS));
		h.AddCardToHand(new Card(eRank.FIVE, eSuit.CLUBS));
		h.AddCardToHand(new Card(eRank.TEN, eSuit.SPADES));
		h.EvaluateHand();

		assertEquals(eHandStrength.ThreeOfAKind.getHandStrength(),
				h.getHandScore().getHandStrength().getHandStrength());
		// HI hand
		assertEquals(eRank.THREE.getiRankNbr(), h.getHandScore().getHiHand().getiRankNbr());

		// kickers.
		assertEquals(2, h.getHandScore().getKickers().size());
	}

	/*
	 * @Test public void TestHighCard() {
	 * 
	 * Hand h = new Hand(); h.AddCardToHand(new Card(eRank.TWO,eSuit.DIAMONDS));
	 * h.AddCardToHand(new Card(eRank.FIVE, eSuit.SPADES)); h.AddCardToHand(new
	 * Card(eRank.EIGHT, eSuit.HEARTS)); h.AddCardToHand(new Card(eRank.TEN,
	 * eSuit.CLUBS)); h.AddCardToHand(new Card(eRank.QUEEN,eSuit.SPADES));
	 * h.EvaluateHand();
	 * 
	 * assertEquals(eHandStrength.HighCard.getHandStrength(),
	 * h.getHandScore().getHandStrength().getHandStrength());
	 * 
	 * //HI hand assertEquals(eRank.THREE.getiRankNbr(),
	 * h.getHandScore().getHiHand().getiRankNbr());
	 * 
	 * assertEquals(0,h.getHandScore().getKickers().size()); }
	 */

	@Test
	public void TestAcesAndEights() {

		Hand h = new Hand();
		h.AddCardToHand(new Card(eRank.EIGHT, eSuit.DIAMONDS));
		h.AddCardToHand(new Card(eRank.EIGHT, eSuit.SPADES));
		h.AddCardToHand(new Card(eRank.ACE, eSuit.HEARTS));
		h.AddCardToHand(new Card(eRank.ACE, eSuit.CLUBS));
		h.AddCardToHand(new Card(eRank.TEN, eSuit.SPADES));
		h.EvaluateHand();

		assertEquals(eHandStrength.AcesAndEights.getHandStrength(),
				h.getHandScore().getHandStrength().getHandStrength());
		// HI hand
		assertEquals(eRank.ACE.getiRankNbr(), h.getHandScore().getHiHand().getiRankNbr());

		// LO hand
		assertEquals(eRank.EIGHT.getiRankNbr(), h.getHandScore().getLoHand().getiRankNbr());

	}

}
