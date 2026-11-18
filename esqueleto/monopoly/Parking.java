package monopoly;

import partida.*;

public class Parking extends Accion {

    private float valor;

    public Parking(String nombre, int posicion, Jugador duenho) {
        super(nombre, posicion, duenho);
        this.valor = 0;
    }

    public void sumarValor(float suma) {
        this.valor += suma;
    }

    public float getValor() { return valor; }
    public void setValor(float valor) { this.valor = valor; }
}