
public class NNModel {
	int setNumber=710;
	
	double[][] m=new double[6][6];
	double[] f=new double[6];
	
	double[] w=new double[6];
	
	double[][] input=new double[setNumber][6];
	double[] expectOutput=new double[setNumber];
	double output;
	
	double theta;
	
	public void randomParameter(){
		for(int i=1;i<6;i++){
			f[i]=Math.random()*10;
			w[i]=Math.random()*80-40;
		
			for(int j=0;j<6;j++){
				m[i][j]=Math.random()*30;
			}	
		}	
		
		theta=Math.random();
	}
	
	public void setData(TrainingData[] trainingSet){
		for(int i=1;i<setNumber;i++){
			input[i][1]=trainingSet[i-1].getX();
			input[i][2]=trainingSet[i-1].getY();
			input[i][3]=trainingSet[i-1].getFront();
			input[i][4]=trainingSet[i-1].getLeft();
			input[i][5]=trainingSet[i-1].getRight();
			expectOutput[i]=trainingSet[i-1].getRadious();
		}
		
		  
	}
	
	public double countTotalError(){
		double totalError=0;
		for(int i=1;i<setNumber;i++){
			totalError+=(Math.pow(expectOutput[i]-countOutput(input[i]),2))/2;
			//System.out.printf("預期輸出為:%f,實際輸出為%f,相差%f\n",expectOutput[i],countOutput(input[i]),Math.abs(expectOutput[i]-countOutput(input[i])));
		}
	
		return totalError;
	}
	
	public double countErrorRadious(){
		double totalError=0;
		for(int i=1;i<setNumber;i++){
			totalError+=Math.abs(expectOutput[i]-countOutput(input[i]));
			//System.out.printf("預期輸出為:%f,實際輸出為%f,相差%f\n",expectOutput[i],countOutput(input[i]),Math.abs(expectOutput[i]-countOutput(input[i])));
		}
	
		return totalError/setNumber;
	}
	
	
	public double countOutput(double[] inputData){
		double totalSum=theta;
		for(int i=1;i<=5;i++){
			
			double vectorSum=0;;
			for(int j=1;j<=5;j++){
				vectorSum+=(inputData[j]-m[j][i])*(inputData[j]-m[j][i]);
			}
			double theta=Math.exp(vectorSum*(-1)/(f[i]*f[i]*2));
			
			totalSum+=w[i]*theta;
		
		}
		
		return totalSum;
	}
	
	public void setParameter(int number,double value){
		if(number<25){
			if(value>30){
				value=30;
			}
			else if(value<0){
				value=0;
			}
			m[number/5+1][number%5+1]=value;
		}
		else if(number>=25&&number<30){
			if(value>10){
				value=10;
			}
			else if(value<0){
				value=0;
			}
			f[number-25+1]=value;
		}
		else if(number>=30&&number<35){
			if(value>40){
				value=40;
			}
			else if(value<-40){
				value=-40;
			}
			w[number-30+1]=value;
		}
		else if(number==35){
			if(value>1){
				value=1;
			}
			else if(value<0){
				value=0;
			}
			theta=value;
		}
	}
	
	public double getParameter(int number){
		if(number<25){
			return m[number/5+1][number%5+1];
		}
		else if(number>=25&&number<30){
			return f[number-25+1];
		}
		else if(number>=30&&number<35){
			return w[number-30+1];
		}
		else if(number==35){
			return theta;
		}
		return 0; 
	}
	
}
