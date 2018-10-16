package arbolClases;

import java.util.HashMap;

import CodigoMaquina.metodos_traduccion;
import errores.GestionErrores;

public class InstruccionReturn extends ClaseInstruccion {
	
	private Expresion expDev;
	
	private TipoVar.tipo_var tip;
	
	private GestionErrores err;
	
	public InstruccionReturn(Expresion e){
		this.expDev = e;
		err = new GestionErrores();
	}

	@Override
	public void addGeneric(HashMap<String, Constante> ctes, HashMap<String, Funtion> funciones) {	
		expDev.addExternal(ctes, funciones);
		if(tip == null) 
			err.error_returnNoDebido(); // error
		if(expDev.tipo() != null && !tip.equals(expDev.tipo()))  
			err.errorTipos_tipoErroneo(expDev.toString()); // error
		if(expDev.tipo() != null && expDev.esArray()) 
			err.errorTipos_tipoErroneo(expDev.toString()); // error
	}

	@Override
	public void addEntorno(HashMap<String, Tipo> var) {
		expDev.addEntorno(var);
	}

	@Override
	public void addEntornoFun(String identifier, Parametros p, TipoVar tipDev) {
		expDev.addEntornoFun(identifier,p,tipDev);
		tip = tipDev.getType();
		if(tip == null) err.error_returnNoDebido();
		if(expDev.tipo() != null && !tip.equals(expDev.tipo())) 
			err.errorTipos_tipoErroneo(expDev.toString()); // error
		if(expDev.tipo() != null && expDev.esArray()) 
			err.errorTipos_tipoErroneo(expDev.toString()); // error
	}

	@Override
	public void addEntornoSub(HashMap<String, Tipo> var) {
		expDev.addEntorno(var);
	}

	@Override
	public int getLV() {
		int lv = 0;
		lv = Math.max(lv, expDev.getLV()); 
		return lv;
	}

	@Override
	public void traduccion(metodos_traduccion trad, HashMap<String, Integer> var_memoria, HashMap<String, Integer> var_por_referencia) {
		trad.cargar_MP();
		expDev.calculaValor(trad, var_memoria, var_por_referencia);
		trad.inserta();
		trad.instruccionReturnConValor();
	}

	@Override
	public int identificadorIns() { 
		return 4;
	} 
	
}