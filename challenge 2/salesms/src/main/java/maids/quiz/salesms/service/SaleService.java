package maids.quiz.salesms.service;

import maids.quiz.salesms.dto.ResponseDto;
import maids.quiz.salesms.dto.SaleDto;
import maids.quiz.salesms.dto.SaleTransactionDto;
import maids.quiz.salesms.exception.CommonExceptions;
import maids.quiz.salesms.model.Client;
import maids.quiz.salesms.model.Product;
import maids.quiz.salesms.model.Sale;
import maids.quiz.salesms.model.SaleTransaction;
import maids.quiz.salesms.repository.SaleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class SaleService extends CrudService<Sale, Integer> {
    @Autowired
    ClientService clientService;
    @Autowired
    ProductService productService;
    ModelMapper modelMapper = new ModelMapper();

    public SaleService(SaleRepository saleRepository) {
        super(saleRepository);
    }

    public ResponseEntity<ResponseDto<Sale>> createNewSale(SaleDto saleDto) {
        double total = 0;
        Client client = clientService.lookupResource(saleDto.getClientId());
        Client seller = clientService.lookupResource(saleDto.getSellerId());
        Set<SaleTransaction> saleTransactions = new HashSet<>();
        for (SaleTransactionDto saleTransactionDto : saleDto.getTransactions()) {
            Product product = productService.lookupResource(saleTransactionDto.getProductId());
            if (saleTransactionDto.getQuantity() > product.getQuantity()) {
                throw new CommonExceptions.BadRequestException(
                        product.getQuantity() + " of " + product.getName() + " is not enough quantity for "
                                + saleTransactionDto.getQuantity()
                );
            }
            SaleTransaction saleTransaction = modelMapper.map(saleTransactionDto, SaleTransaction.class);
            saleTransaction.setProduct(product);
            double totalPrice = saleTransactionDto.getPrice() * saleTransactionDto.getQuantity();
            total += totalPrice;
            saleTransaction.setTotal(totalPrice);
            saleTransactions.add(saleTransaction);
        }
        Sale sale = Sale.builder()
                .client(client)
                .seller(seller)
                .total(total)
                .transactions(saleTransactions)
                .build();
        return ResponseDto.response(sale);
    }
}
