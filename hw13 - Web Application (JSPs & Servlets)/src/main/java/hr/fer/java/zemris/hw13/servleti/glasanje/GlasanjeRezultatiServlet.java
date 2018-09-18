package hr.fer.java.zemris.hw13.servleti.glasanje;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.java.zemris.hw13.servleti.glasanje.GlasanjeServlet.Band;


/**
 * The Class GlasanjeRezultatiServlet is a servlet which sets list with contents of the file "glasanje-rezultati.txt"
 * as an request attribute and forwards further processing to the "glasanjeRez.jsp".
 */
@WebServlet("/glasanje-rezultati")
public class GlasanjeRezultatiServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	
	/**
	 * Reads contents of the file "glasanje-rezultati.txt", for each line creates an instance of <code>BandVotes</code> 
	 * and puts it into a list of <code>BandVotes</code>.
	 * The list is set as request attribute under the name "bandVotes".
	 * Also list of band values is searched for the one which has the maximum number of votes (or more if it's a tie), 
	 * the list of those band is created and is set as request attribute under the name "maxVotes".
	 * Further processing is forwarded to the "glasanjeRez.jsp".
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		
		List<BandVotes> votes = new ArrayList<>();
		
		int max = 0;
		
		if(!Files.exists(Paths.get(fileName))) {
			List<String> lines = Files.readAllLines(Paths.get(req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt")));
			List<String> fileContent = new ArrayList<>();
			
			for(String line : lines) {
				String[] parts = line.split("\t");
				fileContent.add(parts[1] + "\t0");
				Files.write(Paths.get(fileName), fileContent, StandardCharsets.UTF_8);
			}
		}
		
		List<String> lines = Files.readAllLines(Paths.get(fileName));

		for (String line : lines) {
			String[] parts = line.split("\t");
			votes.add(new BandVotes(parts[0], parts[1]));

			int votesMax = Integer.parseInt(parts[1]);
			if (votesMax > max) {
				max = votesMax;
			}
		}

		@SuppressWarnings("unchecked")
		List<Band> bands = (List<Band>) req.getSession().getAttribute("bands");
		List<Band> maxVotes = new ArrayList<>();
		
		if(bands == null) {
			bands = new ArrayList<>();
		}
		
		for(BandVotes vote : votes) {
			if(vote.getVotes().equals(String.valueOf(max))) {
				for(Band band : bands) {
					if(band.getName().equals(vote.getBand())) {
						maxVotes.add(band);
					}
				}
			}
		}
		
		req.setAttribute("maxVotes", maxVotes);
		req.setAttribute("bandVotes", votes);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}
	
	/**
	 * The Class BandVotes represents a single band and the number of votes he acquired.
	 */
	public static class BandVotes {
		
		/** The band name. */
		String band;
		
		/** The number of votes. */
		String votes;
		
		/**
		 * Instantiates a new band votes.
		 *
		 * @param band the band
		 * @param votes the votes
		 */
		public BandVotes(String band, String votes) {
			this.band = band;
			this.votes = votes;
		}
		
		/**
		 * Gets the band name.
		 *
		 * @return the band
		 */
		public String getBand() {
			return band;
		}
		
		/**
		 * Gets the number of votes.
		 *
		 * @return the votes
		 */
		public String getVotes() {
			return votes;
		}
	}
	
}
