package br.com.kotlin.theater.theater.data

import br.com.kotlin.theater.theater.domain.Booking
import br.com.kotlin.theater.theater.domain.Performance
import br.com.kotlin.theater.theater.domain.Seat
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface SeatRepository: JpaRepository<Seat, Long> {

    fun findByRowAndNum(row: Char, num: Int): Optional<Seat>
}
interface PerformanceRepository: JpaRepository<Performance, Long>
interface  BookingRepository: JpaRepository<Booking, Long> {

    fun findBySeatAndPerformance(seat: Seat, performance: Performance): Optional<Booking>
}
