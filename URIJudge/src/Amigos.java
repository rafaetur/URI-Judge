import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class Amigos {

	static Stack<String>	pilhaOperadores	= new Stack<String>();
	static Stack<String>	pilhaGrupos		= new Stack<String>();

	public static void main(String[] args) throws IOException {
		InputStreamReader ir = new InputStreamReader(System.in);
		BufferedReader in = new BufferedReader(ir);
		String entrada = "";
		while ((entrada = in.readLine()) != null) {

			String[] entradas = entrada.split("(\\\\n|\\r?\\n)");

			for (int i = 0; i < entradas.length; i++) {
				System.out.println(doit(entradas[i]));
				pilhaOperadores.clear();
				pilhaGrupos.clear();
			}
		}
	}

	public static String doit(String input) {
		input = input.replaceAll("\\{\\}", " ").replaceAll("\\{", "").replaceAll("\\{", "").replaceAll("\\}", "");
		input = new StringBuilder(input).reverse().toString().replaceAll("\\(", "2").replaceAll("\\)", "(").replaceAll("2", "\\)");
		String[] operadores = input.split("\\s*[a-zA-Z\\(\\)]*");
		String[] conjuntos = input.split("[^a-zA-Z\\ \\(\\)]");

		for (int i = 0; i < operadores.length; i++) {
			if (!operadores[i].equals("")) {
				pilhaOperadores.push(operadores[i]);
			}
		}

		for (int i = 0; i < conjuntos.length; i++) {
			String conjunto = conjuntos[i].trim();
			while (true) {
				if (conjunto.contains("(")) {
					conjunto = conjunto.replaceFirst("\\(", "");
					pilhaGrupos.push("(");
				} else if (conjunto.length() > 1 && conjunto.charAt(conjunto.length() - 1) == ')') {
					if (conjunto.charAt(0) == ')') {
						pilhaGrupos.push(")");
						conjunto = conjunto.substring(0, 1);
					} else {
						String substring = conjunto.substring(0, conjunto.indexOf(')'));
						pilhaGrupos.push(substring);
						conjunto = conjunto.substring(substring.length(), conjunto.length());
					}
				} else {
					break;
				}

			}
			pilhaGrupos.push(conjunto);
		}

		String processaTudo = processaBloco();

		char[] charArray = processaTudo.toCharArray();
		for (int i = 0; i < charArray.length - 1; i++) {
			for (int j = 0; j < charArray.length - 1; j++) {
				if (charArray[j] > charArray[j + 1]) {
					char aux = charArray[j];
					charArray[j] = charArray[j + 1];
					charArray[j + 1] = aux;
				}
			}
		}

		return ("{" + String.valueOf(charArray) + "}").replaceAll("\\ ", "");
	}

	private static String processaBloco() {
		label: while (true) {
			if (pilhaGrupos.size() == 1) {
				break;
			}

			String primeiroConjunto = (String) pilhaGrupos.pop();

			if (primeiroConjunto.equals(")")) {
				primeiroConjunto = processaBloco();
			}

			if (pilhaOperadores.isEmpty()) {
				if (!pilhaGrupos.isEmpty()) {
					pilhaGrupos.pop(); // Apenas retiro a )
				}
				return primeiroConjunto;
			}

			String primeiroOperador = (String) pilhaOperadores.pop();
			String segundoConjunto = (String) pilhaGrupos.pop();

			if (segundoConjunto.equals(")")) {
				segundoConjunto = processaBloco();
			}

			if (pilhaOperadores.isEmpty()) {
				pilhaGrupos.push(resolverOperacao(primeiroConjunto, primeiroOperador, segundoConjunto));
				break;
			}

			String temp = (String) pilhaGrupos.pop();
			if (segundoConjunto.equals("(")) {
				pilhaGrupos.push(temp);
				pilhaOperadores.push(primeiroOperador);
				pilhaGrupos.push(primeiroConjunto);
				break;
			} else if (temp.equals("(")) {
				pilhaGrupos.push(resolverOperacao(primeiroConjunto, primeiroOperador, segundoConjunto));
				break;
			} else {
				pilhaGrupos.push(temp);
			}

			String segundoOperador = (String) pilhaOperadores.pop();

			while (true) {
				if ("*".equals(segundoOperador)) {
					segundoConjunto = resolverOperacao(segundoConjunto, segundoOperador, (String) pilhaGrupos.pop());

					if (pilhaOperadores.isEmpty()) {
						pilhaGrupos.push(resolverOperacao(primeiroConjunto, primeiroOperador, segundoConjunto));
						break label;
					} else {
						segundoOperador = (String) pilhaOperadores.pop();
					}

				} else {
					pilhaGrupos.push(resolverOperacao(primeiroConjunto, primeiroOperador, segundoConjunto));
					pilhaOperadores.push(segundoOperador);
					break;
				}
			}
		}

		return (String) pilhaGrupos.pop();
	}

	private static String resolverOperacao(String primeiroConjunto, String operador, String segundoConjunto) {
		if ("+".equals(operador)) {
			return somar(primeiroConjunto.toCharArray(), segundoConjunto.toCharArray());
		} else if ("-".equals(operador)) {
			return subtrair(primeiroConjunto.toCharArray(), segundoConjunto.toCharArray());
		} else {
			return interseccao(primeiroConjunto.toCharArray(), segundoConjunto.toCharArray());
		}
	}

	private static String somar(char[] conjunto1, char[] conjunto2) {
		String nova = String.valueOf(conjunto1);

		for (int j = 0; j < conjunto2.length; j++) {
			boolean jaPossui = false;
			for (int i = 0; i < conjunto1.length; i++) {
				if (conjunto1[i] == conjunto2[j]) {
					jaPossui = true;
					break;
				}
			}

			if (!jaPossui) {
				nova += conjunto2[j];
			}
		}

		return nova;
	}

	private static String subtrair(char[] conjunto1, char[] conjunto2) {
		String nova = "";
		for (int i = 0; i < conjunto1.length; i++) {
			boolean isPermanece = true;
			for (int j = 0; j < conjunto2.length; j++) {
				if (conjunto1[i] == conjunto2[j]) {
					isPermanece = false;
					break;
				}
			}
			if (isPermanece) {
				nova += conjunto1[i];
			}
		}

		return nova;
	}

	private static String interseccao(char[] conjunto1, char[] conjunto2) {
		String nova = "";
		for (int i = 0; i < conjunto1.length; i++) {
			for (int j = 0; j < conjunto2.length; j++) {
				if (conjunto1[i] == conjunto2[j]) {
					nova += conjunto1[i];
					break;
				}
			}
		}

		return nova;
	}

}
