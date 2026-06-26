
import Entities.Categoria;
import Entities.Pedido;
import Entities.Producto;
import Entities.Usuario;
import Enums.Estado;
import Enums.FormaPago;
import Enums.Rol;
import Services.CategoriaService;
import Services.PedidoService;
import Services.ProductoService;
import Services.UsuarioService;
import UI.MenuCategoria;
import UI.MenuPedido;
import UI.MenuPrincipal;
import UI.MenuProducto;
import UI.MenuUsuario;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

/**
 * Punto de entrada principal de la aplicación FoodStore.
 *
 * <h2>Arquitectura del sistema</h2>
 * <p>La aplicación sigue una arquitectura de tres capas:</p>
 * <ul>
 *   <li><strong>Entidades</strong> ({@link Base} y subclases): modelan el dominio,
 *       protegen invariantes internas (nulo, vacío, negativos) y encapsulan
 *       reglas de flujo propias del objeto.</li>
 *   <li><strong>Servicios</strong> ({@link CrudService} y sus implementaciones):
 *       aplican reglas de negocio amplias (formato, unicidad, stock, transiciones
 *       de estado) y coordinan operaciones entre entidades.</li>
 *   <li><strong>Menús</strong> ({@link MenuCRUD} y sus implementaciones):
 *       gestionan la interacción con el usuario, leen datos por consola mediante
 *       {@link LectorConsola} y capturan excepciones de negocio para mostrar
 *       mensajes claros sin cortar el flujo.</li>
 * </ul>
 *
 * <h2>Orden de instanciación de servicios</h2>
 * <p>El orden de creación es obligatorio por las dependencias entre servicios:</p>
 * <ol>
 *   <li>{@link PedidoService} — no depende de ningún otro servicio.</li>
 *   <li>{@link ProductoService} — recibe {@link PedidoService} para verificar
 *       que un producto no tenga detalles activos antes de eliminarlo.</li>
 *   <li>{@link UsuarioService} y {@link CategoriaService} — independientes
 *       entre sí, pueden instanciarse en cualquier orden.</li>
 * </ol>
 *
 * <h2>Datos de prueba precargados</h2>
 * <p>Al iniciar la aplicación se cargan automáticamente datos de ejemplo que
 * permiten probar todas las funcionalidades del sistema sin necesidad de
 * ingresar datos manualmente:</p>
 * <ul>
 *   <li>2 categorías activas.</li>
 *   <li>4 productos distribuidos en ambas categorías.</li>
 *   <li>3 usuarios con distintos roles.</li>
 *   <li>2 pedidos confirmados con múltiples detalles.</li>
 *   <li>1 pedido en estado PENDIENTE para probar transiciones.</li>
 * </ul>
 *
 * <h2>Jerarquía de excepciones</h2>
 * <pre>
 * FoodStoreException (base)
 * ├── PedidoInvalidoException
 * ├── UsuarioInvalidoException
 * ├── ProductoInvalidoException
 * ├── CategoriaInvalidaException
 * ├── PrecioInvalidoException
 * ├── StockInvalidoException
 * ├── EntidadNoEncontradaException
 * ├── EntidadEliminadaException
 * └── DatoDuplicadoException
 * </pre>
 *
 * @author Marcelo Hernan Fleitas
 * @version 1.0
 * @since 1.0
 */
public class Main {
    public static void main(String[] args) {

        // Services -> se debe respetar el orden de creación para implementar correctamente las reglas de negocio y relaciones entre entidades.
    PedidoService pedidoService = new PedidoService();
    ProductoService productoService = new ProductoService(pedidoService);
    UsuarioService usuarioService = new UsuarioService();
    CategoriaService categoriaService = new CategoriaService();
    
        // ============================================================
        // 2. Datos de Prueba
        // Precargados para facilitar navegación por el menú completo
        cargarDatosDePrueba(categoriaService, productoService, usuarioService, pedidoService);
        // ============================================================
        // 3. MENÚS
        // Cada menú recibe solo los servicios que necesita.
        // ============================================================
        MenuCategoria menuCategoria = new MenuCategoria(categoriaService,productoService);

        MenuProducto menuProducto = new MenuProducto(productoService, categoriaService);

        MenuUsuario menuUsuario = new MenuUsuario(usuarioService);

        MenuPedido menuPedido = new MenuPedido(pedidoService, usuarioService, productoService);

        // ============================================================
        // 4. Menú Principal
        // Punto de entrada a la navegación del sistema.
        // ============================================================
        new MenuPrincipal(menuCategoria, menuProducto, menuUsuario, menuPedido).mostrar();
    }        
    /**
     * Carga un conjunto de datos de prueba representativos para un testeo
     * inicial de las funcionalidades del sistema.
     *
     * <h3>Escenarios cubiertos</h3>
     * <ul>
     *   <li>Creación de categorías, productos, usuarios y pedidos.</li>
     *   <li>Relación bidireccional categoría ↔ producto.</li>
     *   <li>Relación bidireccional usuario ↔ pedido.</li>
     *   <li>Descuento de stock al agregar detalles.</li>
     *   <li>Cálculo automático de totales al confirmar pedidos.</li>
     *   <li>Un pedido en estado PENDIENTE listo para probar transiciones
     *       de estado y cambio de forma de pago.</li>
     *   <li>Un pedido con estado finalizado (TERMINADO) para verificar
     *       que no se puede retroceder ni modificar la forma de pago.</li>
     * </ul>
     *
     * <h3>Reglas de negocio verificadas en la precarga</h3>
     * <ul>
     *   <li>No se pueden crear productos sin categoría válida.</li>
     *   <li>No se puede confirmar un pedido sin detalles activos.</li>
     *   <li>El stock se descuenta automáticamente al agregar detalles.</li>
     *   <li>La disponibilidad se desactiva automáticamente si el stock llega a cero.</li>
     * </ul>
     *
     * @param categoriaService servicio de categorías.
     * @param productoService  servicio de productos.
     * @param usuarioService   servicio de usuarios.
     * @param pedidoService    servicio de pedidos.
     */        
    private static void cargarDatosDePrueba(CategoriaService categoriaService, ProductoService productoService,UsuarioService usuarioService,PedidoService pedidoService) {
        System.out.println("Inicializando carga de datos de prueba...");

        // ----------------------------------------------------------
        // CATEGORÍAS
        // ----------------------------------------------------------
        Categoria hamburguesas = categoriaService.crear( "Hamburguesas", "Hamburguesas artesanales y combos");

        Categoria pizzas = categoriaService.crear("Pizzas", "Pizzas de masa madre hechas a la piedra en horno de barro");

        // ----------------------------------------------------------
        // PRODUCTOS
        // Cada producto se asocia a su categoría mediante agregarProducto(),
        // que mantiene la relación bidireccional (categoria ↔ producto).
        // ----------------------------------------------------------
        Producto clasica = productoService.crear("Betito", 20000.00,"Lomito clásico", 60, "img/betito.png", true, hamburguesas);
        hamburguesas.agregarProducto(clasica);

        Producto doble = productoService.crear("BetoXXL", 29000.00,"Lomito extra grande", 42, "BetoXXL.png", true, hamburguesas);
        hamburguesas.agregarProducto(doble);

        Producto primavera = productoService.crear("Primavera", 28000.00,"Pizza con salame", 30, null, true, pizzas);
        pizzas.agregarProducto(primavera);

        Producto especial = productoService.crear("Especial", 20000.00,"Pizza clásica", 45, null, true, pizzas);
        pizzas.agregarProducto(especial);

        // ----------------------------------------------------------
        // USUARIOS
        // ----------------------------------------------------------
        Usuario admin = usuarioService.crear(
                "James", "Bond",
                "james@foodstore.com", "1134567890",
                Rol.ADMIN, "admin123");

        Usuario cliente1 = usuarioService.crear(
                "Indiana", "Jones",
                "indiana@gmail.com", "1145678901",
                Rol.USUARIO, "clave456");

        Usuario cliente2 = usuarioService.crear(
                "Enola", "Holmes",
                "holmes@gmail.com", "1156789012",
                Rol.USUARIO, "clave789");

        // ----------------------------------------------------------
        // PEDIDO 1 — confirmado y terminado
        // Escenario: pedido completo que ya pasó por todo el flujo.
        // Útil para verificar que no se puede retroceder estado ni
        // modificar la forma de pago.
        // ----------------------------------------------------------
        Pedido pedido1 = pedidoService.crear(FormaPago.EFECTIVO, cliente1);
        pedidoService.agregarDetalle(2, pedido1, clasica);   
        pedidoService.agregarDetalle(2, pedido1, primavera);  
        pedidoService.confirmar(pedido1.getId());                    
        pedidoService.cambiarEstado(pedido1.getId(), Estado.TERMINADO);
        
        // ----------------------------------------------------------
        // PEDIDO 2 — pagado con tarjeta y cancelado por falta de fondos.
        // CONFIRMADO -> CANCELADO es válido ya que el pedido en estado CONFIRMADO esta en proceso y no llegó al estado final del ciclo TERMINADO.
        // ----------------------------------------------------------
        Pedido pedido2 = pedidoService.crear(FormaPago.TARJETA, admin);
        pedidoService.agregarDetalle(1, pedido2, doble);  
        pedidoService.agregarDetalle(1, pedido2, especial);
        pedidoService.confirmar(pedido2.getId());
        pedidoService.cambiarEstado(pedido2.getId(), Estado.CANCELADO);

        System.out.println("Datos de prueba cargados correctamente.");
        System.out.println("  Categorias : 2  (IDs 1-2)");
        System.out.println("  Productos  : 4  (IDs 3-6)");        
        System.out.println("  Usuarios   : 3  (IDs 7-9)");       
        System.out.println("  Pedidos    : 2  (IDs 10-13)");
        System.out.println("  Detalles Pedidos   : 4  (IDs 11-12 y 14-15)");         
        System.out.println("    - Pedido #" + pedido1.getId() + ": TERMINADO ");
        System.out.println("    - Pedido #" + pedido2.getId() + ": CANCELADO");

    }

}
