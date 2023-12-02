package actividadSockets.servidor;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;

public class ServerSocketHilos {

    public static HashMap<Integer, Pelicula> peliculas = new HashMap<>();
    private final static int PUERTO = 2018;
    private static final ThreadGroup hiloGrupo = new ThreadGroup("HilosClientes");


    public static void main(String[] args) {
        peliculas.put(1, new Pelicula("El Padrino", "Francis Ford Coppola", 10.99));
        peliculas.put(2, new Pelicula("Titanic", "James Cameron", 8.99));
        peliculas.put(3, new Pelicula("La La Land", "Damien Chazelle", 12.50));
        peliculas.put(4, new Pelicula("Inception", "Christopher Nolan", 11.75));
        peliculas.put(5, new Pelicula("The Shawshank Redemption", "Frank Darabont", 9.99));
        peliculas.put(6, new Pelicula("Avatar", "James Cameron", 14.99));


        System.out.println("SERVIDOR SOCKET HILOS");
        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            int numHilo = 1;
            while (true) {
                Socket socketCliente = serverSocket.accept();
                Thread hiloCliente = new Thread(hiloGrupo, new ClienteInteraction(socketCliente, Thread.currentThread(), String.valueOf(numHilo), peliculas));
                //Thread hiloCliente = new Thread(new ClienteInteraction(socketCliente, Thread.currentThread(), String.valueOf(numHilo), peliculas));
                hiloCliente.start();
                System.out.println(peliculas.toString());
                System.out.println("Hilo " + numHilo);
                numHilo++;
            }
        } catch (SocketException se) {
            System.out.println("Socket cerrado");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
