package org.example.demo

class PubSubEvent {
    var videoId: String? = null
    var eventId: String? = null

    constructor(eventId: String?, videoId: String?) {
        this.eventId = eventId
        this.videoId = videoId
    }
}