package accesoAFicheros.Practicas_1_2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Practica_1 {

	// comprobar que una carpeta o fichero existe

	public boolean compruebaFicheroCarpeta(String ruta) {

		File fl = new File(ruta);

		return fl.exists();
	}

	// crear archivo

	public void crearArchivo(String ruta, String nombreFichero) {

		File fichero = new File(ruta, nombreFichero);
		boolean ficheroCreado;

		try {

			if (!fichero.exists()) {

				ficheroCreado = fichero.createNewFile();

				System.out.println("Fichero creado: " + ficheroCreado);

			}

		} catch (IOException e) {

			System.out.println("Se ha producido I/O exception: " + e.getMessage());

		}

	}

	// crear carpeta

	public void crearCarpeta(String ruta) throws IOException {

		File carpeta = new File(ruta);
		boolean carpetaExiste;

		if (!carpeta.exists()) {

			carpetaExiste = carpeta.mkdir();

			System.out.println("Fichero creado: " + carpetaExiste);

		}

	}

	// crear archivo y carpeta

	public void crearArchivoEnCarpeta(String ruta, String archivo) throws IOException {

		File carpeta = new File(ruta);
		File archivoNuevo = new File(ruta, archivo);

		if (!carpeta.exists()) {

			carpeta.mkdir();

			archivoNuevo.createNewFile();

		} else {

			archivoNuevo.createNewFile();

		}

	}

	// eliminar archivo

	public void eliminarArchivo(String ruta, String archivo) throws IOException {

		File carpetaFile = new File(ruta);
		File fl = new File(ruta, archivo);

		if (carpetaFile.exists()) {

			fl.delete();

		} else
			throw new FileNotFoundException("Error: El archivo no existe o no es una ruta válida");

	}

	// eliminar carpeta

	public void eliminarCarpeta(String ruta) throws IOException {

		Practica_1 p = new Practica_1();
		File carpeta = new File(ruta);
		File[] conjuntoArchivos = carpeta.listFiles();

		if (carpeta.isDirectory() && carpeta.exists()) {

			if (carpeta.list().length != 0) {

				for (int i = 0; i < conjuntoArchivos.length; i++) {

					p.eliminarArchivo(ruta, conjuntoArchivos[i].getName());

				}

				carpeta.delete();

			} else {

				carpeta.delete();

			}

		} else
			throw new FileNotFoundException("Error: La carpeta no existe o no es una ruta válida");

	}

	// renombrar archivo

	public void renombrarFichero(String nombreAntiguo, String nombreNuevo) throws IOException {

		File ficheroOld = new File(nombreAntiguo);
		File ficheroNew = new File(nombreNuevo);
		boolean cambioRealizado;

		if (ficheroOld.isFile()) {

			cambioRealizado = ficheroOld.renameTo(ficheroNew);

			System.out.println("Se ha cambiado el nombre del archivo: " + cambioRealizado);

		} else {

			System.out.println("No es una ruta v�lida");

		}

	}

	// mover archivo

	public void moverArchivoOCarpeta(String rutaVieja, String rutaNueva) throws IOException {

		File vieja = new File(rutaVieja);
		File nueva = new File(rutaNueva);

		if (vieja.isDirectory() | vieja.isFile() && vieja.exists()) {

			if (!nueva.exists()) {

				crearCarpeta(rutaNueva);

				if (rutaNueva.charAt(rutaNueva.length() - 1) != '\\') {

					rutaNueva += '\\';
				}

				vieja.renameTo(new File(rutaNueva + vieja.getName()));

				vieja.delete();

			} else if (rutaNueva.charAt(rutaNueva.length() - 1) != '\\') {

				rutaNueva += '\\';

				vieja.renameTo(new File(rutaNueva + vieja.getName()));

				vieja.delete();

			} else {

				vieja.renameTo(new File(rutaNueva + vieja.getName()));

				vieja.delete();

			}
		}else 
			throw new FileNotFoundException("Error: La carpeta de origen no existe o no es una ruta válida");
	}

	// listar archivos o carpetas de una carpeta

	public String listarFicheros(String ruta) throws IOException {

		File carpeta = new File(ruta);
		File[] listaInfo = carpeta.listFiles();
		String resultado = "";

		if (carpeta.isDirectory() && carpeta.exists()) {

			for (int i = 0; i < listaInfo.length; i++) {

				if (listaInfo[i].isFile()) {

					resultado += "Archivo >> " + listaInfo[i].getName() + "\n";

				} else if (listaInfo[i].isDirectory()) {

					resultado += "Carpeta >> " + listaInfo[i].getName() + "\n";
				}

			}

		} else
			throw new FileNotFoundException(resultado = "Se ha producido un error, compruebe la ruta");

		return resultado;

	}

	// visualizar nombre y ruta de un fichero

	public void visualizaNombreYRuta(String ruta) {

		File fl = new File(ruta);

		System.out.println("Nombre fichero: " + fl.getName() + "\n" + "\n" + "Ruta: " + fl.getPath());

	}

	// visualiza y modifica el contenido de un fichero de texto

	public void visualizaModificaArchivo(String ruta) {

	}

}
