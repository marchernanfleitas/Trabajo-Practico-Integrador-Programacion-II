/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Exceptions;

/**
 * Excepción utilizada para indicar que una entidad solicitada no existe
 * dentro del sistema o no pudo ser encontrada mediante su identificador.
 *
 * <p>Extiende {@link FoodStoreException}, formando parte del conjunto de
 * excepciones de negocio que representan errores de integridad del dominio.
 *
 * <p>Se lanza cuando:
 * <ul>
 *     <li>Se intenta acceder a una entidad inexistente.</li>
 *     <li>Se busca un ID que no corresponde a ningún registro activo.</li>
 *     <li>Una operación requiere una entidad previa que no está disponible.</li>
 * </ul>
 *
 * <p>Su propósito es comunicar de forma clara que la operación no puede
 * continuar porque el recurso solicitado no está presente en el sistema.
 *
 * @author Marcelo Hernan Fleitas
 * @version 1.0
 * @since 1.0
 */
public class EntidadNoEncontradaException extends FoodStoreException {

    /**
     * Construye una nueva excepción indicando el motivo del error.
     *
     * @param mensaje descripción detallada del problema detectado.
     */
    public EntidadNoEncontradaException(String mensaje) {
        super(mensaje);
    }
}
