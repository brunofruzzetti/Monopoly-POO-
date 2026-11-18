package monopoly;

import java.util.ArrayList;

import partida.*;

public class Solar extends Propiedad{

    //Precios y alquileres de los edificios de un solar (Apéndice I).
    private float precioCasa;
    private float precioHotel;
    private float precioPiscina;
    private float precioPista;
    private float alquilerCasa;
    private float alquilerHotel;
    private float alquilerPiscina;
    private float alquilerPista;

    private ArrayList<Edificio> edificios; //Edificios construidos en la casilla (si es solar).

    public Solar(String nombre, int posicion, float valor, Jugador duenho) {
        super(nombre, posicion, valor, duenho);
        this.edificios = new ArrayList<>();
    }

    public void setDatosSolar(float hipoteca, float alquiler,
                              float precioCasa, float precioHotel, float precioPiscina, float precioPista,
                              float alquilerCasa, float alquilerHotel, float alquilerPiscina, float alquilerPista) {
        setHipoteca(hipoteca);
        setImpuesto(alquiler);
        this.precioCasa = precioCasa;
        this.precioHotel = precioHotel;
        this.precioPiscina = precioPiscina;
        this.precioPista = precioPista;
        this.alquilerCasa = alquilerCasa;
        this.alquilerHotel = alquilerHotel;
        this.alquilerPiscina = alquilerPiscina;
        this.alquilerPista = alquilerPista;
    }

    @Override
    public float valor() {
        if (this.edificios.isEmpty()) {
            if (getGrupo() != null && getGrupo().esDuenhoGrupo(getDuenho())) {
                return getImpuesto() * 2;
            }
            return getImpuesto();
        }
        return contarEdificios("casa") * this.alquilerCasa
                + contarEdificios("hotel") * this.alquilerHotel
                + contarEdificios("piscina") * this.alquilerPiscina
                + contarEdidicios("pista_deporte") * this.alquilerPista;
    }
    @Override
    public boolean alquiler() {
        return cobrarAlquilerA(getJugadorActual(), valor());
    }
    

}
