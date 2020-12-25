package comp401sushi;

public class Sashimi implements Sushi {

	public enum SashimiType {TUNA, YELLOWTAIL, EEL, CRAB, SHRIMP}

	private static double SASHIMI_PORTION_AMOUNT = 0.75;
	
	private IngredientPortion seafood;
	
	public Sashimi(SashimiType type) {
		switch(type) {
		case TUNA:
			seafood = new TunaPortion(SASHIMI_PORTION_AMOUNT);
			break;
		case YELLOWTAIL:
			seafood = new YellowtailPortion(SASHIMI_PORTION_AMOUNT);
			break;
		case EEL:
			seafood = new EelPortion(SASHIMI_PORTION_AMOUNT);
			break;
		case CRAB:
			seafood = new CrabPortion(SASHIMI_PORTION_AMOUNT);
			break;
		case SHRIMP:
			seafood = new ShrimpPortion(SASHIMI_PORTION_AMOUNT);
			break;			
		}
	}
	
	@Override
	public String getName() {
		return seafood.getName() + " sashimi";
	}

	@Override
	public IngredientPortion[] getIngredients() {
		return new IngredientPortion[] {seafood};
	}

	@Override
	public int getCalories() {
		return (int) (seafood.getCalories() + 0.5);
	}

	@Override
	public double getCost() {
		return ((int) (seafood.getCost() * 100.0 + 0.5)) / 100.0;
	}

	@Override
	public boolean getHasRice() {
		return false;
	}

	@Override
	public boolean getHasShellfish() {
		return seafood.getIsShellfish();
	}

	@Override
	public boolean getIsVegetarian() {
		return false;
	}

	@Override
	public double getTotalWeight() {
		return this.SASHIMI_PORTION_AMOUNT;
	}

}
