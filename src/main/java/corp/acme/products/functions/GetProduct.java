package corp.acme.products.functions;

import corp.acme.common.domain.ProductRequest;
import corp.acme.products.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.function.Function;

public class GetProduct implements Function<ProductRequest, corp.acme.common.domain.Product> {
    @Autowired
    ProductsService productsService;

    @Override
    public corp.acme.common.domain.Product apply(ProductRequest productRequest) {
        return productsService.getProduct(productRequest);
    }
}
