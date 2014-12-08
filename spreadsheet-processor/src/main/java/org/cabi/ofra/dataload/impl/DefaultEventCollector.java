package org.cabi.ofra.dataload.impl;

import org.cabi.ofra.dataload.event.Event;
import org.cabi.ofra.dataload.event.IEventCollector;
import org.cabi.ofra.dataload.event.IEventFilter;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Default implementation of the {@link org.cabi.ofra.dataload.event.IEventCollector} interface.
 * Provides a backing store for the event map, and the list of events
 */
public class DefaultEventCollector implements IEventCollector {
  private Map<String, Event> eventMap;
  private List<Event> events;

  public DefaultEventCollector() {
    eventMap = new HashMap<String, Event>();
    events = new LinkedList<Event>();
  }


  @Override
  public void addEvent(Event event) {
    events.add(event);
    eventMap.put(event.getId(), event);
  }

  @Override
  public Event removeEvent(String id) {
    Event e = eventMap.get(id);
    if (e != null) {
      events.remove(e);
      eventMap.remove(id);
      return e;
    }
    return null;
  }

  @Override
  public List<Event> getEvents() {
    return events;
  }

  @Override
  public List<Event> getEvents(IEventFilter filter) {
    return events.stream().filter(e -> filter.filter(e)).collect(Collectors.toList());
  }
}
