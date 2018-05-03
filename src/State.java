import java.util.*;
public class State {
  public Integer balance;
  public HashMap<String, Channel> channels = new HashMap<String, Channel>();

  public Integer getBalance() {
    return balance;
  }

  public void setBalance(Integer balance) {
    this.balance = balance;
  }

  public HashMap<String, Channel> getChannels() {
    return channels;
  }

  public void setChannels(HashMap<String, Channel> channels) {
    this.channels = channels;
  }

  public State () {

  }
  public State(Integer balance, HashMap<String, Channel> channels) {
    this.balance = balance;
    this.channels = channels;
  }
}
