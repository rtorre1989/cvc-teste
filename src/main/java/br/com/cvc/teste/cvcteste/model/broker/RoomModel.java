package br.com.cvc.teste.cvcteste.model.broker;

import lombok.Data;

@Data
public class RoomModel {

    private Integer roomID;
    private String categoryName;
    private PriceModel price;
    
}
