import java.util.Random;

public class Player {
    private static Random ran = new Random();
    private String userName;
    private int score = 0;
    private int mistake = 0;

    public Player(String userName) {
        this.userName = userName;
    }

    public Player() {
        userName = "User#" + (ran.nextInt(9000) + 1000);
    }

    public void setUserName(String userName1) {
        this.userName = userName1;
    }

    public void addScore() {
        score++;
    }

    public void costScore() {
        score--;
    }

    public void addMistake() {
        mistake++;
    }

    public int getScore() {
        return score;
    }

    public String getUserName() {
        return userName;
    }

    public int getMistake() {
        return mistake;
    }

    public void setMistake(int mistake) {
        this.mistake = mistake;
    }

    public void setScore(int score) {
        this.score = score;
    }

}
