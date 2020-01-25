package com.datamaster.polls.dto.request;

import com.datamaster.polls.dto.request.meta.BaseQuestionRequestDTO;
import io.swagger.annotations.ApiModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@ApiModel(description = "Данные для создания вопроса")
public class QuestionCreateDTO extends BaseQuestionRequestDTO {}
