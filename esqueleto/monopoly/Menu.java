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
        switch(normalizar(partes[0])){
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
                    break;
                }
                System.out.println(jugadores.get(turno));
                break;
            /*
            $> jugador
            indica el jugador que tiene el turno
            */
            case "listar":
                switch(normalizar(partes[1])){
                    case "jugadores": listarJugadores(); break;
                    case "enventa": listarVenta(); break;
                }
                
            /*
            $> listar jugadores
            lista los jugadores que juegan
            $> listar enventa
            lista las propiedades que quedan en venta (se mostra el tablero en pantalla)
            */
            case "lanzar":
                if(partes.length < 2 || partes.length > 3 || !partes[1].equalsIgnoreCase("dados")){
                    System.out.println("Error en el formato. Formato: lanzar dados [2+4]");
                    break;
                }
                if(partes.length == 2){
                    lanzarDados();
                    break;
                }
                String[] v = partes[2].split("\\+");
                if(v.length != 2){
                    System.out.println("Error en el formato. Formato: lanzar dados 2+4");
                    break;
                }
                try{
                    int d1 = Integer.parseInt(v[0]);
                    int d2 = Integer.parseInt(v[1]);
                    if(d1 < 1 || d1 > 6 || d2 < 1 || d2 > 6){
                        System.out.println("El valor de cada dado debe estar entre 1 y 6.");
                        break;
                    }
                    lanzarDados(d1, d2);
                }catch(NumberFormatException e){
                    System.out.println("Error en el formato. Los dados deben ser números: lanzar dados 2+4");
                }
                break;
            
            /*
            $> lanzar dados
            lanza los dos dados de forma aleatorio moviendo al jugador esas posiciones (se repinta el tablero)
            $> lanzar dados 2+4
            los dos dados tienen un valor frozoso moviendo al jugador esas posiciones (se repinta el tablero)
            */
            case "acabar":
                if (partes.length != 2 || !partes[1].equalsIgnoreCase("turno")) {
                    System.out.println("Argumentos incorrectos, uso: $> acabar turno");
                    break;
                }
                acabarTurno();
                break;
            /*
            $> acabar turno
            el jugador actual decide que su turno ha acabado y el turno pasa al siguiente
            */
            case "salir":
                if(!normalizar(partes[1]).equalsIgnoreCase("carcel") || partes.length != 2){ 
                    System.out.println("Argumentos incorrectos, uso: $> salir carcel");
                    break;
                }
                salirCarcel();
                break;
            /*
            $> salir cárcel
            el jugador sale de la carcel pagando si esta en ella
            */
            case "describir":
                if(partes.length < 2){
                    System.out.println("Argumentos incorrectos, uso: $> describir nombreCasilla | jugador nombre | avatar ID");
                    break;
                }
                if(normalizar(partes[1]).equals("jugador")){
                    if(partes.length != 3){
                        System.out.println("Argumentos incorrectos, uso: $> describir jugador nombreJugador");
                        break;
                    }
                    descJugador(partes);
                }
                else if(normalizar(partes[1]).equals("avatar")){
                    if(partes.length != 3){
                        System.out.println("Argumentos incorrectos, uso: $> describir avatar ID");
                        break;
                    }
                    descAvatar(partes[2]);
                }
                else{
                    if(partes.length != 2){
                        System.out.println("Argumentos incorrectos, uso: $> describir nombreCasilla");
                        break;
                    }
                    descCasilla(partes[1]);
                }
                break;
            /*
            $> describir Solar8
            se indicaran todas las caracteristicas de una casilla
            $> describir jugador Maria
            se indacaran todas las caracteristicas de un jugador
            */
            case "comprar":
                if(partes.length != 2){
                    System.out.println("Argumentos incorrectos, uso: $> comprar nombreCasilla");
                    break;
                }
                comprar(partes[1]);
                break;
            /*
            $> comprar Solar12
            el jugador compra la casilla por su nombre o numero de solar
            */
            case "hipotecar":
                if(partes.length != 2){
                    System.out.println("Argumentos incorrectos, uso: $> hipotecar nombreCasilla");
                    break;
                }
                hipotecar(partes[1]);
                break;
            /* 
            $> hipotecar Solar12
            el jugador hipoteca la casilla por su nombre
            */
            case "deshipotecar":
                if(partes.length != 2){
                    System.out.println("Argumentos incorrectos, uso: $> deshipotecar nombreCasilla");
                    break;
                }
                deshipotecar(partes[1]);
                break;
            case "ver":
                System.out.println(tablero);
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
        if (partes.length < 2) {
            System.out.println("Argumentos incorrectos. Debes indicar el nombre del jugador.");
            return;
        }
        String nombre = partes[2];
        for (Jugador a : jugadores) {
            if (a.getNombre().equalsIgnoreCase(nombre)) {
                System.out.println(a.infoJugador());
            }   
            else{
                System.out.println("Jugador no registrado");
                return;
            }
        }
    }



    /*Método que realiza las acciones asociadas al comando 'describir avatar'.
    * Parámetro: id del avatar a describir.
    */
    private void descAvatar(String ID) {
        for(Avatar a : avatares){
            if(a.getId().equalsIgnoreCase(ID)){
                System.out.println("{\n\ttipo: " + a.getTipo()
                        + ",\n\tjugador: " + a.getJugador().getNombre()
                        + ",\n\tcasilla: " + a.getLugar().getNombre()
                        + "\n}");
                return;
            }
        }
        System.out.println("No existe ningún avatar con el ID " + ID + ".");
    }

    /* Método que realiza las acciones asociadas al comando 'describir nombre_casilla'.
    * Parámetros: nombre de la casilla a describir.
    */
    private void descCasilla(String nombre) {
        Casilla encontrar = tablero.encontrar_casilla(nombre);
        if(encontrar == null){
            System.out.println("Casilla no encontrada");
            return;
        }
        System.out.println(encontrar.infoCasilla());
    }

    //Método que ejecuta todas las acciones relacionadas con el comando 'lanzar dados'.
    private void lanzarDados() { //de forma aleatoria
        int d1 = dado1.hacerTirada();
        int d2 = dado2.hacerTirada();
        lanzarDados(d1, d2);
    }
    private void lanzarDados(int d1, int d2){ //de forma forzosa
        //comprobaciones
        if(jugadores.size() < 2){
            System.out.println("Se necesitan al menos 2 jugadores para jugar");
            return;
        }
        Jugador jugadorActual = jugadores.get(turno);
        if(tirado){
            System.out.println(jugadorActual.getNombre() + " ya ha tirado los dados");
            return;
        }
        if(!solvente){
            System.out.println(jugadorActual.getNombre() + " tiene deudas pendientes, no puede lanzar los dados");
            return;
        }
        tirado = true;
        //el jugador esta en la carcel
        if(jugadorActual.getEnCarcel()){
            if(d1 == d2){
                //el jugador saca dobles y sale de la carcel
                jugadorActual.setEnCarcel(false);
                jugadorActual.setTiradasCarcel(0);
                System.out.println(jugadorActual.getNombre() + " saca dobles y sale de la carcel");
            }
            else{
                //el jugador no saca dobles y sigue en la carcel o completo su tercera tirada y paga para salir de la carcel
                jugadorActual.setTiradasCarcel(jugadorActual.getTiradasCarcel() + 1);
                if(jugadorActual.getTiradasCarcel() == 3){
                    if(jugadorActual.getFortuna() < Valor.PRECIO_SALIR_CARCEL){
                        solvente = false; //debe declarse en bancarota no implementado
                        System.out.println(jugadorActual.getNombre() + " no puede pagar y debe declararse en bancarrota.");
                        return;
                    }
                    jugadorActual.sumarGastos(Valor.PRECIO_SALIR_CARCEL);
                    jugadorActual.setEnCarcel(false);
                    jugadorActual.setTiradasCarcel(0);
                    System.out.println(jugadorActual.getNombre() + " paga 500.000€ y sale de la carcel.");
                }
                else{
                    System.out.println(jugadorActual.getNombre() + " sigue en la carcel.");
                    return;
                }
            }
        }
        //el jugador no esta en la carcel
        else{
            //el jugador ha sacado dobles
            if(d1 == d2){
                lanzamientos++;
                //son sus terceros dobles consecutivos, el jugador es encarcelado
                if(lanzamientos == 3){
                    jugadorActual.encarcelar(tablero.getPosiciones());
                    lanzamientos = 0;
                    System.out.println(jugadorActual.getNombre() + " saca dobles por tercera vez y va a la carcel.");
                    System.out.println(tablero);
                    return;
                }
                //no son sus terceros dobles consecutivos
                else{
                    tirado = false;
                }
            }
            //el jugador no ha sacado dobles
            else{
                lanzamientos = 0;
            }
        }
        jugadorActual.getAvatar().moverAvatar(tablero.getPosiciones(), d1 + d2); //se mueve el jugador las posiciones de los dados
        Casilla destino = jugadorActual.getAvatar().getLugar();
        solvente = destino.evaluarCasilla(jugadorActual, banca, d1 + d2); //evaluar la casilla de destino
        if(!solvente){
            System.out.println(jugadorActual.getNombre() + " debe hipotecar alguna propiedad o declararse en bancarrota.");
        }
        if(destino.getNombre().equalsIgnoreCase("IrCarcel")){ //cae en la casilla IrCarcel
            jugadorActual.encarcelar(tablero.getPosiciones());
            tirado = true;
            lanzamientos = 0;
            System.out.println(jugadorActual.getNombre() + " se coloca en la casilla de carcel");
        }

        System.out.println(tablero); //se repinta el tablero

    }

    /*Método que ejecuta todas las acciones realizadas con el comando 'comprar nombre_casilla'.
    * Parámetro: cadena de caracteres con el nombre de la casilla.
     */
    private void comprar(String nombre) {
        if(jugadores.isEmpty()){
            System.out.println("No hay jugadores registrados");
            return;
        }
        Jugador jugadorActual = jugadores.get(turno);
        Casilla casilla = tablero.encontrar_casilla(nombre);
        if(casilla == null){
            System.out.println("No existe la casilla " + nombre);
            return;
        }
        casilla.comprarCasilla(jugadorActual, banca);
    }
    /*Método que ejecuta todas las acciones realizadas con el comando 'hipotecar nombre_casilla'.
    * Parámetro: cadena de caracteres con el nombre de la casilla.
     */
    private void hipotecar(String nombreCasilla) {
    Jugador jugadorActual = jugadores.get(turno);
    Casilla casilla = this.tablero.encontrar_casilla(nombreCasilla);
    if (casilla == null) {
        System.out.println("La casilla " + nombreCasilla + " no existe.");
        return;
    }
    casilla.hipotecarCasilla(jugadorActual);
}
    /*Método que ejecuta todas las acciones realizadas con el comando 'deshipotecar nombre_casilla'.
    * Parámetro: cadena de caracteres con el nombre de la casilla.
     */
    private void deshipotecar(String nombreCasilla) {
        Jugador jugadorActual = jugadores.get(turno);
        Casilla casilla = this.tablero.encontrar_casilla(nombreCasilla);
        if (casilla == null) {
            System.out.println("La casilla " + nombreCasilla + " no existe.");
            return;
        }
        casilla.deshipotecarCasilla(jugadorActual);
    }
    //Método que ejecuta todas las acciones relacionadas con el comando 'salir carcel' pagando la cantidad de 500.000€. 
    private void salirCarcel() {
        if(jugadores.size() < 2){
            System.out.println("Se necesitan al menos dos jugadores para jugar");
            return;
        }
        Jugador jugadorActual = jugadores.get(turno);
        if(!jugadorActual.getEnCarcel()){
            System.out.println(jugadorActual.getNombre() + " no se encuentra en la carcel");
            return;
        }
        if(tirado){
            System.out.println(jugadorActual.getNombre() + " ya ha lanzado los dados en este turno");
            return;
        }
        if(jugadorActual.getFortuna() < Valor.PRECIO_SALIR_CARCEL){
            System.out.println(jugadorActual.getNombre() + " no posee el dinero suficiente para salir de la carcel");
            return;
        }
        jugadorActual.sumarGastos(Valor.PRECIO_SALIR_CARCEL);
        jugadorActual.setEnCarcel(false);
        jugadorActual.setTiradasCarcel(0);
        System.out.println(jugadorActual.getNombre() + " paga 500.000€ y sale de la carcel.");
    }

    // Método que realiza las acciones asociadas al comando 'listar enventa'.
    private void listarVenta() {
        for(Casilla c : banca.getPropiedades()){
            System.out.println(c.infoCasilla());
        }
    }

    // Método que realiza las acciones asociadas al comando 'listar jugadores'.
    private void listarJugadores() {
        if(jugadores.size() == 0){
            System.out.println("No hay jugadores registrados");
            return;
        }
        for (Jugador a : jugadores){
            System.out.println(a);
        }
    }

    // Método que realiza las acciones asociadas al comando 'listar avatares'.
    

    // Método que realiza las acciones asociadas al comando 'acabar turno'.
    

    private void acabarTurno() {
        // Comprobar que hay jugadores registrados.
        if (jugadores.isEmpty()) {
            System.out.println("No hay jugadores registrados");
            return;
        }

        // Comprobar si el jugador actual tiene deudas pendientes.
        if (!solvente) {
            System.out.println("El jugador actual tiene deudas pendientes y no puede acabar el turno.");
            return;
        }

        // Comprobar si el jugador actual ha tirado los dados.
        if (!tirado) {
            System.out.println("Debes lanzar los dados antes de acabar el turno.");
            return;
        }

        // Pasar el turno al siguiente jugador.
        turno = (turno + 1) % jugadores.size();

        // Reiniciar las variables para el nuevo turno.
        tirado = false;
        lanzamientos = 0;
        solvente = true;

        // Mostrar el jugador que tiene el turno.
        System.out.println("El jugador actual es " + jugadores.get(turno).getNombre() + ".");
    }



    private static String normalizar(String texto) {
        return Normalizer.normalize(texto, Normalizer.Form.NFD).replaceAll("\\p{M}", "").toLowerCase();
    }

}
