package org.example.productservice.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.productservice.dto.request.CategoryAddBookRequest;
import org.example.productservice.dto.request.RemoveBookInCategoryRequest;
import org.example.productservice.dto.response.DetailCategoryResponse;
import org.example.productservice.exception.AppException;
import org.example.productservice.exception.ErrorCode;
import org.example.productservice.repository.BookCategoryRepository;
import org.example.productservice.repository.BookRepository;
import org.example.productservice.repository.CategoryRepository;
import org.example.productservice.service.BookCategoryService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BookCategoryServiceImpl implements BookCategoryService {
    CategoryRepository categoryRepository;
    BookCategoryRepository bookCategoryRepository;
    BookRepository bookRepository;

    @Override
    public void addBook(CategoryAddBookRequest request) {

        if (categoryRepository.countExistInIds(new HashSet<>(List.of(request.getCategoryId()))) != 1
                || bookRepository.countExistInIds(request.getBookIds()) != request.getBookIds().size())
            throw new AppException(ErrorCode.NOTFOUND_DATA);

        bookCategoryRepository.create(request.getCategoryId(), request.getBookIds(), Instant.now().toEpochMilli());
    }


    @Override
    public void removeBooks(RemoveBookInCategoryRequest request) {
        if (categoryRepository.countExistInIds(new HashSet<>(List.of(request.getCategoryId()))) != 1)
            throw new AppException(ErrorCode.NOTFOUND_DATA);

        if (bookCategoryRepository.removeBookInCategory(request.getCategoryId(), request.getBookIds()) == 0)
            throw new AppException(ErrorCode.UPDATE_FAIL);
    }

    @Override
    public DetailCategoryResponse detail(int id, int page) {
        return null;
    }
}
