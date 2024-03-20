package org.pwr;

public class Point {
    public int x;
    public int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Point)) {
            return false;
        }

        Point second = (Point) obj;

        return (second.x == this.x && second.y == this.y);
    }
}
