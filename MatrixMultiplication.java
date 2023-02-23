//Assignment 5, CSCE-314
//Section: 502
//Student Name: Matthew Stevens
//UIN: 924000693
//Resources used: (timer help) https://stackoverflow.com/questions/4044726/how-to-set-a-timer-in-java

import java.util.concurrent.*;
import java.util.concurrent.locks.*;


public class MatrixMultiplication{
	private static double[][] matrix = new double[2000][2000];
	private static double[][] matrix2 = new double[2000][2000];
	
	private static double[][] resultMatrix = new double[2000][2000];
	private static double[][] seqMatrix = new double[2000][2000];
	//create new locks
	private static Lock lock = new ReentrantLock();
	private static Lock lock2 = new ReentrantLock();
	private static Lock lock3 = new ReentrantLock();
	private static Lock resLock = new ReentrantLock();

		
	public static void main(String[] args){
		//timer values
		long start;
		long end;
		long total;
		long seqStart;
		long seqEnd;
		double seqMult;
		double seqSum;
		double seqTotal;
		// Create a thread pool with 3 threads
    	ExecutorService executor = Executors.newFixedThreadPool(1);
    	
    	//populate both matrices
		System.out.println("Working on populating matrices");
    	executor.execute(new matrixPopulation());
    	executor.shutdown();
    	try {
    	  executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    	} 
    	catch (InterruptedException e) {
    		e.printStackTrace();
    	}
    	
		/*for(int i = 0; i < 2000; i++){
    		for(int j = 0; j < 2000; j++){
    			matrix[i][j] = (double)(Math.random()*10);
    			matrix2[i][j] = (double)(Math.random()*10);
    		}
    		if(i == 1999){
    			System.out.println(matrix[i]);
    			resLock.unlock();
    		}
    	} *///slows down speed but ensures that matrices are populated first.
		System.out.println("Done populating matrices");
		ExecutorService executor1 = Executors.newFixedThreadPool(3);
    	//send threads to function parallel matrix
    	resLock.lock();
    	start = System.currentTimeMillis();
    	executor1.execute(new parallelMatrixFirstThird());
    	executor1.execute(new parallelMatrixSndThird());
    	executor1.execute(new parallelMatrixLastThird());
    	executor1.shutdown();
    	try {
      	  executor1.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
      	} 
      	catch (InterruptedException e) {
      		e.printStackTrace();
      	}
	//	lock.lock();
//		lock2.lock();
 //   	lock3.lock(); //waits until the 3 locks are unlocked before proceeding (end of thread execution)
    	end = System.currentTimeMillis();

    	total = end - start;
    	System.out.println("Parallel time for matrix multiplication is: " + total + " milliseconds.");
    	
    	seqSum = 0;
  		seqStart = System.currentTimeMillis();
    	for(int j = 0; j < 2000; j++){
    		for(int r = 0; r < 2000; r++){
    			for(int c = 0; c < 2000; c++){
    				seqMult = matrix[c][r] * matrix2[r][c];
    				seqSum += seqMult;
    			}
    			//change rows more than columns for population
    			seqMatrix[j][r] = seqSum;
    			seqSum = 0;
    		}
    	}
    	seqEnd = System.currentTimeMillis();
    	seqTotal = seqEnd - seqStart;
    	System.out.println("Sequential time for matrix multiplication is: " + seqTotal + " milliseconds.");
    //	lock3.unlock();
   // 	lock2.unlock();
   // 	lock.unlock();
    	
    	resLock.unlock();
		System.exit(0);
	}
	
	public static class matrixPopulation implements Runnable{
		@Override
		public void run() {
			try{
					lock.lock();
					lock2.lock();
					lock3.lock();
			    	System.out.println("still working...");
			    	for(int i = 0; i < 2000; i++){
			    		for(int j = 0; j < 2000; j++){
			    			matrix[i][j] = (double)(Math.random()*10);
			    			matrix2[i][j] = (double)(Math.random()*10);
			    		}
			    	}
			    	System.out.println("done making matrix");
			    	System.out.println(matrix2[1999][1999]);
					lock3.unlock();
					lock2.unlock();
					lock.unlock();
					System.out.println("unlocked");
			}
			catch (RuntimeException e){
				e.printStackTrace();
			}
		}
	}
	
	public static class parallelMatrixFirstThird implements Runnable{
	    @Override
		public void run() {
			try{ 
					lock.lock(); //acquire the lock
					System.out.println("First third is currently running");
					try{
						double sum = 0;
						double mult = 0;
						for(int i = 0; i < 666; i++){
							for(int j = 0; j < 666; j++){
								for(int c = 0; c < 2000; c++){
									mult = matrix[c][j] * matrix2[j][c]; //matrix row * matrix2 column all summed together
									sum += mult;
								}						
								//set result (column before row)
								resultMatrix[j][i] = sum;
								//reset sum for next iteration of row/column
								sum = 0;
							}
						//nothing need to be done in this loop, just changes row
						}
					}
					catch (RuntimeException e){
						e.printStackTrace();
					}
					lock.unlock(); //release the lock
			} 
			catch (RuntimeException ex){
				ex.printStackTrace();
			}
		}
	}
					
	public static class parallelMatrixSndThird implements Runnable{
	   @Override
		public void run() {
			try{
					lock2.lock(); //acquire the lock
					System.out.println("2nd third is currently running");
					try{
						double sum2 = 0;
						double mult1 = 0;
						for(int i = 666; i < 1333; i++){
							for(int j = 666; j < 1333; j++){
									for(int c = 0; c < 2000; c++){
										mult1 = matrix[c][j] * matrix2[j][c]; //matrix row * matrix2 column all summed together
										sum2 += mult1;
									}
		
								//set result (column before row)
								resultMatrix[j][i] = sum2;
								//reset sum for next iteration of row/column
								sum2 = 0;
							}
						//nothing need to be done in this loop, just changes row
						}
					}
					catch (RuntimeException e){
						e.printStackTrace();
					}
					lock2.unlock(); //release the lock
			} 
			catch (RuntimeException ex){
				ex.printStackTrace();
			}
		}
	}
					
	public static class parallelMatrixLastThird implements Runnable{
	    @Override
		public void run() {
			try{ 
					lock3.lock(); //acquire the lock
					System.out.println("Last third is currently running");
					try{
						double sum3 = 0;
						double mult2 = 0;
						for(int i = 1333; i < 2000; i++){
							for(int j = 1333; j < 2000; j++){
								for(int c = 0; c < 2000; c++){
										mult2 = matrix[c][j] * matrix2[j][c]; //matrix row * matrix2 column all summed together
										sum3 += mult2;
								}
								//set result (column before row)
								resultMatrix[j][i] = sum3;
								//reset sum for next iteration of row/column
								sum3 = 0;
							}
						}
					}
					catch (RuntimeException e){
						e.printStackTrace();
					}
					lock3.unlock(); //release the lock
			} 
			catch (RuntimeException ex){
				ex.printStackTrace();
			}
		}
	}
	
/*	public static class resultMatrixAddition implements Runnable{
		@Override
		public void run(){
			try{
				resLock.lock();
				lock.lock();
				lock2.lock();
				lock3.lock();
				for(int i = 0; i < 666; i++){
					for(int j = 0; j < 666; j++){
						resultMatrix[i][j] = result[i][j];
					}
				}
				for(int i = 0; i < 667; i++){
					for(int j = 0; j < 667; j++){
						resultMatrix[i+666][j+666] = result2[i][j];
					}
				}
				for(int i = 0; i < 667; i++){
					for(int j = 0; j < 667; j++){
						resultMatrix[i+1333][j+1333] = result3[i][j];
					}
				}
				lock3.unlock();
				lock2.unlock();
				lock.unlock();
				resLock.unlock();
			}
			catch (InterruptedException e){
				e.printStackTrack();
			}	
		}
	}*/
}
