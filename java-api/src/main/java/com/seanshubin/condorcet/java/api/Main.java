package com.seanshubin.condorcet.java.api;

import com.seanshubin.condorcet.domain.Ballot;
import com.seanshubin.condorcet.domain.Election;
import com.seanshubin.condorcet.domain.TalliedElection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> candidates = new ArrayList<>();
        candidates.add("Chocolate");
        candidates.add("Vanilla");
        candidates.add("Strawberry");
        List<String> eligibleToVote = new ArrayList<>();
        eligibleToVote.add("Alice");
        eligibleToVote.add("Bob");
        eligibleToVote.add("Carol");
        eligibleToVote.add("Dave");
        List<Ballot> ballots = new ArrayList<>();
        ballots.add(Ballot.Companion.fromString("Alice alice-confirm 1 Vanilla   2 Chocolate"));
        ballots.add(Ballot.Companion.fromString("Carol carol-confirm 1 Chocolate 2 Vanilla"));
        ballots.add(Ballot.Companion.fromString("Dave  dave-confirm  1 Vanilla   2 Chocolate 3 Strawberry"));
        Election election = new Election(candidates, eligibleToVote, ballots);
        TalliedElection tallied = election.tally();
        tallied.toLines().forEach(System.out::println);
    }

    private static List<String> readAllLines(InputStream inputStream) {
        return readAllLines(new BufferedReader(new InputStreamReader(inputStream)));
    }

    private static List<String> readAllLines(BufferedReader reader) {
        List<String> lines = new ArrayList<String>();
        String line = readLine(reader);
        while (line != null) {
            lines.add(line);
            line = readLine(reader);
        }
        return lines;
    }

    private static String readLine(BufferedReader reader) {
        try {
            return reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
