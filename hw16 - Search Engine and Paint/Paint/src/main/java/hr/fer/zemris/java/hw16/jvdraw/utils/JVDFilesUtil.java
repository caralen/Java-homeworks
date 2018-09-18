package hr.fer.zemris.java.hw16.jvdraw.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import hr.fer.zemris.java.hw16.jvdraw.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.JVDraw;
import hr.fer.zemris.java.hw16.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.color.JColorArea;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.objects.Line;
import hr.fer.zemris.java.hw16.jvdraw.visitors.GeometricalObjectBBCalculator;
import hr.fer.zemris.java.hw16.jvdraw.visitors.GeometricalObjectPainter;
import hr.fer.zemris.java.hw16.jvdraw.visitors.GeometricalObjectSaver;

/**
 * The Class JVDFilesUtil is a utility class which is used to deal with mostly files operations.
 */
public class JVDFilesUtil {

	/**
	 * Parses the document.
	 * Each line should start with some of these words: LINE, CIRCLE, FCIRCLE.
	 *
	 * @param model the drawing model
	 * @param path the file path
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws NumberFormatException the number format exception
	 */
	public static void parseDocument(DrawingModel model, Path path) throws IOException, NumberFormatException {
		
		List<String> lines = Files.readAllLines(path);
		
		for(String line : lines) {
			if (line.startsWith("LINE")) {
				String[] parts = line.split(" ");
				IColorProvider provider = new JColorArea(
						new Color(
								Integer.parseInt(parts[5]), 
								Integer.parseInt(parts[6]), 
								Integer.parseInt(parts[7]))
						);
				Line l = new Line(
						provider, 
						new Point2D(Integer.parseInt(parts[1]), Integer.parseInt(parts[2])), 
						new Point2D(Integer.parseInt(parts[3]), Integer.parseInt(parts[4]))
				);
				model.add(l);
				l.setColor(provider.getCurrentColor());
				
			} else if (line.startsWith("CIRCLE")) {
				String[] parts = line.split(" ");
				IColorProvider provider = new JColorArea(
						new Color(
								Integer.parseInt(parts[4]), 
								Integer.parseInt(parts[5]), 
								Integer.parseInt(parts[6]))
						);
				Circle circle = new Circle(
						provider, 
						new Point2D(Integer.parseInt(parts[1]), Integer.parseInt(parts[2])), 
						Integer.parseInt(parts[3])
					);
				model.add(circle);
				circle.setColor(provider.getCurrentColor());
				
			} else if (line.startsWith("FCIRCLE")) {
				String[] parts = line.split(" ");
				IColorProvider fgProvider = new JColorArea(
						new Color(
								Integer.parseInt(parts[4]), 
								Integer.parseInt(parts[5]), 
								Integer.parseInt(parts[6]))
						);
				IColorProvider bgProvider = new JColorArea(
						new Color(
								Integer.parseInt(parts[7]), 
								Integer.parseInt(parts[8]), 
								Integer.parseInt(parts[9]))
						);
				FilledCircle fcircle = new FilledCircle(
						fgProvider,
						bgProvider,
						new Point2D(Integer.parseInt(parts[1]), Integer.parseInt(parts[2])), 
						Integer.parseInt(parts[3])
						);
				model.add(fcircle);
				fcircle.setOutlineColor(fgProvider.getCurrentColor());
				
			} else {
				System.out.println("Invalid line in the document.");
			}
		}
	}


	/**
	 * Opens the file and fills the model with the information from the file.
	 *
	 * @param jvDraw the jv draw
	 * @param model the model
	 * @param canvas the canvas
	 */
	public static void openFile(JVDraw jvDraw, DrawingModel model, JDrawingCanvas canvas) {
		File file = createFileChooser(
				jvDraw, 
				"Open document", 
				new FileNameExtensionFilter("jvd files (*.jvd)", "jvd"),
				Operation.OPEN
				);
		
		if(file != null) {
			Path path = file.toPath();
			try {
				clearModel(model);
				parseDocument(model, path);
				canvas.setPath(path);
			} catch (IOException e) {
				e.printStackTrace();
			} catch(NumberFormatException e) {
				System.out.println("Invalid file format!");
			}
		}
	}

	/**
	 * Clears all objects from the model.
	 *
	 * @param model the model
	 */
	public static void clearModel(DrawingModel model) {
		while(model.getSize() != 0) {
			model.remove(model.getObject(0));
		}
	}

	/**
	 * Saves the file on its path. If the file has not yet been saved, the saveNewFile method is invoked.
	 *
	 * @param jvDraw the jv draw
	 * @param model the model
	 * @param canvas the canvas
	 */
	public static void saveFile(JVDraw jvDraw, DrawingModel model, JDrawingCanvas canvas) {
		if(canvas.getPath() == null) {
			saveNewFile(jvDraw, model, canvas);
		}
		if(!canvas.isChanged()) return;
		try {
			writeToFile(canvas.getPath(), model);
			JOptionPane.showMessageDialog(jvDraw, "File was saved successfully.", "Message", JOptionPane.INFORMATION_MESSAGE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Saves the file to a new location on disk.
	 * User gets to choose where the file will be saved.
	 *
	 * @param jvDraw the jv draw
	 * @param model the model
	 * @param canvas the canvas
	 */
	public static void saveNewFile(JVDraw jvDraw, DrawingModel model, JDrawingCanvas canvas) {
		File file = createFileChooser(
				jvDraw, 
				"Save document", 
				new FileNameExtensionFilter("jvd files (*.jvd)", "jvd"),
				Operation.SAVE);
		
		if(file != null) {
			file = checkExtension("jvd", file);
			Path path = file.toPath();
			try {
				writeToFile(path, model);
				canvas.setPath(path);
				JOptionPane.showMessageDialog(jvDraw, "File was saved successfully.", "Message", JOptionPane.INFORMATION_MESSAGE);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Writes information from the model to the file.
	 * Saving is done by using the visitor pattern with the GeometricalObjectSaver.
	 *
	 * @param path the path
	 * @param model the model
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private static void writeToFile(Path path, DrawingModel model) throws IOException {
		if(path == null) return;
		
		GeometricalObjectSaver saver = new GeometricalObjectSaver();
		
		int modelSize = model.getSize();
		for(int i = 0; i < modelSize; i++) {
			GeometricalObject object = model.getObject(i);
			object.accept(saver);
		}
		Files.write(path, saver.getLines().toString().getBytes());
	}
	
	/**
	 * Exports the picture to a new file on disk.
	 * User gets to choose where the file will be saved and which extension will it have.
	 *
	 * @param jvDraw the jv draw
	 * @param model the model
	 * @param canvas the canvas
	 */
	public static void exportFile(JVDraw jvDraw, DrawingModel model, JDrawingCanvas canvas) {
		String extension = chooseExtension(jvDraw);
		File file = createFileChooser(
				jvDraw, 
				"Export document", 
				new FileNameExtensionFilter(extension, extension), 
				Operation.EXPORT
				);
		
		if(file != null) {
			file = checkExtension(extension, file);
			
			GeometricalObjectBBCalculator bbcalc = new GeometricalObjectBBCalculator();
			int size = model.getSize();
			
			for(int i = 0; i < size; i++) {
				GeometricalObject object = model.getObject(i);
				object.accept(bbcalc);
			}
			
			Rectangle box = bbcalc.getBoundingBox();
			BufferedImage image = new BufferedImage(
				box.width, box.height, BufferedImage.TYPE_3BYTE_BGR
			);
			
			Graphics2D g = image.createGraphics();
			g.translate(-box.x, -box.y);
			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(Color.WHITE);
			g2d.fillRect(box.x, box.y, (int)box.getWidth(), (int)box.getHeight());
			
			GeometricalObjectPainter painter = new GeometricalObjectPainter(g2d);
			for(int i = 0; i < size; i++) {
				GeometricalObject object = model.getObject(i);
				object.accept(painter);
			}
			g.dispose();
			
			try {
				ImageIO.write(image, extension, file);
				JOptionPane.showMessageDialog(jvDraw, "File was exported successfully.", "Message", JOptionPane.INFORMATION_MESSAGE);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * User gets to choose which extension will the file have.
	 * User can choose between 3 extensions: jpg, png and gif.
	 *
	 * @param jvDraw the jv draw
	 * @return the string
	 */
	private static String chooseExtension(JVDraw jvDraw) {
		String[] extensions = {"png", "jpg", "gif"};
		int answer = JOptionPane.showOptionDialog(
				jvDraw, 
				"In which format would you like to export?", 
				"Choose extension", 
				JOptionPane.YES_NO_CANCEL_OPTION, 
				JOptionPane.QUESTION_MESSAGE, 
				null, 
				extensions, 
				extensions[0]);
		
		return answer == -1 ? extensions[0] : extensions[answer];
	}
	
	/**
	 * Checks if the extension is right and adds the extension if it is missing.
	 *
	 * @param extension the extension
	 * @param file the file
	 * @return the file
	 */
	private static File checkExtension(String extension, File file) {
		if (!file.toString().toLowerCase().endsWith(extension.toLowerCase())) {
			file = new File(file.toString() + "." + extension);
		}
		return file;
	}

	/**
	 * Creates the file chooser.
	 *
	 * @param jvDraw the jv draw
	 * @param title the title
	 * @param filter the filter
	 * @param operation the operation
	 * @return the file
	 */
	private static File createFileChooser(JVDraw jvDraw, String title, FileNameExtensionFilter filter, Operation operation) {
		JFileChooser jfc = new JFileChooser();
		jfc.addChoosableFileFilter(filter);
		jfc.setFileFilter(filter);
		jfc.setDialogTitle(title);
		return showDialog(jvDraw, jfc, operation);
	}

	/**
	 * Shows the correct dialog for user to choose where the file will be opened/saved/exported.
	 *
	 * @param jvDraw the jv draw
	 * @param jfc the jfc
	 * @param operation the operation
	 * @return the file
	 */
	private static File showDialog(JVDraw jvDraw, JFileChooser jfc, Operation operation) {
		if (operation == Operation.OPEN) {
			if (jfc.showOpenDialog(jvDraw) != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(jvDraw, "Opening was canceled.", "Message", JOptionPane.INFORMATION_MESSAGE);
			}
		} else if (operation == Operation.SAVE) {
			if (jfc.showSaveDialog(jvDraw) != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(jvDraw, "File is not saved!", "Message", JOptionPane.INFORMATION_MESSAGE);
			}
		} else if (operation == Operation.EXPORT) {
			if (jfc.showSaveDialog(jvDraw) != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(jvDraw, "File is not exported!", "Message", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		return jfc.getSelectedFile();
	}
	
	/**
	 * The Enum Operation represents which operation is being used at the moment.
	 */
	private enum Operation {
		
		/** The open operation. */
		OPEN,
		
		/** The save operation. */
		SAVE, 
		
		/** The export operation. */
		EXPORT;
	}
}
