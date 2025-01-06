package main;


import javafx.scene.Node;
import javafx.scene.transform.Rotate;

public class Precessor {

	private double precessionSpeed; // Speed of precession in degrees per time step
	private double axialTilt; // Current axial tilt (in degrees)
	private Node planet;

	public Precessor(Node planet, double initialAxialTilt, double precessionSpeed) {
		this.planet = planet;
		this.axialTilt = initialAxialTilt;
		this.precessionSpeed = precessionSpeed;
	}

	// Update precession: this will modify the axial tilt or orientation of the
	// planet.
	public void updatePrecession(double timeSpeed) {
		// Apply precession by rotating the planet around the Y-axis
		// Adjust the axial tilt incrementally by applying precession speed over time
		axialTilt += precessionSpeed * timeSpeed;

		// Apply the precession to the planet
		Rotate precessionRotation = new Rotate(axialTilt, Rotate.Y_AXIS);
		planet.getTransforms().setAll(precessionRotation);
	}
}
