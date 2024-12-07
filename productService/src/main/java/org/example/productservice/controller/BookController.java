package org.example.productservice.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.productservice.dto.request.BookCreationRequest;
import org.example.productservice.dto.request.BookUpdateRequest;
import org.example.productservice.dto.request.BookUploadImageRequest;
import org.example.productservice.dto.request.RemoveImageBookRequest;
import org.example.productservice.dto.response.*;
import org.example.productservice.service.BookService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping("/book")
public class BookController {
    BookService bookService;

    @PostMapping("add-new")
    ApiResponse<BookCreationResponse> createBook(@RequestBody BookCreationRequest request) {
        return ApiResponse.<BookCreationResponse>builder()
                .result(bookService.create(request))
                .build();
    }

    @GetMapping("find")
//    @PreAuthorize("hasAnyAuthority('ROLE_WAREHOUSE_MANAGER')")
    ApiResponse<List<ManagerFindBookResponse>> find(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "category", required = false) Integer category,
            @RequestParam(value = "publisher", required = false) Integer publisher,
            @RequestParam(value = "author", required = false) Long author,
            @RequestParam(value = "page", defaultValue = "1") int pageNumber

    ) {
        return ApiResponse.<List<ManagerFindBookResponse>>builder()
                .result(bookService.find(name, category, publisher, author, pageNumber))
                .build();
    }

    @GetMapping("/detail/{book_id}")
    ApiResponse<ManagerBookDetailResponse> getDetail(@PathVariable(name = "book_id") long bookId) {
        return ApiResponse.<ManagerBookDetailResponse>builder()
                .result(bookService.mGetDetail(bookId))
                .build();
    }

    @PatchMapping("/update")
    ApiResponse<Void> update(@Valid @RequestBody BookUpdateRequest request) {
        bookService.update(request);
        return ApiResponse.<Void>builder().build();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PostMapping(value = "/upload/image", consumes = {"multipart/form-data"})
    public ApiResponse<BookUploadImageResponse> uploadBook(
            @RequestPart("file") MultipartFile file,
            @RequestPart("bookId") String bookId,
            @RequestPart("type") String type
    ) {
        BookUploadImageRequest request = BookUploadImageRequest.builder()
                .file(file)
                .type(type)
                .bookId(bookId)
                .build();
        return ApiResponse.<BookUploadImageResponse>builder()
                .result(bookService.uploadImage(request))
                .build();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PutMapping("/remove/image")
    public ApiResponse<Void> removeImageBook(@Valid @RequestBody RemoveImageBookRequest request) {
        bookService.removeImage(request);
        return ApiResponse.<Void>builder()
                .build();
    }
}
