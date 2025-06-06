package io.w4t3rcs.task.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.w4t3rcs.task.validation.FullName;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.List;

@ToString
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "books")
public class Book implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Length(min = 3)
    private String title;
    @NotBlank
    @FullName
    private String author;
    @Min(0)
    private Integer amount;
    @ToString.Exclude
    @JsonBackReference
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "member_books",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "member_id")
    )
    private List<Member> members;
}
