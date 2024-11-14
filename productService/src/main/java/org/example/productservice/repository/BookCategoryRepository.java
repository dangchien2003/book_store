package org.example.productservice.repository;

import java.util.Set;

public interface BookCategoryRepository {
    int create(int categoryId, Set<Long> bookIds, long createAt);

    int removeBookInCategory(int categoryId, Set<Long> bookIds);

//    List<ManagerFindBookResponse> find(FindBook filter) throws Exception;
}
