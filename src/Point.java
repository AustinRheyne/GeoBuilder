import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;
import java.util.ArrayList;

public class Point {
    double radius = 0.03;
    double x;
    double y;
    ArrayList<Line> lines = new ArrayList<Line>();
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {return x;}
    public double getY() {return y;}
    public void setX(double x) {this.x = x;}
    public void setY(double y) {this.y = y;}
    public void addLineConnection(Line line) {lines.add(line);}
    public void draw(){
        StdDraw.setPenRadius(radius);
        StdDraw.point(x, y);
    }
    public boolean pointWithin(double x, double y){
        double dist = Math.sqrt(((this.x-x)*(this.x-x)) + ((this.y-y)*(this.y-y)));
        if (dist <= 1) {
            return true;
        }
        return false;
    }
    public void updateLines(){
        for(int i = 0; i < lines.size(); i++) {
            lines.get(i).setRange();
            lines.get(i).setDomain();
        }
    }
    public void removeLineConnection(Line line) {
        lines.remove(line);
    }
}
