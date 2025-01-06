package main;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;

public class Ring2 {

	private Group ringGroup; // Group to hold the ring
	private Cylinder ringOutside;
	private Cylinder ringInside;
	private double radiusOutside;
	private double radiusInside;
	private double height;
	private Color color;
	private PhysicsSphere attachedSphere; // To track the attached PhysicsSphere

	// Constructor to create a ring with custom parameters
	public Ring2(double radiusOutside, double radiusInside, double height, Color color) {
		this.radiusOutside = radiusOutside;
		this.radiusInside = radiusInside;
		this.height = height;
		this.color = color;
		createRing();
	}

	// Create the ring (Cylinders) and apply materials
	private void createRing() {
		// Outer cylinder
		ringOutside = new Cylinder(radiusOutside, height);
		PhongMaterial outerMaterial = new PhongMaterial(color);
		ringOutside.setMaterial(outerMaterial);
		ringOutside.setRotationAxis(Rotate.X_AXIS);
		ringOutside.setRotate(0); // Rotate to lie flat

		// Inner cylinder (hole)
		ringInside = new Cylinder(radiusInside, height);
		PhongMaterial innerMaterial = new PhongMaterial(Color.BLACK); // Use black to represent the hole
		ringInside.setMaterial(innerMaterial);
		ringInside.setRotationAxis(Rotate.X_AXIS);
		ringInside.setRotate(0); // Rotate to lie flat

		
		   ringInside.setTranslateZ(-0.01); // Slight offset to prevent z-fighting
		// Group both cylinders
		ringGroup = new Group(ringOutside, ringInside);
	}

	// Set the position of the ring group in 3D space
	public void setPosition(PhysicsSphere physicsSphere) {
		ringGroup.setTranslateX(physicsSphere.getSphere().getTranslateX());
		ringGroup.setTranslateY(physicsSphere.getSphere().getTranslateY());
		ringGroup.setTranslateZ(physicsSphere.getSphere().getTranslateZ());
	}

	// Get the group representing the ring
	public Group getRingGroup() {
		return ringGroup;
	}

	// Attach the ring to a PhysicsSphere
//	public void attachToPhysicsSphere(PhysicsSphere physicsSphere) {
//		this.attachedSphere = physicsSphere;
//		// Immediately update the position of the ring
//		update(attachedSphere);
//	}

	// Update the ring's position based on the attached PhysicsSphere
	public void update(PhysicsSphere attachedSphere) {

		ringGroup.setTranslateX(attachedSphere.getSphere().getTranslateX());
		ringGroup.setTranslateY(attachedSphere.getSphere().getTranslateY());
		ringGroup.setTranslateZ(attachedSphere.getSphere().getTranslateZ());

	}

	// Optionally, you can add methods to adjust ring properties dynamically
	public void setColor(Color newColor) {
		PhongMaterial outerMaterial = (PhongMaterial) ringOutside.getMaterial();
		outerMaterial.setDiffuseColor(newColor);
	}

	public void setRadius(double newOuterRadius, double newInnerRadius) {
		ringOutside.setRadius(newOuterRadius);
		ringInside.setRadius(newInnerRadius);
	}

	public void setHeight(double newHeight) {
		ringOutside.setHeight(newHeight);
		ringInside.setHeight(newHeight);
	}
}
