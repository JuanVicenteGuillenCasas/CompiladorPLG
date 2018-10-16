package arbolClases;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import errores.GestionErrores;

public class Parametros {  
	
	private HashMap<String, Tipo> mapa;
	
	private List<String> ordenParametros;
	
	private int contador;
		
	public Parametros(Tipo tip, String id, Parametros par){
		GestionErrores err = new GestionErrores();
		mapa = new HashMap<String, Tipo>();
		ordenParametros = new ArrayList<String>();
		
		mapa.put(id, tip); 
		ordenParametros.add(id);
		
		if(par.getMap().containsKey(id)) err.errorRepiteDefinicion(id); // error por repetidos 
		
		mapa.putAll(par.getMap());
		ordenParametros.addAll(par.devuelveListaOrdenada());
		contador = par.numParametros() + 1;
	}
	
	public Parametros(Tipo tip, String id){
		mapa = new HashMap<String, Tipo>();
		ordenParametros = new ArrayList<String>();
		mapa.put(id , tip);
		ordenParametros.add(id);
		contador = 1;
	}
	
	public Parametros(){
		mapa = new HashMap<String, Tipo>();
		ordenParametros = new ArrayList<String>();
	}
	
	
	public int numParametros() {
		return contador;
	}
	
	public HashMap<String, Tipo> getMap() {	
		return mapa;
	} 
	
	public List<String> devuelveListaOrdenada(){
		return ordenParametros;
	}

	public Tipo devuelveTipo(String name) {
		return mapa.get(name);
	}
			
}