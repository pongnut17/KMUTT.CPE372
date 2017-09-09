import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * AbstractShape class. Intended to serve as a superclass (generalization) for
 * individual shapes like Triangle, Square, etc.
 *
 * Created by Pongnut Jittipanyakul (Prues)
 * ID 58070503419
 *
 * Modified for Exercise 5
 * - Add min and max values of x and y coordinates for the bounding box.
 * - Add inShape() to check point inside the bounding box or not.
 * - Add calcBoundingBox() to calculate the bounding box.
 *
 * 10 September 2017
 */

public abstract class AbstractShape
{
    /**
     * Anchor point X,Y
     * determines the "position" of a shape
     */
    protected Point anchor;
    /* Point is a class in package java.awt that has a public x and y member */

    /**
     * list of points
     */
    protected ArrayList<Point> vertices = new ArrayList<Point>();

    /**
     * min and max values of the bounding box
     */
    protected int minX;
    protected int maxX;
    protected int minY;
    protected int maxY;

    /**
     * id for this shape
     */
    protected int shapeId;

    /**
     * color
     */
    protected Color drawColor;

    /**
     * so we can count and label figures
     */
    protected static int counter = 0;

    /**
     * collection of all shapes
     */
    protected static ArrayList<AbstractShape> allFigures = new ArrayList<AbstractShape>();

    /**
     * used to cycle through display colors
     */
    protected static Color colors[] = {Color.RED, Color.GREEN, Color.BLUE,
            Color.MAGENTA, Color.ORANGE};

    /**
     * Constructor increments counter, sets shapeId, puts shape
     * into the allFigures list, and sets the color.
     * These operations will occur regardless of the type of shape.
     */
    public AbstractShape()
    {
        counter++;
        shapeId = counter;
        drawColor = colors[counter % 5];
        allFigures.add(this);
    }

    /**
     * Find mouse clicked coordinates that inside
     * the bounding box of the shape or not
     *
     * @param x X coordinates of mouse clicked
     * @param y Y coordinate of mouse clicked
     * @return mouse clicked inside the bounding box of the shape or not
     */
    public boolean inShape(int x, int y)
    {
        boolean result = false;

        if (minX <= x && maxX >= x && minY <= y && maxY >= y)
        {
            result = true;
        }

        return result;
    }

    /**
     * Calculate min and max X,Y values of the bounding box
     */
    public void calcBoundingBox()
    {
        ArrayList<Integer> allXPoints = new ArrayList<Integer>();
        ArrayList<Integer> allYPoints = new ArrayList<Integer>();
        for (int i = 0; i < vertices.size(); i++)
        {
            allXPoints.add(vertices.get(i).x);
            allYPoints.add(vertices.get(i).y);
        }
        Collections.sort(allXPoints);
        Collections.sort(allYPoints);
        minX = allXPoints.get(0);
        maxX = allXPoints.get(allXPoints.size() - 1);
        minY = allYPoints.get(0);
        maxY = allYPoints.get(allYPoints.size() - 1);
    }

    /**
     * Move the shape to a new location, specified by
     * the passed x and y coordinates. We can do this in a general manner by
     * calculating the difference between the old and new locations
     * in X and Y directions, then adding this difference to each
     * point. We also need to change the anchor point data item.
     *
     * @param x Y coordinates of new reference/anchor point
     * @param y Y coordinates of new reference/anchor point
     */
    public void move(int x, int y)
    {
        Point newAnchor = new Point(x, y);
        int deltaX = newAnchor.x - anchor.x; /* difference between old & new posn */
        int deltaY = newAnchor.y - anchor.y;
        anchor = newAnchor;
        int points = vertices.size();
        for (int i = 0; i < points; i++)
        {
            Point p = vertices.get(i);
            p.x += deltaX;
            p.y += deltaY;
        /* we don't need to put it back in the arraylist */
        /* since it is still there. */
        }
    }

    /**
     * Draw the shape.
     * This will only work correctly for closed shapes with a finite
     * number of vertices (like triangles, squares and diamonds)
     *
     * @param graphics Graphics context for drawing
     */
    public void draw(Graphics2D graphics)
    {
        graphics.setPaint(drawColor);
        int points = vertices.size();
        for (int i = 0; i < points; i++)
        {
            Point p1 = vertices.get(i);
            Point p2 = vertices.get((i + 1) % points);
            graphics.drawLine(p1.x, p1.y, p2.x, p2.y);
        }
        /* label it near the anchor point */
        int labelx = anchor.x + 5;
        int labely = anchor.y - 5;
        graphics.drawString(new String(" " + shapeId), labelx, labely);
    }

    /**
     * Draw and fill the shape.
     * This will only work correctly for closed shapes with a finite
     * number of vertices (like triangles, squares and diamonds)
     *
     * @param graphics  Graphics context for drawing
     * @param fillColor Color to use for filling.
     */
    public void draw(Graphics2D graphics, Color fillColor)
    {
        draw(graphics);  /* draw the outline */
        int size = vertices.size();
        int x[] = new int[size];
        int y[] = new int[size];
        for (int i = 0; i < size; i++)
        {
            Point p = vertices.get(i);
            x[i] = p.x;
            y[i] = p.y;
        }
        graphics.setPaint(fillColor);
        graphics.fillPolygon(x, y, size);
    }

    /**
     * Calculate and return the perimeter.
     *
     * @return Length of shape boundary
     */
    public abstract double calcPerimeter();

    /**
     * Calculate and return the area of the shape.
     *
     * @return area
     */
    public abstract double calcArea();

    /**
     * static method to draw all the shapes
     * that have been created so far.
     *
     * @param graphics Graphics context for drawing.
     */
    public static void drawAll(Graphics2D graphics)
    {
        for (int i = 0; i < allFigures.size(); i++)
        {
            AbstractShape shape = allFigures.get(i);
            shape.draw(graphics);
        }
    }
}