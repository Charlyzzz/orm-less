import org.junit.Test
import java.time.LocalDate
import kotlin.test.assert
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.test.containsAll

class ReglaDiaParticularTest {

    @Test
    fun `es feriado si el dia es el mismo de la fecha`() {
        val hoy = LocalDate.of(2018, 2, 14)
        val paraEstaRegla = ReglaDiaParticular(hoy)
        reQue { paraEstaRegla esFeriado hoy }
    }

    @Test
    fun `no es feriado si no tiene la misma fecha`() {
        val hoy = LocalDate.of(2018, 2, 14)
        val mañana = hoy.plusDays(1)
        val paraEstaRegla = ReglaDiaParticular(mañana)
        niEnPedo { paraEstaRegla esFeriado hoy }

    }

    @Test
    fun `iterar sobre fechas me devuelve solo las que están dentro del rango`() {
        val inicio = LocalDate.of(2018, 1, 20)
        val fin = LocalDate.of(2018, 3, 20)
        val reglaDia20 = ReglaDiaDelMes(10)

        val dias = diasEntre(20, inicio, fin)
        val diasEsperados = (1..3).map { LocalDate.of(2018, it, 20) }.toTypedArray()

        assert(dias) { containsAll(*diasEsperados) }
    }

    private fun niEnPedo(block: () -> Boolean) = assertFalse(null, block)
    private fun reQue(block: () -> Boolean) = assertTrue(null, block)
}
