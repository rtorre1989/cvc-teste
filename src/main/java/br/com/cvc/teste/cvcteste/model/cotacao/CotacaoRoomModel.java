package br.com.cvc.teste.cvcteste.model.cotacao;

import lombok.Data;

@Data
public class CotacaoRoomModel  {

    private Integer id;
    private CotacaoCatagoryRoomModel category;
    private Double totalPrice;
    private CotacaoPriceModel priceDetail;
    
}
