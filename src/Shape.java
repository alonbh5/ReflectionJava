

import java.awt.Color;
import java.awt.Point;

public abstract class Shape {

    protected Color line = Color.BLACK;
    protected Color fill = Color.WHITE;

    protected Shape() {
    }

    public void draw() {
        System.out.println("Not drawing anything - Shape is an abstract class");
        throw new UnsupportedOperationException();
    }

    abstract public boolean isPointInside(Point p);
    
}
