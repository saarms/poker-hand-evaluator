package armstrong.sa.pokerHandEvaluator;

import java.util.Iterator;
import java.util.ArrayList;
import static java.util.Arrays.asList;
import java.util.Collections;

import static armstrong.sa.pokerHandEvaluator.TestHelper.getCurrentMethodName;
import armstrong.sa.pokerHandEvaluator.Card.Rank;
import armstrong.sa.pokerHandEvaluator.Card.Suit;
import armstrong.sa.pokerHandEvaluator.Hand.HandRank;
import armstrong.sa.pokerHandEvaluator.HandEvaluator;
import static armstrong.sa.pokerHandEvaluator.CardsMatcher.matchesCards;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class HandEvaluationTest {
	
	
	// Can use this to test the test. 
	// TODO: Comment out when I have used more samples and know all my tests work.
	public static Evaluation testThreeOfAKindHand () {
		Evaluation evaluation = new Evaluation();
		
		ArrayList<Card> rankedCards = new ArrayList<Card>(asList(
				new Card (Rank.KING, Suit.DIAMONDS), 
				new Card (Rank.KING, Suit.CLUBS), 
				new Card (Rank.KING, Suit.SPADES)
				));  
		
		ArrayList<Card> kickerCardOption1 = new ArrayList<Card>(asList(
				new Card (Rank.ACE, Suit.SPADES)
				));
		
		ArrayList<Card> unusedCardsOption1 = new ArrayList<Card>(asList(
				new Card (Rank.ACE, Suit.DIAMONDS), 
				new Card (Rank.QUEEN, Suit.HEARTS)
				));
		
		evaluation.setHandRank(HandRank.THREE_OF_A_KIND);
		evaluation.setRankedCards(rankedCards);
		evaluation.setKickerCards(kickerCardOption1);
		evaluation.setUnusedCards(unusedCardsOption1);
				
		return evaluation;
	}
	
	@Test
	public void evaluateHandsStraightFlush() {
		System.out.println("TEST: " + getCurrentMethodName());
		ArrayList<Card> cards = new ArrayList<Card>(asList(
				new Card (Rank.ACE, Suit.SPADES), 
				new Card (Rank.KING, Suit.DIAMONDS), 
				new Card (Rank.QUEEN, Suit.DIAMONDS), 
				new Card (Rank.JACK, Suit.DIAMONDS), 
				new Card (Rank.TEN, Suit.DIAMONDS),
				new Card (Rank.NINE, Suit.DIAMONDS), 
				new Card (Rank.KING, Suit.SPADES)
				));
		
		Hand hand = new Hand (cards);
		Evaluation evaluation = HandEvaluator.evaluateHand(hand);
		
		ArrayList<Card> expectedRankedCards = new ArrayList<Card>(asList(
				new Card (Rank.KING, Suit.DIAMONDS), 
				new Card (Rank.QUEEN, Suit.DIAMONDS), 
				new Card (Rank.JACK, Suit.DIAMONDS), 
				new Card (Rank.TEN, Suit.DIAMONDS),
				new Card (Rank.NINE, Suit.DIAMONDS)
				));
		ArrayList<Card> expectedKickerCards = new ArrayList<Card>();
		ArrayList<Card> expectedUnusedCards = new ArrayList<Card>(asList(
				new Card (Rank.ACE, Suit.SPADES),
				new Card (Rank.KING, Suit.SPADES)
				));
				
		//Hamcrest tests
		assertThat(evaluation.getHandRank(), equalTo(HandRank.STRAIGHT_FLUSH));
		assertThat(evaluation.getRankedCards(), matchesCards(expectedRankedCards));
//		assertThat(evaluation.getKickerCards(), matchesCards(expectedKickerCards));
//		assertThat(evaluation.getUnusedCards(), matchesCards(expectedUnusedCards));
	}
	
	@Test
	public void evaluateHandsFourOfAKind() {
		System.out.println("TEST: " + getCurrentMethodName());
		ArrayList<Card> cards = new ArrayList<Card>(asList(
				new Card (Rank.ACE, Suit.SPADES), 
				new Card (Rank.ACE, Suit.DIAMONDS), 
				new Card (Rank.ACE, Suit.CLUBS), 
				new Card (Rank.KING, Suit.SPADES), 
				new Card (Rank.KING, Suit.DIAMONDS),
				new Card (Rank.KING, Suit.CLUBS), 
				new Card (Rank.KING, Suit.HEARTS)
				));
		
		Hand hand = new Hand (cards);
		Evaluation evaluation = HandEvaluator.evaluateHand(hand);
		
		ArrayList<Card> expectedRankedCards = new ArrayList<Card>(asList(
				new Card (Rank.KING, Suit.DIAMONDS), 
				new Card (Rank.KING, Suit.CLUBS), 
				new Card (Rank.KING, Suit.HEARTS), 
				new Card (Rank.KING, Suit.SPADES)
				));
		//TO DO: Figure out how to allow for any of the aces to be kicker.
		// 		 For now just use the "highest" ordinal Suit enum.  
		ArrayList<Card> expectedKickerCards = new ArrayList<Card>(asList(
				new Card (Rank.ACE, Suit.SPADES) 
				));
		ArrayList<Card> expectedUnusedCards = new ArrayList<Card>(asList(
				new Card (Rank.ACE, Suit.DIAMONDS), 
				new Card (Rank.ACE, Suit.CLUBS)
				));
		
		//Hamcrest tests
		assertThat(evaluation.getHandRank(), equalTo(HandRank.FOUR_OF_A_KIND));
		assertThat(evaluation.getRankedCards(), matchesCards(expectedRankedCards));
		//TO DO: Figure out how to allow for any of the aces to be kicker.
		// 		 For now just use the "highest" ordinal Suit enum.  
		assertThat(evaluation.getKickerCards(), matchesCards(expectedKickerCards));
		assertThat(evaluation.getUnusedCards(), matchesCards(expectedUnusedCards));
	}
	
	@Test
	public void evaluateHandsFullHouse() {
		System.out.println("TEST: " + getCurrentMethodName());
		
		ArrayList<Card> cards = new ArrayList<Card>(asList(
				new Card (Rank.ACE, Suit.SPADES), 
				new Card (Rank.ACE, Suit.DIAMONDS),
				new Card (Rank.KING, Suit.SPADES), 
				new Card (Rank.KING, Suit.DIAMONDS),
				new Card (Rank.KING, Suit.CLUBS), 
				new Card (Rank.QUEEN, Suit.HEARTS), 
				new Card (Rank.TWO, Suit.HEARTS)
				));
		
		Evaluation evaluation = HandEvaluator.evaluateHand(new Hand(cards));
		//Evaluation evaluation = testThreeOfAKindHand();

		// Expected hand
		ArrayList<Card> expectedRankedCards = new ArrayList<Card>(asList(
				new Card (Rank.KING, Suit.CLUBS),
				new Card (Rank.KING, Suit.SPADES),
				new Card (Rank.KING, Suit.DIAMONDS),
				new Card (Rank.ACE, Suit.DIAMONDS),
				new Card (Rank.ACE, Suit.SPADES)
				));
		ArrayList<Card> expectedKickerCards = new ArrayList<Card>();
		ArrayList<Card> expectedUnusedCards = new ArrayList<Card>(asList(
				new Card (Rank.QUEEN, Suit.HEARTS), 
				new Card (Rank.TWO, Suit.HEARTS)
				));
		
		//Hamcrest tests
		assertThat(evaluation.getHandRank(), equalTo(HandRank.FULL_HOUSE));
		assertThat(evaluation.getRankedCards(), matchesCards(expectedRankedCards));
		assertThat(evaluation.getKickerCards(), matchesCards(expectedKickerCards));
		assertThat(evaluation.getUnusedCards(), matchesCards(expectedUnusedCards));
				
	}
	
	@Test
	public void evaluateHandsFlush() {
		System.out.println("TEST: " + getCurrentMethodName());
		
		ArrayList<Card> cards = new ArrayList<Card>(asList(
				new Card (Rank.ACE, Suit.SPADES), 
				new Card (Rank.ACE, Suit.DIAMONDS),
				new Card (Rank.KING, Suit.SPADES), 
				new Card (Rank.KING, Suit.DIAMONDS),
				new Card (Rank.FIVE, Suit.DIAMONDS), 
				new Card (Rank.QUEEN, Suit.DIAMONDS), 
				new Card (Rank.TWO, Suit.DIAMONDS)
				));
		
		Evaluation evaluation = HandEvaluator.evaluateHand(new Hand(cards));
		//Evaluation evaluation = testThreeOfAKindHand();

		// Expected hand
		ArrayList<Card> expectedRankedCards = new ArrayList<Card>(asList(
				new Card (Rank.TWO, Suit.DIAMONDS),
				new Card (Rank.FIVE, Suit.DIAMONDS),
				new Card (Rank.KING, Suit.DIAMONDS),
				new Card (Rank.ACE, Suit.DIAMONDS),
				new Card (Rank.QUEEN, Suit.DIAMONDS)
				));
		ArrayList<Card> expectedKickerCards = new ArrayList<Card>();
		ArrayList<Card> expectedUnusedCards = new ArrayList<Card>(asList(
				new Card (Rank.ACE, Suit.SPADES)
				));
		
		//Hamcrest tests
		assertThat(evaluation.getHandRank(), equalTo(HandRank.FLUSH));
		assertThat(evaluation.getRankedCards(), matchesCards(expectedRankedCards));
//		assertThat(evaluation.getKickerCards(), matchesCards(expectedKickerCards));
//		assertThat(evaluation.getUnusedCards(), matchesCards(expectedUnusedCards));
				
	}
	
	@Test
	public void evaluateHandsStraight() {
		System.out.println("TEST: " + getCurrentMethodName());
		ArrayList<Card> cards = new ArrayList<Card>(asList(
				new Card (Rank.ACE, Suit.SPADES), 
				new Card (Rank.KING, Suit.DIAMONDS), 
				new Card (Rank.QUEEN, Suit.HEARTS), 
				new Card (Rank.JACK, Suit.DIAMONDS), 
				new Card (Rank.TEN, Suit.DIAMONDS),
				new Card (Rank.NINE, Suit.DIAMONDS), 
				new Card (Rank.KING, Suit.SPADES)
				));
		
		Hand hand = new Hand (cards);
		Evaluation evaluation = HandEvaluator.evaluateHand(hand);
		
		ArrayList<Card> expectedRankedCards = new ArrayList<Card>(asList(
				new Card (Rank.KING, Suit.SPADES), 
				new Card (Rank.QUEEN, Suit.HEARTS), 
				new Card (Rank.JACK, Suit.DIAMONDS), 
				new Card (Rank.TEN, Suit.DIAMONDS),
				new Card (Rank.NINE, Suit.DIAMONDS)
				));
		ArrayList<Card> expectedKickerCards = new ArrayList<Card>();
		ArrayList<Card> expectedUnusedCards = new ArrayList<Card>(asList(
				new Card (Rank.ACE, Suit.SPADES),
				new Card (Rank.KING, Suit.DIAMONDS)
				));
				
		//Hamcrest tests
		assertThat(evaluation.getHandRank(), equalTo(HandRank.STRAIGHT));
		assertThat(evaluation.getRankedCards(), matchesCards(expectedRankedCards));
//		assertThat(evaluation.getKickerCards(), matchesCards(expectedKickerCards));
//		assertThat(evaluation.getUnusedCards(), matchesCards(expectedUnusedCards));
	}
	
	@Test
	public void evaluateHandsThreeOfAKind() {
		System.out.println("TEST: " + getCurrentMethodName());
		
		ArrayList<Card> cards = new ArrayList<Card>(asList(
				new Card (Rank.ACE, Suit.SPADES), 
				new Card (Rank.JACK, Suit.DIAMONDS),
				new Card (Rank.KING, Suit.SPADES), 
				new Card (Rank.KING, Suit.DIAMONDS),
				new Card (Rank.KING, Suit.CLUBS), 
				new Card (Rank.QUEEN, Suit.HEARTS)
				));
		
		Evaluation evaluation = HandEvaluator.evaluateHand(new Hand(cards));
		//Evaluation evaluation = testThreeOfAKindHand();

		// Expected hand
		ArrayList<Card> expectedRankedCards = new ArrayList<Card>(asList(
				new Card (Rank.KING, Suit.CLUBS),
				new Card (Rank.KING, Suit.SPADES),
				new Card (Rank.KING, Suit.DIAMONDS)
				));
		ArrayList<Card> expectedKickerCards = new ArrayList<Card>(asList(
				new Card (Rank.ACE, Suit.SPADES)
				));
		ArrayList<Card> expectedUnusedCards = new ArrayList<Card>(asList(
				new Card (Rank.JACK, Suit.DIAMONDS),
				new Card (Rank.QUEEN, Suit.HEARTS)
				));
		
		//Hamcrest tests
		assertThat(evaluation.getHandRank(), equalTo(HandRank.THREE_OF_A_KIND));
		assertThat(evaluation.getRankedCards(), matchesCards(expectedRankedCards));
//		assertThat(evaluation.getKickerCards(), matchesCards(expectedKickerCards));
//		assertThat(evaluation.getUnusedCards(), matchesCards(expectedUnusedCards));
				
	}
	
	@Test
	public void evaluateHandsTwoPairs() {
		System.out.println("TEST: " + getCurrentMethodName());
		
		ArrayList<Card> cards = new ArrayList<Card>(asList(
				new Card (Rank.ACE, Suit.SPADES), 
				new Card (Rank.JACK, Suit.DIAMONDS),
				new Card (Rank.JACK, Suit.SPADES), 
				new Card (Rank.KING, Suit.DIAMONDS),
				new Card (Rank.KING, Suit.CLUBS), 
				new Card (Rank.TWO, Suit.DIAMONDS),
				new Card (Rank.TWO, Suit.HEARTS)
				));
		
		Evaluation evaluation = HandEvaluator.evaluateHand(new Hand(cards));
		//Evaluation evaluation = testThreeOfAKindHand();

		// Expected hand
		ArrayList<Card> expectedRankedCards = new ArrayList<Card>(asList(
				new Card (Rank.KING, Suit.CLUBS),
				new Card (Rank.JACK, Suit.SPADES),
				new Card (Rank.JACK, Suit.DIAMONDS),
				new Card (Rank.KING, Suit.DIAMONDS)
				));
		ArrayList<Card> expectedKickerCards = new ArrayList<Card>(asList(
				new Card (Rank.ACE, Suit.SPADES)
				));
		ArrayList<Card> expectedUnusedCards = new ArrayList<Card>(asList(
				new Card (Rank.TWO, Suit.DIAMONDS),
				new Card (Rank.TWO, Suit.HEARTS)
				));
		
		//Hamcrest tests
		assertThat(evaluation.getHandRank(), equalTo(HandRank.TWO_PAIRS));
		assertThat(evaluation.getRankedCards(), matchesCards(expectedRankedCards));
		assertThat(evaluation.getKickerCards(), matchesCards(expectedKickerCards));
		assertThat(evaluation.getUnusedCards(), matchesCards(expectedUnusedCards));
				
	}
	
	@Test
	public void evaluateHandsPair() {
		System.out.println("TEST: " + getCurrentMethodName());
		
		ArrayList<Card> cards = new ArrayList<Card>(asList(
				new Card (Rank.ACE, Suit.SPADES), 
				new Card (Rank.JACK, Suit.DIAMONDS),
				new Card (Rank.JACK, Suit.SPADES), 
				new Card (Rank.KING, Suit.DIAMONDS),
				new Card (Rank.THREE, Suit.CLUBS), 
				new Card (Rank.TWO, Suit.DIAMONDS),
				new Card (Rank.QUEEN, Suit.HEARTS)
				));
		
		Evaluation evaluation = HandEvaluator.evaluateHand(new Hand(cards));
		//Evaluation evaluation = testThreeOfAKindHand();

		// Expected hand
		ArrayList<Card> expectedRankedCards = new ArrayList<Card>(asList(
				new Card (Rank.JACK, Suit.SPADES),
				new Card (Rank.JACK, Suit.DIAMONDS)
				));
		ArrayList<Card> expectedKickerCards = new ArrayList<Card>(asList(
				new Card (Rank.ACE, Suit.SPADES),
				new Card (Rank.KING, Suit.DIAMONDS),
				new Card (Rank.QUEEN, Suit.HEARTS)
				));
		ArrayList<Card> expectedUnusedCards = new ArrayList<Card>(asList(
				new Card (Rank.THREE, Suit.CLUBS),
				new Card (Rank.TWO, Suit.DIAMONDS)
				));
		
		//Hamcrest tests
		assertThat(evaluation.getHandRank(), equalTo(HandRank.PAIR));
		assertThat(evaluation.getRankedCards(), matchesCards(expectedRankedCards));
		assertThat(evaluation.getKickerCards(), matchesCards(expectedKickerCards));
		assertThat(evaluation.getUnusedCards(), matchesCards(expectedUnusedCards));
				
	}
	
	@Test
	public void evaluateHandsHighCard() {
		System.out.println("TEST: " + getCurrentMethodName());
		
		ArrayList<Card> cards = new ArrayList<Card>(asList(
				new Card (Rank.THREE, Suit.SPADES), 
				new Card (Rank.JACK, Suit.DIAMONDS),
				new Card (Rank.SIX, Suit.SPADES), 
				new Card (Rank.KING, Suit.DIAMONDS),
				new Card (Rank.NINE, Suit.CLUBS), 
				new Card (Rank.TWO, Suit.DIAMONDS),
				new Card (Rank.QUEEN, Suit.HEARTS)
				));
		
		Evaluation evaluation = HandEvaluator.evaluateHand(new Hand(cards));
		//Evaluation evaluation = testThreeOfAKindHand();

		// Expected hand
		ArrayList<Card> expectedRankedCards = new ArrayList<Card>(asList(
				new Card (Rank.KING, Suit.DIAMONDS)
				));
		ArrayList<Card> expectedKickerCards = new ArrayList<Card>(asList(
				new Card (Rank.QUEEN, Suit.HEARTS),
				new Card (Rank.JACK, Suit.DIAMONDS),
				new Card (Rank.NINE, Suit.CLUBS),
				new Card (Rank.SIX, Suit.SPADES)
				));
		ArrayList<Card> expectedUnusedCards = new ArrayList<Card>(asList(
				new Card (Rank.THREE, Suit.SPADES),
				new Card (Rank.TWO, Suit.DIAMONDS)
				));
		
		//Hamcrest tests
		assertThat(evaluation.getHandRank(), equalTo(HandRank.HIGH_CARD));
		assertThat(evaluation.getRankedCards(), matchesCards(expectedRankedCards));
		assertThat(evaluation.getKickerCards(), matchesCards(expectedKickerCards));
		assertThat(evaluation.getUnusedCards(), matchesCards(expectedUnusedCards));
				
	}
	
}
