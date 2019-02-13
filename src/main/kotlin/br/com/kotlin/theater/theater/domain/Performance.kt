package br.com.kotlin.theater.theater.domain

import javax.persistence.*

@Entity
data class Performance(@Id @GeneratedValue(strategy = GenerationType.AUTO)
                       val id: Long,
                       val title: String) {

    @OneToMany(mappedBy="performance",fetch = FetchType.LAZY)
    lateinit var bookings: List<Booking>
}