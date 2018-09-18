package hr.fer.zemris.java.webserver.workers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * The Class CircleWorker implements IWebWorker. It creates a 200x200 circle and writes it to context. 
 */
public class CircleWorker implements IWebWorker {

	/**
	 * Creates a circle, prepares context for image output and calls the write method on the context.
	 */
	@Override
	public synchronized void processRequest(RequestContext context) throws Exception {
		
		context.setMimeType("image/png");
		context.setStatusCode(200);
		
		BufferedImage bim = new BufferedImage(200, 200, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g2d = bim.createGraphics();
		g2d.setColor(Color.decode("#ef5350"));
		g2d.drawOval(0, 0, 200, 200);
		g2d.fillOval(0, 0, 200, 200);
		g2d.dispose();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ImageIO.write(bim, "png", bos);
			int length = bos.size();
			context.setContentLength((long) length);
			context.write(bos.toByteArray(), 0, length);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
