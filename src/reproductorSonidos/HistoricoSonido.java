package reproductorSonidos;

public class HistoricoSonido {
	private String direccion;
	private String nombre;
	
	public HistoricoSonido(String d, String n) {
		this.direccion=d;
		this.nombre=n;
	}
	
	public String getDireccion() {
		return this.direccion;
	}
	public String getNombre() {
		return this.nombre;
	}
	
	@Override
	public String toString() {
		return this.nombre;
	}
	public boolean equals(HistoricoSonido e) {
		boolean iguales=false;
		
		if(e.getDireccion()==this.direccion)  {
			iguales=true;
		}
		
		
		return iguales;
		
	}
	
	
}
