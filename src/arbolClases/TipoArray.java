package arbolClases;

import java.util.List;

import errores.GestionErrores;

public class TipoArray extends Tipo{
	
	private tipo_var tipo;
	
	private int dimensiones;
	
	private List<Integer>  tamPorCadaDim;
	
	public TipoArray(String s, ClaseDimensiones d){	
		GestionErrores err = new GestionErrores();
		if(s.equals("int")) tipo = tipo_var.entero;
		if(s.equals("bool")) tipo = tipo_var.booleano;
		this.dimensiones = d.getDimensiones();	
		this.tamPorCadaDim = d.getListDim();
		for(Integer i : tamPorCadaDim) {
			if(i < 1) err.error_dimImposibles(); // dimension 0 o negativa eso no existe en un array.
		}		
	}

	public tipo_var getType() {
		return tipo;
	}

	public int getDimensions() {
		return dimensiones;
	}
	public int getTamDimN(int i) {
		return tamPorCadaDim.get(i);
		
	}
	public List<Integer> devuelveListaDim(){
		return tamPorCadaDim;
	}

	public boolean cumpleDimensiones(List<Integer> coordenadasAsignar) {
		boolean resp = true;
		
		if(coordenadasAsignar == null || coordenadasAsignar.size() != dimensiones) resp = false;
		else {
			for(int i = 0; i < dimensiones; i++) {
				if(coordenadasAsignar.get(i) >= tamPorCadaDim.get(i) || coordenadasAsignar.get(i) < 0)
					resp = false;
			}		
		}	
		return resp;
	}

	@Override
	public boolean esArray() {
		return true;
	}
	
}