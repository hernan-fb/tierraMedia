import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class EscribirArchivo {
    private void escribirArchivo(String fileName, List<String> textFile) {
        FileWriter file = null;
        PrintWriter pw = null;
        try {
            file = new FileWriter(fileName);
            pw = new PrintWriter(file);
            for (String text : textFile) {
                pw.println(text);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (file != null) {
                    file.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void escribirArchivoComprasDeUsuarios(List<String> lista) {
        String path = System.getProperty("user.dir");
        path += "/resumenUsuarios.out";
        escribirArchivo(path , lista);
    }
}
