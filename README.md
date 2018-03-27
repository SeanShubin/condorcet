# Schulze Method Voting System

## Goal
If a candidate would win a two-candidate election against each of the other candidates in a plurality vote, that candidate must be the winner.
This is known as the "Condorcet Winner Criterion".

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
 