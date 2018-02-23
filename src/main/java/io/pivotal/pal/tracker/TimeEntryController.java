package io.pivotal.pal.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/time-entries")
public class TimeEntryController{

    private TimeEntryRepository timeEntryRepo;


    public TimeEntryController(TimeEntryRepository timeEntryRepo) {
        this.timeEntryRepo = timeEntryRepo;
    }

    @PostMapping
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntry) {
        TimeEntry t = timeEntryRepo.create(timeEntry);

        return new ResponseEntity<>(t, HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable long id) {
        TimeEntry t = timeEntryRepo.find(id);

        if (t == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(t, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() {
        List<TimeEntry> t = timeEntryRepo.list();

        return new ResponseEntity<>(t, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable long id, @RequestBody TimeEntry expected) {
        TimeEntry t = timeEntryRepo.update(id, expected);

        if (t == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(t, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id) {
        timeEntryRepo.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
