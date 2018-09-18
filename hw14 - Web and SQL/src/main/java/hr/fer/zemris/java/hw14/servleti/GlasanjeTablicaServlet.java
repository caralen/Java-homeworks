package hr.fer.zemris.java.hw14.servleti;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.model.PollOption;

/**
 * The Class GlasanjeTablicaServlet is a servlet which generates an xls file 
 * from the contents of the votingDB database.
 */
@WebServlet("/servleti/glasanje-xls")
public class GlasanjeTablicaServlet extends HttpServlet {

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
		
		List<PollOption> options = DAOProvider.getDao().getOptionsForPollId(pollID);
		Collections.sort(options, (e1,e2) -> Long.compare(e2.getVotesCount(), e1.getVotesCount()));

		HSSFWorkbook hwb = new HSSFWorkbook();
		HSSFSheet sheet = hwb.createSheet("Voting results");

		HSSFRow rowhead = sheet.createRow(0);
		rowhead.createCell(0).setCellValue("Bend");
		rowhead.createCell(1).setCellValue("Broj glasova");

		int counter = 1;
		for (PollOption option : options) {
			HSSFRow row = sheet.createRow(counter++);
			row.createCell(0).setCellValue(option.getOptionTitle());
			row.createCell(1).setCellValue(option.getVotesCount());
		}

		resp.setContentType("application/octet-stream");
		resp.setHeader("Content-Disposition", "attachment;filename=\"glasanje.xls\"");

		hwb.write(resp.getOutputStream());
		hwb.close();

	}
}
