package pkgPokerBLL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

import Exception.DeckException;
import pkgPokerEnum.eRank;
import pkgPokerEnum.eSuit;

public class Deck {

	private UUID DeckID;
	private ArrayList<Card> DeckCards = new ArrayList<Card>();
	private int numJokers;

	public Deck() {
		// TODO: Implement This Constructor (no-arg Deck should build up a deck
		// with 52 cards)
		for (eSuit suit : eSuit.values()) {
			for (eRank rank : eRank.values()) {
				DeckCards.add(new Card(rank,suit));
			}
		}
		Collections.shuffle(DeckCards);

		// This method will do a for/each, returning each rank in the enum.
		for (eRank Rank : eRank.values()) {
			System.out.println(Rank.getiRankNbr());
		}
	}
	public Deck(int numJokers){
		this();
		this.numJokers=numJokers;
		for(int i=0;i<this.numJokers;i++){
			DeckCards.add(new Card(eRank.JOKER,eSuit.JOKER));
		}
		Collections.shuffle(DeckCards);
		
		
	}
	
	public Deck(int numJokers, ArrayList<eRank> wilds){
		this(numJokers);
		for (Card currCard : DeckCards) {
			for (eRank rank : wilds) {
				if(currCard.geteRank() == rank){
					currCard.geteRank().setWild(true);
				}
			}
		}
	}
	
	


	public Card DrawCard() throws DeckException{
		// TODO: Implement this method... should draw a card from the deck.
		if(DeckCards.size()==0){
			throw new DeckException(this);
		}
		
		Card card = DeckCards.remove(0);
		return card;
	}
}
