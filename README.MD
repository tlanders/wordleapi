
### Example request

`POST http://localhost:8080/api/wordle/recommend
`

#### Example body

`[
  {"guess":"slate", "wordleResponse":"--a?t!-"},
  {"guess":"abets", "wordleResponse":"a?b?-t!-"}
]`

#### Example response

`{
  "recommendedSolution": "rossa",
  "allowedLetters" : ["a","b","c"],
  "usedLetters" : ["s","e"]
}`

### complete-word-list.txt 
Allegedly the complete list of words that are recognized by Wordle (from https://github.com/Kinkelin/WordleCompetition).

### wordle-dictionary.txt
Allegedly the list of words that will appear in Wordle (from https://github.com/Kinkelin/WordleCompetition). "kazoo"
from 6/19 was missing though.