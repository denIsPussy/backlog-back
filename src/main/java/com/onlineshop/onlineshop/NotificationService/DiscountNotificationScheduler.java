package com.onlineshop.onlineshop.NotificationService;

import com.onlineshop.onlineshop.Controllers.NotificationController;
import com.onlineshop.onlineshop.Repositories.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DiscountNotificationScheduler {

    @Autowired
    private NotificationController notificationController;

    @Autowired
    private DiscountRepository discountRepository;

    // Запуск каждый день в 10:00 утра
//    @Scheduled(cron = "*/10 * * * * ?")
//    public void sendDailyDiscountNotifications() {
//        List<Discount> discounts = discountRepository.findAll(); // Получение активных скидок
//        if (!discounts.isEmpty()){
//            LocalDateTime now = LocalDateTime.now();
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//            String formattedDateTime = now.format(formatter); // Форматирует дату и время в строку
//            String message = "Задача выполняется каждую минуту: " + formattedDateTime;
//            //notificationController.sendToAll(message);
//        }
//    }
}