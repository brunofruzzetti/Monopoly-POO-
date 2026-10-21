package partida;

public class Estadisticas {
    private float dineroInvertido;
    private float pagoTasasImpuestos;
    private float pagoAlquileres;
    private float cobroAlquileres;
    private float pasarSalida;
    private float premios;
    private int vecesCarcel;

    //getters
    public float getDineroInvertido(){ return dineroInvertido; }
    public float getPagoTasasImpuestos(){ return pagoTasasImpuestos; }
    public float getPagoAlquileres(){ return pagoAlquileres; }
    public float getcobroAlquileres(){ return cobroAlquileres; }
    public float getpasarSalida(){ return pasarSalida; }
    public float getPremios(){ return premios; }
    public int getVecesCarcel(){ return vecesCarcel; }

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

    public Estadisticas(){
        dineroInvertido = 0;
        pagoTasasImpuestos = 0;
        pagoAlquileres = 0;
        cobroAlquileres = 0;
        pasarSalida = 0;
        premios = 0;
        vecesCarcel = 0;
    }

    @Override
    public String toString(){
        String salida = "{\n";
        salida += "  dineroInvertido: " + dineroInvertido + "\n";
        salida += "  pagoTasasEImpuestos: " + pagoTasasImpuestos + "\n";
        salida += "  pagoDeAlquileres: " + pagoAlquileres + "\n";
        salida += "  cobroDeAlquileres: " + cobroAlquileres + "\n";
        salida += "  pasarPorCasillaDeSalida: " + pasarSalida + "\n";
        salida += "  premiosInversionesOBote: " + premios + "\n";
        salida += "  vecesEnLaCarcel: " + vecesCarcel + "\n}";
        return salida;
    }
}
