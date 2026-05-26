package com.library.repository;

import com.library.dto.response.AuthorResponse;
import com.library.dto.response.BookResponse;
import com.library.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
    @Query("""
            SELECT new com.library.dto.response.AuthorResponse(
                        a.id,
                        a.name,
                        a.year,
                        a.description
                        )
            FROM Author a
            WHERE a.id = :id AND a.isDeleted = false
            """)
    Optional<AuthorResponse> findByIdAndIsDeletedFalse(@Param("id") int id); //dùng cho find by id
    Optional<Author> findEntityById(int id); // dùng cho update và delete

    @Query("""
          SELECT new com.library.dto.response.AuthorResponse(
                    a.id,
                    a.name,
                    a.year,
                    a.description
                    )
          FROM Author a
          WHERE a.isDeleted = false
                    AND a.name ILIKE %:keyword%
          """)
    List<AuthorResponse> findWithFilter(@Param("keyword") String keyword, Pageable pageable);

    @Modifying
    @Query("UPDATE Author a SET a.isDeleted = true WHERE a.id = :id AND a.isDeleted = false")
    void softDelete(@Param("id") int id);


    @Query("""
           SELECT new com.library.dto.response.BookResponse(
                      b.id,
                      b.title,
                      b.isbn,
                      b.author.id,
                      a.name,
                      b.category.id,
                      c.name
                                 )
           FROM Book b
           JOIN b.author a 
           JOIN b.category c
           WHERE b.author.id = :authorId
           AND b.isDeleted = false
           AND a.isDeleted = false
           AND c.isDeleted = false
           """)
    List<BookResponse> findBooksByAuthorId(@Param("authorId") int authorId);
}
