package game.looping.profissional;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {
	
	private BufferedImage spriteSheet;
	
	public SpriteSheet(String path) {
		try {
			spriteSheet = ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public BufferedImage getSprite(int x, int y, int whidth, int height) {
		return spriteSheet.getSubimage(x, y, whidth, height);
	}
	
	
	

}
