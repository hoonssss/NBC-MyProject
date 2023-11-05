package src;

import java.util.ArrayList;
import java.util.List;

public class MessageBox{
    private List<String> messages;
    public MessageBox(){
        messages = new ArrayList<>();
    }
    public List<String> getMessage(){
        return messages;
    }
    public void addMessage(String message){
        messages.add(message);
    }
    public void clearMessage(){
        messages.clear();
    }
}
