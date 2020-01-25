package com.datamaster.polls.utils;

import com.datamaster.polls.dto.request.meta.BasePollRequestDTO;
import com.datamaster.polls.dto.request.meta.BaseQuestionRequestDTO;
import com.datamaster.polls.exception.ValidationFailedException;
import com.datamaster.polls.model.Poll;
import com.datamaster.polls.model.Question;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

/**
 * Вспомогательный класс для конвертации одних объектов в другие
 */
public class Converter {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Конвертируем дто запроса в сущность Вопроса
     *
     * @param dto  Запрос
     * @param poll Опрос которому принадлежит вопрос
     * @return Вопрос
     */
    public static Question convertQuestionDTO(BaseQuestionRequestDTO dto, Poll poll) {
        return Question.builder()
                .content(dto.getContent())
                .sort(dto.getSort())
                .poll(poll)
                .build();
    }

    /**
     * Конвертируем дто запроса в сущность Опроса
     *
     * @param dto Запрос
     * @return Опрос
     * @throws ValidationFailedException В случае ошибки валидации входных дат
     */
    public static Poll convertPollDTO(BasePollRequestDTO dto) throws ValidationFailedException {
        return Poll.builder()
                .startDate(checkDateFormat(dto.getStartDate(), "startDate"))
                .endDate(checkDateFormat(dto.getEndDate(), "endDate"))
                .active(dto.getActive())
                .name(dto.getName())
                .build();
    }

    /**
     * Конвертирует строку в дату, если значение не валидно,
     * бросается исключение
     *
     * @param value     валидируемое значение
     * @param paramName Название Параметра
     * @return дата
     */
    public static Date checkDateFormat(String value, String paramName)
            throws ValidationFailedException {
        try {
            TemporalAccessor accessor = DATE_FORMAT.parse(value);
            if (!DATE_FORMAT.format(accessor).equals(value)) {
                throw new ValidationFailedException(paramName);
            }
            return Date.from(LocalDate.from(accessor).atStartOfDay(ZoneId.systemDefault()).toInstant());
        } catch (DateTimeParseException e) {
            ValidationFailedException exception = new ValidationFailedException(paramName);
            exception.initCause(e);
            throw exception;
        }
    }
}
