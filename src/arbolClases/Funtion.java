package arbolClases;

import CodigoMaquina.metodos_traduccion;
import arbolClases.Tipo.tipo_var;
import errores.GestionErrores;

public class Funtion{
	
	private String identifier;
	
	private int le,lv;
	
	private Parametros parameter;
	
	private TipoVar devuelve;
	
	private ClaseBloque bloc;
	
	private int saltoCuerpo;
	
	public Funtion(String s, Parametros p, TipoVar tipDev, ClaseBloque b){
		GestionErrores err = new GestionErrores();
		this.identifier = s;
		this.parameter = p;
		this.devuelve = tipDev;
		this.bloc = b;
		
		if(tipDev.getType() != null && !bloc.tieneReturn()) //Puede saltar habiendo returns porque estos no sean seguros de que llegue la ejecucion.
			err.error_necesitaUnReturnSeguro(identifier); //Saltara un error si nuestro cuerpo no tiene una instruccion de return cuando deberia tenerla, este es el caso de las funciones que deben devolver algo.
		
		bloc.getVars_fromFuntion(identifier , parameter, devuelve); // Le pasamos a este bloque para caracterizarlo como funcion;
	}
	
	public String devuelveID() {
		return identifier;
	}
	
	public ClaseBloque devuelveBloque() {
		return bloc;
	}

	public Parametros devPar() {
		return parameter;
	}
	
	public tipo_var devuelveTipo(){
		return devuelve.getType();
	}
	
	public void traduccionPreambulo(metodos_traduccion trad) {
		trad.registraFuncion(identifier); 
	
		//calculo de le:variables del bloque, parametros y zona organizativa.
		le = bloc.getLE(); 
		le += parameter.devuelveListaOrdenada().size();
		le += 5;
		
		//calculo de lv:
		lv = bloc.getLV();	
		
		//escribimos nuestro preambulo:
		trad.preambuloFun(le, lv);
		
		saltoCuerpo = trad.reservaLinea();
		
	}

	public void traduccionCuerpoFun(metodos_traduccion trad) {
		//Ponemos el salto del preambulo a aqui.
		
		trad.saltoIncond(trad.devuelveContIns(), saltoCuerpo); 
		
		//Dejamos como nuevo el contador de variables para traducir la siguiente funcion:
		trad.reiniciaContadorFun();
		
		//traducimos nuestro bloque de la funcion
		bloc.registraParametros(parameter, trad); 
		bloc.traduccion(trad);
		trad.instruccionReturnSinValor();
		
	}
		
}