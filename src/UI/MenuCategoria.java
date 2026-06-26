/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI;

import Entities.Categoria;
import Entities.Producto;
import Exceptions.FoodStoreException;
import Services.CategoriaService;
import Services.ProductoService;
import Utils.LectorConsola;
import java.util.List;

/**
 * Menú de gestión de categorías dentro de la capa de presentación.
 *
 * <p>Extiende {@link MenuCRUD} e implementa las operaciones CRUD específicas
 * para la entidad {@link Categoria}, delegando la lógica de negocio al
 * {@link CategoriaService}.
 *
 * <p>Su responsabilidad es:
 * <ul>
 *     <li>Mostrar opciones del menú de categorías.</li>
 *     <li>Leer datos desde consola mediante {@link LectorConsola}.</li>
 *     <li>Invocar los métodos correspondientes del servicio.</li>
 *     <li>Mostrar resultados y mensajes al usuario.</li>
 * </ul>
 *
 * <p>No contiene lógica de negocio; toda validación y procesamiento se delega
 * al servicio correspondiente.
 *
 * @author Marcelo Hernan Fleitas
 * @version 1.0
 * @since 1.0
 */
public class MenuCategoria extends MenuCRUD {

    private final CategoriaService service;
    private final ProductoService productoService;

    /**
     * Construye el menú de categorías recibiendo la instancia del servicio
     * que gestionará las operaciones CRUD.
     *
     * @param service servicio encargado de gestionar categorías.
     */
    public MenuCategoria(CategoriaService service, ProductoService productoService) {
        this.service = service;
        this.productoService = productoService;
    }
    /**
     * Muestra las opciones disponibles del menú de categorías.
     */
    @Override
    protected void mostrarOpciones() {
        System.out.println("\n=== CATEGORÍAS ===");
        System.out.println("1. Listar");
        System.out.println("2. Crear");
        System.out.println("3. Editar");
        System.out.println("4. Eliminar");
        System.out.println("5. Asignar producto a categoría"); 
        System.out.println("0. Volver");
    }

    /**
     * Lista todas las categorías activas utilizando el servicio.
     * Si la lista está vacía, muestra un mensaje informativo.
     */
    @Override
    public void listar() {
        List<Categoria> lista = service.listar();
        if (listaVacia(lista, "No hay categorías cargadas.")) return;
        lista.forEach(System.out::println);
    }

    /**
     * Solicita los datos necesarios para crear una categoría y delega la
     * operación al servicio.
     *
     * <p>Muestra el ID asignado una vez creada.
     */
    @Override
    protected void crear() {
      try{
        String nombre = LectorConsola.leerString("Nombre: ");
        String descripcion = LectorConsola.leerString("Descripción: ");

        Categoria categoria = service.crear(nombre, descripcion);

        System.out.println("Categoría creada. ID: " + categoria.getId());
            } catch (FoodStoreException e) {
        System.out.println("Error: " + e.getMessage());
    }
    }

    /**
     * Solicita los datos necesarios para editar una categoría existente y
     * delega la operación al servicio.
     */
    @Override
    protected void editar() {
        try {
            List<Categoria> lista = service.listar();
            if (listaVacia(lista, "No hay categorías cargadas.")) return;
            lista.forEach(System.out::println);

            Long id = LectorConsola.leerLong("ID: ");
            String nombre = LectorConsola.leerString("Nuevo nombre (Enter para omitir): ");
            String descripcion = LectorConsola.leerString("Nueva descripción (Enter para omitir): ");

            service.editar(id, nombre.isBlank() ? null : nombre, descripcion.isBlank() ? null : descripcion );
            System.out.println("Categoría modificada.");
        } catch (FoodStoreException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    /**
     * Ejecuta la opción seleccionada por el usuario dentro del menú de categorías.
     * <p>
     * Este método extiende el comportamiento del menú base para incorporar la
     * opción adicional de asignar un producto a una categoría (opción 5).
     * Las opciones estándar (1–4 y 0) continúan delegándose al menú padre.
     * </p>
     *
     * @param opcion número ingresado por el usuario.
     * @throws FoodStoreException si ocurre un error en alguna operación delegada.
     */
        @Override
    protected void ejecutarOpcion(int opcion) throws FoodStoreException {
        if (opcion == 5) {
            asignarProducto();
        } else {
            super.ejecutarOpcion(opcion); // delega 1-4 a menuCRUD.
        }
    }
/**
 * Asigna un producto existente a una categoría existente.
 * <p>
 * Este proceso incluye:
 * </p>
 * <ul>
 *     <li>Listar y validar categorías activas.</li>
 *     <li>Solicitar el ID de categoría y verificar su existencia y estado.</li>
 *     <li>Listar y validar productos activos.</li>
 *     <li>Solicitar el ID de producto y verificar su existencia y estado.</li>
 *     <li>Asignar el producto a la categoría mediante
 *         {@link Categoria#agregarProducto(Producto)}, lo cual gestiona la
 *         relación bidireccional y evita duplicados.</li>
 * </ul>
 *
 * <p>
 * El método captura cualquier {@link FoodStoreException} lanzada por la capa
 * de servicio y muestra el mensaje correspondiente sin interrumpir el flujo
 * del menú.
 * </p>
 */
    private void asignarProducto() {
        try {
            List<Categoria> categorias = service.listar();
            if (listaVacia(categorias, "No hay categorías disponibles.")) return;
            categorias.forEach(System.out::println);

            Long categoriaId = LectorConsola.leerLong("ID de categoría: ");
            Categoria categoria = service.buscarPorId(categoriaId);

            if (categoria == null || categoria.isEliminado()) {
                System.out.println("Error: categoría no válida.");
                return;
            }

            List<Producto> productos = productoService.listar();
            if (listaVacia(productos, "No hay productos disponibles.")) return;
            productos.forEach(System.out::println);

            Long productoId = LectorConsola.leerLong("ID de producto: ");
            Producto producto = productoService.buscarPorId(productoId);

            if (producto == null || producto.isEliminado()) {
                System.out.println("Error: producto no válido.");
                return;
            }

            categoria.agregarProducto(producto); // maneja relación bidireccional y duplicados
            System.out.println("Producto asignado correctamente.");

        } catch (FoodStoreException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    /**
     * Solicita el ID de la categoría a eliminar y delega la operación al servicio.
     */
    @Override
    protected void eliminar() {
      try{  
        List<Categoria> lista = service.listar();
        
        if (listaVacia(lista, "No hay categorías cargadas.")) return;
        
        lista.forEach(System.out::println);
        
        Long id = LectorConsola.leerLong("ID: ");
        
        service.eliminar(id);
        
        System.out.println("Categoría eliminada.");    } 
      catch (FoodStoreException e) {
        System.out.println("Error: " + e.getMessage());
        }
    }
}

