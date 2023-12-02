package actividadSockets.cliente;

import actividadSockets.servidor.Pelicula;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ClienteSocketHilos {
/*
* La aplicación cliente mostrará un menú como el que sigue:

Consultar película por ID
Consultar película por título
Consultar películas por director
Agregar película
Salir de la aplicación
* */

    public static final int PUERTO = 2018;
    public static final String IP_SERVER = "localhost";

    public static void main(String[] args) throws IOException {
        String id;
        String numero2;
        String operandos = null;
        PrintStream salida;
        System.out.println("        APLICACI�N CLIENTE SOCKETS        ");
        System.out.println("-----------------------------------");

        InetSocketAddress direccionServidor = new InetSocketAddress(IP_SERVER, PUERTO);
        boolean flag = true;

        String result;
        Socket socketAlServidor = new Socket();
        String eleccion = "0";
        InputStreamReader entrada;
        BufferedReader bf;
        String resultado;

        try (Scanner sc = new Scanner(System.in)) {
            socketAlServidor.connect(direccionServidor);
            while (flag) {
                System.out.println("Que operacion desea realizar?");
                System.out.println("1-Consultar Pelicula ID\n2-Consultar Pelicula Titulo\n3-Consultar Pelicula Director\n4-Agregar Pelicula\n5-Salir");

                eleccion = sc.nextLine();

                switch (eleccion) {
                    case "1":
                        System.out.println("CLIENTE: El ID de la pelicula");
                        id = sc.nextLine();
                        salida = new PrintStream(socketAlServidor.getOutputStream());
                        result = "1" + "-" + id;
                        salida.println(result);

                        entrada = new InputStreamReader(socketAlServidor.getInputStream());
                        bf = new BufferedReader(entrada);

                        System.out.println("CLIENTE: Esperando al resultado del servidor...");
                        resultado = bf.readLine();
                        System.out.println("CLIENTE: La pelicula es: " + resultado);
                        break;

                    case "2":
                        System.out.println("CLIENTE: El Titulo de la pelicula");
                        id = sc.nextLine();
                        salida = new PrintStream(socketAlServidor.getOutputStream());
                        result = "2" + "-" + id;
                        salida.println(result);

                        entrada = new InputStreamReader(socketAlServidor.getInputStream());
                        bf = new BufferedReader(entrada);

                        System.out.println("CLIENTE: Esperando al resultado del servidor...");
                        resultado = bf.readLine();
                        System.out.println("CLIENTE: La pelicula es: " + resultado);
                        break;
                    case "3":
                        System.out.println("CLIENTE: El director de la pelicula");
                        String director = sc.nextLine();
                        salida = new PrintStream(socketAlServidor.getOutputStream());
                        result = "3" + "-" + director;
                        salida.println(result);

                        ObjectInputStream objectInputStream = new ObjectInputStream(socketAlServidor.getInputStream());
                        try {
                            ArrayList<Pelicula> peliculas = (ArrayList<Pelicula>) objectInputStream.readObject();

                            if (peliculas.isEmpty()) {
                                System.out.println("CLIENTE: No se han encontrado películas");
                            } else {
                                System.out.println("CLIENTE: Películas:");
                                for (Pelicula pelicula : peliculas) {
                                    System.out.println(pelicula);
                                }
                            }
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        break;

                    case "4":

//                        System.out.println("CLIENTE: Ingrese El ID:");
//                        id = sc.nextLine();
                        System.out.println("CLIENTE: Ingrese El Título:");
                        String titulo = sc.nextLine();
                        System.out.println("CLIENTE: Ingrese El Director:");
                        String director1 = sc.nextLine();
                        System.out.println("CLIENTE: Ingrese El Precio:");
                        String precio = sc.nextLine();

                        salida = new PrintStream(socketAlServidor.getOutputStream());
                        String resultado4 = "4" + "-" + titulo + "-" + director1 + "-" + precio;
                        salida.println(resultado4);

                        entrada = new InputStreamReader(socketAlServidor.getInputStream());
                        bf = new BufferedReader(entrada);

                        System.out.println("CLIENTE: Esperando al resultado del servidor...");
                        resultado = bf.readLine();
                        System.out.println("CLIENTE: " + resultado);
                        break;


                    case "5":
                        System.out.println("Hasta pronto!");
                        operandos = "salir";
                        //socketAlServidor.connect(direccionServidor);
                        salida = new PrintStream(socketAlServidor.getOutputStream());
                        salida.println(operandos);
                        flag = false;
                }

            }

        } catch (Exception e) {
        }
    }

}
