
	import java.awt.Color;
	import java.awt.Graphics;
import java.awt.Rectangle;

	public class Brique {

		int x,y;
		boolean vivant;
		Color coulBrique;
		int cptCollisions;
		
		static int width;
		static int height;
		static int gap_x;
		static int gap_y;
		
		static {
			width = 50;
			height = 25;
			gap_x = 20;
			gap_y = 20;
		}
		
		public Brique(int X, int Y){
			x= X * (width + gap_x);
			y= Y * (height + gap_y);
			vivant=true;
			cptCollisions=0;
			coulBrique=Color.BLACK;
		}
		
		public void dessiner(Graphics g){
			if(vivant) {
				g.setColor(coulBrique);
				g.fillRect(x , y , width, height);
			}	
		}
		
		public Rectangle getRectangle() {
			if(vivant) {
				return new Rectangle(x, y, width, height);
			}
			return new Rectangle(x, y, 0, 0);
		}
		
		public boolean checkCollision(Rectangle R){
			if(R.intersects(getRectangle())) {
				vivant=false;
				return true;
			}
			return false;
		
		}
}
