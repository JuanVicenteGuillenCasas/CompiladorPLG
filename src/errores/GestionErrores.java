package errores;

import alex.UnidadLexica;

public class GestionErrores {
   public void errorLexico(int fila, String lexema) {
     System.out.println("ERROR fila "+fila+": Caracter inexperado: "+lexema); 
     System.exit(1);
   }  
   public void errorSintactico(UnidadLexica unidadLexica) {
     System.out.print("ERROR fila "+unidadLexica.fila()+": Elemento inexperado "+unidadLexica.value);
     System.exit(1);
   }
   
   public void errorTipos_VariableUndef(String id) {
	   System.out.print("ERROR de TIPOS:" + id + " nos está dando problemas porque está indefinida.");
	   System.exit(1); 
   }
   
   public void error_returnNoDebido() {
	   System.out.print("ERROR, el main o este procedimientos no deben llevar la instruccion return.");
	   System.exit(1);
   }
   
   public void error_necesitaUnReturnSeguro(String id) {
	   System.out.print("ERROR, la funcion " + id   + " necesita una instruccion return segura.");
	   System.exit(1);
   }
   
   public void errorTipos_Dimensiones_MalPuestas(String id) {
	   System.out.print("ERROR de TIPOS: el array " + id + " nos está dando problemas en sus dimensiones.");
	   System.exit(1); 
   }
   
   public void error_expresionBin_conMalosTipos(String id) {
		   System.out.print("ERROR de TIPOS, la operacion : " + id + " nos está dando problemas en alguna de sus partes.");
		   System.exit(1); 	   
   }
   
   public void error_parametrosNoAdecuados(String id) {
	   System.out.print("ERROR de TIPOS, los parametros de : " + id + " nos están dando problemas.");
	   System.exit(1); 	   
   }
   
   public void error_dimImposibles() {
	   System.out.print("ERROR de TIPOS, Un array esta definido con dimensiones nulas o negativas.");
	   System.exit(1); 	   
   }
   
   public void error_caseMal() {
	   System.out.print("ERROR de TIPOS, los valores del Case no estan segun el protocolo (0,1,2,...) ");
	   System.exit(1); 	   
   }
   
   public void errorTipos_tipoErroneo(String id) {
	   System.out.print("ERROR de TIPOS, el tipo de : " + id + " nos está dando problemas porque no es el debido.");
	   System.exit(1); 	   
   }
   
   public void errorRepiteDefinicion(String id) {
	   System.out.print("ERROR de DEFINICION, " + id + " se esta definiendo dos veces en el mismo nivel.");
	   System.exit(1); 
   }
   
   public void errorFuncionInexistente(String id) {
	   System.out.print("ERROR , " + id + " no esta definida.");
	   System.exit(1); 
   }
   
}
