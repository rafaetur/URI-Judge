import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) throws NumberFormatException, IOException {

		InputStreamReader ir = new InputStreamReader(System.in);
		BufferedReader in = new BufferedReader(ir);
		short loop = Short.valueOf(in.readLine());

		for (short i = 0; i < loop; i++) {
			int num = Integer.valueOf(in.readLine());

			if (num % 2 == 0) {
				if (num == 2) {
					System.out.println("Prime");
					continue;
				} else {
					System.out.println("Not Prime");
					continue;
				}
			}

			boolean isPrime = true;
			for (short it = 3; it < num; it += 2) {
				if (num % it == 0) {
					isPrime = false;
					break;
				}
				if (num / it < it) {
					isPrime = true;
					break;
				}
			}

			if (isPrime) {
				System.out.println("Prime");
			} else {
				System.out.println("Not Prime");
			}
		}
	}
}
