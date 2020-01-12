package com.mycompany.milionerze_it_version;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        boolean gameEnded = false;
        
        while(!gameEnded){
            Game game = new Game();
            while(game.gameStarted()){
            }
            System.out.println("Chcesz zagrac jeszcze raz?");
            Scanner scan = new Scanner(System.in);
            String answ;
            while(true){
                System.out.println("Odpowiedz 'Tak' lub 'Nie':");
                try{
                    answ = scan.next();
                    if(answ.equals("Nie")){
                        gameEnded = true;
                        break;
                    }
                    else if(answ.equals("Tak")) break;
                }catch(Exception e){
                    System.out.println("To nie jest poprawna odpowiedz");
                    scan.next();
                }
            }
        }
    }
}
