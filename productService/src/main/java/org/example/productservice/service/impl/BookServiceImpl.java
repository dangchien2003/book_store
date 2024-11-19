package org.example.productservice.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.productservice.dto.FindBook;
import org.example.productservice.dto.request.BookCreationRequest;
import org.example.productservice.dto.request.BookUpdateRequest;
import org.example.productservice.dto.request.GetDetailListBookRequest;
import org.example.productservice.dto.response.*;
import org.example.productservice.entity.Book;
import org.example.productservice.enums.BookStatus;
import org.example.productservice.exception.AppException;
import org.example.productservice.exception.ErrorCode;
import org.example.productservice.mapper.BookMapper;
import org.example.productservice.repository.AuthorRepository;
import org.example.productservice.repository.BookCategoryRepository;
import org.example.productservice.repository.BookRepository;
import org.example.productservice.repository.PublisherRepository;
import org.example.productservice.service.BookService;
import org.example.productservice.utils.ENumUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BookServiceImpl implements BookService {

    BookRepository bookRepository;
    AuthorRepository authorRepository;
    PublisherRepository publisherRepository;
    BookCategoryRepository bookCategoryRepository;
    BookMapper bookMapper;

    static final int PAGE_SIZE_FOR_MANAGER_FIND = 2;

    @Override
    public BookCreationResponse create(BookCreationRequest request) {
        Book book = bookMapper.toBook(request);

        validateAuthor(book.getAuthorId());
        validatePublisher(book.getPublisherId());

        BookStatus status;
        try {
            status = ENumUtils.getType(BookStatus.class, request.getStatusCode().toUpperCase(Locale.ROOT));
        } catch (AppException e) {
            throw new AppException(ErrorCode.NOT_FOUND_STATUS_CODE);
        }

        book.setStatusCode(status.name());
        book.setSize(request.getSize());
        book.onCreate();

        Long id = bookRepository.create(book);
        if (Objects.isNull(id))
            throw new AppException(ErrorCode.UPDATE_FAIL);

        return BookCreationResponse.builder()
                .id(id)
                .build();
    }

    void validateAuthor(Long authorId) {
        if (Objects.isNull(authorId))
            return;

        if (!authorRepository.existById(authorId))
            throw new AppException(ErrorCode.AUTHOR_NOT_FOUND);
    }

    void validatePublisher(Integer publisherId) {
        if (Objects.isNull(publisherId))
            return;

        if (!publisherRepository.existById(publisherId))
            throw new AppException(ErrorCode.PUBLISHER_NOT_FOUND);
    }

    @Override
    public BookUpdateResponse update(BookUpdateRequest request) {
        return null;
    }

    @Override
    public List<ManagerFindBookResponse> find(String name, Integer category, Integer publisher, Long author, int numberPage) {

        List<Long> filterInBookIds = null;


        try {
            if (!Objects.isNull(category)) {
                filterInBookIds = bookCategoryRepository.getAllBookByCategory(category, numberPage, PAGE_SIZE_FOR_MANAGER_FIND);
                if (filterInBookIds.isEmpty())
                    return new ArrayList<>();
            }

            FindBook filter = FindBook.builder()
                    .name(name)
                    .author(author)
                    .publisher(publisher)
                    .bookIds(filterInBookIds)
                    .numberPage(numberPage)
                    .pageSize(PAGE_SIZE_FOR_MANAGER_FIND)
                    .build();

            return bookRepository.find(filter);
        } catch (Exception e) {
            log.error("Book repository error: ", e);
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    @Override
    public ManagerBookDetailResponse mGetDetail(long bookId) {
        ManagerBookDetailResponse detail;
        try {
            detail = bookRepository.getDetail(bookId);
        } catch (EmptyResultDataAccessException e) {
            throw new AppException(ErrorCode.NOTFOUND_DATA);
        } catch (Exception e) {
            log.error("Book repository error: ", e);
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }

        detail.setBookSize(Book.getBookSize(detail.getSize()));
        detail.setSize(null);
        return detail;
    }

    @Override
    public List<DetailInternal> getDetailListBook(GetDetailListBookRequest request) {
        if (request.getBookIds().isEmpty())
            throw new AppException(ErrorCode.LIST_BOOK_EMPTY);

        try {
            return bookRepository.getListDetail(request.getBookIds().stream().toList());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        } catch (Exception e) {
            log.error("book repository getListDetail error: ", e);
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }
}
