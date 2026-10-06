# UT1 - Manejo de Ficheros en Java: Filtrado de Directorios

**Autor:** Angelo Francisco Baeza Olave
**Módulo:** Programación (2º DAM)

## Descripción
Este proyecto demuestra diferentes técnicas en Java para filtrar archivos y directorios utilizando las interfaces `FilenameFilter` y `FileFilter`. El programa solicita al usuario la ruta de un directorio local y aplica distintos criterios de búsqueda y filtrado de forma modular.

## Estructura del Proyecto
El código está dividido en cuatro archivos independientes para facilitar su prueba y evaluación:

*   **FiltroForma1.java:** Utiliza una clase anónima clásica para filtrar archivos por su extensión, mostrando el nombre y el tamaño en bytes.
*   **FiltroForma2.java:** Implementa una expresión Lambda (Java 8+) para buscar ficheros `.java`, simplificando la implementación de la interfaz funcional.
*   **FiltroForma3.java:** Aplica un filtro combinado mediante Lambda para localizar archivos que empiecen por la palabra "alumno" y terminen en la extensión ".csv", mostrando su ruta absoluta.
*   **FiltroForma4.java:** Utiliza la interfaz `FileFilter` con una referencia a método (`File::isDirectory`) para listar exclusivamente los subdirectorios, etiquetándolos visualmente con `[DIR]`.

## Instrucciones de