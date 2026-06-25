/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

import Exceptions.EntidadEliminadaException;
import java.time.LocalDateTime;


/**
 * Representa la estructura fundamental que comparten todas las entidades del sistema.
 * 
 * <p>Esta clase abstracta define atributos y comportamientos comunes tales como:
 * <ul>
 * <li>Identificador único autogenerado</li>
 * <li>Control de estado lógico de eliminación</li>
 * <li>Registro automático de fecha y hora de creación</li>
 * </ul>
 * 
 * <p>Su propósito es garantizar consistencia, trazabilidad y reglas de negocio
 * compartidas entre todas las entidades que la extienden.
 *
 * <h2>Características principales</h2>
 * <ul>
 * <li><strong>ID inmutable:</strong> asignado automáticamente mediante un contador interno.</li>
 * <li><strong>Eliminación lógica:</strong> evita la reactivación de entidades marcadas como eliminadas.</li>
 * <li><strong>Timestamp de creación:</strong> registrado al momento de instanciar la entidad.</li>
 * </ul>
 *
 * <h2>Uso previsto</h2>
 * <p>Las clases hijas deben extender {@code Base} para heredar su comportamiento
 * y garantizar uniformidad en el manejo de entidades dentro del sistema.</p>
 *
 * @author Marcelo Hernan Fleitas
 * @version 1.0
 * @since 1.0
 */
    public abstract class Base {
    /**
     * Identificador único e inmutable asignado automáticamente a cada entidad.
     * 
     * <p>Se incrementa mediante un contador estático, garantizando unicidad
     * dentro del ciclo de vida de la aplicación.</p>
     */    
    private final Long id;
    /**
     * Indica si la entidad ha sido eliminada lógicamente.
     * 
     * <p>Una entidad eliminada no puede ser reactivada. Este atributo permite
     * preservar la integridad histórica sin borrar datos físicamente.</p>
     */
    private boolean eliminado;
    /**
     * Fecha y hora exacta en la que la entidad fue creada.
     * 
     * <p>Este valor es inmutable y se asigna automáticamente al instanciar
     * la entidad.</p>
     */    
    private final LocalDateTime createdAt;
    /**
     * Contador interno utilizado para generar IDs únicos.
     * 
     * <p>Se incrementa automáticamente cada vez que se crea una nueva instancia.</p>
     */    
    private static long contador=1;

    /**
     * Constructor protegido que inicializa una nueva entidad con:
     * <ul>
     * <li>ID autogenerado</li>
     * <li>Estado no eliminado</li>
     *  <li>Fecha y hora actual como timestamp de creación</li>
     * </ul>
     *
     * <p>Este constructor es utilizado exclusivamente por las clases hijas.</p>
     */
    protected Base() {
        this.id = contador++;
        this.eliminado = false;
        this.createdAt = LocalDateTime.now();
    }  
    /**
     * Obtiene el identificador único de la entidad.
     *
     * @return el ID autogenerado de la entidad
     */
    public Long getId() {
        return id;
    }
    /**
     * Indica si la entidad ha sido eliminada lógicamente.
     *
     * @return {@code true} si la entidad está eliminada; {@code false} en caso contrario
     */
    public boolean isEliminado() {
        return eliminado;
    }
    /**
     * Obtiene la fecha y hora de creación de la entidad.
     *
     * @return un {@link LocalDateTime} representando el timestamp de creación
     */    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    /**
     * Establece el estado de eliminación lógica de la entidad.
     *
     * <p>Regla de negocio: no es posible reactivar una entidad previamente eliminada.
     * Si se intenta cambiar el estado de {@code true} a {@code false}, se lanzará
     * una excepción.</p>
     *
     * @param eliminado nuevo estado de eliminación lógica
     * @throws EntidadEliminadaException si se intenta reactivar una entidad eliminada
     */
    public void setEliminado(boolean eliminado) throws EntidadEliminadaException {
    if (this.eliminado == true && eliminado == false) {
        throw new EntidadEliminadaException("No se puede reactivar una entidad eliminada");
    }
    this.eliminado = eliminado;
}
    /**
     * Representación textual de la entidad.
     *
     * <p>Las clases hijas deben sobrescribir este método para proporcionar
     * una salida clara, legible y adecuada para informes o depuración.</p>
     *
     * @return una cadena con la información relevante de la entidad
     */
    @Override
    public abstract String toString();  
}