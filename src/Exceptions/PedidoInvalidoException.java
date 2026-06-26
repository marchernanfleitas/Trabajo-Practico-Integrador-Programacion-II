/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Exceptions;
/**
 * Excepción utilizada para indicar que un pedido no cumple con las reglas
 * de negocio necesarias para realizar la operación solicitada.
 *
 * <p>Extiende {@link FoodStoreException}, formando parte del conjunto de
 * excepciones de negocio del dominio relacionadas con inconsistencias o
 * estados inválidos en la gestión de pedidos.
 *
 * <p>Se lanza cuando:
 * <ul>
 *     <li>Se intenta confirmar un pedido sin detalles.</li>
 *     <li>Se intenta agregar un detalle con cantidad inválida.</li>
 *     <li>El pedido se encuentra en un estado que no permite la operación.</li>
 *     <li>La transición de estado no está permitida según las reglas del dominio.</li>
 *     <li>El pedido presenta datos incompletos o inconsistentes.</li>
 * </ul>
 *
 * <p>Su propósito es comunicar de forma clara que la operación no puede
 * completarse debido a que el pedido se encuentra en un estado inválido
 * o incumple una regla del negocio.
 *
 * @author Marcelo Hernan Fleitas
 * @version 1.0
 * @since 1.0
 */
public class PedidoInvalidoException extends FoodStoreException {

    /**
     * Construye una nueva excepción indicando el motivo del error.
     *
     * @param mensaje descripción detallada del problema detectado.
     */
    public PedidoInvalidoException(String mensaje) {
        super(mensaje);
    }
}
