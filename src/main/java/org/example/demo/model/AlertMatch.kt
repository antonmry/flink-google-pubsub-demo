package org.example.demo.model

import org.joda.time.DateTime
import org.joda.time.DateTimeZone

data class AlertMatch(
        var alert: Alert? = null,
        var url: String? = null,
        var postNumber: Int? = null,
        var matchDateTime: DateTime = DateTime.now(DateTimeZone.UTC)
)
