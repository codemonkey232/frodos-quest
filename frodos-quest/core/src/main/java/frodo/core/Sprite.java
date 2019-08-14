package frodo.core;

import static frodo.core.Direction.NUM_DIRECTIONS;
import static frodo.core.PixelConstants.*;

public abstract class Sprite {
  public ZImage[] images;
  public ZImage image;

  public int x = 160;
  public int y = 140;
  
  protected int imageFrame = 0;
  protected int framesPerImage = 4;
    
  public void update(State state) {}

  public abstract void move(Scene scene);
    
  public void draw(ZoomSurface surface, int frame) {
    surface.drawImage(image, x - (image.width() / 2), y - image.height() + 2);
  }
  
  protected ZImage image(Direction d, int frame) {
    int numFrames = images.length / NUM_DIRECTIONS;
    return images[d.ordinal() * numFrames + (frame % numFrames)];
  }
  
  protected boolean moveAndAnimate(Mask mask, Direction d) {
    if (move(mask, d.dx, d.dy)) {
      animate(d);
      return true;
    }
    return false;
  }
  
  protected boolean move(Mask mask, int dx, int dy) {
    if (dx == 0 && dy == 0) return false;
    int newX = x + dx;
    int newY = y + dy;
    if (mask.contains(newX, newY) &&
        (isPixelOn(mask, newX, newY) || !isPixelOn(mask, x, y))) {
      x = newX;
      y = newY;
      return true;
    }
    return false;
  }
  
  protected void animate(Direction d) {
    imageFrame += 1;
    image = image(d, imageFrame / framesPerImage);
  }
  
  protected boolean isPixelOn(Mask mask, int px, int py) {
    return Toolkit.isPixelOn(mask.image, px, py);
  }
    
  public boolean isCloseTo(Sprite that, int px) {
    return Math.abs(that.x - this.x) <= (px * SCENE_X_ZOOM)
        && Math.abs(that.y - this.y) <= (px * SCENE_Y_ZOOM);
  }
}
