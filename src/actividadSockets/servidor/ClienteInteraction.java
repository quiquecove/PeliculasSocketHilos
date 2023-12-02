package actividadSockets.servidor;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClienteInteraction implements Runnable {

    private Socket socket;
    private final Thread hilo;
    private String nombrehilo;
    public HashMap<Integer, Pelicula> peliculas;

    String dato1;
    String dato2;


    public ClienteInteraction(Socket socket, Thread hilo, String nombrehilo, HashMap<Integer, Pelicula> peliculas) {
        this.socket = socket;
        this.hilo = hilo;
        this.nombrehilo = nombrehilo;
        this.peliculas = peliculas;
        //this.hilo.start();
    }

    @Override
    public void run() {
        System.out.println("Hilo cliente " + nombrehilo);
        try (PrintStream salida = new PrintStream(socket.getOutputStream());
             InputStreamReader entrada = new InputStreamReader(socket.getInputStream());
             BufferedReader entradaBuffer = new BufferedReader(entrada)) {
            boolean flag = true;
            while (flag) {
                String stringRecibido = entradaBuffer.readLine();
                if (stringRecibido == null || stringRecibido.equalsIgnoreCase("FIN")) {
                    break;
                }

                if (stringRecibido.startsWith("1")) {
                    String[] operadores = stringRecibido.split("-");
                    int id = Integer.parseInt(operadores[1]);
                    Pelicula pelicula = consultarPeliculaID(id);
                    if (pelicula != null) {
                        salida.println(pelicula);
                    } else {
                        salida.println("Pelicula no encontrada");
                    }

                    // Enviar una nueva línea para indicar el final de la respuesta
                    //salida.println();
                } else if (stringRecibido.startsWith("2")) {
                    String[] operadores = stringRecibido.split("-");
                    String titulo = operadores[1];
                    Pelicula pelicula = consultarPeliculaTitulo(titulo);
                    if (pelicula != null) {
                        salida.println(pelicula);
                    } else {
                        salida.println("Pelicula no encontrada");
                    }

                    // Enviar una nueva línea para indicar el final de la respuesta
                    salida.println();


                } else if (stringRecibido.startsWith("3")) {
                    String[] operadores = stringRecibido.split("-");
                    String director = operadores[1];
                    ArrayList<Pelicula> peliculasDirector = consultarPeliculaDirector(director);
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                    objectOutputStream.writeObject(peliculasDirector);
                    objectOutputStream.flush();


                    // Enviar una nueva línea para indicar el final de la respuesta
                    //salida.println();


                } else if (stringRecibido.startsWith("4")) {
                    String[] operadores = stringRecibido.split("-");
                    int id = peliculas.size() + 1;
                    String titulo = operadores[1];
                    String director = operadores[2];
                    double precio = Double.valueOf(operadores[3]);

                    // Intenta convertir el ID a un entero


                    Pelicula peliad = new Pelicula(titulo, director, precio);
                    agregarPelicula(id, peliad);
                    salida.println("Pelicula añadida" + consultarPeliculaID(id));


                } else if (stringRecibido.contains("salir")) {
                    System.out.println("Cliente exit");
                    String resultado = "Server continua";
                    salida.println(resultado);
                    flag = false;
                    try {
                        socket.close();
                    } catch (IOException e) {
                        // Manejo de excepciones, por ejemplo, registro de error
                        System.err.println("Error al cerrar el socket: " + e.getMessage());
                    }


                }
            }
        } catch (
                IOException e) {
            e.printStackTrace();
        }

    }

/*
La aplicación cliente mostrará un menú como el que sigue:
    Consultar película por ID
    Consultar película por título
    Consultar películas por director
    Agregar película
Salir de la aplicación
* */

    public synchronized Pelicula consultarPeliculaID(int ID) {
        for (Map.Entry<Integer, Pelicula> peli : peliculas.entrySet()) {
            if (peli.getKey() == ID) {
                return peli.getValue();
            }
        }
        return null;
    }

    public synchronized Pelicula consultarPeliculaTitulo(String titulo) {
        for (Map.Entry<Integer, Pelicula> peli : peliculas.entrySet()) {
            if (peli.getValue().getTitulo().equalsIgnoreCase(titulo.trim())) {
                return peli.getValue();
            }
        }
        return null;
    }

//    public synchronized ArrayList<Pelicula> consultarPeliculaTitulo(String titulo) {
//        try {
//            ArrayList<Pelicula> pelis = new ArrayList<>();
//            for (Map.Entry<Integer, Pelicula> peli : peliculas.entrySet()) {
//                if (peli.getValue().getTitulo().contains(titulo.toLowerCase())) {
//                    pelis.add(peli.getValue());
//                }
//            }
//            return pelis;
//        } finally {
//            notify();
//        }
//    }

    public synchronized ArrayList<Pelicula> consultarPeliculaDirector(String director) {
        try {
            ArrayList<Pelicula> pelis = new ArrayList<>();
            for (Map.Entry<Integer, Pelicula> peli : peliculas.entrySet()) {
                if (peli.getValue().getDirector().equalsIgnoreCase(director)) {
                    pelis.add(peli.getValue());
                }
            }
            return pelis;
        } finally {
            //notify();
            System.out.println("");
        }
    }

    public synchronized boolean agregarPelicula(Integer id, Pelicula pelicula) {
        try {
            if (peliculas.containsKey(id)) {
                System.out.println("Error: Ya existe una película con ese ID.");
                return false;
            } else {
                peliculas.put(id, pelicula);
                System.out.println("Pelicula añadida");
                return true;
            }
        } finally {
            System.out.println();
        }
    }
//hacer hashmap temporal para recoger consultas

    public String printPelis() {
        for (Pelicula pelicula : peliculas.values()) {
            System.out.println(pelicula.toString());
        }
        return peliculas.values().toString();
    }

}
