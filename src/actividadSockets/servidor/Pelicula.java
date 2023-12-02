package actividadSockets.servidor;

import java.io.Serializable;

public class Pelicula implements Serializable {
    // Atributos
    private String titulo;
    private String director;
    private double precio;

    // Constructor
    public Pelicula( String titulo, String director, double precio) {
        this.titulo = titulo;
        this.director = director;
        this.precio = precio;
    }

    // Métodos de acceso (getters y setters)


    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    // Método toString para imprimir información sobre la película
    @Override
    public String toString() {
        return "titulo='" + titulo + '\'' +
                ", director='" + director + '\'' +
                ", precio=" + precio;
    }
}

