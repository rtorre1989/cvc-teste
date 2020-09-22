package br.com.cvc.teste.cvcteste.model.broker;

import java.util.List;

import lombok.Data;

@Data
public class HotelModel {

    private Integer id;
    private String name;
    private Integer cityCode;
    private String cityName;
    private List<RoomModel> rooms;
    
}
