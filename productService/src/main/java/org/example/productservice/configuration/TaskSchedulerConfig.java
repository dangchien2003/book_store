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

import java.util.*;
import java.util.concurrent.*;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class TaskSchedulerConfig {

    BookRepository bookRepository;
    ExecutorService executorService;
    RedisService redisService;

    public static final Set<Long> PRODUCT_UPDATE = Collections.synchronizedSet(new LinkedHashSet<>());

    @Scheduled(cron = "* */5 * * * *")
    public void taskUpdateProduct() {

        if (PRODUCT_UPDATE.isEmpty()) {
            return;
        }

        List<Long> productIds = new ArrayList<>(PRODUCT_UPDATE).subList(0, PRODUCT_UPDATE.size());
        PRODUCT_UPDATE.removeAll(productIds);

        List<Book> books = null;
        try {
            books = bookRepository.findAllById(productIds);
        } catch (Exception e) {
            log.error("bookRepository.findAllById error: ", e);
        }

        if (books == null || books.isEmpty()) {
            return;
        }

        final Queue<Book> queuePutRelated = new ConcurrentLinkedQueue<>(books);
        final Queue<Book> queuePutProduct = new ConcurrentLinkedQueue<>(books);
        executorService.submit(() -> putRelated(queuePutRelated));
        executorService.submit(() -> putProduct(queuePutProduct));
    }

    void putRelated(Queue<Book> queue) {
        
    }

    void putProduct(Queue<Book> queue) {
        List<Future<Boolean>> futures = new ArrayList<>();
        for (int i = 0; i < queue.size(); i++) {
            Callable<Boolean> callable = () -> {
                Book book = queue.poll();
                if (book == null) {
                    return true;
                }

                String key = PrefixCache.BOOK_.name() + book.getId();

                redisService.delete(key);
                redisService.save(key, book);

                boolean saveOk = redisService.hasKey(key);
                if (!saveOk) {
                    PRODUCT_UPDATE.add(book.getId());
                }

                return saveOk;
            };
            Future<Boolean> future = executorService.submit(callable);
            futures.add(future);
        }

        boolean putAllSuccess = runResult(futures);

        if (putAllSuccess) {
            log.info("Cache sản phẩm thành công");
        } else {
            log.warn("Cache sản phẩm thất bại");
        }
    }

    boolean runResult(List<Future<Boolean>> futures) {
        boolean putAllSuccess = true;
        for (Future<Boolean> future : futures) {
            try {
                if (!Boolean.TRUE.equals(future.get(5, TimeUnit.SECONDS))) {
                    putAllSuccess = false;
                }
            } catch (InterruptedException | TimeoutException | ExecutionException e) {
                putAllSuccess = false;
                log.error("Lỗi khi xử lý task: ", e);
                Thread.currentThread().interrupt();
            }
        }

        return putAllSuccess;
    }
}