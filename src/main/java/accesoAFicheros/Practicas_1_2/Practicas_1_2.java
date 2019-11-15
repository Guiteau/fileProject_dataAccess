package accesoAFicheros.Practicas_1_2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Iterator;
import java.util.Optional;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Practicas_1_2 extends Application {

	private Label nombreAlumnoLabel, rutaLabel, idResidenciaLabel, nombreResidenciaLabel, codUniversidadLabel,
			precioMensualLabel;
	private TextField rutaTexto, accionTexto, idResidenciaField, nombreResidenciaField, codUniversidadField,
			precioMensualField;
	private Button crearButton, eliminarButton, moverButton, verListadoButton, verButton, modificarButton,
			insertarButton, modificaRandomButton, verRegistroButton, verTodoButton;
	private TextArea contenidoArea, centroText;
	private ListView<String> listaList;
	private RadioButton esCarpetaRadio, esFicheroRadio;
	private CheckBox comedorCheck;

	@Override
	public void start(Stage primaryStage) throws Exception {

		// PESTAÑA "Acceso a ficheros"

		// INSTANCIAS

		// Label

		nombreAlumnoLabel = new Label("Diego Méndez Esquerro");
		rutaLabel = new Label("Ruta Actual");

		// Campos de texto

		rutaTexto = new TextField();
		accionTexto = new TextField();
		accionTexto.setPromptText("Carpeta o fichero a crear, eliminar o mover");

		// Botones
		crearButton = new Button("Crear");
		eliminarButton = new Button("Eliminar");
		moverButton = new Button("Mover");
		verListadoButton = new Button("Ver Ficheros y Carpetas");
		verButton = new Button("Ver Contenido Fichero");
		modificarButton = new Button("Modificar Fichero");

		modificarButton.setPrefWidth(150);
		verButton.setPrefWidth(150);

		// Eventos

		crearButton.setOnAction(e -> {
			try {
				onCrearButtonAction(e);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				contenidoArea.setText(e1.getMessage());
			}
		});
		eliminarButton.setOnAction(e -> {
			try {
				onEliminarButtonAction(e);
				contenidoArea.setText("Se ha eliminado con éxito");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				contenidoArea.setText(e1.getMessage());
			}
		});
		moverButton.setOnAction(e -> {
			try {
				onMoverButtonAction(e);
				contenidoArea.setText("Se ha movido con éxito");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				contenidoArea.setText(e1.getMessage());
			}
		});
		verListadoButton.setOnAction(e -> {
			try {
				onVerListadoButtonAction(e);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				contenidoArea.setText(e1.getMessage());
			}
		});
		verButton.setOnAction(e -> onVerButtonAction(e));
		modificarButton.setOnAction(e -> {
			try {
				onModificarButtonAction(e);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				contenidoArea.setText(e1.getMessage());
			}
		});

		// Área de texto

		contenidoArea = new TextArea();
		contenidoArea.setWrapText(true);

		// Vista de lista

		listaList = new ListView<String>();

		// Botones de Radio

		esCarpetaRadio = new RadioButton();
		esFicheroRadio = new RadioButton();

		// AGRUPAR BOTONES DE RADIO

		ToggleGroup ficheroArchivoGroup = new ToggleGroup();
		ficheroArchivoGroup.getToggles().addAll(esCarpetaRadio, esFicheroRadio);

		// HBox botonera

		HBox botonera = new HBox(10, crearButton, eliminarButton, moverButton, esCarpetaRadio, new Label("Es Carpeta"),
				esFicheroRadio, new Label("Es Fichero"));
		botonera.setAlignment(Pos.BASELINE_LEFT);

		// CONSTRUCCIÓN DEL TabPane

		TabPane tabpane = new TabPane();
		Tab ficheros = new Tab("Acceso a Ficheros");
		Tab aleatorio = new Tab("Acceso aleatorio");
		Tab xml = new Tab("Acceso XML");

		tabpane.getTabs().addAll(ficheros, aleatorio, xml);

		// CONSTRUCCIÓN DEL GridPane

		GridPane grid = new GridPane();
		grid.setHgap(15);
		grid.setVgap(15);
		grid.addRow(0, nombreAlumnoLabel);
		grid.addRow(1, rutaLabel, rutaTexto);
		grid.addRow(2, botonera);
		grid.addRow(3, accionTexto);
		grid.addRow(4, verListadoButton);
		grid.addRow(5, listaList);
		grid.addRow(6, verButton, contenidoArea);
		grid.addRow(7, modificarButton);

		GridPane.setHalignment(nombreAlumnoLabel, HPos.CENTER);
		GridPane.setColumnSpan(botonera, 2);
		GridPane.setFillWidth(botonera, true);
		GridPane.setColumnSpan(accionTexto, 2);
		GridPane.setColumnSpan(listaList, 2);
		GridPane.setRowSpan(contenidoArea, 7);
		GridPane.setColumnSpan(verListadoButton, 2);
		GridPane.setColumnSpan(nombreAlumnoLabel, 2);

		// ColumnConstraints

		ColumnConstraints[] cols = { new ColumnConstraints(), new ColumnConstraints() };

		cols[0].setHalignment(HPos.LEFT);
		cols[1].setHgrow(Priority.ALWAYS);

		grid.getColumnConstraints().setAll(cols);

		// CONSTRUCCIÓN DEL BorderPane

		BorderPane root = new BorderPane();
		root.setCenter(grid);
		root.setPadding(new Insets(20, 20, 20, 20));

		ficheros.setContent(root);

		// PESTAÑA "Acceso aleatorio"

		// Labels, fields y checkbox

		idResidenciaLabel = new Label("ID Residencia");
		nombreResidenciaLabel = new Label("Nombre Residencia");
		codUniversidadLabel = new Label("Código de Universidad");
		precioMensualLabel = new Label("Precio Mensual");

		idResidenciaField = new TextField();
		idResidenciaField.setAlignment(Pos.CENTER);
		nombreResidenciaField = new TextField();
		nombreResidenciaField.setAlignment(Pos.CENTER);
		codUniversidadField = new TextField();
		codUniversidadField.setAlignment(Pos.CENTER);
		precioMensualField = new TextField();
		precioMensualField.setAlignment(Pos.CENTER);

		comedorCheck = new CheckBox("Comedor");

		// CONSTRUCCIÓN DEL VBox que contendrá los field a la izquierda

		VBox vboxIzquierda = new VBox(idResidenciaLabel, idResidenciaField, nombreResidenciaLabel,
				nombreResidenciaField, codUniversidadLabel, codUniversidadField, precioMensualLabel, precioMensualField,
				comedorCheck);

		vboxIzquierda.setAlignment(Pos.CENTER);
		vboxIzquierda.setSpacing(10);

		// BOTONES que irán a la derecha

		insertarButton = new Button("Insertar");
		insertarButton.setMaxWidth(Double.MAX_VALUE);
		modificaRandomButton = new Button("Modificar");
		modificaRandomButton.setMaxWidth(Double.MAX_VALUE);
		verRegistroButton = new Button("Visualizar Registro");
		verRegistroButton.setMaxWidth(Double.MAX_VALUE);
		verTodoButton = new Button("Visualizar Todos");
		verTodoButton.setMaxWidth(Double.MAX_VALUE);

		// Botonera de la derecha

		VBox vboxDerecha = new VBox(insertarButton, modificaRandomButton, verRegistroButton, verTodoButton);

		vboxDerecha.setAlignment(Pos.CENTER);
		vboxDerecha.setSpacing(20);

		// ÁREA DE TEXTO

		centroText = new TextArea();

		// CONSTRUCCIÓN DEL BorderPane

		BorderPane bPane = new BorderPane();

		bPane.setLeft(vboxIzquierda);
		bPane.setRight(vboxDerecha);
		bPane.setCenter(centroText);

		// Incluímos el BorderPane() generado en la pestaña de acceso aleatorio

		aleatorio.setContent(bPane);

		// Eventos de cada botón

		insertarButton.setOnAction(e -> {
			try {
				onInsertarButtonAction(e);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				centroText.setText(e1.getMessage());
				;
			}
		});
		modificaRandomButton.setOnAction(e -> {
			try {
				onModificaRandomButtonAction(e);
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				centroText.setText(e2.getMessage());
			}
		});
		verRegistroButton.setOnAction(e -> {
			try {
				onVerRegistroButtonAction(e);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				centroText.setText(e1.getMessage());
			}
		});
		verTodoButton.setOnAction(e -> {
			try {
				onVerTodoButtonAction(e);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				centroText.setText(e1.getMessage());
			}
		});

		// ESCENA

		Scene scene = new Scene(tabpane, 750, 550);
		primaryStage.setTitle("Acceso a Datos");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	private void onVerTodoButtonAction(ActionEvent e) throws IOException {

		centroText.clear();

		@SuppressWarnings("resource")
		RandomAccessFile fichero = new RandomAccessFile("archivo.dat", "r");

		fichero.seek(0); // nos situamos al comienzo del fichero

		while (fichero.getFilePointer() < fichero.length()) { // mientras el puntero no llegue al final

			centroText.appendText(Integer.toString(fichero.readInt())); // añade primero el entero (ID residencia)

			for (int i = 0; i <= 18; i++) {

				centroText.appendText(Character.toString(fichero.readChar())); // añade el nombre universidad y el
																				// código universidad

			}

			centroText.appendText(Integer.toString(fichero.readInt())); // añade el precio
			centroText.appendText(Character.toString(fichero.readChar())); // añade la coma
			centroText.appendText(Boolean.toString(fichero.readBoolean())); // añade el comedor
			centroText.appendText(Character.toString(fichero.readChar())); // añade la coma
			centroText.appendText("\n"); // salto de línea para mostrar comodamente

		}

	}

	private void onVerRegistroButtonAction(ActionEvent e) throws IOException {

		int num = 0;
		int idRes = Integer.parseInt(idResidenciaField.getText());

		@SuppressWarnings("resource")
		RandomAccessFile fichero = new RandomAccessFile("archivo.dat", "r");

		fichero.seek(0); // nos situamos al principio del fichero

		while (num != idRes) {

			centroText.clear();

			num = fichero.readInt();

			centroText.appendText(Integer.toString(num));

			for (int i = 0; i <= 18; i++) {

				centroText.appendText(Character.toString(fichero.readChar())); // añade nombre universidad y código
																				// universidad además de la coma

			}

			centroText.appendText(Integer.toString(fichero.readInt())); // Precio
			centroText.appendText(Character.toString(fichero.readChar())); // es la coma aquí
			centroText.appendText(Boolean.toString(fichero.readBoolean()));
			centroText.appendText(Character.toString(fichero.readChar())); // coma
			centroText.appendText("\n");

		}

	}

	private void onModificaRandomButtonAction(ActionEvent e) throws IOException {

		@SuppressWarnings("resource")
		RandomAccessFile fichero = new RandomAccessFile("archivo.dat", "rw");

		fichero.seek(51 * (Integer.parseInt(idResidenciaField.getText()) - 1)); // nos situamos al principio de la línea
																				// deseada, es decir, si fuera 2ª línea
																				// -1,
																				// te coloca al principio de la 2ª línea
																				// (51*1 y así sucesivamente)
		fichero.readInt(); // leemos el ID

		for (int i = 0; i <= 18; i++) { // 18

			fichero.readChar(); // leemos el nombre residencia (coma + 10 + coma = 12) y el código de
								// universidad (6) + coma = 19)

		}

		fichero.writeInt(Integer.parseInt(precioMensualField.getText())); // precio mensual

		fichero.readChar(); // coma

		fichero.readBoolean(); // comedor

		fichero.readChar(); // coma

	}

	private void onInsertarButtonAction(ActionEvent e) throws IOException {
		// TODO Auto-generated method stub

		int precioRes;
		String coma = ",", nomRes = "", codUni = "";

		precioRes = Integer.parseInt(precioMensualField.getText());

		for (int i = 0; i < nombreResidenciaField.getText().length(); i++) {

			nomRes += nombreResidenciaField.getText().charAt(i);
		}

		while (nomRes.length() < 10) {

			nomRes += ' ';

		}

		for (int i = 0; i < codUniversidadField.getText().length(); i++) {

			codUni += codUniversidadField.getText().charAt(i);
		}

		while (codUni.length() < 6) {

			codUni += ' ';
		}

		RandomAccessFile fichero = new RandomAccessFile("archivo.dat", "rw");

		fichero.seek(fichero.length());

		fichero.writeInt(((int) fichero.length() / 51) + 1);
		fichero.writeChars(coma);
		System.out.println(fichero.length());
		fichero.writeChars(nomRes);
		fichero.writeChars(coma);
		fichero.writeChars(codUni);
		fichero.writeChars(coma);
		System.out.println(fichero.length());
		fichero.writeInt(precioRes);
		fichero.writeChars(coma);
		fichero.writeBoolean(comedorCheck.isSelected());
		fichero.writeChars(coma);

		if (fichero != null) {
			fichero.close();
		}

	}

	private void onModificarButtonAction(ActionEvent e) throws IOException {

		File fl = new File(rutaTexto.getText(), listaList.getSelectionModel().getSelectedItem());

		ObservableList<CharSequence> paragraph = contenidoArea.getParagraphs();
		Iterator<CharSequence> iter = paragraph.iterator();

		BufferedWriter bf = new BufferedWriter(new FileWriter(fl));
		while (iter.hasNext()) {
			CharSequence seq = iter.next();
			bf.append(seq);
			bf.newLine();
		}
		bf.flush();
		bf.close();

	}

	private void onVerButtonAction(ActionEvent e) {

		String linea;
		File fl = new File(rutaTexto.getText(), listaList.getSelectionModel().getSelectedItem());

		try {

			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(new FileReader(fl));

			linea = br.readLine();

			while (linea != null) {

				contenidoArea.setText(linea);

				linea = br.readLine();

			}

		} catch (IOException exc) {

			contenidoArea.setText("Error E/S: " + exc);

		}

	}

	private void onVerListadoButtonAction(ActionEvent e) throws IOException {

		File carpeta = new File(rutaTexto.getText());
		File[] listaInfo = carpeta.listFiles();

		if (carpeta.isDirectory() && carpeta.exists()) {

			for (int i = 0; i < listaInfo.length; i++) {

				listaList.getItems().add(listaInfo[i].getName());

			}

		} else
			throw new FileNotFoundException("Se ha producido un error, compruebe la ruta");

	}

	private void onMoverButtonAction(ActionEvent e) throws IOException {

		Practica_1 p = new Practica_1();
		String rutaActual, rutaNueva = null;

		rutaActual = rutaTexto.getText();

		if (esFicheroRadio.isSelected() | esCarpetaRadio.isSelected()) {

			rutaActual += "\\" + accionTexto.getText();

		}

		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Mover archivo");
		dialog.setContentText("Introduce nuevo destino:");

		Optional<String> resultado = dialog.showAndWait();
		if (resultado.isPresent()) {
			rutaNueva = resultado.get();
		}

		p.moverArchivoOCarpeta(rutaActual, rutaNueva);

	}

	private void onEliminarButtonAction(ActionEvent e) throws IOException {

		Practica_1 p = new Practica_1();
		String ruta = rutaTexto.getText(), carpeta = accionTexto.getText();

		if (esFicheroRadio.isSelected()) {

			p.eliminarArchivo(rutaTexto.getText(), accionTexto.getText());

		} else if (esCarpetaRadio.isSelected()) {

			p.eliminarCarpeta(ruta += "\\" + carpeta);
		}

	}

	private void onCrearButtonAction(ActionEvent e) throws IOException {

		Practica_1 p = new Practica_1();
		String ruta = rutaTexto.getText(), archivoOCarpeta = accionTexto.getText();

		if (esFicheroRadio.isSelected()) {

			p.crearArchivoEnCarpeta(ruta, archivoOCarpeta);

			contenidoArea.setText("Se ha creado el archivo " + archivoOCarpeta);

		} else if (esCarpetaRadio.isSelected()) {

			p.crearCarpeta(ruta += "\\" + archivoOCarpeta);

			contenidoArea.setText("Se ha creado la carpeta " + archivoOCarpeta);
		}

	}

	public static void main(String[] args) {

		launch(args);

	}

}
