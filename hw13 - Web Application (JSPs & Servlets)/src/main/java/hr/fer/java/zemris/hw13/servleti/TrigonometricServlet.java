package hr.fer.java.zemris.hw13.servleti;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static java.lang.Math.sin;
import static java.lang.Math.cos;
import static java.lang.Math.toRadians;;

/**
 * Servlet which is used for calculating sin and cos values of numbers in given range.
 */
@WebServlet("/trigonometric")
public class TrigonometricServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Gets two parameters from the request: a and b.
	 * Creates a list of <code>TrigonometricValues</code> which contains the value of the number, its sin value and its cos value.
	 * List is calculated for the numbers in range from min(a,b) to max(a,b).
	 * The list is set as request attribute "trigonometricValues" and then the request is forwarded to the trigonometric.jsp.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String a = req.getParameter("a");
		String b = req.getParameter("b");
		
		int aValue = Integer.parseInt(a);
		int bValue = Integer.parseInt(b);
		
		if (a == null) {
			a = "0";
		}
		if (b == null) {
			b = "360";
		}
		if (aValue > bValue) {
			int c = aValue;
			aValue = bValue;
			bValue = c;
		}
		if (bValue > aValue + 720) {
			bValue = aValue + 720;
		}
		
		List<TrigonometricValues> values = new ArrayList<>();
		
		for(int i = aValue; i <= bValue; i++) {
			values.add(new TrigonometricValues(i, sin(toRadians(i)), cos(toRadians(i))));
		}
		req.setAttribute("trigonometricValues", values);
		req.getRequestDispatcher("/WEB-INF/pages/trigonometric.jsp").forward(req, resp);
	}
	
	/**
	 * The Class TrigonometricValues is a static class which represents a number and its sin and cos values.
	 */
	public static class TrigonometricValues {
		
		/** The number. */
		int number;
		
		/** The sin of the number. */
		double sinValue;
		
		/** The cos of the number. */
		double cosValue;
		
		/**
		 * Instantiates a new trigonometric values.
		 *
		 * @param number the number
		 * @param sinValue the sin value
		 * @param cosValue the cos value
		 */
		public TrigonometricValues(int number, double sinValue, double cosValue) {
			this.number = number;
			this.sinValue = sinValue;
			this.cosValue = cosValue;
		}

		/**
		 * Gets the number.
		 *
		 * @return the number
		 */
		public int getNumber() {
			return number;
		}

		/**
		 * Gets the sin value.
		 *
		 * @return the sin value
		 */
		public double getSinValue() {
			return sinValue;
		}
		
		/**
		 * Gets the cos value.
		 *
		 * @return the cos value
		 */
		public double getCosValue() {
			return cosValue;
		}
	}
}
