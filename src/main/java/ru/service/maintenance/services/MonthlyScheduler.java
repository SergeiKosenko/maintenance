package ru.service.maintenance.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MonthlyScheduler {

    @Autowired
    private WorkSiteService workSiteService;

    // Запуск задачи в 00:00 первого числа каждого месяца
    @Scheduled(cron = "0 0 0 1 * ?")
    public void runMonthlyTask() {
        workSiteService.updateFlagsMonthly();
    }
}
