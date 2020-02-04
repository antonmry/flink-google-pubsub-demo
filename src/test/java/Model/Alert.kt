package Model

import org.example.demo.model.AlertDestination
import org.example.demo.model.AlertDestinationType
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

    fun hasDestination(targetType: AlertDestinationType): Boolean {
        return destinations.any { it.targetType == targetType }
    }

}
