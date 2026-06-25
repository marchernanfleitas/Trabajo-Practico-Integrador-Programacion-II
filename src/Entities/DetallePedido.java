/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

import Exceptions.PedidoInvalidoException;

/**
 * Representa un ítem dentro de un pedido, compuesto por un producto,
 * una cantidad solicitada y su subtotal correspondiente.
 *
 * <p>El detalle mantiene una relación directa con un {@link Producto} y
 * calcula su subtotal dinámicamente en función del precio del producto
 * y la cantidad solicitada. Además, soporta eliminación lógica mediante
 * el atributo heredado {@code eliminado}, lo que permite excluir el detalle
 * del cálculo del total del pedido sin removerlo físicamente.</p>
 *
 * <h3>Reglas de negocio</h3>
 * <ul>
 *   <li>La cantidad debe ser estrictamente mayor que cero.</li>
 *   <li>El producto no puede ser nulo.</li>
 *   <li>El subtotal se recalcula automáticamente cuando cambia la cantidad o el producto.</li>
 *   <li>Un detalle marcado como eliminado no debe ser considerado en cálculos monetarios.</li>
 * </ul>
 * 
 * @author Marcelo Hernan Fleitas
 * @version 1.0
 * @since 1.0
 */
public class DetallePedido extends Base {

    /**
     * Cantidad de unidades del producto solicitadas en el pedido.
     *
     * <p>Debe ser mayor que cero para cumplir con las reglas de negocio.</p>
     */
    private int cantidad;

    /**
     * Producto asociado a este detalle.
     *
     * <p>No puede ser nulo. Se utiliza para obtener el precio unitario
     * y calcular el subtotal.</p>
     */
    private Producto producto;

    /**
     * Subtotal del detalle de pedido.
     *
     * <p>Se actualiza automáticamente cada vez que cambian la cantidad o el producto.
     * El valor se almacena para facilitar persistencia y evitar recálculos innecesarios,
     * aunque siempre se mantiene consistente mediante {@link #calcularSubtotal()}.</p>
     */
    private double subtotal;

    /**
     * Crea un nuevo detalle de pedido con la cantidad y el producto especificados.
     *
     * <p>El subtotal se calcula dinámicamente mediante {@link #calcularSubtotal()}.</p>
     *
     * @param cantidad cantidad de unidades solicitadas
     * @param producto producto asociado al detalle
     * @throws PedidoInvalidoException si la cantidad es inválida o el producto es nulo
     */
    public DetallePedido(int cantidad, Producto producto) {
        super();
        this.setCantidad(cantidad);
        this.setProducto(producto);
        this.subtotal = calcularSubtotal();
    }

    /**
     * Obtiene la cantidad de unidades solicitadas.
     *
     * @return la cantidad del detalle
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Establece la cantidad de unidades solicitadas.
     *
     * <p>Regla de negocio: la cantidad debe ser estrictamente mayor que cero.
     * Al modificarla, el subtotal se recalcula automáticamente.</p>
     *
     * @param cantidad nueva cantidad del detalle
     * @throws PedidoInvalidoException si la cantidad es menor o igual a cero
     */
    public void setCantidad(int cantidad) {
        if (cantidad <= 0) {
            throw new PedidoInvalidoException("La cantidad debe ser mayor que cero.");
        }
        this.cantidad = cantidad;
        this.subtotal = calcularSubtotal();
    }

    /**
     * Obtiene el producto asociado a este detalle.
     *
     * @return el producto del detalle
     */
    public Producto getProducto() {
        return producto;
    }

    /**
     * Establece el producto asociado al detalle.
     *
     * <p>Regla de negocio: el producto no puede ser nulo.
     * Al modificarlo, el subtotal se recalcula automáticamente.</p>
     *
     * @param producto producto a asociar
     * @throws PedidoInvalidoException si el producto es nulo
     */
    public void setProducto(Producto producto) {
        if (producto == null) {
            throw new PedidoInvalidoException("El producto no puede ser nulo.");
        }
        this.producto = producto;
        this.subtotal = calcularSubtotal();
    }

    /**
     * Obtiene el subtotal asociado a este detalle.
     *
     * @return el subtotal del detalle
     */
    public double getSubtotal() {
        return subtotal;
    }

    /**
     * Calcula el subtotal correspondiente a este detalle.
     *
     * <p>El subtotal se obtiene multiplicando la cantidad por el precio del producto.
     * Si el producto aún no fue asignado, devuelve {@code 0.0} para evitar errores.</p>
     *
     * @return el subtotal calculado según la cantidad y el precio del producto
     */
    private double calcularSubtotal() {
        if (producto != null) {
            return cantidad * producto.getPrecio();
        }
        return 0.0;
    }

    /**
     * Devuelve una representación textual del detalle de pedido.
     *
     * <p>Incluye ID, nombre del producto, cantidad y subtotal formateado.
     * Si el detalle está marcado como eliminado, se indica explícitamente.</p>
     *
     * @return una cadena con la información relevante del detalle
     */
    @Override
    public String toString() {
        return String.format(
            "- DetallePedido #%d: %s x %d => Subtotal: $%.2f%s%n",
            this.getId(),
            producto.getNombre(),
            cantidad,
            subtotal,
            isEliminado() ? " (ELIMINADO)" : ""
        );
    }
}
