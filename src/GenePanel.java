import java.awt.Graphics;

import javax.swing.JPanel;


public class GenePanel extends JPanel {
	int[][] leftBorder=new int[5][2];
	int[][] rightBorder=new int[4][2];
	
	double pointX;
	double pointY;
	double radious;
	int b;
	
	
	
	public GenePanel(){
		leftBorder[0][0]=-60;
		leftBorder[0][1]=0;
		leftBorder[1][0]=-60;
		leftBorder[1][1]=220;
		leftBorder[2][0]=180;
		leftBorder[2][1]=220;
		leftBorder[3][0]=180;
		leftBorder[3][1]=370;
		leftBorder[4][0]=300;
		leftBorder[4][1]=370;
		
		
		rightBorder[0][0]=60;
		rightBorder[0][1]=0;
		rightBorder[1][0]=60;
		rightBorder[1][1]=100;
		rightBorder[2][0]=300;
		rightBorder[2][1]=100;
		rightBorder[3][0]=300;
		rightBorder[3][1]=370;
			
		pointX=0;
		pointY=0; 
		radious=90;
		b=6;
	}
	
	
	public void paintComponent(Graphics g){
		super.paintComponents(g);
		for(int i=0;i<leftBorder.length-1;i++){
			g.drawLine(leftBorder[i][0]+100,400-leftBorder[i][1],leftBorder[i+1][0]+100,400-leftBorder[i+1][1]);
		}
		for(int i=0;i<rightBorder.length-1;i++){
			g.drawLine(rightBorder[i][0]+100,400-rightBorder[i][1],rightBorder[i+1][0]+100,400-rightBorder[i+1][1]);
		}
		
		g.drawOval((int)pointX+85,385-(int)pointY,30,30);
		
		g.drawLine((int)pointX+100,400-(int)pointY,(int) (pointX+100*Math.cos(Math.toRadians(radious))+100),(int)(400-100*Math.sin(Math.toRadians(radious))-pointY));
		g.drawLine((int)pointX+100,400-(int)pointY,(int) (pointX+100*Math.cos(Math.toRadians(radious-45))+100),(int)(400-100*Math.sin(Math.toRadians(radious-45))-pointY));
		g.drawLine((int)pointX+100,400-(int)pointY,(int) (pointX+100*Math.cos(Math.toRadians(radious+45))+100),(int)(400-100*Math.sin(Math.toRadians(radious+45))-pointY));
	}
	
	public void moveCar(double turn){
		pointX=pointX+10*(Math.cos(Math.toRadians(radious)+Math.toRadians(turn))+Math.sin(Math.toRadians(turn))*Math.sin(Math.toRadians(radious)));
		pointY=pointY+10*(Math.sin(Math.toRadians(radious)+Math.toRadians(turn))-Math.sin(Math.toRadians(turn))*Math.cos(Math.toRadians(radious)));
		radious=radious-Math.toDegrees(Math.asin(2*Math.sin(Math.toRadians(turn))/b));
		
	
		
	}
	
	public double getpointX(){
		return pointX;
	}
	
	public double getpointY(){
		return pointY;
	}
	
	
	
	public double countFrontLen(){
		return this.countLen(1);
	}
	
	public double countLeftLen(){
		return this.countLen(2);
	}
	
	public double countRightLen(){
		return this.countLen(3);
	}
	
	public double countLen(int sensor){
		double sensorRad=0;
		
		if(sensor==1){
			sensorRad=radious;
		}
		else if(sensor==2){
			sensorRad=radious+45.0;
		}
		else if(sensor==3){
			sensorRad=radious-45.0;
		}
		double distance=1000000;
		double tempDistance=1000000;
		
		for(int i=0;i<leftBorder.length-1;i++){
			double x1=pointX;
			double y1=pointY;
			double x2=pointX+1000000*Math.cos(Math.toRadians(sensorRad));
			double y2=pointY+1000000*Math.sin(Math.toRadians(sensorRad));
			double x3=leftBorder[i][0];
			double y3=leftBorder[i][1];
			double x4=leftBorder[i+1][0];
			double y4=leftBorder[i+1][1];
			
			double uA=(((x4-x3)*(y1-y3))-((y4-y3)*(x1-x3)))/(((y4-y3)*(x2-x1))-((x4-x3)*(y2-y1)));
		
			double interX=x1+uA*(x2-x1);
			double interY=y1+uA*(y2-y1);
			
			tempDistance=Math.sqrt((interX-x1)*(interX-x1)+(interY-y1)*(interY-y1));
			
			if(tempDistance<distance&&interX>=x3-2&&interX<=x4+2&&interY>=y3-2&&interY<=y4+2&&(interX-x1)/(x2-x1)>0&&(interY-y1)/(y2-y1)>0){
				distance=tempDistance;
			}
		}
		
		for(int i=0;i<rightBorder.length-1;i++){
			double x1=pointX;
			double y1=pointY;
			double x2=pointX+1000000*Math.cos(Math.toRadians(sensorRad));
			double y2=pointY+1000000*Math.sin(Math.toRadians(sensorRad));
			double x3=rightBorder[i][0];
			double y3=rightBorder[i][1];
			double x4=rightBorder[i+1][0];
			double y4=rightBorder[i+1][1];
			
			double uA=(((x4-x3)*(y1-y3))-((y4-y3)*(x1-x3)))/(((y4-y3)*(x2-x1))-((x4-x3)*(y2-y1)));
					
			double interX=x1+uA*(x2-x1);
			double interY=y1+uA*(y2-y1);
			
			tempDistance=Math.sqrt((interX-x1)*(interX-x1)+(interY-y1)*(interY-y1));
			
			if(tempDistance<distance&&interX>=x3-2&&interX<=x4+2&&interY>=y3-2&&interY<=y4+2&&(interX-x1)/(x2-x1)>0&&(interY-y1)/(y2-y1)>0){
				distance=tempDistance;
			}
		}
		return distance;
	}
	

	

}

