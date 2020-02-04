package Model

import org.example.demo.model.AlertDestination
import org.joda.time.DateTime
import org.joda.time.DateTimeZone

data class Alert(
        var id: String? = null,
        var orgId: String? = null,
        var userIds: Collection<String>? = null,
        var enabled: Boolean? = false,
        var dailyThreshold: Int? = null,
        var destinations: Collection<AlertDestination> = ArrayList()
) {
    var cacheDateTime = DateTime.now(DateTimeZone.UTC)

    fun hasDestination(targetType: AlertDestinationType): Boolean {
        return destinations.any { it.targetType == targetType }
    }

}
