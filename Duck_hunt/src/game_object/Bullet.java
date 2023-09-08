package game_object;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

public class Bullet {
    private double x;
    private double y;
    private final Shape shape;
    private final Color color= new Color(100,100,100);
    private final float angle;
    private double size;
    private float speed = 1f;
    public Bullet(double x,double y, float angle,double size,float speed){
        x +=Player.psize/2-(size/2);
        y +=Player.psize/2-(size/2);
        this.x = x;
        this.y=y;
        this.angle = angle;
        this.size=size;
        this.speed = speed;
        shape = new Ellipse2D.Double(0,0,size,size);
    }
    public void update(){
        x += Math.cos(Math.toRadians(angle))*speed;
        y += Math.sin(Math.toRadians(angle))*speed;
    }
    public boolean check(int width,int height){
        if(x<=-size|| y<-size ||x>width||y>height){
            return false;
        }else{
            return true;
        }
    }
    public void draw(Graphics2D g){
        AffineTransform oldTransform = g.getTransform();
        g.setColor(color);
        g.translate(x,y);
        g.fill(shape);
        g.setTransform(oldTransform);
    }

    public Shape getShape(){
        return  new Area(new Ellipse2D.Double(x,y,size,size));
    }
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getSize() {
        return size;
    }
    public double getCenterX(){
        return x+size/2;
    }
    public double getCenterY(){
        return y+size/2;
    }
}
