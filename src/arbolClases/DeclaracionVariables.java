package arbolClases;

import java.util.HashMap;

import errores.GestionErrores;

public class DeclaracionVariables{
	
	private HashMap<String, Tipo> variables;
	
	public DeclaracionVariables(Variable varr){
		variables = new HashMap<String, Tipo>(); 
		variables.put(varr.identifier(), varr.getType()); 
	}
	
	public DeclaracionVariables(Variable varr, DeclaracionVariables dv){
		GestionErrores e = new GestionErrores();
		variables = new HashMap<String, Tipo>();
		variables.put(varr.identifier(), varr.getType()); 
		if(dv.getMap().containsKey(varr.identifier())) e.errorRepiteDefinicion(varr.identifier()); // error de repeticion en una declaracion del mismo bloque.		
		variables.putAll(dv.getMap());
	}

	public HashMap<String, Tipo> getMap() {
		return variables;
	}
	
}