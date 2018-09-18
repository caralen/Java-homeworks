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

/**
 * The Class GlasanjeGlasajServlet is a servlet which updates the glasanje-rezultati.txt file 
 * which keeps record of band names and user's votes. File is updated based on the parameters in the URL.
 */
@WebServlet("/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Takes one parameter from the request under the name "id".
	 * If the file glasanje-rezultati.txt exists it goes through lines of the file and updates the line
	 * which has the name of the band with the id from the parameters.
	 * If the file doesn't exist it is created with band names from the glasanje-definicija.txt and values 0, 
	 * except the one with the id the same as the one from arguments.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String ID = req.getParameter("id");
		
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		
		List<String> lines = Files.readAllLines(Paths.get(req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt")));
		List<String> fileContent = new ArrayList<>();
		
		if(Files.exists(Paths.get(fileName))) {
			fileContent = Files.readAllLines(Paths.get(fileName));
			
			String bandName = null;
			for(String line : lines) {
				if(line.startsWith(ID)) {
					bandName = line.split("\t")[1];
				}
			}
			
			for (int i = 0; i < fileContent.size(); i++) {
				String line = fileContent.get(i);
			    if (line.startsWith(bandName)) {
			    	String[] parts = line.split("\t");
			    	String newLine = parts[0] + "\t" + (Integer.parseInt(parts[1]) + 1);
			        fileContent.set(i, newLine);
			        break;
			    }
			}

		} else {
			
			for(String line : lines) {
				String[] parts = line.split("\t");
				if (Integer.parseInt(parts[0]) == Integer.parseInt(ID)) {
					fileContent.add(parts[1] + "\t1");
				} else {
					fileContent.add(parts[1] + "\t0");
				}
			}
		}
		Files.write(Paths.get(fileName), fileContent, StandardCharsets.UTF_8);
		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
	}
}
