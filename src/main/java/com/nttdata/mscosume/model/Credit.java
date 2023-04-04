package com.nttdata.mscosume.model;

import lombok.Data;

@Data
public class Credit {

    private String id;
    private String idCustomer;
    private String cardNumber;
    private String creditType;
    private String accountNumber;
    private Double balance;
    private Double creditLine;
    private Double debt;

}
