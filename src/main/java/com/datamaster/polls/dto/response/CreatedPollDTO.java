package com.datamaster.polls.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@ApiModel(description = "Объект с идентификатором созданного опроса")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreatedPollDTO implements Serializable {

    private static final long serialVersionUID = 3329616890634203062L;

    /**
     * Идентификатор созданного опроса
     */
    @ApiModelProperty(notes = "Идентификатор созданного опроса")
    private UUID id;
}
