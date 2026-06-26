/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Exceptions;



/**
 * Excepción utilizada para indicar que se intentó operar sobre una entidad
 * que fue eliminada lógicamente del sistema.
 *
 * <p>Extiende {@link FoodStoreException}, formando parte del conjunto de
 * excepciones de negocio que representan estados inválidos dentro del dominio.
 *
 * <p>Se lanza cuando:
 * <ul>
 *     <li>Se intenta editar, reactivar o utilizar una entidad cuyo atributo
 *         <strong>eliminado = true</strong>.</li>
 *     <li>Se intenta acceder a una entidad que ya no debería estar disponible
 *         para operaciones del sistema.</li>
 *     <li>Una regla de negocio impide operar sobre elementos marcados como
 *         eliminados (por ejemplo, pedidos, usuarios, productos o categorías).</li>
 * </ul>
 *
 * <p>Su propósito es mejorar la semántica de los errores y permitir que la
 * capa de presentación informe claramente al usuario que la entidad ya no
 * está disponible para uso.
 *
 * @author Marcelo Hernan Fleitas
 * @version 1.0
 * @since 1.0
 */
public class EntidadEliminadaException extends FoodStoreException {

    /**
     * Construye una nueva excepción indicando el motivo del error.
     *
     * @param mensaje descripción detallada del problema detectado.
     */
    public EntidadEliminadaException(String mensaje) {
        super(mensaje);
    }
}
