import java.util.ArrayList;
import java.util.List;

public class App {

	public static void main(String[] args) {
		int provisoria = 0;
		Excursion eProvisoria = null;
		List<Excursion> lProvisoria = null;
		LeerArchivo archivo = new LeerArchivo();
		EscribirArchivo guardarTextoEnArchivo = new EscribirArchivo();

		Sistema tierraMedia = new Sistema(	archivo.getListadoExcursionesUnificado(),
											archivo.getListadoUsuarios()	);
		System.out.println(ConsoleHelper.mostrarMensajeBienvenida());

		String mensaje = "\nLos datos cargados son los siguientes: \n";
		mensaje += "Promociones (más de un recorrido):\n";
		mensaje += ConsoleHelper.imprimirSoloLasPromocionesDelListado(archivo.getListadoPromociones() )+"\n";
		mensaje += "Excursiones simples (un recorrido):\n";
		mensaje += ConsoleHelper.imprimirSoloLasAtraccionesDelListado(archivo.getListadoAtracciones() );
		mensaje += "Usuarios: \n";
		mensaje += ConsoleHelper.imprimirListadoDeUsuarios(archivo.getListadoUsuarios() );
		System.out.println(mensaje);

		String entradaTeclado = "";
		int ultimoUsuario = 0;
		boolean choice = false;
		List<String> listaDeTickets = new ArrayList<String>();

		do {
			do {
				mensaje = "¿para qué usuario desea comprar itinerarios?\n";
				mensaje += ConsoleHelper.imprimirListadoDeUsuarios(archivo.getListadoUsuarios());
				mensaje += "(presione enter para el próximo usuario de la lista)...";
				System.out.println(mensaje);
				entradaTeclado = ConsoleHelper.entradaTeclado();
				if ( !(entradaTeclado.equals("")) ) {
					try {
						ultimoUsuario = Integer.parseInt(entradaTeclado);
						ultimoUsuario--;
					} catch (Exception e) {
						System.out.println("No está permitido ingresar carácteres no numéricos.\n");
						System.out.println("A continuación la interfaz de compra para el usuario Nro."+ultimoUsuario+1+": "+
								archivo.getListadoUsuarios().get(ultimoUsuario).getNombre()+".");
					}
				}
				if ( ultimoUsuario >= archivo.getListadoUsuarios().size() ) ultimoUsuario = -1;
			} while (ultimoUsuario <= -1);

			lProvisoria = tierraMedia.avanzarALaSiguienteSugerencia( archivo.getListadoUsuarios().get(ultimoUsuario) );
			eProvisoria = tierraMedia.getExcursionSugeridaActual();
			while ( tierraMedia.getExcursionSugeridaActual() != null ) {
				mensaje = tierraMedia.getExcursionSugeridaEnStr();
				System.out.println(mensaje);
				mensaje = "\t"+ConsoleHelper.imprimirEstadoDeUsuario(archivo.getListadoUsuarios().get(ultimoUsuario));
				mensaje += "\nPara contratar esta excursion presione 'S', para rechazar 'N'.";
				System.out.println(mensaje);
				do {
					choice = false;
					entradaTeclado = ConsoleHelper.entradaTeclado();
					if (!entradaTeclado.toLowerCase().trim().equals("s") && !entradaTeclado.toLowerCase().trim().equals("n")) {
						System.out.println("Ingrese s/n para responder a cada oferta.");
						choice = true;
					}
				} while (choice);

				if (entradaTeclado.toLowerCase().equals("s")) {
					archivo.getListadoUsuarios().get(ultimoUsuario).agregarAlCarritoDeExcursionesCompradas(  tierraMedia.getExcursionSugeridaActual() );
				}

				lProvisoria = tierraMedia.avanzarALaSiguienteSugerencia(archivo.getListadoUsuarios().get(ultimoUsuario));
				eProvisoria = tierraMedia.getExcursionSugeridaActual();
				provisoria = lProvisoria.lastIndexOf( eProvisoria );
				System.out.println("");
			}
			mensaje = "Compra del usuario finalizada!\n";
			System.out.println(mensaje);
			listaDeTickets.add ( ConsoleHelper.imprimirExcursionesDelUsuario(archivo.getListadoUsuarios().get(ultimoUsuario)) );
			System.out.println(listaDeTickets.get(ultimoUsuario++) );

		} while (ultimoUsuario < archivo.getListadoUsuarios().size() );

		guardarTextoEnArchivo.escribirArchivoComprasDeUsuarios(listaDeTickets);
	}
}