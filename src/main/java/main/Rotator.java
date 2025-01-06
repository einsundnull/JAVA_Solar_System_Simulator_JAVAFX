package main;

import javafx.geometry.Point3D;
import javafx.scene.Node;

public class Rotator {
	private Node shapeToRotate;
	private Point3D rotationAxis;
	private double rotationSpeedX;
	private double rotationSpeedY;
	private double rotationSpeedZ;
	private double currentRotationX;
	private double currentRotationY;
	private double currentRotationZ;

	public Rotator(Node shapeToRotate, double rotationSpeedX, double rotationSpeedY, double rotationSpeedZ) {
		this.shapeToRotate = shapeToRotate;
		this.rotationSpeedX = rotationSpeedX;
		this.rotationSpeedY = rotationSpeedY;
		this.rotationSpeedZ = rotationSpeedZ;
	}

	public void updateRotation(double timeSpeed) {
		// Rotate around X-axis
		if (rotationSpeedX != 0) {
			shapeToRotate.setRotationAxis(new Point3D(1, 0, 0)); // X-axis
			currentRotationX += rotationSpeedX * timeSpeed;
			shapeToRotate.setRotate(currentRotationX);
		}

		// Rotate around Y-axis
		if (rotationSpeedY != 0) {
			shapeToRotate.setRotationAxis(new Point3D(0, 1, 0)); // Y-axis
			currentRotationY += rotationSpeedY * timeSpeed;
			shapeToRotate.setRotate(currentRotationY);
		}

		// Rotate around Z-axis
		if (rotationSpeedZ != 0) {
			shapeToRotate.setRotationAxis(new Point3D(0, 0, 1)); // Z-axis
			currentRotationZ += rotationSpeedZ * timeSpeed;
			shapeToRotate.setRotate(currentRotationZ);
		}
	}
}
