//Class to maintain list of branches
public class BranchInfo {
  public String branchName;
  public String branchIP;
  public int branchPort;
  public int balance;

  public String getBranchName() {
    return branchName;
  }

  public void setBranchName(String branchName) {
    this.branchName = branchName;
  }

  public String getBranchIP() {
    return branchIP;
  }

  public void setBranchIP(String branchIP) {
    this.branchIP = branchIP;
  }

  public int getBranchPort() {
    return branchPort;
  }

  public void setBranchPort(int branchPort) {
    this.branchPort = branchPort;
  }

  public int getBalance() {
    return balance;
  }

  public void setBalance(int balance) {
    this.balance = balance;
  }

  public BranchInfo() {

  }

  public BranchInfo(String branchName, String branchIP, int branchPort, int balance) {
    //TODO Auto-generated constructor stub
    this.branchName = branchName;
    this.branchIP = branchIP;
    this.branchPort = branchPort;
    this.balance = balance;
  }
}

