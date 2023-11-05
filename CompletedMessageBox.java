package src;

import java.util.*;

public class CompletedMessageBox {
    private List<String> messageBox;
    public CompletedMessageBox(){
        messageBox = new ArrayList<>();
    }
    public List<String> getMessageBox(){
        return messageBox;
    }
    public void addMessageBox(String messageBox){
        this.messageBox.add(messageBox);
    }
}
