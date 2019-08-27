package io.pivotal.pal.tracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryTimeEntryRepository implements  TimeEntryRepository{

    private Map<Long, TimeEntry> store = new ConcurrentHashMap<Long, TimeEntry>();
    private AtomicLong counter = new AtomicLong(1L);

    public TimeEntry create(TimeEntry timeEntry) {

        timeEntry = new TimeEntry(this.counter.getAndIncrement(), timeEntry.getProjectId(), timeEntry.getUserId(), timeEntry.getDate(), timeEntry.getHours());

        this.store.put(timeEntry.getId(), timeEntry);
        return timeEntry;
    }

    public TimeEntry find(long id) {
        return this.store.get(id);
    }

    public List<TimeEntry> list() {
        List<TimeEntry> result = new ArrayList<TimeEntry>(this.store.values());
        return result;
    }

    public TimeEntry update(long id, TimeEntry timeEntry) {
        if(this.store.containsKey(id)) {
            this.store.remove(id);
            timeEntry.setId(id);
            this.store.put(id, timeEntry);
            return this.store.get(id);
        }
        return null;
    }

    public void delete(long id) {
        this.store.remove(id);
    }
}
