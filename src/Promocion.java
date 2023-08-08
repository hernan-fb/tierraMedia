import java.util.List;
import java.util.stream.Collectors;

public abstract class Promocion extends Excursion {

    private List<Excursion> listadoDeLaPromo;

    Promocion(String nombrePromo, int codigoPromo, List<Excursion> listado, TipoExcursion tipoPromo){
        super(nombrePromo, codigoPromo, tipoPromo);
        listadoDeLaPromo = listado;
    }

    public List<Excursion> getListadoDeLaPromo() {
        return listadoDeLaPromo;
    }

    public int getCupo() {
        int cupo = getListadoDeLaPromo().get(0).getCupo();
        for( Excursion excursion : getListadoDeLaPromo() ) {
            cupo = Math.min(cupo, excursion.getCupo() );
        }
        return cupo;
    }

    public void reducirCupo() {
        for( Excursion excursion : getListadoDeLaPromo() ) {
            excursion.reducirCupo();
        }
    }

    public boolean hayCupo() {
        boolean hayCupo = true;
        for( Excursion excursion : getListadoDeLaPromo() ) {
            hayCupo = hayCupo && ( excursion.getCupo()>0 );
        }
        return hayCupo;
    }

    public double getTiempo() {
        double tiempoTotal = 0D;
        for( Excursion excursion : getListadoDeLaPromo() ) {
            tiempoTotal += excursion.getTiempo();
        }
        return tiempoTotal;
    }
}