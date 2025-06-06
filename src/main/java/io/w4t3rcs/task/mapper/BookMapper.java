package io.w4t3rcs.task.mapper;

import io.w4t3rcs.task.config.MapStructConfig;
import io.w4t3rcs.task.dto.BookRequest;
import io.w4t3rcs.task.dto.BookResponse;
import io.w4t3rcs.task.entity.Book;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface BookMapper {
    Book toBook(BookRequest request);

    BookResponse toBookResponse(Book book);
}
