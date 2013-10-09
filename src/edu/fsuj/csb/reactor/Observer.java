package edu.fsuj.csb.reactor;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Observer extends JFrame {
	/**
   * 
   */
  private static final long serialVersionUID = -7132922654936912916L;
	private int refreshLatency=50;
  private int histLength=50;
	private Observable dataSource;	
	private Vector<Double> maxHistory=new Vector<Double>();
	private Vector<Integer> sizeHistory=new Vector<Integer>();
	private PaintPanel drawPanel;
	
	private class PaintPanel extends JPanel{
    private static final long serialVersionUID = 648033512192629081L;

		public void paint(Graphics g) {
		  super.paint(g);
		  SnapShot snap=dataSource.snapShot();
			
		  
		  maxHistory.add(snap.max());
			if (maxHistory.size()>histLength) maxHistory.remove(0);
			double max=0;
			for (double d:maxHistory){
				if (d>max) max=d;
			}
			
			sizeHistory.add(snap.size());
			if (sizeHistory.size()>histLength) sizeHistory.remove(0);
			int maxSize=0;
			for (int s:sizeHistory){
				if (s>maxSize) maxSize=s;
			}
						
		  int width=getWidth()-60;
		  int bottom=getHeight()-30;		  
		  double yscale=(bottom-50)/max;
		  int xscale=1;
		  while ((xscale+1)*maxSize<width) xscale++;	  
		  
		  int xd=maxSize/10;
		  if (xd<1) xd=1;
		  
		  int x=0;
		  while (x*xscale<width){
		  	g.drawLine(10+x*xscale, bottom, 10+x*xscale, bottom+15);
		  	g.drawString(""+(x+1), 15+x*xscale, bottom +20);
		  	x+=xd;
		  }
		  
		  
		  int imax=(int)max/10;
		  if (imax==0) imax=1;
		  for (int i=0; i<=max; i+=imax){
		  	int y=(int)(i*yscale);
		  	g.drawLine(5, bottom-y, width+5, bottom-y);
		  	g.drawString(""+i, width+10, bottom-y+5);
		  }
		  
		  x=10;		  
		  for (int i=0; i<snap.size(); i++) {
		  	for (int j=0; j<xscale; j++){
		  		g.drawLine(x, bottom, x, bottom-(int)(snap.get(i)*yscale));
		  		x++;
		  	}
      }
		}
	}
	
	private class RefreshThread extends Thread{
		public void run() {
		  super.run();
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
	
	public Observer(Observable dataset) {
		dataSource = dataset;
		drawPanel=new PaintPanel();
		drawPanel.setPreferredSize(new Dimension(800,600));
		drawPanel.setSize(drawPanel.getPreferredSize());
		add(drawPanel);
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		(new RefreshThread()).start();
	}

	public void setLatency(int i) {
		refreshLatency=i;
  }
}
