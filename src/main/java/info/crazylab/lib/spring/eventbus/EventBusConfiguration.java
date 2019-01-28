package info.crazylab.lib.spring.eventbus;

import info.crazylab.lib.eventbus.EventBus;
import info.crazylab.lib.eventbus.EventBusImplementation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("info.crazylab.spring.eventbus")
public class EventBusConfiguration {

    private static EventBus eventBus;

    @Bean
    public EventBus eventBus() {

        if (eventBus == null) {
            eventBus = appendSubscribers(new EventBusImplementation());
        }

        return eventBus;
    }

    protected EventBus appendSubscribers(EventBus eventBus) {
        return eventBus;
    }
}
