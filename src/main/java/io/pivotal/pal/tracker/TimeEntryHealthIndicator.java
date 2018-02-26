package io.pivotal.pal.tracker;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class TimeEntryHealthIndicator implements HealthIndicator {


    private final  TimeEntryRepository timeEntryRepository;

    public TimeEntryHealthIndicator(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository = timeEntryRepository;
    }

    @Override
    public Health health() {
        Health.Builder builder = new Health.Builder();
        List<TimeEntry> list = timeEntryRepository.list();
        if(list.size()<5){
             builder.up();
        }
        else{
            builder.down();
        }
        return builder.build();

    }
}
