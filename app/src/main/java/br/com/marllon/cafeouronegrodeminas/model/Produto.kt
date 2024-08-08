package br.com.marllon.cafeouronegrodeminas.model

data class Produto(
    val idProduto: String = "",
    val tipoGrao: TipoGrao = TipoGrao.ARABICA_DO_CERRADO,
    val pontoTorra: PontoTorra = PontoTorra.MEDIA,
    val valor: Double = 0.0,
    val blend: Boolean = false
)

enum class TipoGrao(val descricao: String) {
    ARABICA_DO_CERRADO("Arábica do cerrado"),
    CONILON("Conilon")
}

enum class PontoTorra(val descricao: String) {
    MEDIA("Média"),
    FORTE("Forte")
}

