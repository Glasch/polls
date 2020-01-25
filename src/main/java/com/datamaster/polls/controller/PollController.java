package com.datamaster.polls.controller;

import com.datamaster.polls.dto.request.PollCreateDTO;
import com.datamaster.polls.dto.request.PollUpdateDTO;
import com.datamaster.polls.dto.request.meta.BasePollRequestDTO;
import com.datamaster.polls.dto.response.CreatedPollDTO;
import com.datamaster.polls.dto.response.PageDTO;
import com.datamaster.polls.dto.response.PollDTO;
import com.datamaster.polls.dto.response.SuccessDTO;
import com.datamaster.polls.exception.ObjectNotFoundException;
import com.datamaster.polls.exception.ValidationFailedException;
import com.datamaster.polls.model.enums.PollsSortType;
import com.datamaster.polls.service.PollService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping(value = "/poll")
public class PollController {

    private final PollService service;
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Autowired
    public PollController(PollService service) {
        this.service = service;
    }

    @ApiOperation(value = "Получение списка всех опросов ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список найденных опросов", response = PageDTO.class),
            @ApiResponse(code = 400, message = "Ошибка валидации обязательного параметра sortType", response = ValidationFailedException.class),
            @ApiResponse(code = 400, message = "Ошибка валидации формата входящей даты", response = ValidationFailedException.class)
    })
    @GetMapping("/list/all")
    public PageDTO<PollDTO> findAllPolls(@ApiParam(name = "name", value = "Фильтр по наименованию") @RequestParam(value = "name", required = false) String name,
                                         @ApiParam(name = "date", value = "Фильтр по дате") @RequestParam(value = "date", required = false) String date,
                                         @ApiParam(name = "active", value = "Фильтр по флагу активности") @RequestParam(value = "active", required = false) Boolean isActive,
                                         @ApiParam(name = "sortType", value = "Тип сортировки(name или date)") @RequestParam(value = "sortType", required = false) String sortType,
                                         @ApiParam(name = "pageNum", value = "Номер страницы") @RequestParam(value = "pageNum", required = false, defaultValue = "0") int pageNum,
                                         @ApiParam(name = "pageSize", value = "Размер страницы") @RequestParam(value = "pageSize", required = false, defaultValue = "3") int pageSize
    ) throws ValidationFailedException {
        PollsSortType pollsSortType;
        if ("name".equals(sortType)) {
            pollsSortType = PollsSortType.NAME;
        } else if ("date".equals(sortType)) {
            pollsSortType = PollsSortType.START_DATE;
        } else {
            throw new ValidationFailedException("sortType");
        }

        return service.findAllPolls(name, date, isActive, pollsSortType, pageNum, pageSize);
    }

    @ApiOperation(value = "Создание опроса")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Флаг успешного создания опроса", response = SuccessDTO.class),
            @ApiResponse(code = 400, message = "Не переданы обязательные параметры", response = ValidationFailedException.class)
    })
    @PostMapping("/create")
    public CreatedPollDTO createPoll(@RequestBody(required = false) PollCreateDTO dto) throws Exception {
        checkRequiredParams(dto);
        return service.createPoll(dto);
    }

    @ApiOperation(value = "Обновление опроса")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Флаг успешного обновление опроса", response = SuccessDTO.class),
            @ApiResponse(code = 404, message = "Объект опроса не найден", response = ObjectNotFoundException.class),
    })
    @PutMapping("/update")
    public SuccessDTO updatePoll(@RequestBody(required = false) PollUpdateDTO dto) throws ObjectNotFoundException, ValidationFailedException {
        checkRequiredParams(dto);
        return service.updatePoll(dto);
    }

    @ApiOperation(value = "Удаление опроса")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Флаг успешного удаления опроса", response = SuccessDTO.class),
            @ApiResponse(code = 404, message = "Объект опроса не найден", response = ObjectNotFoundException.class),
    })
    @DeleteMapping("/delete/{id}")
    public SuccessDTO deletePoll(
            @ApiParam(name = "id", value = "Идентификатор опроса", required = true) @PathVariable(value = "id") UUID id) throws ObjectNotFoundException {
        return service.deletePoll(id);
    }

    /**
     * Проверка обязательных входных параметров
     *
     * @param dto ДТО из запроса
     * @throws ValidationFailedException Если процедура валидации не пройдена
     */
    private void checkRequiredParams(BasePollRequestDTO dto) throws ValidationFailedException {
        StringBuilder builder = new StringBuilder();
        if (Objects.isNull(dto)) {
            throw new ValidationFailedException("method body");
        }
        if (StringUtils.isBlank(dto.getStartDate())) {
            builder.append("startDate, ");
        }
        if (StringUtils.isBlank(dto.getEndDate())) {
            builder.append("endDate, ");
        }
        if (StringUtils.isBlank(dto.getName())) {
            builder.append("name, ");
        }
        if (Objects.isNull(dto.getActive())) {
            builder.append("active, ");
        }
        if (dto instanceof PollCreateDTO) {
            if (Objects.isNull(((PollCreateDTO) dto).getQuestions())) {
                builder.append("questions list, ");
            }
        }
        if (dto instanceof PollUpdateDTO) {
            if (Objects.isNull(((PollUpdateDTO) dto).getId())) {
                builder.append("poll id, ");
            }
        }
        if (builder.length() > 0) {
            builder.delete(builder.length() - 2, builder.length() - 1);
            throw new ValidationFailedException(builder.toString());
        }
    }
}
