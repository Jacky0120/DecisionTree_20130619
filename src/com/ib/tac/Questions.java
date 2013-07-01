package com.ib.tac;

import java.util.ArrayList;

public class Questions {
	private int questionNumber;
	private String question;
	private int index;
//	private Answers qAnswer;
	ArrayList<Answers> theqAnswerList= new ArrayList<Answers>();
	
	
	//3 argument Question constructor
		public Questions(int qNumber, String q){
			setQuestionNumber(qNumber);
			setQuestion(q);
		}
	
	//3 argument Question constructor
	public Questions(int qNumber, String q, Answers a){
		setQuestionNumber(qNumber);
		setQuestion(q);
		setqAnswer(index, a);
	}
	
	public void setQuestionNumber(int qNumber){
		questionNumber = qNumber;
	}
	
	public int getQuestionNumber(){
		return questionNumber;
	}
	
	public void setQuestion(String q){
		question = q;
	}
	
	public String getQuestion(){
		return question;
	}
	//You don't need an index for this...
	public void setqAnswer(Answers a){
		theqAnswerList.add(a);
	}
	
	public void setqAnswer(int index, Answers a){
		theqAnswerList.add(index, a);
	}
	
	public Answers getqAnswer(int index){
		return theqAnswerList.get(index);
	}
}