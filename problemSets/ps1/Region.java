package ps1;

import java.awt.*;
import java.util.ArrayList;

public class Region {
    private ArrayList<Point> points;

    public Region() {
        points = new ArrayList<>();
    }

    public void addPoint(Point point){
        this.points.add(point);
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public int getSize(){
        return points.size();
    }

    public void setPoints(ArrayList<Point> points) {
        this.points = points;
    }

    public void wipeRegion() {
        this.points = new ArrayList<>();
    }

}

