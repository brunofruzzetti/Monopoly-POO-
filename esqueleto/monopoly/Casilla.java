package monopoly;

import partida.*;
import java.util.ArrayList;


public abstract class Casilla {

    //Atributos:
    private String nombre; //Nombre de la casilla
    private int posicion; //Posición que ocupa la casilla en el tablero (entero entre 1 y 40).
    private Jugador duenho; //Dueño de la casilla (por defecto sería la banca).
    private ArrayList<Avatar> avatares; //Avatares que están situados en la casilla.
    //private Casilla parking; //referencia para acceder a parking
    private float dineroAcumulado; //valor de cuanto ha dinero ha generado una casilla
    private int frecuentado; //numero de veces que han pasado por una casilla

    
   
    //getters
    public String getNombre() {
        return nombre;
    }

    public int getPosicion() {
        return posicion;
    }

    public Jugador getDuenho() {
        return duenho;
    }

    public ArrayList<Avatar> getAvatares() {
        return avatares;
    }
/* 
    

    public ArrayList<Edificio> getEdificios() {
        return edificios;
    }

    public float getPrecioCasa() {
        return precioCasa;
    }

    public float getPrecioHotel() {
        return precioHotel;
    }

    public float getPrecioPiscina() {
        return precioPiscina;
    }

    public float getPrecioPista() {
        return precioPista;
    }

    public float getAlquilerCasa() {
        return alquilerCasa;
    }

    public float getAlquilerHotel() {
        return alquilerHotel;
    }

    public float getAlquilerPiscina() {
        return alquilerPiscina;
    }

    public float getAlquilerPista() {
        return alquilerPista;
    }
*/
    public float getDineroAcomulado() { return dineroAcumulado; }

    public int getFrecuentado() { return frecuentado; }

    //setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public void setDuenho(Jugador duenho) {
        this.duenho = duenho;
    }
/*

    public void setAvatares(ArrayList<Avatar> avatares) {
        this.avatares = avatares;
    }
/* 
    public void setParking(Casilla parking){
        this.parking = parking;
    }
*/
    /*Método para establecer los datos de un solar según el Apéndice I. Parámetros:
     * hipoteca, alquiler, precios de casa, hotel, piscina y pista de deporte, y
     * alquileres de casa, hotel, piscina y pista de deporte.
     */
   /*  public void setDatosSolar(float hipoteca, float alquiler,
                              float precioCasa, float precioHotel, float precioPiscina, float precioPista,
                              float alquilerCasa, float alquilerHotel, float alquilerPiscina, float alquilerPista) {
        this.hipoteca = hipoteca;
        this.impuesto = alquiler;
        this.precioCasa = precioCasa;
        this.precioHotel = precioHotel;
        this.precioPiscina = precioPiscina;
        this.precioPista = precioPista;
        this.alquilerCasa = alquilerCasa;
        this.alquilerHotel = alquilerHotel;
        this.alquilerPiscina = alquilerPiscina;
        this.alquilerPista = alquilerPista;
        this.dineroAcumulado = 0;
        this.frecuentado = 0;
    }*/
    
    //Constructores:
    public Casilla() {
        avatares = new ArrayList<>();
    }//Parámetros vacíos

    // Constructor
    // Parámetros: nombre casilla, posición en el tablero, valor y dueño.
    public Casilla(String nombre, int posicion, Jugador duenho) {
        this.nombre = nombre;
        this.posicion = posicion;
        this.duenho = duenho;
        this.avatares = new ArrayList<>();
        this.dineroAcumulado = 0;
        this.frecuentado = 0;
    }

    // Funciones obligatorias casilla
    public boolean estaAvatar(Avatar avatar) {
        return avatar!=null && this.avatares.contains(avatar);
    }

    public int frecuenciaVisita() {
        return this.frecuentado;
    }

    //Devuelve el nombre de la casilla seguido de los avatares que hay en ella (por ejemplo "Solar9 &H").
    @Override
    public String toString() {
        if (avatares.isEmpty()) return this.nombre;
        String ids = " &";
        for (Avatar av : avatares) {
            ids += av.getId();
        }
        return this.nombre + ids;
    }

    // Funciones comunes
    //Método utilizado para añadir un avatar al array de avatares en casilla.
    public void anhadirAvatar(Avatar av) {
        if (av != null && !this.avatares.contains(av)) {
            this.avatares.add(av);
        }
    }

    //Método utilizado para eliminar un avatar del array de avatares en casilla.
    public void eliminarAvatar(Avatar av) {
        if (av != null) {
            this.avatares.remove(av);
        }
    }

    //Suma el dinero obtenido de una casilla a lo que ya ha acomulado
    public void sumarDineroAcomulado(float dineroAcomulado){
        this.dineroAcumulado += dineroAcomulado;
    }

    //suma 1 a la casilla donde el jugador ha caido para indicar cuantas veces han caido en esa casilla
    public void sumarFrecuencia(){
        this.frecuentado++;  
    }
/* 
    //Indica si la casilla es una propiedad que se puede comprar (solar, transporte o servicio).
    public boolean esComprable() {
        return tipo.equalsIgnoreCase("Solar") || tipo.equalsIgnoreCase("Transporte")
                || tipo.equalsIgnoreCase("Servicios");
    }
*/
    /*Método para evaluar qué hacer en una casilla concreta. Parámetros:
    * - Jugador cuyo avatar está en esa casilla.
    * - La banca (para ciertas comprobaciones).
    * - El valor de la tirada: para determinar impuesto a pagar en casillas de servicios.
    * Valor devuelto: true en caso de ser solvente (es decir, de cumplir las deudas), y false
    * en caso de no cumplirlas.*/
   /*  public boolean evaluarCasilla(Jugador actual, Jugador banca, int tirada, Tablero tablero, ArrayList<Jugador> jugadores) {
        sumarFrecuencia();
        // 1. Casillas comprables (Solar, Transporte, Servicios)
        if (esComprable()) {
            if (this.duenho != null && this.duenho != banca && this.duenho != actual) {
                if (this.hipotecada) {
                    System.out.println(nombre + " está hipotecada, no se cobra alquiler.");
                    return true;
                }
                float aPagar = 0;
                if (this.tipo.equalsIgnoreCase("Solar")) {
                    aPagar = calcularAlquiler();
                } else if (this.tipo.equalsIgnoreCase("Transporte")) {
                    aPagar = this.impuesto;
                } else if (this.tipo.equalsIgnoreCase("Servicios")) {
                    aPagar = 4 * tirada * Valor.FACTOR_SERVICIO;
                }
                if (actual.getFortuna() < aPagar) {
                    actual.setDeuda(aPagar, this.duenho);
                    System.out.println(actual.getNombre() + " no tiene dinero suficiente para pagar el alquiler de " + formato(aPagar) + "€.");
                    return false;
                }
                actual.sumarGastos(aPagar);
                this.duenho.sumarFortuna(aPagar);
                actual.getEstadisticas().sumarPagoAlquileres(aPagar);
                this.duenho.getEstadisticas().sumarCobroAlquileres(aPagar);
                sumarDineroAcomulado(aPagar);
                System.out.println("Se han pagado " + formato(aPagar) + "€ de alquiler a " + this.duenho.getNombre() + ".");
                return true;
            }
            return true;
        }
        // 2. Casillas de Impuestos
        if (this.tipo.equalsIgnoreCase("Impuesto")) {
            float tasa = this.impuesto;
            if (actual.getFortuna() < tasa) {
                actual.setDeuda(tasa, null);
                System.out.println(actual.getNombre() + " no tiene dinero suficiente para pagar el impuesto de " + formato(tasa) + "€.");
                return false;
            }
            actual.sumarGastos(tasa);
            actual.getEstadisticas().sumarPagoTasasImpuestos(tasa);
            if (parking != null) {
                parking.sumarValor(tasa);
            }
            System.out.println("El jugador paga " + formato(tasa) + "€ que se depositan en el Parking.");
            return true;
        }
        // 3. Casilla Parking
        if (this.nombre.equalsIgnoreCase("Parking")) {
            float bote = this.valor;
            if (bote > 0) {
                actual.sumarFortuna(bote);
                actual.getEstadisticas().sumarPremios(bote);
                this.valor = 0;
                System.out.println("El jugador " + actual.getNombre() + " recibe " + formato(bote) + "€.");
            } else {
                System.out.println("El Parking no tiene bote acumulado actualmente.");
            }
            return true;
        }
        // 4. Casillas de Suerte o Comunidad
        if (this.tipo.equalsIgnoreCase("Suerte") && tablero != null) {
            Carta carta = tablero.sacarCartaSuerte();
            return carta.accionar(actual, banca, jugadores, tablero);
        }
        if ((this.tipo.equalsIgnoreCase("Comunidad") || this.nombre.equalsIgnoreCase("Caja")) && tablero != null) {
            Carta carta = tablero.sacarCartaComunidad();
            return carta.accionar(actual, banca, jugadores, tablero);
        }
        // 5. Salida, Cárcel de visita e IrCarcel: sin acción aquí
        return true;
    }

    */


    /*Método usado para comprar una casilla determinada. Parámetros:
     * - Jugador que solicita la compra de la casilla.
     * - Banca del monopoly (es el dueño de las casillas no compradas aún).*/
    /* 
    public void comprarCasilla(Jugador solicitante, Jugador banca) {
        if (!esComprable()) {
            System.out.println("La casilla " + nombre + " no se puede comprar.");
            return;
        }
        if (duenho != banca) {
            System.out.println("La casilla " + nombre + " ya pertenece a " + duenho.getNombre() + ".");
            return;
        }
        if (!avatares.contains(solicitante.getAvatar())) {
            System.out.println("El avatar de " + solicitante.getNombre() + " no está en la casilla " + nombre + ".");
            return;
        }
        if (solicitante.getFortuna() < valor) {
            System.out.println(solicitante.getNombre() + " no tiene dinero suficiente para comprar " + nombre + ".");
            return;
        }
        solicitante.sumarGastos(valor);
        banca.sumarFortuna(valor);
        banca.eliminarPropiedad(this);
        solicitante.anhadirPropiedad(this);
        solicitante.getEstadisticas().sumarDineroInvertido(valor);
        System.out.println("El jugador " + solicitante.getNombre() + " compra la casilla " + nombre + " por "
                + formato(valor) + "€. Su fortuna actual es " + formato(solicitante.getFortuna()) + "€.");
    }
                */

    /*Método para añadir valor a una casilla. Utilidad:
     * - Sumar valor a la casilla de parking.
     * - Sumar valor a las casillas de solar al no comprarlas tras cuatro vueltas de todos los jugadores.
     * Este método toma como argumento la cantidad a añadir del valor de la casilla.*/
    /* 
    public void sumarValor(float suma) {
        this.valor += suma;
    }
        */

    /*Método para mostrar información de la casilla. Parámetros:
     * - Jugador que solicita la información (para mostrar si es dueño o no de la casilla).
     * Devuelve una cadena con información específica de cada tipo de casilla.*/
 /*    public String infoCasilla() {
        String propietario = (duenho == null) ? "-" : duenho.getNombre();
        if (tipo.equalsIgnoreCase("Solar")) {
            return "{\n"
                    + "  tipo: solar,\n"
                    + "  grupo: " + grupo.getColorGrupo() + ",\n"
                    + "  propietario: " + propietario + ",\n"
                    + "  valor: " + formato(valor) + ",\n"
                    + "  alquiler: " + formato(impuesto) + ",\n"
                    + "  valor hotel: " + formato(precioHotel) + ",\n"
                    + "  valor casa: " + formato(precioCasa) + ",\n"
                    + "  valor piscina: " + formato(precioPiscina) + ",\n"
                    + "  valor pista de deporte: " + formato(precioPista) + ",\n"
                    + "  alquiler casa: " + formato(alquilerCasa) + ",\n"
                    + "  alquiler hotel: " + formato(alquilerHotel) + ",\n"
                    + "  alquiler piscina: " + formato(alquilerPiscina) + ",\n"
                    + "  alquiler pista de deporte: " + formato(alquilerPista) + "\n"
                    + "}";
        }
        if (tipo.equalsIgnoreCase("Transporte") || tipo.equalsIgnoreCase("Servicios")) {
            return "{\n"
                    + "  tipo: " + tipo.toLowerCase() + ",\n"
                    + "  propietario: " + propietario + ",\n"
                    + "  valor: " + formato(valor) + ",\n"
                    + "  alquiler: " + formato(impuesto) + "\n"
                    + "}";
        }
        if (tipo.equalsIgnoreCase("Impuesto")) {
            return "{\n"
                    + "  tipo: impuesto,\n"
                    + "  apagar: " + formato(impuesto) + "\n"
                    + "}";
        }
        if (nombre.equalsIgnoreCase("Parking")) {
            String jugadores = "";
            for (Avatar av : avatares) {
                if (!jugadores.isEmpty()) jugadores += ", ";
                jugadores += av.getJugador().getNombre();
            }
            return "{\n"
                    + "  bote: " + formato(valor) + ",\n"
                    + "  jugadores: [" + jugadores + "]\n"
                    + "}";
        }
        if (nombre.equalsIgnoreCase("Carcel") || nombre.equalsIgnoreCase("Cárcel")) {
            String jugadores = "";
            for (Avatar av : avatares) {
                Jugador j = av.getJugador();
                if (j.getEnCarcel()) {
                    if (!jugadores.isEmpty()) jugadores += " ";
                    jugadores += "[" + j.getNombre() + "," + j.getTiradasCarcel() + "]";
                }
            }
            return "{\n"
                    + "  salir: " + formato(Valor.PRECIO_SALIR_CARCEL) + ",\n"
                    + "  jugadores: " + (jugadores.isEmpty() ? "-" : jugadores) + "\n"
                    + "}";
        }
        if (nombre.equalsIgnoreCase("Salida")) {
            return "{\n"
                    + "  tipo: especial,\n"
                    + "  cobrar: " + formato(Valor.SUMA_VUELTA) + "\n"
                    + "}";
        }
        //Caja de Comunidad, Suerte e IrCarcel no se describen.
        return "No tiene sentido describir la casilla " + nombre + ".";
    }*/
/* 
    // Metodo para hipotecar una casilla
    public void hipotecarCasilla(Jugador solicitante){
        if (this.duenho != solicitante){
            System.out.println(solicitante.getNombre() + " no puede hipotecar " + nombre + ". No es una propiedad que le pertenece.");
            return;
        }
        if (this.hipotecada){
            System.out.println(solicitante.getNombre() + " no puede hipotecar " + nombre + ". Ya está hipotecada.");
            return;
        }
        if (this.hipoteca <= 0){
            System.out.println(solicitante.getNombre() + " no puede hipotecar " + nombre + ". Este tipo de casilla no se puede hipotecar.");
            return;
        }
        if (!edificios.isEmpty()){
            System.out.println(solicitante.getNombre() + " no puede hipotecar " + nombre + ". Antes debe vender todos sus edificios.");
            return;
        }
        solicitante.sumarFortuna(this.hipoteca);
        solicitante.anhadirHipoteca(this);
        this.hipotecada = true;
        System.out.println(solicitante.getNombre() + " recibe " + (long) this.hipoteca + "€ por la hipoteca de " + nombre + "."
                + (grupo == null ? "" : " No puede recibir alquileres ni edificar en el grupo " + grupo.getColorGrupo() + "."));
    }
    public void deshipotecarCasilla(Jugador solicitante){
        if (this.duenho != solicitante){
            System.out.println(solicitante.getNombre() + " no puede deshipotecar " + nombre + ". No es una propiedad que le pertenece.");
            return;
        }
        if (!this.hipotecada){
            System.out.println(solicitante.getNombre() + " no puede deshipotecar " + nombre + ". No está hipotecada.");
            return;
        }
        if (solicitante.getFortuna() < this.hipoteca){
            System.out.println(solicitante.getNombre() + " no tiene dinero suficiente para deshipotecar " + nombre + ".");
            return;
        }
        solicitante.sumarGastos(this.hipoteca);
        solicitante.eliminarHipoteca(this);
        this.hipotecada = false;
        System.out.println(solicitante.getNombre() + " paga " + (long) this.hipoteca + "€ por deshipotecar " + nombre + "."
                + (grupo == null ? "" : " Ahora puede recibir alquileres y edificar en el grupo " + grupo.getColorGrupo() + "."));
    }


    //Devuelve el número de edificios de un tipo (casa, hotel, piscina o pista_deporte) construidos en la casilla.
    public int contarEdificios(String tipoEdificio) {
        int n = 0;
        for (Edificio e : edificios) {
            if (e.getTipo().equals(tipoEdificio)) n++;
        }
        return n;
    }
*/
    /*Método para construir un edificio en la casilla. Parámetros:
     * - Tipo de edificio (casa, hotel, piscina o pista_deporte).
     * - Jugador que solicita la construcción (su avatar debe estar en la casilla).
     * Reglas: el jugador debe ser dueño de todo el grupo; hotel requiere 4 casas (que se sustituyen),
     * piscina un hotel y dos casas, pista de deporte dos hoteles. En un grupo de N solares
     * no puede haber más de N edificios de cada tipo (las casas solo se limitan a N cuando ya hay N hoteles).
     */
    /* 
    public void edificar(String tipoEdificio, Jugador solicitante) {
        if (!tipo.equalsIgnoreCase("Solar")) {
            System.out.println("No se puede edificar en la casilla " + nombre + " porque no es un solar.");
            return;
        }
        if (!avatares.contains(solicitante.getAvatar())) {
            System.out.println("El avatar de " + solicitante.getNombre() + " no está en la casilla " + nombre + ".");
            return;
        }
        if (duenho != solicitante) {
            System.out.println("No se puede edificar en " + nombre + " porque no pertenece a " + solicitante.getNombre() + ".");
            return;
        }
        if (hipotecada || (grupo != null && grupo.hayHipotecas())) {
            System.out.println("No se puede edificar en " + nombre + " porque hay propiedades hipotecadas en su grupo.");
            return;
        }
        if (grupo == null || !grupo.esDuenhoGrupo(solicitante)) {
            System.out.println("No se puede edificar en " + nombre + " porque " + solicitante.getNombre()
                    + " no es dueño de todo el grupo " + (grupo == null ? "" : grupo.getColorGrupo()) + ".");
            return;
        }

        int maxGrupo = grupo.getNumCasillas();
        int casas = contarEdificios("casa");
        int hoteles = contarEdificios("hotel");
        float precio;
        switch (tipoEdificio) {
            case "casa":
                //Máximo 4 casas por solar; si el grupo ya tiene el máximo de hoteles, máximo N casas en el grupo.
                if (casas >= 4 || (grupo.contarEdificios("hotel") >= maxGrupo && grupo.contarEdificios("casa") >= maxGrupo)) {
                    System.out.println("No se puede edificar ningún edificio más en esta casilla ni en el grupo al que la casilla pertenece.");
                    return;
                }
                precio = precioCasa;
                break;
            case "hotel":
                if (casas < 4) {
                    System.out.println("No se puede edificar un hotel, ya que no se dispone de cuatro casas.");
                    return;
                }
                if (grupo.contarEdificios("hotel") >= maxGrupo) {
                    System.out.println("No se puede edificar ningún hotel más en esta casilla ni en el grupo al que la casilla pertenece.");
                    return;
                }
                precio = precioHotel;
                break;
            case "piscina":
                if (hoteles < 1) {
                    System.out.println("No se puede edificar una piscina, ya que no se dispone de un hotel.");
                    return;
                }
                if (casas < 2) {
                    System.out.println("No se puede edificar una piscina, ya que no se dispone de dos casas.");
                    return;
                }
                if (grupo.contarEdificios("piscina") >= maxGrupo) {
                    System.out.println("No se puede edificar ninguna piscina más en esta casilla ni en el grupo al que la casilla pertenece.");
                    return;
                }
                precio = precioPiscina;
                break;
            case "pista_deporte":
                if (hoteles < 2) {
                    System.out.println("No se puede edificar una pista de deporte, ya que no se dispone de dos hoteles.");
                    return;
                }
                if (grupo.contarEdificios("pista_deporte") >= maxGrupo) {
                    System.out.println("No se puede edificar ninguna pista de deporte más en esta casilla ni en el grupo al que la casilla pertenece.");
                    return;
                }
                precio = precioPista;
                break;
            default:
                System.out.println("Tipo de edificio incorrecto. Tipos: casa, hotel, piscina, pista_deporte.");
                return;
        }

        if (solicitante.getFortuna() < precio) {
            System.out.println("La fortuna de " + solicitante.getNombre() + " no es suficiente para edificar "
                    + Edificio.conArticulo(tipoEdificio) + " en la casilla " + nombre + ".");
            return;
        }

        //Al construir un hotel, las cuatro casas del solar se sustituyen por él.
        if (tipoEdificio.equals("hotel")) {
            ArrayList<Edificio> quitar = new ArrayList<>();
            for (Edificio e : edificios) {
                if (e.getTipo().equals("casa")) quitar.add(e);
            }
            for (Edificio e : quitar) {
                edificios.remove(e);
                solicitante.eliminarEdificio(e);
            }
        }

        Edificio nuevo = new Edificio(tipoEdificio, this, solicitante, precio);
        edificios.add(nuevo);
        solicitante.anhadirEdificio(nuevo);
        solicitante.sumarGastos(precio);
        solicitante.getEstadisticas().sumarDineroInvertido(precio);
        System.out.println("Se ha edificado " + Edificio.conArticulo(tipoEdificio) + " en " + nombre
                + ". La fortuna de " + solicitante.getNombre() + " se reduce en " + formato(precio) + "€.");
    }

*/
/*
    private static String nombreTipo(String t, int n) {
        switch (t) {
            case "casa": return n == 1 ? "casa" : "casas";
            case "hotel": return n == 1 ? "hotel" : "hoteles";
            case "piscina": return n == 1 ? "piscina" : "piscinas";
            default: return n == 1 ? "pista de deporte" : "pistas de deporte";
        }
    }

    public void venderEdificios(String tipoEdificio, int cantidad, Jugador solicitante) {
        if (!tipo.equalsIgnoreCase("Solar")) {
            System.out.println("No se pueden vender edificios en " + nombre + ". No es un solar.");
            return;
        }
        if (duenho != solicitante) {
            System.out.println("No se pueden vender " + nombreTipo(tipoEdificio, 2) + " en " + nombre
                    + ". Esta propiedad no pertenece a " + solicitante.getNombre() + ".");
            return;
        }
        if (cantidad <= 0) {
            System.out.println("La cantidad a vender debe ser mayor que cero.");
            return;
        }
        int existentes = contarEdificios(tipoEdificio);
        if (existentes == 0) {
            System.out.println("No hay " + nombreTipo(tipoEdificio, 2) + " en " + nombre + ".");
            return;
        }
        int aVender = Math.min(cantidad, existentes);
        float ganancia = 0;
        ArrayList<Edificio> aEliminar = new ArrayList<>();
        for (Edificio e : edificios) {
            if (aEliminar.size() < aVender && e.getTipo().equals(tipoEdificio)) {
                aEliminar.add(e);
                ganancia += e.getCoste();
            }
        }
        for (Edificio e : aEliminar) {
            edificios.remove(e);
            solicitante.eliminarEdificio(e);
        }
        solicitante.sumarFortuna(ganancia);

        int restantes = contarEdificios(tipoEdificio);
        if (cantidad > existentes) {
            System.out.println("Solamente se puede vender " + aVender + " " + nombreTipo(tipoEdificio, aVender)
                    + ", recibiendo " + formato(ganancia) + "€.");
        } else {
            System.out.println(solicitante.getNombre() + " ha vendido " + aVender + " " + nombreTipo(tipoEdificio, aVender)
                    + " en " + nombre + ", recibiendo " + formato(ganancia) + "€. En la propiedad "
                    + (restantes == 1 ? "queda " : "quedan ") + restantes + " " + nombreTipo(tipoEdificio, restantes) + ".");
        }
    }
  */  
    /*Calcula el alquiler de un solar. Sin edificios, es el alquiler base (el doble si el dueño
     * tiene todo el grupo); con edificios, el alquiler base más el de cada edificio construido.
     */
    /* 
    public float calcularAlquiler() {
        if (edificios.isEmpty()) {
            if (grupo != null && grupo.esDuenhoGrupo(duenho)) {
                return impuesto * 2;
            }
            return impuesto;
        }
        return contarEdificios("casa") * alquilerCasa
                + contarEdificios("hotel") * alquilerHotel
                + contarEdificios("piscina") * alquilerPiscina
                + contarEdificios("pista_deporte") * alquilerPista;
    }

    //Devuelve los identificadores de los edificios de un tipo, por ejemplo "[casa-1, casa-2]" o "-".
    private String idsEdificios(String tipoEdificio) {
        String ids = "";
        for (Edificio e : edificios) {
            if (e.getTipo().equals(tipoEdificio)) {
                if (!ids.isEmpty()) ids += ", ";
                ids += e.getId();
            }
        }
        return ids.isEmpty() ? "-" : "[" + ids + "]";
    }

    //Devuelve los edificios construidos en el solar y su alquiler (usado en 'listar edificios grupo').
    public String infoEdificios() {
        return "{\n"
                + "  propiedad: " + nombre + ",\n"
                + "  hoteles: " + idsEdificios("hotel") + ",\n"
                + "  casas: " + idsEdificios("casa") + ",\n"
                + "  piscinas: " + idsEdificios("piscina") + ",\n"
                + "  pistasDeDeporte: " + idsEdificios("pista_deporte") + ",\n"
                + "  alquiler: " + formato(calcularAlquiler()) + "\n"
                + "}";
    }
                */


    //Formatea una cantidad de dinero sin decimales (por ejemplo 2600000).
    public  static String formato(float cantidad) {
        return String.format("%.0f", cantidad);
    }
}
