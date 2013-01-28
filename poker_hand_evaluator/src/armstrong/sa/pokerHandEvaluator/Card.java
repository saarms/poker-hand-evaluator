package armstrong.sa.pokerHandEvaluator;

import java.util.Comparator;

public class Card implements Comparable<Card>{

	public enum Rank {
		TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT,
		NINE, TEN, JACK, QUEEN, KING, ACE
	};
	
	public enum Suit {
		CLUBS, DIAMONDS, HEARTS, SPADES		
	};
	
	public Rank rank;
	public Suit suit;
	
	public Card (Rank rank, Suit suit) {
		this.rank = rank;
		this.suit = suit;
	}
	
	@Override
	public String toString() {
		return rank + ":" + suit;
	}
	
	@Override
	public int compareTo(Card cardY) {
		return compareForListMatching(this, cardY);
	}
	
	public static int compareForListMatching(Card cardX, Card cardY) {
		// This is strictly for ordering purposes to help compare lists.
		// Obviously cards of the same rank and different suit have the same value.
		// TODO: Handle null cards
		if (cardX.rank.ordinal() < cardY.rank.ordinal()) {
			// Ranks are ordered low card (Two = 0) to high card (Ace = 13). 
			// Here cardY is higher card.
			return 1;
		} else if (cardX.rank.ordinal() == cardY.rank.ordinal()) {
			// cardX and cardY have same rank. 
			return cardX.suit.ordinal() > cardY.suit.ordinal() ? -1
					: cardX.suit.ordinal() < cardY.suit.ordinal() ? 1
							: 0;
		} else // cardY has lower rank than cardX
			return -1;
	}
	
}
