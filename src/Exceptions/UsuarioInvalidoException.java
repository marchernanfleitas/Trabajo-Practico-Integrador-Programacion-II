/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Exceptions;

/**
 * Excepción utilizada para indicar que un usuario no cumple con las reglas
 * de negocio necesarias para realizar la operación solicitada.
 *
 * <p>Extiende {@link FoodStoreException}, formando parte del conjunto de
 * excepciones del dominio que representan datos inválidos o inconsistentes
 * relacionados con la entidad {@link Usuario}.
 *
 * <p>Se lanza cuando:
 * <ul>
 *     <li>Los datos ingresados para crear o editar un usuario no son válidos
 *         (nombre, apellido, mail, celular, contrasenia, etc.).</li>
 *     <li>Se intenta operar con un usuario en un estado no permitido.</li>
 *     <li>Se detecta una violación a las reglas de negocio asociadas a usuarios.</li>
 * </ul>
 *
 * <p>Su propósito es comunicar de forma clara que la operación no puede
 * completarse debido a que el usuario presenta datos inválidos o incumple
 * una regla del dominio.
 *
 * @author Marcelo Hernan Fleitas
 * @version 1.0
 * @since 1.0
 */
public class UsuarioInvalidoException extends FoodStoreException {

    /**
     * Construye una nueva excepción indicando el motivo del error.
     *
     * @param mensaje descripción detallada del problema detectado.
     */
    public UsuarioInvalidoException(String mensaje) {
        super(mensaje);
    }
}
