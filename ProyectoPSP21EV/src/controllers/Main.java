package controllers;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Semaphore;
import models.Hilo;

/**
 * Clase principal
 * 
 * @author jonatan.delafuente
 *
 */

public class Main {
	static final int NUMHILOS = 5;

	/**
	 * Método ejecutador de la aplicación
	 * 
	 * @param args
	 */

	public static void main(String[] args) {

		String opcion = "-1";
		Scanner teclado = new Scanner(System.in);
		Hilo miHilo;
		Semaphore semaforo = new Semaphore(1);
		int acumulativoRegistros;
		int totalRegistros = 0;
		long acumulativoTiempo;
		long totalTiempo = 0;
		BBddControllers retornarArrayIds = new BBddControllers();
		ArrayList<Integer> arrayIds;
		Main disparador = new Main();

		do {
			System.out.println(
					"Introduce:\n1 para lectura secuencial con un hilo\n2 para lectura con 5 hilos concurrentes\n0 para salir del programa");
			opcion = teclado.nextLine();
			switch (opcion) {
			// En el caso de que se quiera hacer un barrido
			case "1":
				disparador.leerConUnHilo();
				break;
			case "2":
				disparador.leerConVariosHilos();
				break;
			case "0":
				System.out.println("Fin del programa");
				break;
			default:
				System.out.println("Introduce una opcion válida!!\n");
			}

		} while (!opcion.equalsIgnoreCase("0"));
	}

	/**
	 * Método que lee los registros de una tabla de una BBDD mySQL y te muestra el
	 * total de registros y el total de tiempo que tarda un hilo en ejecutar la
	 * consulta
	 */

	public void leerConUnHilo() {

		Hilo miHilo;
		Semaphore semaforo = new Semaphore(1);
		int acumulativoRegistros;
		int totalRegistros = 0;
		long acumulativoTiempo;
		long totalTiempo = 0;
		BBddControllers retornarArrayIds = new BBddControllers();
		ArrayList<Integer> arrayIds;

		try {
			totalRegistros = 0;
			totalTiempo = 0;
			arrayIds = retornarArrayIds.leerBbddId();
			int rangoInicial = 0;
			int rangoFinal = arrayIds.size();
			miHilo = new Hilo(semaforo, rangoInicial, rangoFinal);
			System.out.println(miHilo.getName());
			miHilo.start();
			miHilo.join();
			// Accede a los totales de registros y tiempo de cada hilo y los acumula
			acumulativoRegistros = miHilo.getAcumulativoIngresos();
			totalRegistros = totalRegistros + acumulativoRegistros;
			acumulativoTiempo = miHilo.getTiempoTotal();
			totalTiempo = totalTiempo + acumulativoTiempo;
			System.out.println("La suma total de ingresos es: " + totalRegistros + " con un tiempo de ejecución de: "
					+ totalTiempo + " mili segundos\n");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método que lee los registros de una tabla de una BBDD mySQL y te muestra el
	 * total de registros y el total de tiempo que tardan varios hilos en ejecutar
	 * la consulta
	 */

	public void leerConVariosHilos() {

		Hilo miHilo;
		Semaphore semaforo = new Semaphore(1);
		int acumulativoRegistros;
		int totalRegistros = 0;
		long acumulativoTiempo;
		long totalTiempo = 0;
		BBddControllers retornarArrayIds = new BBddControllers();
		ArrayList<Integer> arrayIds;
		arrayIds = retornarArrayIds.leerBbddId();
		int cont = (arrayIds.size() / NUMHILOS);
		int rangoInicial = 0;
		int rangoFinal = 0;
		for (int i = 0; i < NUMHILOS; i++) {
			try {
				rangoInicial = (cont) * (i);
				rangoFinal = ((cont) * (i + 1));

				// Para coger el tramo total de registros si la división numRegistros / numHilos
				// no es exacta
				if (i == NUMHILOS - 1) {
					rangoFinal = arrayIds.size();
				}
				miHilo = new Hilo(semaforo, rangoInicial, rangoFinal);
				// System.out.println(miHilo.getName());
				miHilo.start();
				miHilo.join();
				acumulativoRegistros = miHilo.getAcumulativoIngresos();
				totalRegistros = totalRegistros + acumulativoRegistros;
				acumulativoTiempo = miHilo.getTiempoTotal();
				totalTiempo = totalTiempo + acumulativoTiempo;

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("La suma total de ingresos es: " + totalRegistros + " con un tiempo de ejecución de: "
				+ totalTiempo + " mili segundos\n");
	}
}
