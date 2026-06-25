/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI;

import Entities.Usuario;
import Enums.Rol;
import Exceptions.FoodStoreException;
import Services.UsuarioService;
import Utils.LectorConsola;
import java.util.List;


/**
 * Menú de gestión de usuarios dentro de la capa de presentación.
 *
 * <p>Extiende {@link MenuCRUD} e implementa las operaciones CRUD específicas
 * para la entidad {@link Usuario}. Este menú permite crear, listar, editar
 * y eliminar usuarios, delegando toda la lógica de negocio al
 * {@link UsuarioService}.
 *
 * <p>Responsabilidades del menú:
 * <ul>
 *     <li>Mostrar opciones del menú de usuarios.</li>
 *     <li>Leer datos desde consola mediante {@link LectorConsola}.</li>
 *     <li>Delegar validaciones y operaciones al servicio correspondiente.</li>
 *     <li>Mostrar resultados y mensajes al usuario.</li>
 * </ul>
 *
 * <p>Este menú NO realiza validaciones de negocio ni reglas de unicidad;
 * esas tareas pertenecen exclusivamente al servicio.
 *
 * @author Marcelo Hernan Fleitas
 * @version 1.0
 * @since 1.0
 */
public class MenuUsuario extends MenuCRUD {

    private final UsuarioService service;

    /**
     * Construye el menú de usuarios recibiendo la instancia del servicio
     * encargado de gestionar las operaciones CRUD de usuarios.
     *
     * @param service servicio encargado de gestionar usuarios.
     */
    public MenuUsuario(UsuarioService service) {
        this.service = service;
    }

    /**
     * Muestra las opciones disponibles del menú de usuarios.
     */
    @Override
    protected void mostrarOpciones() {
        System.out.println("\n=== USUARIOS ===");
        System.out.println("1. Listar");
        System.out.println("2. Crear");
        System.out.println("3. Editar");
        System.out.println("4. Eliminar");
        System.out.println("0. Volver");
    }

    /**
     * Lista todos los usuarios activos utilizando el servicio.
     * Si la lista está vacía, muestra un mensaje informativo.
     */
    @Override
    public void listar() {
        List<Usuario> lista = service.listar();
        if (listaVacia(lista, "No hay usuarios cargados.")) return;
        lista.forEach(System.out::println);
    }

    /**
     * Solicita los datos necesarios para crear un usuario y delega la
     * operación al servicio.
     *
     * <p>El flujo de creación incluye:
     * <ul>
     *     <li>Ingreso de nombre, apellido, mail, celular y contraseña.</li>
     *     <li>Selección del rol mediante {@link LectorConsola#leerEnum}.</li>
     * </ul>
     *
     * <p>Si algún dato es inválido, el servicio lanzará la excepción correspondiente.
     */
    @Override
    protected void crear() {
     try{
        String nombre = LectorConsola.leerString("Nombre: ");
        String apellido = LectorConsola.leerString("Apellido: ");
        String mail = LectorConsola.leerString("Mail: ");
        String celular = LectorConsola.leerString("Celular: ");
        Rol rol = LectorConsola.leerEnum("Rol:", Rol.class);
        String contrasenia = LectorConsola.leerString("Contrasenia: ");

        Usuario usuario = service.crear( nombre, apellido, mail,celular,rol,contrasenia);

        System.out.println("Usuario creado. ID: " + usuario.getId());
     } catch (FoodStoreException e) {
         System.out.println("Error: " + e.getMessage());
     }
    }

    /**
     * Edita un usuario existente solicitando nuevos valores opcionales.
     *
     * <p>Los campos vacíos (Enter) se interpretan como "sin cambios".
     * <p>El rol no puede modificarse desde este menú.
     *
     * <p>Las validaciones de unicidad de mail, formato y reglas de negocio
     * se delegan completamente al servicio.
     */
    @Override
    protected void editar() {
     try {   
        Long id = LectorConsola.leerLong("ID: ");

        String nombre = LectorConsola.leerString("Nuevo nombre (Enter para omitir): ");
        String apellido = LectorConsola.leerString("Nuevo apellido (Enter para omitir): ");
        String mail = LectorConsola.leerString("Nuevo mail (Enter para omitir): ");
        String celular = LectorConsola.leerString("Nuevo celular (Enter para omitir): ");
        String contrasenia = LectorConsola.leerString("Nueva Contrasenia (Enter para omitir): ");

        service.editar( 
                id,
                nombre.isBlank() ? null : nombre,
                apellido.isBlank() ? null : apellido,
                mail.isBlank() ? null : mail,
                celular.isBlank() ? null : celular,
                null, // no se permite modificar el rol desde este menú
                contrasenia.isBlank() ? null : contrasenia
                );

        System.out.println("Usuario modificado.");
            } catch (FoodStoreException e) {
        System.out.println("Error: " + e.getMessage());
        }
    }
    

    /**
     * Elimina lógicamente un usuario existente.
     */
    @Override
    protected void eliminar() {
     try{
        Long id = LectorConsola.leerLong("ID: ");
        service.eliminar(id);
        System.out.println("Usuario eliminado.");
        } catch (FoodStoreException e){
         System.out.println("Error: " + e.getMessage());
            }
    }
    
}
