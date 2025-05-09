package pro.sky.telegrambot.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notification_task")
public class NotificationTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Первичный ключ

    @Column(name = "chat_id", nullable = false)
    private Long chatId; // ID чата в Telegram (куда отправлять уведомление)

    @Column(nullable = false)
    private String message; // Текст напоминания

    @Column(name = "notification_date", nullable = false)
    private LocalDateTime notificationDate; // Дата и время отправки

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now(); // Время создания записи

    // Обязательный конструктор без аргументов для Hibernate
    public NotificationTask() {
    }

    public NotificationTask(Long chatId, String message, LocalDateTime notificationDate) {
        this.chatId = chatId;
        this.message = message;
        this.notificationDate = notificationDate;
    }

    public Long getId() {
        return id;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(LocalDateTime notificationDate) {
        this.notificationDate = notificationDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

}
