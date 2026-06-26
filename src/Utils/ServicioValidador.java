/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import Enums.Rol;
import Exceptions.FoodStoreException;
import Exceptions.PrecioInvalidoException;
import Exceptions.StockInvalidoException;
import Exceptions.UsuarioInvalidoException;



/**
 * Clase utilitaria que centraliza todas las validaciones de datos utilizadas
 * en la capa de negocio del sistema.
 *
 * <p>Provee métodos estáticos para validar textos, nombres, apellidos, mails,
 * celulares, contraseñas, precios, stock y cantidades. Cada método lanza
 * {@link IllegalArgumentException} cuando los datos no cumplen con las reglas
 * establecidas.
 *
 * <p>Responsabilidades:
 * <ul>
 *     <li>Garantizar que los datos ingresados cumplan con los formatos requeridos.</li>
 *     <li>Evitar duplicación de lógica de validación en los servicios.</li>
 *     <li>Normalizar textos para mantener consistencia en la base de datos.</li>
 * </ul>
 *
 * <p>Esta clase NO interactúa con la UI ni con la consola.  
 * Su único propósito es validar y normalizar datos.
 *
 * @author Marcelo Hernan Fleitas
 * @version 1.0
 * @since 1.0
 */
public class ServicioValidador {

    /**
     * Valida que un texto no sea nulo ni esté vacío.
     *
     * @param texto texto a validar.
     * @throws IllegalArgumentException si el texto es nulo o está vacío.
     */
    public static void validarTextoNoVacio(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new FoodStoreException("El texto no puede estar vacío.");
        }
    }

    /**
     * Valida que un nombre sea válido:
     * <ul>
     *     <li>No vacío</li>
     *     <li>Solo letras y espacios</li>
     *     <li>Al menos 2 caracteres</li>
     * </ul>
     *
     * @param nombre nombre a validar.
     * @throws IllegalArgumentException si el nombre no cumple las reglas.
     */
    public static void validarNombre(String nombre) {
        validarTextoNoVacio(nombre);

        if (!nombre.matches("^[A-Za-zÁÉÍÓÚáéíóúñÑ ]+$")) {
            throw new FoodStoreException(" el nombre contiene caracteres no permitidos.");
        }

        if (nombre.trim().length() < 2) {
            throw new FoodStoreException("El nombre debe tener al menos 2 caracteres.");
        }
    }

    /**
     * Valida que un apellido sea válido:
     * <ul>
     *     <li>No vacío</li>
     *     <li>Solo letras y espacios</li>
     *     <li>Al menos 2 caracteres</li>
     * </ul>
     *
     * @param apellido apellido a validar.
     * @throws IllegalArgumentException si el apellido no cumple las reglas.
     */
    public static void validarApellido(String apellido) {
        validarTextoNoVacio(apellido);

        if (!apellido.matches("^[A-Za-zÁÉÍÓÚáéíóúñÑ ]+$")) {
            throw new FoodStoreException(" el apellido contiene caracteres no permitidos.");
        }

        if (apellido.trim().length() < 2) {
            throw new FoodStoreException(" el apellido ingresado no es válido.");
        }
    }

    /**
     * Valida el formato de un correo electrónico.
     *
     * @param mail correo a validar.
     * @throws IllegalArgumentException si el formato del mail es inválido.
     */
    public static void validarMail(String mail) {
        validarTextoNoVacio(mail);

        if (!mail.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+(\\.[A-Za-z]{2,})+$")) {
            throw new UsuarioInvalidoException("El mail ingresado no es válido.");
        }
    }

    /**
     * Valida un número de celular:
     * <ul>
     *     <li>No vacío</li>
     *     <li>No puede comenzar con 0</li>
     *     <li>Debe tener al menos 10 dígitos</li>
     *     <li>Solo números</li>
     * </ul>
     *
     * @param celular número de celular.
     * @throws IllegalArgumentException si el celular no cumple las reglas.
     */
    public static void validarCelular(String celular) {
        if (celular == null || celular.trim().isEmpty()) {
            throw new UsuarioInvalidoException("El celular no puede estar vacío.");
        }

        if (celular.startsWith("0")) {
            throw new UsuarioInvalidoException(" el número de celular no puede iniciar con 0.");
        }

        if (!celular.matches("^[0-9]{10,}$")) {
            throw new UsuarioInvalidoException("El celular debe contener solo números y tener al menos 10 dígitos.");
        }
    }

    /**
     * Valida que una contraseña no sea vacía y tenga al menos 6 caracteres.
     *
     * @param contrasenia contraseña a validar.
     * @throws IllegalArgumentException si la contraseña es inválida.
     */
    public static void validarContrasenia(String contrasenia) {
        if (contrasenia == null || contrasenia.trim().isEmpty()) {
            throw new UsuarioInvalidoException("La contrasenia no puede estar vacía.");
        }
        if (contrasenia.length() < 6) {
            throw new UsuarioInvalidoException("La contrasenia no puede tener menos de 6 carácteres.");
        }
    }
/**
 * Valida que un rol no sea nulo.
 *
 * @param rol rol a validar.
 * @throws UsuarioInvalidoException si el rol es nulo.
 */    
    public static void validarRol (Rol rol){
         if (rol == null) {
            throw new UsuarioInvalidoException("El rol no puede ser nulo.");
    }   
    }

    /**
     * Valida que un precio no sea negativo.
     *
     * @param precio precio a validar.
     * @throws IllegalArgumentException si el precio es negativo.
     */
    public static void validarPrecio(double precio) {
        if (precio <= 0) {
            throw new PrecioInvalidoException(" el precio no puede ser negativo.");
        }
    }

    /**
     * Valida que el stock no sea negativo.
     *
     * @param stock cantidad de stock.
     * @throws IllegalArgumentException si el stock es negativo.
     */
    public static void validarStock(int stock) {
        if (stock < 0) {
            throw new StockInvalidoException(" el stock no puede ser negativo.");
        }
    }

    /**
     * Valida que la cantidad sea mayor que cero.
     *
     * @param cantidad cantidad a validar.
     * @throws IllegalArgumentException si la cantidad es menor o igual a cero.
     */
    public static void validarCantidad(int cantidad) {
        if (cantidad <= 0) {
            throw new StockInvalidoException(" la cantidad debe ser mayor que cero.");
        }
    }

    /**
     * Normaliza un texto aplicando:
     * <ul>
     *     <li>Trim de espacios al inicio y fin.</li>
     *     <li>Reemplazo de múltiples espacios por uno solo.</li>
     *     <li>Conversión a minúsculas.</li>
     *     <li>Capitalización de cada palabra.</li>
     * </ul>
     *
     * @param texto texto a normalizar.
     * @return el texto normalizado, o cadena vacía si el texto es nulo.
     */
    public static String normalizarTexto(String texto) {

        if (texto == null) {
            return "";
        }

        texto = texto.trim();
        texto = texto.replaceAll("\\s+", " ");
        texto = texto.toLowerCase();

        StringBuilder resultado = new StringBuilder();

        for (String palabra : texto.split(" ")) {
            if (!palabra.isEmpty()) {
                resultado.append(Character.toUpperCase(palabra.charAt(0)))
                         .append(palabra.substring(1))
                         .append(" ");
            }
        }

        return resultado.toString().trim();
    }
}


