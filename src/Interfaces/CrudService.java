/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import java.util.List;

/**
 * Interfaz genérica que define las operaciones básicas de un servicio CRUD
 * (Crear, Leer, Actualizar, Eliminar) aplicable a cualquier tipo de entidad.
 *
 * <p>Las implementaciones de esta interfaz deben encargarse de gestionar
 * la lógica de negocio correspondiente a cada entidad, así como las
 * validaciones necesarias antes de realizar las operaciones.
 *
 * @param <T> tipo de entidad que manejará el servicio.
 *
 * @author Marcelo Hernan Fleitas
 * @version 1.0
 * @since 1.0
 */
public interface CrudService<T> {

    /**
     * Retorna una lista de todas las entidades activas o visibles según
     * las reglas de negocio de la implementación.
     *
     * @return lista de entidades.
     */
    List<T> listar();

    /**
     * Busca una entidad por su identificador único.
     *
     * @param id identificador de la entidad.
     * @return la entidad encontrada o {@code null} si no existe.
     */
    T buscarPorId(Long id);

    /**
     * Elimina una entidad según la estrategia definida por la implementación.
     * Se utiliza una eliminación lógica en las distintas entidades para facilitar auditorias.
     *
     * @param id identificador de la entidad a eliminar.
     */
    void eliminar(Long id);
}
