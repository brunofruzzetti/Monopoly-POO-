package monopoly;

import partida.*;


public class Edificio {

    //Atributos
    private String id; //Identificador generado automáticamente (por ejemplo "casa-1").
    private String tipo; //casa, hotel, piscina o pista_deporte.
    private Casilla casilla; //Solar en el que está construido.
    private Jugador duenho; //Jugador que lo ha construido.
    private float coste; //Precio pagado por construirlo.

    //Contadores para generar los identificadores (uno por tipo de edificio).
    private static int numCasas = 0;
    private static int numHoteles = 0;
    private static int numPiscinas = 0;
    private static int numPistas = 0;

    public String getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public Casilla getCasilla() {
        return casilla;
    }

    public Jugador getDuenho() {
        return duenho;
    }

    public float getCoste() {
        return coste;
    }

    /*Constructor principal. Parámetros: tipo de edificio, solar en el que se construye,
     * jugador que lo construye y coste. El identificador se genera automáticamente.
     */
    public Edificio(String tipo, Casilla casilla, Jugador duenho, float coste) {
        this.tipo = tipo;
        this.casilla = casilla;
        this.duenho = duenho;
        this.coste = coste;
        this.id = generarId(tipo);
    }

    //Genera un identificador único con el formato "tipo-n".
    private static String generarId(String tipo) {
        switch (tipo) {
            case "casa": return "casa-" + (++numCasas);
            case "hotel": return "hotel-" + (++numHoteles);
            case "piscina": return "piscina-" + (++numPiscinas);
            default: return "pista-" + (++numPistas);
        }
    }

    //Devuelve el tipo con su artículo, para los mensajes (por ejemplo "una casa").
    public static String conArticulo(String tipo) {
        switch (tipo) {
            case "casa": return "una casa";
            case "hotel": return "un hotel";
            case "piscina": return "una piscina";
            default: return "una pista de deporte";
        }
    }

    //Devuelve la información del edificio (usado en 'listar edificios').
    public String infoEdificio() {
        return "{\n"
                + "  id: " + id + ",\n"
                + "  propietario: " + duenho.getNombre() + ",\n"
                + "  casilla: " + casilla.getNombre() + ",\n"
                + "  grupo: " + casilla.getGrupo().getColorGrupo().toLowerCase() + ",\n"
                + "  coste: " + String.format("%.0f", coste) + "\n"
                + "}";
    }

    @Override
    public String toString() {
        return id;
    }
}
