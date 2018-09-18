package hr.fer.zemris.java.raytracer;

import static java.lang.Math.pow;

import java.util.List;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;

/**
 * Util class that is used for calculating color for a single pixel of screen.
 * It uses Phong's reflection model for calculation of color.
 * 
 * <p>
 * These are the steps of color calculation for given ray which goes from eye to the current pixel:
 * <li> Closest intersection with an object in scene is calculated 
 * <li> If there is no intersections current pixel is black
 * <li> Else contribution of each light source is calculated
 * <li> If there is an object between saved intersection and light source then the light source is skipped
 * <li> Else phong's model components are calculated and saved in red, green and blue color arrays
 * 
 * @author Alen Carin
 *
 */
public class Util {
	
	private static final double TOLERANCE = 1E-6;

	/**
	 * Method used for finding the intersections of a given ray with objects in the given scene.
	 * When there is an intersection appropriate color will be stored in the rgb array, 
	 * otherwise black color is stored.
	 * When there are multiple intersections, the closest one is stored.
	 * @param scene where the graphical objects and light sources are located
	 * @param ray of light
	 * @param rgb array of red, green and blue colors
	 */
	protected static void tracer(Scene scene, Ray ray, short[] rgb, Point3D eye) {
		
		double minDistance = Double.MAX_VALUE;
		RayIntersection savedIntersection = null;

		List<GraphicalObject> objects = scene.getObjects();
		for (GraphicalObject object : objects) {
			RayIntersection intersection = object.findClosestRayIntersection(ray);

			if (intersection != null && intersection.getDistance() < minDistance) {
				savedIntersection = intersection;
				minDistance = intersection.getDistance();
			}
		}
		if (savedIntersection == null) {
			rgb[0] = rgb[1] = rgb[2] = 0;
		} else {
			determineColorFor(scene, savedIntersection, rgb, ray, eye);
		}
	}

	/**
	 * Checks if there is a object in scene between each light source and given intersection.
	 * If there isn't calculateColor method is called, else light source is skipped.
	 * 
	 * @param scene that contains objects and light sources
	 * @param savedIntersection is the closest intersection of ray that goes from eye to pixel and objects in scene
	 * @param rgb array of colors
	 * @param ray between eye and pixel
	 * @param eye position of the eye
	 */
	private static void determineColorFor(Scene scene, RayIntersection savedIntersection, short[] rgb, Ray ray, Point3D eye) {
		List<LightSource> lights = scene.getLights();
		List<GraphicalObject> objects = scene.getObjects();
		
		rgb[0] = rgb[1] = rgb[2] = 15;
		
		for(LightSource light : lights) {
			RayIntersection closerIntersection = findCloserIntersection(savedIntersection, light, objects);
			
			if(closerIntersection == null) {
				calculateColor(savedIntersection, rgb, light, eye);
			}
		}
	}


	/**
	 * Calculates color for this pixel based on phong's reflection model.
	 * Contribution of difuse and specular components is calculated and summed.
	 *  
	 * @param intersection intersection of the array and object
	 * @param rgb array of colors
	 * @param light light source
	 * @param eye the position of the eye
	 */
	private static void calculateColor(RayIntersection intersection, short[] rgb,
			LightSource light, Point3D eye) {
		
		Point3D l = light.getPoint().sub(intersection.getPoint()).normalize();
		Point3D n = intersection.getNormal();
		double theta = l.scalarProduct(n) <= 0 ? 0 : l.scalarProduct(n);
		
		Point3D r = l.sub(n.scalarMultiply(theta).scalarMultiply(2));
		Point3D v = eye.sub(intersection.getPoint()).normalize();
		
		double coefficient = pow(r.scalarProduct(v), intersection.getKrn());
		
		if(theta > 0) {
			rgb[0] += light.getR() * intersection.getKdr() * theta + light.getR() * intersection.getKrr() * coefficient;
			rgb[1] += light.getG() * intersection.getKdg() * theta + light.getG() * intersection.getKrg() * coefficient;
			rgb[2] += light.getB() * intersection.getKdb() * theta + light.getB() * intersection.getKrb() * coefficient;
		}
	}


	/**
	 * Searches for the closer intersection of ray from light source to given savedIntersection than
	 * the saved the saved intersection with that same ray.
	 * 
	 * @param savedIntersection given intersection in the scene
	 * @param light the light source
	 * @param objects list of objets in scene
	 * @return
	 */
	private static RayIntersection findCloserIntersection(RayIntersection savedIntersection, 
			LightSource light, List<GraphicalObject> objects) {
		
		Ray lightRay = Ray.fromPoints(light.getPoint(), savedIntersection.getPoint());
		RayIntersection closestIntersection = null;
		double closestDistance = (light.getPoint().sub(savedIntersection.getPoint())).norm();

		for(GraphicalObject object : objects) {
			RayIntersection intersection = object.findClosestRayIntersection(lightRay);
			
			if(intersection != null && intersection.getDistance()+TOLERANCE < closestDistance) {
				closestDistance = intersection.getDistance();
				closestIntersection = intersection;
			}
		}
		return closestIntersection;
	}
}
