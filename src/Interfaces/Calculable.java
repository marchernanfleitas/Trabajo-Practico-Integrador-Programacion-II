/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

/**
 * Define el contrato para cualquier entidad capaz de calcular y actualizar su total.
 *
 * <p>La interfaz {@code Calculable} establece un único método obligatorio,
 * {@link #calcularTotal()}, que debe ser implementado por todas las clases
 * que necesiten recalcular su total según sus propias reglas de negocio.</p>
 *
 * <h2>Propósito</h2>
 * <p>Permite estandarizar el comportamiento de cálculo en distintas entidades
 * del sistema, como {@code Pedido}, que deben mantener sincronizado un valor
 * total basado en sus datos internos.</p>
 *
 * <h2>Reglas de implementación</h2>
 * <ul>
 *   <li>Cada clase debe definir su propia lógica de cálculo.</li>
 *   <li>El método no retorna un valor: actualiza un atributo interno.</li>
 *   <li>El cálculo puede basarse en sumas, multiplicaciones, descuentos u otras reglas.</li>
 * </ul>
 *
 * <h2>Ejemplo de uso</h2>
 * <ul>
 *   <li><strong>Pedido:</strong> suma los subtotales de sus detalles no eliminados
 *       y actualiza el atributo {@code total}.</li>
 * </ul>
 *
 * <p>El uso de esta interfaz favorece la extensibilidad, el polimorfismo y la
 * coherencia del modelo.</p>
 *
 * @author Marcelo Hernan Fleitas
 * @version 1.0
 * @since 1.0
 */
public interface Calculable {
    void calcularTotal (); 
}

