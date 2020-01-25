package com.datamaster.polls.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@ApiModel(description = "Ошибка")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDTO implements Serializable {

    private static final long serialVersionUID = 5337117756637337292L;

    /**
     * Наименование ошибки
     */
    @ApiModelProperty(notes = "Тип ошибка или сообщение")
    private String name;


    /**
     * Сообщение
     */
    @ApiModelProperty(notes = "Сообщение")
    private String message;

    public ErrorDTO(Exception ex) {
        this.name = ex.getClass().getSimpleName();
        this.message = ex.getMessage();
    }
}

