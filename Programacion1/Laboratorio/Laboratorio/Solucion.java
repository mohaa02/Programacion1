package Laboratorio;
import java.util.Scanner;
public class Solucion {

	final static Scanner TECLADO = new Scanner(System.in);

	public static void main(String[] args) {
		int[][] asientos = { { 1, 2, 3, 4, 5 }, { 9, 0, 0, 0, 4 }, { 5, 0, 0, 0, 0 }, { 0, 8, 0, 0, 0 } };
		//int[][] asientos={{1,2,3,4,5}, {9,0,0,0,6}, {7,0,0,0,0}, {0,8,0,0,0}};
		// COMPLETA EL MAIN
		int [][] asientosCOV = crearAsientosCovid(asientos);

		int numAsientosOcupados = contarValoresNoCero(asientos);
		int numAsientosCOVID = obtenNumAsientCovid(asientosCOV);
		
		System.out.println("El n�mero de asientos ocupados es "+numAsientosOcupados+" y el n�mero de asientos posibles covid es "+numAsientosCOVID+" El autob�s inicial es: ");
		mostrarAsientos(asientos);
		if (numAsientosOcupados<numAsientosCOVID) {
			System.out.println("Se puede aplicar la normativa COVID.\r\n El autob�s COVID inicial es:");
			mostrarAsientos(asientosCOV);
			System.out.println("El autobus COVID final es:");
			asignarAsientosFijos(asientos, asientosCOV);
			mostrarAsientos(asientosCOV);
			asignarAsientosNoFijos(asientos, asientosCOV);
			mostrarAsientos(asientosCOV);
		}

	}

	// M�TODOS A DEFINIR
	
	/*   Asigna a los clientes en buenas posiciones del autob�s inicial a las mismas posiciones en el autob�s COVID      */
	
	static void asignarAsientosFijos(int[][] asientos, int[][] asientosCOV) {
		// c�digo
		for(int i = 0; i<asientosCOV.length; i++) {
			for (int j = 0; j< asientosCOV[i].length; j++) {
				if (asientosCOV[i][j]!=-1) {
					asientosCOV[i][j] = asientos[i][j];
				}
			}
		}
	}
	
	/*	Asigna a los clientes en malas posiciones del autob�s inicial a posiciones No covid del autob�s COVID*/
	
	static void asignarAsientosNoFijos(int[][] asientos, int[][] asientosCOV) {
		// c�digo
		for(int i = 0; i<asientos.length; i++) {
			for (int j = 0; j< asientos[i].length; j++) {
				if (asientosCOV[i][j]==-1 && asientos[i][j]!=0) {
					buscarAsientoLibre(asientos[i][j], asientosCOV);
				}
			}
		}
	}
	static void buscarAsientoLibre(int identificador, int[][] asientosCOV) {
		boolean encontrado = false;
		for(int i = 0; i<asientosCOV.length && !encontrado; i++) {
			for (int j = 0; j< asientosCOV.length && !encontrado; j++) {
				if (asientosCOV[i][j]==0) {
					asientosCOV[i][j] = identificador;
					encontrado = true;
				}
			}
		}
	}
	// otros m�todos necesarios ...
	
	
	
					// M�TODOS BASE YA DEFINIDOS
	
	
	/* Obtiene una matriz entera con -1 en las posiciones covid y 0 en las no covid */
	
	static int[][] crearAsientosCovid(int[][] asientos) {

		int[][] asientosC = new int[asientos.length][asientos[0].length];
		for (int f = 0; f < asientos.length; f++)
			for (int c = 0; c < asientos[0].length; c++) {
				if ((esPar(f) && esPar(c)) || (esImpar(f) && esImpar(c)))
					asientosC[f][c] = -1;
				else
					asientosC[f][c] = 0;
			}
		return asientosC;
	}
    /* Comprueba si la posici�n dada por la fila f y la columna c es correcta */
	
	static boolean posicionCorrecta(int f, int c) {
		return !( (esPar(f) && esPar(c)) || (esImpar(f) && esImpar(c)) );
	}
	
	/*  Obtiene el n�mero total de asientos disponibles del autobus seg�n la norma covid*/
	
	static int obtenNumAsientCovid(int[][] asientos) {
		int totalAsientos = asientos.length * asientos[0].length;
		int totalCovid = totalAsientos / 2;
		if (esImpar(totalAsientos)) 
			totalCovid++;
		return totalCovid;
	}
	
	/* Obtiene el n�mero de asientos ocupados del autob�s inicial */
	private static int contarValoresNoCero(int[][] matriz) {
		int ocupados = 0;
		for (int i = 0; i < matriz.length; i++)
			for (int j = 0; j < matriz[0].length; j++)
				if (matriz[i][j] != 0)
					ocupados++;
		return ocupados;
	}
	/* Comprueba si n es impar */
	static boolean esImpar(int n) {
		return n % 2 != 0;
	}

	/* Comprueba si n es par */
	static boolean esPar(int n) {
		return n % 2 == 0;
	}

	/* Muestra con formato el autob�s  */
	private static void mostrarAsientos(int[][] asientos) {
		System.out.println();
		System.out.print("     ");
		for (int i = 0; i < asientos[0].length; i++) {
			System.out.printf(i + "  ");
		}
		System.out.println();
		System.out.print("     ");
		for (int i = 0; i < asientos[0].length; i++) {
			System.out.printf( "---");
		}
		System.out.println();
		for (int fil = 0; fil < asientos.length; fil++) {
			System.out.print(fil + ": ");
			for (int col = 0; col < asientos[0].length; col++)

				if (asientos[fil][col] == -1)
					System.out.printf("%3s", "X");
				else if (asientos[fil][col] == 0)
					System.out.printf("%3s", "_");
				else
					System.out.printf("%3d", asientos[fil][col]);

			System.out.println();
		}
		System.out.println();
	}

}
