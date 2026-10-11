package monopoly;


import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Scanner;


import partida.*;

public class Menu {

    //Atributos
    private ArrayList<Jugador> jugadores; //Jugadores de la partida.
    private ArrayList<Avatar> avatares; //Avatares en la partida.
    private int turno = 0; //Índice correspondiente a la posición en el arrayList del jugador (y el avatar) que tienen el turno
    private int lanzamientos; //Variable para contar el número de lanzamientos de un jugador en un turno.
    private Tablero tablero; //Tablero en el que se juega.
    private Dado dado1; //Dos dados para lanzar y avanzar casillas.
    private Dado dado2;
    private Jugador banca; //El jugador banca.
    private boolean tirado; //Booleano para comprobar si el jugador que tiene el turno ha tirado o no.
    private boolean solvente; //Booleano para comprobar si el jugador que tiene el turno es solvente, es decir, si ha pagado sus deudas.
    private boolean terminar = false; //decide cuando acabar un bucle interno

    //geters
    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }
    public ArrayList<Avatar> getAvatares() {
        return avatares;
    }
    public int getTurno() {
        return turno;
    }
    public int getLanzamientos() {
        return lanzamientos;
    }
    public Tablero getTablero() {
        return tablero;
    }
    public Dado getDado1() {
        return dado1;
    }
    public Dado getDado2() {
        return dado2;
    }
    public Jugador getBanca() {
        return banca;
    }
    public boolean isTirado() {
        return tirado;
    }   
    public boolean isSolvente() {
        return solvente;
    }

    //seters
    public void setJugadores(ArrayList<Jugador> jugadores) {
        this.jugadores = jugadores;
    }
    public void setAvatares(ArrayList<Avatar> avatares) {
        this.avatares = avatares;
    }
    public void setTurno(int turno) {
        this.turno = turno;
    }
    public void setLanzamientos(int lanzamientos) {
        this.lanzamientos = lanzamientos;
    }
    public void setTablero(Tablero tablero) {
        this.tablero = tablero;
    }
    public void setDado1(Dado dado1) {
        this.dado1 = dado1;
    }
    public void setDado2(Dado dado2) {
        this.dado2 = dado2;
    }
    public void setBanca(Jugador banca) {
        this.banca = banca;
    }
    public void setTirado(boolean tirado) {
        this.tirado = tirado;
    }
    public void setSolvente(boolean solvente) {
        this.solvente = solvente;
    }

    //Constructor vacío.
    public Menu() {
        iniciarPartida();
    }

    // Método para inciar una partida: crea los jugadores y avatares.
    private void iniciarPartida() {
        this.jugadores = new ArrayList<>();
        this.avatares = new ArrayList<>();
        this.turno = 0;
        this.lanzamientos = 0;
        this.dado1 = new Dado();
        this.dado2 = new Dado();
        this.banca = new Jugador();
        this.tablero = new Tablero(banca);
        this.tirado = false;
        this.solvente = true;

        System.out.println(tablero);   //imprimir tablero vacio

        Scanner sc = new Scanner(System.in); //Objeto que lee la entrada de la termina, System.in equivale a stdin en C
        System.out.print("$> "); 
        while (!terminar && sc.hasNextLine()) { 
            String linea = sc.nextLine().trim(); //el metodo .trim() elimina el espacio inicial de un String
            if (!linea.isEmpty()){
                analizarComando(linea); //El metodo comandos analizara la entrada del usuario y decidira que ejecutar
            } 
            if (!terminar){
                System.out.print("$> ");
            }
        }
        sc.close();
    }
    /*Método que interpreta el comando introducido y toma la accion correspondiente.
    * Parámetro: cadena de caracteres (el comando).
    */
        private void analizarComando(String comando){
        String[] partes = comando.trim().split("\\s+");
        switch (normalizar(partes[0])){
            case "comandos":
                break;
            /*
            $> comandos 
            lanza un archivo con comandos
            */
            case "crear":{
                //comprobaciones 
                if(partes.length != 4 || !partes[1].equalsIgnoreCase("jugador")){
                    System.out.println("Argumentos incorrectos, uso: $> crear jugador nombreJugador tipoAvatar");
                    break;
                } //comprobacion argumentos
                if(jugadores.size() >= 4){
                    System.out.println("Ya hay 4 jugadores registrados");
                    break;
                } //comprobacion sobre cuantos jugadores hay registrados
                if(!partes[3].equalsIgnoreCase("coche") && !partes[3].equalsIgnoreCase("esfinge") //comprobacion del tipo de avatar
                        && !partes[3].equalsIgnoreCase("sombrero") && !partes[3].equalsIgnoreCase("pelota")){
                    System.out.println("El avatar introducido no es valido. Avatares validos: coche, esfinge, sombrero, pelota");
                    break;
                }
                boolean nombreRepetido = false, avatarRepetido = false;
                for(Jugador a : jugadores){
                    if(a.getNombre().equalsIgnoreCase(partes[2])) nombreRepetido = true;
                    if(a.getAvatar().getTipo().equalsIgnoreCase(partes[3])) avatarRepetido = true;
                }
                if(nombreRepetido){ //comprobacion del nombre en uso
                    System.out.println("Nombre ya en uso.");
                    break;
                }
                if(avatarRepetido){ //comprobacion del avatar en uso
                    System.out.println("Avatar ya en uso.");
                    break;
                }

                //crear jugador y añadirlo
                Jugador j = new Jugador(partes[2], partes[3], tablero.encontrar_casilla("salida"), avatares);
                jugadores.add(j);
                System.out.println(j);
                System.out.println(tablero);
                break;
            }
            /*
            $> crear jugador Maria pelota
            crea jugadores
            */
            case "jugador":
                if(jugadores.size() == 0){
                    System.out.println("No hay jugadores registrados");
                }
                System.out.println(jugadores.get(turno));
                break;
            /*
            $> jugador
            indica el jugador que tiene el turno
            */
            case "listar":
                switch (normalizar(partes[1])){
                    case "jugadores":
                        if(jugadores.size() == 0){
                            System.out.println("No hay jugadores registrados");
                            break;
                        }
                        for (Jugador a : jugadores){
                            System.out.println(a);
                        }
                        break;
                    case "enventa":
                        for(Casilla c : banca.getPropiedades()){
                            System.out.println(c.infoCasilla());
                        }
                        break;
                }
                break;
            /*
            $> listar jugadores
            lista los jugadores que juegan
            $> listar enventa
            lista las propiedades que quedan en venta (se mostra el tablero en pantalla)
            */
            case "lanzar":

                break;
            /*
            $> lanzar dados
            lanza los dos dados de forma aleatorio moviendo al jugador esas posiciones (se repinta el tablero)
            $> lanzar dados 2+4
            los dos dados tienen un valor frozoso moviendo al jugador esas posiciones (se repinta el tablero)
            */
            case "acabar":

                break;
            /*
            $> acabar turno
            el jugador actual decide que su turno ha acabado y el turno pasa al siguiente
            */
            case "salir":

                break;
            /*
            $> salir cárcel
            el jugador sale de la carcel pagando si esta en ella
            */
            case "describir":

                break;
            /*
            $> describir Solar8
            se indicaran todas las caracteristicas de una casilla
            $> describir Maria
            se indacaran todas las caracteristicas de un jugador
            */
            case "comprar":

                break;
            /*
            $> comprar Mostoles/Solar12
            el jugador compra la casilla por su nombre o numero de solar
            */
            case "ver":

                break;
            /*
            $> ver tablero
            se muestra el tablero en pantalla
            */
            case "exit":
                terminar = true;
                break;
            /*
            $> exit
            acabar la partida
            */
            default:
                System.out.println("Caso incorrecto");
                break;
        }
    }

    /*Método que realiza las acciones asociadas al comando 'describir jugador'.
    * Parámetro: comando introducido
     */
    private void descJugador(String[] partes) {

    }

    /*Método que realiza las acciones asociadas al comando 'describir avatar'.
    * Parámetro: id del avatar a describir.
    */
    private void descAvatar(String ID) {

    }

    /* Método que realiza las acciones asociadas al comando 'describir nombre_casilla'.
    * Parámetros: nombre de la casilla a describir.
    */
    private void descCasilla(String nombre) {

    }

    //Método que ejecuta todas las acciones relacionadas con el comando 'lanzar dados'.
    private void lanzarDados() {

    }

    /*Método que ejecuta todas las acciones realizadas con el comando 'comprar nombre_casilla'.
    * Parámetro: cadena de caracteres con el nombre de la casilla.
     */
    private void comprar(String nombre) {

    }

    //Método que ejecuta todas las acciones relacionadas con el comando 'salir carcel'. 
    private void salirCarcel() {

    }

    // Método que realiza las acciones asociadas al comando 'listar enventa'.
    private void listarVenta() {

    }

    // Método que realiza las acciones asociadas al comando 'listar jugadores'.
    private void listarJugadores() {

    }

    // Método que realiza las acciones asociadas al comando 'listar avatares'.
    private void listarAvatares() {

    }

    // Método que realiza las acciones asociadas al comando 'acabar turno'.
    private void acabarTurno() {

    }

    private static String normalizar(String texto) {
        return Normalizer.normalize(texto, Normalizer.Form.NFD).replaceAll("\\p{M}", "").toLowerCase();
    }

}
