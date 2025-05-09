package pro.sky.telegrambot.service;



import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.repository.NotificationTaskRepository;
import pro.sky.telegrambot.utils.MessageParser;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class NotificationService {
    private final NotificationTaskRepository repository;
    private final TelegramBot telegramBot;

    public NotificationService(NotificationTaskRepository repository, TelegramBot telegramBot) {
        this.repository = repository;
        this.telegramBot = telegramBot;
    }

    @Scheduled(cron = "0 * * * * *") // Каждую минуту
    public void checkAndSendNotification() {
        LocalDateTime currentTime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        List<NotificationTask> tasks = repository.findByNotificationDate(currentTime);

        tasks.forEach(task ->{
            SendMessage message = new SendMessage(
                    task.getChatId(),
                    "Напоминание: " + task.getMessage()
            );
            telegramBot.execute(message);

            // Опционально: удаляем отправленную задачу
            repository.delete(task);
        });
    }
    public NotificationTask parseAndSaveTask(Long chatId, String messageText) {
        try {
            MessageParser.ParsedMessage parsed = MessageParser.parse(messageText);
            NotificationTask task = new NotificationTask(
                    chatId,
                    parsed.getText(),
                    parsed.getDateTime()
            );
            return repository.save(task);
        } catch (IllegalArgumentException e) {
            return null;  // Формат сообщения неверный
        }
    }
}
