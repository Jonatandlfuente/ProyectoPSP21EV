package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;

import controllers.BBddControllers;

/**
 * Clase Hilo
 * 
 * @author jonatan.delafuente
 *
 */

public class Hilo extends Thread {

	private int acumulativoIngresos;
	private long tiempoTotal;
	private Semaphore semaforo;
	private int rangoInicial;
	private int rangoFinal;

	/**
	 * Constructor de la Clase Hilo
	 * 
	 * @param semaforo
	 * @param rangoInicial
	 * @param rangoFinal
	 */

	public Hilo(Semaphore semaforo, int rangoInicial, int rangoFinal) {
		super();
		this.semaforo = semaforo;
		this.rangoInicial = rangoInicial;
		this.rangoFinal = rangoFinal;
	}

	/**
	 * Accedente del atributo AcumulativoIngresos
	 * 
	 * @return acumulativoIngresos
	 */

	public int getAcumulativoIngresos() {
		return acumulativoIngresos;
	}

	/**
	 * Mutador del atributo AcumulativoIngresos
	 * 
	 * @param acumulativoIngresos
	 */

	public void setAcumulativoIngresos(int acumulativoIngresos) {
		this.acumulativoIngresos = acumulativoIngresos;
	}

	/**
	 * Accedente del atributo tiempoTotal
	 * 
	 * @return tiempoTotal
	 */

	public long getTiempoTotal() {
		return tiempoTotal;
	}

	/**
	 * Mutador del atributo tiempoTotal
	 * 
	 * @param tiempoTotal
	 */

	public void setTiempoTotal(long tiempoTotal) {
		this.tiempoTotal = tiempoTotal;
	}

	/**
	 * Accedente del atributo semaforo
	 * 
	 * @return
	 */

	public Semaphore getSemaforo() {
		return semaforo;
	}

	/**
	 * Mutador del atributo semaforo
	 * 
	 * @param semaforo
	 */

	public void setSemaforo(Semaphore semaforo) {
		this.semaforo = semaforo;
	}

	/**
	 * Accedente del atributo rangoInicial
	 * 
	 * @return rangoInicial
	 */

	public int getRangoInicial() {
		return rangoInicial;
	}

	/**
	 * Mutador del atributo rangoInicial
	 * 
	 * @param rangoInicial
	 */

	public void setRangoInicial(int rangoInicial) {
		this.rangoInicial = rangoInicial;
	}

	/**
	 * Accedente del atributo rangoFinal
	 * 
	 * @return rangoFinal
	 */

	public int getRangoFinal() {
		return rangoFinal;
	}

	/**
	 * Mutador del atributo rangoFinal
	 * 
	 * @param rangoFinal
	 */

	public void setRangoFinal(int rangoFinal) {
		this.rangoFinal = rangoFinal;
	}

	/**
	 * Método de la lógica del hilo conecta con una BBDD mySQL y hace una lectura de
	 * todos los registros
	 */

	@Override
	public void run() {
		long tiempoInicial = 0;
		long tiempoEstimado = 0;
		ArrayList<Integer> arrayIds;
		BBddControllers retornarId = new BBddControllers();
		arrayIds = retornarId.leerBbddId();
		try {
			semaforo.acquire(1);
			for (int i = rangoInicial; i < rangoFinal; i++) {
				try {
					// Iniciamos el dato del reloj
					tiempoInicial = System.currentTimeMillis();
					Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd_psp_1",
							"DAM2020_PSP", "DAM2020_PSP");
					Statement consulta = conexion.createStatement();
					ResultSet registro = consulta
							.executeQuery("select INGRESOS from empleados where ID=" + arrayIds.get(i));
					if (registro.next()) {
						int num = registro.getInt("INGRESOS");
						// Recogemos el dato del reloj
						tiempoEstimado = System.currentTimeMillis() - tiempoInicial;
						// Acumulamos los totales de ingresos y tiempos en todo el bucle
						acumulativoIngresos = acumulativoIngresos + num;
						tiempoTotal = tiempoTotal + tiempoEstimado;
					}
					conexion.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		semaforo.release(1);
	}
}
