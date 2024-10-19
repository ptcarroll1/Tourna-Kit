package hws.hw9;

/**
 * Utility class for performing various operations on Tournament objects.
 *
 * @author Patrick Carroll
 * @version 3/12/24
 */

public class Standing {
    private int wins;
    private int losses;
    private int ties;

    /**
     * The default constructor.
     */

    public Standing() {
        this.wins = 0;
        this.losses = 0;
        this.ties = 0;
    }
    /**
     * The second constructor.
     *
     * @param wins the wins.
     * @param losses the losses.
     * @param ties the ties.
     */

    public Standing(int wins, int losses, int ties) {
        this.wins = wins;
        this.losses = losses;
        this.ties = ties;
    }

    public int getLosses() {
        return losses;
    }

    public int getTies() {
        return ties;
    }

    public int getWins() {
        return wins;
    }
    /**
     * The method that increases the losses.
     */

    public void increaseLosses() {
        losses++;
    }
    /**
     * The method that increases the ties.
     */

    public void increaseTies() {
        ties++;
    }
    /**
     * The method that increases the wins.
     */

    public void increaseWins() {
        wins++;
    }
    /**
     * The to string method.
     *
     * @return The string form.
     *
     */

    public String toString() {
        return String.format("%3d - %3d - %3d", wins, losses, ties);
    }



}
