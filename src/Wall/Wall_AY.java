package Wall;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

	// -------------------------------------
	// Projet IHM GI3 : Jeu Ball 2D
	// Par: Anass Mahmoudi et Yassine Khatab
	// -------------------------------------

public class Wall_AY extends JPanel implements ActionListener, KeyListener {
	private static final long serialVersionUID = 1L;
	private Timer t = new Timer(7, this);
	private Image bar, ball, wall, end, start, win, time;
	private int x_bar, y_bar, x_ball, y_ball, x_ball2, y_ball2;
	private static int v_bar, v_ballX, v_ballY, v_ball2X, v_ball2Y;
	private boolean clickStart = true, gameOver = false, winner = false, twoBall = false;
	
	private Timer max = new Timer(1000, new ActionListener() {
		int seconds = 1;
		@Override
		public void actionPerformed(ActionEvent e) {
			if(seconds>=120) {
				winner = true;
				max.stop();
				seconds=0;
			}
			seconds++;
		}
	});

	// Constructor--------------------------------------------
	public Wall_AY() {
		bar = new ImageIcon("src/bar.jpg").getImage();
		ball = new ImageIcon("src/ball.png").getImage();
		wall = new ImageIcon("src/wall.jpg").getImage();
		end = new ImageIcon("src/end.jpg").getImage();
		start = new ImageIcon("src/start.jpg").getImage();
		win = new ImageIcon("src/win.jpg").getImage();
		time = new ImageIcon("src/time.gif").getImage();
		t.start();
		addKeyListener(this);
		this.setFocusable(true);
	}

	// Paint--------------------------------------------------
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		if(clickStart) g2d.drawImage(start, 0, 0, this); 
		else if(gameOver) {
			g2d.drawImage(end, 0, 0, this);
			t.stop();
			max.stop();
		}
		else if(winner) {
			g2d.drawImage(win, 0, 0, this);
			t.stop();
			max.stop();
		}
		else {
			g2d.drawImage(wall, 0, 0, this);
			g2d.drawImage(time, 40, 40, this);
			g2d.drawImage(bar, x_bar, y_bar, getWidth()/10, getHeight()/22, this);
			g2d.drawImage(ball, x_ball, y_ball, this);
			if(twoBall) g2d.drawImage(ball, x_ball2, y_ball2, this); // Ball 2 Mode
		}
	}
	
	// Bar Moves----------------------------------------------
	public void left() { v_bar = -2; }
	public void right() { v_bar = 2; }
	
	// Ball Moves---------------------------------------------
	public void moveBall() {
		x_ball += v_ballX;
		y_ball += v_ballY;
		if(x_ball==542 || x_ball==20 || (y_ball>=(y_bar-12) && y_ball<=(y_bar+(getHeight()/22)+12) && (x_ball==(x_bar-24) || x_ball==(x_bar+(getWidth()/10)-10+12)))) { 
			v_ballX = -v_ballX;
		}
		if(y_ball==20 || (x_ball>=(x_bar-12) && x_ball<(x_bar+(getWidth()/10)-10+12) && y_ball==(y_bar-24))) {
			v_ballY = -v_ballY;
		}
		if(((x_ball>(x_bar-25) && x_ball<(x_bar-12)) || (x_ball>=(x_bar+(getWidth()/10)-10+12) && x_ball<(x_bar+(getWidth()/10)-10+25)) ) && (y_ball>y_bar-25) && (y_ball<y_bar-12) ) {
			v_ballX = -v_ballX;
			v_ballY = -v_ballY;
		}
	}
	
	// Ball 2 Moves-------------------------------------------
	public void moveBall2() {
		x_ball2 += v_ball2X;
		y_ball2 += v_ball2Y;
		if(x_ball2==542 || x_ball2==20 || (y_ball2>=(y_bar-12) && y_ball2<=(y_bar+(getHeight()/22)+12) && (x_ball2==(x_bar-24) || x_ball2==(x_bar+(getWidth()/10)-10+12)))) { 
			v_ball2X = -v_ball2X;
		}
		if(y_ball2==20 || (x_ball2>=(x_bar-12) && x_ball2<(x_bar+(getWidth()/10)-10+12) && y_ball2==(y_bar-24))) {
			v_ball2Y = -v_ball2Y;
		}
		if(((x_ball2>(x_bar-25) && x_ball2<(x_bar-12)) || (x_ball2>=(x_bar+(getWidth()/10)-10+12) && x_ball2<(x_bar+(getWidth()/10)-10+25)) ) && (y_ball2>y_bar-25) && (y_ball2<y_bar-12) ) {
			v_ball2X = -v_ball2X;
			v_ball2Y = -v_ball2Y;
		}
	}

	// Action Events------------------------------------------
	@Override
	public void actionPerformed(ActionEvent e) {
		if(x_bar>504) { x_bar=504; v_bar=0; }
		if(x_bar<22) { x_bar=22; v_bar=0; }
		x_bar +=v_bar;
		if(twoBall) { // Ball 2 Mode
			moveBall2(); // Ball 2 Mode
			if(y_ball2>561 && y_ball>561) gameOver = true; // 2 Balls must fall down to lose
		}
		else if(y_ball>561) gameOver = true;
		moveBall();
		repaint();
	}

	// Key Events---------------------------------------------
	@Override
	public void keyPressed(KeyEvent e) {	
		int k = e.getKeyCode();
		if(!gameOver && !winner && !clickStart) {
			if(k==KeyEvent.VK_RIGHT) right();
			else if(k==KeyEvent.VK_LEFT) left();
		}
		if (clickStart) {
			if(k==KeyEvent.VK_2 || k==KeyEvent.VK_NUMPAD2) twoBall = true; // Ball 2 Mode
			if(k==KeyEvent.VK_1 || k==KeyEvent.VK_NUMPAD1) twoBall = false; // Ball 2 Mode
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		int k = e.getKeyCode();
		v_bar = 0;
		if(clickStart && k==KeyEvent.VK_SPACE) {
			t.restart();
			clickStart = false;
			x_ball = 200;
			y_ball = 100;
			x_ball2 = 230; // Ball 2 Mode
			y_ball2 = 450; // Ball 2 Mode
			x_bar = 225;
			y_bar = 500;
			v_ballX = -1;
			v_ballY = 1;
			v_ball2X = 1; // Ball 2 Mode
			v_ball2Y = 1; // Ball 2 Mode
			v_bar = 0;
			max.restart();
			time.flush(); // rafraichir le gif
		}
		if((gameOver || winner) && k==KeyEvent.VK_SPACE) {
			clickStart = true;
			gameOver = false;
			winner = false;
			t.stop();
			max.stop();
		}
	}
	@Override
	public void keyTyped(KeyEvent e) { }
}
