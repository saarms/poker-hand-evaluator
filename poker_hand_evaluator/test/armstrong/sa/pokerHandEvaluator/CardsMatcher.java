package armstrong.sa.pokerHandEvaluator;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class CardsMatcher {

	public static Matcher<List<Card>> matchesCards(final List<Card> cardsExpected) {
		return new BaseMatcher<List<Card>>() {
			@Override
			public boolean matches(final Object item){
				final List<Card> cards = (List<Card>) item;
				if (cards.size() != cardsExpected.size()) return false;
//				System.out.println("pre-sorted cards:          " + cards);
//				System.out.println("pre-sorted cards expected: " + cardsExpected);
				Collections.sort(cards);
				Collections.sort(cardsExpected);
//				System.out.println("sorted cards:          " + cards);
//				System.out.println("sorted cards expected: " + cardsExpected);
				Iterator<Card> cardIterator = cards.iterator();
				Iterator<Card> cardExpectedIterator = cardsExpected.iterator();
				boolean match = true;
				while (cardIterator.hasNext() && cardExpectedIterator.hasNext() && match) {
					Card card = cardIterator.next();
					Card cardExpected = cardExpectedIterator.next();
					match = cardExpected.rank == card.rank && cardExpected.suit == card.suit;
				}
				return match;
			}
			@Override
			public void describeTo(final Description description) {
				if (cardsExpected.isEmpty()) {
					description.appendText("no cards.");
					return;
				}
				description.appendText(cardsExpected.size() + " cards: ");
				Iterator<Card> cardExpectedIterator = cardsExpected.iterator();
				while (cardExpectedIterator.hasNext()) {
					Card cardExpected = cardExpectedIterator.next();
					description.appendText(cardExpected.toString());
					if (cardExpectedIterator.hasNext()) {
						description.appendText(", ");
					}
				}			
			}
			@Override
			public void describeMismatch(final Object item, final Description description) {
				final List<Card> cards = (List<Card>) item;
				if (cards.isEmpty()) {
					description.appendText("was no cards.");
					return;
				}
				description.appendText("was " + cards.size() + " cards: ");
				Iterator<Card> iterator = cards.iterator();
				while (iterator.hasNext()) {
					Card card = iterator.next();
					description.appendText(card.toString());
					if (iterator.hasNext()) {
						description.appendText(", ");
					}
				}
			}
		};
	}

	public static Matcher<Card> isSameCard(final Card cardExpected) {
		return new BaseMatcher<Card>() {
			@Override
			public boolean matches(final Object item){
				final Card card = (Card) item;
				return cardExpected.rank == card.rank && cardExpected.suit == card.suit;
			}
			@Override
			public void describeTo(final Description description) {
				description.appendText("card should be " + cardExpected);
			}
			@Override
			public void describeMismatch(final Object item, final Description description) {
				final Card card = (Card) item;
				description.appendText("was " + card);
			}
		};
	}
	
//	private Matcher<Card> containsCard(final Card cardExpected) {
//	return new BaseMatcher<Card>() {
//		@Override
//		public boolean matches(final Object item){
//			final List<Card> cards = (List<Card>) item;
//			Iterator<Card> iterator = cards.iterator();
//			boolean found = false;
//			while (iterator.hasNext() && !found) {
//				Card card = iterator.next();
//				found = cardExpected.rank == card.rank && cardExpected.suit == card.suit;
//			}
//			return found;
//		}
//		@Override
//		public void describeTo(final Description description) {
//			description.appendText("cards should contain " + cardExpected.rank + ":" + cardExpected.suit);
//		}
//		@Override
//		public void describeMismatch(final Object item, final Description description) {
//			final List<Card> cards = (List<Card>) item;
//			if (cards.isEmpty()) {
//				description.appendText("was empty.");
//				return;
//			}
//			description.appendText("contained ");
//			Iterator<Card> iterator = cards.iterator();
//			while (iterator.hasNext()) {
//				Card card = iterator.next();
//				description.appendText(card.toString());
//				if (iterator.hasNext()) {
//					description.appendText(", ");
//				}
//			}
//		}
//	};
//}
	
}
