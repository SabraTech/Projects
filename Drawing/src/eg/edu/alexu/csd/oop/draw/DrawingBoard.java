package eg.edu.alexu.csd.oop.draw;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

/**
 * The Class DrawingBoard.
 */
public class DrawingBoard extends Canvas {

  /** The Constant serialVersionUID. */
  /*
   * *
   * 
   */
  private static final long serialVersionUID = 1L;
  private DrawingEngine engine;

  /**
   * Instantiates a new drawing board.
   */
  public DrawingBoard(DrawingEngine engine) {
    super();
    super.setSize(962, 680);
    super.setBackground(Color.white);
    super.setLocation(222, 11);
    this.engine = engine;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.awt.Canvas#paint(java.awt.Graphics)
   */
  public void paint(Graphics dummyGraphics) {

    engine.refresh(dummyGraphics);
  }

}
