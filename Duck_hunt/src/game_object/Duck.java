package game_object;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.util.Objects;

public class Duck extends Hp_render {
    public Duck(){
        super(new HP(20, 20));
        this.image = new ImageIcon(Objects.requireNonNull(getClass().getResource("/game_image/duck.png"))).getImage();
//        this.image = new ImageIcon(getClass().getResource("/game_image/plane.png")).getImage();
       //remove duck from screen
        Path2D p = new Path2D.Double();
        p.moveTo(0,duck_size/2);
        p.lineTo(15,10);
        p.lineTo(duck_size-5,13);
        p.lineTo(duck_size+10,duck_size/2);
        p.lineTo(duck_size-5,duck_size-13);
        p.lineTo(15,duck_size-10);
        duckShape = new Area(p);
    }

    public static final double duck_size = 50;
    private double x;
    private double y;
    private final  float speed = 0.3f;
    private float angle =0;
    private final Image image;
    private final Area duckShape;
    public void changeLocation(double x,double y){
        this.x = x;
        this.y = y;
    }
    public void update(){
        x+=Math.cos(Math.toRadians(angle))*speed;
        y+=Math.sin(Math.toRadians(angle))*speed;
    }
    public void changeAngle(float angle){
        if(angle<0){
            angle = 359;
        } else if (angle>359) {
            angle=0;
        }
        this.angle = angle;
    }
    public void draw (Graphics2D g){
        AffineTransform oldTransform = g.getTransform();
        g.translate(x,y);
        AffineTransform tran = new AffineTransform();
        tran.rotate(Math.toRadians(angle+60),duck_size/2,duck_size/2);
        g.drawImage(image,tran,null);

        Shape shape = getShape();
        HpRender(g,shape,y);
        g.setTransform(oldTransform);

        //test
//        g.setColor(new Color(34,214,43));
//        g.draw(shape);
//        g.draw(shape.getBounds2D());

    }
    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }

    public float getangle() {
        return angle;
    }
    public Area getShape(){
        AffineTransform afx = new AffineTransform();
        afx.translate(x,y);
        afx.rotate(Math.toRadians(angle),duck_size/2,duck_size/2);
        return new Area(afx.createTransformedShape(duckShape));
    }
    public boolean check (int width , int height){
        Rectangle size = getShape().getBounds();
        if (x <= -size.getWidth() || y < -size.getHeight() || x> width || y> height){
            return false;
        }else {
            return true;
        }
    }
}
