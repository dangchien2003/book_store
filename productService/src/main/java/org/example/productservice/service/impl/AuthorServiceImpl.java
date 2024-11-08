package org.example.productservice.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.productservice.dto.request.AuthorCreationRequest;
import org.example.productservice.dto.request.AuthorUpdateRequest;
import org.example.productservice.dto.response.AuthorResponse;
import org.example.productservice.entity.Author;
import org.example.productservice.exception.AppException;
import org.example.productservice.exception.ErrorCode;
import org.example.productservice.mapper.AuthorMapper;
import org.example.productservice.repository.AuthorRepository;
import org.example.productservice.service.AuthorService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthorServiceImpl implements AuthorService {

    AuthorRepository authorRepository;
    AuthorMapper authorMapper;

    @Override
    public AuthorResponse create(AuthorCreationRequest request) {
        Author author = authorMapper.toAuthor(request);
        author.onCreate();

        Long id = authorRepository.create(author);
        if (Objects.isNull(id))
            throw new AppException(ErrorCode.UPDATE_FAIL);

        author.setId(id);
        return authorMapper.toAuthorResponse(author);
    }

    @Override
    public AuthorResponse update(AuthorUpdateRequest request) {
        if (Objects.isNull(request.getId()))
            throw new AppException(ErrorCode.NOTFOUND_ID);

        if (!authorRepository.existById(request.getId()))
            throw new AppException(ErrorCode.NOTFOUND_DATA);

        Author author = authorMapper.toAuthor(request);
        author.onUpdate();

        if (authorRepository.update(author) == 0)
            throw new AppException(ErrorCode.UPDATE_FAIL);

        return authorMapper.toAuthorResponse(author);
    }

    @Override
    public AuthorResponse get(Long id) {
        if (Objects.isNull(id))
            throw new AppException(ErrorCode.NOTFOUND_ID);

        Author author;
        try {
            author = authorRepository.findById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new AppException(ErrorCode.NOTFOUND_DATA);
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }

        if (Objects.isNull(author))
            throw new AppException(ErrorCode.NOTFOUND_DATA);

        return authorMapper.toAuthorResponse(author);
    }

    @Override
    public List<AuthorResponse> getAll(int pageNumber) {
        int pageSize = 2;
        try {
            return authorRepository.findAllOrderByName(pageNumber, pageSize);
        } catch (Exception e) {
            log.error("AUTHOR SERVICE ERROR:", e);
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    @Override
    public List<AuthorResponse> getByName(String name, int pageNumber) {
        int pageSize = 2;
        try {
            return authorRepository.findAllByName(name, pageNumber, pageSize);
        } catch (Exception e) {
            log.error("AUTHOR SERVICE ERROR:", e);
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }
}
