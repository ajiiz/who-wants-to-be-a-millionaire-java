package com.mycompany.milionerze_it_version;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

//Poprawne odpowiedzi w pliku txt. sa zapisane jako nr indeksu w liscie zaw. odp.
public class Game {
    private final static List<Question> questions_stage_1 = new ArrayList();
    private final static List<Question> questions_stage_2 = new ArrayList();
    private final static List<Question> questions_stage_3 = new ArrayList();
    private static final int []moneyArr = {0,500,1000,2000,5000,10000,20000,40000,75000,125000,250000,500000,1000000};
    private static int stage;
    private static long result;
    private static int round;
    private String name;
    private static boolean usedLifeRing;
    private static boolean usedFiftyFifty;
    private static boolean usedCallFriend;
    private static boolean usedAskAudience;
    
    Game(){
        Game.result = 0;
        Game.stage = 1;
        this.name = "";
        Game.usedLifeRing = false;
        Game.usedFiftyFifty = false;
        Game.usedCallFriend = false;
        Game.usedAskAudience = false;
        Game.round = 0;
        //Odczytanie pytan z plikow i przypisanie ich do list
        loadQuestions("questions_stage_1.txt",Game.questions_stage_1);
        loadQuestions("questions_stage_1.txt",Game.questions_stage_2);
        loadQuestions("questions_stage_1.txt",Game.questions_stage_3);
        
        //Pomieszanie pytan, aby sie nie powtarzaly
        Collections.shuffle(Game.questions_stage_1);
        Collections.shuffle(Game.questions_stage_2);
        Collections.shuffle(Game.questions_stage_3);
        
        //Pobranie podstawowych informacji o graczu
        Scanner scan = new Scanner(System.in);
        System.out.println("Witamy w grze Milionerzy!");
        System.out.println("Podaj swoja nazwe");
        this.name = scan.next();
        System.out.println("Chcesz zobaczyc zasady zanim rozpoczniemy gre? Odpowiedz Tak/Nie");
        while(true){
            String answerRules = scan.next();
            if("Tak".equals(answerRules)) {
                showRules();
                break;
            }
            else if("Nie".equals(answerRules)) break;
        }
        System.out.println("Rozpoczynamy rozgrywke, powodzenia "+this.name+"!");
        
    }
    //Metoda glowna odpowiadajaca za dzialanie gry
    static public boolean gameStarted(){
        Game.result = Game.moneyArr[round];
        Scanner scan = new Scanner(System.in);
        int option;
        List<Question> list = Game.questions_stage_1;
        
        if(Game.result >= 5000 && Game.result <125000) Game.stage=2;
        if(Game.result>=125000) Game.stage=3;
        if(Game.stage == 2)list = Game.questions_stage_2;
        else if(Game.stage == 3)list = Game.questions_stage_3;
        
        if(Game.result == 1000000){
            announceResult(result);
            return false;
        }

        System.out.println("Pytanie brzmi:");
        System.out.print(Game.round+1+".");
        showQuestion(list);
        System.out.println("\nStan konta: "+Game.result);
        System.out.println("Co chcesz zrobic?: ");
        System.out.println("1.Odpowiedz na pytanie.");
        System.out.println("2.Skorzystaj z kola ratunkowego.");
        System.out.println("3.Zatrzymaj pieniadze");
        while(true){
            System.out.println("Odpowiedz '1' lub '2' lub '3'.");
            try{
                option = scan.nextInt();
                if(option == 1 || option == 2 || option == 3) break;
            }
            catch(Exception e){
                System.out.println("To nie jest poprawna odpowiedz!");
                scan.next(); //czysci skaner
            }
        }
        switch(option){
            case 1:
                Scanner scan_2 = new Scanner(System.in);
                char answQuestion = '0';
                System.out.println("Odpowiedz: ");
                while(true){
                    String answString ="";
                    try{
                        answString = scan_2.next();
                        answQuestion = answString.charAt(0);
                    }catch(Exception e){
                        System.out.println("To nie jest odpowiedz!");
                        scan.next();
                    }
                    if((answQuestion =='A' || answQuestion == 'B' || answQuestion == 'C' || 
                            answQuestion == 'D')&&answString.length()==1) {
                        break;
                    }else{
                        System.out.println("To nie jest odpowiedz!");
                        System.out.println("Podaj poprawnie");
                    }
                }
                    if(checkAnswer(answQuestion, list)){
                        System.out.println("Brawo! Poprawna odpowiedz! Przechodzisz do koljnego etapu.");
                        deleteQuestion(list);
                        Game.round++;
                        Game.usedLifeRing = false;
                        delay();
                    } else {
                        System.out.println("Niestety to nie jest poprawna odpowiedz.");
                        System.out.println("Poprawna odpowiedzia bylo: "+getCorrectAnswerChar(list));
                        announceResult(Game.result);

                        return false;
                    }
                break;
                
            case 2:
                int answLifeRing = -1;
                if(Game.usedAskAudience==true && Game.usedCallFriend==true && Game.usedFiftyFifty){
                    System.out.println("Nie masz juz kol ratunkowych!");
                    break;
                }
                if(Game.usedLifeRing){
                    System.out.println("Nie mozesz skorzystac z kola ratunkowego");
                    break;
                }
                else{
                    System.out.println("Z jakiego kola ratunkowego chcialbys skorzystac?(1,2,3,4");
                    System.out.println("1)Telefon do przjaciela");
                    System.out.println("2)50/50");
                    System.out.println("3)Pomoc od widowni");
                    System.out.println("4)Wstecz");
                    while(true){
                        try{
                            answLifeRing = scan.nextInt();
                        }catch(Exception e){
                            System.out.println("Nie ma takiej odpowiedzi!");
                            scan.next();
                        }
                        if(answLifeRing == 1 || answLifeRing == 2 || answLifeRing == 3
                                || answLifeRing == 4) break;
                    }
                    switch(answLifeRing){
                        case 1:
                            if(!Game.usedCallFriend){
                                callFriend(list);
                                Game.usedLifeRing = true;
                                Game.usedCallFriend = true;
                                delay();
                                break;
                            }else {
                                System.out.println("Nie masz juz tego kola!");
                                delay();
                                break;
                            }
                        case 2:
                            if(!Game.usedFiftyFifty){
                                fiftyFifty(list);
                                Game.usedLifeRing = true;
                                Game.usedFiftyFifty = true;
                                delay();
                                break;
                            }else{
                                System.out.println("Nie masz juz tego kola!");
                                break;
                            }
                        case 3:
                            if(!Game.usedAskAudience){
                                askAudience(list);
                                Game.usedLifeRing = true;
                                Game.usedAskAudience = true;
                                delay();
                                break;
                            }else{
                                System.out.println("Nie masz juz tego kola!");
                                break;
                            }
                        case 4:
                            break;
                    }
                }
                
                break;
            case 3:
                System.out.println("Na pewno chcesz zatrzymac pieniadze?Tak/Nie");
                while(true){
                    String answerGetMoney = "";
                    try{
                        answerGetMoney = scan.next();
                    }catch(Exception e){
                        System.out.println("Nie ma takiej odpowiedzi!");
                        scan.next();
                    }
                    if(answerGetMoney.equals("Tak")){
                        announceResult(Game.result);
                        return false;
                    } else if(answerGetMoney.equals("Nie")){
                        break;
                    }
                }
            default:
                System.out.println("Nie ma takiej opcji!");
        }
        return true;
    }
    //Metoda pokazujaca koncowy wynik
    static void announceResult(long result){
        System.out.println("Osiagnieto wynik: "+result+" zl");
        System.out.println("Powodzenia w kolejnych grach!");
    }
    //Metoda zwracajaca poprawna odpowiedz w charze
    static char getCorrectAnswerChar(List<Question> list){
        return list.get(0).getCorrectAnswer().charAt(0);
    }
    //Metoda sprawdzajaca czy podana odpowiedz jest poprawna
    static boolean checkAnswer(char answer,List<Question> list){
        char correctAnswer = getCorrectAnswerChar(list);
        return answer == correctAnswer;
    }
    //Metoda umozliwiajaca odrzucenie dwoch odpowiedzi(Kolo ratunkowe)
    static void fiftyFifty(List<Question> list){
        Random r = new Random();
        char correctAnswer = getCorrectAnswerChar(list);
        int n = 0;
        int lastNumb = -1;
        while(n<2){
            int randomNumber = r.nextInt(3);
            if(correctAnswer != list.get(0).getAnswers(randomNumber).charAt(0) &&
                    randomNumber != lastNumb){
                list.get(0).removeAnswer(randomNumber);
                lastNumb = randomNumber;
                n++;
            }
        }
        System.out.println("Odpowiedzi po uzyciu kola");        
        for(int i=0; i<list.get(0).numberOfAnswers();i++){
            System.out.println(list.get(0).getAnswers(i));
        }
        
    }
    //Metoda umozliwiajaca zapytanie publicznosci o odpowiedz(Kolo Ratunkowe)
    static void askAudience(List<Question> list){
        Random r = new Random();
        double []percent = {0,0,0,0};
        char []tempAnswers = {'A','B','C','D'};
        for(int i = 0; i<100; i++){
            int randomNumber = r.nextInt(125)+1;
            if(randomNumber<=25) percent[0]++;
            else if(randomNumber>25 && randomNumber <=50) percent[1]++;
            else if(randomNumber>50 && randomNumber <=75) percent[2]++;
            else if(randomNumber>75) percent[3]++;
        }
        char correctAnswer = getCorrectAnswerChar(list);
        for(int j=0; j<4; j++){
            if(tempAnswers[j]!=correctAnswer)percent[j]*=0.75;
            else percent[j]*=1.25;
        }
        System.out.println("O to odpowiedziw widowni: ");
        for(int n=0;n<4;n++)System.out.println("Odpowiedz "+tempAnswers[n]+" : "+percent[n]+"%");
    }
    //Metoda umozliwiajaca telefon do przyjaciela (Kolo Ratunkowe)
    static void callFriend(List<Question> list){
        Random r = new Random();
        int percentCorrect = 0;
        int percentIncorrect = 0;
        char answer;
        for(int i = 0; i<100; i++) {
            int randomNumber = r.nextInt(100)+1;
            if(randomNumber<=75) percentCorrect++;
            else percentIncorrect++;
        }
        if(percentCorrect>percentIncorrect) answer = list.get(0).getCorrectAnswer().charAt(0);
        else answer = list.get(0).getRandomAnswer();
        System.out.println("Wydaje mi sie, ze poprawna odpowiedz to "+answer);
    }
    
    //Metoda usuwajaca pytanie z listy, aby sie nie powtarzalo
    static void deleteQuestion(List<Question> list){
        list.remove(0);
    }
    //Metoda wyswietlajaca pytanie do gracza
    static void showQuestion(List<Question> list){
        Question question = list.get(0);
        System.out.println(question.getText());
        for(int i = 0; i<list.get(0).numberOfAnswers(); i++){
            System.out.println(question.getAnswers(i));
        }
    }
    
    //Metoda wczytujaca pytania z pliku tekstowego do list.
    static void loadQuestions(String fname, List<Question> list){
        String fileName = fname;
        String text, answerA, answerB, answerC, answerD;
        int correctAnswer;
        File file = new File(fileName);
        try(Scanner in = new Scanner(file)){
            while(in.hasNext()){
                text = in.nextLine();
                answerA = in.nextLine();
                answerB = in.nextLine();
                answerC = in.nextLine();
                answerD = in.nextLine();
                String []answers = {
                    answerA, answerB, answerC, answerD
                };
                correctAnswer = in.nextInt();
                list.add(new Question(text,correctAnswer,answers));
                if(in.hasNext()) in.nextLine(); //ominiecie powstajacej pustej lini
            }
            in.close();
        }catch(IOException e){
            System.out.println(e);
        }
    }
    
    //Metoda wyswietlajaca zasady gry
    static void showRules(){
        System.out.println("Zasady:");
        System.out.println("Odpowiadasz na zadane pytania,");
        System.out.println("za poprawna odpowiedz dostaniesz okreslona ilosc pieniedzy");
        System.out.println("Widok stanu konta bedzie dostepny w kazdym momencie");
        System.out.println("Posiadasz 3 kola ratunkowe z ktorych mozesz skorzystac po jedynm razie z kazdego");
        System.out.println("Mozesz skorzystac tylko z jednego kola ratunkowego przy jednym pytaniu");
        System.out.println("Bedziesz mogl zakonczyc gre w kazdym momencie, zabierajac uzbierana sume");
        System.out.println("Jesli jednak odpowiesz niepoprawnie na pytanie to stracisz wszystko");
    }
    
    //Metoda delay
    static void delay(){
        try{
            Thread.sleep(3000);}
        catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
}

