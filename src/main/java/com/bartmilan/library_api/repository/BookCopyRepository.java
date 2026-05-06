package com.bartmilan.library_api.repository;

import com.bartmilan.library_api.model.BookCopy;
import com.bartmilan.library_api.model.enums.BookCopyStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookCopyRepository extends JpaRepository<BookCopy, Long> {

    List<BookCopy> findByBook_Id(Long bookId);

    List<BookCopy> findByStatus(BookCopyStatus status);

    List<BookCopy> findByBook_IdAndStatus(Long bookId, BookCopyStatus status);

    boolean existByBook_IdAndStatus(Long bookId, BookCopyStatus status);
}
