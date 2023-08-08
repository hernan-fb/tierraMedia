
import java.util.ArrayList;
import java.util.List;

public class Usuario {
	private String nombre;
	private double presupuesto;
	private double tiempoDisponible;
	private TipoExcursion tipoPreferido;
	private PerfilUsuario perfil;
	private List<Excursion> carritoExcursionesCompradas;

	public Usuario(String nombre, double presupuesto, double tiempoDisponible, TipoExcursion tipoPreferido) {
		this.nombre = nombre;
		this.presupuesto = presupuesto;
		this.tiempoDisponible = tiempoDisponible;
		this.tipoPreferido = tipoPreferido;
		this.perfil = PerfilUsuario.PERSONALIZADO;
		this.carritoExcursionesCompradas = new ArrayList<Excursion>();
	}
	public Usuario(String nombre, PerfilUsuario predefinido) {
		try {
			this.nombre = nombre;
			this.carritoExcursionesCompradas = new ArrayList<Excursion>();
			switch (predefinido) {
				case FRODO:
					this.presupuesto = 10;
					this.tiempoDisponible = 8;
					this.tipoPreferido = TipoExcursion.AVENTURA;
					this.perfil = PerfilUsuario.FRODO;
					break;
				case GANDALF:
					this.presupuesto = 100;
					this.tiempoDisponible = 5;
					this.tipoPreferido = TipoExcursion.PAISAJE;
					this.perfil = PerfilUsuario.GANDALF;
					break;
				case SAM:
					this.presupuesto = 36;
					this.tiempoDisponible = 8;
					this.tipoPreferido = TipoExcursion.DEGUSTACION;
					this.perfil = PerfilUsuario.SAM;
					break;
			}
		} catch (Exception e) {
			System.out.println("Error en la lectura del archivo usuarios.in. \nSolamente existen 3 usuarios predefinidos: FRODO, GANDALF, SAM");
	}
	}
	public String getNombre() {
		return nombre;
	}
	public PerfilUsuario getPerfilUsuario() {
		return perfil;
	}
	public double getPresupuesto() {
		return presupuesto;
	}

	public double getTiempoDisponible() {
		return tiempoDisponible;
	}

	public TipoExcursion getTipoPreferido() {
		return tipoPreferido;
	}

	public List<Excursion> getCarritoExcursionesCompradas() {
		return carritoExcursionesCompradas;
	}

	public void agregarAlCarritoDeExcursionesCompradas(Excursion excursion) {
		carritoExcursionesCompradas.add(excursion);
		presupuesto -= excursion.getCosto();
		tiempoDisponible -= excursion.getTiempo();
		excursion.reducirCupo();
	}

	public double getTiempoTotalDelCarritoDeExcursionesCompradas() {
		final double[] tiempoUtilizado = {0};
		carritoExcursionesCompradas.forEach( excursion -> tiempoUtilizado[0] += excursion.getTiempo());
		return tiempoUtilizado[0];
	}

	public double getCostoTotalDelCarritoDeExcursionesCompradas() {
		final double[] costoTotalExcursionesDelCarrito = {0};
		carritoExcursionesCompradas.forEach( excursion -> costoTotalExcursionesDelCarrito[0] += excursion.getCosto());
		return costoTotalExcursionesDelCarrito[0];

	}

	public Boolean estaBienPrecio(Double precio) {
		return precio <= presupuesto;
	}

	public Boolean estaBienDuracion(double duracion) {
		return duracion <= tiempoDisponible;
	}

	public Boolean estanBienTiposAtraccion(TipoExcursion tipoAtraccion) {
		return (tipoAtraccion == tipoPreferido);
	}
}