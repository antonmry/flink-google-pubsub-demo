package org.example.demo.model

// TODO: why JodaTime instead of Java 8 Date/Time? Mandatory?
import org.joda.time.DateTime
import org.joda.time.DateTimeZone

data class Alert(var id: String? = null, var orgId: String? = null) {

    var userIds: Collection<String>? = null

    var enabled: Boolean = false
    var cacheDateTime = DateTime.now(DateTimeZone.UTC)

    var dailyThreshold: Int? = null

    var destinations: Collection<AlertDestination> = ArrayList()

    fun hasDestination(targetType: AlertDestinationType): Boolean {
        return destinations.any { it.targetType == targetType }
    }

    override fun toString(): String {
        return "Alert(id=$id, orgId=$orgId, userIds=$userIds, enabled=$enabled, cacheDateTime=$cacheDateTime, dailyThreshold=$dailyThreshold, destinations=$destinations)"
    }

}
