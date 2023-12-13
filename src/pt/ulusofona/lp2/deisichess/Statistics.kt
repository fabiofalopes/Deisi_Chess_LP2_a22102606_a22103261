package pt.ulusofona.lp2.deisichess


enum class StatType {
    TOP_5_CAPTURAS,
    TOP_5_PONTOS,
    PECAS_MAIS_CAPTURADAS,
    PECAS_MAIS_BARALHADAS,
    TIPOS_MAIS_CAPTURADOS
}

fun getStatsCalculator(type: StatType): (GameManager) -> ArrayList<String> {
    return { gameManager -> ArrayList()  };
}
