package org.algoritmica.alvie.utility;

import org.algoritmica.alvie.datastructure.Array;
import org.algoritmica.alvie.information.IntInformation;

public class InputUtility {

	public static Array<IntInformation> randomIntArray(int l, int n, int m) {
		Array<IntInformation> a = new Array<IntInformation>(l, new IntInformation());
		int x;
		for (int i = 0; i < l; i++) {
			x = (int) (Math.random() * (m - n + 1)) + n;
			a.setAt(new IntInformation(x), i);
		}
		return a;
	}

	public static Array<IntInformation> randomDistinctIntArray(int l, int n, int m) {
		Array<IntInformation> a = new Array<IntInformation>(l, new IntInformation());
		int i, j, x;
		boolean generated;
		for (i = 0; i < l; i++) {
			generated = false;
			while (!generated) {
				x = (int) (Math.random() * (m - n + 1)) + n;
				generated = true;
				for (j = 0; j < i; j++) {
					if (x == a.elementAt(j).intValue()) {
						generated = false;
						break;
					}
				}
				a.setAt(new IntInformation(x), i);
			}
		}
		return a;
	}

	public static Array<IntInformation> defaultIntArray(int l) {
		Array<IntInformation> a = new Array<IntInformation>(l, new IntInformation());
		for (int i = 0; i < l; i++) {
			a.setAt(new IntInformation(0), i);
		}
		return a;
	}
}
