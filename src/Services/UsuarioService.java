/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import Entities.DetallePedido;
import Entities.Pedido;
import Entities.Usuario;
import Enums.Rol;
import Exceptions.DatoDuplicadoException;
import Exceptions.EntidadEliminadaException;
import Exceptions.EntidadNoEncontradaException;
import Interfaces.CrudService;
import Utils.ServicioValidador;
import java.util.ArrayList;
import java.util.List;
/**
 * Servicio encargado de gestionar el ciclo de vida de las entidades {@link Usuario}.
 *
 * <p>Implementa las operaciones CRUD definidas en {@link CrudService} y aplica
 * reglas de negocio específicas:
 * <ul>
 *     <li>Validación de nombre, apellido, mail, celular y contraseña.</li>
 *     <li>Evita mails duplicados entre usuarios activos.</li>
 *     <li>Normaliza textos para mantener consistencia.</li>
 *     <li>Impide operar sobre usuarios eliminados lógicamente.</li>
 * </ul>
 *
 * <p>Este servicio no interactúa con la capa de UI; únicamente gestiona lógica
 * de negocio y validaciones internas.
 *
 * @author Marcelo Hernan Fleitas
 * @version 1.0
 * @since 1.0
 */
public class UsuarioService implements CrudService<Usuario> {

    private final List<Usuario> usuarios;

    /**
     * Crea una nueva instancia del servicio inicializando la colección interna.
     */
    public UsuarioService() {
        this.usuarios = new ArrayList<>();
    }

    /**
     * Retorna una lista de todos los usuarios activos (no eliminados).
     *
     * @return lista de usuarios visibles para el sistema.
     */
    @Override
    public List<Usuario> listar() {
        return usuarios.stream()
                .filter(u -> !u.isEliminado())
                .toList();
    }

    /**
     * Busca un usuario por su identificador.
     *
     * @param id identificador único del usuario.
     * @return el usuario encontrado o {@code null} si no existe.
     */
    @Override
    public Usuario buscarPorId(Long id) {
        for (Usuario u : usuarios) {
            if (u.getId().equals(id)) {
                return u;
            }
        }
        return null;
    }

    /**
     * Crea un nuevo usuario validando todos los campos obligatorios y evitando duplicados.
     *
     * @param nombre nombre del usuario.
     * @param apellido apellido del usuario.
     * @param mail correo electrónico único.
     * @param celular número de celular.
     * @param rol rol asignado al usuario.
     * @param contrasenia contraseña del usuario.
     *
     * @return el usuario creado.
     *
     * @throws DatoDuplicadoException si ya existe un usuario activo con el mismo mail.
     * @throws UsuarioInvalidoException si los atributos al construir el usuario son inválidos(nombre,apellido,mail,celular
     *          contrasenia o rol).
     */
    public Usuario crear(String nombre, String apellido, String mail,String celular, Rol rol, String contrasenia) {

        ServicioValidador.validarNombre(nombre);
        ServicioValidador.validarApellido(apellido);
        ServicioValidador.validarMail(mail);
        ServicioValidador.validarCelular(celular);
        ServicioValidador.validarContrasenia(contrasenia);
        ServicioValidador.validarRol(rol);

        nombre = ServicioValidador.normalizarTexto(nombre);
        apellido = ServicioValidador.normalizarTexto(apellido);

        validarMailDuplicado(mail);

        Usuario usuario = new Usuario( nombre, apellido, mail, celular, rol, contrasenia);

        usuarios.add(usuario);
        return usuario;
    }

    /**
     * Edita un usuario existente validando su estado y evitando duplicados de mail.
     *
     * @param id identificador del usuario a editar.
     * @param nombre nuevo nombre (opcional).
     * @param apellido nuevo apellido (opcional).
     * @param mail nuevo mail (opcional).
     * @param celular nuevo celular (opcional).
     * @param rol nuevo rol (opcional).
     * @param contrasenia nueva contraseña (opcional).
     *
     * @throws EntidadNoEncontradaException si el usuario no existe.
     * @throws EntidadEliminadaException si el usuario está eliminado.
     * @throws DatoDuplicadoException si el nuevo mail ya está en uso por otro usuario activo.
     */
    public void editar(Long id, String nombre, String apellido, String mail,String celular, Rol rol, String contrasenia) {

        Usuario usuario = validarUsuarioActivo(id);

        if (nombre != null && !nombre.isBlank()) {
            ServicioValidador.validarNombre(nombre);
            usuario.setNombre(ServicioValidador.normalizarTexto(nombre));
        }

        if (apellido != null && !apellido.isBlank()) {
            ServicioValidador.validarApellido(apellido);
            usuario.setApellido(ServicioValidador.normalizarTexto(apellido));
        }

        if (mail != null && !mail.isBlank()) {
            ServicioValidador.validarMail(mail);

            if (!usuario.getMail().equalsIgnoreCase(mail)) {
                validarMailDuplicado(mail);
            }

            usuario.setMail(mail);
        }

        if (celular != null && !celular.isBlank()) {
            ServicioValidador.validarCelular(celular);
            usuario.setCelular(celular);
        }

        if (rol != null) {
            usuario.setRol(rol);
        }

        if (contrasenia != null && !contrasenia.isBlank()) {
            ServicioValidador.validarContrasenia(contrasenia);
            usuario.setContrasenia(contrasenia);
        }
    }

    /**
     * Elimina lógicamente un usuario y sus pedidos en cascada.
     *
     * @param id identificador del usuario a eliminar.
     *
     * @throws EntidadNoEncontradaException si no existe.
     * @throws EntidadEliminadaException si ya estaba eliminado.
     */
    @Override
    public void eliminar(Long id) {
        Usuario usuario = validarUsuarioActivo(id);

        for (Pedido pedido : usuario.getPedidos()) {    //eliminación lógica de todos los pedidos activos del usuario.
            if (!pedido.isEliminado()) {
                for (DetallePedido d : pedido.getDetalles()) {
                    if (!d.isEliminado()) {

                        d.getProducto().setStock(d.getProducto().getStock() + d.getCantidad());   // Devuelve el stock antes de eliminar
                    }
                    d.setEliminado(true);
                }
                pedido.setEliminado(true);
            }
        }

        usuario.setEliminado(true);
    }
    /**
     * Verifica que el usuario exista y no esté eliminado.
     *
     * @param id identificador del usuario.
     * @return el usuario válido.
     *
     * @throws EntidadNoEncontradaException si no existe.
     * @throws EntidadEliminadaException si está eliminado.
     */
    private Usuario validarUsuarioActivo(Long id) {
        Usuario usuario = buscarPorId(id);

        if (usuario == null) {
            throw new EntidadNoEncontradaException("Usuario inexistente.");
        }
        if (usuario.isEliminado()) {
            throw new EntidadEliminadaException("El usuario fue eliminado.");
        }

        return usuario;
    }

    /**
     * Verifica que no exista otro usuario activo con el mismo mail.
     *
     * @param mail correo electrónico a validar.
     *
     * @throws DatoDuplicadoException si ya existe un usuario activo con ese mail.
     */
    private void validarMailDuplicado(String mail) {
        for (Usuario u : usuarios) {
            if (!u.isEliminado() && u.getMail().equalsIgnoreCase(mail)) {
                throw new DatoDuplicadoException("Ya existe un usuario con ese mail.");
            }
        }
    }
}
