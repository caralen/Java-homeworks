package hr.fer.java.zemris.hw13.servleti.glasanje;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * The Class GlasanjeTablicaServlet is a servlet which generates an xls file 
 * from the contents of the file "glasanje-rezultati.txt".
 */
@WebServlet("/glasanje-xls")
public class GlasanjeTablicaServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates an xls file where the first row is a description.
	 * The first column are band names and the second column contains band votes.
	 * The xls file is written to response output stream.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");

		List<String> lines = Files.readAllLines(Paths.get(fileName));

		HSSFWorkbook hwb = new HSSFWorkbook();
		HSSFSheet sheet = hwb.createSheet("Voting results");

		HSSFRow rowhead = sheet.createRow(0);
		rowhead.createCell(0).setCellValue("Bend");
		rowhead.createCell(1).setCellValue("Broj glasova");

		int counter = 1;
		for (String line : lines) {
			String[] parts = line.split("\t");
			
			HSSFRow row = sheet.createRow(counter++);
			row.createCell(0).setCellValue(parts[0]);
			row.createCell(1).setCellValue(parts[1]);
		}

		resp.setContentType("application/octet-stream");
		resp.setHeader("Content-Disposition", "attachment;filename=\"glasanje.xls\"");

		hwb.write(resp.getOutputStream());
		hwb.close();

	}
}
