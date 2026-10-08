package monopoly;

import partida.*;
import java.util.ArrayList;


class Grupo {

    //Atributos
    private ArrayList<Casilla> miembros; //Casillas miembros del grupo.
    private String colorGrupo; //Color del grupo
    private int numCasillas; //Número de casillas del grupo.

    public ArrayList<Casilla> getMiembros() {
        return miembros;
    }

    public String getColorGrupo() {
        return colorGrupo;
    }

    public int getNumCasillas() {
        return numCasillas;
    }

    public void setMiembros(ArrayList<Casilla> miembros) {
        this.miembros = miembros;
    }

    public void setColorGrupo(String colorGrupo) {
        this.colorGrupo = colorGrupo;
    }

    public void setNumCasillas(int numCasillas) {
        this.numCasillas = numCasillas;
    }



    //Constructor vacío.
    public Grupo() {
    }

    /*Constructor para cuando el grupo está formado por DOS CASILLAS:
    * Requiere como parámetros las dos casillas miembro y el color del grupo.
     */
    public Grupo(Casilla cas1, Casilla cas2, String colorGrupo) {
        this.miembros = new ArrayList<>();
        this.colorGrupo = colorGrupo;
        this.anhadirCasilla(cas1);
        this.anhadirCasilla(cas2);
    }

    /*Constructor para cuando el grupo está formado por TRES CASILLAS:
    * Requiere como parámetros las tres casillas miembro y el color del grupo.
     */
    public Grupo(Casilla cas1, Casilla cas2, Casilla cas3, String colorGrupo) {
        this.miembros = new ArrayList<>();
        this.colorGrupo = colorGrupo;
        this.anhadirCasilla(cas1);
        this.anhadirCasilla(cas2);
        this.anhadirCasilla(cas3);
    }

    /* Método que añade una casilla al array de casillas miembro de un grupo.
    * Parámetro: casilla que se quiere añadir.
     */
    public void anhadirCasilla(Casilla miembro) {
        if (miembros == null) {
            miembros = new ArrayList<>();
        }
        if (miembro != null && !miembros.contains(miembro)) {
            miembros.add(miembro);
            miembro.setGrupo(this);
            numCasillas = miembros.size();
        }
    }

    /*Método que comprueba si el jugador pasado tiene en su haber todas las casillas del grupo:
    * Parámetro: jugador que se quiere evaluar.
    * Valor devuelto: true si es dueño de todas las casillas del grupo, false en otro caso.
     */
    //Devuelve el código ANSI con el que se pinta el grupo en el tablero.
    public String getCodigoColor() {
        switch (colorGrupo) {
            case "Marron": return Valor.MARRON;
            case "Celeste": return Valor.CELESTE;
            case "Rosa": return Valor.ROSA;
            case "Naranja": return Valor.NARANJA;
            case "Rojo": return Valor.RED;
            case "Amarillo": return Valor.YELLOW;
            case "Verde": return Valor.GREEN;
            case "Morado": return Valor.MORADO;
            default: return "";
        }
    }

    public boolean esDuenhoGrupo(Jugador jugador) {
        for (Casilla c : miembros) {
            if (c.getDuenho() != jugador) {
                return false;
            }
        }
        return true;
    }

}
