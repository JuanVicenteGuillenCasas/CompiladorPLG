package CodigoMaquina;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class metodos_traduccion { 
	
	private int constantes; //idea: vamos a contabilizar al principio todas las constantes en un sitio fijo, las primeras posiciones(a partir de 5 obviamente).
	
	private int contadorInstrucciones;
	
	private int contadorNumVar;
	
	private int contadorReservaMemoria;
	
	private List<String> listaInstrucciones;
	
	private HashMap<Integer, Integer> memoria;  
	
	private HashMap<String, Integer> funcion_etiqueta;
	
	public metodos_traduccion(int le, int lv) { 
		constantes = 0;
		contadorInstrucciones = 0;
		contadorNumVar = 0;
		contadorReservaMemoria = 5;
		memoria = new HashMap<Integer,Integer>();
		funcion_etiqueta = new HashMap<String,Integer>();
        listaInstrucciones = new ArrayList<String>();
        listaInstrucciones.add("{" + contadorInstrucciones + "}" + "  " + "ssp" + " " + le + ";" );
        contadorInstrucciones++;
        listaInstrucciones.add("{" + contadorInstrucciones + "}" + "  " + "sep" + " " + lv + ";" );
        contadorInstrucciones++;
	}
	
	public void printeaTodo() throws IOException {	
		FileWriter fichero = new FileWriter("maquina.txt");
		PrintWriter pw = new PrintWriter(fichero); 
		for(String s: listaInstrucciones) pw.println(s);
		pw.println("{" + contadorInstrucciones + "}" + "  " + "stp" +  ";");
		pw.close();
	}
	
	public int reservaLinea() {
		listaInstrucciones.add(" ");
		contadorInstrucciones++;
		return contadorInstrucciones - 1;
	}
	
	public int devuelveContIns() {
		return contadorInstrucciones;
	} 
	
	public void reiniciaContadorFun() { //cada metodo al empezar a mirarse se reinician.  
		contadorNumVar = 0;
		contadorReservaMemoria = 5 ;
	}
	
	public void reiniciaContadorMain() {
		contadorNumVar = constantes;
		contadorReservaMemoria = 5 + constantes;
	}
	
	public int defineCte() {
		
		memoria.put(contadorNumVar, contadorReservaMemoria);
		
		contadorNumVar++; contadorReservaMemoria++; constantes++;
		
		return contadorNumVar - 1;
	}
	
	public void registraFuncion(String funcion) {	
		funcion_etiqueta.put(funcion, contadorInstrucciones);	
	}
	
	public int defineVariableSingular() {
		
		memoria.put(contadorNumVar, contadorReservaMemoria);
		
		contadorNumVar++; contadorReservaMemoria++;
		
		return contadorNumVar - 1; //Devuelve el numero que le toca a la variable;
	}
	
	public int defineArray(List<Integer> tamPorDim) {
		int tam = 1;
		memoria.put(contadorNumVar, contadorReservaMemoria); 
		
		for(Integer t : tamPorDim) tam = tam * t; //calculamos lo que va a ocupar el array.
		
		contadorNumVar++; contadorReservaMemoria += tam;
		
		return contadorNumVar - 1;
	} 
	
	public void CargaCte(Integer i) {
		listaInstrucciones.add("{" + contadorInstrucciones + "}" + "  " + "ldc" +  " " + memoria.get(i).toString() + ";"); 
		contadorInstrucciones++;
	}
	
	public void CargaVarSing(Integer i) { //El numero de variable que es se lo pasa el arbol que lo recibe al crearla.
		listaInstrucciones.add("{" + contadorInstrucciones + "}" + "  " + "lda" +  " " + 0 +  " " + memoria.get(i).toString() + ";"); 
		contadorInstrucciones++;
	}
	
	public void CargaVarSingReferencia(Integer i) { //El numero de variable que es se lo pasa el arbol que lo recibe al crearla.
		listaInstrucciones.add("{" + contadorInstrucciones + "}" + "  " + "lod" +  " " + 0 +  " " + memoria.get(i).toString() + ";"); 
		contadorInstrucciones++;
	}
	
	public void CargaVarArray(List<Integer> dim, List<Integer> cor, Integer pos) { 
		int dj ;
		listaInstrucciones.add("{" + contadorInstrucciones + "}" + "  " + "lda" +  " " + 0 +  " " + memoria.get(pos).toString() + ";"); 
		contadorInstrucciones++;
		for(int i = 0; i < cor.size(); i++) {
			dj = 1;
			for(int j = i + 1; j < dim.size(); j++) dj = dj * dim.get(j);
			listaInstrucciones.add("{" + contadorInstrucciones +"}" + "  " + "ldc" + " " + cor.get(i).toString() + ";"); 
			contadorInstrucciones++;
			listaInstrucciones.add("{" + contadorInstrucciones +"}" + "  " + "ixa" + " " + dj + ";"); // g = 1
			contadorInstrucciones++;
		}
		listaInstrucciones.add("{" + contadorInstrucciones + "}" + "  " + "dec" + " " + 0 + ";"); //por seguir el protocolo pero al estar el limite inferior siempre a 0 esta instruccion es inutil. 
		contadorInstrucciones++;
	}
	
	public void CargaVarArrayReferencia(List<Integer> dim, List<Integer> cor, Integer pos) { 
		int dj ;
		listaInstrucciones.add("{" + contadorInstrucciones + "}" + "  " + "lod" +  " " + 0 +  " " + memoria.get(pos).toString() + ";"); 
		contadorInstrucciones++;
		for(int i = 0; i < cor.size(); i++) {
			dj = 1;
			for(int j = i + 1; j < dim.size(); j++) dj = dj * dim.get(j);
			listaInstrucciones.add("{" + contadorInstrucciones +"}" + "  " + "ldc" + " " + cor.get(i).toString() + ";"); 
			contadorInstrucciones++;
			listaInstrucciones.add("{" + contadorInstrucciones +"}" + "  " + "ixa" + " " + dj + ";"); // g = 1
			contadorInstrucciones++;
		}
		listaInstrucciones.add("{" + contadorInstrucciones + "}" + "  " + "dec" + " " + 0 + ";"); //por seguir el protocolo pero al estar el limite inferior siempre a 0 esta instruccion es inutil. 
		contadorInstrucciones++;
	}
	
	public void apila(String valor) {
		listaInstrucciones.add("{" + contadorInstrucciones +"}" + "  " + "ldc" + " " + valor + ";"); 
		contadorInstrucciones++;
	}
	
	public void cargaValor() {
		listaInstrucciones.add("{" + contadorInstrucciones + "}" + "  " + "ind" + ";" ); 
		contadorInstrucciones++;
	}	
	
	public void inserta() {
		listaInstrucciones.add("{" + contadorInstrucciones + "}" + "  " + "sto" +  ";"); 
		contadorInstrucciones++;
	} 
	
	public void operaciones(String operacion) {
		if (operacion.equals("+")) listaInstrucciones.add("{" + contadorInstrucciones + "}" + "  " + "add" + ";" );
		else if(operacion.equals("-"))listaInstrucciones.add("{" + contadorInstrucciones + "}" + "  " + "sub" + ";" ); 
		else if(operacion.equals("*")) listaInstrucciones.add("{" + contadorInstrucciones + "}" + "  " + "mul" + ";" ); 
		else if(operacion.equals("/")) listaInstrucciones.add("{" + contadorInstrucciones + "}" + "  " + "div" + ";" );
		else if(operacion.equals("<=")) listaInstrucciones.add("{" + contadorInstrucciones + "}" + "  " + "leq" + ";" ); 
		else if(operacion.equals("<")) listaInstrucciones.add("{" + contadorInstrucciones + "}" + "  " + "les" + ";" ); 
		else if(operacion.equals(">=")) listaInstrucciones.add("{" + contadorInstrucciones + "}" + "  " + "geq" + ";" );
		else if(operacion.equals(">")) listaInstrucciones.add("{" + contadorInstrucciones + "}" + "  " + "grt" + ";" );
		else if(operacion.equals("="))listaInstrucciones.add("{" + contadorInstrucciones + "}" + "  " + "equ" + ";" );
		else if(operacion.equals("!=")) listaInstrucciones.add("{" + contadorInstrucciones + "}" + "  " + "neq" + ";" );
		else if(operacion.equals("AND")) listaInstrucciones.add("{" + contadorInstrucciones + "}" + "  " + "and" + ";" ); 
		else if(operacion.equals("OR")) listaInstrucciones.add("{" + contadorInstrucciones + "}" + "  " + "or" + ";" );
		else if(operacion.equals("!!")) listaInstrucciones.add("{" + contadorInstrucciones + "}" + "  " + "not" + ";" ); 
		contadorInstrucciones++;
	}
	
	public void negarCima() {
		listaInstrucciones.add("{" + contadorInstrucciones + "}" + "  " + "neg" + ";" );
		contadorInstrucciones++;
	}
	
	public void saltoCondicion(int marca, int pos) {
		if(pos != contadorInstrucciones) listaInstrucciones.set(pos,"{" + pos + "}" + "  " + "fjp" + " " + marca + ";" ); 
		if(pos == contadorInstrucciones) {
			listaInstrucciones.add("{" + pos + "}" + "  " + "fjp" + " " + marca + ";");
			contadorInstrucciones++;
		}
	}
	
	public void saltoIncond(int marca, int pos) {
		if(pos != contadorInstrucciones) listaInstrucciones.set(pos, "{" + pos + "}" + "  " + "ujp" + " " + marca + ";" ); 
		if(pos == contadorInstrucciones) {
			listaInstrucciones.add( "{" + pos + "}" + "  " + "ujp" + " " + marca + ";" ) ;
			contadorInstrucciones++;	
		}
	}
	
	public void saltoCalculado(int q, int pos) {
		if(pos != contadorInstrucciones) listaInstrucciones.set(pos, "{" + pos + "}" + "  " + "ixj" + " " + q + ";" ); 
		if(pos == contadorInstrucciones) {
			listaInstrucciones.add("{" + pos + "}" + "  " + "ixj" + " " + q + ";");
			contadorInstrucciones++;		
		}
	}
	
	public void instruccionReturnConValor() {
		listaInstrucciones.add("{" + contadorInstrucciones + "}" + "  " + "retf" + ";" ); 
		contadorInstrucciones++;
	}
	
	public void instruccionReturnSinValor() {
		listaInstrucciones.add("{" + contadorInstrucciones + "}" + "  " + "retp" + ";" ); 
		contadorInstrucciones++;
	}
	
	public void preambuloFun(int le, int lv) {
		listaInstrucciones.add("{" + contadorInstrucciones + "}" + "  " + "ssp" + " " + le + ";" ); 
		contadorInstrucciones++;
		listaInstrucciones.add("{" + contadorInstrucciones + "}" + "  " + "sep" + " " + lv + ";" ); 
		contadorInstrucciones++;
	}
	
	public void llamada_mst() {
		listaInstrucciones.add("{" + contadorInstrucciones + "}" + "  " + "mst" + " " + 0 + ";" ); //siempre va a ser 1.
		contadorInstrucciones++;		
	}
	
	public void llamada_cup(int np, String fun) {
		listaInstrucciones.add("{" + contadorInstrucciones + "}" + "  " + "cup" + " " + np + " "  + funcion_etiqueta.get(fun).toString() + ";" ); 
		contadorInstrucciones++;
	}	
	
	public void cargar_MP() {
		listaInstrucciones.add("{" + contadorInstrucciones + "}" + "  " + "lda" +  " " + 0 +  " " + 0 + ";"); 
		contadorInstrucciones++;
	}
	
}
