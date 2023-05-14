package pkg2ddrawingapplication;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Stroke;
/**
 *
 * @author rithv
 */
public abstract class MyShapes {
private Point startPoint = new Point();
    private Point endPoint = new Point();
    private Paint paint;
    private Stroke stroke;
    
    public MyShapes()
    {
        stroke = new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        paint = Color.BLACK;
    }
    
    public MyShapes(Point pntA, Point pntB, Paint paint, Stroke strk)
    {
        startPoint = pntA;
        endPoint = pntB;
        this.paint = paint;
        stroke = strk;
        
    }
    
    public abstract void draw(Graphics2D g2d);


    public Point getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Point endPoint) {
        this.endPoint = endPoint;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public Stroke getStroke() {
        return stroke;
    }

    public void setStroke(Stroke stroke) {
        this.stroke = stroke;
    }    
}
