package es.iesra.datos

import es.iesra.dominio.Reserva

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

    override fun obtenerTodas(): List<Reserva> = reservas.toList()
}