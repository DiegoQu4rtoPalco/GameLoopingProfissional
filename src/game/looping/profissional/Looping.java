package game.looping.profissional;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Looping extends Canvas implements Runnable{

	private static final long serialVersionUID = 1L;
	public static JFrame frame;
    private final int WHIDTH = 	160;
    private final int HEIGHT = 120;
    private final int SCALE = 3;
	
	public Looping() {
		this.setPreferredSize(new Dimension(WHIDTH * SCALE, HEIGHT * SCALE));
		initFrame();
	}
	
	private void initFrame() {
		frame = new JFrame();
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public static void main (String [] args) {
		new Looping();
	}

	@Override
	public void run() {

		
	}

}
