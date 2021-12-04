package model;

import java.util.ArrayList;

public class Questions_Generator {
    ArrayList<Integer> question;

    public Questions_Generator() {
       question = new ArrayList<Integer>();
    }

    public ArrayList generateQuestion(){
        question.clear();

        int first = (int) (Math.random()*100);
        int second = (int) (Math.random()*100);
        int operator = (int)(Math.random()*4);
        int answer=0;
        if(operator==3){
            if(second==0){
                generateQuestion();
            }
            else if(second!=1){
                second=generateDiv(first);
            }
        }

        switch (operator){
            case 0:
                answer=first+second;
                break;
            case 1:
                answer=first-second;
                break;
            case 2:
                answer=first*second;
                break;
            case 3:
                if(second!=0) {
                    answer = first / second;
                }
                else{
                    generateQuestion();
                }
                break;
        }
        question.add(first);
        question.add(second);
        question.add(operator);
        question.add(answer);

        generateBait(answer,0);
        return question;
    }

    private int generateDiv(int first){
        ArrayList<Integer> divs = new ArrayList<>();
        boolean prime=true;
        int div=0;
        for(int i = 2; i<=first/2; i++) {
            if (first % i == 0) {
                prime=false;
                divs.add(i);
            }
        }
        if(prime) {
            generateDiv((int) (Math.random()*99));
        }
        else{
            int selectDiv = (int) (Math.random()*divs.size());
            div=divs.get(selectDiv);
        }

        return div;
    }

    private void generateBait(int answer,int baits){
        int deviation = (int)(Math.random()*10)+1;
        int upOrdown = (int)(Math.random()*2);
        if(deviation>10){
            generateBait(answer,baits);
        }
        if(baits!=3) {
            if(upOrdown==0) {
                deviation += answer;
            }else{
                deviation = answer-deviation;
            }
            if(question.contains(deviation)){
                generateBait(answer,baits);
            }
            question.add(deviation);
            generateBait(answer,baits+1);
        }
    }

    public ArrayList getQuestion() {
        return question;
    }
}
