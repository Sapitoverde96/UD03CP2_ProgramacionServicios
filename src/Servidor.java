import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Random;

public class Servidor {
    static void main() {
        final int puerto = 5002;
        Random random = new Random();

        try (ServerSocket serverSocket = new ServerSocket(puerto)) {
            IO.println("Servidor 'Adivina el número' en puerto %s".formatted(puerto));

            while (true) {
                try (Socket socket = serverSocket.accept();
                     BufferedReader bfLector   = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     PrintWriter    pwEscritor = new PrintWriter(socket.getOutputStream(), true)
                ) {
                    int numeroSecreto = random.nextInt(100) + 1;
                    int intentos = 0;
                    IO.println("Nuevo cliente. Número secreto: %s".formatted(numeroSecreto));
                    String mensajeCliente;
                    while ((mensajeCliente = bfLector.readLine()) != null) {
                        try {
                            int intento = Integer.parseInt(mensajeCliente.trim());
                            intentos++;
                            if (intento < numeroSecreto) {
                                pwEscritor.println("MAYOR");
                            } else if (intento > numeroSecreto) {
                                pwEscritor.println("MENOR");
                            } else {
                                pwEscritor.println("CORRECTO %s".formatted(intentos));
                                IO.println("Cliente acertó en %s intentos.".formatted(intentos));
                                break;
                            }
                        } catch (NumberFormatException exception) {
                            pwEscritor.println("ERROR: Debes enviar un número válido");
                        }
                    }
                } catch (IOException exception) {
                    IO.println("Error con el cliente: %s".formatted(exception.getMessage()));
                }
            }
        } catch (IOException exception) {
            IO.println("Error de conexión: " + exception.getMessage());
        } catch (IllegalArgumentException exception) {
            IO.println("Puerto fuera de rango: %s".formatted(exception.getMessage()));
        }
    }
}
