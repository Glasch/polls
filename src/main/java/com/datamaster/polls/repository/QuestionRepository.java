package com.datamaster.polls.repository;

import com.datamaster.polls.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface QuestionRepository extends JpaRepository<Question, UUID> {


    /**
     * Получение все вопросов в опросе с учетом сортировки
     *
     * @param pollId идентификатор опроса
     * @return Список вопросов
     */
    @Query(value = "SELECT q" +
            "       FROM Question q" +
            "       WHERE q.poll.id = :pollId" +
            "       ORDER BY q.sort ASC")
    List<Question> findAllByPollId(@Param("pollId") UUID pollId);

}
