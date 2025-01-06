package main;

import javafx.scene.Node;

public class Mover {
	private Node shapeToMove; // Shape to move (PhysicsSphere)
	private Node rotationCenter; // Shape to orbit around (another PhysicsSphere)
	private double distance; // Distance between the shapes
	private double angularVelocity; // Speed of movement (how fast the shape moves around)
	private double angle; // Current angle (to calculate the position in orbit)
	private double verticalAmplitude; // Amplitude for vertical movement (Y-axis)
	private double verticalVelocity; // Vertical speed of movement
	private double time; // To keep track of the time for smooth movement

	public Mover() {
		this.time = 0; // Initialize time
	}

	public void move(Node rotationCenter, Node shapeToMove, double distance, double angularVelocity, double verticalAmplitude, double verticalVelocity) {
		this.shapeToMove = shapeToMove;
		this.rotationCenter = rotationCenter;
		this.distance = distance;
		this.angularVelocity = angularVelocity;
		this.angle = 0; // Start at angle 0
		this.verticalAmplitude = verticalAmplitude;
		this.verticalVelocity = verticalVelocity;
	}

	// Update the position of the shape based on its movement around the target
	// shape
	public void update(double timeSpeed) {
		// Update the angle for X and Z movement
		angle += angularVelocity * timeSpeed;
		if (angle >= 360) {
			angle -= 360; // Reset the angle after a full circle
		}

		// Calculate new position using polar coordinates (for X and Z)
		double radians = Math.toRadians(angle);
		double x = rotationCenter.getTranslateX() + distance * Math.cos(radians);
		double z = rotationCenter.getTranslateZ() + distance * Math.sin(radians);

		// Use time-based sine function for smooth vertical movement
		time += verticalVelocity; // Increase time based on vertical velocity
		double y = rotationCenter.getTranslateY() + verticalAmplitude * Math.sin(time);

		// Update the position of the shape
		shapeToMove.setTranslateX(x);
		shapeToMove.setTranslateY(y); // Update Y-axis
		shapeToMove.setTranslateZ(z);
	}
	
	public Node getMovedObject() {
		return shapeToMove;
	}
}
