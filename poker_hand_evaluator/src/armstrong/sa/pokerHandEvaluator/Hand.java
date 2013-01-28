package armstrong.sa.pokerHandEvaluator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Iterator;

public class Hand implements Iterable, Cloneable {

	public enum HandRank {
		STRAIGHT_FLUSH, FOUR_OF_A_KIND, FULL_HOUSE, FLUSH, 
		STRAIGHT, THREE_OF_A_KIND, TWO_PAIRS, PAIR, HIGH_CARD
	}
	
	public ArrayList<Card> cards;
	
	public Hand (ArrayList<Card> cards) {
		this.cards = cards;
	}
	
	public Hand () {
		this.cards = new ArrayList<Card>();
	}
	
	public void add (Card card) {
		cards.add(card);
	}
	
	public void remove (Card card) {
		cards.remove(card);
	}
	
	public void remove (List<Card> cardsToRemove) {
		Iterator<Card> cardIter = cardsToRemove.iterator();
		while (cardIter.hasNext()) {
			Card cardToRemove = cardIter.next();
			this.cards.remove(cardToRemove);
		}
	}
	
	public void remove (Card.Rank rankOfCardsToRemove) {
		Iterator<Card> cardIter = this.cards.iterator();
		while (cardIter.hasNext()) {
			Card card = cardIter.next();
			if (card.rank == rankOfCardsToRemove) {
				//removing from the iterator removes from the underlying collection
				cardIter.remove();
			}
		}
	}
	
	public void remove (Card.Suit suitOfCardsToRemove) {
		Iterator<Card> cardIter = this.cards.iterator();
		while (cardIter.hasNext()) {
			Card card = cardIter.next();
			if (card.suit == suitOfCardsToRemove) {
				//removing from the iterator removes from the underlying collection
				cardIter.remove();
			}
		}
	}
	
	protected Hand clone() {
		Iterator<Card> cardsIter = this.cards.iterator();
		Hand handClone = new Hand();
		while (cardsIter.hasNext()) {
			Card card = cardsIter.next();
			handClone.add(new Card(card.rank, card.suit));
		}
		
		return handClone;
	}
	
	public Iterator<Card> iterator() {
		Iterator<Card> cardIterator = cards.iterator();
		return cardIterator;
	}
	
}
