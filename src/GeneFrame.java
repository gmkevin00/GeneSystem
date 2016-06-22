import javax.swing.JFrame;




import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class GeneFrame extends JFrame {
	JButton startButton=new JButton("Move Car");
	
	TrainingData[] trainingSet=new TrainingData[3000];
	
	NNModel finalModel;
	
	public GeneFrame(){
		super("gene");
		GetTrainData getTrainData=new GetTrainData();
		getTrainData.getDataFromFile();
		trainingSet=getTrainData.getData();
		
		GeneEngine geneEngine=new GeneEngine();
		geneEngine.setTrainingSet(trainingSet);
		geneEngine.generateRandomNNSet();
		geneEngine.startGeneAlgo();
		finalModel=geneEngine.getFinalModel();
		
		
		
		GenePanel genePanel=new GenePanel();
		add(genePanel,BorderLayout.CENTER);
		add(startButton,BorderLayout.SOUTH);
		
		startButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
		
				//genePanel.moveCar(20);
				
				//repaint();
				Thread moveThread=new Thread(){
					public void run(){
						for(int i=0;i<1000;i++){
							double[] inputData=new double[6];
							inputData[1]=genePanel.getpointX()/10;
							inputData[2]=genePanel.getpointY()/10;
							inputData[3]=genePanel.countFrontLen()/10;
							inputData[4]=genePanel.countRightLen()/10;
							inputData[5]=genePanel.countLeftLen()/10;
							System.out.printf("%f     %f     %f     %f     %f     \n",inputData[1],inputData[2],inputData[3],inputData[4],inputData[5]);
							
							double radious=finalModel.countOutput(inputData);
							System.out.printf("Âà%f«×\n",radious);
							
							genePanel.moveCar(radious);
							repaint();
							
							try {
								Thread.sleep(300);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				};
				moveThread.start();	
			}
		});
	}
	
}

