package edu.ucne.jugadorestictactoe.data.remote.dto.Tecnico

import com.squareup.moshi.Json

data class TecnicoApi(

    @Json(name = "tecnicoId")
    val tecnicoId: Int? = null,
    @Json(name = "nombres")
    val nombres: String,

    @Json(name = "salarioHora")
    val salarioHora: Double
)