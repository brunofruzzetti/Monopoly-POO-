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
                System.out.println(jugador.getNombre() + " recibe 1.000.000 de euros.");
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
                } else if (transporte.getDuenho() != jugador) {
                    float doble = Valor.ALQUILER_TRANSPORTE * 2.0f;
                    if (jugador.getFortuna() < doble){
                        System.out.println(jugador.getNombre() + " no tiene suficiente dinero para pagar el alquiler (" + (long) doble + " euros de alquiler).");
                        return false;
                    }
                    jugador.sumarGastos(doble);
                    transporte.getDuenho().sumarFortuna(doble);
                    transporte.getDuenho().getEstadisticas().sumarPagoAlquileres(doble);
                    System.out.println(jugador.getNombre() + " paga " + (long) doble + "euros de alquiler a " + transporte.getDuenho().getNombre() + ".");
                    return true;
                }
            default:
                return true;
    }
}

    // CARTAS DE CAJA DE COMUNIDAD (1 A 6)
    private boolean accionarComunidad(Jugador jugador, Jugador banca, Tablero tablero, ArrayList<Jugador> jugadores) {
        switch (id){
            case 1:
                return cobrarOForzarHipoteca();
            case 2:
                jugador.encarcelar(tablero.getPosiciones());
                System.out.println(jugador.getNombre() + " va directamente a la carcel.");
                return true;
            case 3:
                Casilla salida = tablero.encontrar_casilla("Salida");
                jugador.getAvatar().getLugar().eliminarAvatar(jugador.getAvatar());
                jugador.getAvatar().setLugar(salida);
                jugador.sumarFortuna(2000000f);
            case 4:
                jugador.sumarFortuna(500000f);
                jugador.getEstadisticas().sumarPremios(500000f);
                System.out.println(jugador.getNombre() + " recibe 500.000€ de Hacienda.");
                return true;
            case 5:
                Casilla retrocedo = tablero.encontrar_casilla("Solar1");
                jugador.getAvatar().getLugar().eliminarAvatar(jugador.getAvatar());
                jugador.getAvatar().setLugar(retrocedo);
                retrocedo.anhadirAvatar(jugador.getAvatar());
                System.out.println(jugador.getNombre() + " retrocede hasta Solar1.");
                return retrocedo.evaluarCasilla(jugador, banca, 0, tablero, jugadores);
            case 6:
                cobrarOForzarHipoteca();
        }
    }

    private void moverConPasoPorSalida(Jugador jugador, Casilla destino, Tablero tablero) {
        int posOrigen = jugador.getAvatar().getLugar().getPosicion();
        int posDestino = destino.getPosicion();

        if (posDestino < posOrigen) {
            jugador.sumarFortuna(Valor.SUMA_VUELTA);
            jugador.setVueltas(jugador.getVueltas() + 1);
            jugador.getEstadisticas().sumarPasarSalida(Valor.SUMA_VUELTA);
            System.out.println(jugador.getNombre() + " pasa por la casilla de Salida y cobra " + (long) Valor.SUMA_VUELTA + " euros.");
        }
        jugador.getAvatar().getLugar().eliminarAvatar(jugador.getAvatar());
        jugador.getAvatar().setLugar(destino);
        destino.anhadirAvatar(jugador.getAvatar());
        System.out.println(jugador.getNombre() + " se desplaza hasta " + destino.getNombre() + ".");
    }

    private Casilla buscarTransporteMasCercano(Casilla actual, Tablero tablero) {
        int pos = actual.getPosicion();
        int[] posTransportes = {6,16,26,36};
        int menorDistancia = 50;
        int targetPos = posTransportes[0];

        for (int p : posTransportes) {
            int dist;

            // ¿El transporte está delante de nosotros?
            if (p >= pos) {
                dist = p - pos;
            } else {
                // Si está detrás en número, hay que completar la vuelta
                dist = (40 - pos) + p;
            }
            // Si encontramos un transporte más cercano, nos lo guardamos
            if (dist > 0 && dist < menorDistancia) {
                menorDistancia = dist;
                targetPos = p;
            }
        }
        return tablero.getCasilla(targetPos)
    }

    private boolean cobrarOForzarHipoteca(Jugador jugador, Jugador banca, float cantidad, String motivo) {
        if (jugador.getFortuna() < cantidad) {
            System.out.println(jugador.getNombre() + " no tiene suficiente dinero para pagar " + (long) cantidad + )
        }
    }
}
