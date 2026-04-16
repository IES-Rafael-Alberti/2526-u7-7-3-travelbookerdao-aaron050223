package es.iesra.datos

import es.iesra.dominio.Reserva

interface IReservaDAO {
    fun agregar(reserva: Reserva): Boolean
    fun obtenerTodas(): List<Reserva>
}