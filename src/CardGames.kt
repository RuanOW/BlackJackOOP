
fun main() {
    val availableGames = listOf("BlackJack")
    println("What game would you like to play?")
    printGames(availableGames)

    game@while (true) {
        var gameChosen = readLine().toString()
        when(gameChosen){
            "1" -> {
                println(blackjackSign)
                BlackJackGame()
                break@game
            }
            in listOf("q", "quite", "exit") -> {
                println("quiting game!")
                break@game
            }
            else -> {
                println("Sorry don't seem to have that game..")
                println("Please choose again")
                printGames(availableGames)
                continue@game
            }
        }
    }
}

fun printGames(games: List<String>){
    for((index, game) in games.withIndex()) println("${index} -> ${game}")
}