package com.nttdata.mscosume.service;

import java.util.Date;

import com.nttdata.mscosume.model.Credit;
import com.nttdata.mscosume.model.Movements;
import com.nttdata.mscosume.proxy.ConsumeProxy;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class ConsumeServiceImpl implements ConsumeService{

    private final ConsumeProxy consumeProxy = new ConsumeProxy();

    @Override
    public Mono<Movements> consumeCredit(String idCredit, Double amount) {

        return consumeProxy.getCredit(idCredit)
                .flatMap(resp->checkBalance(resp, amount))
                .flatMap(resp->consumeCredit(resp, amount))
                .flatMap(consumeProxy::updateCredit)
                .flatMap(resp->saveMovement(idCredit, "credit consume", amount, null));
    }


    //AVTIVEWITHDRAW UTIL METHODS
    public Mono<Credit> checkBalance(Credit credit, Double amount){
        return credit.getBalance() > amount ? Mono.just(credit)
                : Mono.error(() -> new IllegalArgumentException("Not enough balance"));
    }

    public Mono<Credit> consumeCredit(Credit credit, Double amount){
        credit.setBalance(credit.getBalance()-amount);
        credit.setDebt(credit.getDebt()+amount);
        return Mono.just(credit);
    }

    public Mono<Movements> saveMovement(String idProduct,
                                     String type,
                                     Double amount,
                                     String idThirdPartyProduct) {

        Movements movement = new Movements();
        movement.setIdProduct(idProduct);
        movement.setType(type);
        movement.setAmount(amount);
        movement.setIdThirdPartyProduct(idThirdPartyProduct);
        movement.setDate(new Date());

        return consumeProxy.saveMovement(movement);
    }
}