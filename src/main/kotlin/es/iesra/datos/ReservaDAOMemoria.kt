package es.iesra.datos

import es.iesra.dominio.Reserva
import es.iesra.dominio.ReservaHotel
import es.iesra.dominio.ReservaVuelo
import javax.swing.text.StyledEditorKit

class ReservaDAOMemoria: IReservaDAO {
    private val lista = mutableListOf<Reserva>()

    override fun guardar(reserva: Reserva) {
        lista.add(reserva)
    }

    override fun leer(): List<Reserva> {
        return lista.toList()
    }

    override fun borrar(tipo: String, id: String): Boolean {
        val idInt = id.toIntOrNull() ?: return false
        return lista.removeIf { reserva ->
            val coincideId = reserva.id == idInt
            when (tipo.uppercase()) {
                "VUELO" -> coincideId && reserva is ReservaVuelo
                "HOTEL" -> coincideId && reserva is ReservaHotel
                else -> false
            }
        }
    }
}