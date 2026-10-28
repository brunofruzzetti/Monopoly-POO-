package monopoly;

import partida.*;
import java.util.ArrayList;

public class Carta {
    private int id;
    private String tipo; // Suerte o Comunidad
    private String descripcion;

    public Carta(int id, String tipo, String descripcion){
        this.id = id;
        this.tipo = tipo;
        this.descripcion = descripcion;
    }

    public int getId(){
        return id;
    }
    public String getTipo(){
        return tipo;
    }
    public String getDescripcion(){
        return descripcion;
    }

    // Ejecuta la accion de la carta sobre el jugador actual
    public boolean accionar(Jugador jugador, Jugador banca, ArrayList<Jugador> jugadores, Tablero tablero){
        System.out.println(jugador.getNombre() + ", elige una carta: " + id + ".");
        System.out.println("Accion: " + descripcion);

        if (tipo.equalsIgnoreCase("Suerte")){
            return accionarSuerte(jugador, banca, jugadores, tablero);
        } else {
            return accionarComunidad(jugador, banca, jugadores, tablero);
        }
    }

    // ACCIONES DE CARTAS DE SUERTE (DE 1 A 7)
    private boolean accionarSuerte(Jugador jugador, Jugador banca, ArrayList<Jugador> jugadores, Tablero tablero){
        switch(id){
            case 1:
                Casilla destino = tablero.encontrar_casilla("Solar19");
                moverConPasoPorSalida();
                return destino.evaluarCasilla(jugador, banca, 0);
        
            case 2:
                jugador.encarcelar(tablero.getPosiciones());
                System.out.println(jugador.getNombre() + " va directamente a la carcel.");
                return true;
            case 3:
                jugador.sumarFortuna(1000000f);
                jugador.getEstadisticas().sumarPremios(1000000f);
                System.out.println(jugador.getNombre() + " recibe 1.000.000 euros.");
                return true;
            case 4:
                float cuota = 250000f;
                int otros = jugadores. size() - 1;
                float total = cuota * otros;
                if (jugador.getFortuna() < total){
                    System.out.println(jugador.getNombre() + " no tiene suficiente dinero para pagar a todos (" + (long) total + " de euros.) Debe hipotecar una propiedad.");
                    return false;
                }
                jugador.sumarFortuna(-total);
                jugador.sumarGastos(total);
                for (Jugador j : jugadores) {
                    if (j != jugador) {
                        j.sumarFortuna(cuota);
                    }
                }
                System.out.println(jugador.getNombre() + " paga 250.000 euros a cada jugador");
                return true;
            case 5:
                int posActual = jugador.getAvatar().getLugar().getPosicion();
                int nuevaPos = posActual - 3;
                if (nuevaPos < 1) {
                    nuevaPos += 40;
                }
                Casilla destino = tablero.getCasilla(nuevaPos);
                jugador.getAvatar().getLugar().eliminarAvatar(jugador.getAvatar());
                jugador.getAvatar().setLugar(destino);
                destino.anhadirAvatar(jugador.getAvatar());
                System.out.println(jugador.getNombre() + " retrocede hasta " + destino.getNombre() + ".");
                return destino.evaluarCasilla(jugador, banca, 0, tablero, jugadores);
            case 6: 
                return cobrarOForzarHipoteca();
            case 7:
                Casilla transporte = buscarTransporteMasCercano();
                moverConPasoPorSalida();
                if (transporte.getDuenho() == banca) {
                    System.out.println(transporte.getNombre() + " no tiene dueño. Puedes comprarla si lo deseas.");
                    return true;
                } else if (transporte.getDuenho != jugador) {
                    float doble = Valor.ALQUILER_TRANSPORTE * 2.0f;
                    if (jugador.getFortuna() < doble){
                        System.out.println(jugador.getNombre() + " no tiene suficiente dinero para pagar el alquiler (" + (long) doble + " euros de alquiler).");
                        return false;
                    }
                    jugador.sumarGastos(doble);
                    transporte.getDuenho().sumarFortuna(doble);
                    transporte.getDuenho().getEstadisticas().sumarPagoAlquileres(doble);ç
                    System.out.println(jugador.getNombre() + " paga " + (long) doble + "euros de alquiler a " + transporte.getDuenho().getNombre() + ".");
                    return true
                }
    }
}
