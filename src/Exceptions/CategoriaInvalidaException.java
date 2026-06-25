/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Exceptions;

/**
 * Excepción específica utilizada para indicar errores relacionados con la
 * validez de una categoría dentro del sistema.
 *
 * <p>Extiende {@link FoodStoreException}, formando parte del conjunto de
 * excepciones de negocio que representan situaciones inválidas detectadas
 * durante la ejecución de las operaciones del dominio.
 *
 * <p>Se lanza cuando:
 * <ul>
 *     <li>Se intenta operar con una categoría nula o inexistente.</li>
 *     <li>La categoría no cumple con las reglas de negocio definidas.</li>
 *     <li>Se detecta un estado inconsistente en la entidad categoría.</li>
 * </ul>
 *
 * <p>Su propósito es mejorar la semántica de los errores y permitir un manejo
 * más preciso en la capa de presentación.
 *
 * @author Marcelo Hernan Fleitas
 * @version 1.0
 * @since 1.0
 */
public class CategoriaInvalidaException extends FoodStoreException {

    /**
     * Construye una nueva excepción indicando el motivo del error.
     *
     * @param mensaje descripción detallada del problema detectado.
     */
    public CategoriaInvalidaException(String mensaje) {
        super(mensaje);
    }
}

