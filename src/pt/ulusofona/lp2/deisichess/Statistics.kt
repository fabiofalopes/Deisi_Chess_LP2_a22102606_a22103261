package pt.ulusofona.lp2.deisichess

enum class StatType {
    TOP_5_CAPTURAS,
    PECAS_MAIS_BARALHADAS,
    TOP_5_PONTOS,
    PECAS_MAIS_5_CAPTURAS,
    TIPOS_CAPTURADOS
}

fun getStatsCalculator(type: StatType): (GameManager) -> ArrayList<String> {
    return when (type){
        StatType.TOP_5_CAPTURAS -> :: top5Captures
        StatType.PECAS_MAIS_BARALHADAS -> :: top3PiecesWithInvalidMoves
        StatType.TOP_5_PONTOS -> :: top5PiecesKillsScore
        StatType.PECAS_MAIS_5_CAPTURAS -> :: top5PiecesWithMoreThen5Captures
        StatType.TIPOS_CAPTURADOS -> :: capturedPieces
    }
}

fun top5Captures(gameManager: GameManager): ArrayList<String> {
    return ArrayList(gameManager.game.pieces
            .sortedByDescending { it.getCountKills() }
            .take(5)
            .map {
                val teamName = if (it.teamId == Team.BLACK_TEAM_ID) {
                    Team.BLACK_TEAM_NAME
                } else {
                    Team.WHITE_TEAM_NAME
                }
                "${it.nickname} ($teamName) fez ${it.countKills} capturas"
            }
    )
}

fun top3PiecesWithInvalidMoves(gameManager: GameManager): ArrayList<String> {
    return ArrayList(gameManager.game.pieces
            .filter { it.getCountInvalidMoves() > 0 }
            .sortedByDescending { it.getCountInvalidMoves().toDouble() / (it.getCountInvalidMoves() + it.getCountValidMoves()) }
            .take(3)
            .map {
                val teamName = if (it.teamId == Team.BLACK_TEAM_ID) {
                    Team.BLACK_TEAM_NAME
                } else {
                    Team.WHITE_TEAM_NAME
                }
                "${teamName}:${it.nickname}:${it.getCountInvalidMoves()}:${it.getCountValidMoves()}"
            }
    )
}

fun top5PiecesKillsScore(gameManager: GameManager): ArrayList<String> {
    return ArrayList(gameManager.game.pieces
            .filter { it.getKillsScore() > 0 }
            .sortedWith(compareByDescending<Piece> { it.getKillsScore() }
            .thenBy { it.nickname })
            .take(5)
            .map {
                val teamName = if (it.teamId == Team.BLACK_TEAM_ID) {
                    Team.BLACK_TEAM_NAME
                } else {
                    Team.WHITE_TEAM_NAME
                }
                "${it.nickname} (${teamName}) tem ${it.getKillsScore()} pontos"
            }
    )
}

fun top5PiecesWithMoreThen5Captures(gameManager: GameManager): ArrayList<String> {
    return ArrayList(gameManager.game.pieces
        .filter { it.getCountKills() > 5 }
        .map {
            val teamName = if (it.teamId == Team.BLACK_TEAM_ID) {
                Team.BLACK_TEAM_NAME
            } else {
                Team.WHITE_TEAM_NAME
            }
            "${teamName}:${it.nickname}:${it.getCountKills()}"
        }
    )
}

fun capturedPieces(gameManager: GameManager): ArrayList<String> {
    return ArrayList()
}



