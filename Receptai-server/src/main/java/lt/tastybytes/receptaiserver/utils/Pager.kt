package lt.tastybytes.receptaiserver.utils

import lt.tastybytes.receptaiserver.dto.PagedRequestDto
import org.springframework.data.domain.PageRequest

class Pager {

    private val page: Long
    private val entriesPerPage: Long?

    constructor() {
        this.page = 0
        this.entriesPerPage = null
    }

    constructor(page: Long) {
        this.page = page
        this.entriesPerPage = null
    }

    constructor(pagedDto: PagedRequestDto) {
        this.page = pagedDto.page().toLong()
        this.entriesPerPage = null
    }

    constructor(page: Long, entriesPerPage: Long) {
        this.page = page
        this.entriesPerPage = entriesPerPage
    }

    fun toPageRequest(defaultPageSize: Long): PageRequest {
        return PageRequest.of(this.page.toInt(), (entriesPerPage ?: defaultPageSize).toInt())
    }

}
