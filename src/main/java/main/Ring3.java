package main;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Ring3 {

	private Group ringGroup; // Group to hold the ring
	private Circle outerCircle;
	private Circle innerCircle;
	private double radiusOutside;
	private double radiusInside;
	private Color color;

	// Constructor to create a ring with custom parameters
	public Ring3(double radiusOutside, double radiusInside, Color color) {
		this.radiusOutside = radiusOutside;
		this.radiusInside = radiusInside;
		this.color = color;
		createRing();
	}

	// Create the ring (circles) and apply materials
	private void createRing() {
		// Outer circle
		outerCircle = new Circle(radiusOutside);
		outerCircle.setFill(color);

		// Inner circle
		innerCircle = new Circle(radiusInside);
		innerCircle.setFill(Color.TRANSPARENT);
		innerCircle.setStroke(color); // To ensure the inner circle blends visually with the outer
//		innerCircle.setStrokeWidth(1.0);

		// Group both circles
		ringGroup = new Group(outerCircle, innerCircle);
	}

	// Set the position of the ring group in 2D or 3D space
	public void setPosition(double x, double y, double z) {
		ringGroup.setTranslateX(x);
		ringGroup.setTranslateY(y);
		ringGroup.setTranslateZ(z);
	}

	// Update the ring's position based on the provided PhysicsSphere
	public void update(PhysicsSphere physicsSphere) {
		setPosition(physicsSphere.getSphere().getTranslateX(), physicsSphere.getSphere().getTranslateY(), physicsSphere.getSphere().getTranslateZ());
	}

	// Get the group representing the ring
	public Group getRingGroup() {
		return ringGroup;
	}

	// Optionally, you can add methods to adjust ring properties dynamically
	public void setColor(Color newColor) {
		outerCircle.setFill(newColor);
		innerCircle.setStroke(newColor); // Match stroke color to the new fill
	}

	public void setRadius(double newOuterRadius, double newInnerRadius) {
		outerCircle.setRadius(newOuterRadius);
		innerCircle.setRadius(newInnerRadius);
	}
}
