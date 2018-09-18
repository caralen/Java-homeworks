package hr.fer.zemris.java.hw17.servlets;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The Class PictureServlet.
 */
@WebServlet("/servlets/renderPicture")
public class PictureServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String picName = req.getParameter("img");
		
		String pictures = req.getServletContext().getRealPath(ExtractorUtil.PICTURES_LOCATION);
		
		BufferedImage image = ImageIO.read(new File(pictures + "/" + picName));
		resp.setContentType("image/png");
		ImageIO.write(image, "png", resp.getOutputStream());
	}
}
