package monopoly;

import partida.*;

public class Transporte extends Propiedad {
    
    public Transporte(String nombre, int posicion, float valor, Jugador duenho) {
        super(nombre, posicion, valor, duenho);
        setImpuesto(Valor.ALQUILER_TRANSPORTE);
    }
    @Override
    public float valor() {
        return getImpuesto();
    }

    @Override
    public boolean alquiler() {
        return cobrarAlquilerA(getJugadorActual(), valor());
    }
}
