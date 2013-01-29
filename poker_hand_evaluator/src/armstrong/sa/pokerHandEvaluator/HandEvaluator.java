package armstrong.sa.pokerHandEvaluator;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;

//debug
import java.util.Set;

import java.util.List;
import java.util.Iterator;

import armstrong.sa.pokerHandEvaluator.Card.Rank;
import armstrong.sa.pokerHandEvaluator.Card.Suit;
import armstrong.sa.pokerHandEvaluator.Hand.HandRank;

public class HandEvaluator {

	private static Map<Card.Rank, List<Card>> cardsGroupedByRank;
	private static List<RankAndCount> rankAndCount;
	private static TreeMap<HandRank, Evaluation> possibleEvaluations;
	
	public static Evaluation evaluateHand (Hand hand) {
		
		// Initialize working hand. After we determine the highest hand rank, 
		// we can remove ranked cards and use this to determine kicker and
		// unused cards.
//		Hand workingHand = hand.clone();
		Hand workingHand = new Hand(hand.cards);
		
		Evaluation evaluation = new Evaluation();
		
		// Methodology: 
		// 1) Group and count cards by rank.
		// 2) Use rank count to find FOUR_OF_A_KIND, FULL_HOUSE, 
		//    THREE_OF_A_KIND, TWO_PAIRS, PAIR, or HIGH_CARD. 
		//    Save highest as possible evaluation and stop.
		// 3) Group and count cards by suit.
		// 4) Use suit count to find best FLUSH, save as possible evaluation.
		// 5) Use grouping to find best STRAIGHT, save as possible evaluation.
		// 6) If possible hand ranks include STRAIGHT and FLUSH, 
		//    check all straights for best STRAIGHT_FLUSH.
		// 7) Return best evaluation.
		
		cardsGroupedByRank = groupHandByRank(hand);
		
		// Printing out cards grouped by rank:
		//System.out.println("Cards grouped by rank: " + cardsGroupedByRank);
		
		// Get list of rank and their counts
		rankAndCount = countCardsByRank(cardsGroupedByRank);
		// Sort list, highest count to lowest, and if same count then by rank.				
		Collections.sort(rankAndCount);
		
		// Prepare list of best hand evaluations. TreeMap will always sort by the key.
		// HandRank is listed in order of best hand to worst.
		possibleEvaluations = new TreeMap<HandRank, Evaluation>();
		
		// Loop through the sorted rank-and-counts to pick out best hands.
		// Sort order is very important here, by high count then by rank.
		Iterator<RankAndCount> rankAndCountIter = rankAndCount.iterator();
		while (rankAndCountIter.hasNext()) {
			RankAndCount rac = rankAndCountIter.next();
//			System.out.println("  " + rac);
			switch (rac.count()) {
			case 4: 
				if (possibleEvaluations.isEmpty()) {
					System.out.println("  Found 4 of a kind. Possible Evals empty. Creating " + HandRank.FOUR_OF_A_KIND);
					Evaluation eval = new Evaluation(HandRank.FOUR_OF_A_KIND);
					List<Card> rankedCards = cardsGroupedByRank.get(rac.rank());
					System.out.println("  Using rankedCards: " + rankedCards);
					eval.setRankedCards(rankedCards);										
					possibleEvaluations.put(HandRank.FOUR_OF_A_KIND, eval);
				} // if not empty, there's already a higher set of four cards on here.				
				break;
			case 3:
				if (possibleEvaluations.isEmpty()) {
					System.out.println("  Found 3 of a kind. Possible Evals empty. Creating " + HandRank.THREE_OF_A_KIND);
					Evaluation eval = new Evaluation(HandRank.THREE_OF_A_KIND);
					List<Card> rankedCards = cardsGroupedByRank.get(rac.rank());
					System.out.println("  Using rankedCards: " + rankedCards);
					eval.setRankedCards(rankedCards);
					possibleEvaluations.put(HandRank.THREE_OF_A_KIND, eval);
				} else {
					// if not empty, there may be three of a kind on here which means full house.
					if (possibleEvaluations.containsKey(HandRank.THREE_OF_A_KIND) &&
							!possibleEvaluations.containsKey(HandRank.FULL_HOUSE)) {
						System.out.println("  Found 3 of a kind. Possible Evals already has THREE_OF_A_KIND. Creating " 
							+ HandRank.FULL_HOUSE);						
						Evaluation eval = helpCreateEvaluation(HandRank.FULL_HOUSE, HandRank.THREE_OF_A_KIND, rac.rank());
						possibleEvaluations.put(HandRank.FULL_HOUSE, eval);
					}
				}
				break;
			case 2:
				if (possibleEvaluations.isEmpty()) {
					System.out.println("  Found pair. Possible Evals empty. Creating " + HandRank.PAIR);
					Evaluation eval = new Evaluation(HandRank.PAIR);
					List<Card> rankedCards = cardsGroupedByRank.get(rac.rank());
					System.out.println("  Using rankedCards: " + rankedCards);
					eval.setRankedCards(rankedCards);
					possibleEvaluations.put(HandRank.PAIR, eval);
				} else {
					// if not empty, there may be three of a kind on here which means full house
					// or there may be another pair which means two pairs.
					if (possibleEvaluations.containsKey(HandRank.THREE_OF_A_KIND) &&
							!possibleEvaluations.containsKey(HandRank.FULL_HOUSE)) {
						System.out.println("  Found pair. Possible Evals already has THREE_OF_A_KIND. Creating " 
							+ HandRank.FULL_HOUSE);						
						Evaluation eval = helpCreateEvaluation(HandRank.FULL_HOUSE, HandRank.THREE_OF_A_KIND, rac.rank());
						possibleEvaluations.put(HandRank.FULL_HOUSE, eval);
					} else if (possibleEvaluations.containsKey(HandRank.PAIR) &&
							!possibleEvaluations.containsKey(HandRank.TWO_PAIRS)) {
						// if not empty, there may be three of a kind on here which means full house
						// or there may be another pair which means two pairs.
						System.out.println("  Found pair. Possible Evals already has PAIR. Creating " 
							+ HandRank.TWO_PAIRS);												
						Evaluation eval = helpCreateEvaluation(HandRank.TWO_PAIRS, HandRank.PAIR, rac.rank());
						possibleEvaluations.put(HandRank.TWO_PAIRS, eval);
					}
				}
				break;
			case 1:
				if (possibleEvaluations.isEmpty()) {
					System.out.println("  All we have is a high card. Possible Evals empty. Creating " + HandRank.HIGH_CARD);
					Evaluation eval = new Evaluation(HandRank.HIGH_CARD);
					List<Card> rankedCards = cardsGroupedByRank.get(rac.rank());
					System.out.println("  Using rankedCards: " + rankedCards);
					eval.setRankedCards(rankedCards);
					possibleEvaluations.put(HandRank.HIGH_CARD, eval);
				} else {
					// if not empty, there's already a higher hand on here.	
				}
				break;
			}
		}
		
//		System.out.println ("  possible evaluations:\n" + possibleEvaluations);
		
		//Get highest evaluation and set kicker and unused cards.
		if (!possibleEvaluations.isEmpty()){
			evaluation = possibleEvaluations.get(possibleEvaluations.firstKey());
		}
//		System.out.println ("  best evaluation:\n" + evaluation);
		
		// Determine kicker(s) by removing ranked cards from clone of hand, 
		// then sorting and iterating through until count of ranked cards
		// and kicker cards is 5. The rest go in unused cards.

//		System.out.println ("  working hand before removing ranked cards:\n" + workingHand.cards);
		workingHand.remove(evaluation.getRankedCards());
		Collections.sort(workingHand.cards);
//		System.out.println ("  working hand after removing ranked cards and sorting:\n" + workingHand.cards);
		int numRankedCards = evaluation.getRankedCards().size();
		int numKickerCards = 5 - numRankedCards;
		List<Card> kickerCards = new ArrayList<Card>();
		
		Iterator<Card> unrankedCardsIter = workingHand.cards.iterator();
		while (unrankedCardsIter.hasNext() && kickerCards.size() < numKickerCards) {
			kickerCards.add(unrankedCardsIter.next());
			unrankedCardsIter.remove(); // Removes from underlying collection, workingHand.cards
		}
		evaluation.setKickerCards(kickerCards);
		evaluation.setUnusedCards(workingHand.cards);
		
		System.out.println ("  best evaluation:\n" + evaluation);
		
		return evaluation;
	}
	
	// handRank: Create a new evaluation with hand rank handRank.  
	// handRankOfHigherCards: Find the ranked cards in the evaluation that has a hand rank
	// (e.g. THREE_OF_A_KIND or PAIR) matching the handRankOfOtherCards passed in.
	// rank: Find the cards having a rank (ACE, KING, etc) matching the rank passed in.
	//   If the rank has three cards, and we already have 3 of a kind, only use the
	//   first two cards to create the full house.
	// Add both partial sets of ranked cards to this evaluation's ranked cards.
	private static Evaluation helpCreateEvaluation(HandRank handRank,
			HandRank handRankOfHigherCards, Rank rank) {
		Evaluation eval = new Evaluation(handRank);
		// Find the ranked cards we already have.
		Evaluation evalOfHigherCards = possibleEvaluations.get(handRankOfHigherCards);
		List<Card> rankedCards = evalOfHigherCards.getRankedCards(); 
		System.out.println("  Start with higher ranked cards " + handRankOfHigherCards + ": " + rankedCards);
		// Get the cards having the rank passed in, in order to add them.
		List<Card> additionalRankedCards = cardsGroupedByRank.get(rank);
		List<Card> rankedCardsToAdd = new ArrayList<Card>();
		// In cases where a full house is formed from two sets of three, 
		// only add two out of the three additional ranked cards.
		if (handRankOfHigherCards == handRank.THREE_OF_A_KIND && additionalRankedCards.size() == 3) {
			Collections.sort(additionalRankedCards);
//			Iterator workingIter = workingRankedCardsToAdd.iterator();
			rankedCardsToAdd.add(additionalRankedCards.get(0));
			rankedCardsToAdd.add(additionalRankedCards.get(1));
		} else {
			rankedCardsToAdd = additionalRankedCards;
		}
		System.out.println("  Add lesser ranked cards: " + rankedCardsToAdd);
		// Put the lists together.
		rankedCards.addAll(rankedCardsToAdd);
		eval.setRankedCards(rankedCards);
		return eval;
	}

	// Take a grouping of cards by rank, and create list of counts by rank.
	public static List<RankAndCount> countCardsByRank(Map<Card.Rank, List<Card>> cardsGroupedByRank) {
		List<RankAndCount> rankAndCountList = new ArrayList<RankAndCount>();
		
		Iterator<Card.Rank> rankIter = cardsGroupedByRank.keySet().iterator();
		while (rankIter.hasNext()) {
			Card.Rank rank = rankIter.next();
			List<Card> cardList = cardsGroupedByRank.get(rank);
			int count = cardList.size();
			rankAndCountList.add(new RankAndCount(rank,count));
		}
		
		return rankAndCountList;
	}
	
	// Group cards in a hand by their rank. return map of rank -> list of cards.
	// For example ACE:SPADES, KING:HEARTS, KING:DIAMONDS would result in map:
	// ACE -> ACE:SPADES
	// KING -> KING:HEARTS, KING:DIAMONDS
	public static Map<Card.Rank, List<Card>> groupHandByRank (Hand hand) {
		Map<Card.Rank, List<Card>> cardsGroupedByRank = new HashMap<Card.Rank, List<Card>>();
		Iterator<Card> handIterator = hand.iterator();
		
		while (handIterator.hasNext()) {
			Card card = handIterator.next();
			if (cardsGroupedByRank.containsKey(card.rank)) {
				// get key's (rank's) value (list of cards), add this card to list, replace it in the map.
				List<Card> cardList = cardsGroupedByRank.get(card.rank);
				cardList.add(card);
				cardsGroupedByRank.put(card.rank, cardList);
			} else {
				// add key (rank) to map with value set to list of just this card.
				List<Card> cardList = new ArrayList<Card>();
				cardList.add(card);
				cardsGroupedByRank.put(card.rank, (cardList));
			}
		}
		return cardsGroupedByRank;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println("nothing to see here.");
		
		
	}
	
}