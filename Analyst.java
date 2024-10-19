package hws.hw9;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Utility class for performing various operations on Tournament objects.
 *
 * @author PATRICK CARROLL
 * @version 04/22/2024
 */
public class Analyst {

    // ------------------------------------------------------------------------
    // Method #1 (10 pts)
    // ------------------------------------------------------------------------

    /**
     * Calculate the total point differential for a particular team, ending with
     * a particular Matchup.
     *
     * @param t The Tournament of interest
     * @param endingWith The Matchup to work backward from
     * @param team The team of interest
     * @return The total point differential
     */
    public static int totalPointDifferential(Tournament t, Matchup endingWith,
            String team) {
        Iterator<Matchup> matchups = t.iterator(endingWith, team);
        int result = 0;

        while (matchups.hasNext()) {
            Matchup m = matchups.next();
            result += Math.abs(m.getScore(Participant.HOME)
                    - m.getScore(Participant.VISITOR));
        }
        return result;
    }

    // ------------------------------------------------------------------------
    // Method #2 (15 pts)
    // ------------------------------------------------------------------------

    /**
     * Build the Schedule for a RoundRobinTournament.
     *
     * @param t The RoundRobinTournament
     * @return The schedules for each Participant (ordered by round)
     */
    public static Map<String, List<String>> buildSchedule(
            RoundRobinTournament t) {
        Matchup[] finMatch = t.getFinalMatchups();
        Map<String, List<String>> schedule = new HashMap<>();
        for (Matchup m : finMatch) {
            schedule.put(m.getName(Participant.HOME),
                    getOpponents(m, m.getName(Participant.HOME), t));
            schedule.put(m.getName(Participant.VISITOR),
                    getOpponents(m, m.getName(Participant.VISITOR), t));

        }
        return schedule;

    }

    /**
     * Helper method.
     *
     * @param last the last match.
     * @param team the team.
     * @param t the round robin.
     * @return the list of opponents.
     */

    private static List<String> getOpponents(Matchup last, String team,
            RoundRobinTournament t) {
        Iterator<Matchup> matches = t.iterator(last, team);
        List<String> result = new ArrayList<>();
        while (matches.hasNext()) {
            Matchup match = matches.next();
            if (match.getName(Participant.HOME) == team) {
                result.add(match.getName(Participant.VISITOR));
            } else {
                result.add(match.getName(Participant.HOME));
            }
        }
        return result;
    }

    // ------------------------------------------------------------------------
    // Method #3 (20 pts)
    // ------------------------------------------------------------------------

    /**
     * Calculate the total point differential for all Matchup objects in a
     * SingleEliminationTournament.
     *
     * @param t The SingleEliminationTournament
     * @return The total point differential
     */
    public static int totalPointDifferential(SingleEliminationTournament t) {
        Matchup finalMatchup = t.getFinalMatchup();
        return pointDiffHelper(t, finalMatchup);
    }

    /**
     * The helper method.
     *
     * @param t the tournament.
     * @param m the match.
     * @return the recursive differential.
     */

    private static int pointDiffHelper(SingleEliminationTournament t,
            Matchup m) {
        if (m == null) {
            return 0;
        }
        int differential = Math.abs(
                m.getScore(Participant.HOME) - m.getScore(Participant.VISITOR));
        Matchup prev1 = t.previous(m, m.getName(Participant.HOME));
        Matchup prev2 = t.previous(m, m.getName(Participant.VISITOR));
        differential += pointDiffHelper(t, prev1) + pointDiffHelper(t, prev2);
        return differential;
    }

    // ------------------------------------------------------------------------
    // Method #4 (20 pts)
    // ------------------------------------------------------------------------

    /**
     * Build the Schedule for a SingleEliminationTournament.
     *
     * @param t The SingleEliminationTournament
     * @return The schedules for each Participant (ordered by round)
     */
    public static Map<String, List<String>> buildSchedule(
            SingleEliminationTournament t) {
        Matchup finalMatchup = t.getFinalMatchup();
        Map<String, List<String>> result = new HashMap<>();
        return getOpps(t, finalMatchup, result);
    }
    /**
     * The helper method.
     *
     * @param t the tournement.
     * @param m the match.
     * @param result the result map.
     * @return a map
     */

    private static Map<String, List<String>> getOpps(
            SingleEliminationTournament t, Matchup m,
            Map<String, List<String>> result) {

        String home = m.getName(Participant.HOME);
        String away = m.getName(Participant.VISITOR);
        Matchup homePrev = t.previous(m, home);
        Matchup awayPrev = t.previous(m, away);



        if (result.containsKey(home)) {
            result.get(home).add(0, away);
        } else {
            List<String> homeOpps = new ArrayList<>();
            homeOpps.add(0, away);
            result.put(home, homeOpps);
        }
        if (result.containsKey(away)) {
            result.get(away).add(0, home);
        } else {
            List<String> awayOpps = new ArrayList<>();
            awayOpps.add(0, home);
            result.put(away, awayOpps);
        }

        if (awayPrev == null && homePrev == null) {
            return result;
        }
        if (homePrev != null) {
            getOpps(t, homePrev, result);
        }
        if (awayPrev != null) {
            getOpps(t, awayPrev, result);
        }
        return result;
    }

    // ------------------------------------------------------------------------
    // Method #5 (20 pts)
    // ------------------------------------------------------------------------

    /**
     * Calculate all of the standings for a given Tournament.
     *
     * @param t The Tournament of interest
     * @param j The Judge to use to determine outcomes
     * @return The Standing objects for every participant
     */
    public static Map<String, Standing> calculateStandings(Tournament t,
            Judge j) {
        Map<String, Standing> result = new HashMap<>();
        Matchup[] matches = t.getFinalMatchups();
        Matchup m = matches[0]; // the final matchup
        Set<Matchup> set = new HashSet<>();

        return genStandings(t, m, result, j, set);
    }

    /**
     * Generates standings based on result of matchup.
     *
     * @param t The tournament
     * @param m the matchup
     * @param rm result map of team and its standings
     * @param j The Judge with the result.
     * @param set Set storing the the matchups already evaluated
     * @return map with team and standings.
     */
    private static Map<String, Standing> genStandings(Tournament t, Matchup m,
            Map<String, Standing> rm, Judge j, Set<Matchup> set) {

        // if matchup in set then ignore and return else add matchup to set and
        // then evaluate it
        if (set.contains(m)) {
            return rm;
        } else {
            set.add(m);
        }

        String away = m.getName(Participant.VISITOR);
        String home = m.getName(Participant.HOME);
        Matchup h = t.previous(m, home);
        Matchup a = t.previous(m, away);

        if (rm.containsKey(home)) {

            Standing hs = rm.get(home);
            hs = updateStanding(j, hs, Participant.HOME, m);
            rm.put(home, hs);

        } else {

            rm.put(home,
                    (updateStanding(j, new Standing(), Participant.HOME, m)));

        }

        if (rm.containsKey(away)) {

            Standing as = rm.get(away);
            as = updateStanding(j, as, Participant.VISITOR, m);
            rm.put(away, as);

        } else {

            rm.put(away, (updateStanding(j, new Standing(), Participant.VISITOR,
                    m)));

        }

        if (h == null && a == null) {
            return rm;

        }

        if (h != null) {
            genStandings(t, h, rm, j, set);

        }
        if (a != null) {
            genStandings(t, a, rm, j, set);
        }

        return rm;

    }

    /**
     * Updates standing based on result of matchup.
     *
     * @param j The Judge with the result.
     * @param s The standings.
     * @param p The participant.
     * @param m the matchup.
     * @return updated standings
     */
    public static Standing updateStanding(Judge j, Standing s, Participant p,
            Matchup m) {
        int result = (j.result(m, p));

        if (result == 1) {
            s.increaseWins();
        } else if (result == -1) {
            s.increaseLosses();
        } else {
            s.increaseTies();
        }

        return s;

    }

}
