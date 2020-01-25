package com.datamaster.polls.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.io.Serializable;
import java.util.UUID;

/**
 * Дто для передачи флага успешной операции
 */
@Getter
@ApiModel(description = "Флаг успешной операции")
public class SuccessDTO implements Serializable {

    private static final long serialVersionUID = -6166631961439236180L;
    private volatile static SuccessDTO instance;

    private SuccessDTO(boolean success) {
        this.success = success;
    }

    public static SuccessDTO getInstance() {
        if (instance == null) {
            synchronized (SuccessDTO.class) {
                if (instance == null) {
                    instance = new SuccessDTO(true);
                }
            }
        }
        return instance;
    }

    /**
     * Идентификатор ПП в БС
     */
    @ApiModelProperty(notes = "Успешность операции")
    private boolean success;
}
