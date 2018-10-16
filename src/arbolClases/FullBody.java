package arbolClases;

import java.util.HashMap;

import CodigoMaquina.metodos_traduccion;

public class FullBody{
	
	private FuncionesAndCtes FuncionesYConstantes;
	
	private Programa programaPrincipal;
	
	private HashMap<String,Constante> lista_constantes;
	
	private HashMap<String,Funtion> lista_funciones; 
	
	private metodos_traduccion trad;
	
	public FullBody(){}
	
	public FullBody(FuncionesAndCtes ft, Programa p) throws Exception{
		FuncionesYConstantes = ft;
		programaPrincipal = p;	
		
		lista_constantes = FuncionesYConstantes.getListCte();
		lista_funciones = FuncionesYConstantes.getListFun();
		programaPrincipal.add(lista_constantes, lista_funciones);
		
		
		//Preparar la pila, ajustar SP y EP correctos;
		
		
		int le = 5, lv = 0;
		le += lista_constantes.size();
		le += programaPrincipal.devuelveLE();
		
		for(Constante c : lista_constantes.values()) lv = Integer.max(lv, c.getLV()); 
		lv = Integer.max(lv,programaPrincipal.devuelveLV() );
		
		
		trad = new metodos_traduccion(le, lv);
		
		for(Constante c : lista_constantes.values()) c.traduccion(trad);
		
		int main = trad.reservaLinea(); //Preparamos el salto al main.
		
		for(Funtion f : lista_funciones.values()) f.traduccionPreambulo(trad);
		for(Funtion f : lista_funciones.values()) f.traduccionCuerpoFun(trad); 
		
		trad.saltoIncond(trad.devuelveContIns(), main); //Ponemos el salto al main justo aantes de empezar el codigo de las funciones.
		programaPrincipal.traduccion(trad);
		
		trad.printeaTodo();
	}
	
	public FullBody(Programa p) throws Exception{	
		programaPrincipal = p;
		lista_constantes = new HashMap<String,Constante>(); // vacio
		lista_funciones = new HashMap<String,Funtion>();	 // vacio	
		programaPrincipal.add(lista_constantes, lista_funciones);
		
		int le = 5, lv = 0;
		le += programaPrincipal.devuelveLE();
		lv = Integer.max(lv,programaPrincipal.devuelveLV() );
		
		trad = new metodos_traduccion(le, lv);
		programaPrincipal.traduccion(trad);
		
		trad.printeaTodo();
	}
	
}