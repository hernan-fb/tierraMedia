import java.util.List;

public class PromocionAxB extends Promocion {
	int codigoExcursionGratuita;
	List<Excursion> listadoDeLaPromo;

	public PromocionAxB(String nombrePromo, int codigoPromo, int codigoExcursionBonificada, List<Excursion> listado, TipoExcursion tipoPromo) {
		super(nombrePromo, codigoPromo, listado, tipoPromo);
		codigoExcursionGratuita = codigoExcursionBonificada;
	}

	public double getCosto() {
		double precio = 0;
		for (Excursion excursion : getListadoDeLaPromo() ) {
			precio += (excursion.getCodigo() == codigoExcursionGratuita)?0:excursion.getCosto();
		}
		return precio;
	}
}