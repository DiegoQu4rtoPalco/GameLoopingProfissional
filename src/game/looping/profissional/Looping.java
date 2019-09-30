package game.looping.profissional;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Looping extends Canvas implements Runnable{
	
	private Thread thread;
	private Boolean isRunning = true;

	private static final long serialVersionUID = 1L;
	public static JFrame frame;
    private final int WHIDTH = 	160;
    private final int HEIGHT = 120;
    private final int SCALE = 3;
    
    private BufferedImage imagem;
	
	public Looping() {
		this.setPreferredSize(new Dimension(WHIDTH * SCALE, HEIGHT * SCALE));
		initFrame();
		imagem = new BufferedImage(WHIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}
	
	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
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
		Looping loopping = new Looping();
		loopping.start();
	}
	
	public void tick() {
		
	}

	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = imagem.getGraphics();
		g.setColor(new Color(19, 19, 19));
		g.fillRect(0, 0, WHIDTH, HEIGHT);
		g = bs.getDrawGraphics();
		g.drawImage(imagem, 0, 0, WHIDTH * SCALE, HEIGHT * SCALE, null);
		bs.show();
	}
	@Override
	public void run() {
		
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;		
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		
		while(isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1) {
				tick();
				render();
				frames++;
				delta--;
			}
			if(System.currentTimeMillis() - timer >= 1000) {
				System.out.println("FPS: " + frames);
				frames = 0;
				timer+=1000;
			}
		}
		stop();
	}

}
