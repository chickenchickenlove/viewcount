package com.me.viewcount.v2;

import com.me.viewcount.domain.ViewCounter;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

@Component
@Slf4j
public class MemoryViewCounterV2 implements ViewCounter {

    private static final String VALUE_FORMAT = "%s-%s";


    private final ConcurrentLinkedQueue<String> eventQue;
    private final MyServiceV2 viewCounter;

    public MemoryViewCounterV2(MyServiceV2 viewCounter) {
        this.viewCounter = viewCounter;
        this.eventQue =  new ConcurrentLinkedQueue<>();
    }

    @Override
    public void visit(Long customerId, Long storeId) {
        String event = String.format(VALUE_FORMAT, customerId, storeId);
        this.eventQue.add(event);
    }

    @Override
    public void commit() {

        HashSet<String> eventSet = new HashSet<>();
        for (int i = 0; i < 10000; i++) {
            String poll = eventQue.poll();
            if (poll == null) {
                break;
            }
            eventSet.add(poll);
        }


        List<EventDto> eventDtos = eventSet.stream()
                .map(EventDto::create)
                .collect(Collectors.toList());

        for (EventDto eventDto : eventDtos) {
            boolean isNew = viewCounter.tx1(eventDto.getCustomerId(), eventDto.getStoreId());
            if (isNew) {
                viewCounter.tx1Update(eventDto.getStoreId());
            }
        }
    }

    @Scheduled(cron = "*/10 * * * * *")
    public void called() {
        log.info("eventQue Size = {}", eventQue.size());
        commit();
    }

    @Getter
    static class EventDto {
        private final Long customerId;
        private final Long storeId;
        public EventDto(Long customerId, Long storeId) {
            this.customerId = customerId;
            this.storeId = storeId;
        }

        public static EventDto create(String data) {
            String[] split = data.split("-");
            return new EventDto(
                    Long.valueOf(split[0]),
                    Long.valueOf(split[1]));
        }
    }


}
