package armstrong.sa.pokerHandEvaluator;


public class RankAndCount implements Comparable<RankAndCount>{
	private final Card.Rank rank;
	private final Integer count;
	
	public RankAndCount(Card.Rank rank, int count){
		this.rank = rank;
		this.count = count;
	}
	
	public Card.Rank rank() { return rank; }
	public int count() { return count; }
	
	public int compareTo(RankAndCount rac) {
		//order by count, then by rank
		if (this.count < rac.count()) {
			// rac passed in has higher count
			return 1;
		} else if (this.count == rac.count()) {
			return this.rank.ordinal() < rac.rank().ordinal() ? 1
					: this.rank.ordinal() > rac.rank().ordinal() ? -1
							: 0;
				//rac passed in has higher rank
		} else 
			return -1;
	}
	
	@Override
	public String toString() {
		return rank + ": " + count;
	}
	
}
