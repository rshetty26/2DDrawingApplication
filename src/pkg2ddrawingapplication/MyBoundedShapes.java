package pkg2ddrawingapplication;

import java.awt.Paint;
import java.awt.Point;
import java.awt.Stroke;
/**
 *
 * @author rithv
 */
public abstract class MyBoundedShapes extends MyShapes{
    private boolean filled;
    
    public MyBoundedShapes(Point pntA, Point pntB, Paint paint, Stroke strk, boolean filled)
    {
        super(pntA, pntB, paint, strk);
        this.filled = filled;
    }

    public boolean isFilled() {
        return filled;
    }

    public void setIsFilled(boolean isFilled) {
        filled = isFilled;
    }
    
    public int getTopLeftX()
    {
        return Math.min((int)(getStartPoint().getX()), (int)(getEndPoint().getX()));
    }
    
    public int getTopLeftY()
    {
        return Math.min((int)(getStartPoint().getY()), (int)(getEndPoint().getY()));
    }
    
    public int getWidth()
    {
        return Math.abs((int)getStartPoint().getX() - (int)getEndPoint().getX());
    }
    
    public int getHeight()
    {
        return Math.abs((int)getStartPoint().getY() - (int)getEndPoint().getY());
    }
      
}
