/**
* This project is designed as a demo to test some math ideas. The current version does not display information to the user, however, that
* will be implemented in the future.
*
* The user can add a new point onto the shape by holding the ALT key and clicking where they want with the mouse.
* The user can move existing points anywhere on the canvas by clicking and holding WITHOUT holding the alt key down
*
* @author   Austin Rheyne
* @version  1.0, 1/11/2023
* */

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    static Shape shape;
    static File ASTEROID_LOCATION = new File("./SavedAsteroids");
    public static void main(String[] args) {
        boolean altKey;
        boolean mouseDown = false;
        // Set the configurations of the canvas
        shape = new Shape();
        StdDraw.setScale(0, 100);
        StdDraw.enableDoubleBuffering();

        // Create the shadow point, which will be used to show where the new location is
        // IMPORTANT: This is the only use of "new Point" outside of shape.java. Because the shadow point is not
        // an actual point on the shape and, instead a point that only exists to display something to the user.
        Point shadowPoint = new Point(0, 0);
        Point dragPoint = null;
        while (true){
            StdDraw.clear();
            altKey = StdDraw.isKeyPressed(KeyEvent.VK_ALT);

            // Position the point at the mouse
            if (altKey) {
                shadowPoint.setX(StdDraw.mouseX());
                shadowPoint.setY(StdDraw.mouseY());
                shadowPoint = shape.nearestPoint(shadowPoint);
            }

            if (StdDraw.isMousePressed()) {
                if (altKey && !mouseDown && dragPoint == null) { // Alt key, Mouse is not down, and the dragPoint does not exist
                    // Ensure that a user cannot drag a point and place a new one down
                    // at the same time
                    mouseDown = true;
                    // Adjust the point to the correct position, then create a new point
                    Line currentLine = shadowPoint.lines.get(0);
                    Point newPoint = shape.newPoint(shadowPoint.getX(), shadowPoint.getY());
                    currentLine.getStart().addLineConnection(shape.newLine(currentLine.getStart(), newPoint));
                    currentLine.getEnd().addLineConnection(shape.newLine(newPoint, currentLine.getEnd()));
                    shape.removeLine(currentLine);
                } else if(dragPoint != null) {
                    dragPoint.setX(StdDraw.mouseX());
                    dragPoint.setY(StdDraw.mouseY());
                    dragPoint.updateLines();
                } else {
                    Point clicked = shape.findClickedPoint(StdDraw.mouseX(), StdDraw.mouseY());
                    if (clicked.getX() != -100) {
                        dragPoint = clicked;
                        dragPoint.setX(StdDraw.mouseX());
                        dragPoint.setY(StdDraw.mouseY());
                        dragPoint.updateLines();
                    }
                }
            } else {
                mouseDown = false;
                dragPoint = null;
            }

            if (altKey) {
                shadowPoint.draw();
            }
            shape.draw();
            StdDraw.show();
        }
    }

    public static void SaveCurrentAsteroid (ActionEvent e) throws IOException {
        StringBuilder output = new StringBuilder();
        for(Point p : shape.getPoints()) {
            output.append(p.getPosition() + ",");
        }
        output.deleteCharAt(output.length()-1);
        FileWriter asteroidFile = new FileWriter(ASTEROID_LOCATION.getName() + "/Asteroid" + ASTEROID_LOCATION.list().length + ".ast");
        asteroidFile.write(output.toString());
        asteroidFile.close();
        System.out.println("Successfully saved the Asteroid as a file");
    }


}

