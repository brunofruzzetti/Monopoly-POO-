package partida;

public class Estadisticas {
    private float dineroInvertido;
    private float pagoTasasImpuestos;
    private float pagoAlquileres;
    private float cobroAlquileres;
    private float pasarSalida;
    private float premios;
    private int vecesCarcel;
    private int vueltas;

    //getters
    public float getDineroInvertido(){ return dineroInvertido; }
    public float getPagoTasasImpuestos(){ return pagoTasasImpuestos; }
    public float getPagoAlquileres(){ return pagoAlquileres; }
    public float getCobroAlquileres(){ return cobroAlquileres; }
    public float getPasarSalida(){ return pasarSalida; }
    public float getPremios(){ return premios; }
    public int getVecesCarcel(){ return vecesCarcel; }
    public int getVueltas(){ return vueltas; }

    //setters
    public void sumarDineroInvertido(float dineroInvertido){
        this.dineroInvertido += dineroInvertido;
    }
    public void sumarPagoTasasImpuestos(float pagoTasasImpuestos){
        this.pagoTasasImpuestos += pagoTasasImpuestos;
    }
    public void sumarPagoAlquileres(float pagoAlquileres){
        this.pagoAlquileres += pagoAlquileres;
    }
    public void sumarPasarSalida(float pasarSalida){
        this.pasarSalida += pasarSalida;
    }
    public void sumarPremios(float premios){
        this.premios += premios;
    }
    public void sumarVecesCarcel(int vecesCarcel){
        this.vecesCarcel += vecesCarcel;
    }
    public void sumarVueltas(int vueltas){
        this.vueltas += vueltas;
    }
    public void sumarCobroAlquileres(float cobroAlquileres){
        this.cobroAlquileres += cobroAlquileres;
    }

    public Estadisticas(){
        dineroInvertido = 0;
        pagoTasasImpuestos = 0;
        pagoAlquileres = 0;
        cobroAlquileres = 0;
        pasarSalida = 0;
        premios = 0;
        vecesCarcel = 0;
        vueltas = 0;
    }

    @Override
    public String toString(){
        return "{\n"
                + "  dineroInvertido: " + (long) dineroInvertido + ",\n"
                + "  pagoTasasEImpuestos: " + (long) pagoTasasImpuestos + ",\n"
                + "  pagoDeAlquileres: " + (long) pagoAlquileres + ",\n"
                + "  cobroDeAlquileres: " + (long) cobroAlquileres + ",\n"
                + "  pasarPorCasillaDeSalida: " + (long) pasarSalida + ",\n"
                + "  premiosInversionesOBote: " + (long) premios + ",\n"
                + "  vecesEnLaCarcel: " + vecesCarcel + "\n"
                + "}";
    }
}
