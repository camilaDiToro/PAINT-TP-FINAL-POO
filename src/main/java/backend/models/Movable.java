package main.java.backend.models;

/**
 * Esta  interfaz  modela  el  comportamiento  de  aquellas  clases  que  tendran  la  cualidad de  poder  moverse.
 */

public interface Movable {

    /***
     * Método que permite mover un diferencial X y en Y al objeto correspondiente
     * @param diffX
     * @param diffY
     */
    void move(double diffX, double diffY);
}
