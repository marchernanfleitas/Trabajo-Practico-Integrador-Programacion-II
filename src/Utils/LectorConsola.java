/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.util.Scanner;

/**
 * Utilidad centralizada para la lectura de datos desde la consola.
 *
 * <p>Esta clase pertenece a la capa de Utils y encapsula todas las
 * operaciones de entrada del usuario, garantizando un manejo uniforme de
 * errores de formato y evitando duplicación de código en los menús.
 *
 * <p>Responsabilidades:
 * <ul>
 *     <li>Leer cadenas, números y valores booleanos desde consola.</li>
 *     <li>Validar formato de entrada (no reglas de negocio).</li>
 *     <li>Solicitar repetidamente un valor hasta que sea válido.</li>
 *     <li>Leer valores de enumeraciones de forma segura.</li>
 * </ul>
 *
 * <p>Esta clase NO realiza validaciones de negocio; solo controla el formato
 * de los datos ingresados por el usuario.
 *
 * @author Marcelo Hernan Fleitas
 * @version 1.0
 * @since 1.0
 */
public class LectorConsola {

    private static final Scanner SC = new Scanner(System.in);

    /**
     * Lee una cadena desde consola mostrando un mensaje previo.
     *
     * @param mensaje texto a mostrar antes de leer.
     * @return la cadena ingresada por el usuario.
     */
    public static String leerString(String mensaje) {
        System.out.print(mensaje);
        return SC.nextLine();
    }

    /**
     * Lee un valor {@code Long} desde consola, solicitándolo repetidamente
     * hasta que el usuario ingrese un número válido.
     *
     * @param mensaje texto a mostrar antes de leer.
     * @return el número ingresado.
     */
    public static Long leerLong(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Long.parseLong(SC.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un número válido.");
            }
        }
    }

    /**
     * Lee un valor {@code Double} desde consola, solicitándolo repetidamente
     * hasta que el usuario ingrese un número válido.
     *
     * @param mensaje texto a mostrar antes de leer.
     * @return el número ingresado.
     */
    public static Double leerDouble(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Double.parseDouble(SC.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un número válido.");
            }
        }
    }

    /**
     * Lee un valor entero desde consola, solicitándolo repetidamente
     * hasta que el usuario ingrese un número entero válido.
     *
     * @param mensaje texto a mostrar antes de leer.
     * @return el entero ingresado.
     */
    public static int leerInt(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(SC.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un número entero válido.");
            }
        }
    }

    /**
     * Lee un valor booleano desde consola utilizando variantes comunes
     * de afirmación y negación (S/N, sí/no, true/false).
     *
     * @param mensaje texto a mostrar antes de leer.
     * @return {@code true} si el usuario ingresa una afirmación,
     *         {@code false} si ingresa una negación.
     */
    public static boolean leerBoolean(String mensaje) {
        while (true) {
            System.out.print(mensaje + " (S/N): ");
            String entrada = SC.nextLine().trim().toLowerCase();

            switch (entrada) {
                case "s":
                case "si":
                case "sí":
                case "y":
                case "yes":
                case "true":
                    return true;

                case "n":
                case "no":
                case "false":
                    return false;
            }
            System.out.println("Entrada inválida. Responda S o N.");
        }
    }

    /**
     * Lee un valor de un tipo {@link Enum} mostrando todas las opciones
     * disponibles y solicitando repetidamente hasta que el usuario ingrese
     * una constante válida.
     *
     * @param mensaje mensaje a mostrar antes de listar las opciones.
     * @param enumClass clase del enum a leer.
     * @param <T> tipo del enum.
     * @return la constante seleccionada por el usuario.
     */
    public static <T extends Enum<T>> T leerEnum(String mensaje, Class<T> enumClass) {
        while (true) {
            System.out.println(mensaje);

            for (T constante : enumClass.getEnumConstants()) {
                System.out.println(" - " + constante.name());
            }

            System.out.print("Ingrese una opción: ");
            String entrada = SC.nextLine().trim().toUpperCase();

            try {
                return Enum.valueOf(enumClass, entrada);
            } catch (IllegalArgumentException e) {
                System.out.println("Opción inválida. Intente nuevamente.");
            }
        }
    }
}

