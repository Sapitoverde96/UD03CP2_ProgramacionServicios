import java.net.Socket;
import java.net.UnknownHostException;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Cliente {
    static void main() {
        final String direccionIP = "localhost";
        final int puerto = 5002;

        try (Socket socket = new Socket(direccionIP, puerto);
             BufferedReader bfLector   = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter    pwEscritor = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader teclado    = new BufferedReader(new InputStreamReader(System.in))
        ) {
            IO.println("Conectado al juego 'Adivina el número'.");
            IO.println("He pensado un número entre 1 y 100. Adivínalo.");
            String mensajeCliente;
            while ((mensajeCliente = teclado.readLine()) != null) {
                pwEscritor.println(mensajeCliente);
                String respuestaServidor = bfLector.readLine();
                if (respuestaServidor == null) {
                    break;
                }
                IO.println(respuestaServidor);
                if (respuestaServidor.startsWith("CORRECTO")) {
                    IO.println("¡Enhorabuena!");
                    break;
                }
            }
        } catch (UnknownHostException exception) {
            IO.println("Host desconocido: %s".formatted(exception.getMessage()));
        } catch (IOException exception) {
            IO.println("Error de conexión: %s".formatted(exception.getMessage()));
        } catch (IllegalArgumentException exception) {
            IO.println("Puerto fuera de rango: %s".formatted(exception.getMessage()));
        }
    }
}
