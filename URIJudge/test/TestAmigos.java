import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class TestAmigos {

	@Test
	public void test() {
		HashMap<String, String> expressaoResultado = new HashMap<String, String>();
		expressaoResultado.put("{ABC}", "{ABC}");
		expressaoResultado.put("{ABC}+{DEFG}+{Z}+{}", "{ABCDEFGZ}");
		expressaoResultado.put("{ABE}*{ABCD}", "{AB}");
		expressaoResultado.put("{ABCD}-{CZ}", "{ABD}");
		expressaoResultado.put("{ABC}+{CDE}*{CEZ}", "{ABCE}");
		expressaoResultado.put("({ABC}+{CDE})*{CEZ}", "{CE}");
		expressaoResultado.put("{ABC}+{}*{AF}*{}+{}", "{ABC}");
		expressaoResultado.put("{BC}+({A}+({C}+{B}))-{A}+{}-{}", "{BC}");
		expressaoResultado.put("({})+{BC}+({A}+({C}+{B}))-{A}+{}-{}", "{BC}");
		expressaoResultado.put("({})", "{}");

		for (Map.Entry<String, String> pair : expressaoResultado.entrySet()) {
			String doit = Amigos.doit(pair.getKey());
			if (doit.equals(pair.getValue())) {
				System.out.println("Expressão: " + pair.getKey() + " -> Esperado: " + pair.getValue() + " - " + doit);
			} else {
				System.err.println("Expressão: " + pair.getKey() + " -> Esperado: " + pair.getValue() + " - " + doit);
				fail();
			}
		}

	}

}
