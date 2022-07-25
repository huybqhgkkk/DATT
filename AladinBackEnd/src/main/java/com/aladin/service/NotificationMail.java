package com.aladin.service;

import com.aladin.repository.KiEmployeeRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@EnableScheduling
public class NotificationMail {
    private final NotificationKi notificationKi;
    private final KiEmployeeRepository kiEmployeeRepository;
    public NotificationMail(NotificationKi notificationKi, KiEmployeeRepository kiEmployeeRepository) {
        this.notificationKi = notificationKi;
        this.kiEmployeeRepository = kiEmployeeRepository;
    }

    @Scheduled(cron = "0 0 8 25 * *")
    public void notificationmail() {
            notificationKi.notification();
    }
    @Scheduled(cron = "1 1 1 1 * *")
    @Transactional(readOnly = true)
    public void doneki()
    {
            kiEmployeeRepository.endKi();
    }

}
