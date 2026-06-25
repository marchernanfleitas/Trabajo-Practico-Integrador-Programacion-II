/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Exceptions;

/**
 * Excepción utilizada para indicar que un valor de stock no cumple con
 * las reglas de negocio del sistema.
 *
 * <p>Extiende {@link FoodStoreException}, formando parte del conjunto de
 * excepciones de dominio que representan valores inválidos detectados durante
 * la creación, edición o uso de entidades que manejan stock.
 *
 * <p>Se lanza cuando:
 * <ul>
 *     <li>Se intenta asignar un stock negativo a un producto.</li>
 *     <li>Se intenta operar con una cantidad mayor al stock disponible.</li>
 *     <li>Una operación requiere stock válido y el valor ingresado es incorrecto.</li>
 * </ul>
 *
 * <p>Su propósito es comunicar de forma clara que el valor de stock
 * proporcionado no es aceptable según las reglas del negocio, permitiendo
 * que la capa de presentación informe adecuadamente al usuario.
 *
 * @author Marcelo Hernan Fleitas
 * @version 1.0
 * @since 1.0
 */
public class StockInvalidoException extends FoodStoreException {

    /**
     * Construye una nueva excepción indicando el motivo del error.
     *
     * @param mensaje descripción detallada del problema detectado.
     */
    public StockInvalidoException(String mensaje) {
        super(mensaje);
    }
}

