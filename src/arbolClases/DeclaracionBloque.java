package arbolClases;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import CodigoMaquina.metodos_traduccion;

public class DeclaracionBloque{
	
	private List<ClaseInstruccion> listaInstrucciones;
	private List<ClaseBloque> listaBloquesHijos;
	private List<InsOrBloque> listaOrdenada; //No nos queda otra que tener una lista con las instrucciones y bloques EN ORDEN.
	
	public DeclaracionBloque(ClaseInstruccion i){
		listaInstrucciones = new ArrayList<ClaseInstruccion>();
		listaBloquesHijos = new ArrayList<ClaseBloque>();
		listaOrdenada = new ArrayList<InsOrBloque>();
		listaInstrucciones.add(i); 
		listaOrdenada.add(i);
	}
	
	public DeclaracionBloque(ClaseInstruccion i , DeclaracionBloque db){ 
		listaInstrucciones = new ArrayList<ClaseInstruccion>();
		listaOrdenada = new ArrayList<InsOrBloque>();
		listaInstrucciones.add(i);
		listaInstrucciones.addAll(db.getIns());
		listaBloquesHijos = db.getBH();
		listaOrdenada.add(i);
		listaOrdenada.addAll(db.getListaOrd());
	}
	

	public DeclaracionBloque(ClaseBloque b){
		listaBloquesHijos = new ArrayList<ClaseBloque>();
		listaInstrucciones = new ArrayList<ClaseInstruccion>();
		listaOrdenada = new ArrayList<InsOrBloque>();
		listaBloquesHijos.add(b);
		listaOrdenada.add(b);		
	} 
	
	public DeclaracionBloque(ClaseBloque b, DeclaracionBloque db){
		listaBloquesHijos = new ArrayList<ClaseBloque>();	
		listaOrdenada = new ArrayList<InsOrBloque>();
		listaBloquesHijos.add(b);
		listaBloquesHijos.addAll(db.getBH());
		listaInstrucciones = db.getIns();
		listaOrdenada.add(b);
		listaOrdenada.addAll(db.getListaOrd());
	}
	
	public List<ClaseInstruccion> getIns() {
		return listaInstrucciones;
	}
	
	public List<ClaseBloque> getBH() {
		return listaBloquesHijos;
	}
	
	public List<InsOrBloque> getListaOrd() { 
		return listaOrdenada;
	}
	
	public void add(HashMap<String, Constante> ctes, HashMap<String, Funtion> funciones) {
		for(ClaseBloque b : listaBloquesHijos)  b.addExternal(ctes, funciones); 
		for(ClaseInstruccion i : listaInstrucciones) i.addGeneric(ctes, funciones);
	}

	public void entorno(HashMap<String, Tipo> var) {
		for(ClaseBloque b : listaBloquesHijos)  b.addEntornoBloquePadre(var); 
		for(ClaseInstruccion i : listaInstrucciones) i.addEntorno(var);
	}

	public void entornoFuncion(String identifier, Parametros p, TipoVar tipDev) {
		for(ClaseBloque b : listaBloquesHijos)  b.getVars_fromFuntion(identifier, p, tipDev); 
		for(ClaseInstruccion i : listaInstrucciones) i.addEntornoFun(identifier, p, tipDev);
	}

	public void entornoSub(HashMap<String, Tipo> var) {
		for(ClaseBloque b : listaBloquesHijos)  b.addEntornoBloquePadre(var); //transmite a sus hijos lo que ha heredado;
		for(ClaseInstruccion i : listaInstrucciones) i.addEntornoSub(var); // Las variables que hereden y no estén ya definidas las hereda.
	}

	public int getLV() {
		int lv = 0;
		for(ClaseInstruccion i : listaInstrucciones) lv = Math.max(lv, i.getLV());
		for(ClaseBloque b: listaBloquesHijos) lv = Math.max(lv, b.getLV());
		return lv;
	}

	public void traduccion(metodos_traduccion trad, HashMap<String, Integer> var_memoria, HashMap<String, Integer> var_por_referencia) {
		
		for(InsOrBloque o : listaOrdenada) {
			if(!o.soyInstruccion()) {
				((ClaseBloque)o).varExternas(var_memoria, var_por_referencia); 
				((ClaseBloque)o).traduccion(trad); 
			}
			else ((ClaseInstruccion)o).traduccion(trad, var_memoria,var_por_referencia);
		}
		
	}

	public boolean tieneReturn() {
		boolean resp = false;
		
		for(ClaseInstruccion i: listaInstrucciones) {
			if(i.identificadorIns() == 4) resp = true; 
			else if(i.identificadorIns() == 3 && ((InstruccionIfElse)i).tieneReturn()) resp = true;	
			//Una instruccion return solo va a ser segura si nos garantizamos que pasa al 100% por esta.
		}
		
		for(ClaseBloque b: listaBloquesHijos) {
			if(b.tieneReturn()) resp = true;
		}
		
		return resp;
	}
	
}