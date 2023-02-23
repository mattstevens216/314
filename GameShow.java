// Skeleton Code provided by Anandi Dutta
// Assignment 5, CSCE-314
// Section: 502
// Student Name: Matthew Stevens
// UIN: 924000693
// Resources used: https://stackoverflow.com/questions/363681/how-do-i-generate-random-integers-within-a-specific-range-in-java (used for rand)
// https://stackoverflow.com/questions/7209110/java-util-nosuchelementexception-no-line-found (for help using Scanner.in)
// https://stackoverflow.com/questions/4044726/how-to-set-a-timer-in-java (FOR TEMPLATE OF TIMER (RUNNABLE R SKELETON)

import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

//This function should work.
public class GameShow{
	//overall user score and lock instantiation.
	private static int userScore = 0;
	private static Lock lock = new ReentrantLock();
	private static boolean continueGame = true;
	
	public static void main(String[] args){
		System.out.println("Welcome to GUESS THE TOPIC! My name is GameMaster Flint, I will be your gamemaster today!");
		System.out.println("The game is simple, guess the topic of the question! You have 10 seconds to answer.");
		//spawn 1 thread and run separately from main
		ExecutorService executor = Executors.newFixedThreadPool(1);
		Scanner in = new Scanner(System.in);
		try{
		// Create a runnable thread with the tasks of gameshow.
			while(continueGame){
			//find this source
			try{
			Runnable r = new Runnable(){
				@Override
				public void run(){
					try{
						executor.execute(new gameShowHost());
					}
					catch (RuntimeException e){
						e.printStackTrace();
					}
				}
			};
			
			//submit to executor threadpool
			Future<?> f = executor.submit(r);
			//after 10 seconds if no response time out.
			f.get(10, TimeUnit.SECONDS);
			}
			catch (final TimeoutException e){
				System.out.println("You have not given an answer. Current Score: " + userScore);
				System.out.println("Please press enter in order to continue playing the game once again and to see an answer to a question.");
				continueGame = true;
			}
			}
		}
		catch (final InterruptedException e){
			e.printStackTrace();
		}
		catch (final ExecutionException e){
			e.printStackTrace();
		}
	}
	
	public static class gameShowHost implements Runnable{
		@Override
		public void run(){
			try{
				//lock
				lock.lock();
					//begin critical section
					//initializers
			 		String[] questions = {"What comes from cows?", "What says moo?", "is java better than haskell?"};
			 		String[] answers = {"Milk", "Cow", "Yes"};
					int questionGen;
					String answerUser = "";
					//generate random number 0-2
					Random rand = new Random();
					questionGen = rand.nextInt(3);
					//commented out test function for questionGen.
					//System.out.println("questionGen: " + questionGen);
					
					//output question to user.
					System.out.println("The Question is: " + questions[questionGen] + " You have 10 seconds to answer.");
					System.out.println("Please enter an answer: ");
					try{
						Scanner in = new Scanner(System.in);

						//make a scanner for user input (wastes resources because you cant close when using System.in
						answerUser = in.nextLine();

					}
					catch (NoSuchElementException e){
						e.printStackTrace();
					}
					//test functions to see if answers and user answer were getting accessed
			        //System.out.println(answers[0]);
			        //System.out.println(answers[1]);
			        //System.out.println(answers[2]);
			        //System.out.println(answerUser);
					
					//boolean checks to see if answer matches user answer for a particular question, if so increment score
					if(Objects.equals(answers[0], answerUser) && Objects.equals(questions[questionGen], questions[0])){
						System.out.println("You have answered question 1 correctly!");
						userScore += 1;
						System.out.println("Your score is: " + userScore);
					}
					else if(Objects.equals(answers[1], answerUser) && Objects.equals(questions[questionGen], questions[1])){
						System.out.println("You have answered question 2 correctly!");
						userScore += 1;
						System.out.println("Your score is: " + userScore);
					}
					else if(Objects.equals(answers[2], answerUser) && Objects.equals(questions[questionGen], questions[2])){
					    System.out.println("You have answered question 3 correctly!");
						userScore += 1;
						System.out.println("Your score is: " + userScore);
					}
					else{
						System.out.println("You have entered an incorrect answer.");
						if(Objects.equals(questions[questionGen], questions[0])){
							System.out.println("The answer is: " + answers[0]);
						}
						if(Objects.equals(questions[questionGen], questions[1])){
							System.out.println("The answer is: " + answers[1]);
						}
						if(Objects.equals(questions[questionGen], questions[2])){
							System.out.println("The answer is: " + answers[2]);
						}
					}
					//unlock the lock to release control of critical section
					lock.unlock();
			}
			catch (NoSuchElementException e){
				e.printStackTrace();
			}
			catch (IllegalStateException e){
				e.printStackTrace();
			}
		}
	}
	
}
