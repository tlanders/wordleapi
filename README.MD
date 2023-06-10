
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