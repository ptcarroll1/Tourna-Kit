package hws.hw9;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

/**
 * Sample tests for TournaKit analyst methods.
 *
 * @author CS159 Faculty
 * @version 04/10/2024
 */
public class AnalystTest {

    public static final String PATH = "src/hws/hw9/data/";

    /**
     * Tests totalPointDifferential(Tournament, Matchup, String).
     */
    @Test
    public void test1() throws Exception {
        Tournament t;
        Matchup m;
        int actual;

        t = RoundRobinTournament.read(PATH + "quidditch_d1_2016.rrt");
        m = t.getFinalMatchups()[0];
        actual = Analyst.totalPointDifferential(t, m, "Beauxbatong");
        assertEquals(415, actual);
    }

    /**
     * Tests buildSchedule(RoundRobinTournament).
     */
    @Test
    public void test2() throws Exception {
        RoundRobinTournament t;
        Map<String, List<String>> actual;
        List<String> teams;

        t = RoundRobinTournament.read(PATH + "quidditch_d1_2016.rrt");
        actual = Analyst.buildSchedule(t);

        teams = actual.get("Beauxbatong");
        assertEquals(5, teams.size());
        assertEquals("Castelobruxo", teams.get(0));
        assertEquals("Durmstrang", teams.get(1));
        assertEquals("Hogwarts", teams.get(2));
        assertEquals("Uagadou", teams.get(3));
        assertEquals("Mahoutokoro", teams.get(4));
    }

    /**
     * Tests totalPointDifferential(SingleEliminationTournament).
     */
    @Test
    public void test3() throws Exception {
        SingleEliminationTournament t;
        int actual;

        t = SingleEliminationTournament.read(PATH + "lacrosse-women_d3_2017.set");
        actual = Analyst.totalPointDifferential(t);
        assertEquals(271, actual);
    }

    /**
     * Tests buildSchedule(SingleEliminationTournament).
     */
    @Test
    public void test4() throws Exception {
        SingleEliminationTournament t;
        Map<String, List<String>> actual;
        List<String> teams;

        t = SingleEliminationTournament.read(PATH + "lacrosse-women_d3_2017.set");
        actual = Analyst.buildSchedule(t);

        teams = actual.get("Middlebury");
        assertEquals(4, teams.size());
        assertEquals("Plymouth St.", teams.get(0));
        assertEquals("Colby", teams.get(1));
        assertEquals("Brockport", teams.get(2));
        assertEquals("TCNJ", teams.get(3));
    }

    /**
     * Tests calculateStanding(Tournament, Judge).
     */
    @Test
    public void test5() throws Exception {
        Tournament t;
        Map<String, Standing> actual;
        Standing standing;

        t = Tournament.read(PATH + "golf_dd_2018.tmt");
        actual = Analyst.calculateStandings(t, new LowWins());

        standing = actual.get("Baloo");
        assertEquals("  2 -   4 -   0", standing.toString());
    }

}
