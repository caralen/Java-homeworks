package hr.fer.java.zemris.hw13.servleti.glasanje;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.util.Rotation;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

/**
 * The Class GlasanjeGrafikaServlet is a servlet which creates a pie chart for showing the distribution of band votes.
 */
@WebServlet("/glasanje-grafika")
public class GlasanjeGrafikaServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Reads lines from the "glasanje-rezultati.txt" and creates a dataset from the contents of the file.
	 * Creates a pie chart from the given dataset and writes it as png image to the response output stream.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		PieDataset dataset = createDataset(fileName);
        JFreeChart chart = createChart(dataset, "Band votes");
		
        BufferedImage image = chart.createBufferedImage(400, 400);
        resp.setContentType("image/png");
        
        ImageIO.write(image, "png", resp.getOutputStream());
	}
	
	/**
	 * Creates the dataset from the contents of the given file.
	 *
	 * @param fileName the file name
	 * @return the pie dataset
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private  PieDataset createDataset(String fileName) throws IOException {
		
		List<String> lines = Files.readAllLines(Paths.get(fileName));
		
		DefaultPieDataset result = new DefaultPieDataset();
		for(String line : lines) {
			String[] parts = line.split("\t");
			result.setValue(parts[0], Integer.parseInt(parts[1]));
		}
        return result;

    }

    /**
     * Creates a chart from the given dataset and title.
     *
     * @param dataset the dataset
     * @param title the title
     * @return the pie chart
     */
    private JFreeChart createChart(PieDataset dataset, String title) {

        JFreeChart chart = ChartFactory.createPieChart3D(
            title,
            dataset,
            true,
            true,
            false
        );

        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);
        return chart;

    }
}
