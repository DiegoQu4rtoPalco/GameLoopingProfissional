package game.looping.profissional;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Looping extends Canvas implements Runnable{
	
	private Thread thread;
	private Boolean isRunning = true;

	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	private final int SCALE = 4;
    private final int WHIDTH = 	160 * SCALE;
    private final int HEIGHT = 120 * SCALE;
    
    private SpriteSheet sprite;
    private BufferedImage player[];
    private BufferedImage imagem;
    private int x;
    private int frames = 0;
    private int maxFrames = 20;
    private int curAnimation = 0;
    private int maxAnimation = 3;
	
	public Looping() {
		x = 0;
		sprite = new SpriteSheet("/spritesheet.png");
		player = new BufferedImage[3];
		player[0] = sprite.getSprite(0, 0, 80, 80);
		player[1] = sprite.getSprite(80, 0, 80, 80);
		player[2] = sprite.getSprite(160, 0, 80, 80);
		this.setPreferredSize(new Dimension(WHIDTH, HEIGHT));
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
		x++;
		if(x >= 480) {
			x = 0;
		}
		frames++;
		if(frames >= maxFrames) {
			frames = 0;
			curAnimation++;
			if(curAnimation >= maxAnimation) {
				curAnimation = 0;
			}
		}
	}

	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = imagem.getGraphics();
		g.setColor(new Color(255, 255, 255));
		g.fillRect(0, 0, WHIDTH, HEIGHT);
		g = bs.getDrawGraphics();
		g.drawImage(imagem, 0, 0, WHIDTH, HEIGHT , null);
		
		for(int i = 0; i <= WHIDTH/80; i++) {
			g.setColor(Color.BLACK);
			g.drawLine(0, i * 80, HEIGHT * 80, i * 80);
			for(int f = 0; f <= WHIDTH/20; f++) {
				g.setColor(Color.BLACK);
				g.drawLine(i * 80, 0, i * 80, HEIGHT);
			}
			
		}
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(0, 0, 0, 100));
		g2.fillRect(0, 0, WHIDTH, HEIGHT);
		g2.rotate(Math.toRadians(45), 90, 90);
		g2.drawImage(player[curAnimation], x, 0, null);
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
