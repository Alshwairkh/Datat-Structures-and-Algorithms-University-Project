public class WordNotFoundException extends Exception{
    public WordNotFoundException(){
        super("Error: Word not found");

    }
    public WordNotFoundException(String s){
        super("Error: ("+s+") word not found");
    }
}
