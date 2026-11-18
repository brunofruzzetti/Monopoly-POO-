package monopoly;

import partida.*;

public class Impuesto extends Casilla {

    private float impuesto;
    private Parking parking;

    public Impuesto(String nombre, int posicion, float impuesto, Jugador duenho) {
        super(nombre, posicion, duenho);
        this.impuesto = impuesto;
    }

    public float getImpuesto() { return impuesto; }
    public void setImpuesto(float impuesto) { this.impuesto = impuesto; }

    public Parking getParking() { return parking; }
    public void setParking(Parking parking) { this.parking = parking; }
    
}
