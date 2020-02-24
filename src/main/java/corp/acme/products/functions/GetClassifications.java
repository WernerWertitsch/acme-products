package corp.acme.products.functions;

import corp.acme.common.domain.Classification;
import corp.acme.common.domain.ClassificationRequest;
import corp.acme.products.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.function.Function;

public class GetClassifications implements Function<ClassificationRequest, List<corp.acme.common.domain.Classification>> {
    @Autowired
    ProductsService productsService;

    @Override
    public List<corp.acme.common.domain.Classification> apply(ClassificationRequest classificationRequest) {
        return productsService.fetchClassifications(classificationRequest.getSubstanceNames());
    }
}
