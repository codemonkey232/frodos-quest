package frodo.html;

import com.google.gwt.dom.client.CanvasElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.core.client.EntryPoint;
import playn.html.HtmlPlatform;
import playn.html.HtmlGraphics;
import playn.html.HtmlCanvas;
import playn.html.HtmlImage;
import playn.core.Graphics;
import playn.core.Canvas;
import playn.core.Scale;

import frodo.core.FrodosQuest;
import frodo.core.Toolkit;

public class FrodosQuestHtml implements EntryPoint {

  @Override public void onModuleLoad () {
    HtmlPlatform.Config config = new HtmlPlatform.Config();
    config.experimentalFullscreen = true;
    // use config to customize the HTML platform, if needed
    HtmlPlatform plat = new HtmlPlatform(config);
    plat.assets().setPathPrefix("frodos-quest/");
    HtmlCanvasCreator cc = new HtmlCanvasCreator(plat.graphics());
    new FrodosQuest(plat, cc);
    plat.start();
  }

  static class HtmlCanvasCreator implements Toolkit.CanvasCreator {
    private final Graphics graphics;
    
    HtmlCanvasCreator(Graphics graphics) {
      this.graphics = graphics;
    }

    public Canvas createCanvas(int pixelWidth, int pixelHeight) {
      CanvasElement elem = Document.get().createCanvasElement();
      elem.setWidth(pixelWidth);
      elem.setHeight(pixelHeight);
      return new HtmlCanvas(graphics, new HtmlImage(graphics, Scale.ONE, elem, "<canvas>"));
    }
  }
}
