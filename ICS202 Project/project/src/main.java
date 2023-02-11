import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class main {
    public static void main(String[] args)
            throws WordAlreadyExistsException, WordNotFoundException, FileNotFoundException {

        //reading dictionary from file
        Scanner input = new Scanner(System.in);
        System.out.println("Enter filename> ");
        String fileName = input.next();
        File f = new File(fileName);
        Dictionary dict = new Dictionary(f);

        //testing findWord
        System.out.println("Check word> ");
        String word = input.next();
        if (dict.findWord(word))
            System.out.println("word found");
        else
            System.out.println("word not found");

        //testing add new word
        System.out.println("add new word> ");
        String newWord = input.next();
        dict.addWord(newWord);

        //testing remove word
        System.out.println("remove word> ");
        String removeWord = input.next();
        dict.deleteWord(removeWord);

        //adding sample test words
        dict.addWord("painter");
        dict.addWord("pointer");
        dict.addWord("printer");
        dict.addWord("punter");
    

        //testing search from similar
        System.out.println("search for similar words> ");
        String similarWord = input.next();
        String[] myArray;
        myArray = dict.findSimilar(similarWord);
        for(String word1:myArray)
            System.out.println(word1);

        
        //updating dictionary
        System.out.println("Save Updated Dictionary (Y/N)");
        String ans = input.next();
        
        if (ans.equals("Y")) {
            System.out.println("Enter filename> ");
            String fileName2 = input.next();
            File newFile = new File(fileName2);
            String[] dictArr = dict.toArray();

            try {
                FileWriter fWriter = new FileWriter(fileName2);
                for(int i=0; i<dictArr.length; i++){
                    fWriter.write(dictArr[i]);
                    fWriter.write(System.lineSeparator());
                }
                fWriter.close();
                System.out.println("Dictionary saved successfully");
            } catch (IOException e) {
                System.out.print(e.getMessage());
            }
        }

    }
}
