/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

import Exceptions.CategoriaInvalidaException;
import Exceptions.FoodStoreException;
import Exceptions.PrecioInvalidoException;
import Exceptions.StockInvalidoException;


/**
 * Representa un producto dentro del sistema de FoodStore.
 *
 * <p>Incluye información esencial como nombre, precio, stock, disponibilidad,
 * descripción, imagen y la categoría a la que pertenece. La clase aplica
 * validaciones internas para garantizar la consistencia del dominio y evitar
 * estados inválidos.</p>
 *
 * <p>Reglas de negocio principales:</p>
 * <ul>
 *     <li>El nombre no puede ser nulo ni estar vacío.</li>
 *     <li>El precio debe ser mayor o igual a cero.</li>
 *     <li>El stock no puede ser negativo.</li>
 *     <li>Si el stock llega a cero, el producto se marca automáticamente como no disponible.</li>
 *     <li>No puede marcarse como disponible un producto sin stock.</li>
 *     <li>La categoría asociada no puede ser nula.</li>
 * </ul>
 *
 * <p>La disponibilidad del producto depende directamente del stock, pero puede
 * modificarse manualmente siempre que el stock sea mayor a cero.</p>
 *
 * @author Marcelo Hernan Fleitas
 * @version 1.0
 * @since 1.0
 */
public class Producto extends Base {
    /**
     * Nombre del producto.
     *
     * <p>No puede ser nulo ni estar vacío. Se utiliza para identificar el producto
     * dentro del sistema y en las operaciones de venta.</p>
     */
    private String nombre;

    /**
     * Precio unitario del producto.
     *
     * <p>Debe ser mayor o igual a cero. Un precio negativo genera una
     * {@link PrecioInvalidoException}.</p>
     */
    private double precio;

    /**
     * Descripción opcional del producto.
     *
     * <p>Permite detallar características adicionales, ingredientes, uso o
     * información relevante para el cliente.</p>
     */
    private String descripcion;

    /**
     * Cantidad de unidades disponibles en inventario.
     *
     * <p>No puede ser negativa. Si el stock llega a cero, el producto se marca
     * automáticamente como no disponible.</p>
     */
    private int stock;

    /**
     * Ruta o identificador de la imagen asociada al producto.
     *
     * <p>Puede representar un archivo local, una URL o un nombre de recurso.</p>
     */
    private String imagen;

    /**
     * Indica si el producto está disponible para la venta.
     *
     * <p>Un producto sin stock no puede marcarse como disponible. Este valor puede
     * cambiar automáticamente cuando el stock llega a cero.</p>
     */
    private boolean disponible;

    /**
     * Categoría a la que pertenece el producto.
     *
     * <p>No puede ser nula. Se utiliza para agrupar productos y aplicar reglas
     * de negocio relacionadas con la organización del catálogo.</p>
     */
    private Categoria categoria;

    /**
     * Construye un nuevo producto aplicando todas las validaciones de negocio.
     *
     * @param nombre nombre del producto; no puede ser nulo ni vacío.
     * @param precio precio unitario; debe ser mayor o igual a cero.
     * @param descripcion texto descriptivo opcional.
     * @param stock cantidad disponible; no puede ser negativa.
     * @param imagen ruta o identificador de imagen asociado al producto.
     * @param disponible indica si el producto está disponible para la venta.
     * @param categoria categoría a la que pertenece; no puede ser nula.
     *
     * @throws FoodStoreException si el nombre es inválido.
     * @throws PrecioInvalidoException si el precio es negativo.
     * @throws StockInvalidoException si el stock es negativo o si se intenta
     *         marcar como disponible un producto sin stock.
     * @throws CategoriaInvalidaException si la categoría es nula.
     */
    public Producto(String nombre, double precio, String descripcion, int stock,
                    String imagen, boolean disponible, Categoria categoria) {
        super();
        this.setNombre(nombre);
        this.setPrecio(precio);
        this.setDescripcion(descripcion);
        this.setStock(stock);
        this.setImagen(imagen);
        this.setDisponible(disponible);
        this.setCategoria(categoria);
    }

    /**
     * Obtiene el nombre del producto.
     *
     * @return nombre del producto.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del producto.
     *
     * @param nombre nombre a asignar; no puede ser nulo ni vacío.
     *
     * @throws FoodStoreException si el nombre es nulo o vacío.
     */
    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new FoodStoreException("El nombre no puede estar vacio");
        }
        this.nombre = nombre;
    }

    /**
     * Obtiene el precio del producto.
     *
     * @return precio actual.
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Establece el precio del producto.
     *
     * @param precio valor a asignar; debe ser mayor o igual a cero.
     *
     * @throws PrecioInvalidoException si el precio es negativo.
     */
    public void setPrecio(double precio) {
        if (precio <= 0) {
            throw new PrecioInvalidoException("El precio no puede ser negativo.");
        }
        this.precio = precio;
    }

    /**
     * Obtiene la descripción del producto.
     *
     * @return descripción o {@code null} si no fue definida.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción del producto.
     *
     * @param descripcion texto descriptivo opcional.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el stock disponible.
     *
     * @return cantidad de unidades en inventario.
     */
    public int getStock() {
        return stock;
    }

    /**
     * Establece el stock del producto.
     *
     * <p>Si el stock se establece en cero, el producto se marca automáticamente
     * como no disponible.</p>
     *
     * @param stock cantidad a asignar; no puede ser negativa.
     *
     * @throws StockInvalidoException si el stock es negativo.
     */
    public void setStock(int stock) {
        if (stock < 0) {
            throw new StockInvalidoException("El stock no puede ser negativo.");
        }
        this.stock = stock;

        if (stock == 0) {
            this.disponible = false;
        }
    }

    /**
     * Obtiene la imagen asociada al producto.
     *
     * @return ruta o identificador de imagen.
     */
    public String getImagen() {
        return imagen;
    }

    /**
     * Establece la imagen del producto.
     *
     * @param imagen ruta o identificador de imagen.
     */
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    /**
     * Indica si el producto está disponible para la venta.
     *
     * @return {@code true} si está disponible; {@code false} en caso contrario.
     */
    public boolean isDisponible() {
        return disponible;
    }

    /**
     * Modifica la disponibilidad del producto.
     *
     * <p>No puede marcarse como disponible un producto cuyo stock sea cero.</p>
     *
     * @param disponible nuevo estado de disponibilidad.
     *
     * @throws StockInvalidoException si se intenta habilitar un producto sin stock.
     */
    public void setDisponible(boolean disponible) {
        if (disponible && this.stock == 0) {
            throw new StockInvalidoException("No se puede marcar como disponible un producto sin stock.");
        }
        this.disponible = disponible;
    }

    /**
     * Obtiene la categoría asociada al producto.
     *
     * @return categoría del producto.
     */
    public Categoria getCategoria() {
        return categoria;
    }

    /**
     * Asocia el producto a una categoría.
     *
     * @param categoria categoría a asignar; no puede ser nula.
     *
     * @throws CategoriaInvalidaException si la categoría es nula.
     */
    public void setCategoria(Categoria categoria) {
        if (categoria == null) {
            throw new CategoriaInvalidaException("La categoría no puede ser nula.");
        }
        this.categoria = categoria;
    }

    /**
     * Devuelve una representación legible del producto.
     * 
     * <p>Incluye ID, nombre, precio, stock, disponibilidad y nombre de su categoria.
     * Es útil para depuración y reportes.</p>
     *
     * @return una cadena con la información relevante del producto
     */
    @Override
    public String toString() {
        return String.format(
            "Producto #%d | %s | $%.2f | Stock: %d | Disponible: %s | Categoria: %s",
            getId(),
            nombre,
            precio,
            stock,
            disponible ? "Si" : "No",
            categoria.getNombre()
        );
    }
}


