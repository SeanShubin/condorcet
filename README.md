# The Condorcet Method of resolving votes

## Prerequisites
- [maven](https://maven.apache.org/)
- [java jdk](http://www.oracle.com/technetwork/java/javase/downloads/index.html)

## Verify it works

    mvn package
    java -jar console/target/condorcet.jar < domain/src/test/resources/test-data/05-schulze-example-from-wikipedia/input.txt 

## Environment specific build instructions

### Ubuntu 16.04.4 x64

    apt-get update
    apt-get -y install maven default-jdk
    git clone https://github.com/SeanShubin/condorcet.git
    cd condorcet
    mvn package
    java -jar console/target/condorcet.jar < domain/src/test/resources/test-data/03-schulze-example-from-wikipedia/input.txt

## Goal
- Determine the preference of a group of eligible voters, both accurately and verifiably
    - Allow voters to express their entire preference
    - If a candidate would win a two-candidate election against each of the other candidates in a plurality vote, that candidate must be the winner.
    This is known as the "Condorcet Winner Criterion".
    - Publish enough information so that any voter can verify the results.
    If it is a secret ballot vote, individuals will not be able to verify the votes of others, but they can still detect if their vote was hijacked.




### Important, but out of scope to the voting system itself
- Who is allowed to vote
- How candidates are selected
- What to do in the event of a tie
- What to do if fraudulent ballots are detected
- What to do if the tally is discovered to be incorrect

### Capturing individual preference
- The voter is able to specify all of their preferences, not just their top candidate
    - This can be done by assigning a “rank” to each candidate, where 1 is their most preferred, 2 is their second most preferred, and so on.
- The voter is able to give the same rank to multiple candidates
    - This allows the voter to signify no preference between a group of candidates, while still expressing the ranking of that group in relation to other candidates.
- The voter is able to express which candidates they are voting against
    - This can be done by having a “<none of these>” entry they can rank in relation to the other candidates
    - While in practice it may be necessary to elect a candidate below <none of these> should <none of these> win, it is still useful to capture the fact that the voters did not have any choices they approved of.  This situation may indicate a problem with the candidate selection process, or the need to support write-in candidates.  
- The voter should be able to cast a vote with no rankings
    - This allows for telling the difference between “no preference at all”, and “preference is unknown”.
- Any unranked candidates are considered lower rank than ranked candidates
- All unranked candidates are considered ties with each other

### Mechanics
- Each voter is given a voter id.
    - This is attached to their ballot so that the voter cannot vote twice
- Upon voting, each voter is given a secret ballot identifier used to identify which ballot is theirs
    - A version 4 UUID is suitable for this purpose
    - The voter should get a different secret ballot identifier for each election they participate in
- After the votes are tallied, the following information is made public
    - The list of eligible voters
    - The eligible voters who did not vote
    - The actual votes, using the secret ballot identifier instead of the voter id
    - (optional) Intermediate calculations for the tally, so that if a technically minded person arrives at a different result, they can check to see if the flaw is in the voting system or their own calculations.
    - (optional, and only if not secret ballot) The actual ballots, including the voter id, can be made public.  This may be useful for practice runs used to test the voting system, or in representative voting situations where it is necessary to hold voters accountable to those they represent.
- The secret ballot identifier and actual votes, allows each voter to confirm that their vote was cast correctly, without revealing their identity.
- The list of eligible voters, allows someone who thought they were eligible to vote, to detect that their vote was not counted
- The list of eligible voters who did not vote, allows those who did not vote, to detect if a vote was cast in their name
- The “schulze method” is one kind of Condorcet method, this will be used to resolve circular ambiguities (refer to example below)

### Examples

#### Why Condorcet methods are superior to first past the post
Lets say you have 3 candidates, lets call them "minor-improvements", "status-quo", and "radical-changes".

    30% of the population prefers minor-improvements, then status-quo, then radical-changes
    30% of the population prefers status-quo, then minor-improvements, then radical-changes
    40% of the population prefers radical-changes, then minor-improvements, then status-quo

If you are only counting who has the most first place votes, radical-changes would win, even though this does not accurately reflect voter preference.
To see why consider this:

    70% of the voters would rather have minor-improvements than status-quo
    60% of the voters would rather have minor-improvements than radical-changes
    60% of the voters would rather have status-quo than radical-changes

When the candidates are compared in pairs, it is obvious the accurate voter preference is minor-improvements, then status-quo, then radical-changes.
But when only the voter's top most candidate is considered, we have thrown away so much information that the least preferred candidate, radical-changes, actually wins.
In a Condorcet method, the candidates in this same situation would be ranked minor-improvements, then status-quo, then radical-changes, which is consistent with the actual voter preference.

[Full Example](domain/src/test/resources/test-data/01-contrast-first-past-the-post)

#### How Condorcet methods reduce tactical voting
#### Why Condorcet methods are superior to instant runoff voting
#### How circular ambiguities are resolved using the “schulze method”
#### Verifying results against example on wikipedia





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

