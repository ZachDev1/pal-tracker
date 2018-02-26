package io.pivotal.pal.tracker;

import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/time-entries")
public class TimeEntryController{

    private TimeEntryRepository timeEntryRepo;
    private final GaugeService gauge;
    private final CounterService counter;




    public TimeEntryController(TimeEntryRepository timeEntryRepo,CounterService counterService,GaugeService gaugeService) {
        this.timeEntryRepo = timeEntryRepo;
        this.counter=counterService;
        this.gauge=gaugeService;
    }

    @PostMapping
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntry) {
        TimeEntry t = timeEntryRepo.create(timeEntry);
        counter.increment("TimeEntry.created");
        gauge.submit("timeEntries.count", timeEntryRepo.list().size());
        return new ResponseEntity<>(t, HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable long id) {
        TimeEntry t = timeEntryRepo.find(id);

        if (t == null) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        counter.increment("TimeEntry.read");
        return new ResponseEntity<>(t, HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() {
        List<TimeEntry> t = timeEntryRepo.list();
        counter.increment("TimeEntry.listed");
        return new ResponseEntity<>(t, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable long id, @RequestBody TimeEntry expected) {
        TimeEntry t = timeEntryRepo.update(id, expected);

        if (t == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        counter.increment("TimeEntry.updated");

        return new ResponseEntity<>(t, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id) {
        timeEntryRepo.delete(id);
        counter.increment("TimeEntry.deleted");
        gauge.submit("timeEntries.count", timeEntryRepo.list().size());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
