package io.w4t3rcs.task.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class MemberRequest implements Serializable {
    private String name;
    private List<BookRequest> books;
}
