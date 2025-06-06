package io.w4t3rcs.task.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookRequest implements Serializable {
    private String title;
    private String author;
    private Integer amount;
}
