package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    @Autowired
    private TelegramBot telegramBot;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            // Process your updates here

            // Проверяем, что апдейт содержит сообщение и текст
            if (update.message() !=null && update.message().text() != null) {
                String messageText = update.message().text();
                Long chatId = update.message().chat().id();

                // Обрабатываем команду /start
                if (messageText.equals("/start")) {
                    String startMessege = "Бот запущен. Привет! Я бот. Вот что я могу(пока мало что)" +
                            "1.Напоминать тебе о событиях которые ты хочешь не забыть. (Пиши в формате: 01.01.2022 20:00 Сделать домашнюю работу)";
                    //Отправляем сообщение
                    SendMessage message = new SendMessage(chatId, startMessege);
                    telegramBot.execute(message);
                }
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

}
