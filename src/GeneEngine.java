import java.util.Random;


public class GeneEngine {
	int modelCount=300;
	
	TrainingData[] trainingSet=new TrainingData[3000];
	NNModel[] nnmodel=new NNModel[modelCount];
	
	
	int finalModel=-1;
	
	public void setTrainingSet(TrainingData[] trainingSet){
		this.trainingSet=trainingSet;
	}
	
	
	public void generateRandomNNSet(){
		for(int i=0;i<300;i++){
			nnmodel[i]=new NNModel();
			nnmodel[i].randomParameter();
			nnmodel[i].setData(trainingSet);
		}
	}
	
	public void startGeneAlgo(){
		//temp_nnmodel=new NNModel[300];
		double minError=10000000;
		
		for(int i=1;i<=150;i++){
			this.geneCopy();
			this.geneCross();
			this.geneChange();
			System.out.printf("第%d次訓練\n",i);
			
			for(int j=0;j<300;j++){
				if(nnmodel[j].countTotalError()<minError){
					minError=nnmodel[j].countTotalError();
					finalModel=j;
					System.out.printf("發現最小值%f\n",nnmodel[j].countErrorRadious());
				}
			}
			
		}
		
		
		
		System.out.printf("final total error:%f\n",minError);
		
		
		
		
	}
	
	public NNModel getFinalModel(){
		return nnmodel[finalModel];
	}
	
	public void geneCopy(){
		int best_copy = nnmodel.length / 10;
		
		int flag=0;
		double	tempMin=1000000;
		
		for(int i=0;i<nnmodel.length-1;i++){
			if(nnmodel[i].countTotalError()<tempMin){
				flag=i;
				tempMin=nnmodel[i].countTotalError();
				
			}
		}
		
		for(int i=0;i<best_copy;i++){
			for(int j=0;j<36;j++){
				nnmodel[i].setParameter(j,nnmodel[flag].getParameter(j));
			}
		}
		
		NNModel[] copy_nnmodel=new NNModel[modelCount];
		double[] prob=new double[modelCount];
		int sumError=0;
		
		for(int i=0;i<nnmodel.length;i++){
			sumError+=1.0 / nnmodel[i].countTotalError();
		}
		
		for(int i=0;i<nnmodel.length;i++){
			prob[i]=(1.0 / nnmodel[i].countTotalError()) / sumError;
		}
		
		
		for(int i = 0 , j = 0 ; j < nnmodel.length ; i = (i + 1) % nnmodel.length) {
			double choose_prob = Math.random() * 10;
			if (prob[i] > choose_prob) {
				copy_nnmodel[j]=new NNModel();
				for(int k=0;k<36;k++){
					copy_nnmodel[j].setParameter(k,nnmodel[i].getParameter(k));
				}
				j++;
			}
		}
		
		for (int i = 0; i < nnmodel.length; i++) {
			for(int j=0;j<36;j++){
				nnmodel[i].setParameter(j,copy_nnmodel[i].getParameter(j));
			}
		}
		
	}
	
	
	public void geneCross(){
		Random rand_int = new Random();
		for(int i=0;i<nnmodel.length;i++){
			if(Math.random()>0.5){
				int x = Math.abs(rand_int.nextInt()) % nnmodel.length;
				//System.out.println(x);
				
				for(int j=0;j<36;j++){
					double temp=Math.random()-0.5;	
					
					NNModel tempM=new NNModel();
					
					tempM=nnmodel[i];
					nnmodel[i].setParameter(j,nnmodel[i].getParameter(j)+temp*(nnmodel[i].getParameter(j)-nnmodel[x].getParameter(j)));
					nnmodel[x]=nnmodel[i];
					nnmodel[x].setParameter(j,nnmodel[x].getParameter(j)+temp*(nnmodel[x].getParameter(j)-nnmodel[i].getParameter(j)));
				}	
			}
			
		}
	}
	
	public void geneChange(){
		for(int i=0;i<nnmodel.length;i++){
			
			for(int j=0;j<36;j++){
				if(Math.random()<0.03){
					if(Math.random()<Math.random()-0.5){
						double temp=Math.random()-0.5;
										
						if(j<25){
							temp*=30;
						}
						else if(j>=25&&j<30){
							temp*=10;
						}
						else if(j>=30&&j<35){
							temp*=40;
						}
						
						
						nnmodel[i].setParameter(j,nnmodel[i].getParameter(j)+temp*0.1);			
						
					}
					
				}
			}
			
		}
	}
	
	
}
