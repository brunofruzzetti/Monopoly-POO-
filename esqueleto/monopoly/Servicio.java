package monopoly;

import partida.*;

public class Servicio extends Propiedad{

    public Servicio(String nombre, int posicion, float valor, Jugador duenho) {
        super(nombre, posicion, valor, duenho);
    }
    
    @Override
    public float valor() {
        return 4 * getUltimaTirada() * Valor.FACTOR_SERVICIO;
    }

    @Override
    public boolean alquiler() {
        return cobrarAlquilerA(getJugadorActual(), valor());
    }
    
}
