Gestor de Alumnos en Fichero de Texto (PE Ejercicio 08)
Aplicación de consola en Java para la gestión persistente de un registro de alumnos utilizando almacenamiento en ficheros de texto. Este proyecto implementa la API moderna Java NIO.2 (java.nio.file.Path, Files) para garantizar operaciones seguras, eficientes y portables en cualquier sistema operativo.

Características Principales
El menú interactivo permite realizar cuatro operaciones fundamentales aplicadas a ficheros secuenciales:

1. Añadir Alumno (Alta): Guarda nuevos registros al final del fichero (StandardOpenOption.APPEND) sin sobreescribir los datos existentes, empleando Files.writeString(). Crea automáticamente el directorio y el archivo si no existen.

2. Mostrar Lista: Carga y muestra todos los registros utilizando Files.readAllLines(). Incluye validaciones para informar al usuario si el fichero está vacío o no existe.

3. Buscar Alumno: Implementa una búsqueda avanzada insensible a mayúsculas/minúsculas (case-insensitive). Utiliza Files.lines() con Streams y try-with-resources para leer el archivo de forma perezosa (lazy), optimizando el uso de memoria en archivos grandes.

4. Contar Registros: Calcula el total de alumnos registrados analizando el tamaño de la lista recuperada del fichero.

Aspectos Técnicos Destacados
Rutas Portables: Uso de Paths.get() para evitar la codificación estática (hardcoding) de separadores de directorios, asegurando compatibilidad nativa entre Windows, Linux y macOS.

Gestión de Excepciones: Bloques try-catch y try-with-resources para prevenir fugas de memoria y capturar errores específicos (IOException, NoSuchFileException), proporcionando retroalimentación clara al usuario sin que la aplicación se cierre abruptamente.

Codificación Segura: Lectura y escritura forzada a StandardCharsets.UTF_8 para evitar corrupción de caracteres especiales y tildes.

Instrucciones de Ejecución
Requisitos: Java 14 o superior (el código utiliza text blocks """ y sintaxis moderna).

Ejecución: Ejecuta la clase principal PE_Ejercicio08_Angelo_Baeza.java desde tu IDE (NetBeans, Eclipse, IntelliJ) o terminal.

Archivos de prueba: Al iniciar, el programa creará automáticamente la carpeta datos/ y el archivo alumnos.txt en el directorio de trabajo actual. Se incluye un archivo de prueba pre-rellenado en el repositorio para evaluar rápidamente las funciones de búsqueda y conteo
