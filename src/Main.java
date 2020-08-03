import java.awt.Color;
import java.awt.Point;

public class Main {

    public static void main(String[] args) {

        ReflectionImpl CircleRef = new ReflectionImpl();
        ReflectionImpl SquareRef = new ReflectionImpl();
        Circle cir = new Circle(new Point(5, 5), 6);
        Square sqr = new Square(new Point(0, 0), 10);
        String x = "23";

        CircleRef.load(x);
        SquareRef.load(sqr);

        System.out.println(CircleRef.isExtending());
        System.out.println(CircleRef.getTotalNumberOfConstructors());
        System.out.println(SquareRef.getAllImplementedInterfaces().toString());


    }
}
