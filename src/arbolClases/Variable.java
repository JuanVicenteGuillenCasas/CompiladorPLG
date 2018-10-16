package arbolClases;

public class Variable {
	
	private String ident;
	
	private Tipo tipo;
	
	public Variable(Tipo t , String id){
		this.ident = id;
		this.tipo = t;
	}

	public boolean soyArray() {
		if(!tipo.esArray()) return false;
		else return true;
	}

	
	public String identifier() {
		return ident;
	}
	
	public Tipo getType() {
		return tipo;
	}	
	
}