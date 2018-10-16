package arbolClases;

import java.util.HashMap;

import CodigoMaquina.metodos_traduccion;
import errores.GestionErrores;

public class InstruccionWhile extends ClaseInstruccion{
	
	private Expresion valorWhile;
	
	private ClaseBloque bloque;
	
	private GestionErrores err; 
	
	public InstruccionWhile(Expresion e, ClaseBloque b){
		err = new GestionErrores();
		valorWhile = e;
		bloque = b;
		if(e.tipo() != null && !e.tipo().equals(TipoVar.tipo_var.booleano))
			err.errorTipos_tipoErroneo(valorWhile.toString()); // error
	}

	@Override
	public void addGeneric(HashMap<String, Constante> ctes, HashMap<String, Funtion> funciones) {
		valorWhile.addExternal(ctes, funciones); 
		bloque.addExternal(ctes, funciones); 
		if(valorWhile.tipo() == null || !valorWhile.tipo().equals(TipoVar.tipo_var.booleano)) 
			err.errorTipos_tipoErroneo(valorWhile.toString()); // error
	}

	@Override
	public void addEntorno(HashMap<String, Tipo> var) {
		valorWhile.addEntorno(var); 
		bloque.addEntornoBloquePadre(var);  
		if(valorWhile.tipo() != null && !valorWhile.tipo().equals(TipoVar.tipo_var.booleano))
			err.errorTipos_tipoErroneo(valorWhile.toString()); //error
	}

	@Override
	public void addEntornoFun(String identifier, Parametros p, TipoVar tipDev) {
		valorWhile.addEntornoFun(identifier,p,tipDev); 
		bloque.getVars_fromFuntion(identifier, p, tipDev); 
		if(valorWhile.tipo() != null && !valorWhile.tipo().equals(TipoVar.tipo_var.booleano)) 
			err.errorTipos_tipoErroneo(valorWhile.toString()); //error
	}

	@Override
	public void addEntornoSub(HashMap<String, Tipo> var) {
		valorWhile.addEntornoSub(var); 
		bloque.addEntornoBloquePadre(var);  
		if(valorWhile.tipo() != null && !valorWhile.tipo().equals(TipoVar.tipo_var.booleano)) 
			err.errorTipos_tipoErroneo(valorWhile.toString()); //error
	}

	@Override
	public int getLV() {
		int lv = 0;
		
		lv = Math.max(lv, valorWhile.getLV());
		lv = Math.max(lv, bloque.getLV());
		
		return lv;
	}

	@Override
	public void traduccion(metodos_traduccion trad, HashMap<String, Integer> var_memoria, HashMap<String, Integer> var_por_referencia) {
		int linea1, linea2;
		linea1 = trad.devuelveContIns();
		valorWhile.calculaValor(trad, var_memoria, var_por_referencia); 
		linea2 = trad.reservaLinea();
		bloque.varExternas(var_memoria, var_por_referencia);
		bloque.traduccion(trad); 
		trad.saltoIncond(linea1 , trad.devuelveContIns());
		trad.saltoCondicion(trad.devuelveContIns(), linea2); 
	}

	@Override
	public int identificadorIns() { 
		return 6;
	}

	public int getLE() {
		int le;
		le = bloque.getLE();
		return le; 
	}

	
}