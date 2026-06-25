/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI;

import Entities.DetallePedido;
import Entities.Pedido;
import Entities.Producto;
import Entities.Usuario;
import Enums.Estado;
import Enums.FormaPago;
import Exceptions.FoodStoreException;
import Services.PedidoService;
import Services.ProductoService;
import Services.UsuarioService;
import Utils.LectorConsola;
import java.util.List;

/**
 * Menú de gestión de pedidos dentro de la capa de presentación.
 *
 * <p>Extiende {@link MenuCRUD} e implementa las operaciones CRUD específicas
 * para la entidad {@link Pedido}. Además, incorpora un submenú de edición
 * que permite modificar estado, forma de pago y detalles del pedido.
 *
 * <p>Este menú interactúa con:
 * <ul>
 *     <li>{@link PedidoService} para gestionar pedidos.</li>
 *     <li>{@link UsuarioService} para validar usuarios asociados.</li>
 *     <li>{@link ProductoService} para validar productos y stock.</li>
 * </ul>
 *
 * <p>Responsabilidades del menú:
 * <ul>
 *     <li>Mostrar opciones y leer datos desde consola mediante {@link LectorConsola}.</li>
 *     <li>Delegar toda la lógica de negocio a los servicios correspondientes.</li>
 *     <li>Controlar errores de negocio y mostrar mensajes adecuados.</li>
 *     <li>Gestionar el flujo de creación y edición de pedidos.</li>
 * </ul>
 *
 * <p>Este menú NO realiza validaciones de negocio ni cálculos;
 * esas responsabilidades pertenecen exclusivamente a los servicios.
 *
 * @author Marcelo Hernan Fleitas
 * @version 1.0
 * @since 1.0
 */
public class MenuPedido extends MenuCRUD {

    private final PedidoService service;
    private final UsuarioService usuarioService;
    private final ProductoService productoService;

    /**
     * Construye el menú de pedidos recibiendo las instancias de los servicios
     * necesarios para gestionar usuarios, productos y pedidos.
     *
     * @param service servicio encargado de gestionar pedidos.
     * @param usuarioService servicio encargado de gestionar usuarios.
     * @param productoService servicio encargado de gestionar productos.
     */
    public MenuPedido(
            PedidoService service,
            UsuarioService usuarioService,
            ProductoService productoService) {
        this.service = service;
        this.usuarioService = usuarioService;
        this.productoService = productoService;
    }

    /**
     * Muestra las opciones disponibles del menú de pedidos.
     */
    @Override
    protected void mostrarOpciones() {
        System.out.println("\n=== PEDIDOS ===");
        System.out.println("1. Listar");
        System.out.println("2. Crear");
        System.out.println("3. Editar");
        System.out.println("4. Eliminar");
        System.out.println("0. Volver");
    }

    /**
     * Lista todos los pedidos activos utilizando el servicio.
     * Si la lista está vacía, muestra un mensaje informativo.
     */
    @Override
    public void listar() {
        List<Pedido> lista = service.listar();
        if (listaVacia(lista, "No hay pedidos cargados.")) return;
        lista.forEach(System.out::println);
    }

    /**
     * Crea un pedido en estado borrador, permitiendo agregar múltiples detalles pedido.
     * 
     *
     * <p>El flujo de creación incluye:
     * <ul>
     *     <li>Selección de usuario.</li>
     *     <li>Selección de forma de pago.</li>
     *     <li>Selección de productos y cantidades.</li>
     *     <li>Validación de stock y datos ingresados.</li>
     *     <li>Confirmación final del pedido.</li>
     * </ul>
     *
     * <p>Si ocurre un error al cargar detalles o el pedido queda vacío,
     * la creación se cancela.
     */
    @Override
    public void crear() {
        try {
            System.out.println("\n--- Usuarios disponibles ---");
            usuarioService.listar().forEach(System.out::println);

            Long idUsuario = LectorConsola.leerLong("ID de usuario: ");
            Usuario usuario = usuarioService.buscarPorId(idUsuario);
            if (usuario == null || usuario.isEliminado()) {
                System.out.println("Usuario inexistente.");
                return;
            }

            FormaPago formaPago = LectorConsola.leerEnum("Forma de pago:", FormaPago.class);

            Pedido pedido = service.crear(formaPago, usuario);
            System.out.println("Pedido # " + pedido.getId() + " creado en estado PENDIENTE.");
            System.out.println("Para agregar detalles y confirmar el pedido debe hacerlo desde la opción Editar.");

        } catch (FoodStoreException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Agrega un detalle a un pedido existente, validando producto, stock
     * y cantidad ingresada. 
     *
     * @param pedido pedido al que se agregará el detalle.
     */
    private void agregarDetalle(Pedido pedido) {
        List<Producto> productos = productoService.listar();
        if (listaVacia(productos, "No hay productos disponibles.")) return;
        productos.forEach(System.out::println);

        Long idProducto = LectorConsola.leerLong("ID de producto: ");
        Producto producto = productoService.buscarPorId(idProducto);
        if (producto == null || producto.isEliminado()) {
            System.out.println("Producto inexistente.");
            return;
        }

        int cantidad = LectorConsola.leerInt("Cantidad: ");
        service.agregarDetalle(cantidad, pedido, producto);

        System.out.println("Detalle agregado.");
    }

    /**
     * Submenú de edición de un pedido existente.
     *
     * <p>Permite:
     * <ul>
     *      <li>Confirmar pedido.</li>
     *     <li>Cambiar estado.</li>
     *     <li>Cambiar forma de pago.</li>
     *     <li>Agregar detalles.</li>
     *     <li>Quitar detalles.</li>
     * </ul>
     *
     * <p>El menú se repite hasta que el usuario seleccione la opción de volver.
     */
    @Override
   protected void editar() {
       try {
           List<Pedido> lista = service.listar();
           if (listaVacia(lista, "No hay pedidos cargados.")) return;
           lista.forEach(System.out::println);

           Long id = LectorConsola.leerLong("ID de pedido: ");
           Pedido pedido = service.buscarPorId(id);
           if (pedido == null || pedido.isEliminado()) {
               System.out.println("Pedido inexistente.");
               return;
           }

           int opcion = -1;
           while (opcion != 0) {
               System.out.println("\n=== EDITAR PEDIDO ===" + pedido.getId() +
                       " | Estado: " + pedido.getEstado() + " ===");
               System.out.println("1. Confirmar pedido");
               System.out.println("2. Cambiar forma de pago");
               System.out.println("3. Agregar detalle");
               System.out.println("4. Quitar detalle");
               System.out.println("5. Cambiar estado");
               System.out.println("0. Volver");
               System.out.print("Seleccione: ");

               try {
                   opcion = Integer.parseInt(LectorConsola.leerString(""));
                   switch (opcion) {
                       case 1 -> confirmarPedido(pedido);
                       case 2 -> cambiarFormaPago(pedido);
                       case 3 -> agregarDetalle(pedido);
                       case 4 -> quitarDetalle(pedido);
                       case 5 -> cambiarEstado(pedido);
                       case 0 -> System.out.println("Volviendo...");
                       default -> System.out.println("Opción inválida.");
                   }
               } catch (NumberFormatException e) {
                   System.out.println("Ingrese un número válido.");
               } catch (FoodStoreException e) {
                   System.out.println("Error: " + e.getMessage());
               }
           }
       } catch (FoodStoreException e) {
           System.out.println("Error: " + e.getMessage());
       }
   }

    /**
     * Cambia el estado del pedido validando la transición.
     */
    private void cambiarEstado(Pedido pedido) {
        Estado estado = LectorConsola.leerEnum("Nuevo estado:", Estado.class);
        service.cambiarEstado(pedido.getId(), estado);
        System.out.println("Estado actualizado.");
    }

    /**
     * Cambia la forma de pago del pedido.
     */
    private void cambiarFormaPago(Pedido pedido) {
        FormaPago formaPago = LectorConsola.leerEnum("Nueva forma de pago:", FormaPago.class);
        service.cambiarFormaPago(pedido.getId(), formaPago);
        System.out.println("Forma de pago actualizada.");
    }

    /**
     * Quita un detalle del pedido y recalcula el total.
     */
    private void quitarDetalle(Pedido pedido) {
        List<DetallePedido> detalles = pedido.getDetalles();
        if (listaVacia(detalles, "El pedido no tiene detalles.")) return;
        detalles.forEach(System.out::println);

        Long idProducto = LectorConsola.leerLong("ID de producto a quitar: ");
        Producto producto = productoService.buscarPorId(idProducto);
        if (producto == null) {
            System.out.println("Producto inexistente.");
            return;
        }
        service.eliminarDetalle(pedido, producto);
        System.out.println("Detalle eliminado. Total actualizado: $" + String.format("%.2f", pedido.getTotal()));
    }
    /**
     * Confirma un pedido existente.
     */
    private void confirmarPedido(Pedido pedido) {
    service.confirmar(pedido.getId());
    System.out.println("Pedido #" + pedido.getId() + " confirmado. Total: $" + String.format("%.2f", pedido.getTotal()));
    }
    
    /**
     * Elimina lógicamente un pedido existente.
     */
    @Override
    public void eliminar() {
        Long id = LectorConsola.leerLong("ID: ");
        service.eliminar(id);
        System.out.println("Pedido eliminado.");
    } 
    
    
}

