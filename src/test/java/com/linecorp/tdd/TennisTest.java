package com.linecorp.tdd;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Test;

public class TennisTest {

    private Tennis tennis;

    @Test
    public void loveAll() {
        assertThat(tennis.score(), is("Love All"));
    }

    @Test
    public void fifteenLove() {
        givenPlayer1Scores(1);

        assertThat(tennis.score(), is("Fifteen Love"));

    }

    @Test
    public void thirtyLove() {
        givenPlayer1Scores(2);

        assertThat(tennis.score(), is("Thirty Love"));
    }

    @Test
    public void fortyLove() {
        givenPlayer1Scores(3);

        assertThat(tennis.score(), is("Forty Love"));
    }

    @Test
    public void loveFifteen() {

        givenPlayer2Scores(1);

        assertThat(tennis.score(), is("Love Fifteen"));
    }

    @Test
    public void thirtyFifteen() {

        givenPlayer1Scores(2);
        givenPlayer2Scores(1);

        assertThat(tennis.score(), is("Thirty Fifteen"));
    }

    @Test
    public void fifteenThirty() {

        givenPlayer1Scores(1);
        givenPlayer2Scores(2);

        assertThat(tennis.score(), is("Fifteen Thirty"));
    }

    @Test
    public void fifteenAll() {

        givenPlayer1Scores(1);
        givenPlayer2Scores(1);

        assertThat(tennis.score(), is("Fifteen All"));
    }

    @Test
    public void thirtyAll() {

        givenPlayer1Scores(2);
        givenPlayer2Scores(2);

        assertThat(tennis.score(), is("Thirty All"));
    }

    @Test
    public void deuce() {

        givenPlayer1Scores(3);
        givenPlayer2Scores(3);

        assertThat(tennis.score(), is("Deuce"));
    }

    @Test
    public void player1Wins() {
        givenPlayer2Scores(2);
        givenPlayer1Scores(4);

        assertThat(tennis.score(), is("Joey wins"));
    }

    @Test
    public void player2Wins() {
        givenPlayer1Scores(2);
        givenPlayer2Scores(4);

        assertThat(tennis.score(), is("Joseph wins"));
    }

    @Test
    public void player1Adv() {
        givenPlayer1Scores(3);
        givenPlayer2Scores(3);
        givenPlayer1Scores(1);

        assertThat(tennis.score(), is("Joey Adv"));
    }

    @Test
    public void player2Adv() {
        givenPlayer1Scores(3);
        givenPlayer2Scores(4);

        assertThat(tennis.score(), is("Joseph Adv"));
    }

    private void givenPlayer1Scores(int i) {
        IntStream.range(0, i).forEach(index -> tennis.player1Score());
    }

    private void givenPlayer2Scores(int i) {
        IntStream.range(0, i).forEach(index -> tennis.player2Score());
    }

    @Before
    public void setUp() throws Exception {
        tennis = new Tennis("Joey", "Joseph");
    }
}
