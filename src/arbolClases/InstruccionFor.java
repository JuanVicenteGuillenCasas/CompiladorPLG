package arbolClases;

import java.util.HashMap;

import CodigoMaquina.metodos_traduccion;
import errores.GestionErrores;

public class InstruccionFor extends ClaseInstruccion{
	
	private ClaseBloque bloqueFor;
	
	private InsAsignacion primeraAsignacion;
	
	private InsAsignacion segundaAsignacion;
	
	private Expresion CondicionFor;
	
	private GestionErrores err;
	
	public InstruccionFor(InsAsignacion a1, Expresion e , InsAsignacion a2, ClaseBloque b){
		err = new GestionErrores();
		this.primeraAsignacion = a1;
		this.segundaAsignacion = a2;
		this.CondicionFor = e;
		this.bloqueFor = b;
		if(a1.tipo() != null && a2.tipo() != null && !a1.tipo().equals(a2.tipo()))
			err.errorTipos_tipoErroneo(a1.toString()); // error en las asignaciones.
		if(e.tipo() != null && !e.tipo().equals(TipoVar.tipo_var.booleano))
			err.errorTipos_tipoErroneo(e.toString()); // error expresion
	}

	@Override
	public void addGeneric(HashMap<String, Constante> ctes, HashMap<String, Funtion> funciones) {
		primeraAsignacion.addGeneric(ctes, funciones);
		segundaAsignacion.addGeneric(ctes, funciones); 
		CondicionFor.addExternal(ctes, funciones); 
		bloqueFor.addExternal(ctes, funciones); 
		if(primeraAsignacion.tipo() == null || segundaAsignacion.tipo() == null || CondicionFor.tipo() == null) 
			err.errorTipos_VariableUndef("Alguna de las variables"); // error
		if(!primeraAsignacion.tipo().equals(segundaAsignacion.tipo()) || !CondicionFor.tipo().equals(TipoVar.tipo_var.booleano) ) 
			err.errorTipos_tipoErroneo("Alguna de las variables"); // error
	}

	@Override
	public void addEntorno(HashMap<String, Tipo> var) {
		primeraAsignacion.addEntorno(var);
		segundaAsignacion.addEntorno(var);
		CondicionFor.addEntorno(var);
		bloqueFor.addEntornoBloquePadre(var);
		if(primeraAsignacion.tipo() != null && segundaAsignacion.tipo() != null && !primeraAsignacion.tipo().equals(segundaAsignacion.tipo())) 
			err.errorTipos_tipoErroneo(primeraAsignacion.toString()); // error
		if(CondicionFor.tipo() != null && !CondicionFor.tipo().equals(TipoVar.tipo_var.booleano)) 
			err.errorTipos_tipoErroneo(CondicionFor.toString()); //error
	}

	@Override
	public void addEntornoFun(String identifier, Parametros p, TipoVar tipDev) {
		primeraAsignacion.addEntornoFun(identifier, p, tipDev);
		segundaAsignacion.addEntornoFun(identifier, p, tipDev);
		CondicionFor.addEntornoFun(identifier,p,tipDev);
		bloqueFor.getVars_fromFuntion(identifier, p, tipDev); 
		if(primeraAsignacion.tipo() != null && segundaAsignacion.tipo() != null && !primeraAsignacion.tipo().equals(segundaAsignacion.tipo())) 
			err.errorTipos_tipoErroneo(primeraAsignacion.toString()); // error
		if(CondicionFor.tipo() != null && !CondicionFor.tipo().equals(TipoVar.tipo_var.booleano))
			err.errorTipos_tipoErroneo(CondicionFor.toString()); //error
	}

	@Override
	public void addEntornoSub(HashMap<String, Tipo> var) {
		primeraAsignacion.addEntornoSub(var);
		segundaAsignacion.addEntornoSub(var);
		CondicionFor.addEntornoSub(var);
		bloqueFor.addEntornoBloquePadre(var);
		if(primeraAsignacion.tipo() != null && segundaAsignacion.tipo() != null && !primeraAsignacion.tipo().equals(segundaAsignacion.tipo())) 
			err.errorTipos_tipoErroneo(primeraAsignacion.toString()); // error
		if(CondicionFor.tipo() != null && !CondicionFor.tipo().equals(TipoVar.tipo_var.booleano)) 
			err.errorTipos_tipoErroneo(CondicionFor.toString()); //error
	}

	@Override
	public int getLV() {
		int lv = 0;
		
		lv = Math.max(lv, primeraAsignacion.getLV());
		lv = Math.max(lv, segundaAsignacion.getLV());
		lv = Math.max(lv, CondicionFor.getLV());
		lv = Math.max(lv, bloqueFor.getLV());
		
		return lv;
	}

	@Override
	public void traduccion(metodos_traduccion trad, HashMap<String, Integer> var_memoria, HashMap<String, Integer> var_por_referencia) {
		int linea1,linea2;
		primeraAsignacion.traduccion(trad, var_memoria, var_por_referencia);
		linea1 = trad.devuelveContIns();		
		CondicionFor.calculaValor(trad, var_memoria, var_por_referencia); 
		linea2 = trad.reservaLinea();
		bloqueFor.varExternas(var_memoria, var_por_referencia);
		bloqueFor.traduccion(trad); 
		segundaAsignacion.traduccion(trad, var_memoria, var_por_referencia);  
		trad.saltoIncond(linea1, trad.devuelveContIns() );
		trad.saltoCondicion(trad.devuelveContIns(), linea2);		
	}

	@Override
	public int identificadorIns() {
		return 2;
	}

	public int getLE() {
		int le ;
		le = bloqueFor.getLE();
		return le;
	}
	
}