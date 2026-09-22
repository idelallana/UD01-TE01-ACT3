package ejercicio3;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Programa que comprueba si los 4 primeros bytes de un fichero
 * coinciden con la firma habitual de un fichero ZIP: 80, 75, 3, 4
 * (en hexadecimal: 0x50, 0x4B, 0x03, 0x04 -> "PK\3\4").
 *
 * Uso: java FirmaZip <ruta_del_fichero>
 */
public class ficheroZIP {

    // Firma esperada de un fichero ZIP
    private static final int[] FIRMA_ZIP = {80, 75, 3, 4};

    public static void main(String[] args) {

        // Comprobamos que se ha pasado la ruta del fichero como argumento
        if (args.length != 1) {
            System.out.println("Uso: java FirmaZip <ruta_del_fichero>");
            return;
        }

        String ruta = args[0];

        // Array donde guardaremos los bytes leídos (máximo 4)
        byte[] cabecera = new byte[4];

        // try-with-resources: el InputStream se cierra solo, aunque salte una excepción
        try (InputStream entrada = new FileInputStream(ruta)) {

            // Leemos como máximo 4 bytes desde el inicio del fichero.
            // read(byte[]) puede devolver menos bytes de los pedidos,
            // así que hay que comprobar el valor devuelto (bytesLeidos).
            int bytesLeidos = entrada.read(cabecera);

            // Si el fichero está vacío, read() devuelve -1
            if (bytesLeidos == -1) {
                System.out.println("El fichero está vacío: no se ha podido leer ningún byte.");
                return;
            }

            // Si se han leído menos de 4 bytes, el fichero es demasiado pequeño
            if (bytesLeidos < 4) {
                System.out.println("El fichero contiene menos de 4 bytes (solo " + bytesLeidos
                        + "), no se puede comprobar la firma ZIP.");
                return;
            }

            // Comparamos byte a byte los 4 primeros bytes leídos con la firma esperada.
            // Los bytes en Java son con signo (-128 a 127), así que los convertimos
            // a valores sin signo (0 a 255) con "& 0xFF" antes de comparar.
            boolean coincide = true;
            for (int i = 0; i < FIRMA_ZIP.length; i++) {
                int valorLeido = cabecera[i] & 0xFF;
                if (valorLeido != FIRMA_ZIP[i]) {
                    coincide = false;
                    break;
                }
            }

            if (coincide) {
                System.out.println("La cabecera es compatible con un fichero ZIP.");
            } else {
                System.out.println("La cabecera no corresponde a la firma esperada de un fichero ZIP.");
            }

        } catch (IOException e) {
            System.out.println("Error al leer el fichero: " + e.getMessage());
        }
    }
}

