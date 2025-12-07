package com.comp2042.model.event;

/**
 * Immutable data class for controller.
 * Encapsulates type of event and source of event.
 */
public final class MoveEvent {
    private final EventType eventType;
    private final EventSource eventSource;

    /**
     * Constructs a MoveEvent object.
     * @param eventType  the type of event
     * @param eventSource the source of event
     */
    public MoveEvent(EventType eventType, EventSource eventSource) {
        this.eventType = eventType;
        this.eventSource = eventSource;
    }

    /**
     * Gets the event type.
     * @return a {@code EventType} object containing the type of event
     */
    public EventType getEventType() {
        return eventType;
    }

    /**
     * Gets the event source.
     * @return a {@code EventSource} object containing the source of event
     */
    public EventSource getEventSource() {
        return eventSource;
    }
}
