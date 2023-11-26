package actividadSockets.servidor;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class ServerSocketHilos {

    public static HashMap<Integer,Pelicula>peliculas=new HashMap<>();
    private final static int PUERTO=2018;

    public static void main(String[] args) {
        try(ServerSocket serverSocket=new ServerSocket(PUERTO)){
            int numHilo = 1;
            while(true) {
                Socket socketCliente = serverSocket.accept();
                new ClienteInteraction(socketCliente, new Thread(), String.valueOf(numHilo), peliculas);
                numHilo++;
                System.out.println(peliculas.toString());

            }
        }catch (Exception e){

        }
    }




}
