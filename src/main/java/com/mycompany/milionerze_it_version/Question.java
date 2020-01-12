package com.mycompany.milionerze_it_version;

import java.util.Random;


public class Question {
    private final String question;
    private final int correct_answer;
    private String[] answers;
    
    Question(String question, int correct_answer, String[] answers) {
        this.question = question;
        this.correct_answer = correct_answer;
        this.answers = answers;
    }
    
    // Metoda zwracająca tresc pytania
    public String getText() {
        return this.question;
    }
    
    //Metoda zwracająca poprawna odpowiedz
    public String getCorrectAnswer(){
        return this.answers[this.correct_answer]; //pozniej sprawdzamy charAt(correctAnswer) == odpowiedz
    }
    
    //Metoda zwracajaca odpowiedzi
    public String getAnswers(int n) {
        return this.answers[n];
    }
    //Metoda usuwajaca podana odpowiedz z tablicy
    public void removeAnswer(int n){
        this.answers[n] = " ";
    }
    //Metoda zwracajaca liczbe odpowiedzi
    public int numberOfAnswers(){
        return this.answers.length;
    }
    //Metoda zwracajaca losowa odpowiedz bez poprawnej
    public char getRandomAnswer(){
        Random r = new Random();
        int randomNumber;
        while(true){
            randomNumber = r.nextInt(3);
            if(randomNumber != this.correct_answer) break;
        }
        return this.answers[randomNumber].charAt(0);
    }
}
