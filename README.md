#  Food Store – Sistema de Gestión de Pedidos

**Trabajo Práctico Integrador – Programación II**  
Tecnicatura Universitaria en Programación – UTN  
Java 21 · Aplicación de consola · Almacenamiento en memoria

---

## Descripción

Sistema de gestión de pedidos de comida desarrollado en Java 21 con POO. Permite administrar Categorías, Productos, Usuarios y Pedidos mediante un menú interactivo de consola, con operaciones CRUD completas, validaciones, manejo de excepciones personalizadas y arquitectura por capas.

---

## Cómo ejecutar

**Requisitos:** JDK 21 instalado.

```bash
# Clonar el repositorio
git clone <URL_DEL_REPOSITORIO>
cd FoodStore

# Compilar
javac -d out src/**/*.java

# Ejecutar
java -cp out Main
```

> También puede importarse y ejecutarse directamente desde un IDE como IntelliJ IDEA o Eclipse.

---

## Estructura del proyecto

```
src/
├── Main.java
├── Entities/          # Clases del dominio (Base, Categoria, Producto, Usuario, Pedido, DetallePedido)
├── Enums/             # Rol, Estado, FormaPago
├── Exceptions/        # Excepciones personalizadas (FoodStoreException y subclases)
├── Interfaces/        # Calculable, CrudService<T>
├── Services/          # Lógica de negocio (CategoriaService, ProductoService, UsuarioService, PedidoService)
└── UI/                # Menús de consola (MenuPrincipal, MenuCRUD, MenuCategoria, MenuProducto, MenuUsuario, MenuPedido)
└── Utils/             # LectorConsola, ServicioValidador
```

---

## Funcionalidades

| Módulo      | Operaciones disponibles                          |
|-------------|--------------------------------------------------|
| Categorías  | Listar, Crear, Editar, Eliminar (baja lógica)    |
| Productos   | Listar, Crear, Editar, Eliminar (baja lógica)    |
| Usuarios    | Listar, Crear, Editar, Eliminar (baja lógica)    |
| Pedidos     | Listar, Crear con detalles, Actualizar estado/pago, Eliminar (baja lógica) |

---

## Conceptos aplicados

- **POO:** herencia (`Base`), polimorfismo, abstracción (`MenuCRUD`, `Base`)
- **Interfaces:** `Calculable` (calcularTotal), `CrudService<T>` (operaciones CRUD genéricas)
- **Colecciones:** `List` / `ArrayList` para persistencia en memoria
- **Excepciones propias:** `CategoriaInvalidaException`, `DatoDuplicadoException`, `EntidadNoEncontradaException`, `PedidoInvalidoException`, `PrecioInvalidoException`, `StockInvalidoException`, `UsuarioInvalidoException`, `EntidadEliminadaException`
- **Soft delete:** baja lógica mediante `eliminado = true`
- **SRP:** cada clase y método tiene una única responsabilidad

---

## Entregables

- 📁 Código fuente completo (este repositorio)
- 📄 Documentación técnica en PDF: [Ver documento](./Trabajo_Práctico_Integrador_Programación_II.pdf)
- 🎥 Video demostrativo: [Ver video](<URL_DEL_VIDEO>)
