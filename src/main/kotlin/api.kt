import java.time.LocalDate

fun esFeriado(fecha: LocalDate, regla: Regla): Boolean {
    fecha.dayOfWeek

    return when (regla) {
        is ReglaDiaParticular -> regla.diaFeriado.isEqual(fecha)
        is ReglaDiaDeLaSemana -> fecha.diaDeLaSemana() == regla.diaDeLaSemana
        is ReglaDiaDelMes -> fecha.dayOfMonth == regla.numeroDiaDelMes
        is ReglaCompuesta -> regla.reglas.any { esFeriado(fecha, it) }
        is ReglaSinFeriado -> false
    }
}

fun feriadosEntre(fechaInicio: LocalDate, fechaFin: LocalDate, regla: Regla): List<LocalDate> {
    return when (regla) {
        is ReglaDiaParticular -> listOf(regla.diaFeriado)
        is ReglaDiaDeLaSemana -> diasEntre(MismoDiaDeLaSemana(regla.diaDeLaSemana), fechaInicio, fechaFin)
        is ReglaDiaDelMes -> diasEntre(MismoDiaDelMes(regla.numeroDiaDelMes), fechaInicio, fechaFin)
        is ReglaCompuesta -> regla.reglas.flatMap { feriadosEntre(fechaInicio, fechaFin, it) }
        is ReglaSinFeriado -> emptyList()
    }
}

sealed class CriterioIteracion(val semilla: (LocalDate, LocalDate) -> LocalDate, val iterador: (LocalDate) -> LocalDate)

data class MismoDiaDelMes(val diaDelmes: Int) :
    CriterioIteracion({ inicio, _ -> inicio.withDayOfMonth(diaDelmes) }, { it.plusMonths(1) })

data class MismoDiaDeLaSemana(val diaDeLaSemana: DiaDeLaSemana) :
    CriterioIteracion({ inicio, _ -> inicio.anterior(diaDeLaSemana) }, { it.plusWeeks(1) })


fun diasEntre(criterioIteracion: CriterioIteracion, fechaInicio: LocalDate, fechaFin: LocalDate): List<LocalDate> {
    return generateSequence(criterioIteracion.semilla(fechaInicio, fechaFin), criterioIteracion.iterador)
        .filter { it.isAfterEquals(fechaInicio) }
        .takeWhile { it.isBeforeEquals(fechaFin) }
        .toList()
}

private fun LocalDate.anterior(diaDeLaSemana: DiaDeLaSemana): LocalDate {
    var fecha = this
    while (fecha.diaDeLaSemana() != diaDeLaSemana) {
        fecha = fecha.minusDays(1)
    }
    return fecha
}

sealed class Regla

data class ReglaDiaParticular(val diaFeriado: LocalDate) : Regla()

data class ReglaDiaDeLaSemana(val diaDeLaSemana: DiaDeLaSemana) : Regla()

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
    DOMINGO;
}

private fun LocalDate.isBeforeEquals(fecha: LocalDate): Boolean =
    this.isEqual(fecha) or this.isBefore(fecha)

private fun LocalDate.isAfterEquals(fecha: LocalDate): Boolean =
    this.isEqual(fecha) or this.isAfter(fecha)

private fun LocalDate.diaDeLaSemana(): DiaDeLaSemana = DiaDeLaSemana.values()[this.dayOfWeek.value - 1]

infix fun Regla.esFeriado(fecha: LocalDate): Boolean = esFeriado(fecha, this)