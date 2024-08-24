
### Example request

`POST http://localhost:8080/api/wordle/recommend
`

#### Example body

`[
  "s-l-a?t!e-",
  "p-a!r-t!y-"
]`

"s-" means that "s" is not in the solution.
"a?" means that at least one "a" is in the solution, but not at the current position.
"t!" means that "t" is in the solution and at the current position.

#### Example response

`{
  "recommendedSolution": "faith",
  "possibleSolutions" : ["cacti", "faith"]
}`

### complete-word-list.txt 
Allegedly the full list of the 12972 words recognized by the official wordle game (from https://github.com/Kinkelin/WordleCompetition).

### wordle-dictionary.txt
Allegedly the list of all 2315 words that will become the official wordle at some point, shuffled to prevent spoilers (from https://github.com/Kinkelin/WordleCompetition).