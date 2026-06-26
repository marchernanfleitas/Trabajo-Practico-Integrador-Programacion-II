/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import Entities.Categoria;
import Entities.Producto;
import Exceptions.DatoDuplicadoException;
import Exceptions.EntidadEliminadaException;
import Exceptions.EntidadNoEncontradaException;
import Exceptions.FoodStoreException;
import Interfaces.CrudService;
import Utils.ServicioValidador;
import java.util.ArrayList;
import java.util.List;

/**
 * Servicio encargado de gestionar el ciclo de vida de las entidades {@link Producto}.
 *
 * <p>Implementa las operaciones CRUD definidas en {@link CrudService} y aplica
 * reglas de negocio específicas:
 * <ul>
 *     <li>Validación de nombre, precio y stock antes de crear o editar.</li>
 *     <li>Evita nombres duplicados entre productos activos.</li>
 *     <li>Normaliza textos para mantener consistencia.</li>
 *     <li>Impide operar sobre productos eliminados lógicamente.</li>
 * </ul>
 *
 * <p>Este servicio no interactúa con la capa de UI; únicamente gestiona lógica
 * de negocio y validaciones internas.
 *
 * @author Marcelo Hernan Fleitas
 * @version 1.0
 * @since 1.0
 */
public class ProductoService implements CrudService<Producto> {

    private final List<Producto> productos;

     private final PedidoService pedidoService;

    /**
     * Crea una nueva instancia del servicio.
     *
     * @param pedidoService servicio de pedidos para verificar dependencias
     *                      antes de eliminar un producto.
     */
    public ProductoService(PedidoService pedidoService) {
        this.productos = new ArrayList<>();
        this.pedidoService = pedidoService;
    }

    /**
     * Retorna una lista de todos los productos activos (no eliminados).
     *
     * @return lista de productos visibles para el sistema.
     */
    @Override
    public List<Producto> listar() {
        return productos.stream()
                .filter(u -> !u.isEliminado())
                .toList();
    }

    /**
     * Busca un producto por su identificador.
     *
     * @param id identificador único del producto.
     * @return el producto encontrado o {@code null} si no existe.
     */
    @Override
    public Producto buscarPorId(Long id) {
        for (Producto p : productos) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Crea un nuevo producto validando nombre, precio, stock y duplicados.
     *
     * @param nombre nombre del producto.
     * @param precio precio del producto.
     * @param descripcion descripción opcional.
     * @param stock cantidad disponible.
     * @param imagen URL o ruta de imagen.
     * @param disponible estado de disponibilidad.
     * @param categoria categoría asociada.
     *
     * @return el producto creado.
     *
     * @throws IllegalArgumentException si algún dato obligatorio es inválido.
     * @throws DatoDuplicadoException si ya existe un producto activo con el mismo nombre.
     */
    public Producto crear(String nombre, double precio, String descripcion, int stock, String imagen, boolean disponible, Categoria categoria) {

        ServicioValidador.validarNombre(nombre);
        ServicioValidador.validarPrecio(precio);
        ServicioValidador.validarStock(stock);

        nombre = ServicioValidador.normalizarTexto(nombre);

        validarNombreDuplicado(nombre);

        Producto producto = new Producto(nombre, precio, descripcion, stock, imagen, disponible, categoria);

        productos.add(producto);
        return producto;
      }      


    /**
     * Edita un producto existente validando su estado y evitando duplicados.
     *
     * @param id identificador del producto a editar.
     * @param nombre nuevo nombre (opcional).
     * @param precio nuevo precio (opcional).
     * @param descripcion nueva descripción (opcional).
     * @param stock nuevo stock (opcional).
     * @param imagen nueva imagen (opcional).
     * @param disponible nuevo estado de disponibilidad (opcional).
     * @param categoria nueva categoría asociada (opcional).
     *
     * @throws EntidadNoEncontradaException si el producto no existe.
     * @throws EntidadEliminadaException si el producto está eliminado.
     * @throws DatoDuplicadoException si el nuevo nombre ya está en uso por otro producto activo.
     */
    public void editar(Long id, String nombre, Double precio, String descripcion, Integer stock, String imagen, Boolean disponible, Categoria categoria) {
            Producto producto = validarProductoActivo(id);

        if (nombre != null && !nombre.isBlank()) {
            ServicioValidador.validarNombre(nombre);
            nombre = ServicioValidador.normalizarTexto(nombre);

            if (!producto.getNombre().equalsIgnoreCase(nombre)) {
                validarNombreDuplicado(nombre);
            }

            producto.setNombre(nombre);
        }

        if (precio != null) {
            ServicioValidador.validarPrecio(precio);
            producto.setPrecio(precio);
        }

        if (descripcion != null && !descripcion.isBlank()) {
            producto.setDescripcion(descripcion);
        }

        if (stock != null) {
            ServicioValidador.validarStock(stock);
            producto.setStock(stock);
        }

        if (imagen != null && !imagen.isBlank()) {
            producto.setImagen(imagen);
        }

        if (disponible != null) {
            producto.setDisponible(disponible);
        }

        if (categoria != null) {
            producto.setCategoria(categoria);
        } 
    }
    /**
     * Elimina lógicamente un producto.
     *
     * @param id identificador del producto a eliminar.
     *
     * @throws EntidadNoEncontradaException si no existe.
     * @throws EntidadEliminadaException si ya estaba eliminado.
     */
    @Override
    public void eliminar(Long id) {
        Producto producto = validarProductoActivo(id);
    boolean estaEnUso = pedidoService.listar().stream()         //no se permite eliminar productos que poseen un detalle pedido activo para evitar incosistencia de pedidos con productos elimminados=true
            .flatMap(p -> p.getDetalles().stream())
            .anyMatch(d -> !d.isEliminado()
                    && d.getProducto().getId().equals(producto.getId()));

    if (estaEnUso) {
        throw new FoodStoreException("No se puede eliminar el producto porque está incluido en un pedido activo.");
    }

    producto.setEliminado(true);

    }
      
      /**
     * Verifica que el producto exista y no esté eliminado.
     *
     * @param id identificador del producto.
     * @return el producto válido.
     *
     * @throws EntidadNoEncontradaException si no existe.
     * @throws EntidadEliminadaException si está eliminado.
     */
    private Producto validarProductoActivo(Long id) {
        Producto producto = buscarPorId(id);

        if (producto == null) {
            throw new EntidadNoEncontradaException("Producto inexistente.");
        }
        if (producto.isEliminado()) {
            throw new EntidadEliminadaException("El producto fue eliminado.");
        }

        return producto;
    }

    /**
     * Verifica que no exista otro producto activo con el mismo nombre.
     *
     * @param nombre nombre a validar.
     *
     * @throws DatoDuplicadoException si ya existe un producto activo con ese nombre.
     */
    private void validarNombreDuplicado(String nombre) {
        for (Producto p : productos) {
            if (!p.isEliminado() && p.getNombre().equalsIgnoreCase(nombre)) {
                throw new DatoDuplicadoException("Ya existe un producto con ese nombre.");
            }
        }
    }
}
