import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Line2D;

import javax.swing.JPanel;
public class SudokuBoardLines extends JPanel {
	
	SudokuBoardLines(){}
	
	@Override
    public void paint(java.awt.Graphics g)
    {
      super.paint(g);
      Graphics2D g2 = (Graphics2D) g;
      Stroke stroke = new BasicStroke(3f);
      g2.setStroke(stroke);
      Line2D lin = new Line2D.Float(120, 0, 120, 360);
      Line2D lin2 = new Line2D.Float(240, 0, 240, 360);
      Line2D lin3 = new Line2D.Float(0, 120, 360, 120);
      Line2D lin4 = new Line2D.Float(0, 240, 360, 240);
      g2.draw(lin);
      g2.draw(lin2);
      g2.draw(lin3);
      g2.draw(lin4);
    }
}
