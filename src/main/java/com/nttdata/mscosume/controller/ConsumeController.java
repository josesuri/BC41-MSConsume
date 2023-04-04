package com.nttdata.mscosume.controller;

import com.nttdata.mscosume.model.Movements;
import com.nttdata.mscosume.service.ConsumeService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/consume")
public class ConsumeController {

    private final ConsumeService consumeService;

    @PostMapping("/{id}")
    public Mono<Movements> withdraw(@PathVariable("id") String idProduct,
                                    @RequestParam Double amount) {
        return consumeService.consumeCredit(idProduct, amount);
    }
}