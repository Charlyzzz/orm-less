import java.time.LocalDate

fun esFeriado(fecha: LocalDate, regla: Regla): Boolean {
    fecha.dayOfWeek

    return when (regla) {
        is ReglaDiaParticular -> regla.diaFeriado.isEqual(fecha)
        is ReglaDiaDeLaSemana -> fecha.numeroDiaDeLaSemana() == regla.numeroDiaDeLaSemana
        is ReglaDiaDelMes -> fecha.dayOfMonth == regla.numeroDiaDelMes
        is ReglaCompuesta -> regla.reglas.any { esFeriado(fecha, it) }
        is ReglaSinFeriado -> false
    }
}

fun feriadosEntre(fechaInicio: LocalDate, fechaFin: LocalDate, regla: Regla): List<LocalDate> {
    return when (regla) {
        is ReglaDiaParticular -> listOf(regla.diaFeriado)
        is ReglaDiaDeLaSemana ->
        is ReglaDiaDelMes -> diasEntre(regla.numeroDiaDelMes, fechaInicio, fechaFin)
        is ReglaCompuesta -> regla.reglas.flatMap { feriadosEntre(fechaInicio, fechaFin, it) }
        is ReglaSinFeriado -> emptyList()
    }
}

fun diasEntre(numeroDiaDelMes: Int, fechaInicio: LocalDate, fechaFin: LocalDate): List<LocalDate> {
    val primerDia: LocalDate = fechaInicio.withDayOfMonth(numeroDiaDelMes)

    return generateSequence(primerDia) { it.plusMonths(1) }
        .takeWhile { it.isBefore(fechaFin) or it.isEqual(fechaFin) }
        .toList()
}


fun diasEntre2(numeroDiaDeLaSemana: Int, fechaInicio: LocalDate, fechaFin: LocalDate): List<LocalDate> {
    val primerDia: LocalDate = fechaInicio.withDayOfMonth(numeroDiaDelMes)

    return generateSequence(primerDia) { it.plusMonths(1) }
        .takeWhile { it.isBefore(fechaFin) or it.isEqual(fechaFin) }
        .toList()
}

sealed class Regla

data class ReglaDiaParticular(val diaFeriado: LocalDate) : Regla()

data class ReglaDiaDeLaSemana(val numeroDiaDeLaSemana: DiaDeLaSemana) : Regla()

data class ReglaDiaDelMes(val numeroDiaDelMes: Int) : Regla()

data class ReglaCompuesta(val reglas: List<Regla>) : Regla()

object ReglaSinFeriado : Regla()

enum class DiaDeLaSemana {
    LUNES,
    MARTES,
    MIERCOLES,
    JUEVES,
    VIERNES,
    SABADO,
    DOMINGO
}

fun LocalDate.numeroDiaDeLaSemana(): DiaDeLaSemana = DiaDeLaSemana.values()[this.dayOfWeek.value]

fun ClosedRange<LocalDate>.iterator(): Iterator<LocalDate> = TODO()

infix fun Regla.esFeriado(fecha: LocalDate): Boolean = esFeriado(fecha, this)