import java.util.*;
public class Channel {
  public Boolean on;
  public ArrayList<Integer> messages = new ArrayList<Integer>();

  public Boolean getOn() {
    return on;
  }

  public void setOn(Boolean on) {
    this.on = on;
  }

  public ArrayList<Integer> getMessages() {
    return messages;
  }

  public void setMessages(ArrayList<Integer> messages) {
    this.messages = messages;
  }

  public Channel () {

  }
  public Channel(Boolean on, ArrayList<Integer> messages) {
    this.on = on;
    this.messages = messages;
  }
}
