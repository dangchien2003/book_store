package org.example.productservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.productservice.dto.FindBook;
import org.example.productservice.dto.request.*;
import org.example.productservice.dto.response.*;
import org.example.productservice.entity.Book;
import org.example.productservice.enums.BookStatus;
import org.example.productservice.enums.PrefixCache;
import org.example.productservice.exception.AppException;
import org.example.productservice.exception.ErrorCode;
import org.example.productservice.mapper.BookMapper;
import org.example.productservice.repository.AuthorRepository;
import org.example.productservice.repository.BookCategoryRepository;
import org.example.productservice.repository.BookRepository;
import org.example.productservice.repository.PublisherRepository;
import org.example.productservice.service.BookService;
import org.example.productservice.service.CloudinaryService;
import org.example.productservice.service.RedisService;
import org.example.productservice.utils.ENumUtils;
import org.example.productservice.utils.ScheduleUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.Instant;
import java.util.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BookServiceImpl implements BookService {

    BookRepository bookRepository;
    AuthorRepository authorRepository;
    PublisherRepository publisherRepository;
    BookCategoryRepository bookCategoryRepository;
    RedisService redisService;
    CloudinaryService cloudinaryService;
    BookMapper bookMapper;
    ObjectMapper objectMapper;

    static final int PAGE_SIZE_FOR_MANAGER_FIND = 2;

    @Override
    public void removeImage(RemoveImageBookRequest request) {

        validateEditImage(request.getType());

        if (bookRepository.countExistInIds(new HashSet<>(List.of(request.getBookId()))) == 0)
            throw new AppException(ErrorCode.NOTFOUND_DATA);

        ImageBook imageBook;
        try {
            imageBook = bookRepository.getImageBook(request.getBookId());
        } catch (Exception e) {
            log.error("bookRepository.getImageBook error: ", e);
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }

        boolean updateSuccess;
        String imageRemove;
        if (request.getType().equalsIgnoreCase("main")) {
            if (imageBook.getMainImage() == null) {
                throw new AppException("Ảnh không tồn tại");
            }

            updateSuccess = bookRepository.removeMainImage(request.getBookId(), Instant.now().toEpochMilli());
            imageRemove = imageBook.getMainImage();
        } else {
            if (request.getIndex() == null || request.getIndex() < 0) {
                throw new AppException("Vị trí ảnh không hợp lệ");
            }

            List<String> childImages = imageBook.convertChildImage();
            if (childImages.size() - 1 < request.getIndex()) {
                throw new AppException("Ảnh không tồn tại");
            }

            imageRemove = childImages.get(request.getIndex());
            childImages.remove((int) request.getIndex());
            String newOtherImage = imageBook.convertChildImageToString();

            updateSuccess = bookRepository.updateChildImageBook(
                    newOtherImage,
                    request.getBookId(),
                    Instant.now().toEpochMilli());
        }

        if (!updateSuccess)
            throw new AppException(ErrorCode.UPDATE_FAIL);

        ScheduleUtils.addScheduleCacheProduct(request.getBookId());
        ScheduleUtils.addScheduleRemoveImage(imageRemove);
    }

    void validateEditImage(String type) {
        if (type == null
                || (!type.equalsIgnoreCase("main") && !type.equalsIgnoreCase("child")))
            throw new AppException("Đối tượng không xác định");
    }

    @Override
    public BookUploadImageResponse uploadImage(BookUploadImageRequest request) {
        long bookId;
        try {
            bookId = Long.parseLong(request.getBookId());
        } catch (Exception e) {
            throw new AppException(ErrorCode.INVALID_DATA);
        }

        validateEditImage(request.getType());

        if (bookRepository.countExistInIds(new HashSet<>(List.of(bookId))) == 0)
            throw new AppException(ErrorCode.NOTFOUND_DATA);

        CloudinaryUploadResponse uploadResponse;
        try {
            uploadResponse = objectMapper.convertValue(
                    cloudinaryService.uploadFile(request.getFile(), "book_store/book")
                    , CloudinaryUploadResponse.class);
        } catch (IOException e) {
            log.error("upload file error: ", e);
            throw new AppException("Lỗi tải tệp tin");
        }

        ImageBook imageBook;
        try {
            imageBook = bookRepository.getImageBook(bookId);
        } catch (Exception e) {
            log.error("bookRepository.getImageBook error: ", e);
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }

        boolean uploadSuccess;
        if (request.getType().equalsIgnoreCase("main")) {
            uploadSuccess = bookRepository.updateMainImageBook(uploadResponse.getSecure_url(), bookId, Instant.now().toEpochMilli());

            if (imageBook.getMainImage() != null) {
                ScheduleUtils.addScheduleRemoveImage(imageBook.getMainImage());
            }
        } else {
            List<String> childImage = imageBook.convertChildImage();
            childImage.add(uploadResponse.getSecure_url());
            String otherImage = imageBook.convertChildImageToString();

            uploadSuccess = bookRepository.updateChildImageBook(otherImage, bookId, Instant.now().toEpochMilli());
        }

        if (!uploadSuccess)
            throw new AppException(ErrorCode.UPDATE_FAIL);

        ScheduleUtils.addScheduleCacheProduct(bookId);

        return BookUploadImageResponse.builder()
                .url(uploadResponse.getSecure_url())
                .build();
    }

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

        ScheduleUtils.addScheduleCacheProduct(id);

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
    public void update(BookUpdateRequest request) {
        if (bookRepository.countExistInIds(new HashSet<>(List.of(request.getId()))) == 0)
            throw new AppException(ErrorCode.NOTFOUND_DATA);

        Book book = bookMapper.toBook(request);
        book.setSize(request.getBookSize());
        book.setModifiedAt(Instant.now().toEpochMilli());

        if (bookRepository.updateBookDetail(book) == 0) {
            throw new AppException(ErrorCode.UPDATE_FAIL);
        }

        ScheduleUtils.addScheduleCacheProduct(book.getId());
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
        Book book = redisService.get(PrefixCache.BOOK_.name() + bookId);
        if (book != null) {
            detail = bookMapper.toManagerBookDetailResponse(book);
        } else {
            try {
                detail = bookRepository.getDetail(bookId);
            } catch (EmptyResultDataAccessException e) {
                throw new AppException(ErrorCode.NOTFOUND_DATA);
            } catch (Exception e) {
                log.error("Book repository error: ", e);
                throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
            }
            ScheduleUtils.addScheduleCacheProduct(detail.getId());
        }

        if (detail.getOtherImage() != null) {
            detail.setChildImages(List.of(detail.getOtherImage().split("///")));
        }

        detail.setBookSize(Book.getBookSize(detail.getSize()));
        detail.setOtherImage(null);
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

    @Override
    @Transactional
    public List<QuantityBookAfterMinusResponse> minusQuantityBooks(List<BookMinusQuantityRequest> request) {

        List<Long> bookIds = request.stream()
                .sorted(Comparator.comparing(BookMinusQuantityRequest::getBookId))
                .map(BookMinusQuantityRequest::getBookId)
                .toList();

        List<DetailInternal> books;
        try {
            books = bookRepository.getListDetail(bookIds);
        } catch (Exception e) {
            log.error("bookRepository.getListDetail error", e);
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }

        if (books.size() != request.size())
            throw new AppException(ErrorCode.NOTFOUND_DATA);

        books.sort(Comparator.comparing(DetailInternal::getId));
        request.sort(Comparator.comparing(BookMinusQuantityRequest::getBookId));
        List<QuantityBookAfterMinusResponse> newQuantity = new ArrayList<>();
        for (int i = 0; i < books.size(); i++) {
            int availableQuantity = books.get(i).getQuantity();
            int minusQuantity = request.get(i).getQuantity();
            if (availableQuantity < minusQuantity)
                throw new AppException(ErrorCode.QUANTITY_NOT_ENOUGH);

            // set new quantity
            books.get(i).setQuantity(availableQuantity - minusQuantity);

            // convert to new data and response
            newQuantity.add(bookMapper.toQuantityBookAfterMinusResponse(books.get(i)));
        }

        if (bookRepository.updateQuantity(newQuantity, bookIds) != bookIds.size())
            throw new AppException(ErrorCode.UPDATE_FAIL);

        return newQuantity;
    }
}
