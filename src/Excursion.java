import java.util.Comparator;
import java.util.List;

public abstract class Excursion {
	private String nombre;
	private int codigo;
	private TipoExcursion tipo;

	Excursion(String nombre, int codigoPromo, TipoExcursion tipo) {
		this.nombre = nombre;
		this.codigo = codigoPromo;
		this.tipo = tipo;
	}
	protected boolean comprarExcursion() {
		if ( getCupo()<=0 ) {
			return false;
		}
		else {
			reducirCupo();
			return true;
		}
	}
	protected abstract double getTiempo();

	protected abstract double getCosto();

	protected abstract int getCupo();

	protected abstract void reducirCupo();

	protected String getNombre() {
		return this.nombre;
	}

	protected int getCodigo(){
		return codigo;
	}

	protected TipoExcursion getTipo() {
		return tipo;
	}
    public static Comparator<Excursion> getComparator(SortParameter... sortParameters) {
        return new ExcursionComparator(sortParameters);
    }

	public enum SortParameter {
		//TIPO_EXCURSION_ASC,
		CODIGO_DESC,
		PRECIO_DESC,
		TIEMPO_DESC
	}

	private static class ExcursionComparator implements Comparator<Excursion> {
		private SortParameter[] parameters;

		private ExcursionComparator(SortParameter[] parameters) {
			this.parameters = parameters;
		}

		public int compare(Excursion o1, Excursion o2) {
			int comparison;
			for (SortParameter parameter : parameters) {
				switch (parameter) {
					//case TIPO_EXCURSION_ASC:
					//    break;
					case CODIGO_DESC:
						comparison = ((o2.getCodigo()>=100)?1:0) - ((o1.getCodigo()>=100)?1:0);
						if (comparison != 0 ) return comparison;
						break;
					case PRECIO_DESC:
						comparison = (o2.getCosto() == o1.getCosto())?0:(o2.getCosto() > o1.getCosto())?1:-1;
						if (comparison != 0 ) return comparison;
						break;
					case TIEMPO_DESC:
						comparison = (o2.getTiempo() == o1.getTiempo())?0:(o2.getTiempo() > o1.getTiempo())?1:-1;
						if (comparison != 0 ) return comparison;
						break;
				}
			}
			return 0;
		}
	}
}
