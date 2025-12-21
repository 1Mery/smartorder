package com.turkcell.orderservice.infrastructure.client;

import com.turkcell.orderservice.application.exception.InsufficientStockException;
import com.turkcell.orderservice.application.ports.StockClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.UUID;

@Slf4j
@Component
public class StockWebClientAdapter implements StockClient {

    private final WebClient client;


    public StockWebClientAdapter(
            @Qualifier("productWebClient") WebClient client) {   //springden product service için olan web clientı istiyoruz
        this.client = client;
    }

    @Override
    public void checkStock(UUID productId, int quantity) {
        try {
            client.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/api/v1/products/{id}/check-stock")
                            .queryParam("quantity", quantity) //Stok kontrolü
                            .build(productId))
                    .retrieve()
                    .toBodilessEntity() //cevap almadan çıkma
                    .block();

        } catch (WebClientResponseException e) {

            if (e.getStatusCode() == HttpStatus.CONFLICT) {
                throw new InsufficientStockException(
                        "Insufficient stock for product " + productId
                );
            }
            throw e;
        }
    }

    @Override
    public void decreaseStock(UUID productId, int quantity) {
        try {
            client.put()
                    .uri(uriBuilder -> uriBuilder
                            .path("/api/v1/products/{productId}/stock/decrease")
                            .build(productId))
                    .contentType(MediaType.APPLICATION_JSON)   //
                    .bodyValue(java.util.Map.of("quantity", quantity)) //product service beklenen quantity tipine dönüştürmeye sağladık
                    .retrieve()  //response’u al ve status’a göre işlem
                    .toBodilessEntity() //cevap almadan çıkma
                    .block();

        } catch (WebClientResponseException e) {

            if (e.getRawStatusCode() == 409 || e.getRawStatusCode() == 422) {
                String msg = extractMessageSimple(e.getResponseBodyAsString());
                throw new InsufficientStockException( " productId=" + productId + ", requested=" + quantity + ". " + msg);
            }

            throw e; // diğer hatalar aynen fırlasın
        }
    }

    @Override
    public void increaseStock(UUID productId, int quantity) {
        try {
            client.put()
                    .uri(uriBuilder -> uriBuilder
                            .path("/api/v1/products/{productId}/stock/increase")
                            .build(productId))
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(java.util.Map.of("stockAmount", quantity))
                    .retrieve()
                    .toBodilessEntity() //cevap almadan çıkma
                    .block();

        } catch (WebClientResponseException e) {

            if (e.getRawStatusCode() == 409 || e.getRawStatusCode() == 422) {
                String msg = extractMessageSimple(e.getResponseBodyAsString());
                throw new InsufficientStockException( "Insufficient stock. productId=" + productId + ", requested=" + quantity + ". " + msg);
            }

            throw e;
        }
    }

    //kullanıcıya anlaşılır hata mesajı dönmek
    private String extractMessageSimple(String body) {
        if (body == null || body.isBlank())
            return "Insufficient stock";

        // body genelde: {"message":"Insufficient stock"}

        int keyIndex = body.indexOf("\"message\"");
        if (keyIndex == -1) //bu kontrol yapılmazsa StringIndexOutOfBoundsException alınır buda 500 hatasına sebep olur
            return body; //message yoksa body’yi olduğu gibi dön

        //mesajın başladığı ve bittiği tırnakları bularak aradaki body'i direk yazar

        int firstQuote = body.indexOf("\"", body.indexOf(":", keyIndex) + 1);
        if (firstQuote == -1)
            return body;

        int secondQuote = body.indexOf("\"", firstQuote + 1);
        if (secondQuote == -1)
            return body;

        return body.substring(firstQuote + 1, secondQuote);
    }

}

//fail-fast business validation