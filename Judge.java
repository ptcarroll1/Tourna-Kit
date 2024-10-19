package hws.hw9;

/**
 * Class for performing various operations on Tournament objects.
 *
 * @author Patrick Carroll
 * @version 3/12/24
 */
public interface Judge {


    /**
     * The result method.
     *
     * @param matchup the matchup.
     * @param who the participant.
     * @return an integer.
     */
    int result(Matchup matchup, Participant who);

}
