package actividadSockets.servidor;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClienteInteraction implements Runnable {

    private Socket socket;
    private final Thread hilo;
    private String nombrehilo;
    public HashMap<Integer,Pelicula> peliculas;

    public ClienteInteraction(Socket socket, Thread hilo, String nombrehilo, HashMap<Integer, Pelicula> peliculas) {
        this.socket = socket;
        this.hilo = hilo;
        this.nombrehilo = nombrehilo;
        this.peliculas = peliculas;
        this.hilo.start();
    }

    @Override
    public void run() {





    }

/*
La aplicación cliente mostrará un menú como el que sigue:
    Consultar película por ID
    Consultar película por título
    Consultar películas por director
    Agregar película
Salir de la aplicación
* */

    public synchronized Pelicula consultarPeliculaID(int ID){
       try {
           for (Map.Entry<Integer, Pelicula> peli : peliculas.entrySet()) {
               if (peli.getKey() == ID) {

                   return peli.getValue();
               }
           }
           return null;
       }finally {
           notify();
       }
    }
    public synchronized ArrayList<Pelicula> consultarPeliculaTitulo(String titulo){
        try {
            ArrayList<Pelicula> pelis = new ArrayList<>();
            for (Map.Entry<Integer, Pelicula> peli : peliculas.entrySet()) {
                if (peli.getValue().getTitulo().contains(titulo.toLowerCase())) {
                    pelis.add(peli.getValue());
                }
            }
            return pelis;
        }finally {
            notify();
        }
    }

    public synchronized ArrayList<Pelicula> consultarPeliculaDirector(String director){
        try {
            ArrayList<Pelicula> pelis = new ArrayList<>();
            for (Map.Entry<Integer, Pelicula> peli : peliculas.entrySet()) {
                if (peli.getValue().getDirector().equals(director.toLowerCase())) {
                    pelis.add(peli.getValue());
                }
            }
            return pelis;
        }finally {
            notify();
        }
    }

    public synchronized boolean agregarPelicula(Integer id, Pelicula pelicula) {
        try {
            for (Map.Entry<Integer, Pelicula> peli : peliculas.entrySet()) {
                if (id == peli.getKey()) {
                    System.out.println("Error");
                    return false;
                } else {
                    peliculas.put(id, pelicula);
                    System.out.println("Pelicula añadida");
                    return true;
                }
            }
        } finally {
            notify();
        }
        return false;
    }
//hacer hashmap temporal para recoger consultas
}
