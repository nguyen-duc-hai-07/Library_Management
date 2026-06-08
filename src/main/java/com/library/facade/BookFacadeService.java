package com.library.facade;

import com.library.dto.request.BookRequest;
import com.library.dto.response.BookResponse;

public interface BookFacadeService {
    BookResponse createBook(BookRequest bookRequest);

    BookResponse updateBook(int id, BookRequest bookRequest);
}
