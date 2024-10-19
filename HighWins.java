package hws.hw9;


/**
* Utility class for performing various operations on Tournament objects.
*
* @author Patrick Carroll
* @version 3/12/24
*/


public class HighWins implements Judge {


    @Override
   public int result(Matchup matchup, Participant who) {


        int whoScore = matchup.getScore(who);
        int otherScore = matchup.getScore(who.other());


        if (whoScore > otherScore) {
            return 1;
        }
        if (whoScore < otherScore) {
            return -1;
        } else {
            return 0;
        }


    }


}
