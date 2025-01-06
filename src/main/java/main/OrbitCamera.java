package main;

import javafx.scene.PerspectiveCamera;
import javafx.scene.transform.Rotate;

public class OrbitCamera {
	private PerspectiveCamera camera;
	private PhysicsSphere target;
	private double orbitDistance;
	private double horizontalAngle; // Rotation around Y axis (yaw)
	private double verticalAngle; // Rotation around X axis (pitch)

	// Constants for controls
	private static final double MOUSE_SENSITIVITY = 0.5;
	private static final double ZOOM_SENSITIVITY = 50.0;

	public OrbitCamera(PhysicsSphere target, double initialDistance) {
		this.camera = new PerspectiveCamera(true);
		this.target = target;
		this.orbitDistance = initialDistance;
		this.horizontalAngle = 0;
		this.verticalAngle = 0;

		// Set up camera
		camera.setFarClip(50000);
		camera.setNearClip(0.1);
		updateCameraPosition();
	}

	public PerspectiveCamera getCamera() {
		return camera;
	}

	public void setTarget(PhysicsSphere newTarget) {
		this.target = newTarget;
		updateCameraPosition();
	}

	private void updateCameraPosition() {
		// Convert spherical coordinates to Cartesian
		double hRadians = Math.toRadians(horizontalAngle);
		double vRadians = Math.toRadians(verticalAngle);

		// Calculate camera position relative to target
		double x = orbitDistance * Math.cos(vRadians) * Math.sin(hRadians);
		double y = orbitDistance * Math.sin(vRadians);
		double z = orbitDistance * Math.cos(vRadians) * Math.cos(hRadians);

		// Update camera position relative to target's position
		camera.setTranslateX(target.getSphere().getTranslateX() + x);
		camera.setTranslateY(target.getSphere().getTranslateY() + y);
		camera.setTranslateZ(target.getSphere().getTranslateZ() + z);

		// Point camera at target
		camera.setRotationAxis(Rotate.Y_AXIS);
		camera.setRotate(horizontalAngle);

		Rotate xRotate = new Rotate(verticalAngle, Rotate.X_AXIS);
		camera.getTransforms().setAll(xRotate);
	}

	public void handleMouseDragged(double deltaX, double deltaY) {
		horizontalAngle += deltaX * MOUSE_SENSITIVITY;
		verticalAngle = Math.max(-85, Math.min(85, verticalAngle + deltaY * MOUSE_SENSITIVITY));
		updateCameraPosition();
	}

	public void handleScroll(double deltaY) {
		orbitDistance = Math.max(100, orbitDistance - deltaY * ZOOM_SENSITIVITY);
		updateCameraPosition();
	}

	public void update() {
		// Call this in animation timer to keep camera following target
		updateCameraPosition();
	}
}