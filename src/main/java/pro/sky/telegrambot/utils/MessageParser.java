package pro.sky.telegrambot.utils;



import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageParser {
    // Паттерн для распознавания даты и текста: "01.01.2025 20:00 Текст напоминания"
    private static final Pattern PATTERN = Pattern.compile(
            "(\\d{2}\\.\\d{2}\\.\\d{4}\\s\\d{2}:\\d{2})(\\s+)(.+)"
    );

    // Форматтер для преобразования строки в LocalDateTime
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public static ParsedMessage parse(String text) throws  IllegalArgumentException{
        Matcher matcher = PATTERN.matcher(text);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Некорректный формат сообщения. Используйте: ДД.ММ.ГГГГ ЧЧ:MM Текст");
        }

        try {
            String dateTimeStr = matcher.group(1); // "01.01.2025 20:00"
            String taskText = matcher.group(3);  // "Текст напоминания"

            LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, FORMATTER);
            return new ParsedMessage(dateTime, taskText);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Некорректная дата или время");
        }
    }
    public static class ParsedMessage {
        private final LocalDateTime dateTime;
        private final String text;

        public ParsedMessage(LocalDateTime dateTime, String text) {
            this.dateTime = dateTime;
            this.text = text;
        }

        public LocalDateTime getDateTime() {
            return dateTime;
        }

        public String getText() {
            return text;
        }
    }
}

