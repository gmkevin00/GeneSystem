import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;


public class GetTrainData {
	TrainingData[] trainingSet=new TrainingData[3000];
	
	public void getDataFromFile(){
		int dataCount=0;
		
		
		
		try{
			for(int fileCount=0;fileCount<13;fileCount++){
				System.out.printf("file:%d\n",fileCount);
				// Open the file that is the first 
				// command line parameter
				FileInputStream fstream = new FileInputStream(String.format("train_data/train_%d_D2.txt",fileCount));
				// Get the object of DataInputStream
				DataInputStream in = new DataInputStream(fstream);
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				String strLine;
				//Read File Line By Line
				
				while ((strLine = br.readLine()) != null) {
				// Print the content on the console
					System.out.println (strLine);
					String[] dataSourceLine=strLine.split(" ");
					
					trainingSet[dataCount]=new TrainingData();
					trainingSet[dataCount].setX(Double.parseDouble(dataSourceLine[0]));
					trainingSet[dataCount].setY(Double.parseDouble(dataSourceLine[1]));
					trainingSet[dataCount].setFront(Double.parseDouble(dataSourceLine[2]));
					trainingSet[dataCount].setLeft(Double.parseDouble(dataSourceLine[3]));
					trainingSet[dataCount].setRight(Double.parseDouble(dataSourceLine[4]));
					trainingSet[dataCount].setRadious(Double.parseDouble(dataSourceLine[5]));
					
					dataCount++;
				}
				
				System.out.println (dataCount);
				
				//Close the input stream
				in.close();
			}		
		}catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.toString());
		}
	}
	
	public TrainingData[] getData(){
		return trainingSet;
	}
}
