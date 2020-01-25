package com.datamaster.polls.service;

import com.datamaster.polls.dto.request.PollCreateDTO;
import com.datamaster.polls.dto.request.QuestionCreateDTO;
import com.datamaster.polls.exception.ValidationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.List;


//@SpringBootTest
@DataJpaTest
@AutoConfigureTestDatabase
public class PollServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private PollService pollService;

    @Test
    public void testCreatePollValidationException() throws ValidationFailedException {
        PollCreateDTO dto = initPollCreateDTO("2020-01-20", "WrongDateFormat", Boolean.TRUE, Collections.EMPTY_LIST);
        logger.info("IT works!");
//        pollService.createPoll(dto);
    }


    private PollCreateDTO initPollCreateDTO(String startDate, String endDate,
                                            Boolean active, List<QuestionCreateDTO> questions){
        PollCreateDTO dto = new PollCreateDTO();
        dto.setStartDate(startDate);
        dto.setEndDate(endDate);
        dto.setActive(active);
        dto.setQuestions(questions);
        return dto;
    }
}

