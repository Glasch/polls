package com.datamaster.polls.service;

import com.datamaster.polls.dto.request.PollCreateDTO;
import com.datamaster.polls.dto.request.PollUpdateDTO;
import com.datamaster.polls.dto.request.QuestionCreateDTO;
import com.datamaster.polls.dto.request.QuestionUpdateDTO;
import com.datamaster.polls.dto.response.CreatedPollDTO;
import com.datamaster.polls.exception.ObjectNotFoundException;
import com.datamaster.polls.exception.ValidationFailedException;
import com.datamaster.polls.model.Question;
import com.datamaster.polls.repository.PollRepository;
import com.datamaster.polls.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.*;

import static org.junit.Assert.fail;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PollServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private PollService pollService;

    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @AfterMethod
    void clear(){
        questionRepository.deleteAll();
        pollRepository.deleteAll();
    }

    @Test(suiteName = "create",
            testName = "testCreatePollPositive",
            description = "Положительный кейс создания опроса")
    public void testCreatePollPositive() throws ValidationFailedException {
        PollCreateDTO dto = initPollCreateDTO("2020-01-20", "2020-01-21", Boolean.TRUE, initQuestionsCreateDTOList());
        pollService.createPoll(dto);
        Assert.assertEquals(pollRepository.count(), 1);
        Assert.assertEquals(questionRepository.count(), 3);
    }

    @Test(suiteName = "create",
            testName = "testCreatePollValidationExceptionDate",
            description = "Получение ошибки валидации даты",
            expectedExceptions = ValidationFailedException.class)
    public void testCreatePollValidationExceptionDate() throws Exception {
        PollCreateDTO dto = initPollCreateDTO("2020-01-20", "WrongDate", Boolean.TRUE, Collections.EMPTY_LIST);
        try {
            pollService.createPoll(dto);
        } catch (ValidationFailedException e) {
            logAndThrowException(e);
        }
    }

    @Test(suiteName = "delete",
            testName = "testDeletePollNotFoundException",
            description = "Получение ошибки, когда опрос для удаления не найден",
            expectedExceptions = ObjectNotFoundException.class)
    public void testDeletePollNotFoundException() throws Exception {
        try {
            pollService.deletePoll(UUID.randomUUID());
        } catch (ObjectNotFoundException e) {
            logAndThrowException(e);
        }
    }

    @Test(suiteName = "delete",
            testName = "testDeletePollPositive",
            description = "Позитивный кейс удаления опроса со всеми вопросами")
    public void testDeletePollPositive() throws Exception {
        PollCreateDTO dto = initPollCreateDTO("2020-01-20", "2020-01-21", Boolean.TRUE, initQuestionsCreateDTOList());
        CreatedPollDTO pollFirst = pollService.createPoll(dto);
        pollService.createPoll(dto);
        long beforeDelete = pollRepository.count();
        pollService.deletePoll(pollFirst.getId());
        long afterDelete = pollRepository.count();
        Assert.assertEquals(beforeDelete - afterDelete, 1L);
    }

    @Test(suiteName = "update",
            testName = "testUpdatePollPositive",
            description = "Позитивный кейс обновления опроса")
    public void testUpdatePollPositive() throws Exception {
        /* Создаем опрос который будем обновлять */
        PollCreateDTO dto = initPollCreateDTO("2020-01-20", "2020-01-21", Boolean.TRUE, initQuestionsCreateDTOList());
        CreatedPollDTO pollFirst = pollService.createPoll(dto);
        /* Получаем айди любого вопроса который надо обновить а не создать новый */
        UUID id = questionRepository.findAll().iterator().next().getId();
        /* Собираем объект для передачи запроса */
        PollUpdateDTO updateDTO = initPollUpdateDTO(pollFirst.getId() ,"2020-01-20", "2020-01-21", Boolean.FALSE, initQuestionUpdateDTOList(id));
        /* Обновляем */
        pollService.updatePoll(updateDTO);
        /* Проверяем что опрос стал неактивным */
        if (pollRepository.findAll().iterator().next().getActive()) {
            fail();
        }
        /* Количество опросов должно стать 3 + 2 = 5 в т.ч. 1 обновленный */
        Assert.assertEquals(questionRepository.count(), 5);
        Optional<Question> byId = questionRepository.findById(id);
        if (byId.isEmpty()) {
            fail();
        }
        Assert.assertEquals(byId.get().getContent(), "Test Update new");
    }

    @Test(suiteName = "update",
            testName = "testUpdatePollNotFound",
            description = "Обновление опроса которого нет",
            expectedExceptions = ObjectNotFoundException.class)
    public void testUpdatePollNotFound() throws Exception {
        PollUpdateDTO updateDTO = initPollUpdateDTO(UUID.randomUUID() ,"2020-01-20", "2020-01-21", Boolean.FALSE, null);
        try {
            pollService.updatePoll(updateDTO);
        } catch (ObjectNotFoundException e) {
            logAndThrowException(e);
        }
    }


    private PollCreateDTO initPollCreateDTO(String startDate, String endDate,
                                            Boolean active, List<QuestionCreateDTO> questions) {
        PollCreateDTO dto = new PollCreateDTO();
        dto.setStartDate(startDate);
        dto.setEndDate(endDate);
        dto.setActive(active);
        dto.setQuestions(questions);
        return dto;
    }

    private PollUpdateDTO initPollUpdateDTO(UUID id ,String startDate, String endDate,
                                            Boolean active, List<QuestionUpdateDTO> questions) {
        PollUpdateDTO dto = new PollUpdateDTO();
        dto.setId(id);
        dto.setStartDate(startDate);
        dto.setEndDate(endDate);
        dto.setActive(active);
        dto.setQuestions(questions);
        return dto;
    }

    private List<QuestionCreateDTO> initQuestionsCreateDTOList() {
        List<QuestionCreateDTO> res = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            QuestionCreateDTO dto = new QuestionCreateDTO();
            dto.setContent("Test" + i);
            dto.setSort(i);
            res.add(dto);
        }
        return res;
    }

    private List<QuestionUpdateDTO> initQuestionUpdateDTOList(UUID oldQuestionUUID) {
        List<QuestionUpdateDTO> res = new ArrayList<>();
        boolean first = true;
        for (int i = 0; i < 3; i++) {
            QuestionUpdateDTO dto = new QuestionUpdateDTO();
            dto.setContent("Test Update" + i);
            if (first) {
                dto.setId(oldQuestionUUID);
                dto.setContent("Test Update new");
                first = false;
            }
            dto.setSort(i);
            res.add(dto);
        }
        return res;
    }

    private void logAndThrowException(Exception e) throws Exception {
        logger.info("message: " + e.getMessage());
        throw e;
    }
}

