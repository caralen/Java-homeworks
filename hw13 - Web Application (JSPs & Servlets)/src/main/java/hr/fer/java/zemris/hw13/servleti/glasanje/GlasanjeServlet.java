package hr.fer.java.zemris.hw13.servleti.glasanje;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The Class GlasanjeServlet is a servlet which reads file which contains description of some musical groups, 
 * creates a list of these bands and sets it as session attribute. Further processing is forwarded to the glasanjeIndex.jsp.
 */
@WebServlet("/glasanje")
public class GlasanjeServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Reads file "/WEB-INF/glasanje-definicija.txt". For each line it creates an instance of
	 * <code>Band</code> and adds it to a list of Bands. 
	 * After adding all lines to the list, list is set as session attribute under the name "bands".
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		List<String> lines = Files.readAllLines(Paths.get(fileName));
		
		List<Band> bands = new ArrayList<>();
		for(String line : lines) {
			String[] parts = line.split("\t");
			bands.add(new Band(parts[0], parts[1], parts[2]));
		}
		
		req.getSession().setAttribute("bands", bands);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
		
	}
	
	/**
	 * The Class Band represents a single musical band.
	 */
	public static class Band {
		
		/** The id of the band. */
		String ID;
		
		/** The name of the band. */
		String name;
		
		/** The youtube link to the band's song. */
		String link;
		
		/**
		 * Instantiates a new band.
		 *
		 * @param iD the identificator of the band
		 * @param name the name of the band
		 * @param link the youtube link to the band's song
		 */
		public Band(String iD, String name, String link) {
			ID = iD;
			this.name = name;
			this.link = link;
		}

		/**
		 * Gets the id.
		 *
		 * @return the id
		 */
		public String getID() {
			return ID;
		}

		/**
		 * Gets the name.
		 *
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * Gets the link.
		 *
		 * @return the link
		 */
		public String getLink() {
			return link;
		}
	}
}
