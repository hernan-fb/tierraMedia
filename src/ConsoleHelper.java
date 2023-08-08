import java.util.Formatter;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ConsoleHelper {
    public static String mostrarMensajeBienvenida() {
        String mensaje = "¡Bienvenido al programa de Promoción de turismo en Tierra Media!\n";
        mensaje += "Para la compra de Excursiones, el programa realizará a continuación la carga de datos mediante los archivos:\n\tatracciones.in\n\tpromociones.in\n\tusuarios.in\n";
        mensaje += "La carga de los archivos es realizada en el directorio de trabajo actual.";
        return mensaje;
    }
    public static String imprimirListadoDeUsuarios(List<Usuario> usuarios) {
        int cod = 0;
        Formatter fmt = new Formatter();
        fmt.format("\t%-5s %-20s %-15s %10s %7s %11s\n", "Cod.", "Nombre de Usuario", "Tipo de usuario", "Monedas", "Tiempo", "Preferencia");
        for( Usuario usuario : usuarios ) {
            fmt.format("\t% -5d %-20s %-15s %10.2f %7.2f %11s\n", ++cod, usuario.getNombre(), usuario.getPerfilUsuario(), usuario.getPresupuesto(),
                    usuario.getTiempoDisponible(), usuario.getTipoPreferido() );
        }
        return fmt.toString();
    }

    public static String imprimirCabeceraDeExcursion() {
        Formatter fmt = new Formatter();
        fmt.format("\t%-5s %-27s %-15s %-10s %-9s %-7s", "Cod.", "Nombre de Itinerario", "Tipo Excursion", "Costo", "Duración", "Cupo" );
        return fmt.toString();
    }

    public static String imprimirSubTotalesDeLaExcursion(List<Excursion> listado) {
        Formatter fmt = new Formatter();
        final double[] costoTotal = {0};
        final double [] tiempoTotal = {0};
        listado.forEach(excursion -> costoTotal[0] += excursion.getCosto() );
        listado.forEach(excursion -> tiempoTotal[0] += excursion.getTiempo() );
        fmt.format("\t%-40s%15.2f %11.2f", "Total del usuario . . . . . . . . . . .", costoTotal[0], tiempoTotal[0]);
        return fmt.toString();
    }

    public static String imprimirUnaExcursion(Excursion excursion) {
        Formatter fmt = new Formatter();
        if ( excursion instanceof Promocion) {
            fmt.format("\n\t%-5d %-27s %-15s %-7.2f %9.2f %7d", excursion.getCodigo(), excursion.getNombre(), excursion.getTipo(),
                    excursion.getCosto(), excursion.getTiempo(), excursion.getCupo());
            fmt.format("\n\t\t  Esta promoción incluye las excursiones: ");
            fmt.format("\n\t\t  %-27s %-15s %7s", "Nombre", "Tipo Excur.", "Duración");
            Promocion promoAux = (Promocion) excursion;
            for (Excursion exc : promoAux.getListadoDeLaPromo()) {
                fmt.format("\n\t\t  %-27s %-15s %-9.2f", exc.getNombre(), exc.getTipo(), exc.getTiempo());
            }
            fmt.format("\n");
        }
        else {
            fmt.format("\n\t%-5d %-27s %-15s %-7.2f %9.2f %7d", excursion.getCodigo(), excursion.getNombre(), excursion.getTipo(),
                    excursion.getCosto(), excursion.getTiempo(), excursion.getCupo());
        }
        return fmt.toString();
    }
    public static String imprimirExcursiones(List<Excursion> listaUnificada) {
        String mensaje;
        mensaje = imprimirSoloLasAtraccionesDelListado(listaUnificada);
        mensaje += "" + imprimirSoloLasPromocionesDelListado(listaUnificada);
        return mensaje;
    }
    public static String imprimirSoloLasAtraccionesDelListado(List<Excursion> listaExcursiones) {
        String texto;
        listaExcursiones = listaExcursiones.stream().filter(entry -> !(entry instanceof Promocion)).collect(Collectors.toList());
        if (listaExcursiones.size() == 0 ) return "";
        texto = "";//"\tListado de Itinerarios Simples (Atracciones Turísticas):\n";
        texto += imprimirCabeceraDeExcursion();
        for( Excursion excursion : listaExcursiones ) {
            texto += imprimirUnaExcursion(excursion);
        }
        texto += "\n\n";
        return texto;
    }
    public static String imprimirSoloLasPromocionesDelListado(List<Excursion> listaPromos) {
        String texto;
        listaPromos = listaPromos.stream().filter(entry -> (entry instanceof Promocion)).collect(Collectors.toList());
        if (listaPromos.size() == 0 ) return "";
        texto = "";//""\tListado de Promociones (grupos de excursiones simples):\n";
        texto += imprimirCabeceraDeExcursion();
        for( Excursion promo : listaPromos ) {
            texto += imprimirUnaExcursion(promo);
        }
        return texto;
    }

    public static String imprimirExcursionesDelUsuario(Usuario usuario) {

        String texto = "";
        texto += "Resumen del Carrito del Usuario:      "+usuario.getNombre()+"\n";
        texto += "\tTipo de Perfil:                   "+usuario.getPerfilUsuario()+"\n";
        texto += "\tTipo de Excursion Preferida:      "+usuario.getTipoPreferido()+"\n";
        texto += "\tPresupuesto utilizado [monedas]:         "+usuario.getCostoTotalDelCarritoDeExcursionesCompradas()+"\n";
        texto += "\tPresupuesto Actual Disponible [monedas]: "+usuario.getPresupuesto()+"\n";
        texto += "\tTiempo Contratado con Excursiones [h]:   "+usuario.getTiempoTotalDelCarritoDeExcursionesCompradas()+"\n";
        texto += "\tTiempo Libre sin excursiones [h]:        "+(usuario.getTiempoDisponible())+"\n";;
        texto += "\tListado de Excursiones contratadas: \n";
        texto += imprimirExcursiones( usuario.getCarritoExcursionesCompradas() );
        texto += imprimirSubTotalesDeLaExcursion( usuario.getCarritoExcursionesCompradas() );
        texto += "\n";

        return texto;
    }

    public static String entradaTeclado() {
        Scanner entradaEscaner = null;
                entradaEscaner = new Scanner(System.in);    //Creación de un objeto Scanner
        return entradaEscaner.nextLine();                   //Invocamos un método sobre un objeto Scanner
    }

    public static String imprimirEstadoDeUsuario(Usuario usuario) {
        return  "Usuario: "+usuario.getNombre()+"\tMonedas: "+usuario.getPresupuesto()+"\t Tiempo: "+usuario.getTiempoDisponible()+"\t Tipo de Excursion Preferida: "+usuario.getTipoPreferido()+"\n";
    }
}