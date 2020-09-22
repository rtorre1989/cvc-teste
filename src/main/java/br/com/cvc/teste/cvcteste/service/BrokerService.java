package br.com.cvc.teste.cvcteste.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.cvc.teste.cvcteste.model.broker.HotelModel;

@Service
public interface BrokerService {

    List<HotelModel> getHotelsAvailCity(Integer idCity);

    List<HotelModel> getHotelsDetails(Integer idHotel);
    
}
