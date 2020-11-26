package controllers;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase que gestiona los controladores de la BBDD
 * 
 * @author jonatan.delafuente
 *
 */

public class BBddControllers {

	/**
	 * Conexión a la BBDD mySql cuyos parámetros de conexión están en un fichero
	 * properties
	 * 
	 * @return conexion
	 */

	// Conexión BBDD
	public Connection conexionBbdd() {

		Connection conexion = null;
		Properties propiedades = new Properties();
		InputStream entrada = null;
		try {
			entrada = new FileInputStream("datos.properties");
			propiedades.load(entrada);
			propiedades.getProperty("pass");
			conexion = DriverManager.getConnection(propiedades.getProperty("servidor"), propiedades.getProperty("user"),
					propiedades.getProperty("pass"));
		} catch (SQLException e) {
			System.out.println("Fallo en la conexión a la BBDD");
		} catch (FileNotFoundException e) {
			e.getLocalizedMessage();
		} catch (IOException e) {
			e.getLocalizedMessage();
		}
		return conexion;
	}

	/**
	 * Método que lee una bbdd mySQL y devuelve un arrayList con los ID de todos los
	 * empleados
	 * 
	 * @return arrayIds
	 */

	public ArrayList<Integer> leerBbddId() {

		ArrayList<Integer> arrayIds = new ArrayList<>();
		try {
			Connection conexion = conexionBbdd();
			if (conexion != null) {
				Statement consulta = conexion.createStatement();
				ResultSet registro = consulta.executeQuery("select ID from empleados");
				while (registro.next()) {
					int num = registro.getInt("ID");
					arrayIds.add(num);
				}
				conexion.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return arrayIds;
	}
}
