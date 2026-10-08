package monopoly;

import partida.*;
import java.util.ArrayList;


public class Casilla {

    //Atributos:
    private String nombre; //Nombre de la casilla
    private String tipo; //Tipo de casilla (Solar, Especial, Transporte, Servicios, Comunidad, Suerte y Impuesto).
    private float valor; //Valor de esa casilla (en la mayoría será valor de compra, en la casilla parking se usará como el bote).
    private int posicion; //Posición que ocupa la casilla en el tablero (entero entre 1 y 40).
    private Jugador duenho; //Dueño de la casilla (por defecto sería la banca).
    private Grupo grupo; //Grupo al que pertenece la casilla (si es solar).
    private float impuesto; //Cantidad a pagar por caer en la casilla: el alquiler en solares/servicios/transportes o impuestos.
    private float hipoteca; //Valor otorgado por hipotecar una casilla
    private ArrayList<Avatar> avatares; //Avatares que están situados en la casilla.

    //Precios y alquileres de los edificios de un solar (Apéndice I).
    private float precioCasa;
    private float precioHotel;
    private float precioPiscina;
    private float precioPista;
    private float alquilerCasa;
    private float alquilerHotel;
    private float alquilerPiscina;
    private float alquilerPista;

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public float getValor() {
        return valor;
    }

    public int getPosicion() {
        return posicion;
    }

    public Jugador getDuenho() {
        return duenho;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public float getImpuesto() {
        return impuesto;
    }

    public float getHipoteca() {
        return hipoteca;
    }

    public ArrayList<Avatar> getAvatares() {
        return avatares;
    }

    public float getPrecioCasa() {
        return precioCasa;
    }

    public float getPrecioHotel() {
        return precioHotel;
    }

    public float getPrecioPiscina() {
        return precioPiscina;
    }

    public float getPrecioPista() {
        return precioPista;
    }

    public float getAlquilerCasa() {
        return alquilerCasa;
    }

    public float getAlquilerHotel() {
        return alquilerHotel;
    }

    public float getAlquilerPiscina() {
        return alquilerPiscina;
    }

    public float getAlquilerPista() {
        return alquilerPista;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public void setDuenho(Jugador duenho) {
        this.duenho = duenho;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public void setImpuesto(float impuesto) {
        this.impuesto = impuesto;
    }

    public void setHipoteca(float hipoteca) {
        this.hipoteca = hipoteca;
    }

    public void setAvatares(ArrayList<Avatar> avatares) {
        this.avatares = avatares;
    }

    /*Método para establecer los datos de un solar según el Apéndice I. Parámetros:
     * hipoteca, alquiler, precios de casa, hotel, piscina y pista de deporte, y
     * alquileres de casa, hotel, piscina y pista de deporte.
     */
    public void setDatosSolar(float hipoteca, float alquiler,
                              float precioCasa, float precioHotel, float precioPiscina, float precioPista,
                              float alquilerCasa, float alquilerHotel, float alquilerPiscina, float alquilerPista) {
        this.hipoteca = hipoteca;
        this.impuesto = alquiler;
        this.precioCasa = precioCasa;
        this.precioHotel = precioHotel;
        this.precioPiscina = precioPiscina;
        this.precioPista = precioPista;
        this.alquilerCasa = alquilerCasa;
        this.alquilerHotel = alquilerHotel;
        this.alquilerPiscina = alquilerPiscina;
        this.alquilerPista = alquilerPista;
    }


    //Constructores:
    public Casilla() {
    avatares = new ArrayList<>();
    }//Parámetros vacíos

    /*Constructor para casillas tipo Solar, Servicios o Transporte:
    * Parámetros: nombre casilla, tipo (debe ser solar, serv. o transporte), posición en el tablero, valor y dueño.
    * En los solares, la hipoteca y los alquileres se establecen después con setDatosSolar.
    */
    public Casilla(String nombre, String tipo, int posicion, float valor, Jugador duenho) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.posicion = posicion;
        this.valor = valor;
        this.duenho = duenho;
        this.hipoteca = 0; //Transportes y servicios no se pueden hipotecar.
        if (tipo.equalsIgnoreCase("Transporte")) {
            this.impuesto = Valor.ALQUILER_TRANSPORTE;
        } else if (tipo.equalsIgnoreCase("Servicios")) {
            this.impuesto = Valor.FACTOR_SERVICIO;
        } else {
            this.impuesto = 0;
        }
        this.avatares = new ArrayList<>();
    }

    /*Constructor utilizado para inicializar las casillas de tipo IMPUESTOS.
     * Parámetros: nombre, posición en el tablero, impuesto establecido y dueño.
     */
    public Casilla(String nombre, int posicion, float impuesto, Jugador duenho) {
        this.nombre = nombre;
        this.tipo = "Impuesto";
        this.posicion = posicion;
        this.impuesto = impuesto;
        this.duenho = duenho;
        this.valor = 0;
        this.hipoteca = 0;
        this.avatares = new ArrayList<>();
    }

    /*Constructor utilizado para crear las otras casillas (Suerte, Caja de comunidad y Especiales):
     * Parámetros: nombre, tipo de la casilla (será uno de los que queda), posición en el tablero y dueño.
     */
    public Casilla(String nombre, String tipo, int posicion, Jugador duenho) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.posicion = posicion;
        this.duenho = duenho;
        this.valor = 0;
        this.impuesto = 0;
        this.hipoteca = 0;
        this.avatares = new ArrayList<>();
    }

    //Devuelve el nombre de la casilla seguido de los avatares que hay en ella (por ejemplo "Solar9 &H").
    @Override
    public String toString() {
        if (avatares.isEmpty()) return this.nombre;
        String ids = " &";
        for (Avatar av : avatares) {
            ids += av.getId();
        }
        return this.nombre + ids;
    }

    //Método utilizado para añadir un avatar al array de avatares en casilla.
    public void anhadirAvatar(Avatar av) {
        if (av != null && !this.avatares.contains(av)) {
            this.avatares.add(av);
        }
    }

    //Método utilizado para eliminar un avatar del array de avatares en casilla.
    public void eliminarAvatar(Avatar av) {
        if (av != null) {
            this.avatares.remove(av);
        }
    }

    //Indica si la casilla es una propiedad que se puede comprar (solar, transporte o servicio).
    public boolean esComprable() {
        return tipo.equalsIgnoreCase("Solar") || tipo.equalsIgnoreCase("Transporte")
                || tipo.equalsIgnoreCase("Servicios");
    }

    /*Método para evaluar qué hacer en una casilla concreta. Parámetros:
    * - Jugador cuyo avatar está en esa casilla.
    * - La banca (para ciertas comprobaciones).
    * - El valor de la tirada: para determinar impuesto a pagar en casillas de servicios.
    * Valor devuelto: true en caso de ser solvente (es decir, de cumplir las deudas), y false
    * en caso de no cumplirlas.*/
    public boolean evaluarCasilla(Jugador actual, Jugador banca, int tirada) {
        return true;
    }

    /*Método usado para comprar una casilla determinada. Parámetros:
     * - Jugador que solicita la compra de la casilla.
     * - Banca del monopoly (es el dueño de las casillas no compradas aún).*/
    public void comprarCasilla(Jugador solicitante, Jugador banca) {
        if (!esComprable()) {
            System.out.println("La casilla " + nombre + " no se puede comprar.");
            return;
        }
        if (duenho != banca) {
            System.out.println("La casilla " + nombre + " ya pertenece a " + duenho.getNombre() + ".");
            return;
        }
        if (!avatares.contains(solicitante.getAvatar())) {
            System.out.println("El avatar de " + solicitante.getNombre() + " no está en la casilla " + nombre + ".");
            return;
        }
        if (solicitante.getFortuna() < valor) {
            System.out.println(solicitante.getNombre() + " no tiene dinero suficiente para comprar " + nombre + ".");
            return;
        }
        solicitante.sumarGastos(valor);
        banca.sumarFortuna(valor);
        banca.eliminarPropiedad(this);
        solicitante.anhadirPropiedad(this);
        System.out.println("El jugador " + solicitante.getNombre() + " compra la casilla " + nombre + " por "
                + formato(valor) + "€. Su fortuna actual es " + formato(solicitante.getFortuna()) + "€.");
    }

    /*Método para añadir valor a una casilla. Utilidad:
     * - Sumar valor a la casilla de parking.
     * - Sumar valor a las casillas de solar al no comprarlas tras cuatro vueltas de todos los jugadores.
     * Este método toma como argumento la cantidad a añadir del valor de la casilla.*/
    public void sumarValor(float suma) {
        this.valor += suma;
    }

    /*Método para mostrar información de la casilla. Parámetros:
     * - Jugador que solicita la información (para mostrar si es dueño o no de la casilla).
     * Devuelve una cadena con información específica de cada tipo de casilla.*/
    public String infoCasilla() {
        String propietario = (duenho == null) ? "-" : duenho.getNombre();
        if (tipo.equalsIgnoreCase("Solar")) {
            return "{\n"
                    + "  tipo: solar,\n"
                    + "  grupo: " + grupo.getColorGrupo() + ",\n"
                    + "  propietario: " + propietario + ",\n"
                    + "  valor: " + formato(valor) + ",\n"
                    + "  alquiler: " + formato(impuesto) + ",\n"
                    + "  valor hotel: " + formato(precioHotel) + ",\n"
                    + "  valor casa: " + formato(precioCasa) + ",\n"
                    + "  valor piscina: " + formato(precioPiscina) + ",\n"
                    + "  valor pista de deporte: " + formato(precioPista) + ",\n"
                    + "  alquiler casa: " + formato(alquilerCasa) + ",\n"
                    + "  alquiler hotel: " + formato(alquilerHotel) + ",\n"
                    + "  alquiler piscina: " + formato(alquilerPiscina) + ",\n"
                    + "  alquiler pista de deporte: " + formato(alquilerPista) + "\n"
                    + "}";
        }
        if (tipo.equalsIgnoreCase("Transporte") || tipo.equalsIgnoreCase("Servicios")) {
            return "{\n"
                    + "  tipo: " + tipo.toLowerCase() + ",\n"
                    + "  propietario: " + propietario + ",\n"
                    + "  valor: " + formato(valor) + ",\n"
                    + "  alquiler: " + formato(impuesto) + "\n"
                    + "}";
        }
        if (tipo.equalsIgnoreCase("Impuesto")) {
            return "{\n"
                    + "  tipo: impuesto,\n"
                    + "  apagar: " + formato(impuesto) + "\n"
                    + "}";
        }
        if (nombre.equalsIgnoreCase("Parking")) {
            String jugadores = "";
            for (Avatar av : avatares) {
                if (!jugadores.isEmpty()) jugadores += ", ";
                jugadores += av.getJugador().getNombre();
            }
            return "{\n"
                    + "  bote: " + formato(valor) + ",\n"
                    + "  jugadores: [" + jugadores + "]\n"
                    + "}";
        }
        if (nombre.equalsIgnoreCase("Carcel") || nombre.equalsIgnoreCase("Cárcel")) {
            String jugadores = "";
            for (Avatar av : avatares) {
                Jugador j = av.getJugador();
                if (j.getEnCarcel()) {
                    if (!jugadores.isEmpty()) jugadores += " ";
                    jugadores += "[" + j.getNombre() + "," + j.getTiradasCarcel() + "]";
                }
            }
            return "{\n"
                    + "  salir: " + formato(Valor.PRECIO_SALIR_CARCEL) + ",\n"
                    + "  jugadores: " + (jugadores.isEmpty() ? "-" : jugadores) + "\n"
                    + "}";
        }
        if (nombre.equalsIgnoreCase("Salida")) {
            return "{\n"
                    + "  tipo: especial,\n"
                    + "  cobrar: " + formato(Valor.SUMA_VUELTA) + "\n"
                    + "}";
        }
        //Caja de Comunidad, Suerte e IrCarcel no se describen.
        return "No tiene sentido describir la casilla " + nombre + ".";
    }

    /* Método para mostrar información de una casilla en venta.
     * Valor devuelto: texto con esa información.
     */
    public String casEnVenta() {
        if (tipo.equalsIgnoreCase("Solar")) {
            return "{\n"
                    + "  tipo: solar,\n"
                    + "  grupo: " + grupo.getColorGrupo() + ",\n"
                    + "  valor: " + formato(valor) + "\n"
                    + "}";
        }
        return "{\n"
                + "  tipo: " + tipo.toLowerCase() + ",\n"
                + "  valor: " + formato(valor) + "\n"
                + "}";
    }

    //Formatea una cantidad de dinero sin decimales (por ejemplo 2600000).
    private static String formato(float cantidad) {
        return String.format("%.0f", cantidad);
    }

}
