/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Exceptions;

/**
 * Excepción base para todas las excepciones de negocio del sistema Food Store.
 *
 * <p>Extiende {@link RuntimeException}, permitiendo que las reglas de negocio
 * puedan expresarse mediante excepciones no verificadas (unchecked), lo que
 * simplifica el flujo de los servicios y menús sin obligar a declarar
 * {@code throws} en cada método.
 *
 * <p>Esta clase actúa como raíz del modelo de excepciones del dominio, y es
 * extendida por excepciones más específicas como:
 * <ul>
 *     <li>{@code EntidadNoEncontradaException}</li>
 *     <li>{@code EntidadEliminadaException}</li>
 *     <li>{@code DatoDuplicadoException}</li>
 *     <li>{@code CategoriaInvalidaException}</li>
 *     <!-- y cualquier otra excepción de negocio del sistema -->
 * </ul>
 *
 * <p>Su propósito es:
 * <ul>
 *     <li>Unificar el manejo de errores de negocio.</li>
 *     <li>Diferenciar claramente errores del dominio de errores técnicos.</li>
 *     <li>Permitir que la capa de presentación capture y muestre mensajes
 *         amigables al usuario.</li>
 * </ul>
 *
 * @author Marcelo Hernan Fleitas
 * @version 1.0
 * @since 1.0
 */
public class FoodStoreException extends RuntimeException {

    /**
     * Construye una nueva excepción de negocio con un mensaje descriptivo.
     *
     * @param mensaje detalle del error ocurrido según las reglas del dominio.
     */
    public FoodStoreException(String mensaje) {
        super(mensaje);
    }
}
