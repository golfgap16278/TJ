package userPackage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import mainPackage.MazeDrawer;
import mainPackage.Jerry;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MazeDrawer myPanel = new MazeDrawer();
		JFrame myFrame = new JFrame("TJ Maze");

		myFrame.setLayout(new BorderLayout());
		myFrame.add(myPanel, BorderLayout.CENTER);
		myFrame.pack();
		myFrame.setVisible(true);
		myFrame.setLocationRelativeTo(null);
		myFrame.setMinimumSize(new Dimension(640, 360));
		myFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		myFrame.addComponentListener(myPanel);
		myFrame.addKeyListener(myPanel);
		
		try {
			//myPanel.mazeWorld.writeJSON();
			//myPanel.mazeWorld.readJSON();;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		IntersectPanel myPanel = new IntersectPanel();
//		JFrame myFrame = new JFrame("TJ Maze");
//
//		myFrame.setLayout(new BorderLayout());
//		myFrame.add(myPanel, BorderLayout.CENTER);
//		myFrame.pack();
//		myFrame.setVisible(true);
//		myFrame.setLocationRelativeTo(null);
//		myFrame.setMinimumSize(new Dimension(640, 360));
//		myFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
//		boolean isIntersected = Jerry.isIntersected(7.5, 6.5, 8.5, 4.5, 8, 6, 9, 6);
//		System.out.println(isIntersected);


	}

}
