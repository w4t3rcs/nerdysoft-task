package io.w4t3rcs.task.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class BookResponse implements Serializable {
    private Long id;
    private String title;
    private String author;
    private Integer amount;
}
