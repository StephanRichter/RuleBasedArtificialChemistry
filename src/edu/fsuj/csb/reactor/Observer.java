package edu.fsuj.csb.reactor;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Observer extends JFrame {
	/**
   * 
   */
  private static final long serialVersionUID = -7132922654936912916L;
	private int refreshLatency=1000;
	private Observable dataSource;
	
	private class PaintPanel extends JPanel{
		/**
     * 
     */
    private static final long serialVersionUID = 648033512192629081L;

		public void paint(Graphics g) {
		  super.paint(g);
		  SnapShot snap=dataSource.snapShot();		  
		  int bottom=getHeight()-20;
		  double max=snap.max();
		  double yscale=(bottom-50)/max;
		  int xscale=1;
		  while ((xscale+1)*snap.size()<getWidth()-20) xscale++;
		  System.out.println(snap.max());
		  
		  int imax=(int)max/10;
		  if (imax==0) imax=1;
		  for (int i=0; i<max; i+=imax){
		  	int y=(int)(i*yscale);
		  	g.drawLine(5, bottom-y, getWidth()-100, bottom-y);
		  	g.drawString(""+i, getWidth()-100, bottom-y);
		  }
		  
		  int x=10;		  
		  for (int i=0; i<snap.size(); i++) {
		  	for (int j=0; j<xscale; j++){
		  		g.drawLine(x, bottom, x, bottom-(int)(snap.get(i)*yscale));
		  		x++;
		  	}
      }
		}
	}
	
	public Observer(Observable dataset) {
		dataSource = dataset;
		final PaintPanel drawPanel=new PaintPanel();
		drawPanel.setPreferredSize(new Dimension(800,600));
		drawPanel.setSize(drawPanel.getPreferredSize());
		add(drawPanel);
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);

		while (true){
			try {
	      Thread.sleep(refreshLatency);
      } catch (InterruptedException e) {
	      e.printStackTrace();
      }
			SwingUtilities.invokeLater(new Runnable() {
				
				public void run() {
					drawPanel.repaint();
				}
			});
		}
	}
}
