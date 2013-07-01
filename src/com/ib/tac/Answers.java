package com.ib.tac;

public class Answers {
	private int answerNumber;
	private String answer;
	private int LinkedNumber;

	public Answers(int aNumber,String a,int lNumber){
		setAnswerNumber(aNumber);
		setAnswer(a);
		setLinkedNumber(lNumber);
	}
	
	public void setAnswerNumber(int aNumber){
		answerNumber = aNumber;
	}
	
	public int getAnswerNumber(){
		return answerNumber;
	}
	
	public void setAnswer(String a){
		answer = a;
	}
	
	public String getAnswer(){
		return answer;
	}
	
	public void setLinkedNumber(int lNumber){
		LinkedNumber = lNumber;
	}
	
	public int getLinkedNumber(){
		return LinkedNumber;
	}
}
