package br.com.cvc.teste.cvcteste.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import br.com.cvc.teste.cvcteste.model.broker.HotelModel;
import br.com.cvc.teste.cvcteste.properties.BrokerProperties;

@Service
public class BrokerServiceImpl implements BrokerService {

    @Autowired
    private BrokerProperties properties;

    @Override
    public List<HotelModel> getHotelsAvailCity(Integer idCity) {
        RestTemplate restTemplate = new RestTemplate();
        HotelModel[] listHotels;
        try {
            listHotels = restTemplate.getForObject(properties.getUrl() + properties.getAvailCity() + idCity.toString(),
                    HotelModel[].class);

            return Arrays.asList(listHotels);
        } catch (RestClientException ex) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<HotelModel> getHotelsDetails(Integer idHotel) {
        RestTemplate restTemplate = new RestTemplate();
        HotelModel[] hotel;
        try {
            hotel = restTemplate.getForObject(properties.getUrl() + properties.getDetails() + idHotel.toString(),
                    HotelModel[].class);

            return Arrays.asList(hotel);
        } catch (RestClientException ex) {
            return new ArrayList<>();
        }
    }

}
