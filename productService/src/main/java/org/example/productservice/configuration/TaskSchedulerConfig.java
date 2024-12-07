package org.example.productservice.configuration;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.productservice.entity.Book;
import org.example.productservice.enums.PrefixCache;
import org.example.productservice.repository.BookRepository;
import org.example.productservice.service.CloudinaryService;
import org.example.productservice.service.RedisService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class TaskSchedulerConfig {

    BookRepository bookRepository;
    ExecutorService executorService;
    RedisService redisService;
    CloudinaryService cloudinaryService;
    public static final LinkedBlockingDeque<Long> PRODUCT_UPDATE = new LinkedBlockingDeque<>(500);
    public static final LinkedBlockingDeque<String> REMOVE_IMAGE = new LinkedBlockingDeque<>(500);

    @Scheduled(fixedDelay = 500)
    public void taskRemoveImageAfter500ms() {
        String item = REMOVE_IMAGE.poll();
        if (item == null) {
            return;
        }
        cloudinaryService.removeImage(getPublicId(item), "book_store/book");
    }

    String getPublicId(String url) {
        String[] splitUrl = url.split("/");
        return "book_store/book/" + splitUrl[splitUrl.length - 1].split("\\.")[0];
    }

    @Scheduled(fixedDelay = 500)
    public void taskUpdateProductRunningAfter500ms() {

        List<Long> productIds = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            Long id = PRODUCT_UPDATE.poll();
            if (id != null) {
                productIds.add(id);
            }
        }

        if (productIds.isEmpty()) {
            return;
        }
        log.info("cache: " + productIds);

        List<Book> books = null;
        try {
            books = bookRepository.findAllById(productIds);
        } catch (Exception e) {
            log.error("bookRepository.findAllById error: ", e);
        }

        if (books == null || books.isEmpty()) {
            return;
        }

        final List<Book> booksCache = books;
        executorService.submit(() -> putRelated(booksCache));
        executorService.submit(() -> putProduct(booksCache));
    }

    void putRelated(List<Book> books) {

    }

    void putProduct(List<Book> books) {
        Map<String, Object> map = new HashMap<>();
        books.forEach(book -> map.put(PrefixCache.BOOK_.name() + book.getId(), book));
        redisService.savePipeline(map);
    }
}