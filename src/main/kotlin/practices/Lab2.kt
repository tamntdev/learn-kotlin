package org.example.practices

import kotlin.random.Random

const val RESULT_WIN = "WIN"
const val RESULT_LOSE = "LOSE"
const val RESULT_DRAW = "DRAW"
const val TURN_PAPER = "PAPER"
const val TURN_ROCK = "ROCK"
const val TURN_SCISSOR = "SCISSOR"

fun main() {
    // Game bao búa kéo
    println("Game BAO BÚA KÉO")

    while (true) {
        println("1. Bao")
        println("2. Búa")
        println("3. Kéo")
        var player1Turn = 0
        while (player1Turn < 1 || player1Turn > 3) {
            println("Nhập vào đòn đánh của bạn:")
            player1Turn = readln().toIntOrNull() ?: 0
        }
        val playerHand = getHand(player1Turn)
        println(
            "Bạn đã chọn: $player1Turn = $playerHand"
        )

        val computerHand = generateComputerHand()

        println("Computer Chọn: $computerHand")
        println("Bắt đầu trận chiến")
        println(getResultMessage(battle(playerHand, computerHand)))
        println("Nhấn 1 để tiếp tục, 2 để end game")
        val select = readln().toIntOrNull() ?: 2

        if (select == 1) {

        } else {
            break
        }
    }
}


fun getResultMessage(result: String): String {
    return when (result) {
        RESULT_WIN -> "You win!"
        RESULT_LOSE -> "You loss!"
        else -> "Draw!"
    }
}

fun getHand(turn: Int): String {
    return when (turn) {
        1 -> TURN_PAPER
        2 -> TURN_ROCK
        else -> TURN_SCISSOR
    }
}

fun generateComputerHand(): String {
    val computerRandomFactor = Random.nextInt(10)

    return getHand(
        if (computerRandomFactor < 3) {
            1
        } else if (computerRandomFactor < 8) {
            2
        } else {
            3
        }
    )
}

// battle
fun battle(player1Hand: String, player2Hand: String): String {
    var result = RESULT_DRAW

    when (player1Hand) {
        TURN_PAPER -> {
            result = when (player2Hand) {
                TURN_ROCK -> RESULT_WIN
                TURN_SCISSOR -> RESULT_LOSE
                else -> RESULT_DRAW
            }
        }

        TURN_ROCK -> {
            result = when (player2Hand) {
                TURN_PAPER -> RESULT_LOSE
                TURN_SCISSOR -> RESULT_WIN
                else -> RESULT_DRAW
            }
        }

        TURN_SCISSOR -> {
            result = when (player2Hand) {
                TURN_PAPER -> RESULT_WIN
                TURN_ROCK -> RESULT_LOSE
                else -> RESULT_DRAW
            }
        }
    }

    return result
}