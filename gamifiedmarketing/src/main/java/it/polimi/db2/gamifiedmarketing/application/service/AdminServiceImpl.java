package it.polimi.db2.gamifiedmarketing.application.service;

import it.polimi.db2.gamifiedmarketing.application.entity.Product;
import it.polimi.db2.gamifiedmarketing.application.repository.ProductRepository;
import it.polimi.db2.gamifiedmarketing.application.repository.SubmissionRepository;
import it.polimi.db2.gamifiedmarketing.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SubmissionRepository submissionRepository;
    @Autowired
    private ProductRepository productRepository;


    @Override
    public void deleteProductByDate(Date date) {
        Product product = productRepository.getProductByDate(date);
    }

    @Override
    public void addProduct(Product product) {

    }

    @Override
    public void getVisualQuestionnaire() {

    }

    @Override
    public List<Product> provaGet() {
        return productRepository.provaGet();
    }
}
