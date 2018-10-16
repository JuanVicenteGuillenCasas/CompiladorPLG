package arbolClases;

import java.util.HashMap;

import CodigoMaquina.metodos_traduccion;
import errores.GestionErrores;

public class ExpresionBinaria  extends Expresion{
	
	public enum operadoresBinarios  {MAS, MENOS, POR,ENTRE, MENIG, MAYIG,MEN, MAY, IG, NOIG, AND , OR, NINGUNO};
	
	private operadoresBinarios opUsado;
	
	private Tipo.tipo_var tipo; //tipo de variable si es variable.
	
	private Expresion exp0,exp1;
	
	GestionErrores err;
	
	public ExpresionBinaria(Expresion e0, Expresion e1, String operador){
		err = new GestionErrores();
		
		if(operador.equals("+")) opUsado = operadoresBinarios.MAS; 
		else if(operador.equals("-")) opUsado = operadoresBinarios.MENOS;
		else if(operador.equals("*")) opUsado = operadoresBinarios.POR;
		else if(operador.equals("/")) opUsado = operadoresBinarios.ENTRE;
		else if(operador.equals("<=")) opUsado = operadoresBinarios.MENIG;
		else if(operador.equals(">=")) opUsado = operadoresBinarios.MAYIG;
		else if(operador.equals("<")) opUsado = operadoresBinarios.MEN;
		else if(operador.equals(">")) opUsado = operadoresBinarios.MAY;
		else if(operador.equals("=")) opUsado = operadoresBinarios.IG;
		else if(operador.equals("!=")) opUsado = operadoresBinarios.NOIG;
		else if(operador.equals("OR")) opUsado = operadoresBinarios.OR;
		else if(operador.equals("AND")) opUsado = operadoresBinarios.AND; 

		exp0 = e0;
		exp1 = e1;
		
		if(operador.equals("+") || operador.equals("-") ||operador.equals("*") ||operador.equals("/") || 
				operador.equals("MAX") ||operador.equals("MIN")) tipo = TipoVar.tipo_var.entero;
		else  tipo = TipoVar.tipo_var.booleano; 		
		
		if(opUsado.equals(operadoresBinarios.OR) || opUsado.equals(operadoresBinarios.AND) ) {
			if(exp0.tipo() != null && !exp0.tipo().equals( TipoVar.tipo_var.booleano)) 
				err.error_expresionBin_conMalosTipos(opUsado.name());
			if(exp1.tipo() != null && !exp1.tipo().equals( TipoVar.tipo_var.booleano)) 
				err.error_expresionBin_conMalosTipos(opUsado.name()); 
		}
		else {
			if(exp0.tipo() != null && !exp0.tipo().equals( TipoVar.tipo_var.entero)) 
				err.error_expresionBin_conMalosTipos(opUsado.name());
			if(exp1.tipo() != null && !exp1.tipo().equals( TipoVar.tipo_var.entero)) 
				err.error_expresionBin_conMalosTipos(opUsado.name());		
		}
		
	}
	
	
	public void addExternal(HashMap<String, Constante> ctes, HashMap<String, Funtion> funciones) {
		exp0.addExternal(ctes, funciones);
		exp1.addExternal(ctes, funciones);
		if(exp0.tipo() == null || exp1.tipo() == null) {
			err.error_expresionBin_conMalosTipos(opUsado.name()); 
		} // error
		
		if(opUsado.equals(operadoresBinarios.OR) || opUsado.equals(operadoresBinarios.AND) ) {
			if(!exp0.tipo().equals( TipoVar.tipo_var.booleano) ||
					!exp1.tipo().equals( TipoVar.tipo_var.booleano)) 
				err.error_expresionBin_conMalosTipos(opUsado.name()); // lanza error
		}
		else {
			if(!exp0.tipo().equals( TipoVar.tipo_var.entero) || !exp1.tipo().equals( TipoVar.tipo_var.entero)) 
				err.error_expresionBin_conMalosTipos(opUsado.name()); // lanza error
		}		
	}

	public void addEntorno(HashMap<String, Tipo> var) {
		exp0.addEntorno(var);
		exp1.addEntorno(var);
		if(exp0.tipo() != null && exp1.tipo() != null) {

			if(exp0.esArray() || exp1.esArray()) err.error_expresionBin_conMalosTipos(opUsado.name()); // error por usar un identificador de array en una operacion binaria.
			
			if(opUsado.equals(operadoresBinarios.OR) || opUsado.equals(operadoresBinarios.AND) ) {
				if(!exp0.tipo().equals( TipoVar.tipo_var.booleano) || !exp1.tipo().equals( TipoVar.tipo_var.booleano)) 
					err.error_expresionBin_conMalosTipos(opUsado.name()); // lanza error
			}
			else {
				if(!exp0.tipo().equals( TipoVar.tipo_var.entero) || !exp1.tipo().equals( TipoVar.tipo_var.entero)) 
					err.error_expresionBin_conMalosTipos(opUsado.name()); // lanza error
			}	
		}	
	}

	public void addEntornoFun(String identifier, Parametros p, TipoVar tipDev) {
		exp0.addEntornoFun(identifier,p,tipDev);
		exp1.addEntornoFun(identifier,p,tipDev);
		if(exp0.tipo() != null && exp1.tipo() != null) {

			if(exp0.esArray() || exp1.esArray()) err.error_expresionBin_conMalosTipos(opUsado.name()); // error por usar un identificador de array en una operacion binaria.
			
			if(opUsado.equals(operadoresBinarios.OR) || opUsado.equals(operadoresBinarios.AND) ) {
				if(!exp0.tipo().equals( TipoVar.tipo_var.booleano) || !exp1.tipo().equals( TipoVar.tipo_var.booleano))
					err.error_expresionBin_conMalosTipos(opUsado.name()); // lanza error
			}
			else {
				if(!exp0.tipo().equals( TipoVar.tipo_var.entero) || !exp1.tipo().equals( TipoVar.tipo_var.entero)) 
					err.error_expresionBin_conMalosTipos(opUsado.name()); // lanza error
			}	
		}	
	}

	public void addEntornoSub(HashMap<String, Tipo> var) {
		exp0.addEntornoSub(var);
		exp1.addEntornoSub(var); 
		if(exp0.tipo() != null && exp1.tipo() != null) {
			
			if(exp0.esArray() || exp1.esArray()) err.error_expresionBin_conMalosTipos(opUsado.name()); // error por usar un identificador de array en una operacion binaria.
			
			if(opUsado.equals(operadoresBinarios.OR) || opUsado.equals(operadoresBinarios.AND) ) {
				if(!exp0.tipo().equals( TipoVar.tipo_var.booleano) || !exp1.tipo().equals( TipoVar.tipo_var.booleano)) 
					err.error_expresionBin_conMalosTipos(opUsado.name()); // lanza error
			}
			else {
				if(!exp0.tipo().equals( TipoVar.tipo_var.entero) || !exp1.tipo().equals( TipoVar.tipo_var.entero)) 
					err.error_expresionBin_conMalosTipos(opUsado.name()); // lanza error
			}	
		}	
	}

	public TipoVar.tipo_var  tipo() {
		return tipo;
	}
	
	public operadoresBinarios devuelveOperador() {
		return opUsado;
	}

	public boolean esArray() {	
		return false;
	}


	@Override
	public String toString() {
		return "(" +  exp0.toString() + ")" + opUsado.name() + "(" + exp1.toString() + ")";
	}


	@Override
	public void calculaValor(metodos_traduccion trad,  HashMap<String, Integer> var_memoria,HashMap<String, Integer> var_por_referencia) {
		
		exp0.calculaValor(trad, var_memoria,var_por_referencia);
		exp1.calculaValor(trad, var_memoria,var_por_referencia); 
		
		if(opUsado.equals(operadoresBinarios.MAS)) trad.operaciones("+");
		else if(opUsado.equals(operadoresBinarios.MENOS)) trad.operaciones("-");
		else if(opUsado.equals(operadoresBinarios.POR)) trad.operaciones("*");
		else if(opUsado.equals(operadoresBinarios.ENTRE)) trad.operaciones("/");
		else if(opUsado.equals(operadoresBinarios.MENIG)) trad.operaciones("<=");
		else if(opUsado.equals(operadoresBinarios.MAYIG)) trad.operaciones(">=");
		else if(opUsado.equals(operadoresBinarios.MEN)) trad.operaciones("<");
		else if(opUsado.equals(operadoresBinarios.MAY)) trad.operaciones(">");
		else if(opUsado.equals(operadoresBinarios.IG)) trad.operaciones("=");
		else if(opUsado.equals(operadoresBinarios.NOIG)) trad.operaciones("!=");
		else if(opUsado.equals(operadoresBinarios.OR)) trad.operaciones("OR");
		else if(opUsado.equals(operadoresBinarios.AND)) trad.operaciones("AND"); 
		
	}


	@Override
	public int getLV() {
		int lv ;
		
		lv = Math.max(exp0.getLV(), 1 + exp1.getLV() );
		
		return lv;
	}
	
}