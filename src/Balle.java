	import java.awt.Color;
	import java.awt.Graphics;
import java.awt.Rectangle;
	
public class Balle {

	double x,y;
	double dx, dy;
	Color coulBalle;
	int width,height;

	
	public Balle(int X, int Y){
		setX(X);
		setY(Y);
		dx=0;
		dy=0;
		setCoul(new Color(71,192,196));
		width=15;
		height=15;
	}
	
	public void setY(double yParam) { y=yParam; }
	public double getY() { return y ;}
	
	public void setX(double xParam) { x=xParam; }
	public double getX() { return x ;}
	
	public void setCoul(Color couleurParam) { coulBalle=couleurParam; }
	public Color getCoul() { return coulBalle; }

	
	public void bouge() {
		x = x + dx;
		y = y + dy;
	}
	
	//pour que la balle suive la raquette avant de commencer
	public void position(int nx, int min, int max) {
		if(nx>min && nx<max)
			x=nx;
	}
		
	public void dessiner(Graphics g){
		g.setColor(coulBalle);
		g.fillOval((int)x, (int)y, width, height);
	}
	
	public Rectangle getRectangle() {
		return new Rectangle((int)x,(int)y,15,15);		
	}
	
	public Rectangle getRectangleH() {
		return new Rectangle((int)x,(int)y,15,3);		
	}
	
	public Rectangle getRectangleB() {
		return new Rectangle((int)x,(int)y+15-3,15,3);			
	}
	
	public Rectangle getRectangleG() {
		return new Rectangle((int)x,(int)y,3,15);		
	}
	
	public Rectangle getRectangleD() {
		return new Rectangle((int)x+15-3,(int)y,3,15);	
	}

}
