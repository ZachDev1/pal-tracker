package io.pivotal.pal.tracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    private long id = 1;
    private HashMap<Long, TimeEntry> timeEntryHashMap = new HashMap<>();

    public InMemoryTimeEntryRepository(){
    }


    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        timeEntry.setId(id);
        timeEntryHashMap.put(timeEntry.getId(), timeEntry);
        id++;
        return timeEntry;
    }

    @Override
    public TimeEntry find(long id) {
            return timeEntryHashMap.get(id);
    }

    @Override
    public TimeEntry update(long id, TimeEntry timeEntry) {
        TimeEntry t = timeEntryHashMap.get(id);
        t.setDate(timeEntry.getDate());
        t.setHours(timeEntry.getHours());
        t.setProjectId(timeEntry.getProjectId());
        t.setUserId(timeEntry.getUserId());
        return t;
    }

    @Override
    public void delete(long id) {
        timeEntryHashMap.remove(id);

    }

    @Override
    public List<TimeEntry> list() {
        return new ArrayList<>(timeEntryHashMap.values());
    }
}
