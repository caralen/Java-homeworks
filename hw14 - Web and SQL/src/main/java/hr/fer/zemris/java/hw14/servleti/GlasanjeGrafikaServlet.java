package hr.fer.zemris.java.hw14.servleti;

import java.awt.image.BufferedImage;
import java.io.IOException;
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

import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.model.Poll;
import hr.fer.zemris.java.hw14.model.PollOption;

/**
 * The Class GlasanjeGrafikaServlet is a servlet which creates a pie chart for showing the distribution of band votes.
 * The pie chart is created based on the information stored in the database.
 */
@WebServlet("/servleti/glasanje-grafika")
public class GlasanjeGrafikaServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String parameter = req.getParameter("pollID");
		
		if(parameter == null) {
			req.setAttribute("message", "No parameter sent!");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		
		long pollID;
		try {
			pollID = Long.parseLong(parameter);
		} catch (NumberFormatException e) {
			req.setAttribute("message", "Parameter must be of type long!");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		
		Poll poll = DAOProvider.getDao().getPoll(pollID);
		
		PieDataset dataset = createDataset(pollID);
        JFreeChart chart = createChart(dataset, poll.getTitle());
		
        BufferedImage image = chart.createBufferedImage(400, 400);
        resp.setContentType("image/png");
        
        ImageIO.write(image, "png", resp.getOutputStream());
	}
	
	/**
	 * Creates the dataset from the database contents.
	 *
	 * @param id the identification number of the poll
	 * @return the pie dataset
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private  PieDataset createDataset(long id) throws IOException {
		
		DefaultPieDataset result = new DefaultPieDataset();
		List<PollOption> options = DAOProvider.getDao().getOptionsForPollId(id);
		
		for(PollOption option : options) {
			result.setValue(option.getOptionTitle(), option.getVotesCount());
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
