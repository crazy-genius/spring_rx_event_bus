package info.crazylab.lib.spring.eventbus;

import info.crazylab.lib.eventbus.Subscriber;
import info.crazylab.lib.spring.eventbus.stereotype.EventSubscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SubscriberComponentScanner {

    @Autowired
    private ConfigurableListableBeanFactory configurableListableBeanFactory;

    public List<Subscriber> scan(String sourcePath) {
        List<Subscriber> subscribers = new ArrayList<>();

        ClassPathScanningCandidateComponentProvider scanner =
                new ClassPathScanningCandidateComponentProvider(false);

        scanner.addIncludeFilter(new AnnotationTypeFilter(EventSubscriber.class));

        for (BeanDefinition beanDefinition: scanner.findCandidateComponents(sourcePath)) {
            Object object = null;
            try {
                object = getBean(beanDefinition);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            if (object == null) {
                throw new RuntimeException("Object for class: ".concat(beanDefinition.toString()).concat(" can'not be find."));
            }

            subscribers.add((Subscriber) object);
        }

        return subscribers;
    }

    private Object getBean(BeanDefinition beanDefinition) throws ClassNotFoundException {
        String beanName = beanDefinition.getBeanClassName();
        return configurableListableBeanFactory.getBean(Class.forName(beanName));
    }
}
