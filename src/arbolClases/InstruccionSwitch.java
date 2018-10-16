package arbolClases;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import CodigoMaquina.metodos_traduccion;
import errores.GestionErrores;

public class InstruccionSwitch extends ClaseInstruccion{
	
	private String identifVar;
	
	private boolean enc;
	
	private HashMap<Integer, ClaseBloque> valor_bloque_case;
	
	private GestionErrores err;
		
	
	public InstruccionSwitch(String id, ClaseCases cs){
		err = new GestionErrores();
		identifVar = id;
		valor_bloque_case = cs.getMap();
		enc = false;
		
		int i = 0; 
		for(Integer j : valor_bloque_case.keySet()) {
			if(!j.equals(i)) 
				err.error_caseMal();			
			i++;
		}
	}
	
	@Override
	public void addGeneric(HashMap<String, Constante> ctes, HashMap<String, Funtion> funciones) {
		if(enc == false) err.errorTipos_VariableUndef(identifVar); //error	
		for(ClaseBloque b : valor_bloque_case.values() ) 
				b.addExternal(ctes, funciones); 	
		
	}

	@Override
	public void addEntorno(HashMap<String, Tipo> var) {
		if(enc == false) {			
			if(var.containsKey(identifVar)) {
				enc = true;
				if(var.get(identifVar).esArray()) err.errorTipos_tipoErroneo(identifVar); //error
				if(!var.get(identifVar).getType().equals(TipoVar.tipo_var.entero)) 
					err.errorTipos_tipoErroneo(identifVar); //error
			}
		}		
	
		for(ClaseBloque b : valor_bloque_case.values() ) 
			b.addEntornoBloquePadre(var);  
			
		
	}

	@Override
	public void addEntornoFun(String identifier, Parametros p, TipoVar tipDev) {
		if(enc == false) {			
			if(p.getMap().containsKey(identifVar)) {
				enc = true;
				if(p.getMap().get(identifVar).esArray()) err.errorTipos_tipoErroneo(identifVar); //error
				if(!p.getMap().get(identifVar).getType().equals(TipoVar.tipo_var.entero)) 
					err.errorTipos_tipoErroneo(identifVar); //error
			}
		}
		
		for(ClaseBloque b : valor_bloque_case.values() ) 
			b.getVars_fromFuntion(identifier, p, tipDev);  
		
		
	}

	@Override
	public void addEntornoSub(HashMap<String, Tipo> var) {
		if(enc == false) {			
			if(var.containsKey(identifVar)) {
				enc = true;
				if(var.get(identifVar).esArray()) err.errorTipos_tipoErroneo(identifVar); //error
				if(!var.get(identifVar).getType().equals(TipoVar.tipo_var.entero)) 
					err.errorTipos_tipoErroneo(identifVar); //error
			}
		}
				
		for(ClaseBloque b : valor_bloque_case.values() ) 
			b.addEntornoBloquePadre(var); 			
		
	}

	@Override
	public int getLV() {
		int lv = 1;
		
		for(ClaseBloque b: valor_bloque_case.values()) lv = Math.max(lv, b.getLV());
		
		return lv;
	}

	@Override
	public void traduccion(metodos_traduccion trad, HashMap<String, Integer> var_memoria, HashMap<String, Integer> var_por_referencia) {
		int linea, q;
		List<Integer> lista_lineasUp = new ArrayList<Integer>();
		List<Integer> lista_lineasDown = new ArrayList<Integer>();
		trad.CargaVarSing(var_memoria.get(identifVar)); 
		trad.cargaValor();
		trad.negarCima();
		linea = trad.reservaLinea();
		for(ClaseBloque b: valor_bloque_case.values()) {
			lista_lineasUp.add(trad.devuelveContIns());
			b.varExternas(var_memoria, var_por_referencia); 
			b.traduccion(trad);
			lista_lineasDown.add(trad.reservaLinea());
		}
		for(int i = valor_bloque_case.size() - 1; i >= 0; i--) {
			trad.saltoIncond(lista_lineasUp.get(i) , trad.devuelveContIns());
		}
		q = trad.devuelveContIns() - 1;
		for(int i = 0; i < valor_bloque_case.size(); i++) {
			trad.saltoIncond(trad.devuelveContIns(), lista_lineasDown.get(i)); 
		}
		trad.saltoCalculado(q, linea);
	}

	@Override
	public int identificadorIns() { 
		return 5;
	}

	public int getLE() {
		int le = 0;
		
		for(ClaseBloque b : valor_bloque_case.values()) le += b.getLE();
		
		return le;
	}

		
}