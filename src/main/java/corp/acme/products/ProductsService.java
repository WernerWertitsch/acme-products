package corp.acme.products;

import corp.acme.common.domain.Classification;
import corp.acme.common.domain.Product;
import corp.acme.common.domain.ProductRequest;
import corp.acme.common.util.ServiceCall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

@Service
public class ProductsService {

    Logger logger = Logger.getLogger("ProductsService");

    @Autowired
    private DiscoveryClient discoveryClient;


    public Product getProduct(ProductRequest productRequest) {
        Product product = new Product();
        product.setCancelled(false);
        product.setClassification(productRequest.getClassification());
        product.setFeePrct(fetchFeeForProductRequest(productRequest));
        product.setInsuredValue(productRequest.getValue());
        product.setPremium(calculatePremium(product));
        return product;
    }

    public List<Classification> getClassificationsForNames(List<String> substanceNames) {
        return this.fetchClassifications(substanceNames);
    }

    private BigDecimal calculatePremium(Product product) {
        return product.getInsuredValue().multiply(new BigDecimal(product.getFeePrct()));
    }


    private Double fetchFeeForProductRequest(ProductRequest productRequest) {
        URI uri = this.discoveryClient.getInstances("FEES").get(0).getUri();
        WebClient.RequestHeadersSpec call = ServiceCall.buildDefaultJsonCall(uri, "feeForCategoryId", productRequest);
        return call.retrieve().toEntity(Double.class).block().getBody();
    }


    // Hm, all these should return these asynchronous Flux thingies..
    private List<Classification> fetchClassifications(List<String> names){
        URI uri = this.discoveryClient.getInstances("REGULATORY").get(0).getUri();
        WebClient.RequestHeadersSpec call = ServiceCall.buildDefaultJsonCall(uri, "bySubstanceNames", names);
        return call.retrieve().toEntity(List.class).block().getBody();
    }


//    private Classification fetchClassification(String substanceName) {
//        URI uri = this.discoveryClient.getInstances("REGULATORY").get(0).getUri();
//        WebClient.RequestHeadersSpec call = ServiceCall.buildDefaultCall(uri, "bySubstanceName", substanceName);
//        return call.retrieve().toEntity(Classification.class).block().getBody();
//    }

}
