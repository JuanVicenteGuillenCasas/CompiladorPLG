package arbolClases;

import java.util.HashMap;

import CodigoMaquina.metodos_traduccion;

public class ClaseBloque extends InsOrBloque {
	
	private DeclaracionBloque cuerpo;
	
	private HashMap<String, Tipo> entornoInterno;
	
	private HashMap<String, Integer> var_memoria;
	
	private HashMap<String, Integer> var_por_referencia;
	
	private int le;
		
	public ClaseBloque(DeclaracionVariables dv, DeclaracionBloque db){
		le = 0;
		cuerpo = db;
		entornoInterno = dv.getMap();
		cuerpo.entorno(entornoInterno); 
		var_memoria = new HashMap<String, Integer>();
		var_por_referencia = new HashMap<String, Integer>();
	}
	
	public ClaseBloque(DeclaracionBloque db){
		le = 0;
		cuerpo = db;
		entornoInterno = new HashMap<String,Tipo>();
		var_memoria = new HashMap<String, Integer>();
		var_por_referencia = new HashMap<String, Integer>();
	}

	public void getVars_fromFuntion(String identifier, Parametros p, TipoVar tipDev) { // Si es un bloque de funcion, meter el valor a devolver y los parametros.
		 cuerpo.entornoFuncion(identifier,p,tipDev);			 
	}

	public void addExternal(HashMap<String, Constante> lista_constantes, HashMap<String, Funtion> lista_funciones) {
		cuerpo.add(lista_constantes,lista_funciones);
	}

	public void addEntornoBloquePadre(HashMap<String, Tipo> e) {
		cuerpo.entornoSub(e); 
	}
	
	public boolean tieneReturn() {
		return cuerpo.tieneReturn();
	}

	public void traduccion(metodos_traduccion trad) {
		//Registramos variables locales.
		for(String l :  entornoInterno.keySet()) {
			int i;
			Tipo t = entornoInterno.get(l); 
			if(t.esArray()) {
				i = trad.defineArray(((TipoArray)t).devuelveListaDim());
				if(var_por_referencia.containsKey(l)) var_por_referencia.remove(l); //Se sobreescribe el parametro por referencia por una variable local.
			}
			else i = trad.defineVariableSingular();
			var_memoria.put(l, i);
		}
		
		cuerpo.traduccion(trad, var_memoria, var_por_referencia);
		
	}

	public int getLE() {
		
		for(Tipo t : entornoInterno.values()) {
			if(t.esArray()) {
				int tam = 1;
				for(Integer s : ((TipoArray)t).devuelveListaDim()) tam = tam * s;
				le += tam;
			}
			else le += 1;
		}
		
		for(ClaseBloque b : cuerpo.getBH()) le += b.getLE();
		
		for(ClaseInstruccion i : cuerpo.getIns()) {
			if(i.identificadorIns() == 2 ) {
				le += ((InstruccionFor)i).getLE();
			}
			else if(i.identificadorIns() == 3) {
				le += ((InstruccionIfElse)i).getLE();
			}
			else if(i.identificadorIns() == 5) {
				le += ((InstruccionSwitch)i).getLE();
			}
			else if(i.identificadorIns() == 6) {
				le += ((InstruccionWhile)i).getLE();
			}			
		}
		
		return le;
	} 

	public int getLV() {
		return cuerpo.getLV();
	}

	public void registraParametros(Parametros parameter, metodos_traduccion trad) {
		int i = 0;
		for(String l : parameter.devuelveListaOrdenada()) {
			Tipo t = parameter.getMap().get(l);
			if(t.esArray()) { 
				i = trad.defineVariableSingular(); //Se pasan por referencia, ocupan 1.
				var_por_referencia.put(l, i);
			}
			else i = trad.defineVariableSingular();
			var_memoria.put(l, i);
		}		
	}

	public void varExternas(HashMap<String, Integer> vars, HashMap<String, Integer> por_ref) {
		for(String l :  vars.keySet()) {
			int i = vars.get(l);
			var_memoria.put(l, i);
		} 		
		for(String s : por_ref.keySet())  var_por_referencia.put(s, por_ref.get(s)); 
	}

	@Override
	public boolean soyInstruccion() {
		return false;
	}
	
}