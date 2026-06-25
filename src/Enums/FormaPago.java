/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Enums;

/**
 * Representa los distintos métodos de pago disponibles para un {@link Pedido}.
 *
 * <p>Este enum define las formas de pago aceptadas por el sistema, permitiendo
 * estandarizar la lógica de cobro y evitar valores inválidos o inconsistentes.</p>
 *
 * <h2>Descripción de las formas de pago</h2>
 * <ul>
 *   <li><strong>EFECTIVO:</strong> pago realizado en dinero físico al momento de la entrega.</li>
 *   <li><strong>TARJETA:</strong> pago mediante tarjeta de crédito o débito.</li>
 *   <li><strong>TRANSFERENCIA:</strong> pago mediante transferencia bancaria.</li>
 * </ul>
 *
 * <p>El uso de un enum garantiza claridad y seguridad en la selección del método
 * de pago dentro del sistema.</p>
 *
 * @author Marcelo Hernan Fleitas
 * @version 1.0
 * @since 1.0
 */
public enum FormaPago {
    /** Pago mediante tarjeta de crédito o débito. */ 
    TARJETA,
    
    /** Pago mediante transferencia bancaria. */
    TRANSFERENCIA,

    /** Pago realizado en dinero físico. */    
    EFECTIVO    
}