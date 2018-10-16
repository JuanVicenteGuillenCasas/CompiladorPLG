package arbolClases;

import java.util.HashMap;

import errores.GestionErrores;

public class FuncionesAndCtes {
	
	private HashMap<String,Funtion> listadoFunciones;
	private  HashMap<String,Constante> listadoConstantes;
	
	public FuncionesAndCtes(Funtion f, FuncionesAndCtes fc){
		GestionErrores err = new GestionErrores();
		listadoFunciones = new HashMap<String, Funtion>();
		if(fc.getListFun() != null && fc.getListFun().containsKey(f.devuelveID())) 
			err.errorRepiteDefinicion(f.devuelveID()); //error
		listadoFunciones.put(f.devuelveID(),f);
		if(fc.getListFun() != null) listadoFunciones.putAll(fc.getListFun());
		if(fc.getListCte() != null) listadoConstantes = fc.getListCte();
	} 
	
	public FuncionesAndCtes(Constante c, FuncionesAndCtes fc){
		GestionErrores err = new GestionErrores();
		listadoConstantes = new HashMap<String, Constante>();
		if(fc.getListCte() != null && fc.getListCte().containsKey(c.identificadorCte())) 
			err.errorRepiteDefinicion(c.identificadorCte());//error 
		listadoConstantes.put(c.identificadorCte(), c);
		if(fc.getListCte() != null) listadoConstantes.putAll(fc.getListCte());
		if(fc.getListFun() != null) listadoFunciones = fc.getListFun();
	}
	
	public FuncionesAndCtes(Funtion f){
		listadoConstantes = new HashMap<String, Constante>();
		listadoFunciones = new HashMap<String, Funtion>();
		listadoFunciones.put(f.devuelveID(),f);
	}
	
	public FuncionesAndCtes(Constante c){
		listadoFunciones = new HashMap<String, Funtion>();
		listadoConstantes = new HashMap<String,Constante>();
		listadoConstantes.put(c.identificadorCte(),c);
	}

	public HashMap<String,Constante> getListCte() {
		return listadoConstantes;
	}

	public HashMap<String,Funtion> getListFun() {
		return listadoFunciones;
	}
	
}