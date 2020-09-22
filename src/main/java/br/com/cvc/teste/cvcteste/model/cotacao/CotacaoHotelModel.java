package br.com.cvc.teste.cvcteste.model.cotacao;

import java.util.List;

import lombok.Data;

@Data
public class CotacaoHotelModel {

    private Integer id;
    private String name;
    private List<CotacaoRoomModel> rooms;
    
}
