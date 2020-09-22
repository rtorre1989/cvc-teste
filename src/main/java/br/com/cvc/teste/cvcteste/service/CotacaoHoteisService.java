package br.com.cvc.teste.cvcteste.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.cvc.teste.cvcteste.model.cotacao.CotacaoHotelModel;

@Service
public interface CotacaoHoteisService {

    List<CotacaoHotelModel> obterCotacaoHotel(Integer hotelId, LocalDate checkInDate, LocalDate checkOutDate,
            Integer numberOfAdults, Integer numberOfChildren);

    List<CotacaoHotelModel> obterCotacaoCidade(Integer cityId, LocalDate checkInDate, LocalDate checkOutDate,
            Integer numberOfAdults, Integer numberOfChildren) ;
}
