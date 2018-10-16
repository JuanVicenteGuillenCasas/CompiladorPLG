package arbolClases;

import java.util.HashMap;
import errores.GestionErrores;

public class ClaseCases{
	
	private HashMap<Integer, ClaseBloque> exp_bl;
	
	public ClaseCases(String val, ClaseBloque b , ClaseCases cs){
		GestionErrores e = new GestionErrores();
		exp_bl = new HashMap<Integer,ClaseBloque>();
		exp_bl.put(Integer.valueOf(val), b);
		if(cs.getMap().containsKey(Integer.valueOf(val))) e.errorRepiteDefinicion(val); // error case repetido
		exp_bl.putAll(cs.getMap()); 
	}
	public ClaseCases(String val, ClaseBloque b ){
		exp_bl = new HashMap<Integer,ClaseBloque>();
		exp_bl.put(Integer.valueOf(val), b);
	}
	public HashMap<Integer, ClaseBloque> getMap() {
		return exp_bl;
	}
}