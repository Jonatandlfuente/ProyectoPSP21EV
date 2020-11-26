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

public class Hilos extends Thread {

	private int acumulativoIngresos;
	private long tiempoTotal;
	private Semaphore semaforo;
	private int rangoInicial;
	private int rangoFinal;

	public Hilos(Semaphore semaforo, int rangoInicial, int rangoFinal) {
		super();
		this.semaforo = semaforo;
		this.rangoInicial = rangoInicial;
		this.rangoFinal = rangoFinal;
	}

	public int getAcumulativoIngresos() {
		return acumulativoIngresos;
	}

	public void setAcumulativoIngresos(int acumulativoIngresos) {
		this.acumulativoIngresos = acumulativoIngresos;
	}

	public long getTiempoTotal() {
		return tiempoTotal;
	}

	public void setTiempoTotal(long tiempoTotal) {
		this.tiempoTotal = tiempoTotal;
	}

	public Semaphore getSemaforo() {
		return semaforo;
	}

	public void setSemaforo(Semaphore semaforo) {
		this.semaforo = semaforo;
	}

	public int getRangoInicial() {
		return rangoInicial;
	}

	public void setRangoInicial(int rangoInicial) {
		this.rangoInicial = rangoInicial;
	}

	public int getRangoFinal() {
		return rangoFinal;
	}

	public void setRangoFinal(int rangoFinal) {
		this.rangoFinal = rangoFinal;
	}

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
						acumulativoIngresos = acumulativoIngresos + num;
						// Recogemos el dato del reloj
						tiempoEstimado = System.currentTimeMillis() - tiempoInicial;
						// Calculamos el tiempo total en una variable acumulativa y el total de primos
						tiempoTotal = tiempoTotal + tiempoEstimado;
					}
					conexion.close();

				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		semaforo.release(1);
	}
}
