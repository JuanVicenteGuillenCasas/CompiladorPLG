package arbolClases;

import java.util.HashMap;

import CodigoMaquina.metodos_traduccion;

public abstract class ClaseInstruccion extends InsOrBloque {

	public abstract void addGeneric(HashMap<String, Constante> ctes, HashMap<String, Funtion> funciones) ;

	public abstract void addEntorno(HashMap<String, Tipo> var);

	public abstract void addEntornoFun(String identifier, Parametros p, TipoVar tipDev) ;

	public abstract void addEntornoSub(HashMap<String, Tipo> var) ;

	public abstract int getLV();
	
	public boolean soyInstruccion() {return true; }

	public abstract void traduccion(metodos_traduccion trad, HashMap<String, Integer> var_memoria, HashMap<String, Integer> var_por_referencia) ;
	
	public abstract int identificadorIns();
} 