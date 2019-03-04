import org.junit.Test
import java.time.LocalDate
import kotlin.test.assert
import kotlin.test.assertEquals
import kotlin.test.containsAll

class DiasEntreConDiaDelMesTest {

    @Test
    fun `cuando no hay ninguno adentro del rango devuelve vacio`() {
        val inicio = LocalDate.of(2018, 1, 20)
        val fin = LocalDate.of(2018, 1, 21)

        assertEquals(emptyList(), diasEntre(MismoDiaDelMes(5), inicio, fin))
    }

    @Test
    fun `cuando coincide con la fecha de inicio la incluye`() {
        val inicio = LocalDate.of(2018, 1, 20)
        val fin = LocalDate.of(2018, 3, 20)

        assert(diasEntre(MismoDiaDelMes(20), inicio, fin)) {
            containsAll(
                LocalDate.of(2018, 1, 20),
                LocalDate.of(2018, 2, 20),
                LocalDate.of(2018, 3, 20)
            )
        }
    }

    @Test
    fun `cuando la fecha de inicio es posterior devuelve ese dia mas un mes`() {
        val inicio = LocalDate.of(2018, 1, 20)
        val fin = LocalDate.of(2018, 3, 20)

        assert(diasEntre(MismoDiaDelMes(5), inicio, fin)) {
            containsAll(
                LocalDate.of(2018, 2, 5),
                LocalDate.of(2018, 3, 5)
            )
        }
    }

    @Test
    fun `cuando la fecha de inicio es anteior devuelve ese dia mas un mes`() {
        val inicio = LocalDate.of(2018, 1, 20)
        val fin = LocalDate.of(2018, 3, 20)

        assert(diasEntre(MismoDiaDelMes(22), inicio, fin)) {
            containsAll(
                LocalDate.of(2018, 1, 22),
                LocalDate.of(2018, 2, 22)
            )
        }
    }
}
