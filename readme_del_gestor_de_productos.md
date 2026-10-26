# UT1 - Gestor de Productos CSV (GestorProductosRA1)

**Autor:** Angelo Francisco Baeza 
**Módulo:** Programación (2º DAM)

## Descripción
Esta aplicación de consola en Java permite gestionar un catálogo de productos persistente almacenado en un archivo CSV. El sistema hace uso de las APIs `java.io` y `java.nio.file` para la gestión segura de ficheros, la creación automatizada del entorno y la manipulación de flujos de texto mediante `BufferedReader` y `BufferedWriter`.

## Estructura del Proyecto
*   **GestorProductosRA1.java:** Clase principal que contiene el menú interactivo, la lógica CRUD y la manipulación de ficheros.
*   **Producto.java:** Modelo de datos que define la estructura del producto (id, nombre, precio).
*   **ProductoInvalidoException.java:** Excepción personalizada para validar las reglas de negocio (ej. IDs duplicados, precios negativos).

## Funcionalidades Implementadas
1.  **Preparación de entorno:** Crea automáticamente los directorios `datos/` y `backups/`, inicializando el archivo `productos.csv` con su cabecera correspondiente.
2.  **Operaciones CRUD:**
    *   Listado de productos.
    *   Alta de productos con validación (no permite sobreescribir IDs y añade en modo *append*).
    *   Búsqueda de productos en memoria.
    *   Modificación y Eliminación (volcando los datos a una colección en RAM y reescribiendo el fichero completo).
3.  **Conversión de formatos:** Exportación a `.tsv` e importación desde `.tsv`.
4.  **Copias de seguridad:** Generación de backups con marca de tiempo (formato `YYYYMMDD_HHMMSS`) mediante `Files.copy`.
5.  **Robustez y Gestión de Errores:** Uso intensivo de `try-with-resources` para evitar bloqueos del sistema de archivos, y captura de excepciones como `NumberFormatException` para evitar caídas abruptas de la aplicación por errores de teclado del usuario.

## Documentación Técnica: Problema con los separadores
**¿Qué ocurre si un nombre contiene `;` (punto y coma) o tabulador?**
Al utilizar un método de conversión de texto plano (como `String.split(";")`), si el nombre de un producto contiene internamente el carácter separador, el sistema lo interpretará como una nueva columna de datos en lugar de texto. 

Por ejemplo, si un nombre es `Teclado; Gaming`, al hacer `split(";")` el array resultante tendrá 4 posiciones en lugar de las 3 esperadas (ID, "Teclado", " Gaming", Precio). Esto provocará una excepción `NumberFormatException` durante la lectura, ya que el programa intentará convertir la palabra " Gaming" en el valor numérico del precio. Para aplicaciones empresariales, este problema se soluciona encapsulando los campos de texto problemáticos entre comillas dobles (ej: `"Teclado; Gaming"`) y utilizando un *parser* CSV avanzado (como Apache Commons CSV) en lugar de un simple `split`.

## Instrucciones de Ejecución (NetBeans)
1. Abre el proyecto en tu entorno Apache NetBeans.
2. Abre la clase principal que contiene el método `main`.
3. Haz clic derecho en el editor de código y selecciona **Run File** (o pulsa **Shift + F6**).
4. Sigue las instrucciones del menú numérico en la terminal integrada.