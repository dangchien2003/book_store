package org.example.productservice.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.productservice.dto.request.PublisherCreationRequest;
import org.example.productservice.dto.request.PublisherUpdateRequest;
import org.example.productservice.dto.response.PublisherResponse;
import org.example.productservice.entity.Publisher;
import org.example.productservice.exception.AppException;
import org.example.productservice.exception.ErrorCode;
import org.example.productservice.mapper.PublisherMapper;
import org.example.productservice.repository.PublisherRepository;
import org.example.productservice.service.PublisherService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PublisherServiceImpl implements PublisherService {

    PublisherRepository publisherRepository;
    PublisherMapper publisherMapper;

    @Override
    public PublisherResponse create(PublisherCreationRequest request) {
        Publisher publisher = publisherMapper.toPublisher(request);
        publisher.onCreate();

        Integer id = publisherRepository.create(publisher);
        if (Objects.isNull(id))
            throw new AppException(ErrorCode.UPDATE_FAIL);

        publisher.setId(id);
        return publisherMapper.toPublisherResponse(publisher);
    }

    @Override
    public PublisherResponse update(PublisherUpdateRequest request) {
        if (Objects.isNull(request.getId()))
            throw new AppException(ErrorCode.NOTFOUND_ID);

        if (!publisherRepository.existById(request.getId()))
            throw new AppException(ErrorCode.NOTFOUND_DATA);

        Publisher publisher = publisherMapper.toPublisher(request);
        publisher.onUpdate();

        if (publisherRepository.update(publisher) == 0)
            throw new AppException(ErrorCode.UPDATE_FAIL);

        return publisherMapper.toPublisherResponse(publisher);
    }

    @Override
    public PublisherResponse get(Integer id) {
        if (Objects.isNull(id))
            throw new AppException(ErrorCode.NOTFOUND_ID);

        Publisher publisher;
        try {
            publisher = publisherRepository.findById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new AppException(ErrorCode.NOTFOUND_DATA);
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }

        if (Objects.isNull(publisher))
            throw new AppException(ErrorCode.NOTFOUND_DATA);

        return publisherMapper.toPublisherResponse(publisher);
    }

    @Override
    public List<PublisherResponse> getAll(int pageNumber) {
        int pageSize = 2;
        try {
            return publisherRepository.findAllOrderByName(pageNumber, pageSize);
        } catch (Exception e) {
            log.error("AUTHOR SERVICE ERROR:", e);
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    @Override
    public List<PublisherResponse> getByName(String name, int pageNumber) {
        int pageSize = 2;
        try {
            return publisherRepository.findAllByName(name, pageNumber, pageSize);
        } catch (Exception e) {
            log.error("AUTHOR SERVICE ERROR:", e);
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }
}
