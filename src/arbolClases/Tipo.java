package arbolClases;

public abstract class Tipo {
	
	public enum tipo_var {entero, booleano};
	
	public abstract tipo_var getType();
	
	public abstract boolean esArray();

}
