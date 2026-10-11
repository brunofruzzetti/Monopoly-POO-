package partida;

import java.util.ArrayList;

import monopoly.*;


public class Jugador {

    //Atributos:
    private String nombre; //Nombre del jugador
    private Avatar avatar; //Avatar que tiene en la partida.
    private float fortuna; //Dinero que posee.
    private float gastos; //Gastos realizados a lo largo del juego.
    private boolean enCarcel; //Será true si el jugador está en la carcel
    private int tiradasCarcel; //Cuando está en la carcel, contará las tiradas sin éxito que ha hecho allí para intentar salir (se usa para limitar el numero de intentos).
    private int vueltas; //Cuenta las vueltas dadas al tablero.
    private ArrayList<Casilla> propiedades; //Propiedades que posee el jugador.

    //getters
    public String getNombre() { return nombre; }
    public Avatar getAvatar() { return avatar; }
    public float getFortuna() { return fortuna; }
    public float getGastos() { return gastos; }
    public boolean getEnCarcel() { return enCarcel; }
    public int getTiradasCarcel() { return tiradasCarcel; }
    public int getVueltas() { return vueltas;}
    public ArrayList<Casilla> getPropiedades() { return propiedades; }

    //setters
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public void setAvatar(Avatar avatar){
        this.avatar = avatar;
    }
    public void setFortuna(float fortuna){
        this.fortuna = fortuna;
    }
    public void setGastos(float gastos){
        this.gastos = gastos;
    }
    public void setEnCarcel(boolean enCarcel){
        this.enCarcel = enCarcel;
    }
    public void setTiradasCarcel(int tiradasCarcel){
        this.tiradasCarcel = tiradasCarcel;
    }
    public void setVueltas(int vueltas){
        this.vueltas = vueltas;
    }
    public void setPropiedades(ArrayList<Casilla> propiedades){
        this.propiedades = propiedades;
    }



    //Constructor vacío. Se usará para crear la banca.
    public Jugador() {
        nombre = "Banca";
        fortuna = Valor.FORTUNA_BANCA;
        propiedades = new ArrayList<>();
        avatar = null;
    }

    /*Constructor principal. Requiere parámetros:
    * Nombre del jugador, tipo del avatar que tendrá, casilla en la que empezará y ArrayList de
    * avatares creados (usado para dos propósitos: evitar que dos jugadores tengan el mismo nombre y
    * que dos avatares tengan mismo ID). Desde este constructor también se crea el avatar.
     */
    public Jugador(String nombre, String tipoAvatar, Casilla inicio, ArrayList<Avatar> avCreados) {
        this.nombre = nombre;
        fortuna = Valor.FORTUNA_INICIAL;
        propiedades = new ArrayList<>();
        avatar = new Avatar(tipoAvatar, this, inicio, avCreados);
        if (avCreados != null){
            avCreados.add(this.avatar);
        }
    }

    //Otros métodos:
    //Método para añadir una propiedad al jugador. Como parámetro, la casilla a añadir.
    public void anhadirPropiedad(Casilla casilla) {
        if (casilla != null && !this.propiedades.contains(casilla)) {
            this.propiedades.add(casilla);
            casilla.setDuenho(this);
        }
    }

    //Método para eliminar una propiedad del arraylist de propiedades de jugador.
    public void eliminarPropiedad(Casilla casilla) {
        if (casilla != null) {
            this.propiedades.remove(casilla);
        }
    }

    //Método para añadir fortuna a un jugador
    //Como parámetro se pide el valor a añadir. Si hay que restar fortuna, se pasaría un valor negativo.
    public void sumarFortuna(float valor) {
        this.fortuna += valor;
    }

    //Método para sumar gastos a un jugador.
    //Parámetro: valor a añadir a los gastos del jugador (será el precio de un solar, impuestos pagados...).
    public void sumarGastos(float valor) {
        this.fortuna -= valor;
        this.gastos += valor;
    }

    /*Método para establecer al jugador en la cárcel. 
    * Se requiere disponer de las casillas del tablero para ello (por eso se pasan como parámetro).*/
    public void encarcelar(ArrayList<ArrayList<Casilla>> pos) {
        this.enCarcel = false;
        this.tiradasCarcel = 0;
        // La Cárcel es la primera casilla del lado oeste (índice 1, posición 0)
        // La Cárcel es la posición 11 (índice 0 del lado Oeste: pos.get(1).get(0))
        Casilla carcel = pos.get(1).get(0);

        if (this.avatar != null) {
            if (this.avatar.getLugar() != null) {
                this.avatar.getLugar().eliminarAvatar(this.avatar);
            }
            this.avatar.setLugar(carcel);
            carcel.anhadirAvatar(this.avatar);
        }
    }
    public boolean puedePagar(float cantidad) {
    return this.fortuna >= cantidad;
}
    @Override
    public String toString() {
        String idAvatar;
        if (this.avatar != null) {
            idAvatar = this.avatar.getId();
        } else {
            idAvatar = "null";
        }

        String salida = "{\n";
        salida =  salida + "  nombre: " + this.nombre + ",\n";
        salida =  salida + "  avatar: " + idAvatar + "\n";
        salida =  salida + "}";

        return salida;
    }
}
