import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;

public class Word {
    private LinkedList<String> words;

    public Word() {
        Set<String> wordSet = new HashSet<>();
        try {
            File myObj = new File("/Users/javibetancourt/CSCE314/Wordle/src/Words.txt");
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {
                String[] words = myReader.nextLine().trim().split("\\s+"); // Split line into words
                for (String word : words) {
                    if (!word.isEmpty()) {
                        wordSet.add(word);
                    }
                }
            }

            words = new LinkedList<>(wordSet);
            Collections.shuffle(words); // Shuffle the list

            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public boolean isWordInFile(String wordToCheck) {
        try {
            File myObj = new File("/Users/javibetancourt/CSCE314/Wordle/src/Words.txt");
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {
                String[] words = myReader.nextLine().trim().split("\\s+");
                for (String word : words) {
                    if (word.equals(wordToCheck)) {
                        myReader.close();
                        return true;
                    }
                }
            }
    
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    
        return false;
    }

    public String getRandomWord() {
        if (!words.isEmpty()) {
            return words.pop(); // Remove and return the first word
        }
        return null;
    }
}