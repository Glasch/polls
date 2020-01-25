package com.datamaster.polls.service;

import com.datamaster.polls.dto.request.PollCreateDTO;
import com.datamaster.polls.dto.request.PollUpdateDTO;
import com.datamaster.polls.dto.request.QuestionCreateDTO;
import com.datamaster.polls.dto.request.QuestionUpdateDTO;
import com.datamaster.polls.dto.response.CreatedPollDTO;
import com.datamaster.polls.dto.response.PageDTO;
import com.datamaster.polls.dto.response.PollDTO;
import com.datamaster.polls.dto.response.SuccessDTO;
import com.datamaster.polls.exception.ObjectNotFoundException;
import com.datamaster.polls.exception.ValidationFailedException;
import com.datamaster.polls.model.Poll;
import com.datamaster.polls.model.Question;
import com.datamaster.polls.model.enums.PollsSortType;
import com.datamaster.polls.repository.PollRepository;
import com.datamaster.polls.repository.specification.PollSpecification;
import com.datamaster.polls.utils.Converter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class PollService {

    private final PollRepository repository;
    private final QuestionService questionService;

    @Autowired
    public PollService(PollRepository repository, QuestionService questionService) {
        this.repository = repository;
        this.questionService = questionService;
    }

    /**
     * Постраничное получение списка опросов и вопросов по заданным фильтрам
     *
     * @param name     фильтр по наименованию
     * @param date     фильтр по дате
     * @param isActive фильтр по активности
     * @param sortType Тип сортировки опросов
     * @param pageNum  Номер страницы
     * @param pageSize Размер страницы
     * @return Страница с данными
     * @throws ValidationFailedException Ошибка валидации формата входных дат
     */
    public PageDTO<PollDTO> findAllPolls(String name, String date, Boolean isActive, PollsSortType sortType, int pageNum, int pageSize) throws ValidationFailedException {

        /* Базовая инициация спецификации */
        Specification<Poll> spec = Specification.where(null);

        /* Подготовка входных строк */
        String actualName = StringUtils.trimToNull(name);
        String actualDateString = StringUtils.trimToNull(date);

        /* Фильтр по дате если представлена */
        if (!StringUtils.isEmpty(actualDateString)) {
            Date actualDate = Converter.checkDateFormat(actualDateString, "date");
            spec = spec.and(PollSpecification.afterStartDate(actualDate));
            spec = spec.and(PollSpecification.beforeEndDate(actualDate));
        }

        /* Фильтр по наименванию если представлено */
        if (!StringUtils.isEmpty(actualName)) {
            spec = spec.and(PollSpecification.likeName(actualName));
        }

        /* Фильтр по активности если представлена */
        if (Objects.nonNull(isActive)) {
            spec = spec.and(PollSpecification.hasActive(isActive));
        }

        /* Формируем постраничный запрос на Опросы */
        Page<Poll> page;
        PageRequest request = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.ASC, "name"));
        if (PollsSortType.NAME.equals(sortType)) {
            page = repository.findAll(spec, request);
        } else {
            page = repository.findAll(spec, request);
        }

        /* Формируем запрос на отсортированные вопросы и подготовливаем ответ */
        List<PollDTO> content = page.stream()
                .map(poll -> new PollDTO(poll, questionService.findAllByPollId(poll.getId())))
                .collect(Collectors.toList());
        return new PageDTO<PollDTO>(page.getTotalPages(), page.getTotalElements(), page.isLast(), page.isFirst(), pageSize, pageNum, content);
    }

    /**
     * Создание нового опроса
     *
     * @param dto Данные для создания опроса
     * @return Объект с идентификатором нового опроса
     * @throws ValidationFailedException Ошибка валидации формата входных дат
     */
    public CreatedPollDTO createPoll(PollCreateDTO dto) throws ValidationFailedException {
        Poll poll = Converter.convertPollDTO(dto);
        List<Question> questions = dto.getQuestions()
                .parallelStream()
                .map((QuestionCreateDTO questionCreateDTO) -> Converter.convertQuestionDTO(questionCreateDTO, poll))
                .collect(Collectors.toList());
        poll.setQuestions(questions);
        repository.save(poll);
        questionService.saveAll(questions);
        return new CreatedPollDTO(poll.getId());
    }

    /**
     * Обновление Опроса
     *
     * @param dto Данные для обновления опроса
     * @return Флаг успешной процедуры обновления
     * @throws ObjectNotFoundException   Если объет для обновления не найден по идентификатору
     * @throws ValidationFailedException Ошибко валидации формата входных дат
     */
    @Transactional
    public SuccessDTO updatePoll(PollUpdateDTO dto) throws ObjectNotFoundException, ValidationFailedException {
        /* Валидация дат на входе */
        Date endDate = Converter.checkDateFormat(dto.getEndDate(), "endDate");
        Date startDate = Converter.checkDateFormat(dto.getEndDate(), "startDate");
        /* Обновляем опрос если нашли, иначе ошибка */
        Poll poll = checkPoll(dto.getId());
        poll.setName(dto.getName());
        poll.setEndDate(endDate);
        poll.setStartDate(startDate);
        poll.setActive(dto.getActive());
        repository.save(poll);

        List<Question> questionsToSave = new ArrayList<>();
        for (QuestionUpdateDTO questionUpdateDTO : dto.getQuestions()) {
            if (Objects.isNull(questionUpdateDTO.getId())) {
                /* Id вопроса не передан --> Считаем его новым --> Записываем новый вопрос */
                Question newQuestion = Converter.convertQuestionDTO(questionUpdateDTO, poll);
                questionsToSave.add(newQuestion);
                continue;
            }
            /* Id передан, если вопрос по нему не найден --> Ошибка(Пользователю необходимо проверить запрос) */
            Question question = questionService.findById(questionUpdateDTO.getId());

            /* Id передан --> Нашли вопрос --> Обновляем */
            question.setContent(questionUpdateDTO.getContent());
            question.setSort(questionUpdateDTO.getSort());
            questionsToSave.add(question);
        }
        questionService.saveAll(questionsToSave);
        return SuccessDTO.getInstance();
    }

    /**
     * Удаление опроса
     *
     * @param id Идентификатор опроса
     * @return Флаг успешной процедуры удаления
     * @throws ObjectNotFoundException Если опрос не найден по идентификатору
     */
    public SuccessDTO deletePoll(UUID id) throws ObjectNotFoundException {
        checkPoll(id);
        repository.deleteById(id);
        return SuccessDTO.getInstance();
    }

    /**
     * Поиск Опроса по идентификатору
     *
     * @param id Идентификатор
     * @return Опрос
     * @throws ObjectNotFoundException если не найден
     */
    private Poll checkPoll(UUID id) throws ObjectNotFoundException {
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(Poll.class, id));
    }

    /**
     * Проверка дат на формат и базовую логику
     *
     * @param startDate дата начала опроса
     * @param endDate   дата окончания опроса
     * @throws ValidationFailedException если проверка не пройдена
     */
    private void checkDates(String startDate, String endDate) throws ValidationFailedException {
        Date start = Converter.checkDateFormat(startDate, "startDate");
        Date end = Converter.checkDateFormat(endDate, "endDate");
        if (start.after(end)) {
            throw new ValidationFailedException("start date is after endDate");
        }
    }
}
