package Laboratorio;
import java.util.Scanner;
public class ExamenLab {
	final static int ASIENTOS_POR_FILA = 4;
	final static int MAX_BILLETES = 10;
	final static double DESCUENTO = 0.05;
	final static String[] MENU = { "Salir", "Venta de billetes", "Consulta de asientos libres",
	"Anulacion de billetes" };
	
	//1. Modificadfojjoai
	//2. Esto es un mensaje de prueba 2
	//3. Feo soy yo

	final static Scanner TECLADO = new Scanner(System.in);

	public static void main(String[] args) {
		System.out.println("** Bienvenido al sistema de venta de billetes de avion **");
		int filas = pedirEnteroPositivo("Introduzca la cantidad de filas del avion: ");
		boolean[][] asientos = crearMatriz(filas, ASIENTOS_POR_FILA, true); // Asiento disponible: true, ocupado: false
		int precio = pedirEnteroPositivo("Introduzca el precio del billete individual: ");
		menuPrincipal(asientos, precio);
	}

	private static void menuPrincipal(boolean[][] asientos, int precio) {
		boolean salir = false;
		do {
			mostrarOpcionesMenu(MENU);
			int opcion = pedirEnteroEnRango("Elija una opcion del menu", 0, MENU.length - 1);
			switch (opcion) {
			case 0:
				salir = true;
				break;
			case 1:
				venderBilletes(asientos, precio); 
				break;
			case 2:
				consultarAsientoLibre(asientos);
				break;
			case 3:
				cancelarBillete(asientos);
				break;
			default:
				System.out.println("Opcion desconocida.");
			}
		} while (!salir);
	}

	public static void mostrarOpcionesMenu(String[] texto) {
		for (int pos = 0; pos < texto.length; pos++)
			System.out.println(pos + ". " + texto[pos]);
	}

	private static void venderBilletes(boolean[][] asientos, int precio) {
		int disponibles = contarValores(asientos, true); // obtener cantidad asientos disponibles
		if (disponibles > 0) {
			int max = (disponibles > MAX_BILLETES) ? MAX_BILLETES : disponibles;
			int billetes = pedirEnteroEnRango("Indique cuantos billetes desea comprar: ", 1, max);
			mostrarMatriz(asientos);
			int[] filaAsientos = new int[billetes];
			for (int i = 0; i < billetes; i++) {
				filaAsientos[i] = pedirAsientoComprado(asientos, i + 1);
			}
			boolean aplicaDescuento = comprobarSiAplicaDescuento(filaAsientos, asientos.length);
			double precioFinal = calcularPrecioFinal(billetes, precio, aplicaDescuento);
			mostrarPrecioFinal(precioFinal, aplicaDescuento);
		} else {
			System.out.println("Lo sentimos no hay asientos disponibles.");
		}
	}

	private static int pedirAsientoComprado(boolean[][] asientos, int asiento) {
		boolean comprado = false;
		int fila;
		do {
			fila = pedirEnteroEnRango("Indique la fila del asiento " + asiento + " a comprar: ", 0,
					asientos.length - 1);
			int columna = pedirEnteroEnRango("Indique la columna del asiento " + asiento + " a comprar:", 0,
					asientos[0].length - 1);
			if (asientos[fila][columna]) {
				asientos[fila][columna] = false;
				comprado = true;
				System.out.printf("Se ha comprado el asiento %d en la fila %d\n", columna, fila);
			} else {
				System.out.printf("No se puede comprar el asiento %d en la fila %d, ya esta ocupado\n", columna, fila);
				System.out.println("Selecione uno entre los disponibles:");
				mostrarMatriz(asientos);
			}
		} while (!comprado);
		return fila;
	}

	private static boolean comprobarSiAplicaDescuento(int[] filaAsientos, int cantidadFilas) {
		boolean aplicaDescuento = false;
		int[] cuentaAsientosFilas = new int[cantidadFilas];
		// comprobamos cuantos asientos se han comprado en cada fila del avion
		// para ello recorremos el vector con las filas de los asientos comprados
		// tenemos un vector de contadores, en el que cada posicion "i" indica la cantidad
		// de asientos comprados en la fila "i"
		for (int i = 0; i < filaAsientos.length; i++) {
			cuentaAsientosFilas[filaAsientos[i]]++;
		}
		for (int i = 0; i < cuentaAsientosFilas.length; i++) {
			if (cuentaAsientosFilas[i] == ASIENTOS_POR_FILA) { // Se han comprado los 4 asientos de la fila
				aplicaDescuento = true;
			}
		}
		return aplicaDescuento;
	}

	private static double calcularPrecioFinal(int billetes, int precio, boolean aplicaDescuento) {
		double precioFinal = billetes * precio;
		if (aplicaDescuento) {
			precioFinal = precioFinal * (1 - DESCUENTO);
		}
		return precioFinal;
	}

	private static void mostrarPrecioFinal(double precioFinal, boolean aplicaDescuento) {
		if (aplicaDescuento) {
			System.out.printf("El precio final, incluyendo descuento por comprar fila completa es: %.2f\n",
					precioFinal);
		} else {
			System.out.printf("El precio final (ningun descuento aplicable) es: %.2f\n", precioFinal);
		}
	}

	private static void consultarAsientoLibre(boolean[][] asientos) {
		int fila = pedirEnteroEnRango("Indique la fila del asiento a consultar: ", 0, asientos.length - 1);
		int columna = pedirEnteroEnRango("Indique la columna del asiento a consultar ", 0, asientos[0].length - 1);
		if (asientos[fila][columna]) {
			System.out.printf("El asiento %d en la fila %d esta libre\n", columna, fila);
		} else {
			System.out.printf("El asiento %d en la fila %d esta ocupado\n", columna, fila);
		}
	}

	private static void cancelarBillete(boolean[][] asientos) {
		int fila = pedirEnteroEnRango("Indique la fila del asiento a cancelar: ", 0, asientos.length - 1);
		int columna = pedirEnteroEnRango("Indique la columna del asiento a cancelar ", 0, asientos[0].length - 1);
		if (asientos[fila][columna]) {
			System.out.printf("Error, el asiento %d en la fila %d no esta comprado\n", columna, fila);
		} else {
			asientos[fila][columna] = true;
			System.out.printf("Se ha cancelado la compra del asiento %d en la fila %d\n", columna, fila);
		}
	}

	public static int pedirEnteroPositivo(String mensaje) {
		int num;
		System.out.print(mensaje);
		num = TECLADO.nextInt();
		while (num <= 0) {
			System.out.print("Error, debe seleccionar un valor entero positivo. Intentelo otra vez... ");
			num = TECLADO.nextInt();
		}
		return num;
	}

	private static int pedirEnteroEnRango(String mensaje, int limiteInf, int limiteSup) {
		int num = 0;
		if (limiteInf > limiteSup)
			System.out.println("Los valores de los limites definen un intervalo vacio");
		else {
			System.out.printf(mensaje + "(valor en rango [%d, %d]) ", limiteInf, limiteSup);
			num = TECLADO.nextInt();
			while (num < limiteInf || num > limiteSup) {
				System.out.printf("Error, debe seleccionar un valor entero en el rango [%d, %d]. Intentelo otra vez... ",
						limiteInf, limiteSup);
				num = TECLADO.nextInt();
			}
		}
		return num;
	}

	private static boolean[][] crearMatriz(int filas, int columnas, boolean b) {
		boolean[][] matriz = new boolean[filas][columnas];
		for (int fila = 0; fila < filas; fila++)
			for (int columna = 0; columna < columnas; columna++)
				matriz[fila][columna] = b;
		return matriz;
	}

	private static int contarValores(boolean[][] matriz, boolean b) {
		int disponibles = 0;
		for (int i = 0; i < matriz.length; i++)
			for (int j = 0; j < matriz[0].length; j++)
				if (matriz[i][j] == b)
					disponibles++;
		return disponibles;
	}

	public static void mostrarVector(boolean[] vector) {
		for (int i = 0; i < vector.length; i++)
			System.out.print(vector[i] + "  ");
		System.out.println();
	}

	public static void mostrarMatriz(boolean[][] matriz) {
		for (int i = 0; i < matriz.length; i++) {
			System.out.print("Fila " + i + ": ");
			mostrarVector(matriz[i]);
		}
	}

}
