package corp.acme.products.functions;

import corp.acme.common.domain.Classification;
import corp.acme.products.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.function.Function;

public class GetClassification implements Function<List<String>, List<corp.acme.common.domain.Classification>> {
    @Autowired
    ProductsService productsService;

    @Override
    public List<corp.acme.common.domain.Classification> apply(List<String> names) {
        return productsService.getClassificationsForNames(names);
    }
}
