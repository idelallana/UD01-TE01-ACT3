package ejercicio3;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ficheroZIP {

    public static void main(String[] args) {

        // Comprobamos que nos pasan la ruta
        if (args.length != 1) {
            System.out.println("Uso: java ficheroZIP <ruta_del_fichero>");
            return;
        }

        String ruta = args[0];
        byte[] cabecera = new byte[4];

        // try con recursos: cierra el flujo automáticamente
        try (InputStream entrada = new FileInputStream(ruta)) {

            // Leemos solo los 4 primeros bytes
            int bytesLeidos = entrada.read(cabecera);

            // Si leemos menos de 4 (o -1 si está vacío), no podemos comprobar la firma
            if (bytesLeidos < 4) {
                System.out.println("El fichero tiene menos de 4 bytes, no se puede comprobar la firma.");
            } else if (cabecera[0] == 80 && cabecera[1] == 75
                    && cabecera[2] == 3 && cabecera[3] == 4) {
                System.out.println("La cabecera es compatible con un fichero ZIP.");
            } else {
                System.out.println("La cabecera no corresponde a la firma esperada de un fichero ZIP.");
            }

        } catch (IOException e) {
            System.out.println("Error al leer el fichero: " + e.getMessage());
        }
    }
}

