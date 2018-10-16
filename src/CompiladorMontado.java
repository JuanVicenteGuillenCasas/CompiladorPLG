
import java.io.File;
import java.io.FileInputStream;
import alex.AnalizadorLexico;
import asint.parser;
import java_cup.runtime.Symbol;

public class CompiladorMontado {
	@SuppressWarnings("deprecation")
	public static void main(String args[]) {
		File file = new File("EjemploCompleto.txt");
		try {
			FileInputStream entrada = new FileInputStream(file);
			AnalizadorLexico lexico = new AnalizadorLexico(entrada);
			parser sintactico = new parser(lexico);
			
			Symbol salida = sintactico.debug_parse();
			System.out.println(salida);
		} catch (Exception e) {			
			e.printStackTrace();
		}		
		
	}
}
