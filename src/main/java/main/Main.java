package main;

import java.util.ArrayList;

import org.jcp.xml.dsig.internal.dom.DOMCanonicalXMLC14NMethod;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Point3D;
import javafx.geometry.Rectangle2D;
import javafx.scene.DepthTest;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

	private static final double CAMERA_SPEED = 10;
	private static final double CAMERA_SPEED_SCROLL = 50;
	private static final double MOUSE_SENSITIVITY = 0.1;

	private double cameraX,cameraY,cameraZ;
	private double mouseX, mouseY;
	private double yaw = 63.80; // Set yaw to 103.1
	private double pitch = 63.80; // Set pitch to -90.0
	double distance = 10.0; // distance between the camera and earth
	double angle = 0; // angle for the camera's orbit

	Rotate rotateYaw;
	Rotate rotatePitch;

	private double screenWidth;
	private double screenHeight;
//	private Mover moverI;
//	private Mover moverII;
//	private Mover moverIII;
	public boolean pause = false;
	private PhysicsSphere mercury;
	private PhysicsSphere venus;
	private PhysicsSphere mars;
	private PhysicsSphere earth;
	private PhysicsSphere jupiter;
	private PhysicsSphere saturn;
	private PhysicsSphere uranus;
	private PhysicsSphere neptune;
	private PhysicsSphere moon;
	private PhysicsSphere io;
	private PhysicsSphere europa;
	private PhysicsSphere sun;

	private PhysicsSphere cameraObject;

	private Trace sunTrace;
	private Trace mercuryTrace;
	private Trace venusTrace;
	private Trace earthTrace;
	private Trace marsTrace;
	private Trace jupiterTrace;
	private Trace saturnTrace;
	private Trace uranusTrace;
	private Trace neptuneTrace;
	private Trace moonTrace;
	private Trace ioTrace;
	private Trace europaTrace;

	private Ring ringJupiter;

	private Mover sunToEarthMover;
	private Mover earthToMoonMover;
	private Mover sunToMercuryMover;
	private Mover sunToVenusMover;
	private Mover sunToMarsMover;
	private Mover sunToJupiterMover;
	private Mover sunToSaturnMover;
	private Mover sunToUranusMover;
	private Mover sunToNeptuneMover;
	private Mover jupiterToIoMover;
	private Mover jupiterToEuropaMover;

	private Rotator sunRotator;
	private Rotator mercuryRotator;
	private Rotator venusRotator;
	private Rotator earthRotator;
	private Rotator moonRotator;
	private Rotator marsRotator;
	private Rotator jupiterRotator;
	private Rotator saturnRotator;
	private Rotator uranusRotator;
	private Rotator neptuneRotator;

	private Rotator ioRotator;
	private Rotator europaRotator;

	private Precessor sunPrecessor;
	private Precessor mercuryPrecessor;
	private Precessor venusPrecessor;
	private Precessor earthPrecessor;
	private Precessor marsPrecessor;
	private Precessor jupiterPrecessor;
	private Precessor saturnPrecessor;
	private Precessor uranusPrecessor;
	private Precessor neptunePrecessor;

	private static final double MERCURY_RADIUS = 2.4397; // Mercury radius in km
	private static final double VENUS_RADIUS = 6.0518; // Venus radius in km
	private static final double EARTH_RADIUS = 6.371; // Earth radius in km
	private static final double MARS_RADIUS = 3.3895; // Mars radius in km
	private static final double JUPITER_RADIUS = 69.911; // Jupiter radius in km
	private static final double SATURN_RADIUS = 58.232; // Saturn radius in km
	private static final double URANUS_RADIUS = 25.362; // Uranus radius in km
	private static final double NEPTUNE_RADIUS = 24.622; // Neptune radius in km
	private static final double MOON_RADIUS = 1.7371; // Moon radius in km

	// Constants for the orbital distances of planets (in millions of kilometers)
	private static final double MERCURY_DISTANCE = 57.91; // Mercury's distance from the Sun in millions of km
	private static final double VENUS_DISTANCE = 108.2; // Venus's distance from the Sun in millions of km
	private static final double EARTH_DISTANCE = 149.6; // Earth's distance from the Sun in millions of km
	private static final double MARS_DISTANCE = 227.9; // Mars's distance from the Sun in millions of km
	private static final double JUPITER_DISTANCE = 778.6; // Jupiter's distance from the Sun in millions of km
	private static final double SATURN_DISTANCE = 1434; // Saturn's distance from the Sun in millions of km
	private static final double URANUS_DISTANCE = 2871; // Uranus's distance from the Sun in millions of km
	private static final double NEPTUNE_DISTANCE = 4495; // Neptune's distance from the Sun in millions of km
	private static final double MOON_DISTANCE = 0.3844; // Moon's distance from Earth in millions of km

	// Constants for orbital periods (in days)
	private static final double MERCURY_PERIOD = 87.97; // Mercury orbital period in days
	private static final double VENUS_PERIOD = 224.7; // Venus orbital period in days
	private static final double EARTH_PERIOD = 365.24; // Earth orbital period in days
	private static final double MARS_PERIOD = 687; // Mars orbital period in days
	private static final double JUPITER_PERIOD = 4333; // Jupiter orbital period in days
	private static final double SATURN_PERIOD = 10759; // Saturn orbital period in days
	private static final double URANUS_PERIOD = 30687; // Uranus orbital period in days
	private static final double NEPTUNE_PERIOD = 60190; // Neptune orbital period in days
	private static final double MOON_PERIOD = 27.3; // Moon orbital period around Earth in days

	// Scaling factors
	private static final double DISTANCE_SCALE = 1e-6; // Scale for orbital distances (affects the size of the orbit)
	private static final double SPEED_SCALE = 1e-2; // Scale for angular velocity (affects rotation speed)
	// Calculate angular velocities
	private static final double EARTH_ANGULAR_VELOCITY = 2 * Math.PI / EARTH_PERIOD; // radians per day
	private static final double MOON_ANGULAR_VELOCITY = EARTH_ANGULAR_VELOCITY * (EARTH_PERIOD / MOON_PERIOD);
	private double simulationTime;
//	private double timeSpeed = 525960; // Earth completes orbit in 60 seconds if the distance is set to real values
	private double timeSpeed = 525960 * (30 / EARTH_DISTANCE); // Earth completes orbit in 60 seconds if the distance is
																// set to real values

	private AnimationTimer animationTimer;
	private PerspectiveCamera camera;
	ArrayList<PerspectiveCamera> cameras;
	private Group world;
	private SubScene subScene;
	private Pane rootPane;
	private Group root;
	private Stage primaryStage;

	private Text simulationTimeText;
	private int earthRotations = 0;
	private int moonOrbits = 0;
	private Mover cameraMover;

	@Override
	public void start(Stage primaryStage) {

		timeSpeed = 525960 * (30 / 149600000);
		timeSpeed = 525960 * 0.0002000666;
		timeSpeed = 105.2;

		this.primaryStage = primaryStage;
		world = new Group();
		setPlanets();
		setTraces();
		setPlanetsPhongMaterial();
		setRing();
		setMover();
		setRotator();
		setPrecessor();
		setCamera();
		setPlanetsToRootPane();
		setRingsToRootPane();

		// Set camera and subscene configuration
		this.primaryStage.initStyle(StageStyle.UNDECORATED);
		Rectangle2D screenBounds = Screen.getPrimary().getBounds();
		screenWidth = screenBounds.getWidth();
		screenHeight = screenBounds.getHeight();

		subScene = new SubScene(world, screenWidth, screenHeight, true, SceneAntialiasing.BALANCED);
		subScene.setFill(Color.BLACK);
		subScene.setCamera(camera);

		root = new Group();

		// Create and add the simulation time text
		simulationTimeText = new Text();
		simulationTimeText.setFont(Font.font(20));
		simulationTimeText.setFill(Color.WHITE);
		simulationTimeText.setTranslateY(10); // Adjust the Y translation
		simulationTimeText.setTranslateX(10); // Adjust the X translation

		// Create a Pane to hold the simulationTimeText (this Pane remains in 2D space)
		Pane timeTextPane = new Pane(simulationTimeText);
		timeTextPane.setTranslateX(10); // Position it at the center horizontally
		timeTextPane.setTranslateY(50); // Adjust vertical position as needed

		// Always rotate the text to face the camera (180 degrees)
		Rotate rotateY = new Rotate(180, Rotate.Y_AXIS);
//	    simulationTimeText.getTransforms().addAll(rotateY);

		// Add the Pane containing the simulationTimeText to the root
		root.getChildren().add(subScene);
		root.getChildren().add(timeTextPane); // Add the Pane with the text

		rootPane = new Pane();
		rootPane.setDepthTest(DepthTest.ENABLE);
		rootPane.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));

		Scene scene = new Scene(root, screenWidth, screenHeight, true);

		// Event handlers for camera movement and rotation
		handleKeyboardInput(scene, camera);
		handleMouseInput(scene, camera);

		// Configure and show the stage
		this.primaryStage.setTitle("JavaFX 3D Environment");
		this.primaryStage.setScene(scene);
		this.primaryStage.setFullScreen(true);
		this.primaryStage.show();

		// Set animation
		setAnimation();

		// Always update the text to face the camera
	}

	private void setAnimation() {
		animationTimer = new AnimationTimer() {

			@Override
			public void handle(long now) {
				if (!pause) {
					// Update the position of each sphere
					sunToMercuryMover.update(timeSpeed);
					sunToVenusMover.update(timeSpeed);
					sunToEarthMover.update(timeSpeed);
					sunToMarsMover.update(timeSpeed);
					sunToJupiterMover.update(timeSpeed);
					sunToSaturnMover.update(timeSpeed);
					sunToUranusMover.update(timeSpeed);
					sunToNeptuneMover.update(timeSpeed);

					// Update the position of the moons
					earthToMoonMover.update(timeSpeed);
					jupiterToIoMover.update(timeSpeed);
					jupiterToEuropaMover.update(timeSpeed);

					cameraMover.update(timeSpeed);
					ringJupiter.update(saturn);
					
					System.out.println(cameraMover.getMovedObject().getTranslateX());
					camera.setTranslateX(cameraX + cameraMover.getMovedObject().getTranslateX());
					camera.setTranslateY(cameraY +cameraMover.getMovedObject().getTranslateY());
					camera.setTranslateZ(cameraZ +cameraMover.getMovedObject().getTranslateZ());
					
					// Update rotators and precessors
					sunRotator.updateRotation(timeSpeed);
					mercuryRotator.updateRotation(timeSpeed);
					venusRotator.updateRotation(timeSpeed);
					earthRotator.updateRotation(EARTH_ANGULAR_VELOCITY * timeSpeed);
					moonRotator.updateRotation(MOON_ANGULAR_VELOCITY * timeSpeed);
					marsRotator.updateRotation(timeSpeed);
					jupiterRotator.updateRotation(timeSpeed);
					saturnRotator.updateRotation(timeSpeed);
					uranusRotator.updateRotation(timeSpeed);
					neptuneRotator.updateRotation(timeSpeed);
					ioRotator.updateRotation(timeSpeed);
					europaRotator.updateRotation(timeSpeed);

					sunPrecessor.updatePrecession(timeSpeed);
					mercuryPrecessor.updatePrecession(timeSpeed);
					venusPrecessor.updatePrecession(timeSpeed);
					earthPrecessor.updatePrecession(timeSpeed);
					marsPrecessor.updatePrecession(timeSpeed);
					jupiterPrecessor.updatePrecession(timeSpeed);
					saturnPrecessor.updatePrecession(timeSpeed);
					uranusPrecessor.updatePrecession(timeSpeed);
					neptunePrecessor.updatePrecession(timeSpeed);

					// Update simulation time text
					updateTextField();
				}
			}
		};
		animationTimer.start();
	}

	// Method to update the rotation of the simulation time text so that it always
	// faces the camera
	private void updateTextRotationToFaceCamera() {
		// Get the camera's current rotation or position
		Point3D cameraPosition = camera.localToScene(new Point3D(0, 0, 0));

		// Calculate rotation based on the camera's position relative to the text
		double angle = Math.atan2(cameraPosition.getY(), cameraPosition.getX()) * 180 / Math.PI;

		// Set the new rotation of the text to always face the camera
		simulationTimeText.setRotate(angle);
	}

	private void setRingsToRootPane() {
		world.getChildren().add(ringJupiter.getRing());

	}

	private void setPlanetsToRootPane() {

		world.getChildren().add(sun.getSphere());
		world.getChildren().add(mercury.getSphere());
		world.getChildren().add(venus.getSphere());
		world.getChildren().add(earth.getSphere());
		world.getChildren().add(mars.getSphere());
		world.getChildren().add(jupiter.getSphere());
		world.getChildren().add(saturn.getSphere());
		world.getChildren().add(uranus.getSphere());
		world.getChildren().add(neptune.getSphere());
		world.getChildren().add(moon.getSphere());
		world.getChildren().add(io.getSphere());
		world.getChildren().add(europa.getSphere());
		world.getChildren().add(cameraObject.getSphere());
	}

	private void updateTextField() {
		simulationTime += timeSpeed / 360.24;
		double years = simulationTime / EARTH_PERIOD;
		double days = simulationTime % EARTH_PERIOD;

		// Update Earth rotations and Moon orbits
		earthRotations = (int) (simulationTime / 1); // 1 day per rotation
		moonOrbits = (int) (simulationTime / MOON_PERIOD);

		// Update the text
		simulationTimeText.setText(String.format("Time: %.2f years, %.2f days\nEarth Rotations: %d\nMoon Orbits: %d",
				years, days, earthRotations, moonOrbits));

		// Get the camera's position
		Point3D cameraPosition = new Point3D(camera.getTranslateX(), camera.getTranslateY(), camera.getTranslateZ());

		// Get the camera's rotation (pitch, yaw)
		Rotate rotation = (Rotate) camera.getTransforms().get(0); // Assuming the first transform is the rotation
		double yaw = rotation.getAngle(); // You may need to calculate this based on camera's transformations
		double pitch = rotation.getAngle(); // Calculate the pitch accordingly (could be another rotation around the
											// X-axis)

		// Update the text with simulation time and camera info
		simulationTimeText.setText(String.format(
				"Time: %.2f years, %.2f days\nEarth Rotations: %d\nMoon Orbits: %d\n\nCamera Position: (%.2f, %.2f, %.2f)\nYaw: %.2f\nPitch: %.2f",
				years, days, earthRotations, moonOrbits, cameraPosition.getX(), cameraPosition.getY(),
				cameraPosition.getZ(), yaw, pitch));
	}

	private void setCamera() {
		cameraX = -2750;
		cameraY = -750;
		cameraZ = -1200;
		rotateYaw = new Rotate(yaw, Rotate.Y_AXIS);
		rotatePitch = new Rotate(pitch, Rotate.X_AXIS);
		camera = new PerspectiveCamera(true);
		camera.setFarClip(50000);
		camera.setNearClip(0.1);
		camera.setTranslateX(cameraX);
		camera.setTranslateY(cameraY);
		camera.setTranslateZ(cameraZ);
		
		

		camera.getTransforms().setAll(rotateYaw, rotatePitch);
	}

	private void setPlanets() {
		// Skalierungsfaktor für die Größe
		double sizeScale = 1e-5;

		sun = new PhysicsSphere(1000, 120, Color.YELLOW, 0, 0, 0);
		mercury = new PhysicsSphere(2.4397 * sizeScale, 10, Color.GRAY, 0, 0, 0);
		venus = new PhysicsSphere(6.0518 * sizeScale, 25, Color.ORANGE, 0, 0, 0);
		earth = new PhysicsSphere(6.371 * sizeScale, 30, Color.BLUE, 0, 0, 0);
		mars = new PhysicsSphere(3.3895 * sizeScale, 20, Color.RED, 0, 0, 0);
		jupiter = new PhysicsSphere(69.911 * sizeScale, 60, Color.WHITE, 0, 0, 0);
		saturn = new PhysicsSphere(58.232 * sizeScale, 50, Color.YELLOW, 0, 0, 0);
		uranus = new PhysicsSphere(25.362 * sizeScale, 40, Color.CYAN, 0, 0, 0);
		neptune = new PhysicsSphere(24.622 * sizeScale, 35, Color.BLUE, 0, 0, 0);
		moon = new PhysicsSphere(1.7371 * sizeScale, 5, Color.GRAY, 0, 0, 0);
		io = new PhysicsSphere(1.8216 * sizeScale, 8, Color.YELLOW, 0, 0, 0);
		europa = new PhysicsSphere(1.5608 * sizeScale, 7, Color.WHITE, 0, 0, 0);

		cameraObject = new PhysicsSphere(300, 9, Color.GRAY, 0, 0, 0);
	}

	public void setTraces() {
		double strokeWidth = 2.0; // Width of the trace lines
		int maxLength = 50000; // Maximum number of points in the trace

		// Initialize traces and attach to root
		sunTrace = new Trace(Color.YELLOW, strokeWidth, maxLength);
		sunTrace.attachToParent(world);

		mercuryTrace = new Trace(Color.GRAY, strokeWidth, maxLength);
		mercuryTrace.attachToParent(world);

		venusTrace = new Trace(Color.ORANGE, strokeWidth, maxLength);
		venusTrace.attachToParent(world);

		earthTrace = new Trace(Color.BLUE, strokeWidth, maxLength);
		earthTrace.attachToParent(world);

		marsTrace = new Trace(Color.RED, strokeWidth, maxLength);
		marsTrace.attachToParent(world);

		jupiterTrace = new Trace(Color.WHITE, strokeWidth, maxLength);
		jupiterTrace.attachToParent(world);

		saturnTrace = new Trace(Color.YELLOW, strokeWidth, maxLength);
		saturnTrace.attachToParent(world);

		uranusTrace = new Trace(Color.CYAN, strokeWidth, maxLength);
		uranusTrace.attachToParent(world);

		neptuneTrace = new Trace(Color.BLUE, strokeWidth, maxLength);
		neptuneTrace.attachToParent(world);

		moonTrace = new Trace(Color.GRAY, strokeWidth, maxLength);
		moonTrace.attachToParent(world);

		ioTrace = new Trace(Color.YELLOW, strokeWidth, maxLength);
		ioTrace.attachToParent(world);

		europaTrace = new Trace(Color.WHITE, strokeWidth, maxLength);
		europaTrace.attachToParent(world);

		// Optionally, start an animation loop

	}

	private void setMover() {
		// Skalierungsfaktor für die Geschwindigkeit
		double speedScale = 1e-5;

		sunToEarthMover = new Mover();
		earthToMoonMover = new Mover();
		sunToMercuryMover = new Mover();
		sunToVenusMover = new Mover();
		sunToMarsMover = new Mover();
		sunToJupiterMover = new Mover();
		sunToSaturnMover = new Mover();
		sunToUranusMover = new Mover();
		sunToNeptuneMover = new Mover();

		jupiterToIoMover = new Mover();
		jupiterToEuropaMover = new Mover();

		cameraMover = new Mover();

		sunToMercuryMover.move(sun.getSphere(), mercury.getSphere(), 300, 47.36 * speedScale, 0, 0.1);
		sunToVenusMover.move(sun.getSphere(), venus.getSphere(), 550, 35.02 * speedScale, 0, 0.1);
		sunToEarthMover.move(sun.getSphere(), earth.getSphere(), 800, 29.78 * speedScale, 0, 0.1);
		sunToMarsMover.move(sun.getSphere(), mars.getSphere(), 1200, 24.07 * speedScale, 0, 0.1);
		sunToJupiterMover.move(sun.getSphere(), jupiter.getSphere(), 2000, 13.07 * speedScale, 0, 0.1);
		sunToSaturnMover.move(sun.getSphere(), saturn.getSphere(), 2800, 9.69 * speedScale, 0, 0.1);
		sunToUranusMover.move(sun.getSphere(), uranus.getSphere(), 3500, 6.81 * speedScale, 0, 0.1);
		sunToNeptuneMover.move(sun.getSphere(), neptune.getSphere(), 4200, 5.43 * speedScale, 0, 0.1);

		cameraMover.move(sun.getSphere(), cameraObject.getSphere(), 3000,300 * speedScale, speedScale, 0.1);
		
		earthToMoonMover.move(earth.getSphere(), moon.getSphere(), 125, 100.022 * speedScale, 0, 0.5);
		jupiterToIoMover.move(jupiter.getSphere(), io.getSphere(), 20, 17.34 * speedScale, 0, 0.5);
		jupiterToEuropaMover.move(jupiter.getSphere(), europa.getSphere(), 25, 13.74 * speedScale, 0, 0.5);

		
	}

	private void setRotator() {
		// Skalierungsfaktor für die Rotationsgeschwindigkeit
		double rotationScale = 1e-2;

		sunRotator = new Rotator(sun.getSphere(), 0.05 * rotationScale, 0.05 * rotationScale, 0);
		mercuryRotator = new Rotator(mercury.getSphere(), 0.1 * rotationScale, 0.1 * rotationScale, 0);
		venusRotator = new Rotator(venus.getSphere(), 0.1 * rotationScale, 0.1 * rotationScale, 0);
		earthRotator = new Rotator(earth.getSphere(), 1 * rotationScale, 36.5 * rotationScale, 0);
		marsRotator = new Rotator(mars.getSphere(), 0.15 * rotationScale, 0.15 * rotationScale, 0);
		jupiterRotator = new Rotator(jupiter.getSphere(), 0.05 * rotationScale, 0.05 * rotationScale, 0);
		saturnRotator = new Rotator(saturn.getSphere(), 0.05 * rotationScale, 0.05 * rotationScale, 0);
		uranusRotator = new Rotator(uranus.getSphere(), 0.05 * rotationScale, 0.05 * rotationScale, 0);
		neptuneRotator = new Rotator(neptune.getSphere(), 0.05 * rotationScale, 0.05 * rotationScale, 0);
		moonRotator = new Rotator(moon.getSphere(), 0.2 * rotationScale, 0.05 * rotationScale, 0);
		ioRotator = new Rotator(io.getSphere(), 0.2 * rotationScale, 0.2 * rotationScale, 0);
		europaRotator = new Rotator(europa.getSphere(), 0.2 * rotationScale, 0.2 * rotationScale, 0);
	}

//	private void setRotator() {
//	    // Skalierungsfaktor für die Rotationsgeschwindigkeit
//	    double rotationScale = 1e-2;
//
//	    // Create a Pane for tracing the movements
//	    Pane tracePane = new Pane();  // You might want to pass this Pane from your UI or main scene.
//
//	    // Create Rotators with tracePane to display traces of rotations
//	    sunRotator = new Rotator(sun.getSphere(), 0.05 * rotationScale, 0.05 * rotationScale, 0, tracePane);
//	    mercuryRotator = new Rotator(mercury.getSphere(), 0.1 * rotationScale, 0.1 * rotationScale, 0, tracePane);
//	    venusRotator = new Rotator(venus.getSphere(), 0.1 * rotationScale, 0.1 * rotationScale, 0, tracePane);
//	    earthRotator = new Rotator(earth.getSphere(), 1 * rotationScale, 0.05 * rotationScale, 0, tracePane);
//	    marsRotator = new Rotator(mars.getSphere(), 0.15 * rotationScale, 0.15 * rotationScale, 0, tracePane);
//	    jupiterRotator = new Rotator(jupiter.getSphere(), 0.05 * rotationScale, 0.05 * rotationScale, 0, tracePane);
//	    saturnRotator = new Rotator(saturn.getSphere(), 0.05 * rotationScale, 0.05 * rotationScale, 0, tracePane);
//	    uranusRotator = new Rotator(uranus.getSphere(), 0.05 * rotationScale, 0.05 * rotationScale, 0, tracePane);
//	    neptuneRotator = new Rotator(neptune.getSphere(), 0.05 * rotationScale, 0.05 * rotationScale, 0, tracePane);
//	    moonRotator = new Rotator(moon.getSphere(), 0.2 * rotationScale, 0.05 * rotationScale, 0, tracePane);
//	    ioRotator = new Rotator(io.getSphere(), 0.2 * rotationScale, 0.2 * rotationScale, 0, tracePane);
//	    europaRotator = new Rotator(europa.getSphere(), 0.2 * rotationScale, 0.2 * rotationScale, 0, tracePane);
//
//	    // Optionally, add the tracePane to the scene, assuming 'scene' is your JavaFX scene or layout
//	    // scene.getChildren().add(tracePane);  // Uncomment and adjust according to your layout setup
//	}

	private void setPlanetsPhongMaterial() {
		sun = setCelestialBodyImages(sun, "sun");
		mercury = setCelestialBodyImages(mercury, "mercury");
		venus = setCelestialBodyImages(venus, "venus");
		earth = setCelestialBodyImages(earth, "earth");
		mars = setCelestialBodyImages(mars, "mars");
		jupiter = setCelestialBodyImages(jupiter, "jupiter");
		saturn = setCelestialBodyImages(saturn, "saturn");
		uranus = setCelestialBodyImages(uranus, "uranus");
		neptune = setCelestialBodyImages(neptune, "neptune");
		moon = setCelestialBodyImages(moon, "moon");
		io = setCelestialBodyImages(io, "io");
		europa = setCelestialBodyImages(europa, "europa");

	}

	private void setRing() {
		ringJupiter = new Ring(150.0, 130.0, 1, Color.GRAY);
	}

	private void setPrecessor() {
		sunPrecessor = new Precessor(sun.getSphere(), 23.5, 0.002); // Sun's axial tilt and precession speed
		mercuryPrecessor = new Precessor(mercury.getSphere(), 7.0, 0.01); // Mercury's precession
		venusPrecessor = new Precessor(venus.getSphere(), 3.0, 0.01); // Venus' precession
		earthPrecessor = new Precessor(earth.getSphere(), 23.5, 0.003); // Earth's precession
		marsPrecessor = new Precessor(mars.getSphere(), 25.2, 0.005); // Mars' precession
		jupiterPrecessor = new Precessor(jupiter.getSphere(), 3.1, 0.003); // Jupiter's precession
		saturnPrecessor = new Precessor(saturn.getSphere(), 26.7, 0.002); // Saturn's precession
		uranusPrecessor = new Precessor(uranus.getSphere(), 97.8, 0.001); // Uranus' extreme axial tilt
		neptunePrecessor = new Precessor(neptune.getSphere(), 28.3, 0.001); // Neptune's precession
	}

	private PhysicsSphere setCelestialBodyImages(PhysicsSphere celestialBody, String celestialBodyType) {
		Image image = null;

		// Select the appropriate image based on the type
		switch (celestialBodyType.toLowerCase()) {
		case "sun":
			image = new Image(getClass().getResource("/sun.jpeg").toExternalForm()); // Corrected path
			break;
		case "mercury":
			image = new Image(getClass().getResource("/mercury.jpeg").toExternalForm());
			break;
		case "venus":
			image = new Image(getClass().getResource("/venus.jpeg").toExternalForm());
			break;
		case "earth":
			image = new Image(getClass().getResource("/earth.png").toExternalForm());
			break;
		case "mars":
			image = new Image(getClass().getResource("/mars.jpeg").toExternalForm());
			break;
		case "jupiter":
			image = new Image(getClass().getResource("/jupiter.jpeg").toExternalForm());
			break;
		case "saturn":
			image = new Image(getClass().getResource("/saturn.jpeg").toExternalForm());
			break;
		case "uranus":
			image = new Image(getClass().getResource("/uranus.jpeg").toExternalForm());
			break;
		case "neptune":
			image = new Image(getClass().getResource("/neptun.jpeg").toExternalForm());
			break;
		case "moon":
			image = new Image(getClass().getResource("/moon.jpeg").toExternalForm());
			break;
		case "io":
			image = new Image(getClass().getResource("/io.jpeg").toExternalForm());
			break;
		case "europa":
			image = new Image(getClass().getResource("/europa.jpeg").toExternalForm());
			break;
		default:
			System.out.println("No image found for: " + celestialBodyType);
			// No image if the body type is not recognized
		}

		// Set the image as a PhongMaterial texture
		PhongMaterial material = new PhongMaterial();
		material.setDiffuseMap(image);
		celestialBody.setMaterial(material);
		return celestialBody;
	}

	private void handleMouseInput(Scene scene, PerspectiveCamera camera) {
		scene.setOnMousePressed((MouseEvent event) -> {
			mouseX = event.getSceneX();
			mouseY = event.getSceneY();
		});

		scene.setOnMouseDragged((MouseEvent event) -> {
			// Calculate how much the mouse has moved
			double deltaX = event.getSceneX() - mouseX;
			double deltaY = event.getSceneY() - mouseY;

			// Update the mouse position for next time
			mouseX = event.getSceneX();
			mouseY = event.getSceneY();

			// Update yaw and pitch based on mouse movement
			yaw -= deltaX * MOUSE_SENSITIVITY;
			pitch += deltaY * MOUSE_SENSITIVITY;

			// You can now remove the restriction on pitch
			// pitch = Math.max(-90, Math.min(90, pitch)); // Remove this line to allow full
			// freedom in pitch rotation

			// Apply the yaw and pitch rotations in the correct order
			Rotate rotateYaw = new Rotate(yaw, Rotate.Y_AXIS); // Rotation around Y-axis (yaw)
			Rotate rotatePitch = new Rotate(pitch, Rotate.X_AXIS); // Rotation around X-axis (pitch)

			// Apply both rotations to the camera
			camera.getTransforms().setAll(rotateYaw, rotatePitch);

			// Optional: Debug output to monitor yaw and pitch
			System.out.println("Yaw: " + yaw + " Pitch: " + pitch);

		});

		scene.setOnScroll(event -> {
			double dz = 0;

			// Check if the user is scrolling up or down
			if (event.getDeltaY() > 0) {
				dz = CAMERA_SPEED_SCROLL; // Scroll up = move forward
			} else if (event.getDeltaY() < 0) {
				dz = -CAMERA_SPEED_SCROLL; // Scroll down = move backward
			}

			// Transform direction based on camera's rotation (yaw)
			// Using yaw (rotation around Y-axis) to determine the direction the camera is
			// looking
			double sinYaw = Math.sin(Math.toRadians(yaw));
			double cosYaw = Math.cos(Math.toRadians(yaw));

			// Calculate movement in the X and Z axes based on the camera's rotation
			double forwardX = sinYaw * dz; // Forward/backward movement along the X axis
			double forwardZ = cosYaw * dz; // Forward/backward movement along the Z axis

			// Update the camera's position based on its current orientation and scroll
			// input
			cameraX += forwardX;
			cameraY += forwardZ;
			camera.setTranslateX(camera.getTranslateX() + cameraX);
			camera.setTranslateZ(camera.getTranslateZ() + cameraZ);

		});

	}

	private void handleKeyboardInput(Scene scene, PerspectiveCamera camera) {
		scene.setOnKeyPressed(event -> {
			double dx = 0, dy = 0, dz = 0;
			if (event.getCode() == KeyCode.UP)
				dz = CAMERA_SPEED; // Forward
			if (event.getCode() == KeyCode.DOWN)
				dz = -CAMERA_SPEED; // Backward
			if (event.getCode() == KeyCode.RIGHT)
				dx = CAMERA_SPEED; // Left strafe
			if (event.getCode() == KeyCode.LEFT)
				dx = -CAMERA_SPEED; // Right strafe
			if (event.getCode() == KeyCode.UP && event.isShiftDown())
				dy = CAMERA_SPEED; // Up
			if (event.getCode() == KeyCode.DOWN && event.isShiftDown())
				dy = -CAMERA_SPEED; // Down
			if (event.getCode() == KeyCode.ESCAPE)
				System.exit(0); // EXIT
			if (event.getCode() == KeyCode.P)
				pause = !pause; // Pause
			if (event.getCode() == KeyCode.PAGE_UP)
				increaseTimeSpeed(); // Speed up time
			if (event.getCode() == KeyCode.PAGE_DOWN)
				decreaseTimeSpeed(); // Slow down time
			if (event.getCode() == KeyCode.F1) {

			} else if (event.getCode() == KeyCode.F2) {

			} else if (event.getCode() == KeyCode.F3) {

			}

			// Transform direction based on camera's rotation
			double sinYaw = Math.sin(Math.toRadians(yaw));
			double cosYaw = Math.cos(Math.toRadians(yaw));

			double forwardX = sinYaw * dz;
			double forwardZ = cosYaw * dz;

			double strafeX = cosYaw * dx;
			double strafeZ = -sinYaw * dx;
			
			cameraX +=forwardX + strafeX;
			cameraY += dy;
			cameraZ +=  forwardZ + strafeZ;

			camera.setTranslateX(camera.getTranslateX() + cameraX );
			camera.setTranslateY(camera.getTranslateY() + cameraY);
			camera.setTranslateZ(camera.getTranslateZ() + cameraZ);
		});
	}

	private void increaseTimeSpeed() {
		timeSpeed += 10; // Increase speed by 10%
		System.out.println("Time speed increased: " + timeSpeed);
	}

	// Method to decrease the time speed
	private void decreaseTimeSpeed() {
		timeSpeed -= 10; // Decrease speed by 10%
		System.out.println("Time speed decreased: " + timeSpeed);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
