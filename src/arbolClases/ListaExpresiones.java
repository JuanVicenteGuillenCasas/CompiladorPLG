package arbolClases;

import java.util.ArrayList;
import java.util.List;

public class ListaExpresiones{
	
	private List<Expresion> lista_par;
	
	public ListaExpresiones(Expresion e, ListaExpresiones pst){		
		lista_par = new ArrayList<Expresion>();
		lista_par.add(e);
		lista_par.addAll(pst.getList());
	}
	
	public ListaExpresiones(Expresion e){
		lista_par = new ArrayList<Expresion>();
		lista_par.add(e);
	}
	
	public ListaExpresiones(){
		lista_par = new ArrayList<Expresion>();
	}

	public List<Expresion> getList() {
		return lista_par;
	}
	
	
}