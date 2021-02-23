import java.awt.Color;
import java.awt.Rectangle;

public class BriqueDure extends Brique {
	
	public BriqueDure(int X, int Y) {
		super(X, Y);
		// TODO Auto-generated constructor stub
		coulBrique = new Color(255,64,171);
	}
	
	public boolean checkCollision(Rectangle R){
		if(R.intersects(getRectangle())) {
			
			if(cptCollisions==1) {
				coulBrique=Color.BLACK;
			}
	
			if(cptCollisions==2) {
				vivant=false;
				System.out.println("Brique dure détruite");
				
			}
			
			return true;
		}
		return false;
	
	}

}
