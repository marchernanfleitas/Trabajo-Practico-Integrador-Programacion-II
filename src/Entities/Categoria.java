/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

import Exceptions.CategoriaInvalidaException;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa una categoría dentro del catálogo de productos del sistema.
 *
 * <p>Una {@code Categoria} agrupa múltiples instancias de {@link Producto},
 * permitiendo organizar, clasificar y consultar productos según su tipo o
 * características comunes. Cada categoría posee un nombre, una descripción
 * y una colección interna de productos asociados.</p>
 *
 * <h2>Responsabilidades principales</h2>
 * <ul>
 * <li>Administrar información descriptiva de la categoría.</li>
 * <li>Gestionar la relación uno-a-muchos con {@link Producto}.</li>
 * <li>Garantizar integridad en la asociación bidireccional con los productos.</li>
 * </ul>
 *
 * <h2>Relación con {@link Producto}</h2>
 * <p>La relación es bidireccional:
 * <ul>
 * <li>Una categoría puede contener múltiples productos.</li>
 * <li>Cada producto conoce la categoría a la que pertenece.</li>
 * </ul>
 * 
 * <p>El método {@link #agregarProducto(Producto)} asegura que ambas partes
 * mantengan la referencia correcta, evitando inconsistencias.</p>
 *
 * <h2>Invariantes</h2>
 * <ul>
 * <li>El nombre nunca puede ser nulo ni vacío.</li>
 * <li>La lista de productos nunca es nula.</li>
 * <li>La categoría siempre inicia con una lista vacía.</li>
 * </ul>
 *
 * @author Marcelo Hernan Fleitas
 * @version 1.0
 * @since 1.0
 */
public class Categoria extends Base{
    /**
     * Nombre identificador de la categoría.
     *
     * <p>Debe ser único dentro del contexto del catálogo y no puede ser nulo
     * ni contener solo espacios en blanco.</p>
     */    
    private String nombre;
    /**
     * Descripción opcional que detalla el propósito o características de la categoría.
     */    
    private String descripcion;
    /**
     * Lista de productos asociados a esta categoría.
     *
     * <p>Se inicializa siempre como una lista vacía y nunca se expone directamente.
     * El método {@link #getProductos()} retorna una copia defensiva para preservar
     * el encapsulamiento.</p>
     */    
    private final List <Producto> productos;  
    /**
     * Crea una nueva categoría con su nombre y descripción.
     *
     * <p>Inicializa la lista interna de productos y delega la validación del nombre
     * al método {@link #setNombre(String)}.</p>
     *
     * @param nombre nombre de la categoría, no puede ser nulo ni vacío
     * @param descripcion descripción opcional de la categoría
     * @throws IllegalArgumentException si el nombre es inválido
     */
    public Categoria(String nombre, String descripcion) {
        super();
        this.setNombre(nombre);
        this.setDescripcion(descripcion);
        this.productos = new ArrayList<>();
    }
    /**
     * Obtiene el nombre de la categoría.
     *
     * @return el nombre de la categoría
     */
    public String getNombre() {
        return nombre;
    }
    /**
     * Establece el nombre de la categoría.
     *
     * <p>Regla de negocio: el nombre no puede ser nulo ni estar vacío. Esta
     * validación garantiza consistencia en el catálogo.</p>
     *
     * @param nombre nuevo nombre de la categoría
     * @throws IllegalArgumentException si el nombre es nulo o vacío
     */
    public void setNombre(String nombre) {
        if (nombre==null || nombre.trim().isEmpty()){
            throw new CategoriaInvalidaException("El nombre no puede estar vacio");
        }  
        this.nombre= nombre;
        }
    /**
     * Obtiene la descripción de la categoría.
     *
     * @return la descripción de la categoría, o {@code null} si no fue definida
     */
    public String getDescripcion() {
        return descripcion;
    }
    /**
     * Establece la descripción de la categoría.
     *
     * @param descripcion texto descriptivo opcional
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    /**
     * Retorna una copia defensiva de la lista de productos asociados.
     *
     * <p>Esto evita que código externo modifique la colección interna,
     * preservando el encapsulamiento.</p>
     *
     * @return una nueva lista con los productos asociados
     */
    public List<Producto> getProductos() {
     return new ArrayList<>(productos);
    }
    /**
     * Agrega un producto a la categoría, manteniendo la relación bidireccional.
     *
     * <p>Reglas de negocio:</p>
     * <ul>
     * <li>No se permiten productos nulos.</li>
     * <li>No se agregan productos duplicados.</li>
     * <li>Se actualiza la categoría del producto mediante {@code p.setCategoria(this)}.</li>
     * </ul>
     *
     * @param p producto a agregar
     * @throws IllegalArgumentException si el producto es nulo
     */      
    public void agregarProducto(Producto p) {
        if (p == null) {
            throw new CategoriaInvalidaException("El producto no puede ser nulo.");
        }
        if(p.isEliminado()){
            throw new CategoriaInvalidaException("No se puede agregar un producto eliminado.");
        }
        if (!productos.contains(p)) {
            productos.add(p);
            p.setCategoria(this); // mantiene la relación bidireccional con Producto.
        }
    }
    /**
     * Devuelve una representación textual de la categoría.
     *
     * <p>Incluye ID, nombre, descripción y cantidad de productos asociados.
     * Es útil para depuración y reportes.</p>
     *
     * @return una cadena con la información relevante de la categoría
     */
    @Override
    public String toString() {
        long productosActivos = productos.stream()
                .filter(p -> !p.isEliminado())
                .count();
        return String.format(
                "Categoria #%d | %s | %s | Productos: %d",
                getId(),
                nombre,
                descripcion != null ? descripcion : "Sin descripción",
                productosActivos
        );
    }
}    


