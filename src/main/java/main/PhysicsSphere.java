package main;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

public class PhysicsSphere {

	private Sphere sphere;
	private double mass;
	private double radius;
	private PhongMaterial material;
	private Vector3D position;
	private Vector3D velocity;
	private double angularVelocity; // Angular velocity in radians per second

	public PhysicsSphere(double mass, double radius, Color color, double speedX, double speedY, double speedZ) {
		this.mass = mass;
		this.radius = radius;
		this.material = new PhongMaterial(color);

		// Initialize position and velocity vectors
		this.position = new Vector3D(0, 0, 0); // Starting at the origin
		this.velocity = new Vector3D(speedX, speedY, speedZ); // Initial speed

		// Create the sphere
		this.sphere = new Sphere(radius);
		this.sphere.setMaterial(material);
		this.sphere.setTranslateX(position.getX());
		this.sphere.setTranslateY(position.getY());
		this.sphere.setTranslateZ(position.getZ());
	}
	
	

	// Getters and setters for mass, radius, etc.
	public double getMass() {
		return mass;
	}

	public void setMass(double mass) {
		this.mass = mass;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public PhongMaterial getMaterial() {
		return material;
	}

	public void setMaterial(PhongMaterial material) {
		this.material = material;
		sphere.setMaterial(material);
	}

	public Vector3D getPosition() {
		return position;
	}

	public void setPosition(Vector3D position) {
		this.position = position;
		updateSpherePosition();
	}

	public Vector3D getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector3D velocity) {
		this.velocity = velocity;
	}

	public void setAngularVelocity(double angularVelocity) {
		this.angularVelocity = angularVelocity;
	}

	public double getAngularVelocity() {
		return angularVelocity;
	}

	// Update position using velocity and deltaTime
	public void updatePosition(double deltaTime) {
		// Move based on velocity
		position = position.add(velocity.multiply(deltaTime));
		updateSpherePosition();
	}

	// Update rotation using angular velocity and deltaTime
	public void updateRotation(double deltaTime, Vector3D axis) {
		double angle = angularVelocity * deltaTime; // Rotation in radians
		position = rotateAroundAxis(angle, axis);
		updateSpherePosition();
	}

	// Update sphere's graphical position
	private void updateSpherePosition() {
		this.sphere.setTranslateX(position.getX());
		this.sphere.setTranslateY(position.getY());
		this.sphere.setTranslateZ(position.getZ());
	}

	// Access the Sphere object
	public Sphere getSphere() {
		return sphere;
	}

	// Helper method for rotating the position around an axis
	public Vector3D rotateAroundAxis(double angle, Vector3D axis) {
		double cos = Math.cos(angle);
		double sin = Math.sin(angle);
		double oneMinusCos = 1.0 - cos;

		double x = position.getX();
		double y = position.getY();
		double z = position.getZ();

		double u = axis.getX();
		double v = axis.getY();
		double w = axis.getZ();

		// Compute rotation using the rotation matrix
		double newX = u * (u * x + v * y + w * z) * oneMinusCos + x * cos + (-w * y + v * z) * sin;
		double newY = v * (u * x + v * y + w * z) * oneMinusCos + y * cos + (w * x - u * z) * sin;
		double newZ = w * (u * x + v * y + w * z) * oneMinusCos + z * cos + (-v * x + u * y) * sin;

		return new Vector3D(newX, newY, newZ);
	}
}
