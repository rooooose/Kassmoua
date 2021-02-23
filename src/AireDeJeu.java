import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class AireDeJeu {

	int x,y;
	int w,h;
	int bord;
	Color coulBord,coulFond;
	Raquette maRaquette;
	Balle maBalle;
	Brique[][] tabric;
	public String[][] lvl;
	// déclaration de l’image
	Image imgfond ;
	// adresse de l’image
	String imgfondAdresse="img/imgfond.jpg";
	int nbLignes = 16;
	int nbColonnes = 22;
	int cptBriques= 0;
	Balle balleBonus;
	int cptBriquesBonus;
	boolean balleBonusLiberee;
	
	public AireDeJeu(int p1,int p2, int _numLevel){
		
		w=500;
		h=700;
		x=(p1-w)/2;
		y=(p2-h)/2;
		bord=10;
		coulBord=new Color(255,64,171);
		coulFond=new Color (255,243,238);
		
		//on charge les images
 		try {
 		//on récupère l'image à l'adresse où on l’a mise…
 		imgfond=Toolkit.getDefaultToolkit().getImage((imgfondAdresse));
 		//fin chargement des images.
 		} catch (Exception e) {System.out.println("erreur dans le chargement des images:"+e);};
 		System.out.println("chargement des images ok");
		
		maRaquette=new Raquette(p1,p2);
		maBalle=new Balle(200,725);
		
		tabric= new Brique[nbColonnes][nbLignes];
		lvl = new String[nbColonnes][nbLignes];
		
		balleBonusLiberee = false;
		
		
		levelLoad(_numLevel);
		
		for(int i=0;i<nbColonnes;i++) {
			for(int j=0;j<nbLignes;j++) {
				
				tabric[i][j]=new Brique (i, j);
				
				switch(lvl[i][j]) {
				
					case "1":
						cptBriques++;
						break;
						
					case "2":
						tabric[i][j]=new BriqueDure (i,j);
						cptBriques++;
						break;
						
					case "3":
						Brique nouvelle_brique = new BriqueBonusBalles (i,j);
						tabric[i][j] = nouvelle_brique;

					
						balleBonus = new Balle( nouvelle_brique.x, nouvelle_brique.y);
						break;
						
					case "0":
						tabric[i][j].vivant = false;
						break;
						
				}
				
			}
		}
	}
	
	public void dessiner(Graphics g){
		//g.setColor(coulFond);
		//g.fillRect(x, y, w, h);
		g.drawImage(imgfond,x,y,w,h, null);
		
		g.setColor(coulBord);
		g.fillRect(x, y, bord,h);
		g.fillRect(x, y, w, bord);
		g.fillRect((w-bord+x), y, bord,h);
		
		maRaquette.dessiner(g);
		maBalle.dessiner(g);
		balleBonus.dessiner(g);
		
		g.setColor(Color.WHITE);
		
		for(int i=0;i<nbColonnes;i++) {
			for(int j=0;j<nbLignes;j++) {
				if(tabric[i][j]!=null) {
					tabric[i][j].dessiner(g);
				}	
			}
		}
	
	}
	
	public void testCollisionRaquette()
	{
		if(maBalle.getRectangleH().intersects(maRaquette.getRectangle()) || maBalle.getRectangleB().intersects(maRaquette.getRectangle()))
		{
			maBalle.dy=-maBalle.dy;
		}
		else if(maBalle.getRectangleG().intersects(maRaquette.getRectangle()) || maBalle.getRectangleD().intersects(maRaquette.getRectangle()))
		{
			maBalle.dx=-maBalle.dx;
		}
		
		
		if(balleBonus.getRectangleH().intersects(maRaquette.getRectangle()) || balleBonus.getRectangleB().intersects(maRaquette.getRectangle()))
		{
			balleBonus.dy=-balleBonus.dy;
		}
		else if(balleBonus.getRectangleG().intersects(maRaquette.getRectangle()) || balleBonus.getRectangleD().intersects(maRaquette.getRectangle()))
		{
			balleBonus.dx=-balleBonus.dx;
		}
	}
	
	public void levelLoad(int _numLevel) {
		int numLevel= _numLevel;
		
		try {//création et ouverture d'un flux de lecture
			FileInputStream fstream = new FileInputStream("level"+numLevel+".txt");
			DataInputStream in = new DataInputStream(fstream); //lit les infos
			BufferedReader br = new BufferedReader( new InputStreamReader(in));
			String strLine;
			
			int numligne=0;
			while((strLine=br.readLine()) !=null) {
				String[] tampon=strLine.split(";");
				
				for(int i=0; i<nbColonnes;i++) {
					lvl[i][numligne]=tampon[i];
				}
				System.out.println (strLine);
				numligne++;
			}
			in.close();
		}
		catch(Exception e) {
			System.err.println("Erreur :"+e.getMessage());
		}
	}
	

}
