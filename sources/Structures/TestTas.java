package Structures;

import java.util.Random;

public class TestTas {
	public static void main(String[] args) {
		int min = 0;
		int[] count = new int[100];
		Random r = new Random();
		Tas<Integer> f = new Tas<>();

		assert (f.estVide());
		for (int i = 0; i < 100; i++) {
			if (r.nextBoolean()) {
				Integer val = r.nextInt(count.length);
				System.out.println("Insertion de " + val);
				f.inserer(val, val);
				assert (!f.estVide());
				if (val < min)
					min = val;
				count[val]++;
			} else {
				if (!f.estVide()) {
					int val = f.extraire();
					count[val]--;
					assert (count[val] >= 0);
					assert (val >= min);
					if (val > min)
						min = val;
					System.out.println("Extraction de " + val);
				}
			}
		}
	}
}
