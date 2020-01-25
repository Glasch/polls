package com.datamaster.polls.service;

import com.datamaster.polls.exception.ObjectNotFoundException;
import com.datamaster.polls.model.Question;
import com.datamaster.polls.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class QuestionService {
    private final QuestionRepository repository;

    public QuestionService(QuestionRepository questionRepository) {
        this.repository = questionRepository;
    }

    /**
     * Сохраняем список вопросов в базу
     *
     * @param questions Список вопросов
     * @return Список сохраненных вопросов
     */
    public List<Question> saveAll(List<Question> questions) {
        return repository.saveAll(questions);
    }

    /**
     * Поиск списка всех вопросов по идентикатору опроса
     *
     * @param pollID Идентификатор опроса
     * @return Список вопросов
     */
    public List<Question> findAllByPollId(UUID pollID) {
        return repository.findAllByPollId(pollID);
    }

    /**
     * Поиск вопроса по идентификатору, иначе ошибка
     *
     * @param id идентификатор для поиска
     * @return Вопрос
     * @throws ObjectNotFoundException если не найден
     */
    public Question findById(UUID id) throws ObjectNotFoundException {
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(Question.class, id));
    }
}
