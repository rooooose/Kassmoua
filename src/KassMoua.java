import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JFrame;

public class KassMoua extends JFrame implements Runnable, KeyListener, MouseListener, MouseMotionListener{
	
	int width,height;
	AireDeJeu monAire;
	private Thread processusJeu;
	private Image doubleBuffer;
	private Graphics gBuffer;
	boolean gameOver;
	boolean win;
	boolean start;
	boolean balleHorsChamp;
	boolean balleBonusHorsChamp;
	
	int numLvl;
	int vies;
	
	public KassMoua()
	{
		super("KassMoua 19-20");
		
		width=600;
		height=800;
		gameOver=false;
		numLvl=1;
		vies=2;
		balleHorsChamp=false;
		
		this.setSize(width,height);
		this.setLocation(10,10);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		monAire=new AireDeJeu(width,height,1);
		
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		
		this.setVisible(true);
		
		processusJeu= new Thread(this);
		processusJeu.start();
		
		doubleBuffer = createImage(getSize().width, getSize().height);
        gBuffer = doubleBuffer.getGraphics();
        
        
        /*this.setLayout(null);
          pauseSonButton=new JButton("SON ON/OFF");
          pauseSonButton.setBounds(50,650,150,40);
          this.add(pauseSonButton);
          pauseSonButton.addActionListener(new ActionListener()
           {
         		public void actionPerformed(ActionEvent e)
         			{
         				if(audio_On==true){audio_On=false;}
         				else audio_On=true;
          			}
           });*/
        
     		
	}
	
	public void paint(Graphics g) {
		
		if(gameOver==false) {
			gBuffer.setColor(Color.BLACK);
			gBuffer.fillRect(0, 0, width, height);
			monAire.dessiner(gBuffer);
			
			gBuffer.setColor(Color.WHITE);
			gBuffer.setFont(new Font("Raleway", Font.PLAIN, 40)); 
			
			gBuffer.drawString("Vies : "+vies, 400, 100);
			
			if(start==false) {
				gBuffer.setColor(Color.WHITE);
				gBuffer.drawString("Click to start", monAire.w/4,monAire.h/2+50);
			}
		}
		else {
			gBuffer.setColor(Color.BLACK);
			gBuffer.fillRect(0, 0, width, height);
			gBuffer.setColor(Color.WHITE);
			gBuffer.drawString("GAME OVER", (monAire.w/2)-20, monAire.h/2);
			gBuffer.setFont(new Font("Raleway", Font.PLAIN, 30)); 
			gBuffer.drawString("Press Space to restart", monAire.w/3, (monAire.h/2)+50);
		}
		
		
        g.drawImage(doubleBuffer,0,0,this);
        //pauseSonButton.repaint();
        
	}    
	
	
	public void CollisionBriqueBalle() {
		boolean jaifini=false;
		for(int i=0;i<22;i++) {
			for(int j=0;j<16;j++) {
					if(monAire.tabric[i][j].checkCollision(monAire.maBalle.getRectangleH()) || monAire.tabric[i][j].checkCollision(monAire.maBalle.getRectangleB())) 
					{
						monAire.maBalle.dy=-monAire.maBalle.dy;
						jaifini=true;
						
						monAire.tabric[i][j].cptCollisions++;
						
						if(monAire.tabric[i][j].vivant==false) {
							monAire.cptBriques--;
						}
						
						//System.out.println(monAire.cptBriques);
						System.out.println(monAire.tabric[i][j].cptCollisions);
						
							if(monAire.lvl[i][j].equals ("3")) {
								monAire.balleBonus.dx=1.6;
								monAire.balleBonus.dy=-1.6;
								monAire.balleBonusLiberee = true;
							}
					
						
					}
					if(monAire.tabric[i][j].checkCollision(monAire.maBalle.getRectangleG()) || monAire.tabric[i][j].checkCollision(monAire.maBalle.getRectangleD())) 
					{
						monAire.maBalle.dx=-monAire.maBalle.dx;
						jaifini=true;
						
						monAire.tabric[i][j].cptCollisions++;
						
						if(monAire.tabric[i][j].vivant==false) {
							monAire.cptBriques--;
						}
						
						//System.out.println(monAire.cptBriques);
						System.out.println(monAire.tabric[i][j].cptCollisions);
						
						if(monAire.lvl[i][j].equals ("3")) {
							monAire.balleBonus.dx=1.6;
							monAire.balleBonus.dy=-1.6;
							monAire.balleBonusLiberee = true;
						}

					}
					if(jaifini) break;	

			}
			if(jaifini) break;
		}
	}
	
	public void CollisionBriqueBalleBonus() {
		boolean jaifini=false;
		for(int i=0;i<22;i++) {
			for(int j=0;j<16;j++) {
					if(monAire.tabric[i][j].checkCollision(monAire.balleBonus.getRectangleH()) || monAire.tabric[i][j].checkCollision(monAire.balleBonus.getRectangleB())) 
					{
						monAire.balleBonus.dy=-monAire.balleBonus.dy;
						jaifini=true;
						
						monAire.tabric[i][j].cptCollisions++;
						
						if(monAire.tabric[i][j].vivant==false) {
							monAire.cptBriques--;
						}
						
						System.out.println(monAire.tabric[i][j].cptCollisions);
						
					
					
						
					}
					if(monAire.tabric[i][j].checkCollision(monAire.balleBonus.getRectangleG()) || monAire.tabric[i][j].checkCollision(monAire.balleBonus.getRectangleD())) 
					{
						monAire.balleBonus.dx=-monAire.balleBonus.dx;
						jaifini=true;
						
						monAire.tabric[i][j].cptCollisions++;
						
						if(monAire.tabric[i][j].vivant==false) {
							monAire.cptBriques--;
						}
						
						System.out.println(monAire.tabric[i][j].cptCollisions);
						

						

					}
					if(jaifini) break;	

			}
			if(jaifini) break;
		}
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		KassMoua monJeu = new KassMoua();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
			
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int key = e.getKeyCode();
		switch(key) {
		case KeyEvent.VK_DOWN :
			System.out.println("bas");
			this.repaint();
			break;
			
		case KeyEvent.VK_RIGHT :
			System.out.println("droite");
			monAire.maRaquette.bouge(5);
			this.repaint();
			break;
			
		case KeyEvent.VK_UP :
			System.out.println("haut");
			this.repaint();
			break;
			
		case KeyEvent.VK_LEFT :
			System.out.println("gauche");
			monAire.maRaquette.bouge(-5);
			this.repaint();
			break;
			
		case KeyEvent.VK_SPACE :
			System.out.println("espace");
			if(gameOver==true)
			{
				monAire=new AireDeJeu(width,height,numLvl);
				gameOver=false;
				vies=2;
			}
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(start==false) {
			monAire.maBalle.dx=1.6;
			monAire.maBalle.dy=-1.6;

		}
		start=true;
		System.out.println("start");
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		int xSouris = e.getX();
		int ySouris = e.getY();
		monAire.maRaquette.position(xSouris-monAire.maRaquette.larg/2, monAire.x+monAire.bord, monAire.x+monAire.w-monAire.maRaquette.larg-monAire.bord);
		if(start==false) {
			monAire.maBalle.position(xSouris-7, monAire.x+monAire.bord+monAire.maRaquette.larg/2, monAire.x+monAire.w-monAire.maRaquette.larg/2-monAire.bord);
		}
		repaint();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		while(true) {
			try {
					if(monAire.maBalle.x<(monAire.x+monAire.bord) || monAire.maBalle.x>(monAire.x+monAire.w-2*monAire.bord))
					{
						monAire.maBalle.dx=-monAire.maBalle.dx;
					}		
					if(monAire.maBalle.y<60)
					{
						monAire.maBalle.dy=-monAire.maBalle.dy;
					}
					
					if(monAire.balleBonus.x<(monAire.x+monAire.bord) || monAire.balleBonus.x>(monAire.x+monAire.w-2*monAire.bord))
					{
						monAire.balleBonus.dx=-monAire.balleBonus.dx;
					}		
					if(monAire.balleBonus.y<60)
					{
						monAire.balleBonus.dy=-monAire.balleBonus.dy;
					}
					
					
					
				System.out.println(monAire.cptBriques);	
					
				CollisionBriqueBalle();
				
				//si la balle bonus est libérée, elle est dans le champ
				if(monAire.balleBonusLiberee) {
					CollisionBriqueBalleBonus();
					balleBonusHorsChamp=false;
				}
				else {
					if(start) {
						balleBonusHorsChamp=true;
					}
					
				}
					
				monAire.testCollisionRaquette();
				monAire.maBalle.bouge();
				monAire.balleBonus.bouge();
				Thread.sleep(5);
				
				//si les balles sortent du jeu, elles sont hors champ
				if(monAire.maBalle.getY()>(monAire.y+monAire.h)){
					balleHorsChamp=true;
				}
				if(monAire.balleBonus.getY()>(monAire.y+monAire.h)){
					balleBonusHorsChamp=true;
				}
				
				if ( balleBonusHorsChamp && balleHorsChamp )
				{	
						vies--;
						if(vies==0) {
							gameOver=true;
						}
						else//réinitialisation du niveau
						{
							monAire=new AireDeJeu(width,height,numLvl);
							start=false;
							balleBonusHorsChamp=false;
							balleHorsChamp=false;
						}
					
					
				}
				
				win=false;
				if(monAire.cptBriques==-1)
				{
					win=true;
				}
				
				if(win==true)
				{
					monAire.levelLoad(numLvl+1);
					monAire=new AireDeJeu(width,height,numLvl+1);
					gameOver=false;
					System.out.println("Gagné");
					start=false;
					balleHorsChamp=false;
				}
				
			}
			catch(Exception ex) {
				
			}
			repaint();
		}
	}
	
	

}
