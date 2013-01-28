package armstrong.sa.pokerHandEvaluator;

import java.util.Iterator;
import java.util.ArrayList;
import static java.util.Arrays.asList;
import java.util.Collections;

import armstrong.sa.pokerHandEvaluator.Card.Rank;
import armstrong.sa.pokerHandEvaluator.Card.Suit;
import armstrong.sa.pokerHandEvaluator.Hand.HandRank;
import armstrong.sa.pokerHandEvaluator.HandEvaluator;
import static armstrong.sa.pokerHandEvaluator.CardsMatcher.matchesCards;

import org.junit.Test;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.BaseMatcher;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class HandTest {

	@Test
	public void testRemoveList() {
		Hand hand = new Hand(new ArrayList<Card>(asList(
				new Card (Rank.ACE, Suit.SPADES), 
				new Card (Rank.JACK, Suit.DIAMONDS),
				new Card (Rank.JACK, Suit.SPADES), 
				new Card (Rank.KING, Suit.DIAMONDS),
				new Card (Rank.THREE, Suit.CLUBS), 
				new Card (Rank.TWO, Suit.CLUBS),
				new Card (Rank.QUEEN, Suit.HEARTS)
				)));
		
		hand.remove(asList(
				new Card (Rank.THREE, Suit.CLUBS), 
				new Card (Rank.JACK, Suit.DIAMONDS)
				));
		
		Hand handExpected = new Hand(new ArrayList<Card>(asList(
				new Card (Rank.ACE, Suit.SPADES), 
				new Card (Rank.JACK, Suit.SPADES), 
				new Card (Rank.KING, Suit.DIAMONDS),
				new Card (Rank.TWO, Suit.CLUBS),
				new Card (Rank.QUEEN, Suit.HEARTS)
				)));
		
		assertThat(hand.cards, matchesCards(handExpected.cards));
	}
	
}
