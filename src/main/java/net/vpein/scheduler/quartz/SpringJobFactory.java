package net.vpein.scheduler.quartz;

import lombok.extern.java.Log;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

@Log
public final class SpringJobFactory extends SpringBeanJobFactory implements ApplicationContextAware {


    private transient AutowireCapableBeanFactory beanFactory;
    @Override
    public void setApplicationContext(final ApplicationContext context) {
        beanFactory = context.getAutowireCapableBeanFactory();
    }

    @Override
    protected Object createJobInstance(final TriggerFiredBundle bundle) throws Exception {
        final Object job = super.createJobInstance(bundle);
        log.info("create job instance");
        beanFactory.autowireBean(job);
        return job;
    }
}