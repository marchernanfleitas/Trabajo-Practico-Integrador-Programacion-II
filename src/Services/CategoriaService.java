/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import Entities.Categoria;
import Exceptions.CategoriaInvalidaException;
import Exceptions.DatoDuplicadoException;
import Exceptions.EntidadEliminadaException;
import Exceptions.EntidadNoEncontradaException;
import Interfaces.CrudService;
import Utils.ServicioValidador;
import java.util.ArrayList;
import java.util.List;

/**
 * Servicio encargado de gestionar el ciclo de vida de las entidades {@link Categoria}.
 *
 * <p>Implementa las operaciones CRUD definidas en {@link CrudService} y aplica
 * reglas de negocio específicas para garantizar la integridad del dominio:</p>
 *
 * <ul>
 *     <li>Evita nombres duplicados entre categorías activas.</li>
 *     <li>Normaliza y valida los textos ingresados antes de persistirlos.</li>
 *     <li>Impide operar sobre categorías eliminadas lógicamente.</li>
 *     <li>Prohíbe eliminar categorías que posean productos activos asociados.</li>
 * </ul>
 *
 * <p>Este servicio no interactúa con la capa de UI ni utiliza Scanner;
 * se limita exclusivamente a la lógica de negocio y validaciones.</p>
 *
 * @author Marcelo Hernan Fleitas
 * @version 1.0
 * @since 1.0
 */
public class CategoriaService implements CrudService<Categoria> {

    private final List<Categoria> categorias;

    /**
     * Crea una nueva instancia del servicio inicializando la colección interna.
     */
    public CategoriaService() {
        this.categorias = new ArrayList<>();
    }

    /**
     * Retorna una lista de todas las categorías activas (no eliminadas).
     *
     * @return lista de categorías visibles para el sistema.
     */
    @Override
    public List<Categoria> listar() {
        return categorias.stream()
                .filter(p -> !p.isEliminado())
                .toList();
    }

    /**
     * Busca una categoría por su Identificador.
     *
     * @param id identificador único de la categoría.
     * @return la categoría encontrada o {@code null} si no existe.
     */
    @Override
    public Categoria buscarPorId(Long id) {
        for (Categoria c : categorias) {
            if (c.getId().equals(id)) {
                return c;
            }
        }
        return null;
    }

    /**
     * Crea una nueva categoría validando nombre y duplicados para luego normalizar el texto ingresado.
     *
     * @param nombre nombre de la categoría.
     * @param descripcion descripción opcional.
     * @return la categoría creada.
     *
     * @throws IllegalArgumentException si el nombre está vacío.
     * @throws DatoDuplicadoException si ya existe una categoría activa con el mismo nombre.
     */
    public Categoria crear(String nombre, String descripcion) {

        ServicioValidador.validarTextoNoVacio(nombre);
        nombre = ServicioValidador.normalizarTexto(nombre);
        descripcion = ServicioValidador.normalizarTexto(descripcion);

        validarNombreDuplicado(nombre);

        Categoria categoria = new Categoria(nombre, descripcion);
        categorias.add(categoria);

        return categoria;
    }

    /**
     * Edita una categoría existente validando su estado y evitando duplicados.
     *
     * @param id identificador de la categoría a editar.
     * @param nombre nuevo nombre (opcional).
     * @param descripcion nueva descripción (opcional).
     *
     * @throws EntidadNoEncontradaException si la categoría no existe.
     * @throws EntidadEliminadaException si la categoría está eliminada.
     * @throws DatoDuplicadoException si el nuevo nombre ya está en uso por otra categoría activa.
     */
    public void editar(Long id, String nombre, String descripcion) {

        Categoria categoria = validarCategoriaActiva(id);

        if (nombre != null && !nombre.isBlank()) {
            ServicioValidador.validarNombre(nombre);
            nombre = ServicioValidador.normalizarTexto(nombre);

            if (!categoria.getNombre().equalsIgnoreCase(nombre)) {
                validarNombreDuplicado(nombre);
            }

            categoria.setNombre(nombre);
        }

        if (descripcion != null && !descripcion.isBlank()) {
            categoria.setDescripcion(
                    ServicioValidador.normalizarTexto(descripcion)
            );
        }
    }

    /**
     * Elimina lógicamente una categoría, siempre que no posea productos activos asociados.
     *
     * <p>La eliminación es de tipo <strong>soft delete</strong>, por lo que la categoría
     * no se elimina físicamente, sino que se marca como inactiva mediante el atributo
     * {@code eliminado}.</p>
     *
     * @param id identificador único de la categoría a eliminar.
     *
     * @throws EntidadNoEncontradaException si la categoría no existe.
     * @throws EntidadEliminadaException si la categoría ya estaba eliminada.
     * @throws CategoriaInvalidaException si la categoría posee productos activos y por lo tanto
     *         no puede eliminarse.
     */
        @Override
    public void eliminar(Long id) {
        Categoria categoria = validarCategoriaActiva(id);

        boolean tieneProductosActivos = categoria.getProductos().stream().anyMatch(p -> !p.isEliminado());

        if (tieneProductosActivos) {
            throw new CategoriaInvalidaException("No se puede eliminar la categoría porque tiene productos activos asociados.");
        }

        categoria.setEliminado(true);
    }
    /**
     * Verifica que la categoría exista y no esté eliminada.
     *
     * @param id identificador de la categoría.
     * @return la categoría válida.
     *
     * @throws EntidadNoEncontradaException si no existe.
     * @throws EntidadEliminadaException si está eliminada.
     */
    private Categoria validarCategoriaActiva(Long id) {
        Categoria categoria = buscarPorId(id);

        if (categoria == null) {
            throw new EntidadNoEncontradaException("Categoría inexistente.");
        }
        if (categoria.isEliminado()) {
            throw new EntidadEliminadaException("La categoría fue eliminada.");
        }

        return categoria;
    }

    /**
     * Verifica que no exista otra categoría activa con el mismo nombre.
     *
     * @param nombre nombre a validar.
     *
     * @throws DatoDuplicadoException si ya existe una categoría activa con ese nombre.
     */
    private void validarNombreDuplicado(String nombre) {
        for (Categoria c : categorias) {
            if (!c.isEliminado() && c.getNombre().equalsIgnoreCase(nombre)) {
                throw new DatoDuplicadoException("Ya existe una categoría con ese nombre.");
            }
        }
    }
}
