package org.example.demo.model

data class AlertDestination(
        var targetType: AlertDestinationType = AlertDestinationType.NOOP,
        var destinations: Collection<String> = emptyList(),
        var schedule: String? = null
)
