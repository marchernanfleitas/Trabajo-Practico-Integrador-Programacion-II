/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Exceptions;

/**
 * Excepción utilizada para indicar que un dato que debe ser único dentro del
 * sistema ya existe y no puede volver a registrarse.
 *
 * <p>Extiende {@link FoodStoreException}, formando parte del conjunto de
 * excepciones de negocio que representan violaciones a reglas del dominio.
 *
 * <p>Se lanza cuando:
 * <ul>
 *     <li>Se intenta crear una entidad con un valor que ya está registrado
 *         (por ejemplo, nombre de categoría, mail de usuario, nombre de producto).</li>
 *     <li>Se intenta editar una entidad asignándole un valor que ya pertenece
 *         a otra entidad activa.</li>
 * </ul>
 *
 * <p>Su propósito es mejorar la semántica de los errores y permitir que la
 * capa de presentación informe claramente al usuario sobre conflictos de
 * unicidad.
 *
 * @author Marcelo Hernan Fleitas
 * @version 1.0
 * @since 1.0
 */
public class DatoDuplicadoException extends FoodStoreException {

    /**
     * Construye una nueva excepción indicando el motivo del conflicto.
     *
     * @param mensaje descripción detallada del dato duplicado detectado.
     */
    public DatoDuplicadoException(String mensaje) {
        super(mensaje);
    }
}


