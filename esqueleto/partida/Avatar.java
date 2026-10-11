package partida;

import monopoly.*;

import java.util.ArrayList;
import java.util.Random;

public class Avatar {

    //Atributos
    private String id; //Identificador: una letra generada aleatoriamente.
    private String tipo; //Sombrero, Esfinge, Pelota, Coche
    private Jugador jugador; //Un jugador al que pertenece ese avatar.
    private Casilla lugar; //Los avatares se sitúan en casillas del tablero.

    //getters
    public String getId() {return id; }
    public String getTipo() { return tipo;}
    public Jugador getJugador() { return jugador; }
    public Casilla getLugar() { return lugar; }

    //setters
    public void setId(String id){
        this.id = id;
    }
    public void setTipo(String tipo){
        this.tipo = tipo;
    }
    public void setJugador(Jugador jugador){
        this.jugador = jugador;
    }
    public void setLugar(Casilla lugar){
        this.lugar = lugar;
    }

    //Constructor vacío
    public Avatar() {
        
    }

    /*Constructor principal. Requiere éstos parámetros:
    * Tipo del avatar, jugador al que pertenece, lugar en el que estará ubicado, y un arraylist con los
    * avatares creados (usado para crear un ID distinto del de los demás avatares).
     */
    public Avatar(String tipo, Jugador jugador, Casilla lugar, ArrayList<Avatar> avCreados) {
        this.tipo = tipo;
        this.jugador = jugador;
        this.lugar = lugar;
        this.generarId(avCreados);
        if (lugar != null){
            lugar.anhadirAvatar(this);
        }    
    }

    //A continuación, tenemos otros métodos útiles para el desarrollo del juego.
    /*Método que permite mover a un avatar a una casilla concreta. Parámetros:
    * - Un array con las casillas del tablero. Se trata de un arrayList de arrayList de casillas (uno por lado).
    * - Un entero que indica el numero de casillas a moverse (será el valor sacado en la tirada de los dados).
    * EN ESTA VERSIÓN SUPONEMOS QUE valorTirada siemrpe es positivo.
     */
    public void moverAvatar(ArrayList<ArrayList<Casilla>> casillas, int valorTirada) {
        if (this.lugar == null || casillas == null) {
            return;
        }

        // Posición actual de 0 a 39
        int posActual = this.lugar.getPosicion() - 1;
        int suma = posActual + valorTirada;
        int nuevaPos = suma % 40;

        // Comprobar si pasa o cae en la casilla de Salida
        if (suma >= 40) {
            if (this.jugador != null) {
                this.jugador.setVueltas(this.jugador.getVueltas() + 1);
                this.jugador.sumarFortuna(Valor.SUMA_VUELTA);
                System.out.println(this.jugador.getNombre() + " pasa por la Salida y cobra " + (long) Valor.SUMA_VUELTA + "€.");
            }
        }

        // Localizar la casilla de destino en la matriz de lados
        int lado = nuevaPos / 10;
        int indice = nuevaPos % 10;
        Casilla destino = casillas.get(lado).get(indice);

        // Cambiar de casilla
        this.lugar.eliminarAvatar(this);
        this.lugar = destino;
        this.lugar.anhadirAvatar(this);

        System.out.println("El avatar " + this.id + " avanza " + valorTirada + " posiciones hasta " + destino.getNombre() + ".");
    }

    /*Método que permite generar un ID para un avatar. Sólo lo usamos en esta clase (por ello es privado).
    * El ID generado será una letra mayúscula. Parámetros:
    * - Un arraylist de los avatares ya creados, con el objetivo de evitar que se generen dos ID iguales.
     */
    private void generarId(ArrayList<Avatar> avCreados) {
        Random rand = new Random();
        boolean repetido;
        String IdGenerado;
        
        do {
            repetido = false;
            char letra = (char) ('A' + rand.nextInt(26));
            IdGenerado = String.valueOf(letra);
            if (avCreados != null){
                for (Avatar av : avCreados){
                    if (av != null && IdGenerado.equals(av.getId())){
                        repetido = true; 
                        break;
                    }
                }            
            }    
        } while(repetido); 
        this.id = IdGenerado;
    }
}
