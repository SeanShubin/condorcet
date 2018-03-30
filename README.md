# Condorcet Method Voting

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


To further illustrate the problem with instant runoff voting, consider a contrived case with 10 candiates.
Each voter has their pet preference, which is relatively unknown.


    2 A B C
    1 C B A
    1 D B A
    1 E B A
    1 F B A
    1 G B A
    1 J B A
    1 I B A
    1 J B A

In both instant runoff and first past the post A wins, even though 80% of the voters prefer B to A 


All Condorcet methods will produce the same results as long as there is no circular ambiguity.
For example, say your candidates were rock, paper, and scissors.
If paper beats rock, rock beats scissors, and scissors beats paper, there is a circular ambiguity.
Condorcet methods differ in which "Condorcet completion method" they use to resolve such cycles.

The "Schulze" method for resolving cycles
Lets say 5 voters prefer A to B, and 4 voters prefer B to C
One possible "path" from A to C is A-(5)-B-(4)-C.
A paths "strength" is the strength of its weakest link.  The strength of the path A-B-C in this example is 4
The Schulze method computes the strongest path between each pair of candidates, and uses that to rank the candidates.
This eliminates cycles, which allows the candidates to be ranked.

The Floyd-Warshall algorithm
This is a graph analysis algorithm for finding the shortest paths in a weighted graph
It can be used to compute the strongest paths according to the Schulze method


<first past the post counter-example>

<instant run-off counter counter-example>

## Example Implementation

### Candidate Selection
Candidate selection is beyond the scope of this voting system, 
but keep in mind that the computed rankings will not represent actual preferences for candidates that the majority of the voters are not familiar with.

### Input

Each eligible voter will be able to assign numeric rankings to each candidate.
1 for their most preferred candidate, 2 for their second choice, and so on.
It is ok to assign the same number to multiple candidates, this expresses no preference between them.
There will always be a <no candidate> option, to express that those ranked after <no candidate> are actually being voted against.
What to do if <no candidate> actually wins the election, should be decided before the election is run.
Even though practical considerations may necessitate awarding the winning result to the second place person,
having this information makes it obvious that there is a problem with selecting viable candidates.   

### Output

The following will be output
- list of eligible voter ids that participated
- list of eligible voter ids that did not participate
- list of each vote, except instead of a voter id, list the confirmation number that only the voter knows is theirs

The vote output format will start with a confirmation code, then a list of candidate rankings (ranking and candidate-name), formatted as so.

    456-683-781 1 Bob-Johnson 2 Alice-Smith 2 Carol-Williams 3 Dave-Jones 
    202-324-658 1 Alice-Smith 2 Bob-Johnson 3 Carol-Williams
    795-667-002 1 Carol-Williams 2 Bob-Johnson 3 Alice-Smith 4 <no-candidate> 5 Dave-Jones




### Verifying election results

The goal here is provide enough information so that voters an verify the integrity of any election they were eligible to participate in, while protecting the secrecy of their vote.   

Upon voting, each voter gets a confirmation code.
Only the individual voter knows which confirmation code is theirs.
All confirmation codes and their corresponding votes will be made public.
This allows individual voters to verify that their vote was not altered, without exposing which vote is theirs.
Which voters participated in the election will be made public, but not how they actually voted.
This allows individual voters who abstained to verify that a vote was not cast in their name.

Anyone eligible to vote in an election, can verify the following once the election results are made public
- The Schulze method was applied correctly, by tallying the votes themselves 
- Their vote was not altered, by looking up their vote with their confirmation number
- If they abstained, a vote was not cast in their name, by looking up the list of those who actually cast ballots
