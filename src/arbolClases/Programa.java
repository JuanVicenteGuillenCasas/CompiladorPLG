package arbolClases;

import java.util.HashMap;

import CodigoMaquina.metodos_traduccion;

public class Programa {
	
	private ClaseBloque bloquePrincipal;  
	

	public Programa(ClaseBloque b){
		bloquePrincipal = b;
	}
	
	public int devuelveLE() {
		int le = bloquePrincipal.getLE();
		return le;
	}
	
	public int devuelveLV() { 
		int lv = bloquePrincipal.getLV();
		return lv;
	}

	public void add(HashMap<String, Constante> lista_constantes, HashMap<String, Funtion> lista_funciones) {
		for(Funtion f : lista_funciones.values()) 
			f.devuelveBloque().addExternal(lista_constantes, lista_funciones);
		bloquePrincipal.addExternal(lista_constantes, lista_funciones);	  
	}
	
	public void  traduccion (metodos_traduccion tr) {
		tr.reiniciaContadorMain();
		bloquePrincipal.traduccion(tr); 
	}
	
}