import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LeerArchivo {
    List<Excursion> listadoUnificado;
    List<Excursion> listadoAtracciones;
    List<Excursion> listadoPromociones;
    List<Usuario> listadoUsuarios;

    LeerArchivo() {
        listadoAtracciones = listadoAtracciones();
        listadoPromociones = listadoPromociones();
        listadoUsuarios = perfilesUsuario();
        listadoUnificado = Stream.concat(listadoPromociones.stream(), listadoAtracciones.stream())
                        .collect(Collectors.toList());
    }

    public List<Excursion> getListadoExcursionesUnificado() {
        return listadoUnificado;
    }

    public List<Excursion> getListadoAtracciones() {
        return listadoAtracciones;
    }

    public List<Excursion> getListadoPromociones() {
        return listadoPromociones;
    }

    public List<Usuario> getListadoUsuarios() {
        return listadoUsuarios;
    }

    private List<Excursion> listadoAtracciones() {//
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        List<Excursion> listaAtracciones = new ArrayList<Excursion>();
        String path = System.getProperty("user.dir");
        path += "/atracciones.in";

        try {
            archivo = new File(path);
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);
            String linea;
            int codigo = 1;
            while ((linea = br.readLine()) != null) {
                String[] aux = linea.split("-");
                String nombre = aux[0];
                double costo = Double.valueOf(aux[1]);
                double tiempo = Double.valueOf(aux[2]);
                int cupo = Integer.parseInt(aux[3]);
                TipoExcursion tipo = TipoExcursion.valueOf(aux[4].toUpperCase());
                listaAtracciones.add(new Atraccion(nombre, codigo++, costo, tiempo, cupo, tipo));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fr != null) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return listaAtracciones;
    }

    private List<Excursion> listadoPromociones() {
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        List<Excursion> listaAtracciones = listadoAtracciones();
        List<Excursion> listaPromociones = new ArrayList<>();//guardar atracciones
        String path = System.getProperty("user.dir");
        path += "/promociones.in";
        try {
            archivo = new File(path);
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);
            String linea;
            int codigoPromo = 101;

            while ((linea = br.readLine()) != null) {
                String[] aux = linea.split(" ");
                String pack = aux[0];// descripcion pack
                TipoExcursion tipoPromo = TipoExcursion.valueOf(aux[1].toUpperCase());// tipo de promocion
                String codpromo = aux[3];
                String promo = aux[4];
                String[] aux2 = aux[2].split(",");
                List<Excursion> atracciones = new ArrayList<>();//guardo las atracciones de las promos
                for (int i = 0; i < aux2.length; i++) {
                    int codigo = Integer.parseInt(aux2[i]);
                    atracciones.addAll(listaAtracciones.stream().filter(atraccion -> atraccion.getCodigo() == codigo).collect(Collectors.toList()));
                }

                if (codpromo.equals("PP")) {
                    listaPromociones.add(new PromocionPorcentual(pack, codigoPromo++, Double.valueOf(promo), atracciones, tipoPromo));
                } else if (codpromo.equals("PA")) {
                    listaPromociones.add(new PromocionAbsoluta(pack, codigoPromo++, Double.valueOf(promo), atracciones, tipoPromo));
                } else if (codpromo.equals("PAB")) {
                    listaPromociones.add(new PromocionAxB(pack, codigoPromo++, Integer.parseInt(promo), atracciones, tipoPromo));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fr != null) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return listaPromociones;
    }

    private List<Usuario> perfilesUsuario() {//
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        List<Usuario> listaUsuarios = new ArrayList<>();
        String path = System.getProperty("user.dir");
        path += "/usuarios.in";
        try {
            archivo = new File(path);
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] aux = linea.split(" ");
                String nombre = aux[0];
                if (aux.length == 2) {
                    PerfilUsuario perfil = PerfilUsuario.valueOf(aux[1].toUpperCase());
                    listaUsuarios.add(new Usuario(nombre, perfil));
                } else {
                    TipoExcursion tipo = TipoExcursion.valueOf(aux[4].toUpperCase());
                    double monedas = Double.valueOf(aux[2]);
                    double tiempo = Double.valueOf(aux[3]);
                    listaUsuarios.add(new Usuario(nombre, monedas, tiempo, tipo));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fr != null) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return listaUsuarios;
    }
}