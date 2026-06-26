/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Exceptions;

/**
 * Excepción utilizada para indicar que un precio ingresado no cumple con
 * las reglas de negocio del sistema.
 *
 * <p>Extiende {@link FoodStoreException}, formando parte del conjunto de
 * excepciones de dominio que representan valores inválidos detectados durante
 * la creación o modificación de entidades que manejan precios.
 *
 * <p>Se lanza cuando:
 * <ul>
 *     <li>Se intenta asignar un precio negativo.</li>
 *     <li>El precio no cumple con las validaciones definidas en el dominio.</li>
 *     <li>Una operación requiere un precio válido y el valor ingresado es incorrecto.</li>
 * </ul>
 *
 * <p>Su propósito es comunicar de forma clara que el precio proporcionado
 * no es aceptable según las reglas del negocio, permitiendo que la capa de
 * presentación informe adecuadamente al usuario.
 *
 * @author Marcelo Hernan Fleitas
 * @version 1.0
 * @since 1.0
 */
public class PrecioInvalidoException extends FoodStoreException {

    /**
     * Construye una nueva excepción indicando el motivo del error.
     *
     * @param mensaje descripción detallada del problema detectado.
     */
    public PrecioInvalidoException(String mensaje) {
        super(mensaje);
    }
}
