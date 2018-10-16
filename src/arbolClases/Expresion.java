package arbolClases;

import java.util.HashMap;

import CodigoMaquina.metodos_traduccion;

public abstract class Expresion {
	
	public abstract Tipo.tipo_var tipo() ;
	
	public abstract void addExternal(HashMap<String, Constante> ctes, HashMap<String, Funtion> funciones); 
	
	public abstract void addEntorno(HashMap<String, Tipo> var);
	
	public abstract void  addEntornoFun(String identifier, Parametros p, TipoVar tipDev);
	
	public abstract void addEntornoSub(HashMap<String, Tipo> var);
	
	public abstract boolean esArray();
	
	public abstract String toString();
	
	public abstract void calculaValor(metodos_traduccion trad,  HashMap<String, Integer> var_memoria, HashMap<String, Integer> var_por_referencia);

	public abstract int getLV();
}
