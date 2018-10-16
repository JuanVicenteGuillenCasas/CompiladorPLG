package arbolClases;

import java.util.HashMap;

import CodigoMaquina.metodos_traduccion;
import errores.GestionErrores;

public class InstruccionIfElse extends ClaseInstruccion{
	
	private enum tipoif {IFELSE, IF};
	
	private tipoif tip;
	
	private Expresion condicion;
	
	private ClaseBloque bloque1;
	
	private ClaseBloque bloque2;
	
	private GestionErrores err;
	
	public InstruccionIfElse(Expresion e, ClaseBloque b){
		err = new GestionErrores();
		this.tip = tipoif.IF;
		this.condicion = e;
		this.bloque1 = b;
		if(e.tipo() != null && !e.tipo().equals(TipoVar.tipo_var.booleano)) 
			err.errorTipos_tipoErroneo(condicion.toString()); // erroor
	}
	
	public InstruccionIfElse(Expresion e, ClaseBloque b1, ClaseBloque b2){
		err = new GestionErrores();
		this.tip = tipoif.IFELSE;
		this.condicion = e;
		this.bloque1 = b1;
		this.bloque2 = b2;
		if(e.tipo() != null && !e.tipo().equals(TipoVar.tipo_var.booleano)) 
			err.errorTipos_tipoErroneo(condicion.toString()); // erroor
	}

	@Override
	public void addGeneric(HashMap<String, Constante> ctes, HashMap<String, Funtion> funciones) {
		if(tip.equals(tipoif.IF)) {
			condicion.addExternal(ctes, funciones);
			bloque1.addExternal(ctes, funciones);
		}
		else if(tip.equals(tipoif.IFELSE)) {
			condicion.addExternal(ctes, funciones);
			bloque1.addExternal(ctes, funciones);
			bloque2.addExternal(ctes, funciones);
		}	
		if(condicion.tipo() == null || !condicion.tipo().equals(TipoVar.tipo_var.booleano)) 
			err.errorTipos_tipoErroneo(condicion.toString()); // error
	}

	@Override
	public void addEntorno(HashMap<String, Tipo> var) {
		if(tip.equals(tipoif.IF)) {
			condicion.addEntorno(var);
			bloque1.addEntornoBloquePadre(var); 
		}
		else if(tip.equals(tipoif.IFELSE)) {
			condicion.addEntorno(var);
			bloque1.addEntornoBloquePadre(var); 
			bloque2.addEntornoBloquePadre(var); 
		}		
		if(condicion.tipo() != null && !condicion.tipo().equals(TipoVar.tipo_var.booleano)) 
			err.errorTipos_tipoErroneo(condicion.toString()); //error
	}

	@Override
	public void addEntornoFun(String identifier, Parametros p, TipoVar tipDev) {
		if(tip.equals(tipoif.IF)) {
			condicion.addEntornoFun(identifier,p,tipDev);
			bloque1.getVars_fromFuntion(identifier, p, tipDev); 
		}
		else if(tip.equals(tipoif.IFELSE)) {
			condicion.addEntornoFun(identifier,p,tipDev);
			bloque1.getVars_fromFuntion(identifier, p, tipDev);
			bloque2.getVars_fromFuntion(identifier, p, tipDev); 
		}		
		if(condicion.tipo() != null && !condicion.tipo().equals(TipoVar.tipo_var.booleano)) 
			err.errorTipos_tipoErroneo(condicion.toString()); //error
	}

	@Override
	public void addEntornoSub(HashMap<String, Tipo> var) {
		if(tip.equals(tipoif.IF)) {
			condicion.addEntornoSub(var);
			bloque1.addEntornoBloquePadre(var); 
		}
		else if(tip.equals(tipoif.IFELSE)) {
			condicion.addEntornoSub(var);
			bloque1.addEntornoBloquePadre(var); 
			bloque2.addEntornoBloquePadre(var); 		
		}		
		if(condicion.tipo() != null && !condicion.tipo().equals(TipoVar.tipo_var.booleano)) 
			err.errorTipos_tipoErroneo(condicion.toString()); //error
	}

	@Override
	public int getLV() {
		int lv = 0;
		
		lv = Math.max(lv, bloque1.getLV());
		if(bloque2 != null) lv = Math.max(lv, bloque2.getLV());
		lv = Math.max(lv, condicion.getLV());
		
		return lv;
	}

	@Override
	public void traduccion(metodos_traduccion trad, HashMap<String, Integer> var_memoria, HashMap<String, Integer> var_por_referencia) {
		
		condicion.calculaValor(trad, var_memoria, var_por_referencia);
		
		if(tip.equals(tipoif.IF)) {
			int linea = trad.reservaLinea();
			bloque1.varExternas(var_memoria, var_por_referencia);
			bloque1.traduccion(trad);
			trad.saltoCondicion(trad.devuelveContIns(), linea); 
		}
		else {
			int linea1, linea2;
			linea1 = trad.reservaLinea();
			bloque1.varExternas(var_memoria, var_por_referencia);
			bloque1.traduccion(trad);
			linea2 = trad.reservaLinea();
			trad.saltoCondicion(trad.devuelveContIns(), linea1);
			bloque2.varExternas(var_memoria, var_por_referencia);
			bloque2.traduccion(trad);
			trad.saltoIncond(trad.devuelveContIns(), linea2); 
		}	
	}

	@Override
	public int identificadorIns() {
		return 3;
	}

	public boolean tieneReturn() {
		if(tip.equals(tipoif.IF)) {
			return false; // no podemos asegurar que pasa por aqui con seguridad, por lo que no es seguro.
		}
		else {
			return bloque1.tieneReturn() && bloque2.tieneReturn(); //Tienen que tener return los dos. Para asegurarnos que pasa por el return.
		}
	}

	public int getLE() {
		int le;
		
		if(tip.equals(tipoif.IF)) {
			le = bloque1.getLE();
		}
			
		else {
			le = bloque1.getLE() + bloque2.getLE();
		}
		
		return le;
	} 
	
}