package hr.fer.zemris.java.hw17.servlets;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The Class ThumbnailServlet.
 */
@WebServlet("/servlets/renderThumbnail")
public class ThumbnailServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String picName = req.getParameter("img");
		
		String thumbnails = req.getServletContext().getRealPath(ExtractorUtil.THUMBNAILS_LOCATION);
		String pictures = req.getServletContext().getRealPath(ExtractorUtil.PICTURES_LOCATION);
		Path thumbnailsPath = Paths.get(thumbnails);
		
		if(!Files.isDirectory(thumbnailsPath)) {
			Files.createDirectory(thumbnailsPath);
		}

		if (!Files.exists(Paths.get(thumbnails + picName))) {
			generateThumbnail(thumbnails, pictures, picName);
		}

		resp.setContentType("image/png");
		
		try(InputStream is = Files.newInputStream(Paths.get(thumbnails + picName));
				OutputStream os = resp.getOutputStream()) {
			
			byte[] buff = new byte[1024];
			while(true) {
				int r = is.read(buff);
				if(r < 1) break;
			
				os.write(buff, 0, r);
			}
		}
//		BufferedImage image = ImageIO.read(new File(thumbnails + picName));
//		ImageIO.write(image, "png", resp.getOutputStream());
	}
	
	/**
	 * Generate thumbnail.
	 *
	 * @param thumbnails the thumbnails
	 * @param pictures the pictures
	 * @param picName the pic name
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void generateThumbnail(String thumbnails, String pictures, String picName) throws IOException {
		
		BufferedImage image = ImageIO.read(new File(pictures + "/" + picName));
		Image img = image.getScaledInstance(150, 150, Image.SCALE_DEFAULT);
		image = createBufferedImage(img);
		OutputStream os = Files.newOutputStream(Paths.get(thumbnails + picName));
		ImageIO.write(image, "png", os);
	}

	/**
	 * Creates the buffered image.
	 *
	 * @param img the img
	 * @return the buffered image
	 */
	private BufferedImage createBufferedImage(Image img) {
		BufferedImage bufferedImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);

		Graphics g = bufferedImage.createGraphics();
		g.drawImage(img, 0, 0, null);
		g.dispose();
		return bufferedImage;
	}
}
