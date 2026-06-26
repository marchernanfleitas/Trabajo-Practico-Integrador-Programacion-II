/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;


import Entities.DetallePedido;
import Entities.Pedido;
import Entities.Producto;
import Entities.Usuario;
import Enums.Estado;
import Enums.FormaPago;
import Exceptions.EntidadEliminadaException;
import Exceptions.EntidadNoEncontradaException;
import Exceptions.PedidoInvalidoException;
import Exceptions.StockInvalidoException;
import Exceptions.UsuarioInvalidoException;
import Interfaces.CrudService;
import Utils.ServicioValidador;
import java.util.ArrayList;
import java.util.List;

/**
 * Servicio encargado de gestionar el ciclo de vida de las entidades {@link Pedido}.
 *
 * <p>Implementa las operaciones CRUD definidas en {@link CrudService} y aplica
 * reglas de negocio específicas:
 * <ul>
 *     <li>Validación de usuario, forma de pago y estado del pedido.</li>
 *     <li>Control de stock al agregar detalles.</li>
 *     <li>Evita operar sobre pedidos eliminados lógicamente.</li>
 *     <li>Gestiona transiciones válidas entre estados del pedido.</li>
 *     <li>Calcula el total del pedido antes de confirmarlo.</li>
 * </ul>
 *
 * <p>Este servicio no interactúa con la capa de UI; únicamente gestiona lógica
 * de negocio y validaciones internas.
 *
 * @author Marcelo Hernan Fleitas
 * @version 1.0
 * @since 1.0
 */
public class PedidoService implements CrudService<Pedido> {

    private final List<Pedido> pedidos;

    /**
     * Crea una nueva instancia del servicio inicializando la colección interna.
     */
    public PedidoService() {
        this.pedidos = new ArrayList<>();
    }

    /**
     * Retorna una lista de todos los pedidos activos (no eliminados).
     *
     * @return lista de pedidos visibles para el sistema.
     */
    @Override
    public List<Pedido> listar() {
        return pedidos.stream()
                .filter(p -> !p.isEliminado())
                .toList();
    }

    /**
     * Busca un pedido por su identificador.
     *
     * @param id identificador único del pedido.
     * @return el pedido encontrado o {@code null} si no existe.
     */
    @Override
    public Pedido buscarPorId(Long id) {
        for (Pedido p : pedidos) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Elimina lógicamente un pedido junto con todos sus detalles.
     *
     * @param id identificador del pedido a eliminar.
     *
     * @throws EntidadNoEncontradaException si el pedido no existe.
     * @throws EntidadEliminadaException si ya estaba eliminado.
     */
    @Override
    public void eliminar(Long id) {
        Pedido pedido = validarPedidoActivo(id);
 
        for (DetallePedido dt : pedido.getDetalles()) {
            if (!dt.isEliminado()){
                dt.getProducto().setStock(dt.getProducto().getStock() + dt.getCantidad()); //retorna el stock al producto antes del soft delete para mantener consistencia.
            }
            dt.setEliminado(true); //se elimina el detalle con el id asignado.
        }
        pedido.setEliminado(true); //eliminación lógica del pedido
    }

    /**
     * Crea un nuevo pedido validando usuario y forma de pago.
     *
     * @param formaPago forma de pago seleccionada.
     * @param usuario usuario asociado al pedido.
     *
     * @return el pedido creado (aún no confirmado).
     *
     * @throws PedidoInvalidoException si la forma de pago es nula.
     * @throws UsuarioInvalidoException si el usuario es nulo o está eliminado.
     */
    public Pedido crear(FormaPago formaPago, Usuario usuario) {

        if (formaPago == null) {
            throw new PedidoInvalidoException("La forma de pago no puede ser nula.");
        }

        if (usuario == null || usuario.isEliminado()) {
            throw new UsuarioInvalidoException("Usuario inexistente.");
        }

        Pedido nuevoPedido= new Pedido(formaPago, usuario);
        pedidos.add(nuevoPedido);       // se agrega un nuevo pedido en borrador.
        usuario.agregarPedido(nuevoPedido);  // se relaciona con un usuario dueño de ese pedido.
        return nuevoPedido;
    }

    /**
     * Agrega un detalle al pedido validando stock, cantidad y estado del pedido.
     *
     * @param cantidad cantidad del producto.
     * @param pedido pedido al que se agregará el detalle.
     * @param producto producto a agregar.
     *
     * @throws EntidadNoEncontradaException si el pedido no existe.
     * @throws EntidadEliminadaException si el pedido está eliminado.
     * @throws PedidoInvalidoException si el producto es inválido o la cantidad no es válida.
     * @throws StockInvalidoException si no hay stock suficiente.
     */
    public void agregarDetalle(int cantidad, Pedido pedido, Producto producto) {

        validarPedidoActivo(pedido);
        validarProductoActivo(producto);
        validarPedidoModificable(pedido);   
        ServicioValidador.validarCantidad(cantidad);
        validarStockDisponible(producto, cantidad);

        pedido.addDetallePedido(cantidad, producto);
        producto.setStock(producto.getStock() - cantidad);
    }

    /**
     * Confirma un pedido, calculando su total y agregándolo al registro.
     *
     * @param pedido pedido a confirmar.
     *
     * @throws EntidadNoEncontradaException si el pedido no existe.
     * @throws EntidadEliminadaException si el pedido está eliminado.
     * @throws PedidoInvalidoException si el pedido no tiene detalles.
     */
    public void confirmar(Long id) {
        Pedido pedido = validarPedidoActivo(id);
        validarPedidoModificable(pedido);

    boolean tieneDetallesActivos = pedido.getDetalles().stream().anyMatch(d -> !d.isEliminado()); // valida solo pedidos activos
        
        if(!tieneDetallesActivos){
            throw new PedidoInvalidoException("El pedido debe tener al menos un Detalle Pedido activo.");
        }

        pedido.calcularTotal();
        pedido.setEstado(Estado.CONFIRMADO);
    }

    /**
     * Cambia el estado de un pedido validando la transición permitida.
     *
     * @param id identificador del pedido.
     * @param nuevo nuevo estado solicitado.
     *
     * @throws EntidadNoEncontradaException si el pedido no existe o está eliminado.
     * @throws PedidoInvalidoException si la transición de estado no es válida.
     */
    public void cambiarEstado(Long id, Estado nuevo) {
        Pedido pedido = validarPedidoActivo(id); 

        if (pedido == null || pedido.isEliminado()) {
            throw new EntidadNoEncontradaException("Pedido inexistente.");
        }

        if (!pedido.getEstado().estadosPosibles(nuevo)) {
            throw new PedidoInvalidoException(
                    "No se puede cambiar de " + pedido.getEstado() + " a " + nuevo);
        }
        pedido.setEstado(nuevo);
    }
    /**
     * Cambia la forma de pago de un pedido.
     *
     * @param id identificador del pedido.
     * @param nuevaFormaPago nueva forma de pago 
     *
     * @throws EntidadNoEncontradaException si el pedido no existe o está eliminado.
     */    
    public void cambiarFormaPago(Long id, FormaPago nuevaFormaPago) {
        Pedido pedido = validarPedidoActivo(id); 

        if (pedido == null || pedido.isEliminado()) {
            throw new EntidadNoEncontradaException("Pedido inexistente.");
        }
        validarPedidoModificable(pedido);
        pedido.setFormaPago(nuevaFormaPago);
    }    

    /**
     * Verifica que el pedido exista y no esté eliminado.
     *
     * @param id identificador del pedido.
     * @return el pedido válido.
     *
     * @throws EntidadNoEncontradaException si no existe.
     * @throws EntidadEliminadaException si está eliminado.
     */
    private Pedido validarPedidoActivo(Long id) {
        Pedido pedido = buscarPorId(id);

        if (pedido == null) {
            throw new EntidadNoEncontradaException("Pedido inexistente.");
        }
        if (pedido.isEliminado()) {
            throw new EntidadEliminadaException("El pedido fue eliminado.");
        }

        return pedido;
    }

    /**
     * Verifica que el pedido no sea nulo y no esté eliminado.
     *
     * @param pedido instancia del pedido.
     * @return el pedido válido.
     *
     * @throws EntidadNoEncontradaException si es nulo.
     * @throws EntidadEliminadaException si está eliminado.
     */
    private Pedido validarPedidoActivo(Pedido pedido) {
        if (pedido == null) {
            throw new EntidadNoEncontradaException("Pedido inexistente.");
        }
        if (pedido.isEliminado()) {
            throw new EntidadEliminadaException("El pedido fue eliminado.");
        }
        return pedido;
    }
    
    /**
     * Elimina lógicamente un detalle del pedido y devuelve el stock al producto.
     *
     * @param pedido  pedido del que se quitará el detalle.
     * @param producto producto cuyo detalle se elimina.
     *
     * @throws EntidadNoEncontradaException si el pedido es nulo o no existe.
     * @throws EntidadEliminadaException    si el pedido está eliminado.
     * @throws PedidoInvalidoException      si el producto es nulo o no tiene detalle.
     */
    public void eliminarDetalle(Pedido pedido, Producto producto) {
        validarPedidoActivo(pedido);
        validarProductoActivo(producto);
        validarPedidoModificable(pedido);
        DetallePedido detalle = pedido.findDetallePedidoByProducto(producto);
        if (detalle == null) {
            throw new PedidoInvalidoException("No existe un detalle activo con dicho producto en el pedido.");
        }

        producto.setStock(producto.getStock() + detalle.getCantidad());

        pedido.deleteDetallePedidoByProducto(producto);

        pedido.calcularTotal();
    }

    /**
     * Verifica que el producto exista y no esté eliminado.
     *
     * @param producto producto a validar.
     * @return el producto válido.
     *
     * @throws PedidoInvalidoException si el producto es nulo o está eliminado.
     */
    private Producto validarProductoActivo(Producto producto) {
        if (producto == null || producto.isEliminado()) {
            throw new PedidoInvalidoException("Producto inexistente.");
        }
        return producto;
    }

    /**
     * Verifica que el stock del producto sea suficiente para la cantidad solicitada.
     *
     * @param producto producto a validar.
     * @param cantidad cantidad solicitada.
     *
     * @throws StockInvalidoException si el stock es insuficiente.
     */
    private void validarStockDisponible(Producto producto, int cantidad) {
        if (producto.getStock() < cantidad) {
            throw new StockInvalidoException("Stock insuficiente para: " + producto.getNombre() + ". Disponible: " + producto.getStock() );
        }
    }
   /**
    * Verifica que el pedido esté en estado PENDIENTE para permitir modificaciones.
    *
    * @param pedido pedido a validar.
    * @throws PedidoInvalidoException si el pedido no está en estado PENDIENTE.
    */
    private void validarPedidoModificable(Pedido pedido) {
       if (pedido.getEstado() != Estado.PENDIENTE) {
           throw new PedidoInvalidoException(
               "Solo se pueden modificar pedidos en estado PENDIENTE. " +"Estado actual: " + pedido.getEstado());
       }
   }
}
