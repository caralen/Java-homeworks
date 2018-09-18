package hr.fer.java.zemris.hw13.servleti;

import java.awt.image.BufferedImage;
import java.io.IOException;

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
 * Servlet which is used for generating a pie chart that describes the usage of operating systems.
 * @author Alen Carin
 *
 */
@WebServlet("/reportImage")
public class ReportImageServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates piechart, dataset for pie chart writes that pie chart to response output stream as a png image.
	 *
	 * @param req the request
	 * @param resp the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		PieDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset, "OS chart");
		
        BufferedImage image = chart.createBufferedImage(400, 400);
        resp.setContentType("image/png");
        
        ImageIO.write(image, "png", resp.getOutputStream());
	}
	
	/**
	 * Creates dataset for pie chart.
	 * @return DefaultPieDataset which has stored values for pie chart.
	 */
	private  PieDataset createDataset() {
        DefaultPieDataset result = new DefaultPieDataset();
        result.setValue("Linux", 21.6);
        result.setValue("Mac", 26.2);
        result.setValue("Windows", 52.2);
        return result;

    }

    /**
     * Creates a chart <code>JFreeChart</code> with all of its necessary information.
     *
     * @param dataset the dataset
     * @param title the title
     * @return the j free chart
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
