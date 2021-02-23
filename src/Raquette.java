import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Raquette {

	int larg, haut;
	double x,y;
	Color coulRaquette;
	
	public Raquette(int p1, int p2){
		larg=100;
		haut=10;
		x=p1/2-(larg/2);
		y=p2-60;
		coulRaquette=new Color(100,100,100);
	}
		
	public void dessiner(Graphics g){
		g.setColor(coulRaquette);
		g.fillRect((int)x, (int)y, larg, haut);
	}
	
	public void bouge(int d) {
		x=x+d;
	}
	
	public void position(int nx, int min, int max) {
		if(nx>min && nx<max)
			x=nx;
	}
	
	public Rectangle getRectangle() {
		return new Rectangle((int)x,(int)y,larg,haut);		
	}
}
