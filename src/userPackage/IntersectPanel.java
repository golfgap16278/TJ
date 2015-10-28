package userPackage;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.Timer;

import javax.swing.JPanel;

import mainPackage.Jerry;
import mainPackage.MazeGame;

public class IntersectPanel extends JPanel{
	
	Point P1 = new Point(1,4);
	Point P2 = new Point(5,1);
	
	Point P3 = new Point(4,1);
	Point P4 = new Point(3,4);
	
	public IntersectPanel() {
		// TODO Auto-generated constructor stub
		
		this.setPreferredSize(new Dimension(500, 500));
		this.setBackground(Color.GRAY);

	}
	
	@Override
	protected void paintComponent(Graphics g1) {
		// TODO Auto-generated method stub
		super.paintComponent(g1);
		
		Graphics2D g = (Graphics2D) g1;
		
		g.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
		g.setColor(Color.WHITE);
		g.setStroke(new BasicStroke(2));
		
		g.drawLine(P1.x*100, P1.y*100, P2.x*100, P2.y*100);
		
		g.setColor(Color.BLACK);
		g.drawLine(P3.x*100, P3.y*100, P4.x*100, P4.y*100);
		
		System.out.println(Jerry.findIntersectPoint(new Point(2,0),new Point(0,2), new Point(1,1), new Point(-1,-1))[0]);
		System.out.println(Jerry.findIntersectPoint(new Point(2,0),new Point(0,2), new Point(1,1), new Point(-1,-1))[1]);
		
		
	}

}
