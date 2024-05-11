package lt.tastybytes.receptaiserver.utils

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.domain.Sort.Order

class Sorter {

    private var orders: List<Order> = ArrayList()

    constructor(vararg orders: Order) {
        this.orders = orders.toList()
    }

    fun toPageRequest(pageRequest: PageRequest): PageRequest {
        return pageRequest.withSort(Sort.by(orders))
    }

}