package com.datamaster.polls.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ApiModel(description = "Постраничный вывод")
public class PageDTO<T> implements Serializable {

    /**
     * Всего страниц
     */
    @ApiModelProperty(notes = "Всего страниц")
    private int totalPages;

    /**
     * Всего элементов
     */
    @ApiModelProperty(notes = "Всего элементов")
    private long totalElements;

    /**
     * Признак последней страницы
     */
    @ApiModelProperty(notes = "Признак последней страницы")
    private boolean last;

    /**
     * Признак первой страницы
     */
    @ApiModelProperty(notes = "Признак первой страницы")
    private boolean first;

    /**
     * Размер страницы
     */
    @ApiModelProperty(notes = "Размер страницы")
    private int size;

    /**
     * Номер страницы
     */
    @ApiModelProperty(notes = "Номер страницы")
    private int number;

    /**
     * Данные на странице
     */
    @ApiModelProperty(notes = "Данные на странице")
    private List<T> content;

    public PageDTO(int totalPages, long totalElements, boolean last, boolean first, int size, int number, List<T> content) {
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.last = last;
        this.first = first;
        this.size = size;
        this.number = number;
        this.content = content;
    }
}
