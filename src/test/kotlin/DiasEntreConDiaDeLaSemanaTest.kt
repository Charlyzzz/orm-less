import org.junit.Test
import java.time.LocalDate
import kotlin.test.assert
import kotlin.test.assertEquals
import kotlin.test.containsAll

class DiasEntreConDiaDeLaSemanaTest {

    //2019-1-21 fué Lunes

    @Test
    fun `cuando no hay ninguno adentro del rango devuelve vacio`() {
        val lunes = LocalDate.of(2019, 1, 21)
        val miercoles = LocalDate.of(2019, 1, 23)

        assertEquals(emptyList(), diasEntre(MismoDiaDeLaSemana(DiaDeLaSemana.VIERNES), lunes, miercoles))
    }

    @Test
    fun `cuando coincide con la fecha de inicio la incluye`() {
        val lunes = LocalDate.of(2019, 1, 21)
        val lunesEnDosSemanas = LocalDate.of(2019, 2, 4)

        assert(diasEntre(MismoDiaDeLaSemana(DiaDeLaSemana.LUNES), lunes, lunesEnDosSemanas)) {
            containsAll(
                LocalDate.of(2019, 1, 21),
                LocalDate.of(2019, 1, 28),
                LocalDate.of(2019, 2, 4)
            )
        }
    }

    @Test
    fun `cuando la fecha de inicio es posterior devuelve ese dia mas una semana`() {
        val martes = LocalDate.of(2019, 1, 22)
        val lunesEnDosSemanas = LocalDate.of(2019, 2, 4)

        assert(diasEntre(MismoDiaDeLaSemana(DiaDeLaSemana.LUNES), martes, lunesEnDosSemanas)) {
            containsAll(
                LocalDate.of(2019, 1, 28),
                LocalDate.of(2019, 2, 4)
            )
        }
    }

    @Test
    fun `cuando la fecha de inicio es anteior devuelve ese dia mas una semana`() {
        val lunes = LocalDate.of(2019, 1, 21)
        val lunesEnDosSemanas = LocalDate.of(2019, 2, 4)

        assert(diasEntre(MismoDiaDeLaSemana(DiaDeLaSemana.MIERCOLES), lunes, lunesEnDosSemanas)) {
            containsAll(
                LocalDate.of(2019, 1, 23),
                LocalDate.of(2019, 1, 30)
            )
        }
    }
}
