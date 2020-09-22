package br.com.cvc.teste.cvcteste.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cvc.teste.cvcteste.model.broker.HotelModel;
import br.com.cvc.teste.cvcteste.model.broker.PriceModel;
import br.com.cvc.teste.cvcteste.model.broker.RoomModel;
import br.com.cvc.teste.cvcteste.model.cotacao.CotacaoCatagoryRoomModel;
import br.com.cvc.teste.cvcteste.model.cotacao.CotacaoHotelModel;
import br.com.cvc.teste.cvcteste.model.cotacao.CotacaoPriceModel;
import br.com.cvc.teste.cvcteste.model.cotacao.CotacaoRoomModel;

import java.time.temporal.ChronoUnit;

@Service
public class CotacaoHoteisServiceImpl implements CotacaoHoteisService {

    private final Double COMISSAO_VIAGEM = 0.7;

    @Autowired
    private BrokerService brokerService;

    public List<CotacaoHotelModel> obterCotacaoHotel(Integer hotelId, LocalDate checkInDate, LocalDate checkOutDate,
            Integer numberOfAdults, Integer numberOfChildren) {
        List<CotacaoHotelModel> result = new ArrayList<>();

        List<HotelModel> hoteis = brokerService.getHotelsDetails(hotelId);

        result = obterCotacao(hoteis, checkInDate, checkOutDate, numberOfAdults, numberOfChildren);
        return result;
    }

    public List<CotacaoHotelModel> obterCotacaoCidade(Integer cityId, LocalDate checkInDate, LocalDate checkOutDate,
            Integer numberOfAdults, Integer numberOfChildren) {
        List<CotacaoHotelModel> result = new ArrayList<>();

        List<HotelModel> hoteis = brokerService.getHotelsAvailCity(cityId);

        result = processar(hoteis, checkInDate, checkOutDate, numberOfAdults, numberOfChildren);
        return result;
    }

    private List<CotacaoHotelModel> obterCotacao(List<HotelModel> listHotels, LocalDate checkInDate,
            LocalDate checkOutDate, Integer numberOfAdults, Integer numberOfChildren) {
        List<CotacaoHotelModel> result = new ArrayList<>();

        Long numberOfDays = ChronoUnit.DAYS.between(checkInDate, checkOutDate);

        for (HotelModel hotel : listHotels) {

            CotacaoHotelModel hotelModel = new CotacaoHotelModel();
            hotelModel.setId(hotel.getId());
            hotelModel.setName(hotel.getName());
            hotelModel.setRooms(new ArrayList<>());

            for (RoomModel room : hotel.getRooms()) {

                CotacaoRoomModel roomModel = new CotacaoRoomModel();
                roomModel.setId(room.getRoomID());
                roomModel.setCategory(new CotacaoCatagoryRoomModel());
                roomModel.getCategory().setName(room.getCategoryName());

                roomModel.setPriceDetail(new CotacaoPriceModel());
                roomModel.getPriceDetail().setPricePerDayAdult(room.getPrice().getAdult());
                roomModel.getPriceDetail().setPricePerDayChild(room.getPrice().getChild());

                Double totalPrice = this.calcularTotalPrice(room.getPrice(), numberOfAdults, numberOfChildren,
                        numberOfDays);

                roomModel.setTotalPrice(totalPrice);
                hotelModel.getRooms().add(roomModel);
            }

            result.add(hotelModel);

        }

        return result;
    }

    private Double calcularTotalPrice(PriceModel price, Integer numberOfAdults, Integer numberOfChildren,
            Long numberOfDays) {
        Double totalPriceAdult = numberOfDays * numberOfAdults * price.getAdult();
        Double totalPriceChild = numberOfDays * numberOfChildren * price.getChild();

        Double comissao = (totalPriceAdult / COMISSAO_VIAGEM) + (totalPriceChild / COMISSAO_VIAGEM);

        return totalPriceAdult + totalPriceChild + comissao;
    }

    private List<CotacaoHotelModel> processar(List<HotelModel> listHotels, LocalDate checkInDate,
            LocalDate checkOutDate, Integer numberOfAdults, Integer numberOfChildren) {

        List<List<HotelModel>> subLists = Lists.partition(listHotels, 10);
        List<CompletableFuture<Object>> futuresList = new ArrayList<CompletableFuture<Object>>();

        for (List<HotelModel> subLista : subLists) {
            CompletableFuture<Object> obterCotacaoAsy = CompletableFuture.supplyAsync(
                    () -> (obterCotacao(subLista, checkInDate, checkOutDate, numberOfAdults, numberOfChildren)));

            futuresList.add(obterCotacaoAsy);
        }

        CompletableFuture<Void> allFutures = CompletableFuture
                .allOf(futuresList.toArray(new CompletableFuture[futuresList.size()]));

        CompletableFuture<Object> allCompletableFuture = allFutures.thenApply(future -> {
            return futuresList.stream().map(completableFuture -> completableFuture.join()).collect(Collectors.toList());
        });

        CompletableFuture<Object> completableFuture = allCompletableFuture.toCompletableFuture();

        List<CotacaoHotelModel> result = new ArrayList<>();
        try {
            List<List<CotacaoHotelModel>> listas = (List<List<CotacaoHotelModel>>) completableFuture.get();

            for (List<CotacaoHotelModel> list : listas) {
                result.addAll(list);
            }

            return result;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return result;
    }

}
