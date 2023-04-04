package com.nttdata.mscosume.service;

import com.nttdata.mscosume.model.Movements;
import reactor.core.publisher.Mono;

public interface ConsumeService {
    public Mono<Movements> consumeCredit(String idCredit, Double amount);
}