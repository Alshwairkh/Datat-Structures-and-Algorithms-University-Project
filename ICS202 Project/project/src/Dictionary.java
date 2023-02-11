import java.util.Arrays;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Dictionary {
    private AVLTree<String> tree;

    // non-parameter constructor to create empty dictionary
    public Dictionary() {
        tree = new AVLTree<>();
    }

    // constructor to create a dictionary with one string
    public Dictionary(String s) {
        tree = new AVLTree<>(new BSTNode<>(s));
    }

    // constructor to create a dictionary loaded with words in a file
    public Dictionary(File f) throws FileNotFoundException {
        tree = new AVLTree<>();
        Scanner sc = new Scanner(f);
        //loop to go through all words in the txtFile
        while (sc.hasNextLine())
            tree.insertAVL(sc.nextLine());

        System.out.println("Dictionary loaded successFully");
    }

    // method to add word into the dictionary
    public void addWord(String s) throws WordAlreadyExistsException {
        // throw exception if the word already existed
        if (this.findWord(s))
            throw new WordAlreadyExistsException(s);
        else
            tree.insertAVL(s);
        System.out.println("word added successfully");
    }

    // method to check if a word is already existed in the dictionary
    public boolean findWord(String s) {
        return tree.isInTree(s);
    }

    // method to delete a word from the dictionary
    public void deleteWord(String s) throws WordNotFoundException {
        if (this.findWord(s)){
            tree.deleteAVL(s);
            System.out.println("word deleted");}
        // throw an exception if the word does not exist in the dictionary
        else
            throw new WordNotFoundException(s);
    }

    //method to convert the dictionary into array
    public String[] toArray() {
        // convert the tree to string
        String dictStr = tree.treeToString();
        String[] dictArray = null;
        dictArray = dictStr.split(" ");
        // sort thr array
        Arrays.sort(dictArray);
        return dictArray;
    }

    
    // find similar method
    public String[] findSimilar (String s){
        // convert the dict into array
        String[] dictArray = this.toArray();
        // counter to count the number of similar words
        int similarCounter=0;
        // queue to enqueue similar words
        Queue<String> similarQueue = new Queue<>();
        // loop to go through all words
        for(String word:dictArray){
            // if the length is equal to the input
            if (word.length()==s.length()){
                // counter to count number of different characters
                int diff=0;
                for(int i=0;i<s.length();i++){
                    // if the chars are not equal increment diff
                    if (word.charAt(i)!=s.charAt(i))
                        diff++;

                }
                // if difference is 1 enqueue the word and increment similar counter
                if (diff==1){
                    similarQueue.enqueue(word);
                    similarCounter++;
                }

            }
            // if the input is longer by one character
            else if (s.length()-word.length()==1){
                // assume that they are identical
                boolean identical = true;
                // counter to count number of different characters
                int diff=0;
                for (int i=0;i<word.length();i++){
                    // if the twp words are identical so far
                    if (identical) {
                        // if there is a difference
                        if (word.charAt(i) != s.charAt(i)) {
                            // change identical to false and increment diff
                            identical = false;
                            diff++;
                            // if the next char is also different increment diff
                            if (word.charAt(i) != s.charAt(i + 1))
                                diff++;

                        }
                    }
                    // if the two words are no longer identical
                    else{
                        // if any further differences increment diff
                        if (word.charAt(i)!=s.charAt(i+1))
                            diff++;
                    }
                }
                // if diff is one or zero enqueue the word
                if (diff<=1){
                    similarQueue.enqueue(word);
                    similarCounter++;
                }

            }
            // if the word is longer by 1 character
            else if (word.length()-s.length()==1){
                // assume that they are identical
                boolean identical = true;
                // counter to count number of different characters
                int diff=0;
                for (int i=0;i<s.length();i++){
                    // if the twp words are identical so far
                    if (identical) {
                        // if there is a difference
                        if (s.charAt(i)!=word.charAt(i)) {
                            // change identical to false and increment diff
                            identical = false;
                            diff++;
                            // if the next char is also different increment diff
                            if (s.charAt(i)!=word.charAt(i+1))
                                diff++;

                        }
                    }
                    // if the two words are no longer identical
                    else{
                        // if any further differences increment diff
                        if (s.charAt(i)!=word.charAt(i+1))
                            diff++;
                    }
                }
                // if diff is one or zero enqueue the word
                if (diff<=1){
                    similarQueue.enqueue(word);
                    similarCounter++;
                }
            }
            // if the difference between the lengths is more than 1, skip the word
            else
                continue;
        }
        // create a string array
        String[] similarArray = new String[similarCounter];
        // fill the array with similar words from the queue
        for (int i=0;i<similarArray.length;i++)
            similarArray[i] = similarQueue.dequeue();
        //return the array
        return similarArray;

    }
}