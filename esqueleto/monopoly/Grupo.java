package monopoly;

import partida.*;
import java.util.ArrayList;


public class Grupo {

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
        if (jugador == null || miembros == null || miembros.isEmpty()) {
        return false;
    }
        for (Casilla c : miembros) {
            if (c.getDuenho() != jugador) {
                return false;
            }
        }
        return true;
    }

    /*Devuelve los edificios de cada casilla del grupo y, al final, qué se puede construir todavía
     * según los límites del grupo (N edificios de cada tipo, siendo N el número de solares; 4 casas
     * por solar, o N casas en el grupo cuando ya hay N hoteles).
     */
    public String infoEdificios() {
        String salida = "";
        for (int i = 0; i < miembros.size(); i++) {
            salida += miembros.get(i).infoEdificios() + (i < miembros.size() - 1 ? ",\n" : "\n");
        }

        int hoteles = contarEdificios("hotel");
        int restanCasas = 0;
        if (hoteles >= numCasillas) {
            restanCasas = Math.max(0, numCasillas - contarEdificios("casa"));
        } else {
            for (Casilla c : miembros) {
                restanCasas += Math.max(0, 4 - c.contarEdificios("casa"));
            }
        }
        int[] restan = {
                restanCasas,
                Math.max(0, numCasillas - hoteles),
                Math.max(0, numCasillas - contarEdificios("piscina")),
                Math.max(0, numCasillas - contarEdificios("pista_deporte"))
        };
        String[] singular = {"casa", "hotel", "piscina", "pista de deporte"};
        String[] plural = {"casas", "hoteles", "piscinas", "pistas de deporte"};

        ArrayList<String> si = new ArrayList<>();
        ArrayList<String> no = new ArrayList<>();
        for (int i = 0; i < restan.length; i++) {
            if (restan[i] > 0) {
                si.add(restan[i] + " " + (restan[i] == 1 ? singular[i] : plural[i]));
            } else {
                no.add(plural[i]);
            }
        }
        if (!si.isEmpty()) {
            salida += "Aún se puede" + (si.size() == 1 && si.get(0).startsWith("1 ") ? "" : "n")
                    + " edificar " + enumerar(si, " y ") + ".";
        }
        if (!no.isEmpty()) {
            if (!si.isEmpty()) salida += " ";
            salida += "Ya no se pueden construir ni " + enumerar(no, " ni ") + ".";
        }
        return salida;
    }

    //Une una lista de elementos con comas y el conector final (por ejemplo "a, b y c").
    private static String enumerar(ArrayList<String> elementos, String conector) {
        String texto = "";
        for (int i = 0; i < elementos.size(); i++) {
            if (i > 0) texto += (i == elementos.size() - 1) ? conector : ", ";
            texto += elementos.get(i);
        }
        return texto;
    }

    //Devuelve el número de edificios de un tipo construidos en todas las casillas del grupo.
    public int contarEdificios(String tipoEdificio) {
        int n = 0;
        for (Casilla c : miembros) {
            n += c.contarEdificios(tipoEdificio);
        }
        return n;
    }

}
