package mainPackage;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.Visibility;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

import mainPackage.GridCell.visibleObject;
import mainPackage.MazeGame.Difficulty;
import userPackage.SmartJerry;

public class MazeDrawer extends JPanel implements ComponentListener, KeyListener {

	final int ROWS = 30;
	final int COLUMNS = 30;

	final int NUMBER_OF_CHEESES = 10;

	final int SLEEP_DURATION = 800; // millisecond

	final Difficulty LEVEL = Difficulty.NORMAL;

	private int height = 720;
	private int width = 1280;

	private int startX = 0;
	private int startY = 0;

	private int lineHeight = 0;
	private int lineWidth = 0;

	private Timer timer;

	MazeGame mazeWorld;

	public MazeDrawer() {

		this.setPreferredSize(new Dimension(width, height));
		this.setBackground(Color.GRAY);

		this.timer = new Timer();

		// maze configuration
		mazeWorld = new MazeGame();
		mazeWorld.createRandomMaze(ROWS, COLUMNS, LEVEL);
		mazeWorld.generateRandomCheese(NUMBER_OF_CHEESES, ROWS, COLUMNS);
		mazeWorld.createJerry(ROWS / 2, COLUMNS / 2, new SmartJerry());
		mazeWorld.sleepDuration = this.SLEEP_DURATION;

		MazeGame.MyWorker mazeWorker = mazeWorld.new MyWorker();
		mazeWorker.execute();

		//GraphicLoop();

	}



	public void paintComponent(Graphics g1) {

		// System.out.println("WTF");

		super.paintComponent(g1);
		Graphics2D g = (Graphics2D) g1;

		g.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
		// g.setRenderingHints(new
		// RenderingHints(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_SPEED));

		g.setStroke(new BasicStroke(2));

		//drawCheesePerceptionDegree(g);

		drawVisibility(g, mazeWorld.jerry);
		
		//drawIntersectedLine(g, mazeWorld.jerry);

		g.setStroke(new BasicStroke(1));
		g.setColor(Color.WHITE);

		GridCell[][] cellArray = mazeWorld.worldGraph.cellArray;

		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {

				if (!cellArray[i][j].hasEastEdge)
					drawRightWall(g, cellArray[i][j].rowIndex, cellArray[i][j].columnIndex);
				if (!cellArray[i][j].hasSouthEdge)
					drawLowerWall(g, cellArray[i][j].rowIndex, cellArray[i][j].columnIndex);

			}
		}

//		 for (GridCell node : mazeWorld.worldGraph.cellArray) {
//		 if (!node.hasEastEdge)
//		 drawRightWall(g, node.rowIndex, node.columnIndex);
//		 if (!node.hasSouthEdge)
//		 drawLowerWall(g, node.rowIndex, node.columnIndex);
//		 }

		g.setStroke(new BasicStroke(5));

		drawBorder(g);

		g.setStroke(new BasicStroke(3));

		// DrawGraph(g);

		g.setStroke(new BasicStroke(1));
		g.setColor(new Color(255, 193, 7));

		for (Cheese i : mazeWorld.cheeseSet) {
			drawCheese(g, i.rowIndex, i.columnIndex);
		}

		g.setColor(Color.DARK_GRAY);

		// DrawJerry(g, mazeWorld.jerry.rowIndex, mazeWorld.jerry.columnIndex);

		drawMovingJerry(g, mazeWorld.jerry);

		// System.out.println(mazeWorld.stepCount);

	}

	private void drawRightWall(Graphics2D g, int rowIndex, int columnIndex) {

		g.drawLine(startX + (columnIndex + 1) * lineWidth, startY + rowIndex * lineHeight,
				startX + (columnIndex + 1) * lineWidth, startY + (rowIndex + 1) * lineHeight);

	}

	private void drawLowerWall(Graphics2D g, int rowIndex, int columnIndex) {

		g.drawLine(startX + columnIndex * lineWidth, startY + (rowIndex + 1) * lineHeight,
				startX + (columnIndex + 1) * lineWidth, startY + (rowIndex + 1) * lineHeight);

	}

	private void drawBorder(Graphics2D g) {

		g.drawRect(startX, startY, lineWidth * COLUMNS, lineHeight * ROWS);

	}

	private void drawCheese(Graphics2D g, int rowIndex, int columnIndex) {

		g.fillOval(startX + Math.round((columnIndex + 0.25f) * lineWidth),
				startY + Math.round((rowIndex + 0.25f) * lineHeight), Math.round(0.5f * lineWidth),
				Math.round(0.5f * lineHeight));

		// Polygon p = new Polygon();
		//
		// p.addPoint(startX+(int)((columnIndex+0.15)*lineWidth),
		// startY+(int)((rowIndex+0.3)*lineHeight));
		// p.addPoint(startX+(int)((columnIndex+0.15)*lineWidth),
		// startY+(int)((rowIndex+0.7)*lineHeight));
		// p.addPoint(startX+(int)((columnIndex+0.85)*lineWidth),
		// startY+(int)((rowIndex+0.7)*lineHeight));
		//
		// g.fillPolygon(p);

	}

	private void drawFadingCheese(Graphics2D g, int rowIndex, int columnIndex) {

		long currentTime = System.currentTimeMillis();
		float fadingProgress = (currentTime - mazeWorld.workerTime) / (float) SLEEP_DURATION;

		g.setColor(new Color(255, 193, 7, 120));

		g.fillOval(startX + Math.round((columnIndex + 0.25f) * lineWidth),
				startY + Math.round((rowIndex + 0.25f) * lineHeight), Math.round(0.5f * lineWidth),
				Math.round(0.5f * lineHeight));

	}

	private void drawJerry(Graphics2D g, int rowIndex, int columnIndex) {

		g.setColor(Color.darkGray);

		g.fillOval(startX + Math.round((columnIndex + 0.25f) * lineWidth),
				startY + Math.round((rowIndex + 0.25f) * lineHeight), Math.round(0.5f * lineWidth),
				Math.round(0.5f * lineHeight));

		g.setStroke(new BasicStroke(2));
		g.setColor(Color.white);

		g.drawOval(startX + Math.round((columnIndex + 0.25f) * lineWidth),
				startY + Math.round((rowIndex + 0.25f) * lineHeight), Math.round(0.5f * lineWidth),
				Math.round(0.5f * lineHeight));

	}

	private void drawMovingJerry(Graphics2D g, Jerry jerry) {

		long currentTime = System.currentTimeMillis();
		double x = (currentTime - mazeWorld.workerTime) / (double) SLEEP_DURATION;

		// float movingProgress = (float)x;
		float movingProgress = (float) (1.0 / (1 + Math.exp(-16.0 * (x - 0.5))));

		if (movingProgress > 1)
			movingProgress = 1;

		if (movingProgress < 0)
			movingProgress = 0;

		if (jerry.tempCell == null) {
			// start
			g.setColor(Color.darkGray);

			g.fillOval(startX + Math.round((jerry.currentCell.columnIndex + 0.25f) * lineWidth),
					startY + Math.round((jerry.currentCell.rowIndex + 0.25f) * lineHeight),
					Math.round(0.5f * lineWidth), Math.round(0.5f * lineHeight));

			g.setStroke(new BasicStroke(2));
			g.setColor(Color.white);

			g.drawOval(startX + Math.round((jerry.currentCell.columnIndex + 0.25f) * lineWidth),
					startY + Math.round((jerry.currentCell.rowIndex + 0.25f) * lineHeight),
					Math.round(0.5f * lineWidth), Math.round(0.5f * lineHeight));

		} else if (jerry.currentCell.rowIndex == jerry.tempCell.rowIndex
				& jerry.currentCell.columnIndex == jerry.tempCell.columnIndex) {
			// stop
			g.setColor(Color.darkGray);

			g.fillOval(startX + Math.round((jerry.currentCell.columnIndex + 0.25f) * lineWidth),
					startY + Math.round((jerry.currentCell.rowIndex + 0.25f) * lineHeight),
					Math.round(0.5f * lineWidth), Math.round(0.5f * lineHeight));

			g.setStroke(new BasicStroke(2));
			g.setColor(Color.white);

			g.drawOval(startX + Math.round((jerry.currentCell.columnIndex + 0.25f) * lineWidth),
					startY + Math.round((jerry.currentCell.rowIndex + 0.25f) * lineHeight),
					Math.round(0.5f * lineWidth), Math.round(0.5f * lineHeight));

		} else {
			// moving
			g.setColor(Color.darkGray);
			g.setStroke(new BasicStroke(2));

			if (jerry.currentCell.rowIndex == jerry.tempCell.rowIndex
					& jerry.currentCell.columnIndex - 1 == jerry.tempCell.columnIndex) {
				// move to the right
				g.fillOval(startX + Math.round((jerry.tempCell.columnIndex + 0.25f + movingProgress) * lineWidth),
						startY + Math.round((jerry.tempCell.rowIndex + 0.25f) * lineHeight),
						Math.round(0.5f * lineWidth), Math.round(0.5f * lineHeight));

				g.setColor(Color.white);

				g.drawOval(startX + Math.round((jerry.tempCell.columnIndex + 0.25f + movingProgress) * lineWidth),
						startY + Math.round((jerry.tempCell.rowIndex + 0.25f) * lineHeight),
						Math.round(0.5f * lineWidth), Math.round(0.5f * lineHeight));

			} else if (jerry.currentCell.rowIndex == jerry.tempCell.rowIndex
					& jerry.currentCell.columnIndex + 1 == jerry.tempCell.columnIndex) {
				// move to the left
				g.fillOval(startX + Math.round((jerry.tempCell.columnIndex + 0.25f - movingProgress) * lineWidth),
						startY + Math.round((jerry.tempCell.rowIndex + 0.25f) * lineHeight),
						Math.round(0.5f * lineWidth), Math.round(0.5f * lineHeight));

				g.setColor(Color.white);

				g.drawOval(startX + Math.round((jerry.tempCell.columnIndex + 0.25f - movingProgress) * lineWidth),
						startY + Math.round((jerry.tempCell.rowIndex + 0.25f) * lineHeight),
						Math.round(0.5f * lineWidth), Math.round(0.5f * lineHeight));

			} else if (jerry.currentCell.rowIndex - 1 == jerry.tempCell.rowIndex
					& jerry.currentCell.columnIndex == jerry.tempCell.columnIndex) {
				// move down
				g.fillOval(startX + Math.round((jerry.tempCell.columnIndex + 0.25f) * lineWidth),
						startY + Math.round((jerry.tempCell.rowIndex + 0.25f + movingProgress) * lineHeight),
						Math.round(0.5f * lineWidth), Math.round(0.5f * lineHeight));

				g.setColor(Color.white);

				g.drawOval(startX + Math.round((jerry.tempCell.columnIndex + 0.25f) * lineWidth),
						startY + Math.round((jerry.tempCell.rowIndex + 0.25f + movingProgress) * lineHeight),
						Math.round(0.5f * lineWidth), Math.round(0.5f * lineHeight));

			} else if (jerry.currentCell.rowIndex + 1 == jerry.tempCell.rowIndex
					& jerry.currentCell.columnIndex == jerry.tempCell.columnIndex) {
				// move up
				g.fillOval(startX + Math.round((jerry.tempCell.columnIndex + 0.25f) * lineWidth),
						startY + Math.round((jerry.tempCell.rowIndex + 0.25f - movingProgress) * lineHeight),
						Math.round(0.5f * lineWidth), Math.round(0.5f * lineHeight));

				g.setColor(Color.white);

				g.drawOval(startX + Math.round((jerry.tempCell.columnIndex + 0.25f) * lineWidth),
						startY + Math.round((jerry.tempCell.rowIndex + 0.25f - movingProgress) * lineHeight),
						Math.round(0.5f * lineWidth), Math.round(0.5f * lineHeight));
			}
		}
	}
	
	private void drawIntersectedLine(Graphics2D g, Jerry jerry){
		
		int visionRange = jerry.visionRange;
		visibleObject tempVisibility[][] = jerry.getVisibility();
		
		GridCell[][] cellArray = mazeWorld.worldGraph.cellArray;
		GridCell cell = jerry.currentCell;

		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {

//				if (!cellArray[i][j].hasEastEdge)
//					drawRightWall(g, cellArray[i][j].rowIndex, cellArray[i][j].columnIndex);
//				if (!cellArray[i][j].hasSouthEdge)
//					drawLowerWall(g, cellArray[i][j].rowIndex, cellArray[i][j].columnIndex);

			}
		}
		
		for (int i = (-visionRange); i < visionRange + 1; i++) {
			for (int j = (-visionRange); j < visionRange + 1; j++) {
				
				boolean indicator = false;
				
				for (int k = cell.rowIndex-visionRange; k < cell.rowIndex + visionRange + 1; k++) {
					for (int l = (cell.columnIndex-visionRange); l < cell.columnIndex + visionRange + 1; l++) {
						
						if (!cellArray[k][l].hasEastEdge){
							
							Boolean isIntersected = Jerry.isIntersected(cell.columnIndex + j + 0.5,
									cell.rowIndex + i + 0.5, cell.columnIndex + 0.5, cell.rowIndex + 0.5,
									cellArray[k][l].columnIndex + 1, cellArray[k][l].rowIndex,
									cellArray[k][l].columnIndex + 1, cellArray[k][l].rowIndex + 1);			
							
//							System.out.println("x1 = " + (cell.columnIndex+j+0.5));
//							System.out.println("y1 = " + (cell.rowIndex + i + 0.5));
//							System.out.println("x2 = " + (cell.columnIndex + 0.5));
//							System.out.println("y2 = " + (cell.rowIndex + 0.5));
//							System.out.println("x3 = " + (cellArray[k][l].columnIndex + 1));
//							System.out.println("y3 = " + (cellArray[k][l].rowIndex));
//							System.out.println("x4 = " + (cellArray[k][l].columnIndex + 1));
//							System.out.println("y4 = " + (cellArray[k][l].rowIndex + 1));
//							System.out.println("==============================");
							
							if(isIntersected != null){
								if(isIntersected.booleanValue())
									indicator = true;
							}
						}
							
						if (!cellArray[k][l].hasSouthEdge){
							
							Boolean isIntersected = Jerry.isIntersected(cell.columnIndex + j + 0.5,
									cell.rowIndex + i + 0.5, cell.columnIndex + 0.5, cell.rowIndex + 0.5,
									cellArray[k][l].columnIndex , cellArray[k][l].rowIndex + 1,
									cellArray[k][l].columnIndex + 1, cellArray[k][l].rowIndex + 1);
							
//							System.out.println("x1 = " + (cell.columnIndex+j+0.5));
//							System.out.println("y1 = " + (cell.rowIndex + i + 0.5));
//							System.out.println("x2 = " + (cell.columnIndex + 0.5));
//							System.out.println("y2 = " + (cell.rowIndex + 0.5));
//							System.out.println("x3 = " + (cellArray[k][l].columnIndex + 1));
//							System.out.println("y3 = " + (cellArray[k][l].rowIndex));
//							System.out.println("x4 = " + (cellArray[k][l].columnIndex + 1));
//							System.out.println("y4 = " + (cellArray[k][l].rowIndex + 1));
//							System.out.println(isIntersected);
//							System.out.println("==============================");
							
							if(isIntersected != null){
								if(isIntersected.booleanValue())
									indicator = true;
							}
						}

					}
				}
				
				if(indicator){
					
					g.setColor(Color.RED);
					g.drawLine(Math.round(startX + (jerry.currentCell.columnIndex + j + 0.5f) * lineWidth),
					Math.round(startY + (jerry.currentCell.rowIndex + i + 0.5f) * lineHeight),
					Math.round(startX + (jerry.currentCell.columnIndex + 0.5f) * lineWidth),
					Math.round(startY + (jerry.currentCell.rowIndex + 0.5f) * lineHeight));
					
				}else{
					
					g.setColor(Color.BLACK);
					g.drawLine(Math.round(startX + (jerry.currentCell.columnIndex + j + 0.5f) * lineWidth),
					Math.round(startY + (jerry.currentCell.rowIndex + i + 0.5f) * lineHeight),
					Math.round(startX + (jerry.currentCell.columnIndex + 0.5f) * lineWidth),
					Math.round(startY + (jerry.currentCell.rowIndex + 0.5f) * lineHeight));
					
				}
				
				
			}
		}
	}
	
	
	

	private void drawVisibility(Graphics2D g, Jerry jerry) {

		int visionRange = jerry.visionRange;
		visibleObject tempVisibility[][] = jerry.getVisibility();
		
		for(int r=0 ; r<ROWS ; r++){
			for(int c=0 ; c<COLUMNS ; c++){
				if((r < jerry.currentCell.rowIndex-visionRange | r > jerry.currentCell.rowIndex+visionRange) | (c < jerry.currentCell.columnIndex-visionRange | c > jerry.currentCell.columnIndex+visionRange)){
					g.setColor(Color.DARK_GRAY);
					g.fillRect(startX + (c) * lineWidth,
							startY + (r) * lineHeight, lineWidth, lineHeight);
				}
			}
		}
		
		

		for (int i = (-visionRange); i < visionRange + 1; i++) {
			for (int j = (-visionRange); j < visionRange + 1; j++) {

				if (tempVisibility[i + visionRange][j + visionRange] == visibleObject.CHEESE) {

					g.setColor(Color.yellow.brighter().brighter());
					g.fillRect(startX + (jerry.currentCell.columnIndex + j) * lineWidth,
							startY + (jerry.currentCell.rowIndex + i) * lineHeight, lineWidth, lineHeight);

				} else if (tempVisibility[i + visionRange][j + visionRange] == visibleObject.JERRY) {

					g.setColor(Color.PINK);
					g.fillRect(startX + (jerry.currentCell.columnIndex + j) * lineWidth,
							startY + (jerry.currentCell.rowIndex + i) * lineHeight, lineWidth, lineHeight);

				} else if (tempVisibility[i + visionRange][j + visionRange] == visibleObject.TOM) {

					g.setColor(Color.RED.brighter().brighter());
					g.fillRect(startX + (jerry.currentCell.columnIndex + j) * lineWidth,
							startY + (jerry.currentCell.rowIndex + i) * lineHeight, lineWidth, lineHeight);

				} else if (tempVisibility[i + visionRange][j + visionRange] == visibleObject.EMPTY_SPACE) {

					g.setColor(Color.lightGray);
					g.fillRect(startX + (jerry.currentCell.columnIndex + j) * lineWidth,
							startY + (jerry.currentCell.rowIndex + i) * lineHeight, lineWidth, lineHeight);

				} else if (tempVisibility[i + visionRange][j + visionRange] == visibleObject.NULL) {

					g.setColor(Color.DARK_GRAY);
					g.fillRect(startX + (jerry.currentCell.columnIndex + j) * lineWidth,
							startY + (jerry.currentCell.rowIndex + i) * lineHeight, lineWidth, lineHeight);

				}

			}
		}

		for (int i = (-visionRange); i < visionRange + 1; i++) {
			for (int j = (-visionRange); j < visionRange + 1; j++) {

				if(tempVisibility[i + visionRange][j + visionRange] != visibleObject.NULL){
				
					g.setStroke(new BasicStroke(3));
					g.setColor(Color.WHITE);
					g.drawLine(Math.round(startX + (jerry.currentCell.columnIndex + j + 0.5f) * lineWidth),
							Math.round(startY + (jerry.currentCell.rowIndex + i + 0.5f) * lineHeight),
							Math.round(startX + (jerry.currentCell.columnIndex + 0.5f) * lineWidth),
							Math.round(startY + (jerry.currentCell.rowIndex + 0.5f) * lineHeight));
					
				}
	
			}
		}

	}

	private void drawCheesePerceptionDegree(Graphics2D g) {

		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {

				int degree = (int) mazeWorld.worldGraph.cellArray[i][j].cheesePerceptionDegree;

				g.setColor(new Color(255, 255, 255 - 5*degree));
				g.fillRect(startX + j * lineWidth, startY + i * lineHeight, lineWidth, lineHeight);

			}
		}

	}
	
	private void GraphicLoop() {

		this.timer.scheduleAtFixedRate(new TimerTask() {

			public void run() {
				if (!mazeWorld.isEnd)
					repaint();
				else
					cancel();
			}
		}, 0, 5); // this method will call repaint() at every 10 millisec(100
					// FPS)

	}

	private void calculateHeightAndWidth() {

		lineHeight = height / ROWS;
		lineWidth = width / COLUMNS;

		if (lineHeight > lineWidth) {
			lineHeight = lineWidth;
		} else {
			lineWidth = lineHeight;
		}

	}

	private void calculateStartingPoint() {

		int verticalGap = height - lineHeight * ROWS;
		int horizontalGap = width - lineWidth * COLUMNS;

		startX = horizontalGap / 2;
		startY = verticalGap / 2;

	}

	// private void drawGraph(Graphics2D g) {
	//
	// for (GridCell i : mazeWorld.worldGraph.cellList) {
	//
	// g.setColor(Color.lightGray.darker().darker());
	//
	// if (i.hasNorthEdge)
	// g.drawLine(startX + (int) ((i.columnIndex + 0.5) * lineWidth),
	// startY + (int) ((i.rowIndex + 0.5) * lineHeight),
	// startX + (int) ((i.columnIndex + 0.5) * lineWidth),
	// startY + (int) ((i.rowIndex + 0.5 - 1) * lineHeight));
	// if (i.hasSouthEdge)
	// g.drawLine(startX + (int) ((i.columnIndex + 0.5) * lineWidth),
	// startY + (int) ((i.rowIndex + 0.5) * lineHeight),
	// startX + (int) ((i.columnIndex + 0.5) * lineWidth),
	// startY + (int) ((i.rowIndex + 0.5 + 1) * lineHeight));
	// if (i.hasWestEdge)
	// g.drawLine(startX + (int) ((i.columnIndex + 0.5) * lineWidth),
	// startY + (int) ((i.rowIndex + 0.5) * lineHeight),
	// startX + (int) ((i.columnIndex + 0.5 - 1) * lineWidth),
	// startY + (int) ((i.rowIndex + 0.5) * lineHeight));
	// if (i.hasEastEdge)
	// g.drawLine(startX + (int) ((i.columnIndex + 0.5) * lineWidth),
	// startY + (int) ((i.rowIndex + 0.5) * lineHeight),
	// startX + (int) ((i.columnIndex + 0.5 + 1) * lineWidth),
	// startY + (int) ((i.rowIndex + 0.5) * lineHeight));
	//
	// }
	//
	// for (GridCell i : mazeWorld.worldGraph.cellList) {
	//
	// g.setColor(Color.DARK_GRAY);
	// g.fillOval(startX + (int) ((i.columnIndex + 0.25) * lineWidth),
	// startY + (int) ((i.rowIndex + 0.25) * lineHeight), (int) (0.5 *
	// lineWidth),
	// (int) (0.5 * lineHeight));
	//
	// }
	// }

	// public static void main(String[] args) {
	//
	// Drawer myPanel = new Drawer();
	// JFrame myFrame = new JFrame("TJ Maze");
	//
	// myFrame.setLayout(new BorderLayout());
	// myFrame.add(myPanel, BorderLayout.CENTER);
	// myFrame.pack();
	// myFrame.setVisible(true);
	// myFrame.setLocationRelativeTo(null);
	// myFrame.setMinimumSize(new Dimension(640, 360));
	// myFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	//
	// myFrame.addComponentListener(myPanel);
	// // myFrame.addKeyListener(myPanel);
	//
	// }

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentResized(ComponentEvent e) {

		width = this.getWidth();
		height = this.getHeight();

		calculateHeightAndWidth();
		calculateStartingPoint();

	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_SPACE) {

			mazeWorld = new MazeGame();
			mazeWorld.createRandomMaze(ROWS, COLUMNS, LEVEL);
			mazeWorld.generateRandomCheese(NUMBER_OF_CHEESES, ROWS, COLUMNS);
			mazeWorld.createJerry(ROWS / 2, COLUMNS / 2, new SmartJerry());
			this.repaint();
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
