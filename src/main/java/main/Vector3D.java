package main;

public class Vector3D {
	private double x, y, z;

	// Constructor
	public Vector3D(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	// Getter and setter methods for x, y, and z
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setZ(double z) {
		this.z = z;
	}

	// Add two vectors
	public Vector3D add(Vector3D other) {
		return new Vector3D(this.x + other.x, this.y + other.y, this.z + other.z);
	}

	// Subtract two vectors
	public Vector3D subtract(Vector3D other) {
		return new Vector3D(this.x - other.x, this.y - other.y, this.z - other.z);
	}

	// Scalar multiplication (multiplying vector by a scalar)
	public Vector3D multiply(double scalar) {
		return new Vector3D(this.x * scalar, this.y * scalar, this.z * scalar);
	}

	// Dot product
	public double dot(Vector3D other) {
		return this.x * other.x + this.y * other.y + this.z * other.z;
	}

	// Normalize the vector (to make it unit length)
	public Vector3D normalize() {
		double length = Math.sqrt(x * x + y * y + z * z);
		return new Vector3D(x / length, y / length, z / length);
	}

	// Get the length (magnitude) of the vector
	public double length() {
		return Math.sqrt(x * x + y * y + z * z);
	}
}
