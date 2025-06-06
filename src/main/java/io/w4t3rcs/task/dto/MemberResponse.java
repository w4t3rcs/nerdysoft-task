package io.w4t3rcs.task.dto;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
public class MemberResponse implements Serializable {
    private Long id;
    private String name;
    private LocalDate membershipDate;
    @JsonIncludeProperties({"id", "title", "author"})
    private List<BookResponse> books;
}
