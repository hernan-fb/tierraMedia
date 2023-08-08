import java.util.List;

public class PromocionPorcentual extends Promocion {
	List<Excursion> listadoDeLaPromo;
	double porcentajeDescuento;

	public PromocionPorcentual(String nombrePromo, int codigoPromo, double porcentajeDesc, List<Excursion> listado, TipoExcursion tipoPromo) {
		super(nombrePromo, codigoPromo, listado, tipoPromo);
		porcentajeDescuento = porcentajeDesc;
	}

	public double getCosto() {
		double precio = 0;
		for (Excursion excursion : getListadoDeLaPromo() ) {
			precio += excursion.getCosto();
		}
		return precio*(1-porcentajeDescuento/100);
	}
}
