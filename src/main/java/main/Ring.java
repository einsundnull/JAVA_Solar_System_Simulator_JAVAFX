package main;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;

public class Ring {

	private Group ringGroup; // Group to hold the ring
	private Cylinder ringOutside;
	private Cylinder ringInside;
	private double radiusOutside;
	private double radiusInside;
	private double height;
	private Color color;
	private PhysicsSphere attachedSphere; // To track the attached PhysicsSphere

	// Constructor to create a ring with custom parameters
	public Ring(double radiusOutside, double radiusInside, double height, Color color) {
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

		// Group both cylinders
		ringGroup = new Group(ringOutside, ringInside);
	}

	// Add ring to root
//    public void addToScene(Group root3D) {
//        root3D.getChildren().add(ringGroup);
//    }

	// Update the position of the ring
	// Update the position of the ring
	public void update(PhysicsSphere attachedSphere) {
	    double x = attachedSphere.getSphere().getTranslateX();
	    double y = attachedSphere.getSphere().getTranslateY();
	    double z = attachedSphere.getSphere().getTranslateZ();

	    // Apply transformation in a controlled manner to avoid jitter
	    ringGroup.setTranslateX(x);
	    ringGroup.setTranslateY(y);
	    ringGroup.setTranslateZ(z);
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

	public Group getRing() {
		return ringGroup;
	}
}
