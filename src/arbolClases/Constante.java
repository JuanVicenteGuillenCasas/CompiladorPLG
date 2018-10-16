package arbolClases;

import CodigoMaquina.metodos_traduccion;
import errores.GestionErrores;

public class Constante {
	
	private int indiceMemoria;
	
	private String identifier;
	
	private Tipo tipo;
	
	private Expresion valor;
	
	public Constante(String s, Tipo tp, Expresion exp){
		GestionErrores e = new GestionErrores();
		this.identifier = s;
		this.tipo = tp;
		this.valor = exp;
		if(valor.tipo() == null) e.errorTipos_VariableUndef(s); // error que saldra si metemos identificadores.
	}
		
	public String identificadorCte() {
		return identifier;
	}
	
	public Expresion getValue() {
		return valor;
	}
	
	public int getLV() {
		int lv = 0;
		lv = Integer.max(lv, 1 + valor.getLV());
		return lv;
	}
		
	public Tipo getType() {
		return tipo;
	}

	public int devuelveIndice() {
		return indiceMemoria;
	} 
	
	public void traduccion(metodos_traduccion trad) { 
		indiceMemoria = trad.defineCte();
		trad.CargaCte(indiceMemoria); 
		valor.calculaValor(trad,null,null); 
		trad.inserta();
	}
	
}