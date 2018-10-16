package arbolClases;

import java.util.HashMap;
import java.util.List;

import CodigoMaquina.metodos_traduccion;
import arbolClases.Tipo.tipo_var;
import errores.GestionErrores;

public class ExpresionUnaria extends Expresion{
	
	private TipoVar.tipo_var tipo;  
	
	private enum tipo_expresion_final {entero_booleano , expresion_parentesis , negar_expresion, identificador, llamada, array_celda }; // identificador_array cuando encontramos array con sus indices []
	
	private tipo_expresion_final t;
	
	private String identificador; // puede ser de funcion, variable o array;
	
	private InstruccionCall llamada;
	 
	private List<Integer> lista_coord;
	 
	private List<Integer> lista_dim;
	
	private Expresion expresion_par;
	
	private boolean Idarray;
	
	GestionErrores err;
	
	private String simbol;
	
	private Constante cte; //Solo se rellena si es un identificador de una constante.
	
	public ExpresionUnaria(String simbolo, String st){
		err = new GestionErrores();
		simbol = simbolo;
		
		if(st.equals("int"))  {	
			tipo = TipoVar.tipo_var.entero;
			t = tipo_expresion_final.entero_booleano;
		}
		
		else if(st.equals("bool")) {
			tipo = TipoVar.tipo_var.booleano;
			t = tipo_expresion_final.entero_booleano;
			
		}
		else {
			t = tipo_expresion_final.identificador;
			identificador = simbolo;
		}
		Idarray = false;	
	}
	
	public ExpresionUnaria(InstruccionCall ic){
		err = new GestionErrores();
		
		t = tipo_expresion_final.llamada;
		llamada = ic;
		Idarray = false;	
	}

	public ExpresionUnaria(String iden, ClaseDimensiones dim) { 
		err = new GestionErrores();
		
		t = tipo_expresion_final.array_celda; 
		identificador = iden;
		lista_coord = dim.getListDim();
		Idarray = false;	//falso porque accedes a una celda, no al array en sí
	}
	
	public ExpresionUnaria(Expresion e){
		err = new GestionErrores();
		
		t = tipo_expresion_final.expresion_parentesis;
		expresion_par = e;
		if(e.tipo() != null) tipo = e.tipo();
		Idarray = false;
	}
	
	public ExpresionUnaria(Expresion e, String sim){
		err = new GestionErrores();
		
		t = tipo_expresion_final.negar_expresion;
		expresion_par = e;
		if(e.tipo() != null) {
			if(!e.tipo().equals(tipo_var.booleano)) err.errorTipos_tipoErroneo(e.toString()); //error
			tipo = e.tipo();
		}
		Idarray = false;
	}

	public TipoVar.tipo_var tipo() {
		return tipo;
	}

	@Override
	public void addExternal(HashMap<String, Constante> ctes, HashMap<String, Funtion> funciones) { // ultima que se hace...
		if(t.equals(tipo_expresion_final.identificador) && tipo == null) {
			if(ctes.containsKey(identificador)) {
				tipo = ctes.get(identificador).getType().getType();
				cte =  ctes.get(identificador);
			}	
		}
		else if(t.equals(tipo_expresion_final.llamada)) {
			if(!funciones.containsKey(llamada.getId())) 
				err.errorFuncionInexistente(llamada.getId()); // error
			llamada.addGeneric(ctes, funciones); 
			if(tipo == null && llamada.getReturnValue() != null) tipo = llamada.getReturnValue();
		}
		
		else if(t.equals(tipo_expresion_final.expresion_parentesis)) {
			expresion_par.addExternal(ctes, funciones);
			if(tipo == null && expresion_par.tipo() != null) tipo = expresion_par.tipo();
		}
		
		else if(t.equals(tipo_expresion_final.negar_expresion)) {
			expresion_par.addExternal(ctes, funciones);
			if(tipo == null && expresion_par.tipo() != null) {
				if(!expresion_par.tipo().equals(tipo_var.booleano)) 
					err.errorTipos_tipoErroneo(expresion_par.toString()); // error
				tipo = expresion_par.tipo();
			}
		}
		
		if(tipo == null) {
			err.errorTipos_VariableUndef("la expresion unaria"); 
		} //error gordo.
	}

	@Override
	public void addEntorno(HashMap<String, Tipo> var) {
		if(t.equals(tipo_expresion_final.identificador) && tipo == null) {
			if(var.containsKey(identificador)) {
				if(var.get(identificador).esArray()) { Idarray = true;	lista_dim = ((TipoArray)var.get(identificador)).devuelveListaDim(); }
				tipo = var.get(identificador).getType();
			}
				
		}
		else if(t.equals(tipo_expresion_final.array_celda)  && tipo == null) {
			if(var.containsKey(identificador)) {
				if (!var.get(identificador).esArray()) err.errorTipos_tipoErroneo(identificador); //error
				if(!((TipoArray) var.get(identificador)).cumpleDimensiones(lista_coord)) {
					err.errorTipos_Dimensiones_MalPuestas(identificador);
				}//error
				lista_dim = ((TipoArray)var.get(identificador)).devuelveListaDim();
				tipo = var.get(identificador).getType();
			}
		}
		else if(t.equals(tipo_expresion_final.llamada)) {
			llamada.addEntorno(var);		
		}
		else if(t.equals(tipo_expresion_final.expresion_parentesis) ) {
			expresion_par.addEntorno(var); 
			if(tipo == null && expresion_par.tipo() != null) tipo = expresion_par.tipo();
		}
		else if(t.equals(tipo_expresion_final.negar_expresion)) {
			expresion_par.addEntorno(var);
			if(tipo == null && expresion_par.tipo() != null) {
				if(!expresion_par.tipo().equals(tipo_var.booleano)) 
					err.errorTipos_tipoErroneo(expresion_par.toString()); // error
				tipo = expresion_par.tipo();
			}
		}
	}

	@Override
	public void addEntornoFun(String identifier, Parametros p, TipoVar tipDev) {
		if(t.equals(tipo_expresion_final.identificador) && tipo == null) {
			if(p.getMap().containsKey(identificador)) {
			  if(p.getMap().get(identificador).esArray()) {Idarray = true ; lista_dim = ((TipoArray)p.getMap().get(identificador)).devuelveListaDim(); }
			  tipo = p.getMap().get(identificador).getType();
			}
		}
		else if(t.equals(tipo_expresion_final.array_celda)  && tipo == null) {
			if(p.getMap().containsKey(identificador)) {
				if (!p.getMap().get(identificador).esArray()) err.errorTipos_tipoErroneo(identificador); //error
				if(!((TipoArray) p.getMap().get(identificador)).cumpleDimensiones(lista_coord)) {
					err.errorTipos_Dimensiones_MalPuestas(identificador); 
				}//error
				lista_dim = ((TipoArray)p.getMap().get(identificador)).devuelveListaDim();
				tipo = p.getMap().get(identificador).getType();
			}
		} 
		else if(t.equals(tipo_expresion_final.llamada)) {
			llamada.addEntornoFun(identifier,p,tipDev);
		}
		else if(t.equals(tipo_expresion_final.expresion_parentesis) ) {
			expresion_par.addEntornoFun(identifier, p, tipDev);
			if(tipo == null && expresion_par.tipo() != null) tipo = expresion_par.tipo();
		}
		else if(t.equals(tipo_expresion_final.negar_expresion)) {
			expresion_par.addEntornoFun(identifier, p, tipDev); 
			if(tipo == null && expresion_par.tipo() != null) {
				if(!expresion_par.tipo().equals(tipo_var.booleano)) 
					err.errorTipos_tipoErroneo(expresion_par.toString());  // error
				tipo = expresion_par.tipo();
			}
		}
	}

	@Override
	public void addEntornoSub(HashMap<String, Tipo> var) {
		if(t.equals(tipo_expresion_final.identificador) && tipo == null) {
			if(var.containsKey(identificador)) {
				if(var.get(identificador).esArray()) {Idarray = true; lista_dim = ((TipoArray)var.get(identificador)).devuelveListaDim(); }
				tipo = var.get(identificador).getType();
			}
				
		}
		else if(t.equals(tipo_expresion_final.array_celda)  && tipo == null) {
			if(var.containsKey(identificador)) {
				if (!var.get(identificador).esArray()) err.errorTipos_tipoErroneo(identificador); //error
				if(!((TipoArray) var.get(identificador)).cumpleDimensiones(lista_coord)) {
					err.errorTipos_Dimensiones_MalPuestas(identificador);
				} //error
				lista_dim = ((TipoArray)var.get(identificador)).devuelveListaDim(); 
				tipo = var.get(identificador).getType();
			}
		}
		else if(t.equals(tipo_expresion_final.llamada)) {
			llamada.addEntornoSub(var);
		}
		
		else if(t.equals(tipo_expresion_final.expresion_parentesis)) {
			expresion_par.addEntornoSub(var); 
			if(tipo == null && expresion_par.tipo() != null) tipo = expresion_par.tipo();
		}
		else if(t.equals(tipo_expresion_final.negar_expresion)) {
			expresion_par.addEntornoSub(var); 
			if(tipo == null && expresion_par.tipo() != null) {
				if(!expresion_par.tipo().equals(tipo_var.booleano)) 
					err.errorTipos_tipoErroneo(expresion_par.toString()); // error
				tipo = expresion_par.tipo();
			}
		} 
	}

	
	public  List<Integer> devuelveListaCoord(){
		return lista_coord;
	}
	
	public List<Integer> devuelveDim(){
		return lista_dim;
	}
	
	
	@Override
	public boolean esArray() {	
		return Idarray;
	}

	@Override
	public String toString() {
		String res = "";
		if(t.equals(tipo_expresion_final.entero_booleano)) res = simbol;
		if(t.equals(tipo_expresion_final.array_celda)) res = identificador + "[array]";
		if(t.equals(tipo_expresion_final.identificador)) res = identificador;
		if(t.equals(tipo_expresion_final.llamada)) res = llamada.getId();
		if(t.equals(tipo_expresion_final.negar_expresion)) res = "--" + "(" + expresion_par.toString()  +")";
		if(t.equals(tipo_expresion_final.expresion_parentesis)) res = "(" + expresion_par.toString()  +")";
		return res;
	}

	@Override
	public void calculaValor(metodos_traduccion trad,  HashMap<String, Integer> var_memoria, HashMap<String, Integer> var_por_referencia) {
		if(t.equals(tipo_expresion_final.entero_booleano)) {
			trad.apila(simbol); 
		}
		if(t.equals(tipo_expresion_final.array_celda)) {
			if(!var_por_referencia.containsKey(identificador)) {
				trad.CargaVarArray(lista_dim, lista_coord, var_memoria.get(identificador));
				trad.cargaValor();
			}
			else {
				trad.CargaVarArrayReferencia(lista_dim, lista_coord, var_memoria.get(identificador));
				trad.cargaValor();	
			}
		}
		if(t.equals(tipo_expresion_final.identificador)) {
			if(Idarray == true) {
				if(!var_por_referencia.containsKey(identificador))
					trad.CargaVarSing(var_memoria.get(identificador)); //cargamos simplemente la direccion donde empieza.
				else {
					trad.CargaVarSingReferencia(var_memoria.get(identificador)); //cargamos la direccion real que esta por referencia.
				}
			}
			else {
				if(!var_memoria.containsKey(identificador)) { //Si es una constante no renombrada dentro del bloque.
					trad.CargaCte(cte.devuelveIndice()); 
					trad.cargaValor();
				}
				else {
				trad.CargaVarSing(var_memoria.get(identificador));
				trad.cargaValor();
				}
			}
		}
		if(t.equals(tipo_expresion_final.llamada)) {
			llamada.traduccion(trad, var_memoria, var_por_referencia);
		}
		if(t.equals(tipo_expresion_final.negar_expresion)) {
			expresion_par.calculaValor(trad, var_memoria, var_por_referencia);
			trad.operaciones("!!");
		}
		if(t.equals(tipo_expresion_final.expresion_parentesis)) {
			expresion_par.calculaValor(trad, var_memoria, var_por_referencia); 
		}
	}

	@Override
	public int getLV() {
		int lv = 0; 
		
		if(t.equals(tipo_expresion_final.entero_booleano)) lv = 1;
		if(t.equals(tipo_expresion_final.array_celda)) lv = 2;
		if(t.equals(tipo_expresion_final.identificador)) lv = 1;
		if(t.equals(tipo_expresion_final.llamada)) lv = llamada.getLV();
		if(t.equals(tipo_expresion_final.negar_expresion)) lv = expresion_par.getLV();
		if(t.equals(tipo_expresion_final.expresion_parentesis)) lv = expresion_par.getLV();
		
		
		return lv;
	}
	
}