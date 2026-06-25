/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI;

import Exceptions.FoodStoreException;
import Utils.LectorConsola;
import java.util.List;
/**
 * Clase base abstracta que define la estructura general de un menú CRUD
 * dentro de la capa de presentación del sistema.
 *
 * <p>Implementa el patrón <strong>Template Method</strong>, delegando en las
 * subclases la implementación concreta de las operaciones CRUD mientras
 * mantiene un flujo de ejecución uniforme para todos los menús.
 *
 * <p>Esta clase se encarga de:
 * <ul>
 *     <li>Mostrar opciones del menú.</li>
 *     <li>Leer y validar la opción ingresada por el usuario.</li>
 *     <li>Ejecutar la operación correspondiente.</li>
 *     <li>Manejar excepciones de negocio ({@link FoodStoreException}).</li>
 * </ul>
 *
 * <p>Las subclases deben implementar los métodos abstractos para definir
 * el comportamiento específico de cada menú.
 *
 * @author Marcelo Hernan Fleitas
 * @version 1.0
 * @since 1.0
 */
public abstract class MenuCRUD {

    /**
     * Inicia el ciclo principal del menú, mostrando opciones y ejecutando
     * acciones hasta que el usuario seleccione la opción de salida (0).
     *
     * <p>Controla errores de entrada numérica y excepciones de negocio,
     * mostrando mensajes adecuados sin interrumpir el flujo del menú.
     */
    public void ejecutar() {
        int opcion = -1;
        while (opcion != 0) {
            mostrarOpciones();
            System.out.print("Seleccione: ");
            try {
                opcion = Integer.parseInt(LectorConsola.leerString(""));
                ejecutarOpcion(opcion);
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número válido.");
            } catch (FoodStoreException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    /**
     * Muestra las opciones disponibles del menú.
     * <p>Debe ser implementado por cada subclase para personalizar
     * la interfaz del menú.
     */
    protected abstract void mostrarOpciones();

    /**
     * Ejecuta la acción correspondiente a la opción seleccionada.
     *
     * <p>Las opciones por defecto son:
     * <ul>
     *     <li>1 → listar</li>
     *     <li>2 → crear</li>
     *     <li>3 → editar</li>
     *     <li>4 → eliminar</li>
     *     <li>0 → volver</li>
     * </ul>
     *
     * @param opcion número ingresado por el usuario.
     * @throws FoodStoreException si ocurre un error de negocio en alguna operación CRUD.
     */
    protected void ejecutarOpcion(int opcion) throws FoodStoreException {
        switch (opcion) {
            case 1 -> listar();
            case 2 -> crear();
            case 3 -> editar();
            case 4 -> eliminar();
            case 0 -> System.out.println("Volviendo...");
            default -> System.out.println("Opción inválida.");
        }
    }

    /**
     * Lista las entidades correspondientes al menú.
     *
     * @throws FoodStoreException si ocurre un error de negocio.
     */
    protected abstract void listar() throws FoodStoreException;

    /**
     * Verifica si una lista está vacía y muestra un mensaje si corresponde.
     *
     * @param lista lista a validar.
     * @param mensajeVacia mensaje a mostrar si la lista está vacía.
     * @return {@code true} si la lista está vacía, {@code false} en caso contrario.
     */
    protected static boolean listaVacia(List<?> lista, String mensajeVacia) {
        if (lista == null || lista.isEmpty()) {
            System.out.println(mensajeVacia);
            return true;
        }
        return false;
    }

    /**
     * Crea una nueva entidad.
     *
     * @throws FoodStoreException si ocurre un error de negocio.
     */
    protected abstract void crear() throws FoodStoreException;

    /**
     * Edita una entidad existente.
     *
     * @throws FoodStoreException si ocurre un error de negocio.
     */
    protected abstract void editar() throws FoodStoreException;

    /**
     * Elimina una entidad existente.
     *
     * @throws FoodStoreException si ocurre un error de negocio.
     */
    protected abstract void eliminar() throws FoodStoreException;
}
