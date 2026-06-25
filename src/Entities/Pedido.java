/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

import Enums.Estado;
import Enums.FormaPago;
import Exceptions.PedidoInvalidoException;
import Exceptions.UsuarioInvalidoException;
import Interfaces.Calculable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa un pedido realizado por un usuario dentro del sistema.
 *
 * <p>Un pedido contiene información sobre su fecha de creación, estado,
 * forma de pago, usuario asociado y los distintos detalles que lo componen.
 * La fecha es inmutable y se asigna automáticamente al momento de instanciar
 * el pedido. El total monetario se recalcula dinámicamente mediante
 * {@link #calcularTotal()} considerando únicamente los detalles no eliminados.</p>
 *
 * <h3>Reglas de negocio principales</h3>
 * <ul>
 *   <li>Todo pedido debe tener un usuario válido y no eliminado.</li>
 *   <li>La forma de pago solo puede modificarse mientras el pedido esté en estado {@code PENDIENTE}.</li>
 *   <li>No se permite retroceder el estado del pedido ni modificar pedidos cancelados.</li>
 *   <li>No puede agregarse más de un detalle para el mismo producto.</li>
 *   <li>Los detalles eliminados no se consideran en el cálculo del total.</li>
 * </ul>
 *
 * <h3>Relación con otras entidades</h3>
 * <ul>
 *   <li>{@link Usuario}: propietario del pedido.</li>
 *   <li>{@link DetallePedido}: ítems que conforman el pedido.</li>
 *   <li>{@link Producto}: productos incluidos en cada detalle.</li>
 * </ul>
 *
 * Esta clase extiende {@link Base} para heredar identificadores y metadatos
 * comunes, e implementa {@link Calculable} para garantizar el cálculo del total.
 * 
 * @author Marcelo Hernan Fleitas
 * @version 1.0
 * @since 1.0
 */

public class Pedido extends Base implements Calculable {

    /**
     * Fecha inmutable en la que se generó el pedido.
     *
     * <p>Se asigna automáticamente al momento de creación del pedido y no puede
     * modificarse posteriormente.</p>
     */
    private final LocalDate fecha;

    /**
     * Estado actual del pedido.
     *
     * <p>Controla el flujo del proceso (por ejemplo: PENDIENTE, PAGADO, ENTREGADO).
     * No puede retroceder ni modificarse si el pedido está cancelado.</p>
     */
    private Estado estado;

    /**
     * Forma de pago seleccionada por el usuario.
     *
     * <p>Solo puede modificarse mientras el pedido esté en estado {@code PENDIENTE}.</p>
     */
    private FormaPago formaPago;

    /**
     * Lista de detalles asociados al pedido.
     *
     * <p>Siempre se inicializa vacía. No se expone directamente para evitar
     * modificaciones externas no controladas; se devuelve una copia defensiva.</p>
     */
    private final List<DetallePedido> detalles;

    /**
     * Usuario que realizó el pedido.
     *
     * <p>Debe ser un usuario válido y no eliminado. La relación es obligatoria.</p>
     */
    private Usuario usuario;

    /**
     * Total monetario del pedido.
     *
     * <p>Se recalcula dinámicamente mediante {@link #calcularTotal()} considerando
     * únicamente los detalles no eliminados.</p>
     */
    private double total;

    /**
     * Crea un nuevo pedido asignando fecha, estado inicial, forma de pago y usuario.
     *
     * <p>La fecha se establece automáticamente como la fecha actual. El estado
     * inicial es siempre {@code PENDIENTE}. La lista de detalles comienza vacía
     * y el total en cero.</p>
     *
     * @param formaPago forma de pago seleccionada; no puede ser nula
     * @param usuario usuario que realiza el pedido; debe ser válido y no eliminado
     * @throws PedidoInvalidoException si la forma de pago es nula
     * @throws UsuarioInvalidoException si el usuario es nulo o está eliminado
     */
    public Pedido(FormaPago formaPago, Usuario usuario) {
        super();
        this.fecha = LocalDate.now();
        this.estado = Estado.PENDIENTE;
        this.setFormaPago(formaPago);
        this.setUsuario(usuario);
        this.detalles = new ArrayList<>();
        this.total = 0.0;
    }

    /**
     * Obtiene la fecha de creación del pedido.
     *
     * @return fecha del pedido
     */
    public LocalDate getFecha() {
        return fecha;
    }

    /**
     * Obtiene el estado actual del pedido.
     *
     * @return estado del pedido
     */
    public Estado getEstado() {
        return estado;
    }

    /**
     * Obtiene la forma de pago seleccionada.
     *
     * @return forma de pago
     */
    public FormaPago getFormaPago() {
        return formaPago;
    }

    /**
     * Obtiene una copia defensiva de la lista de detalles.
     *
     * @return lista de detalles del pedido
     */
    public List<DetallePedido> getDetalles() {
        return new ArrayList<>(detalles);
    }

    /**
     * Obtiene el usuario asociado al pedido.
     *
     * @return usuario propietario del pedido
     */
    public Usuario getUsuario() {
        return usuario;
    }
    /**
     * Modifica la forma de pago del pedido.
     *
     * <p>Solo es posible si el pedido se encuentra en estado {@code PENDIENTE}.</p>
     *
     * @param formaPago nueva forma de pago
     * @throws PedidoInvalidoException si la forma de pago es nula o el pedido no está pendiente
     */
    public void setFormaPago(FormaPago formaPago) {
        if (formaPago == null) {
            throw new PedidoInvalidoException("La forma de pago no puede ser nula.");
        }
        if (this.estado != null && this.estado != Estado.PENDIENTE) {
            throw new PedidoInvalidoException("Solo se puede cambiar la forma de pago en pedidos pendientes.");
        }
        this.formaPago = formaPago;
    }

    /**
     * Asigna el usuario propietario del pedido.
     *
     * @param usuario usuario válido y no eliminado
     * @throws UsuarioInvalidoException si el usuario es nulo o está eliminado
     */
    public void setUsuario(Usuario usuario) {
        if (usuario == null || usuario.isEliminado()) {
            throw new UsuarioInvalidoException("El usuario no puede ser nulo o estar eliminado.");
        }
        this.usuario = usuario;
    }

    /**
     * Cambia el estado del pedido respetando las reglas de negocio.
     *
     * <p>No se permite retroceder estados ni modificar pedidos cancelados.</p>
     *
     * @param nuevoEstado estado al que se desea avanzar
     * @throws PedidoInvalidoException si el estado es nulo, si el pedido está cancelado,
     *                                 o si se intenta retroceder el flujo
     */
    public void setEstado(Estado nuevoEstado) {
        if (nuevoEstado == null) {
            throw new PedidoInvalidoException("El estado no puede ser nulo.");
        }
        if (this.estado == Estado.CANCELADO) {
            throw new PedidoInvalidoException("No se puede modificar un pedido cancelado.");
        }
        if (nuevoEstado.ordinal() < this.estado.ordinal()) {
            throw new PedidoInvalidoException("No se puede retroceder el estado del pedido.");
        }
        this.estado = nuevoEstado;
    }
    /**
     * Recalcula el total del pedido sumando los subtotales de los detalles no eliminados.
     *
     * <p>Este método sobrescribe la operación definida por {@link Calculable}.</p>
     */
    @Override
    public void calcularTotal() {
        this.total = 0;
        for (DetallePedido d : detalles) {
            if (!d.isEliminado()) {
                this.total += d.getSubtotal();
            }
        }
    }

    /**
     * Obtiene el total monetario del pedido.
     *
     * @return total del pedido
     */
    public double getTotal() {
        return total;
    }
    /**
     * Agrega un nuevo detalle al pedido.
     *
     * <p>No se permite agregar dos detalles del mismo producto.</p>
     *
     * @param cantidad cantidad del producto
     * @param producto producto asociado
     * @throws PedidoInvalidoException si el producto es nulo, la cantidad es inválida
     *                                 o ya existe un detalle para ese producto
     */
    public void addDetallePedido(int cantidad, Producto producto) {
        if (producto == null) {
            throw new PedidoInvalidoException("El producto no puede ser nulo.");
        }
        if (cantidad <= 0) {
            throw new PedidoInvalidoException("La cantidad debe ser mayor que cero.");
        }
        if (findDetallePedidoByProducto(producto) != null) {
            throw new PedidoInvalidoException("Ya existe un detalle para este producto.");
        }
        detalles.add(new DetallePedido(cantidad, producto));
    }

    /**
     * Busca un detalle asociado a un producto específico.
     *
     * @param producto producto buscado
     * @return el detalle correspondiente o {@code null} si no existe
     */
    public DetallePedido findDetallePedidoByProducto(Producto producto) {
        for (DetallePedido d : detalles) {
            if (!d.isEliminado() && d.getProducto().getId().equals(producto.getId())) {
                return d;
                    }
                }
            return null;
    }
          
        
    /**
     * Elimina lógicamente un detalle del pedido según su producto.
     *
     * <p>Este método no remueve físicamente el detalle de la lista. En su lugar,
     * marca el detalle como eliminado mediante {@code setEliminado(true)}.
     * Esto permite conservar el registro para auditoría o historial, y asegura
     * que el detalle no sea considerado en el cálculo del total.</p>
     *
     * <p>Si no existe un detalle asociado al producto indicado, se lanza una
     * excepción indicando que la operación no puede completarse.</p>
     *
     * @param producto producto cuyo detalle debe eliminarse
     * @throws PedidoInvalidoException si el producto es nulo o no existe un detalle asociado
     */
    public void deleteDetallePedidoByProducto(Producto producto) {
        if (producto == null) {
            throw new PedidoInvalidoException("El producto ingresado para eliminar del pedido no puede ser nulo.");
        }

        for (DetallePedido d : detalles) {
            if (d.getProducto().getId().equals(producto.getId())) {
                d.setEliminado(true);   // Eliminación lógica marcando Eliminado true
                return;
            }
        }

             throw new PedidoInvalidoException("No existe un detalle con dicho producto en el pedido.");
    }
    /**
     * Devuelve una representación resumida del pedido.
     *
     * @return cadena con información principal del pedido
     */
    @Override
    public String toString() {
        long detallesActivos = detalles.stream()
                .filter(d -> !d.isEliminado())
                .count();
        return String.format(
                "> Pedido #%d | %s | %s | %s | Total: $%.2f | Detalles: %d",
                getId(),
                fecha,
                estado,
                formaPago,
                total,
                detallesActivos  
        );
    }
}
