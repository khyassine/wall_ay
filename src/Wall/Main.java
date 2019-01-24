package Wall;
import java.awt.Dimension;

import javax.swing.JFrame;

	// -------------------------------------
	// Projet IHM GI3 : Jeu Ball 2D
	// Par Yassine Khatab et Anass Mahmoudi
	// -------------------------------------

public class Main extends JFrame {
	private static final long serialVersionUID = 1L;
	public Main() {
		super("Wall_AY");
		setContentPane(new Wall_AY());
		setPreferredSize(new Dimension(600,600));
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		pack();
		setLocationRelativeTo(null);
		setResizable(false);
	}

	public static void main(String[] args) {
		new Main();
	}	
}
