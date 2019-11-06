package ex.rabbit.listener;

import ex.rabbit.domain.Product;
import ex.rabbit.repositories.ProductRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ProductMessageListener {

    private static final Logger log = LogManager.getLogger(ProductMessageListener.class);
    private ProductRepository productRepository;

    public ProductMessageListener(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void receiveMessage(Map<String, String> message) {
        log.info("Received <" + message + ">");
        Long id = Long.valueOf(message.get("id"));
        Product product = productRepository.findById(id).orElse(null);
        product.setMessageReceived(true);
        product.setMessageCount(product.getMessageCount() + 1);

        productRepository.save(product);
        log.info("Message processed...");
    }
}
