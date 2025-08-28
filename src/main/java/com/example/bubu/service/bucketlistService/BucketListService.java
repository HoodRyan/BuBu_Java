package com.example.bubu.service.bucketlistService;

import com.example.bubu.repository.bucketlistRepository.BucketListRepository;

public class BucketListService {
    private final BucketListRepository bucketListRepository;

    public BucketListService(BucketListRepository bucketListRepository) {
        this.bucketListRepository = bucketListRepository;
    }


}
