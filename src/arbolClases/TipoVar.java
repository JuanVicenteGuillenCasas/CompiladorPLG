package arbolClases;

public class TipoVar extends Tipo{
		
	private tipo_var tipo;
	
	public TipoVar(String simbolo){
		if(simbolo.equals("int")) tipo = tipo_var.entero;
		if(simbolo.equals("bool")) tipo = tipo_var.booleano;
	}
	
	public TipoVar(){
		tipo = null;
	}
	
	public tipo_var getType() {
		return tipo;
	}

	@Override
	public boolean esArray() {
		return false;
	}
	
}