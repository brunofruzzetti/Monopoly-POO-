package monopoly;

import partida.*;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;


public class Tablero {
    //Atributos.
    private ArrayList<ArrayList<Casilla>> posiciones; //Posiciones del tablero: se define como un arraylist de arraylists de casillas (uno por cada lado del tablero).
    private HashMap<String, Grupo> grupos; //Grupos del tablero, almacenados como un HashMap con clave String (será el color del grupo).
    private Jugador banca; //Un jugador que será la banca.

    public ArrayList<ArrayList<Casilla>> getPosiciones() {
        return posiciones;
    }

    public HashMap<String, Grupo> getGrupos() {
        return grupos;
    }

    public Jugador getBanca() {
        return banca;
    }

    public void setPosiciones(ArrayList<ArrayList<Casilla>> posiciones) {
        this.posiciones = posiciones;
    }

    public void setGrupos(HashMap<String, Grupo> grupos) {
        this.grupos = grupos;
    }

    public void setBanca(Jugador banca) {
        this.banca = banca;
    }

    //Constructor: únicamente le pasamos el jugador banca (que se creará desde el menú).
    public Tablero(Jugador banca) {
        this.banca = banca;
        this.posiciones = new ArrayList<>();
        this.grupos = new HashMap<>();
        this.generarCasillas();
        this.generarGrupos();
        //Al principio, la banca es dueña de todas las propiedades.
        for (ArrayList<Casilla> lado : posiciones) {
            for (Casilla c : lado) {
                if (c.esComprable()) {
                    banca.anhadirPropiedad(c);
                }
            }
        }
    }


    //Método para crear todas las casillas del tablero. Formado a su vez por cuatro métodos (1/lado).
    //Las posiciones van de 1 (Salida) a 40 (Solar22), en el sentido de avance de los avatares.
    private void generarCasillas() {
        this.insertarLadoSur();
        this.insertarLadoOeste();
        this.insertarLadoNorte();
        this.insertarLadoEste();
    }

    //Método para insertar las casillas del lado norte (posiciones 21 a 30).
    private void insertarLadoNorte() {
        ArrayList<Casilla> lado = new ArrayList<>();
        lado.add(new Casilla("Parking", "Especial", 21, banca));
        lado.add(crearSolar("Solar12", 22, 2200000, 1100000, 1500000, 1500000, 300000, 600000, 180000, 2200000, 10500000, 2100000, 2100000));
        lado.add(new Casilla("Suerte", "Suerte", 23, banca));
        lado.add(crearSolar("Solar13", 24, 2200000, 1100000, 1500000, 1500000, 300000, 600000, 180000, 2200000, 10500000, 2100000, 2100000));
        lado.add(crearSolar("Solar14", 25, 2400000, 1200000, 1500000, 1500000, 300000, 600000, 200000, 2325000, 11000000, 2200000, 2200000));
        lado.add(new Casilla("Trans3", "Transporte", 26, Valor.PRECIO_TRANSPORTE, banca));
        lado.add(crearSolar("Solar15", 27, 2600000, 1300000, 1500000, 1500000, 300000, 600000, 220000, 2450000, 11500000, 2300000, 2300000));
        lado.add(crearSolar("Solar16", 28, 2600000, 1300000, 1500000, 1500000, 300000, 600000, 220000, 2450000, 11500000, 2300000, 2300000));
        lado.add(new Casilla("Serv2", "Servicios", 29, Valor.PRECIO_SERVICIO, banca));
        lado.add(crearSolar("Solar17", 30, 2800000, 1400000, 1500000, 1500000, 300000, 600000, 240000, 2600000, 12000000, 2400000, 2400000));
        posiciones.add(lado);
    }

    //Método para insertar las casillas del lado sur (posiciones 1 a 10).
    private void insertarLadoSur() {
        ArrayList<Casilla> lado = new ArrayList<>();
        lado.add(new Casilla("Salida", "Especial", 1, banca));
        lado.add(crearSolar("Solar1", 2, 600000, 300000, 500000, 500000, 100000, 200000, 20000, 400000, 2500000, 500000, 500000));
        lado.add(new Casilla("Caja", "Comunidad", 3, banca));
        lado.add(crearSolar("Solar2", 4, 600000, 300000, 500000, 500000, 100000, 200000, 40000, 800000, 4500000, 900000, 900000));
        lado.add(new Casilla("Imp1", 5, Valor.IMPUESTO, banca));
        lado.add(new Casilla("Trans1", "Transporte", 6, Valor.PRECIO_TRANSPORTE, banca));
        lado.add(crearSolar("Solar3", 7, 1000000, 500000, 500000, 500000, 100000, 200000, 60000, 1000000, 5500000, 1100000, 1100000));
        lado.add(new Casilla("Suerte", "Suerte", 8, banca));
        lado.add(crearSolar("Solar4", 9, 1000000, 500000, 500000, 500000, 100000, 200000, 60000, 1000000, 5500000, 1100000, 1100000));
        lado.add(crearSolar("Solar5", 10, 1200000, 600000, 500000, 500000, 100000, 200000, 80000, 1250000, 6000000, 1200000, 1200000));
        posiciones.add(lado);
    }

    //Método que inserta casillas del lado oeste (posiciones 11 a 20).
    private void insertarLadoOeste() {
        ArrayList<Casilla> lado = new ArrayList<>();
        lado.add(new Casilla("Cárcel", "Especial", 11, banca));
        lado.add(crearSolar("Solar6", 12, 1400000, 700000, 1000000, 1000000, 200000, 400000, 100000, 1500000, 7500000, 1500000, 1500000));
        lado.add(new Casilla("Serv1", "Servicios", 13, Valor.PRECIO_SERVICIO, banca));
        lado.add(crearSolar("Solar7", 14, 1400000, 700000, 1000000, 1000000, 200000, 400000, 100000, 1500000, 7500000, 1500000, 1500000));
        lado.add(crearSolar("Solar8", 15, 1600000, 800000, 1000000, 1000000, 200000, 400000, 120000, 1750000, 9000000, 1800000, 1800000));
        lado.add(new Casilla("Trans2", "Transporte", 16, Valor.PRECIO_TRANSPORTE, banca));
        lado.add(crearSolar("Solar9", 17, 1800000, 900000, 1000000, 1000000, 200000, 400000, 140000, 1850000, 9500000, 1900000, 1900000));
        lado.add(new Casilla("Caja", "Comunidad", 18, banca));
        lado.add(crearSolar("Solar10", 19, 1800000, 900000, 1000000, 1000000, 200000, 400000, 140000, 1850000, 9500000, 1900000, 1900000));
        lado.add(crearSolar("Solar11", 20, 2200000, 1000000, 1000000, 1000000, 200000, 400000, 160000, 2000000, 10000000, 2000000, 2000000));
        posiciones.add(lado);
    }

    //Método que inserta las casillas del lado este (posiciones 31 a 40).
    private void insertarLadoEste() {
        ArrayList<Casilla> lado = new ArrayList<>();
        lado.add(new Casilla("IrCarcel", "Especial", 31, banca));
        lado.add(crearSolar("Solar18", 32, 3000000, 1500000, 2000000, 2000000, 400000, 800000, 260000, 2750000, 12750000, 2550000, 2550000));
        lado.add(crearSolar("Solar19", 33, 3000000, 1500000, 2000000, 2000000, 400000, 800000, 260000, 2750000, 12750000, 2550000, 2550000));
        lado.add(new Casilla("Caja", "Comunidad", 34, banca));
        lado.add(crearSolar("Solar20", 35, 3200000, 1600000, 2000000, 2000000, 400000, 800000, 280000, 3000000, 14000000, 2800000, 2800000));
        lado.add(new Casilla("Trans4", "Transporte", 36, Valor.PRECIO_TRANSPORTE, banca));
        lado.add(new Casilla("Suerte", "Suerte", 37, banca));
        lado.add(crearSolar("Solar21", 38, 3500000, 1750000, 2000000, 2000000, 400000, 800000, 350000, 3250000, 17000000, 3400000, 3400000));
        lado.add(new Casilla("Imp2", 39, Valor.IMPUESTO, banca));
        lado.add(crearSolar("Solar22", 40, 4000000, 2000000, 2000000, 2000000, 400000, 800000, 500000, 4250000, 20000000, 4000000, 4000000));
        posiciones.add(lado);
    }

    //Crea un solar con los valores del Apéndice I.
    private Casilla crearSolar(String nombre, int posicion, float precio, float hipoteca,
                               float precioCasa, float precioHotel, float precioPiscina, float precioPista,
                               float alquiler, float alquilerCasa, float alquilerHotel, float alquilerPiscina, float alquilerPista) {
        Casilla solar = new Casilla(nombre, "Solar", posicion, precio, banca);
        solar.setDatosSolar(hipoteca, alquiler, precioCasa, precioHotel, precioPiscina, precioPista,
                alquilerCasa, alquilerHotel, alquilerPiscina, alquilerPista);
        return solar;
    }

    //Crea los ocho grupos de solares y los guarda en el HashMap con el color como clave.
    private void generarGrupos() {
        anhadirGrupo(new Grupo(encontrar_casilla("Solar1"), encontrar_casilla("Solar2"), "Marron"));
        anhadirGrupo(new Grupo(encontrar_casilla("Solar3"), encontrar_casilla("Solar4"), encontrar_casilla("Solar5"), "Celeste"));
        anhadirGrupo(new Grupo(encontrar_casilla("Solar6"), encontrar_casilla("Solar7"), encontrar_casilla("Solar8"), "Rosa"));
        anhadirGrupo(new Grupo(encontrar_casilla("Solar9"), encontrar_casilla("Solar10"), encontrar_casilla("Solar11"), "Naranja"));
        anhadirGrupo(new Grupo(encontrar_casilla("Solar12"), encontrar_casilla("Solar13"), encontrar_casilla("Solar14"), "Rojo"));
        anhadirGrupo(new Grupo(encontrar_casilla("Solar15"), encontrar_casilla("Solar16"), encontrar_casilla("Solar17"), "Amarillo"));
        anhadirGrupo(new Grupo(encontrar_casilla("Solar18"), encontrar_casilla("Solar19"), encontrar_casilla("Solar20"), "Verde"));
        anhadirGrupo(new Grupo(encontrar_casilla("Solar21"), encontrar_casilla("Solar22"), "Morado"));
    }

    private void anhadirGrupo(Grupo grupo) {
        grupos.put(grupo.getColorGrupo(), grupo);
    }

    //Devuelve la casilla que ocupa la posición indicada (de 1 a 40).
    public Casilla getCasilla(int posicion) {
        for (ArrayList<Casilla> lado : posiciones) {
            for (Casilla c : lado) {
                if (c.getPosicion() == posicion) {
                    return c;
                }
            }
        }
        return null;
    }

    //Para imprimir el tablero, modificamos el método toString().
    @Override
    public String toString() {
        //Ancho de cada celda: el del texto más largo (nombre + avatares), con un mínimo de 9.
        int ancho = 9;
        for (ArrayList<Casilla> lado : posiciones) {
            for (Casilla c : lado) {
                ancho = Math.max(ancho, c.toString().length());
            }
        }
        String linea = "_".repeat(ancho);
        StringBuilder sb = new StringBuilder();

        //Borde superior.
        sb.append(" ");
        for (int i = 0; i < 11; i++) {
            sb.append(linea).append(" ");
        }
        sb.append("\n");

        //Fila norte: de Parking (21) a IrCarcel (31).
        sb.append("|");
        for (int pos = 21; pos <= 31; pos++) {
            sb.append(celda(getCasilla(pos), ancho)).append("|");
        }
        sb.append("\n");

        //Filas intermedias: oeste de Solar11 (20) a Solar6 (12) y este de Solar18 (32) a Solar22 (40).
        for (int i = 0; i < 9; i++) {
            sb.append("|").append(celda(getCasilla(20 - i), ancho)).append("|");
            if (i < 8) {
                sb.append(" ".repeat(9 * ancho + 8));
            } else {
                //En la última fila intermedia se dibuja el borde superior de la fila sur.
                for (int j = 0; j < 9; j++) {
                    sb.append(linea);
                    if (j < 8) sb.append(" ");
                }
            }
            sb.append("|").append(celda(getCasilla(32 + i), ancho)).append("|\n");
        }

        //Fila sur: de Cárcel (11) a Salida (1).
        sb.append("|");
        for (int pos = 11; pos >= 1; pos--) {
            sb.append(celda(getCasilla(pos), ancho)).append("|");
        }
        return sb.toString();
    }

    //Devuelve el texto de una casilla subrayado, con el color de su grupo y relleno hasta el ancho indicado.
    //Se rellena antes de añadir los códigos de color para que no cuenten en el ancho.
    private String celda(Casilla c, int ancho) {
        String texto = String.format("%-" + ancho + "s", c.toString());
        String color = (c.getGrupo() != null) ? c.getGrupo().getCodigoColor() : "";
        return color + Valor.SUBRAYADO + texto + Valor.RESET;
    }

    //Método usado para buscar la casilla con el nombre pasado como argumento (sin distinguir mayúsculas ni tildes):
    public Casilla encontrar_casilla(String nombre){
        String buscado = normalizar(nombre);
        for (ArrayList<Casilla> lado : posiciones) {
            for (Casilla c : lado) {
                if (normalizar(c.getNombre()).equals(buscado)) {
                    return c;
                }
            }
        }
        return null;
    }

    private static String normalizar(String texto) {
        return Normalizer.normalize(texto, Normalizer.Form.NFD).replaceAll("\\p{M}", "").toLowerCase();
    }
}
