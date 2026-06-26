/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Enums;
/**
 * Representa los distintos estados posibles en el ciclo de vida de un {@link Pedido}.
 *
 * <p>Cada valor del enum define una etapa específica del proceso de compra y
 * determina qué transiciones son válidas según las reglas de negocio.</p>
 *
 * <h2>Descripción de los estados</h2>
 * <ul>
 *   <li><strong>PENDIENTE:</strong> el pedido fue creado pero aún no ha sido confirmado.</li>
 *   <li><strong>CONFIRMADO:</strong> el pedido fue validado y se encuentra en proceso.</li>
 *   <li><strong>TERMINADO:</strong> el pedido fue completado exitosamente.</li>
 *   <li><strong>CANCELADO:</strong> el pedido fue anulado por el usuario o el sistema.</li>
 * </ul>
 *
 * <p>Este enum también define las transiciones permitidas entre estados mediante
 * el método {@link #estadosPosibles(Estado)}, garantizando que el flujo del pedido
 * sea coherente y seguro.</p>
 */
public enum Estado {
    /** Pedido creado pero aún no confirmado. */    
    PENDIENTE,
    
    /** Pedido validado y en proceso. */    
    CONFIRMADO,
    
    /** Pedido completado exitosamente. */    
    TERMINADO,
    
    /** Pedido cancelado por el usuario o el sistema. */
    CANCELADO; 
    /**
     * Verifica si es posible realizar una transición desde el estado actual
     * hacia el estado indicado.
     *
     * <p>Este método implementa las reglas de negocio que definen el flujo
     * permitido del ciclo de vida de un pedido. Las transiciones válidas son:</p>
     *
     * <ul>
     *   <li><strong>PENDIENTE → CONFIRMADO</strong></li>
     *   <li><strong>PENDIENTE → CANCELADO</strong></li>
     *   <li><strong>CONFIRMADO → TERMINADO</strong></li>
     *   <li><strong>CONFIRMADO → CANCELADO</strong></li>
     * </ul>
     *
     * <p>Los estados <strong>TERMINADO</strong> y <strong>CANCELADO</strong> son finales,
     * por lo que no permiten ninguna transición posterior.</p>
     *
     * @param nuevo el estado al que se desea cambiar.
     * @return {@code true} si la transición es válida según las reglas de negocio,
     *         {@code false} en caso contrario.
     */
    public boolean estadosPosibles(Estado nuevo) {
        return switch (this) {
            case PENDIENTE -> nuevo == CONFIRMADO || nuevo == CANCELADO;
            case CONFIRMADO -> nuevo == TERMINADO || nuevo == CANCELADO;
            case TERMINADO -> false;
            case CANCELADO -> false;
        };
    }
}

