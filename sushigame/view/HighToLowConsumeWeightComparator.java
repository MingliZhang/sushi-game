package sushigame.view;

import java.util.Comparator;

import sushigame.model.Chef;

public class HighToLowConsumeWeightComparator implements Comparator<Chef>{

	@Override
	public int compare(Chef a, Chef b) {
		// We do b - a because we want largest to smallest
		return (int) (Math.round(b.getWeightComsumed()*100.0) - 
				Math.round(a.getWeightComsumed()*100));
	}

}
