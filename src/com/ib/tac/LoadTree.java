package com.ib.tac;

import java.io.*;
import java.lang.*;
import java.util.*;

public class LoadTree {
	private Scanner input;
	int questionSize = 20;
	int q = 0;
	int answerSize = 20;
	int a = 0;
	int questionNumber = 0;
	int i = 0;
	boolean hasResult = false;
	
	Description description = new Description("");
	Questions[] questionList = new Questions[questionSize];
	Answers[] answerList = new Answers[answerSize];
	ArrayList<Questions> theQuestionList = new ArrayList<Questions>();
	ArrayList<Answers> theAnswerList = new ArrayList<Answers>();
	Answers result = new Answers(0,"result",0);
	
	public void processTree(){
		for(int i = 0; i  < questionSize; i++){
			questionList[i]= new Questions(0,"",null);
		}
		for(int i = 0; i  < answerSize; i++){
			answerList[i]= new Answers(0,"",0);
		}
		try{
			input = new Scanner(new File("Noodle3.txt"));
			
			while(input.hasNext()){
				int n = 1;
				String t;	
				t = input.nextLine();				
				if ("[D]".equals(t)){	
					description.setDescription(input.nextLine());
					//System.out.printf("%s%s\n", "Description is: ",description.getDescription());
				}
				else if ("[Q]".equals(t)){
					questionNumber = input.nextInt();
					questionList[q].setQuestionNumber(questionNumber);
					input.nextLine();
					questionList[q].setQuestion(input.nextLine());
					theQuestionList.add(questionList[q]);
					//System.out.printf("%s%d\n%s%s\n","Question number is: ",theQuestionList.get(q).getQuestionNumber(),"Question is ",theQuestionList.get(q).getQuestion());
					q++;
					i = 0;
				}
				else if ("[A]".equals(t)){
					while (("[Q]".equals(t) || "".equals(t) || "[R]".equals(t)) == false)
					{
					answerList[a].setAnswerNumber(n);
					if ("[A]".equals(t)){
						answerList[a].setAnswer(input.nextLine()); 
					}
					if ("[A]".equals(t) == false){
						answerList[a].setAnswer(t);
					}
					answerList[a].setLinkedNumber(input.nextInt());
					theAnswerList.add(answerList[a]);
					theQuestionList.get(q-1).setqAnswer(i, answerList[a]);
					//System.out.printf("%s%d\n%s%s\n%s%s\n","Answer number is: ",theAnswerList.get(a).getAnswerNumber(),"Answer is: ",theAnswerList.get(a).getAnswer(), "The related questions is: ",theAnswerList.get(a).getLinkedNumber());
/*					for(int m=0; m<=i ;m++){
						System.out.printf("%s%d%s%d%s%s\n\n", "Question ",q," has answer ",theQuestionList.get(q-1).getqAnswer(i).getAnswerNumber()," : ",theQuestionList.get(q-1).getqAnswer(i).getAnswer());
					}*/
					a++;
					i++;
					input.nextLine();
					t = input.nextLine();
					n++;
					}
					n = 1;
				}
				else if ("[R]".equals(t)){
					
					questionNumber = input.nextInt();
					questionList[q].setQuestionNumber(questionNumber);
					input.nextLine();
					questionList[q].setQuestion(input.nextLine());

					questionList[q].theqAnswerList.add(0,result);
					theQuestionList.add(questionList[q]);
					//System.out.printf("%s%d\n%s%s\n","Result number is: ",theQuestionList.get(q).getQuestionNumber(),"Result is ",questionList[q].getQuestion());
					q++;
				}
				else{
					//System.out.printf("%s%s%s\n", "===",t,"===");	
				}
			}
		}
		catch(FileNotFoundException fileNotFoundException){
			System.err.println("Error opening file. (FileNotFoundException)");
			System.exit(1);
		}
		catch(NoSuchElementException elementException){
			System.err.println("File improperly formed.(NoSuchElementException)");
			input.close();
			System.exit(1);
		}
		catch(IllegalStateException stateException ){
			 System.err.println( "Error reading from file.(IllegalStateException)" );
			 System.exit(1);
		 } // end catch

	
		finally{
		if(input!= null)
			input.close();
		}
	}
	
	public void displayTree(){
		q = 0;
		a = 0;
		input = new Scanner(System.in);
		int[] pathQ = new int[questionSize];
		int[] pathA = new int[answerSize];
		System.out.printf("%s%s\n", "Description: ",description.getDescription());
		printQuestion(0);
		pathQ[q] = 1;
		q++;
		
		while(hasResult == false){
		System.out.printf("%s", "Enter you Choice: ");
		pathA[a] = input.nextInt();
		pathQ[q] = theQuestionList.get(pathQ[q-1]-1).getqAnswer(pathA[a]-1).getLinkedNumber();
		
		if(theQuestionList.get(pathQ[q]-1).theqAnswerList.get(0).getAnswer().equals("result")){
			System.out.printf("%s%d\n%s%s\n","Result number is: ",theQuestionList.get(pathQ[q]-1).getQuestionNumber(),"Result is: ",questionList[pathQ[q]-1].getQuestion());
			System.out.printf("%s\n", "You have reached the conclusion, the roadmap is: ");
			for(int m=0; m<q; m++){
				System.out.printf("%s%d\t","Q: ",pathQ[m]);
				System.out.printf("%s%d\t","A: ",pathA[m]);
			}
			System.out.printf("%s%d\t","Result: ",pathQ[q]);
			hasResult = true;
			input.close();
		}
		else{
		printQuestion(theQuestionList.get(pathQ[q-1]-1).getqAnswer(pathA[a]-1).getLinkedNumber()-1);
		}
		q++;
		a++;
		}
	}
	
	public void printQuestion(int i){
		System.out.printf("%s%d\n%s%s\n","Question number is: ",theQuestionList.get(i).getQuestionNumber(),"Question is: ",questionList[i].getQuestion());
		if(theQuestionList.get(i).theqAnswerList.get(0).getAnswer().equals("result")){
			return;
		}
		else {
		for(int m = 0; m < theQuestionList.get(i).theqAnswerList.size()-1;m++){
			System.out.printf("%d\t%s\n",theQuestionList.get(i).getqAnswer(m).getAnswerNumber(),theQuestionList.get(i).getqAnswer(m).getAnswer());
		}
		}
	}
}
