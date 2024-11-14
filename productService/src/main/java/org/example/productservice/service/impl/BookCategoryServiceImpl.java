package org.example.productservice.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.productservice.dto.request.CategoryAddBookRequest;
import org.example.productservice.dto.request.RemoveBookInCategoryRequest;
import org.example.productservice.dto.response.DetailCategoryResponse;
import org.example.productservice.entity.Category;
import org.example.productservice.exception.AppException;
import org.example.productservice.exception.ErrorCode;
import org.example.productservice.repository.BookCategoryRepository;
import org.example.productservice.repository.BookRepository;
import org.example.productservice.repository.CategoryRepository;
import org.example.productservice.service.BookCategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
@Slf4j
public class BookCategoryServiceImpl implements BookCategoryService {
    CategoryRepository categoryRepository;
    BookCategoryRepository bookCategoryRepository;
    BookRepository bookRepository;

    @Override
    public void addBook(CategoryAddBookRequest request) {
        if (!validateExistAllCategoryId(new HashSet<>(List.of(request.getCategoryId())))
                || bookRepository.countExistInIds(request.getBookIds()) != request.getBookIds().size())
            throw new AppException(ErrorCode.NOTFOUND_DATA);

        bookCategoryRepository.create(request.getCategoryId(), request.getBookIds(), Instant.now().toEpochMilli());
    }


    @Override
    public void removeBooks(RemoveBookInCategoryRequest request) {

        if (!validateExistAllCategoryId(new HashSet<>(List.of(request.getCategoryId()))))
            throw new AppException(ErrorCode.NOTFOUND_DATA);

        if (bookCategoryRepository.removeBookInCategory(request.getCategoryId(), request.getBookIds()) == 0)
            throw new AppException(ErrorCode.UPDATE_FAIL);
    }

    @Override
    public DetailCategoryResponse detail(int id, int page) {
        int pageSize = 10;

        if (!validateExistAllCategoryId(new HashSet<>(List.of(id))))
            throw new AppException(ErrorCode.NOTFOUND_DATA);
        
        try {
            Category category = categoryRepository.findById(id);
            return new DetailCategoryResponse(category.getName(), bookRepository.getAllBookInCategory(id, page, pageSize));
        } catch (Exception e) {
            log.error("Book category service error: ", e);
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }

    }

    boolean validateExistAllCategoryId(Set<Integer> ids) {
        return categoryRepository.countExistInIds(ids) == ids.size();
    }
}
