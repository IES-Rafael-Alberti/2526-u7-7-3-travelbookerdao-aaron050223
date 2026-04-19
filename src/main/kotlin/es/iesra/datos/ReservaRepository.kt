package es.iesra.datos

import es.iesra.dominio.Reserva
import es.iesra.dominio.ReservaHotel
import es.iesra.dominio.ReservaVuelo

/**
 * Implementación en memoria del repositorio de reservas.
 */
class ReservaRepository(val dao: IReservaDAO) : IReservaRepository {
    private val reservas = dao.leer().toMutableList()

    override fun agregar(reserva: Reserva): Boolean {
        var agregado = false
        // Si no existe, se agrega la reserva a la lista.
        if (!reservas.contains(reserva)) {
            dao.guardar(reserva)
            reservas.add(reserva)
            agregado = true
        }
        return agregado
    }

    override fun eliminar(tipo: String, id: String): Boolean {
        val borradoEnDao = dao.borrar(tipo, id)

        if (borradoEnDao) {
            val idInt = id.toIntOrNull() ?: return false
            return reservas.removeIf { reserva ->
                val coincideId = reserva.id == idInt
                when (tipo.uppercase()) {
                    "VUELO" -> coincideId && reserva is ReservaVuelo
                    "HOTEL" -> coincideId && reserva is ReservaHotel
                    else -> false
                }
            }
        }
        return false
    }

    override fun obtenerTodas(): List<Reserva> = reservas.toList()

}