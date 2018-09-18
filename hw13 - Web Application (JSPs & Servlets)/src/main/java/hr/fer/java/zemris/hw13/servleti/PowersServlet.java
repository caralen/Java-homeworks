package hr.fer.java.zemris.hw13.servleti;

import static java.lang.Math.pow;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Servlet used for generating an xls file with calculated powers of numbers in given range.
 * @author Alen Carin
 *
 */
@WebServlet("/powers")
public class PowersServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Takes three parameters from the request: a, b and n.
	 * Calculates powers of numbers in interval [a,b].
	 * Creates and xml file that contains n sheets.
	 * In each sheet the first column are numbers from min(a,b) to max(a,b) and
	 * the in the second column are i-th powers of the number in that row, where i is the sheet number.
	 *
	 * @param req the request
	 * @param resp the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String a = req.getParameter("a");
		String b = req.getParameter("b");
		String n = req.getParameter("n");

		int aValue = Integer.parseInt(a);
		int bValue = Integer.parseInt(b);
		int nValue = Integer.parseInt(n);

		if (aValue < -100 || aValue > 100) {
			req.setAttribute("message", "a must be from the interval [-100,100]");
			req.getRequestDispatcher("/WEB-INF/pages/message.jsp").forward(req, resp);
			return;
		}
		if (bValue < -100 || bValue > 100) {
			req.setAttribute("message", "a must be from the interval [-100,100]");
			req.getRequestDispatcher("/WEB-INF/pages/message.jsp").forward(req, resp);
			return;
		}
		if (nValue < 1 || nValue > 5) {
			req.setAttribute("message", "n must be from the interval [1,5]");
			req.getRequestDispatcher("/WEB-INF/pages/message.jsp").forward(req, resp);
			return;
		}

		HSSFWorkbook hwb = new HSSFWorkbook();

		for (int i = 1; i <= nValue; i++) {
			HSSFSheet sheet = hwb.createSheet("sheet" + i);

			int step = bValue > aValue ? 1 : -1;
			int counter = 0;

			HSSFRow rowhead = sheet.createRow(counter++);
			rowhead.createCell(0).setCellValue("number");
			rowhead.createCell(1).setCellValue("power");
			
			for (int j = aValue; j < bValue; j += step) {
				HSSFRow row = sheet.createRow(counter++);
				row.createCell(0).setCellValue(j);
				row.createCell(1).setCellValue(pow(j, i));
			}
		}
		resp.setContentType("application/octet-stream");
		resp.setHeader("Content-Disposition", "attachment;filename=\"tablica.xls\"");

		hwb.write(resp.getOutputStream());
		hwb.close();
	}
}
