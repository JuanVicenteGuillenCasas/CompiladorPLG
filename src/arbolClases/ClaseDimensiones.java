package arbolClases;

import java.util.ArrayList;
import java.util.List;

public class ClaseDimensiones {
	
	 private List<Integer> tama_dim;
	 
	 private int dimensiones;
	
	 public ClaseDimensiones(String size, ClaseDimensiones masdim){
		 this.dimensiones = 1 + masdim.getDimensiones();
		 tama_dim = new ArrayList<Integer>();
		 tama_dim.add(Integer.parseInt(size));
		 tama_dim.addAll(masdim.getListDim());
	 }
	 
	 public ClaseDimensiones(String size){
		 this.dimensiones = 1;
		 tama_dim = new ArrayList<Integer>();
		 tama_dim.add(Integer.parseInt(size));
	 }

	public int getDimensiones() {
		return dimensiones;
	}

	public List<Integer> getListDim() {
		return tama_dim;
	}
	
}