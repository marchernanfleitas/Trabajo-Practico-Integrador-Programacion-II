/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI;

import Utils.LectorConsola;

/**
 * Menú principal del sistema, encargado de centralizar la navegación hacia los
 * distintos menús CRUD de la aplicación.
 *
 * <p>Esta clase pertenece a la capa de presentación y actúa como punto de entrada
 * para el usuario, permitiendo acceder a:
 * <ul>
 *     <li>Menú de Categorías</li>
 *     <li>Menú de Productos</li>
 *     <li>Menú de Usuarios</li>
 *     <li>Menú de Pedidos</li>
 * </ul>
 *
 * <p>Su responsabilidad es únicamente mostrar opciones, leer la selección del usuario
 * y delegar la ejecución al menú correspondiente. No contiene lógica de negocio.
 *
 * @author Marcelo Hernan Fleitas
 * @version 1.0
 * @since 1.0
 */
public class MenuPrincipal {

    private final MenuCategoria menuCategoria;
    private final MenuProducto menuProducto;
    private final MenuUsuario menuUsuario;
    private final MenuPedido menuPedido;

    /**
     * Construye el menú principal recibiendo las instancias de los submenús
     * que serán invocados según la opción seleccionada.
     *
     * @param menuCategoria menú encargado de gestionar categorías.
     * @param menuProducto menú encargado de gestionar productos.
     * @param menuUsuario menú encargado de gestionar usuarios.
     * @param menuPedido menú encargado de gestionar pedidos.
     */
    public MenuPrincipal(
            MenuCategoria menuCategoria,
            MenuProducto menuProducto,
            MenuUsuario menuUsuario,
            MenuPedido menuPedido) {
        this.menuCategoria = menuCategoria;
        this.menuProducto = menuProducto;
        this.menuUsuario = menuUsuario;
        this.menuPedido = menuPedido;
    }

    /**
     * Inicia el ciclo principal del menú, mostrando las opciones disponibles
     * y permitiendo al usuario navegar hacia los distintos submenús.
     *
     * <p>Controla errores de entrada numérica y evita que el sistema falle ante
     * valores inválidos, manteniendo una experiencia de uso consistente.
     */
    public void mostrar() {
        int opcion = -1;
        while (opcion != 0) {
            portada();
            System.out.println("1. Categorias");
            System.out.println("2. Productos");
            System.out.println("3. Usuarios");
            System.out.println("4. Pedidos");
            System.out.println("0. Salir");
            System.out.print("Seleccione: ");

            try {
                opcion = Integer.parseInt(LectorConsola.leerString(""));

                switch (opcion) {
                    case 1 -> menuCategoria.ejecutar();
                    case 2 -> menuProducto.ejecutar();
                    case 3 -> menuUsuario.ejecutar();
                    case 4 -> menuPedido.ejecutar();
                    case 0 -> System.out.println("¡Hasta luego!");
                    default -> System.out.println("Opción inválida.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número válido.");
            }
        }
    }

    /**
     * Muestra el encabezado del sistema antes de desplegar las opciones del menú.
     */
    private void portada() {
        String portada = """
                ====================  FOODSTORE  ====================

                        Bienvenido al Sistema de Gestion de Pedidos 

                =====================================================
                """;

        System.out.println(portada);
    }
}
