package com.linecorp.tdd;

import java.util.HashMap;
import java.util.Map;

public class Tennis {

    private final String player1;
    private final String player2;

    private int score1 = 0;
    private int score2 = 0;

    public Tennis(String player1, String player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    private Map<Integer, String> createLookup() {

        final Map<Integer, String> lookup = new HashMap<>();
        lookup.put(0, "Love");
        lookup.put(1, "Fifteen");
        lookup.put(2, "Thirty");
        lookup.put(3, "Forty");

        return lookup;
    }

    public String score() {
        final Map<Integer, String> lookup = createLookup();

        final StringBuilder builder = new StringBuilder();
        if (isSameScore()) {
            if (score1 < 3) {
                return lookup.get(score1) + " All";
            }
            return "Deuce";
        }

        if (score1 < 4 && score2 < 4) {
            builder.append(lookup.get(score1));
            builder.append(' ');
            builder.append(lookup.get(score2));

            return builder.toString();
        }

        final String name = score1 > score2 ? player1 : player2;
        if (Math.abs(score1 - score2) == 1) {
            return name + " Adv";
        }
        return name + " wins";
    }

    private boolean isSameScore() {
        return score1 == score2;
    }

    void player1Score() {
        score1++;
    }

    void player2Score() {
        score2++;
    }
}

