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
 * Menú de gestión de productos dentro de la capa de presentación.
 *
 * <p>Extiende {@link MenuCRUD} e implementa las operaciones CRUD específicas
 * para la entidad {@link Producto}. Este menú permite crear, listar, editar
 * y eliminar productos, delegando toda la lógica de negocio al
 * {@link ProductoService}.
 *
 * <p>Además, interactúa con {@link CategoriaService} para validar la categoría
 * asociada a cada producto.
 *
 * <p>Responsabilidades del menú:
 * <ul>
 *     <li>Mostrar opciones del menú de productos.</li>
 *     <li>Leer datos desde consola mediante {@link LectorConsola}.</li>
 *     <li>Delegar validaciones y operaciones al servicio correspondiente.</li>
 *     <li>Mostrar resultados y mensajes al usuario.</li>
 * </ul>
 *
 * <p>Este menú NO realiza validaciones de negocio ni cálculos; esas tareas
 * pertenecen exclusivamente a los servicios.
 *
 * @author Marcelo Hernan Fleitas
 * @version 1.0
 * @since 1.0
 */
public class MenuProducto extends MenuCRUD {

    private final ProductoService service;
    private final CategoriaService categoriaService;

    /**
     * Construye el menú de productos recibiendo las instancias de los servicios
     * necesarios para gestionar productos y categorías.
     *
     * @param service servicio encargado de gestionar productos.
     * @param categoriaService servicio encargado de gestionar categorías.
     */
    public MenuProducto( ProductoService service, CategoriaService categoriaService) {
        this.service = service;
        this.categoriaService = categoriaService;
    }

    /**
     * Muestra las opciones disponibles del menú de productos.
     */
    @Override
    protected void mostrarOpciones() {
        System.out.println("\n=== PRODUCTOS ===");
        System.out.println("1. Listar");
        System.out.println("2. Crear");
        System.out.println("3. Editar");
        System.out.println("4. Eliminar");
        System.out.println("0. Volver");
    }

    /**
     * Lista todos los productos activos utilizando el servicio.
     * Si la lista está vacía, muestra un mensaje informativo.
     */
    @Override
    public void listar() {
        List<Producto> lista = service.listar();
        if (listaVacia(lista, "No hay productos cargados.")) return;
        lista.forEach(System.out::println);
    }

    /**
     * Solicita los datos necesarios para crear un producto y delega la
     * operación al servicio.
     *
     * <p>El flujo de creación incluye:
     * <ul>
     *     <li>Ingreso de nombre, precio, descripción, stock e imagen.</li>
     *     <li>Selección de disponibilidad.</li>
     *     <li>Selección de categoría válida.</li>
     * </ul>
     *
     * <p>Si la categoría no existe o está eliminada, la creación se cancela.
     */
    @Override
    protected void crear() {
      try{
        String nombre = LectorConsola.leerString("Nombre: ");
        double precio = LectorConsola.leerDouble("Precio: ");
        String descripcion = LectorConsola.leerString("Descripción: ");
        int stock = LectorConsola.leerInt("Stock: ");
        String imagen = LectorConsola.leerString("URL de imagen: ");
        boolean disponible = LectorConsola.leerBoolean("¿Disponible? (s/n): ");

        Long idCategoria = LectorConsola.leerLong("ID de categoría: ");
        Categoria categoria = categoriaService.buscarPorId(idCategoria);

        if (categoria == null || categoria.isEliminado()) {
            System.out.println("Categoría inexistente.");
            return;
        }

        Producto producto = service.crear(nombre, precio,descripcion,stock,imagen,disponible,categoria);

        System.out.println("Producto creado. ID: " + producto.getId());    } 
      catch (FoodStoreException e) {
        System.out.println("Error: " + e.getMessage());
    }
    }

    /**
     * Edita un producto existente solicitando nuevos valores opcionales.
     *
     * <p>Los campos vacíos (Enter) se interpretan como "sin cambios".
     *
     * <p>Los valores ingresados se delegan al servicio, que se encarga de
     * validar y aplicar las modificaciones correspondientes.
     */
    @Override
    protected void editar() {
      try{
        Long id = LectorConsola.leerLong("ID: ");

        String nombre = LectorConsola.leerString("Nuevo nombre (Enter para omitir): ");
        String precioStr = LectorConsola.leerString("Nuevo precio (Enter para omitir): ");
        String descripcion = LectorConsola.leerString("Nueva descripción (Enter para omitir): ");
        String stockStr = LectorConsola.leerString("Nuevo stock (Enter para omitir): ");
        String imagen = LectorConsola.leerString("Nueva URL de imagen (Enter para omitir): ");

        Double precio = precioStr.isBlank() ? null : Double.parseDouble(precioStr);
        Integer stock = stockStr.isBlank() ? null : Integer.parseInt(stockStr);
        
        service.editar( 
                id,
                nombre.isBlank() ? null : nombre,
                precio,
                descripcion.isBlank() ? null : descripcion,
                stock,
                imagen.isBlank() ? null : imagen,
                null,   // disponible no se permite edición desde este menú
                null    // categoría no se edita desde este menú solo de Menu Categoria.
            );
        System.out.println("Producto modificado.");    } 
      catch (FoodStoreException e) {
        System.out.println("Error: " + e.getMessage());
    }
    }

    /**
     * Elimina lógicamente un producto existente.
     */
    @Override
    protected void eliminar() {
      try{
        Long id = LectorConsola.leerLong("ID: ");
        service.eliminar(id);
        System.out.println("Producto eliminado.");    } 
      catch (FoodStoreException e) {
        System.out.println("Error: " + e.getMessage());
    }
    }
}
