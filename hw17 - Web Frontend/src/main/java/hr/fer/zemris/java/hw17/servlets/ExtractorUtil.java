package hr.fer.zemris.java.hw17.servlets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;

import hr.fer.zemris.java.hw17.model.Picture;

/**
 * The Class ExtractorUtil is a utility class used for extracting information from files.
 */
public class ExtractorUtil {
	
	/** The Constant DESCRIPTOR_LOCATION. */
	public static final String DESCRIPTOR_LOCATION = "/WEB-INF/slike/opisnik.txt";
	
	/** The Constant THUMBNAILS_LOCATION. */
	public static final String THUMBNAILS_LOCATION = "/WEB-INF/slike/thumbnails";
	
	/** The Constant PICTURES_LOCATION. */
	public static final String PICTURES_LOCATION = "/WEB-INF/slike";
	
	/** Application servlet context */
	public static ServletContext CONTEXT;
	
	
	/**
	 * Gets the number of pictures.
	 *
	 * @return the number of pictures
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static int getNumberOfPictures() throws IOException {
		Path path = Paths.get(CONTEXT.getRealPath(DESCRIPTOR_LOCATION));
		List<String> lines = Files.readAllLines(path);
		return lines.size() / 3;
	}
	
	/**
	 * Gets the picture.
	 *
	 * @param index the index
	 * @return the picture
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static Picture getPicture(int index) throws IOException {
		List<Picture> pictures = getAllPictures();
		return pictures.get(index);
	}

	public static List<Picture> getAllPicturesForTag(String tag) throws IOException {
		
		List<Picture> pictures = getAllPictures();
		List<Picture> picturesWithTag = new ArrayList<>();
		
		boolean hasTag = false;
		for(Picture pic : pictures) {
			hasTag = false;
			
			for(String t : pic.getTags()) {
				if(t.equals(tag)) {
					hasTag = true;
					break;
				}
			}
			if(hasTag) {
				picturesWithTag.add(pic);
			}
		}
		return picturesWithTag;
	}
	
	/**
	 * Returns a list of Picture objects. 
	 * The list contains all Picture objects created from the desciptor file.
	 * @return the list of Picture objects
	 * @throws IOException
	 */
	public static List<Picture> getAllPictures() throws IOException{
		Path path = Paths.get(CONTEXT.getRealPath(DESCRIPTOR_LOCATION));
		
		List<String> lines = Files.readAllLines(path);
		List<Picture> pictures = new ArrayList<>();
		
		int counter = 1;
		StringBuilder sb = new StringBuilder();
		
		for(String line : lines) {
			sb.append(line);
			sb.append("#");
			
			if(counter % 3 == 0) {
				String[] parts = sb.toString().split("#");
				
				String name = parts[0].trim().replace("\\s+", "");
				String description = parts[1].trim();
				String[] tags = parts[2].replaceAll("\\s+", "").split(",");
				
				pictures.add(new Picture(name, description, tags));
				sb.setLength(0);
			}
			counter++;
		}
		return pictures;
	}
	
	/**
	 * Gets the all tags.
	 *
	 * @return the all tags
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static Set<String> getAllTags() throws IOException{
		Set<String> tags = new HashSet<>();
		Path path = Paths.get(CONTEXT.getRealPath(DESCRIPTOR_LOCATION));
		
		int counter = 1;
		List<String> lines = Files.readAllLines(path);
		
		for(String line : lines) {
			if(counter % 3 == 0) {
				String[] parts = line.trim().replaceAll("\\s+", "").split(",");
				for(String part : parts) {
					tags.add(part);
				}
			}
			counter++;
		}
		return tags;
	}
	
	/**
	 * Gets the picture by name.
	 *
	 * @param pictureName the picture name
	 * @return the picture by name
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static Picture getPictureByName(String pictureName) throws IOException {
		List<Picture> pictures = getAllPictures();
		
		for(Picture pic : pictures) {
			if(pic.getName().equals(pictureName)) {
				return pic;
			}
		}
		return null;
	}
	
}
