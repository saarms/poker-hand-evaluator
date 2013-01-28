package armstrong.sa.pokerHandEvaluator;

import java.util.List;

public class Evaluation {

	private Hand.HandRank handRank;
	private List<Card> rankedCards;
	private List<Card> kickerCards;
	private List<Card> unusedCards;
	
	public Evaluation (Hand.HandRank handRank) {
		this.handRank = handRank;
	}
	
	public Evaluation () {}
	
	public Hand.HandRank getHandRank() {
		return handRank;
	}
	public void setHandRank(Hand.HandRank handRank) {
		this.handRank = handRank;
	}
	public List<Card> getRankedCards() {
		return rankedCards;
	}
	public void setRankedCards(List<Card> rankedCards) {
		this.rankedCards = rankedCards;
	}
	public List<Card> getKickerCards() {
		return kickerCards;
	}
	public void setKickerCards(List<Card> kickerCards) {
		this.kickerCards = kickerCards;
	}
	public List<Card> getUnusedCards() {
		return unusedCards;
	}
	public void setUnusedCards(List<Card> unusedCards) {
		this.unusedCards = unusedCards;
	}
	
	@Override
	public String toString() {
		String readableEval = "    Evaluation: " + handRank + "\n"
		+ "    ranked cards: " + rankedCards + "\n"
		+ "    kicker cards: " + kickerCards + "\n"
		+ "    unused cards: " + unusedCards;
		return readableEval;
	}
	
}
