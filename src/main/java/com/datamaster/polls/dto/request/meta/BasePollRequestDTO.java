package com.datamaster.polls.dto.request.meta;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public abstract class BasePollRequestDTO implements Serializable {

    private static final long serialVersionUID = -5091888205426630224L;

    /**
     * Наименование опроса
     */
    @ApiModelProperty(notes = "Наименование опроса", required = true)
    private String name;

    /**
     * Дата начала
     */

    @ApiModelProperty(notes = "Дата начала", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private String startDate;

    /**
     * Дата окончания
     */
    @ApiModelProperty(notes = "Дата окончания", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private String endDate;

    /**
     * Активность
     */
    @ApiModelProperty(notes = "Активность", required = true)
    private Boolean active;

}
