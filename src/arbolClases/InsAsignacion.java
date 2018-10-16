package arbolClases;

import java.util.HashMap;
import java.util.List;

import CodigoMaquina.metodos_traduccion;
import arbolClases.Tipo.tipo_var;
import errores.GestionErrores;

public class InsAsignacion extends ClaseInstruccion {
	
	private String identVar;
	
	private tipo_var tipoIden;
	
	private Expresion expresionAsignar;
	
	private List<Integer> coordenadasAsignar;
	
	private List<Integer> dimVar;
	
	private GestionErrores err;
	
	
	public InsAsignacion(String id, Expresion e){
		err = new GestionErrores();
		identVar = id;
		expresionAsignar = e;	
	}
	
	public InsAsignacion(String id , ClaseDimensiones acd , Expresion e ){
		err = new GestionErrores();
		identVar = id;
		coordenadasAsignar = acd.getListDim();
		expresionAsignar = e;
	}

	@Override
	public void addGeneric(HashMap<String, Constante> ctes, HashMap<String, Funtion> funciones) { // esta es la ultima que se hace.
		
		if(tipoIden == null) err.errorTipos_VariableUndef(identVar); // error
		expresionAsignar.addExternal(ctes,funciones);
		if(expresionAsignar.tipo() == null || !tipoIden.equals(expresionAsignar.tipo())) { //Aqui sabemos que no llegan ids de arrays molestos
			err.errorTipos_tipoErroneo(expresionAsignar.toString()); 
		} // error
				
	}

	@Override
	public void addEntorno(HashMap<String, Tipo> var) {
		
		expresionAsignar.addEntorno(var);
		
		//Busca el identificador y chekea con la expresion si procede
		
		if( var.get(identVar) != null && !var.get(identVar).esArray()) {
			if(tipoIden == null) tipoIden = var.get(identVar).getType();		
		}
			
		else if(var.get(identVar) != null && var.get(identVar).esArray()) { 
			if(tipoIden == null) {
				if(!((TipoArray) var.get(identVar)).cumpleDimensiones(coordenadasAsignar)) {
					err.errorTipos_Dimensiones_MalPuestas(identVar);
				} // se accede mal a la celda
				dimVar = ((TipoArray)var.get(identVar)).devuelveListaDim();
				tipoIden = var.get(identVar).getType();	
			}							
		}
		
		if(tipoIden != null && expresionAsignar.tipo() != null) {
				if(expresionAsignar.esArray() || !expresionAsignar.tipo().equals(tipoIden)) 
					err.errorTipos_tipoErroneo(expresionAsignar.toString());  // error de tipos
		}	
			
	}

	@Override
	public void addEntornoFun(String identifier, Parametros p, TipoVar tipDev) {
		expresionAsignar.addEntornoFun(identifier,p,tipDev);
			
		//Busca el identificador y chekea con la expresion si procede
		if( p.getMap().get(identVar) != null && !p.getMap().get(identVar).esArray()) {
			if(tipoIden == null) tipoIden =  p.getMap().get(identVar).getType();			
		} 
		
		else if( p.getMap().get(identVar) != null && p.getMap().get(identVar).esArray()) {
			if(tipoIden == null) {
				if( !((TipoArray) p.getMap().get(identVar)).cumpleDimensiones(coordenadasAsignar)) {
					err.errorTipos_Dimensiones_MalPuestas(identVar);
				} //error de acceso array
				dimVar = ((TipoArray)p.getMap().get(identVar)).devuelveListaDim();
				tipoIden = p.getMap().get(identVar).getType();
			}		
		}
		
		if(tipoIden != null && expresionAsignar.tipo() != null) {
				if(expresionAsignar.esArray() || !expresionAsignar.tipo().equals(tipoIden)) 
					err.errorTipos_tipoErroneo(expresionAsignar.toString()); // error tipos		
		}	
		
	}

	@Override
	public void addEntornoSub(HashMap<String, Tipo> var) {
		expresionAsignar.addEntornoSub(var);
					
		//Busca el identificador y chekea con la expresion si procede
				
			if( var.get(identVar) != null && !var.get(identVar).esArray()) {
				if(tipoIden == null) tipoIden = var.get(identVar).getType();		
			}
				
			else if(var.get(identVar) != null && var.get(identVar).esArray()) {
				if(tipoIden == null) {
					if(!((TipoArray) var.get(identVar)).cumpleDimensiones(coordenadasAsignar)) {
						err.errorTipos_Dimensiones_MalPuestas(identVar);
					} // se accede mal a la celda
					dimVar = ((TipoArray)var.get(identVar)).devuelveListaDim();
					tipoIden = var.get(identVar).getType();	
				}							
			}
			
			if(tipoIden != null && expresionAsignar.tipo() != null) {
					if(expresionAsignar.esArray() || !expresionAsignar.tipo().equals(tipoIden)) 
						err.errorTipos_tipoErroneo(expresionAsignar.toString()); // error de tipos
			}	
	}
	
	public TipoVar.tipo_var tipo(){
		return tipoIden;
	}

	@Override
	public int getLV() {
		int lv = 2; //como minimo sabemos que dos cosas se tienen que apilar al ser una asignacion.
		
		lv = Math.max(lv, 1 + expresionAsignar.getLV());
		
		return lv;
	}

	@Override
	public void traduccion(metodos_traduccion trad, HashMap<String, Integer> var_memoria ,HashMap<String, Integer> var_por_referencia) {
		int dir = var_memoria.get(identVar);
		if(dimVar != null && coordenadasAsignar != null) {
			if(!var_por_referencia.containsKey(identVar)) trad.CargaVarArray(dimVar, coordenadasAsignar, dir);	
			else trad.CargaVarArrayReferencia(dimVar, coordenadasAsignar, dir);
		}
		else trad.CargaVarSing(dir);
		
		expresionAsignar.calculaValor(trad, var_memoria,var_por_referencia); 
		
		trad.inserta();
		
	}

	@Override
	public int identificadorIns() {
		return 0;
	}
	
}