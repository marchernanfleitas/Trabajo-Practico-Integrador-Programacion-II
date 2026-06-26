/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Enums;
/**
 * Representa los distintos roles que puede tener un {@link Usuario} dentro del sistema.
 *
 * <p>Los roles determinan los permisos, responsabilidades y nivel de acceso
 * que posee cada usuario. Este enum permite controlar la lógica de autorización
 * y mantener un modelo de seguridad claro y consistente.</p>
 *
 * <h2>Descripción de los roles</h2>
 * <ul>
 *   <li><strong>ADMIN:</strong> posee acceso total al sistema, incluyendo gestión de usuarios, productos y pedidos.</li>
 *   <li><strong>CLIENTE:</strong> usuario estándar que puede realizar pedidos y consultar su historial.</li>
 * </ul>
 *
 * <p>El uso de un enum evita errores de tipeo y asegura que los roles válidos
 * estén claramente definidos.</p>
 *
 * @author Marcelo Hernan Fleitas
 * @version 1.0
 * @since 1.0
 */
public enum Rol {
    /** Usuario con permisos administrativos completos. */    
    ADMIN,
    
    /** Usuario estándar que realiza pedidos. */    
    USUARIO   
}