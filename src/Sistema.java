import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Sistema {
	private List<Excursion> listaExcursionesUnificado;
	private List<Usuario> listaUsuarios;
	private LocalDate fechaSistema;
	private LinkedList<Excursion>[] newIterator;
	private int usuarioActual;
	private List<Excursion> listaDeExcursionesDelUsuarioActual = null;
	final Comparator<Excursion> excComparator = Excursion.getComparator(Excursion.SortParameter.CODIGO_DESC,
			Excursion.SortParameter.PRECIO_DESC, Excursion.SortParameter.TIEMPO_DESC);

	Sistema(List<Excursion> listadoUnificado, List<Usuario> listadoUsuarios) {
		listaExcursionesUnificado = listadoUnificado;
		listaUsuarios = listadoUsuarios;
		fechaSistema = LocalDate.now();
		newIterator = new LinkedList[listadoUsuarios.size()];
		inicializarArray(newIterator);
		usuarioActual = -1;
	}

	private void filtrarPromocionesRealizables() {
		 listaDeExcursionesDelUsuarioActual.
					removeIf(promocion ->    promocion.getCosto() > listaUsuarios.get(usuarioActual).getPresupuesto() ||
											promocion.getTiempo() > listaUsuarios.get(usuarioActual).getTiempoDisponible() );
	}

	private void filtrarAtraccionesYaCompradas() {
		// filtra con contenido de usuario.getCarritoExcursionesCompradas()
		listaDeExcursionesDelUsuarioActual.
				removeIf(excursionDisponible -> {
					boolean flag = false;
					for (Excursion excursionComprada : listaUsuarios.get(usuarioActual)
							.getCarritoExcursionesCompradas() ) {
						flag = flag || (excursionDisponible.getCodigo() == excursionComprada.getCodigo());
						if (flag) break;
					}
					return flag;
				} );
		listaDeExcursionesDelUsuarioActual.
				removeIf(excursionDisponible -> {
					List<Excursion> promosCompradas = listaUsuarios.get(usuarioActual).getCarritoExcursionesCompradas().
							stream().filter(excursionComprada -> (excursionComprada instanceof Promocion) )
							.collect(Collectors.toList());
					boolean flag = false;
					for (Excursion excursionComprada : promosCompradas ) {
						Promocion promoComprada = (Promocion) excursionComprada;
						for (Excursion excComprada : promoComprada.getListadoDeLaPromo() ) {
							flag = flag || (excComprada.getCodigo() == excursionDisponible.getCodigo() );
							if (flag) break;
						}
						if (flag) break;
					}
					return flag;
				} );
	}

	private void ordenarListaSugerencias() {
		Collections.sort(listaDeExcursionesDelUsuarioActual, excComparator);
		listaDeExcursionesDelUsuarioActual =
		Stream.concat( listaDeExcursionesDelUsuarioActual.stream().filter(entry -> entry.getTipo() == listaUsuarios.get(usuarioActual).getTipoPreferido() ),
				       listaDeExcursionesDelUsuarioActual.stream().filter(entry -> entry.getTipo() != listaUsuarios.get(usuarioActual).getTipoPreferido() )
				      ).collect(Collectors.toList() );
	}

	private int getNumeroDeUsuario(Usuario usuario) {
		int i;
		for (i=0; !usuario.equals(listaUsuarios.get(i)); i++ );
		return i;
	}

	private void inicializarArray(LinkedList<Excursion>[] array) {
		for (int i = 0; i<array.length; i++ ) {
			array[i] = new LinkedList<Excursion>();
		}
	}

	private int getPosicionIteradorEnLista() {
		return (newIterator[usuarioActual].isEmpty())?-1:listaDeExcursionesDelUsuarioActual.indexOf( newIterator[usuarioActual].getLast() );
	}

	public List<Excursion> avanzarALaSiguienteSugerencia(Usuario usuario) {
		if (usuarioActual != getNumeroDeUsuario(usuario)) {
			usuarioActual = getNumeroDeUsuario(usuario);
			listaDeExcursionesDelUsuarioActual = new ArrayList<>(listaExcursionesUnificado);
		}

		filtrarPromocionesRealizables();	// cambios en listaDeExcursionesDelUsuarioActual[usuarioActual]
		filtrarAtraccionesYaCompradas();	// cambios en listaDeExcursionesDelUsuarioActual[usuarioActual]
		ordenarListaSugerencias();			// cambios en listaDeExcursionesDelUsuarioActual[usuarioActual]

		while ( newIterator[usuarioActual].size() 	>= 1 	&&
				getPosicionIteradorEnLista() 		== -1) 	{
			newIterator[usuarioActual].removeLast();
		}
		//agrega el nuevo elemento a sugerir
		newIterator[usuarioActual].add(
				(listaDeExcursionesDelUsuarioActual.size()-1 <= getPosicionIteradorEnLista() )?
						null:listaDeExcursionesDelUsuarioActual.get(getPosicionIteradorEnLista()+1) );
		return listaDeExcursionesDelUsuarioActual;
	}

	public String getExcursionSugeridaEnStr() {
		if (newIterator[usuarioActual].getLast() == null || listaDeExcursionesDelUsuarioActual.size() == 0 ) {
			return "";
		}
		/*(newIterador[usuarioActual].removeIf((excursion -> {
				boolean flag = true;
				for (Excursion exc : listaDeExcursionesDelUsuarioActual ) {
					flag = flag && (excursion.getCodigo() != exc.getCodigo());
				}
				return flag;
				}
				)) ) */
		String texto = ConsoleHelper.imprimirCabeceraDeExcursion();
		texto += ConsoleHelper.imprimirUnaExcursion( newIterator[usuarioActual].getLast() );
		return texto;
	}

	public void reiniciarIterador(Usuario usuario) {
		newIterator[usuarioActual].clear();
		avanzarALaSiguienteSugerencia(usuario);
		//newIterator[usuarioActual].add(listaDeExcursionesDelUsuarioActual.get(0));
	}

	public Excursion getExcursionSugeridaActual() {
		return newIterator[usuarioActual].getLast();
	}
}