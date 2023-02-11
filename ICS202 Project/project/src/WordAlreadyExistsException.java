public class WordAlreadyExistsException extends Exception{
    public WordAlreadyExistsException(){
        super("Error: Word already existed");
    }
    public WordAlreadyExistsException(String s){
        super(("Error: ("+s+") word already existed"));
    }
}
