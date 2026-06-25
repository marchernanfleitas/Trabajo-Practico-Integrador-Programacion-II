/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

import Enums.Rol;
import Exceptions.UsuarioInvalidoException;
import java.util.ArrayList;
import java.util.List;


/**
 * Representa a un usuario del sistema, incluyendo su información personal,
 * credenciales, rol asignado y el historial de pedidos asociados.
 *
 * <p>Un usuario puede realizar múltiples pedidos, los cuales se registran
 * únicamente cuando son confirmados por {@link PedidoService}. La clase
 * garantiza la integridad de los datos mediante validaciones estrictas en
 * todos sus campos obligatorios.</p>
 *
 * <p>Extiende de {@link Base}, por lo que hereda el identificador único y
 * el estado de eliminación lógica.</p>
 *
 * @author Marcelo Hernan Fleitas
 * @version 1.0
 * @since 1.0
 */
public class Usuario extends Base {

    /**
     * Nombre del usuario.
     *
     * <p>No puede ser nulo ni estar vacío. Se valida al asignarse.</p>
     */
    private String nombre;

    /**
     * Apellido del usuario.
     *
     * <p>No puede ser nulo ni estar vacío. Se valida al asignarse.</p>
     */
    private String apellido;

    /**
     * Correo electrónico del usuario.
     *
     * <p>Debe ser un valor no vacío. La validación de formato puede
     * ampliarse según las reglas del negocio.</p>
     */
    private String mail;

    /**
     * Número de celular del usuario.
     *
     * <p>No puede ser nulo ni estar vacío. Representa el medio de contacto
     * principal.</p>
     */
    private String celular;

    /**
     * Rol asignado al usuario dentro del sistema.
     *
     * <p>No puede ser nulo. Determina los permisos y acciones disponibles
     * (por ejemplo: ADMIN, CLIENTE).</p>
     */
    private Rol rol;

    /**
     * Contraseña del usuario.
     *
     * <p>No puede ser nula ni vacía. Se almacena en texto plano solo a
     * efectos académicos; en un entorno real debería utilizarse hashing.</p>
     */
    private String contrasenia;

    /**
     * Lista de pedidos asociados al usuario.
     *
     * <p>Solo se agregan pedidos confirmados. La lista nunca es nula y se
     * devuelve una copia defensiva para evitar modificaciones externas.</p>
     */
    private final List<Pedido> pedidos;

    /**
     * Construye un usuario inicializando todos sus datos obligatorios.
     *
     * <p>Todos los campos se validan mediante sus respectivos setters para
     * garantizar consistencia y evitar estados inválidos.</p>
     *
     * @param nombre nombre del usuario
     * @param apellido apellido del usuario
     * @param mail correo electrónico
     * @param celular número de celular
     * @param rol rol asignado
     * @param contrasenia contraseña del usuario
     *
     * @throws UsuarioInvalidoException si algún dato obligatorio es inválido
     */
    public Usuario(String nombre, String apellido, String mail, String celular, Rol rol, String contrasenia) {
        super();
        this.setNombre(nombre);
        this.setApellido(apellido);
        this.setMail(mail);
        this.setCelular(celular);
        this.setContrasenia(contrasenia);
        this.setRol(rol);
        this.pedidos = new ArrayList<>();
    }
    /**
     * Nombre del usuario.
     *
     * <p>No puede ser nulo ni estar vacío. Se utiliza para identificar al usuario
     * dentro del sistema y en las operaciones de venta.</p>
     */
    public String getNombre() {
        return nombre;
    }
    /**
     * Apellido del usuario.
     *
     * <p>No puede ser nulo ni estar vacío. Se utiliza para identificar al usuario
     * dentro del sistema y en las operaciones de venta.</p>
     */
    public String getApellido() {
        return apellido;
    }
    /**
     * Mail del usuario.
     *
     * <p>No puede ser nulo ni estar vacío. Se utiliza para identificar al usuario
     * dentro del sistema y en las operaciones de venta.</p>
     */
    public String getMail() {
        return mail;
    }
    /**
     * Celular del usuario.
     *
     * <p>No puede ser nulo ni estar vacío. Se utiliza para identificar al usuario
     * dentro del sistema y en las operaciones de venta.</p>
     */
    public String getCelular() {
        return celular;
    }
    /**
     * Rol del usuario.
     *
     * <p>No puede ser nulo. Se utiliza para identificar al usuario
     * dentro del sistema y en las operaciones de venta.</p>
     */
    public Rol getRol() {
        return rol;
    }
    /**
     * Retorna una copia defensiva de la lista de pedidos asociados.
     *
     * <p>Esto evita que código externo modifique la colección interna,
     * preservando el encapsulamiento.</p>
     *
     * @return una nueva lista con los pedidos asociados
     */
    public List<Pedido> getPedidos() {
        return new ArrayList<>(pedidos);
    }
    /**
     * Contrasenia del usuario.
     *
     * <p>No puede ser nula ni estar vacía. Se utiliza para el login del usuario
     * dentro del sistema.</p>
     */
    public String getContrasenia() {
        return contrasenia;
    }
    /**
     * Asigna el nombre del usuario.
     *
     * @param nombre nombre no vacío
     * @throws UsuarioInvalidoException si es nulo o vacío
     */
    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new UsuarioInvalidoException("El nombre no puede estar vacío.");
        }
        this.nombre = nombre;
    }

    /**
     * Asigna el apellido del usuario.
     *
     * @param apellido apellido no vacío
     * @throws UsuarioInvalidoException si es nulo o vacío
     */
    public void setApellido(String apellido) {
        if (apellido == null || apellido.trim().isEmpty()) {
            throw new UsuarioInvalidoException("El apellido no puede estar vacío.");
        }
        this.apellido = apellido;
    }

    /**
     * Asigna el correo electrónico del usuario.
     *
     * @param mail correo no vacío
     * @throws UsuarioInvalidoException si es nulo o vacío
     */
    public void setMail(String mail) {
        if (mail == null || mail.trim().isEmpty()) {
            throw new UsuarioInvalidoException("El mail no puede estar vacío.");
        }
        this.mail = mail;
    }

    /**
     * Asigna el número de celular del usuario.
     *
     * @param celular número no vacío
     * @throws UsuarioInvalidoException si es nulo o vacío
     */
    public void setCelular(String celular) {
        if (celular == null || celular.trim().isEmpty()) {
            throw new UsuarioInvalidoException("El celular no puede estar vacío.");
        }
        this.celular = celular;
    }

    /**
     * Asigna la contraseña del usuario.
     *
     * @param contrasenia contraseña no vacía
     * @throws UsuarioInvalidoException si es nula o vacía
     */
    public void setContrasenia(String contrasenia) {
        if (contrasenia == null || contrasenia.trim().isEmpty()) {
            throw new UsuarioInvalidoException("La contraseña no puede estar vacía.");
        }
        this.contrasenia = contrasenia;
    }

    /**
     * Asigna el rol del usuario.
     *
     * @param rol rol no nulo
     * @throws UsuarioInvalidoException si el rol es nulo
     */
    public void setRol(Rol rol) {
        if (rol == null) {
            throw new UsuarioInvalidoException("El rol no puede ser nulo.");
        }
        this.rol = rol;
    }

    /**
     * Registra un pedido confirmado en la lista del usuario.
     *
     * <p>Este método solo debe ser invocado desde {@link PedidoService}
     * cuando un pedido pasa a estado confirmado. No se permiten valores
     * nulos.</p>
     *
     * @param pedido pedido confirmado a asociar
     * @throws UsuarioInvalidoException si el pedido es nulo
     */
    public void agregarPedido(Pedido pedido) {
        if (pedido == null) {
            throw new UsuarioInvalidoException("El pedido no puede ser nulo.");
        }
        this.pedidos.add(pedido);
    }

    /**
     * Genera una representación textual detallada del usuario.
     *
     * <p>Incluye datos personales, rol, cantidad de pedidos activos y el
     * total acumulado de los pedidos no eliminados.</p>
     *
     * @return cadena formateada con la información del usuario
     */
    @Override
    public String toString() {
        long pedidosActivos = pedidos.stream().filter(p -> !p.isEliminado()).count();
        double totalAcumulado = pedidos.stream().filter(p -> !p.isEliminado()).mapToDouble(Pedido::getTotal).sum();

        return String.format(
                "===========================================================%n" +
                "ID: %d | USUARIO: %s %s%n" +
                "Mail: %s | Celular: %s | Rol: %s%n" +
                "Pedidos activos: %d | Total acumulado: $%.2f%n" +
                "===========================================================%n",
                getId(), nombre, apellido,
                mail, celular, rol,
                pedidosActivos, totalAcumulado
        );
    }
}    
   