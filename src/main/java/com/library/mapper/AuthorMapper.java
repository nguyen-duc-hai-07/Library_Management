package com.library.mapper;

import com.library.dto.request.AuthorRequest;
import com.library.dto.response.AuthorResponse;
import com.library.model.Author;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    AuthorResponse toResponse(Author author);

    Author toEntity(AuthorRequest authorRequest);
}
