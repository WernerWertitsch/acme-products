package corp.acme.products.functions;

import corp.acme.common.domain.Classification;
import corp.acme.products.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class GetAllClassifications implements Supplier<List<Classification>> {
    @Autowired
    ProductsService productsService;

    @Override
    public List<Classification> get() {
        return productsService.fetchAllClassifications();
    }
}
