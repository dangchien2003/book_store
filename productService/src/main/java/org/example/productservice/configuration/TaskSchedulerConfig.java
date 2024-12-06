package org.example.productservice.configuration;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.productservice.entity.Book;
import org.example.productservice.enums.PrefixCache;
import org.example.productservice.repository.BookRepository;
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
    public static final LinkedBlockingDeque<Long> PRODUCT_UPDATE = new LinkedBlockingDeque<>();

    @Scheduled(fixedDelay = 500)
    public void taskUpdateProductAlwaysRunning() {

        List<Long> productIds = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            productIds.add(PRODUCT_UPDATE.poll());
        }

        if (productIds.isEmpty()) {
            return;
        }

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