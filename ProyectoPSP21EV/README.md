# PROYECTO 1ºEVALUACION PSP PARTE 2

## DESCRIPCIÓN

En el segundo proyecto llamado **ProyectoPSP21EV** se trata de una aplicación en la que el usuario podra elegir entre hacer una lectura secuencial de los registros de una bbdd o una lectura de 5 hilos concurrentes. El programa hará una suma total del campo ingresos, sumando de forma secuencial cada registro de ingresos de la bbdd y lo mostrará por consola. Del mismo modo calculará el tiempo de ejecución de la operación en cada caso mostrándola del mismo modo.

Los parámetros de conexión estarán contenidos en un archivo properties dentro de cada proyecto

## REQUISITOS

+ Los jar dentro de los proyectos están compilados con una JDK javaSe-v1.8, el ide utilizado para realizar el proyecto es eclipse
+ Se deberá tener creada una bbdd mySQL cuyos parámetros de conexión en un servidor local serán:

  + databaseName=bbdd_psp_1
  + user=DAM2020_PSP
  + pass=DAM2020_PSP
  
  siendo importante distinguir mayúsculas de minúsculas
  
+ La bbdd deberá contener la tabla empleados y deberá estar compuesta por un campo ID de tipo int autoincremental, un campo EMAIL de tipo varchar(100) y un campo INGRESOS int

## PASOS PARA CLONAR EL PROYECTO

1. Descargar la consola de git desde la siguiente url: (https://git-scm.com/downloads), y la abrimos.

2. Escribir en la consola:
 
git clone https://github.com/Jonatandlfuente/ProyectoPSP1EV.git

Con eso clonará el repositorio en una carpeta local


## EJECUCIÓN Y DEPENDENCIAS

1. Si ejecutamos desde un ide:

   1.  Para ejecutar cada uno de los proyectos se deberá ejecutar la clase **Main** que se encuentra en el paquete controllers en cada proyecto. Si se utiliza un gestor de dependencias se necesitará el archivo **MySQL Connector/J** que se podrá descargar en el enlace (https://mvnrepository.com/artifact/mysql/mysql-connector-java.) O bien, si no usamos un gestor de dependencias, el archivo se encontrará físicamente en la carpeta **lib** de cada uno de los proyectos. 

   2. Bastaría con enlazarlo. En el caso de Eclipse, nos iríamos al proyecto, botón derecho Properties -> Java build path, ahí seleccionamos el conector que tenemos y pulsamos remove para eliminarlo. Para insertarlo en la ruta correcta pulsamos Add External JARs.. y dentro de la carpeta lib seleccionamos el archivo conector, para enlazar el ide con la bbdd mySql.

2. Para ejecutar el .jar

    * Desde Windows. Abrimos la consola win+R y escribimos cmd, nos vamos a la carpeta raiz donde se encuentra el jar dentro de cada proyecto y escribimos "java -jar ProyectoPSP21EV.jar" sin las comillas.

    * Desde Linux. Abra un símbolo del sistema con CTRL + ALT + T. Vaya a su directorio de archivos ".jar". Si su versión de Ubuntu lo admite, debería poder hacer clic derecho en el directorio de su archivo ".jar" y hacer clic en "Abrir en la Terminal", por último escribir como en el caso de windows "java -jar ProyectoPSP21EV.jar" sin las comillas.


## APUNTES FINALES

Son un par de sencillos programas para poder tener un primer contacto con un ejemplo de funcionamiento de hilos muy sencillo e intuitivo.
