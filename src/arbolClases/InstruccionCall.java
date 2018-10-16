package arbolClases;

import java.util.HashMap;
import java.util.List;

import CodigoMaquina.metodos_traduccion;
import errores.GestionErrores;

public class InstruccionCall extends ClaseInstruccion{
	
	private List<Expresion> listaParametrosMetidos;
	
	private String identificador;
	
	private Tipo.tipo_var tipo;
	
	private GestionErrores err;
	
	public InstruccionCall(String id, ListaExpresiones pars){ 
		err = new GestionErrores();
		this.identificador = id;
		listaParametrosMetidos = pars.getList();
	}

	@Override
	public void addGeneric(HashMap<String, Constante> ctes, HashMap<String, Funtion> funciones) {
		
		for(Expresion e :listaParametrosMetidos) {
			e.addExternal(ctes, funciones);
			if(e.tipo() == null) err.errorTipos_VariableUndef(e.toString()); // lanza error. 
		}
		
		if(!funciones.containsKey(identificador)) err.errorFuncionInexistente(identificador); //error
		
		tipo = funciones.get(identificador).devuelveTipo();
		
		//Checkeo de que los tipos se metieron correctamente.
		
		int i = 0;
		List<String> listaOrdenada = funciones.get(identificador).devPar().devuelveListaOrdenada();
		HashMap<String,Tipo> mapaParametros = funciones.get(identificador).devPar().getMap();
		
		if(listaParametrosMetidos.size() != listaOrdenada.size())
			err.error_parametrosNoAdecuados(identificador); 
		
		for(Expresion e : listaParametrosMetidos) {
			if(!e.tipo().equals(mapaParametros.get(listaOrdenada.get(i)).getType())) 
				err.errorTipos_tipoErroneo(e.toString()); //error
			if(e.esArray()) {
				if(!mapaParametros.get(listaOrdenada.get(i)).esArray()) 
					err.errorTipos_tipoErroneo(e.toString()); //error, falta checkear las dimensiones
				List<Integer> d = ((TipoArray) mapaParametros.get(listaOrdenada.get(i))).devuelveListaDim();
				List<Integer> ed = ((ExpresionUnaria)e).devuelveDim();
				if(d.size() != ed.size()) err.errorTipos_tipoErroneo(e.toString());
				int count = 0;
				for(Integer dim : ed) { 
					if(!dim.equals(d.get(count))) err.errorTipos_tipoErroneo(e.toString());
					count++;
				}
			}			
			i++;
		}	
	}

	@Override
	public void addEntorno(HashMap<String, Tipo> var) {
		for(Expresion e :listaParametrosMetidos) e.addEntorno(var);	
	}

	@Override
	public void addEntornoFun(String identifier, Parametros p, TipoVar tipDev) {
		for(Expresion e :listaParametrosMetidos) e.addEntornoFun(identifier,p,tipDev); 
	}

	@Override
	public void addEntornoSub(HashMap<String, Tipo> var) {
		for(Expresion e :listaParametrosMetidos) e.addEntornoSub(var); 
	}

	public Tipo.tipo_var getReturnValue() {
		return tipo;
	}
	
	public String getId() {
		return identificador;
	}

	@Override
	public int getLV() {
		int lv = 0;
		
		for(Expresion e: listaParametrosMetidos) lv = Math.max(lv, e.getLV());
		
		return lv;
	}

	@Override
	public void traduccion(metodos_traduccion trad, HashMap<String, Integer> var_memoria, HashMap<String, Integer> var_por_referencia) {
		trad.llamada_mst();
		for(Expresion e: listaParametrosMetidos) e.calculaValor(trad, var_memoria, var_por_referencia); 
		trad.llamada_cup(listaParametrosMetidos.size(), identificador); //todos ocupan 1 porque los arrays se pasan por referencia.
	}

	@Override
	public int identificadorIns() {
		return 1;
	}
	
}