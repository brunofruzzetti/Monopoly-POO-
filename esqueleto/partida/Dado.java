package partida;

import java.util.Random;

public class Dado {
    //El dado solo tiene un atributo en nuestro caso: su valor.
    private int valor;

    //Metodo para simular lanzamiento de un dado: devolverá un valor aleatorio entre 1 y 6.
    public int hacerTirada() {

        Random random = new Random();
        
        // la funcion random.nextInt genera un numero entre 0 y n (excluyendo n)
        int dado = random.nextInt(6) + 1;
        System.out.println("Salió un: " + dado);
        
        return dado;
    }
}
