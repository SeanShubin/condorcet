# The Condorcet Method of resolving votes

## Prerequisites
- [maven](https://maven.apache.org/)
- [java jdk](http://www.oracle.com/technetwork/java/javase/downloads/index.html)

## Verify it works

    mvn package
    java -jar console/target/condorcet.jar < domain/src/test/resources/test-data/03-schulze-example-from-wikipedia/input.txt 

## Environment specific build instructions

### Ubuntu 16.04.4 x64

    apt-get update
    apt-get -y install maven default-jdk
    git clone https://github.com/SeanShubin/condorcet.git
    cd condorcet
    mvn package
    java -jar console/target/condorcet.jar < domain/src/test/resources/test-data/03-schulze-example-from-wikipedia/input.txt

## Todo list
- more detailed technical documentation for the application
- more detailed technical documentation for schulze flavor of condorcet methods

## Goal
If a candidate would win a two-candidate election against each of the other candidates in a plurality vote, that candidate must be the winner.
This is known as the "Condorcet Winner Criterion".

Condorcet methods are good at ranking candidates in an order that accurately reflects voter preference.

The first step is allowing voters to express the entirety of their preference rather than just their #1 candidate.
A voter can do this by ranking candidates in order of preference.

For example, lets say you have this situation

    30% of the population prefers A, then B, then C
    30% of the population prefers B, then A, then C
    40% of the population prefers C, then A, then B

If you are only counting who has the most first place votes, C would win, even though this does not accurately reflect voter preference.
To see why consider this:

    70% of the voters would rather have A than B
    60% of the voters would rather have A than C
    60% of the voters would rather have B than C

When the candidates are compared in pairs, it is obvious the accurate voter preference is A, then B, then C.
But when only the voter's top most candidate is considered, we have thrown away so much information that the least preferred candidate, C, actually wins.
In a Condorcet method, the candidates in this same situation would be ranked A, then B, then C, which is consistent with the actual voter preference.

Now consider this dilemma

    3 voters prefer A, then B, then C
    4 voters prefer B, then A, then C
    2 voters prefer C, then A, then B

If we are counting the most first place votes or doing instant runoff voting, although the 2 voters prefer C, they don't dare vote that way because that would throw the election to B rather than A.
It is in the best interest of the 2 voters to not express their preference accurately.
This unfairly misrepresents the number of voters who actually preferred C, as that information about their preference was lost.
A Condorcet method would compare the candidates in pairs, like so:

    A defeats B 5 to 4
    A defeats C 7 to 2
    B defeats C 7 to 2
    
So the ranking becomes A, then B, then C.
In a Condorcet method, the 2 voters could accurately express their vote, and not cause B to defeat A.


Instant runoff voting does not meat condorcet criteria, it works like this:

    Ballots are initially counted for each elector's top choice,
    losing candidates are eliminated,
    and ballots for losing candidates are redistributed until one candidate is the top remaining choice of a majority of the voters.

The problem with this is that since only the top choices are counted each round, candidates closer to the consensus preference can be the first ones eliminated.
To illustrate this, consider a contrived case with 10 candidates.
Each voter has a relatively unknown preference.
Each voter would settle for B instead of their preference.


    2 A B C
    1 C B A
    1 D B A
    1 E B A
    1 F B A
    1 G B A
    1 J B A
    1 I B A
    1 J B A

In both instant runoff and first past the post A wins, even though 80% of the voters prefer B to A.
This is because B, the one every single voter preferred over someone else's top candidate, was the first one eliminated. 

