package com.project.order.handler;

import com.project.order.exception.ExternalAPIFailure;
import com.project.order.model.ExternalRequest;
import com.project.order.model.OrderedProduct;
import com.project.order.model.Product;
import com.project.order.property.AppProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ExternalAPI {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    AppProperty appProperty;

    public List<Product> getProductDetails(List<OrderedProduct> orderList) {
        try {
            List<Long> id = orderList.stream().map(order -> order.getProductId()).collect(Collectors.toList());
            HttpHeaders httpHeaders=new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            ExternalRequest externalRequest=new ExternalRequest();
            externalRequest.setProductId(id);
            HttpEntity<ExternalRequest> httpEntity=new HttpEntity<>(externalRequest,httpHeaders);
            ResponseEntity<List<Product>> listResponseEntity = restTemplate.exchange(appProperty.getExternalUrl() + "/getProductsById", HttpMethod.POST, httpEntity, new ParameterizedTypeReference<List<Product>>() {
            });
            if(HttpStatus.OK.equals(listResponseEntity.getStatusCode())) {
                return listResponseEntity.getBody();
            } else
                throw new ExternalAPIFailure();
        }catch (Exception e){
            throw new ExternalAPIFailure(e);
        }
    }


    public String updateProduct(Map<Long, Product> validProductsMap) {
        try {
            List<Product> validProducts = validProductsMap.values().parallelStream().collect(Collectors.toList());
            Map<Long, Integer> orderedProductsMap = validProducts.stream().collect(Collectors.toMap(Product::getId, Product::getQuantity));
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            ExternalRequest externalRequest=new ExternalRequest();
            externalRequest.setProductDetails(orderedProductsMap);
            HttpEntity<ExternalRequest> httpEntity=new HttpEntity<>(externalRequest,httpHeaders);
            ResponseEntity<String> listResponseEntity = restTemplate.postForEntity(appProperty.getExternalUrl() + "/updateProduct", httpEntity, String.class);
            if (HttpStatus.OK.equals(listResponseEntity.getStatusCode())) {
                return listResponseEntity.getBody();
            } else
                throw new ExternalAPIFailure();
        }catch (Exception e){
            throw new ExternalAPIFailure(e);
        }
    }
}
