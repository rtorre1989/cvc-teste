package br.com.cvc.teste.cvcteste.controller;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.cvc.teste.cvcteste.model.broker.HotelModel;
import br.com.cvc.teste.cvcteste.model.cotacao.CotacaoHotelModel;
import br.com.cvc.teste.cvcteste.service.BrokerService;
import br.com.cvc.teste.cvcteste.service.CotacaoHoteisService;

@Validated
@RestController
@RequestMapping("cvc-cotacao-hoteis")
public class CotacaoHoteisController {

    @Autowired
    private BrokerService brokerservice;

    @Autowired
    private CotacaoHoteisService service;

    @GetMapping("cotacao-hotel")
    public List<CotacaoHotelModel> getCotacaoHotel(@RequestParam(required = true) @Min(1) @Max(99999) int hotelId,
    @RequestParam  @DateTimeFormat(pattern = "yyyy-MM-dd", iso = ISO.DATE) @Valid LocalDate checkInDate,
    @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd", iso = ISO.DATE) @Valid LocalDate checkOutDate, @RequestParam  @Min(1)  @Max(10) Integer numberOfAdults,
    @RequestParam  @Min(1)  @Max(10) Integer numberOfChildren) {
        return service.obterCotacaoHotel(hotelId, checkInDate, checkOutDate, numberOfAdults, numberOfChildren);
    }

    @GetMapping("cotacao-cidade")
    public List<CotacaoHotelModel> getCotacaoCidade(@RequestParam(required = true) @Min(1) @Max(99999) int cityId,
            @RequestParam  @DateTimeFormat(pattern = "yyyy-MM-dd", iso = ISO.DATE) @Valid LocalDate checkInDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd", iso = ISO.DATE) @Valid LocalDate checkOutDate, @RequestParam  @Min(1)  @Max(10) Integer numberOfAdults,
            @RequestParam  @Min(1)  @Max(10) Integer numberOfChildren) {

        return service.obterCotacaoCidade(cityId, checkInDate, checkOutDate, numberOfAdults, numberOfChildren);
    }

}