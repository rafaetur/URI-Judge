import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class EmPerigo {

	public static void main(String[] args) throws IOException {
		InputStreamReader ir = new InputStreamReader(System.in);
		BufferedReader in = new BufferedReader(ir);
		String entrada = "";
		while ((entrada = in.readLine()) != null) {
			if ("00e0".equals(entrada)) {
				break;
			}
			System.out.println(doit(entrada));
		}
	}

	public static long doit(String input) {
		long quantidade = Long.parseLong(input.substring(0, 2));
		long zeros = Long.parseLong(input.substring(3, 4));

		if (zeros > 0) {
			String aux = String.valueOf(quantidade);
			for (int i = 0; i < zeros; i++) {
				aux = aux.concat("0");
			}
			quantidade = Long.valueOf(aux);
		}

		String entrada = Long.toBinaryString(quantidade).toString();

		char last = entrada.charAt(0);
		entrada = entrada.substring(1, entrada.length()).concat(String.valueOf(last));

		return Long.valueOf(entrada, 2);
	}

}
