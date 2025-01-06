package main;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.geometry.Point3D;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import javafx.util.Duration;

public class Trace {
	private final Polyline trace;
	private final int maxLength; // Maximum number of points in the trace
	private final Timeline fadeTimeline;

	public Trace(Color color, double strokeWidth, int maxLength) {
		this.maxLength = maxLength;

		// Create the trace polyline
		trace = new Polyline();
		trace.setStroke(color);
		trace.setStrokeWidth(strokeWidth);
		trace.setEffect(new DropShadow(20, color));
		trace.setOpacity(1.0);

		// Timeline to gradually fade out the trace
		fadeTimeline = new Timeline(new KeyFrame(Duration.millis(50), event -> {
			double currentOpacity = trace.getOpacity();
			if (currentOpacity > 0) {
				trace.setOpacity(currentOpacity - 0.01);
			}
		}));
		fadeTimeline.setCycleCount(Timeline.INDEFINITE);
		fadeTimeline.play();
	}

	// Attach the trace to the parent node
	public void attachToParent(javafx.scene.Group parent) {
		parent.getChildren().add(trace);
	}

	// Update the trace position
	public void update(Point3D position) {
		ObservableList<Double> points = trace.getPoints();

		// Add the current position
		points.addAll(position.getX(), position.getY());

		// Limit the length of the trace
		if (points.size() > maxLength * 2) { // Each point has X and Y
			points.remove(0, 2); // Remove the oldest (X, Y)
		}
	}

	// Remove the trace from its parent node
	public void detachFromParent(javafx.scene.Group parent) {
		parent.getChildren().remove(trace);
		fadeTimeline.stop();
	}
}
