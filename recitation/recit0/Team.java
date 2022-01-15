package recit0;

public class Team {
    private String mascot;
    private int score;

    public Team(String mascot) {
        this.mascot = mascot;
        score = 0;
    }

    public void score() {
        this.score += 2;
    }

    public String getMascot() {
        return mascot;
    }

    public int getScore() {
        return score;
    }

    public static void main(String[] args) {
        Team Celtics = new Team("Lucky the Leprechaun");
        Team ChicagoBulls = new Team("Bulls");
        
        Celtics.score();
        ChicagoBulls.score();
        ChicagoBulls.score();
        
        if (Celtics.getScore() > ChicagoBulls.getScore()) {
            System.out.println(Celtics.getMascot() + " wins!");
        } else if (ChicagoBulls.getScore() > Celtics.getScore()) {
            System.out.println(ChicagoBulls.getMascot() + " win!");
        } else {
            System.out.println("Tie!");
        }
    }
}
