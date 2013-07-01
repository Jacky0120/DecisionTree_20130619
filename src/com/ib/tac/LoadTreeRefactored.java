package com.ib.tac;

import java.awt.Component;
import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class LoadTreeRefactored extends JFrame {
	boolean hasResult = false;
	static String version = "2.0";
	JLabel questionLabel;
	
	Description description = new Description("");
	//List<Questions> questionList = new ArrayList<Questions>();
	//List<Answers> answerList = new ArrayList<Answers>();
	List<Questions> theQuestionList = new ArrayList<Questions>();
	List<Answers> theAnswerList = new ArrayList<Answers>();
	Answers result = new Answers(0,"result",0);
	
	public LoadTreeRefactored(){
		 super("Diagnosis Tool " + version);
		 this.processTree();
		 //this.displayTree();
		 this.displayUI();
	}
	
	public void displayUI(){
		int q = 0;
		int a = 0;
		int[] pathQ = new int[theQuestionList.size()];
		int[] pathA = new int[theAnswerList.size()]; 
		setLayout(new FlowLayout());
		printDescription();
		printQA(q);
		pathQ[q] = 1;
		q++;
	}
	
	public void printDescription(){
		JLabel descriptionLabel;
		descriptionLabel = new JLabel(description.getDescription());
		descriptionLabel.setToolTipText("Describe what this tree is designed to do.");
		add(descriptionLabel);
	}
	
	public void printQA(int i){
		questionLabel = new JLabel(theQuestionList.get(i).getQuestion()); 
		add(questionLabel);
		List<JButton> theButtonList = new ArrayList<JButton>();
		if(theQuestionList.get(i).theqAnswerList.get(0).getAnswer().equals("result")){
			return;
		}
		else {
			for(int m = 0; m < theQuestionList.get(i).theqAnswerList.size();m++){
				theButtonList.add(new JButton(theQuestionList.get(i).getqAnswer(m).getAnswer()));
				add(theButtonList.get(m));
				theButtonList.get(m).addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						JOptionPane.showMessageDialog(LoadTreeRefactored.this, e.getActionCommand());
					}
				});
			}
		}
	}
	
	
	public void processTree(){
		//why you need this as a member variable?
		Scanner input = null;
		try{
			input = new Scanner(new BufferedReader(new InputStreamReader(new FileInputStream("Noodle3.txt"), "UTF-8")));
			while(input.hasNext()){
				
				String t;	
				t = input.nextLine();				
				if ("[D]".equals(t)){	
					description.setDescription(input.nextLine());
				}
				else if ("[Q]".equals(t)){
					int questionNumber = input.nextInt();
					input.nextLine();//this is bad...
					String question = input.nextLine();
					theQuestionList.add(new Questions(questionNumber, question));

				}
				else if ("[A]".equals(t)){
					int ctr = 1;
					while (("[Q]".equals(t) || "".equals(t) || "[R]".equals(t)) == false)
					{
						
						String answerStr;
						if ("[A]".equals(t)){
							answerStr = input.nextLine(); 
						}
						else {
							answerStr = t;
						}
						int linkedNumber = input.nextInt();
						Answers answer = new Answers(ctr++, answerStr, linkedNumber);
						theAnswerList.add(answer);
						theQuestionList.get(theQuestionList.size()-1).setqAnswer(answer);

						input.nextLine();
						t = input.nextLine();

					}

				}
				else if ("[R]".equals(t)){
					
					int questionNumber = input.nextInt();
					input.nextLine();
					String questionText = input.nextLine();
					theQuestionList.add(new Questions(questionNumber, questionText));
					theQuestionList.get(theQuestionList.size()-1).theqAnswerList.add(0,result);

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
		catch (UnsupportedEncodingException e) {
			System.err.println( "Error reading from file.(Unsupported encoding exception)" );
			 System.exit(1);
		}

	
		finally{
		if(input!= null)
			input.close();
		}
	}
	
	public void displayTree(){
		int q = 0;
		int a = 0;
		Scanner input = new Scanner(System.in);
		int[] pathQ = new int[theQuestionList.size()];
		int[] pathA = new int[theAnswerList.size()];
		System.out.printf("%s%s\n", "Description: ",description.getDescription());
		printQuestion(0);
		pathQ[q] = 1;
		q++;
		
		while(hasResult == false){
		System.out.printf("%s", "Enter you Choice: ");
		pathA[a] = input.nextInt();
		pathQ[q] = theQuestionList.get(pathQ[q-1]-1).getqAnswer(pathA[a]-1).getLinkedNumber();
		
		if(theQuestionList.get(pathQ[q]-1).theqAnswerList.get(0).getAnswer().equals("result")){
			System.out.printf("%s%d\n%s%s\n","Result number is: ",theQuestionList.get(pathQ[q]-1).getQuestionNumber(),"Result is: ",theQuestionList.get(pathQ[q]-1).getQuestion());
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
		System.out.printf("%s%d\n%s%s\n","Question number is: ",theQuestionList.get(i).getQuestionNumber(),"Question is: ", theQuestionList.get(i).getQuestion());
		if(theQuestionList.get(i).theqAnswerList.get(0).getAnswer().equals("result")){
			return;
		}
		else {
			for(int m = 0; m < theQuestionList.get(i).theqAnswerList.size();m++){
				System.out.printf("%d\t%s\n",theQuestionList.get(i).getqAnswer(m).getAnswerNumber(),theQuestionList.get(i).getqAnswer(m).getAnswer());
			}
		}
	}
}
