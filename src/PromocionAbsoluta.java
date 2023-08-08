import java.util.List;

public class PromocionAbsoluta extends Promocion {
	List<Excursion> listadoDeLaPromo;
	double precio;

	public PromocionAbsoluta(String nombrePromo, int codigoPromo, double precioPromo, List<Excursion> listado, TipoExcursion tipoPromo) {
		super(nombrePromo, codigoPromo, listado, tipoPromo);
		precio = precioPromo;
	}
	public double getCosto() {
		return precio;
	}
}