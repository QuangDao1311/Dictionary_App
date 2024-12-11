package game.question;

import java.util.Random;

import word.Word;

public class WordGuess extends Word {
    private int position;

    public WordGuess() {
        super();
        randomPosition();
    }

    public WordGuess(String word, String explain) {
        super(word, explain);
        randomPosition();
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void randomPosition() {
        String word = super.getWordTarget();
        int n = word.length();
        Random rand = new Random();
        if (n <= 1) {
            position = 0;
        } else {
            position = rand.nextInt(n - 1);                 //random vị trí sẽ lấy để đoán từ
        }
    }

    public boolean checkAnswers(char answer) {
        char correctAnswers = Character.toUpperCase(super.getWordTarget().charAt(position));        //lưu từ cần đoán vào correctAnswers
        answer = Character.toUpperCase(answer);
        if (answer == correctAnswers) {
            return true;
        }
        return false;
    } 

    public void printWordGuess() {
        String s = super.getWordTarget();
        String wordGuess = s.substring(0, position) + "_" + s.substring(position + 1, s.length());  //in từ đó ra nhưng thay kí tự bằng "_"
        System.out.println("----------------" + wordGuess + "------------");
    }

    public String wordGuess() {
        String s = super.getWordTarget();
        String wordGuess = s.substring(0, position) + "_" + s.substring(position + 1, s.length());  //trả về wordGuess là từ để đố
        return wordGuess;
    }

    public void printWord() {
        System.out.println(super.getWordTarget() + "  :\t\t" + super.getWordExplain());
    }
}