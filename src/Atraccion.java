public class Atraccion extends Excursion{
	private double precio;
	private double tiempo;
	private int cupo;

	Atraccion(String nombre, int codigoPromo, double costo, double time, int cupoAtrac, TipoExcursion tipo){
		super(nombre,codigoPromo,tipo);
		precio = costo;
		tiempo = time;
		cupo = cupoAtrac;
	}

	public double getCosto() {
		return precio;
	}

	public double getTiempo() {
		return tiempo;
	}

	public int getCupo() {
		return cupo;
	}
	public void reducirCupo() {
		cupo--;
	}
}
