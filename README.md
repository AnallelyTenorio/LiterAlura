# LiterAlura

## Descripción
En este proyecto pongo en práctica mis conocimientos adquiridos en mi curso de Spring Boot que forma parte de mi formación como Backend en el programa Oracle Next Education by Alura Latam. Esta es una aplicación de consola que permite gestionar una biblioteca digital. Puedes buscar libros en la API de Gutendex, mostrar los libros guardados, listar los autores, ver autores vivos en un año determinado y filtrar libros guardados por idioma.

## Características
- **Buscar Libro**: Permite buscar un libro por su nombre y muestra información detallada del libro encontrado.
- **Mostrar libros guardados**: Muestra una lista de todos los libros almacenados en la base de datos.
- **Mostrar lista de autores**: Muestra una lista de todos los autores almacenados en la base de datos.
- **Mostrar autores vivos en un año determinado**: Muestra los autores que estaban vivos en el año especificado.
- **Mostrar libros por idioma**: Filtra y muestra los libros almacenados según el idioma especificado.

## Requisitos
- Java 17 o superior
- Maven
- PostgressSQL 16 o superior.
-   Crear base de datos:
    - Nombra tu base de datos
    - Define un usuario y contraseña
- Configurar variables de entorno (Para terminal bash en MAC utilizar el comando export)
    ```bash
     export DB_HOST= localhost
     export DB_NAME= Nombre de tu BD
     export DB-USER= Tu usuario
     export DB_PASSWORD= Tu contraseña
     ```

## Instalación
1. Clona este repositorio en tu máquina local.
   ```bash
   git clone https://github.com/AnallelyTenorio/LiterAlura
   ```
2. Compilar el proyecto usando maven
    ```bash
    cd /literalura
    ```
    ```bash
    mvn compile
    ```
    ```bash
    mvn package -DskipTests
    ```

3. Ejecutar la aplicacion
   ```bash
   cd target
   ```
   ```bash
   java -jar literalura-0.0.1-SNAPSHOT.jar
   ```
## Uso
Sigue las instrucciones del menú para interactuar con la aplicación. Aquí hay un ejemplo de cómo se ve el menú principal:
```bash
******************************************************
Elige la opción deseada

1- Buscar Libro

2- Mostrar libros guardados

3- Mostrar lista de autores

4- Mostrar autores vivos en un año determinado

5- Mostrar libros por idioma

0- Salir
******************************************************
```

## Funciones Detalladas

**Buscar Libro**

Solicita el nombre del libro que deseas buscar, muestra la información del libro y guarda el libro y su autor en la base de datos si no están ya almacenados.

**Mostrar libros guardados**

Muestra una lista de todos los libros que están almacenados en la base de datos, incluyendo título, autor, idioma y número de descargas.

**Mostrar lista de autores**

Muestra una lista de todos los autores que están almacenados en la base de datos, incluyendo nombre, año de nacimiento y año de muerte. También muestra los libros asociados a cada autor.

**Mostrar autores vivos en un año determinado**

Solicita un año y muestra una lista de autores que estaban vivos en ese año.

**Mostrar libros por idioma**

Solicita una clave de idioma (por ejemplo, en para inglés, es para español, fr para francés) y muestra una lista de libros almacenados en ese idioma.
