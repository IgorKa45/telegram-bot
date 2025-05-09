package pro.sky.telegrambot.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.model.NotificationTask;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificationTaskRepository extends JpaRepository<NotificationTask, Long> {
    // Найти все задачи с указанной датой/временем напоминания
    List<NotificationTask> findByNotificationDate(LocalDateTime notificationDate);

    // Найти все задачи для определённого chatId
    List<NotificationTask> findByChatId(Long chatId);
}
