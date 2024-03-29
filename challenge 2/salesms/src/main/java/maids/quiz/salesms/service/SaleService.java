package maids.quiz.salesms.service;

import maids.quiz.salesms.dto.*;
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
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class SaleService extends CrudService<Sale, Integer> {
    @Autowired
    ClientService clientService;
    @Autowired
    ProductService productService;
    @Autowired
    SaleRepository saleRepository;
    @Autowired
    SaleTransactionService saleTransactionService;

    ModelMapper modelMapper = new ModelMapper();

    public SaleService(SaleRepository saleRepository) {
        super(saleRepository);
    }

    @Transactional
    public ResponseEntity<ResponseDto<Sale>> createNewSale(SaleDto saleDto) {
        double total = 0;
        Client client = clientService.lookupResource(saleDto.getClientId());
        Client seller = clientService.lookupResource(saleDto.getSellerId());
        Set<SaleTransaction> saleTransactions = new HashSet<>();

        for (SaleTransactionDto saleTransactionDto : saleDto.getTransactions()) {
            Product product = productService.lookupResource(saleTransactionDto.getProductId());
            if (saleTransactionDto.getQuantity() > product.getQuantity()) {
                throw new CommonExceptions.BadRequestException(
                        product.getQuantity() + " is the only available amount of " + product.getName()
                                + " is not enough quantity for " + saleTransactionDto.getQuantity()
                );
            }
            SaleTransaction saleTransaction = modelMapper.map(saleTransactionDto, SaleTransaction.class);
            saleTransaction.setProduct(product);
            double totalPrice = saleTransactionDto.getPrice() * saleTransactionDto.getQuantity();
            total += totalPrice;
            saleTransaction.setTotal(totalPrice);
            saleTransactions.add(saleTransaction);

            product.setQuantity(product.getQuantity() - saleTransaction.getQuantity());
//            TODO: implement bulk update to improve performance
            productService.add(product);
        }
        Sale sale = Sale.builder()
                .client(client)
                .seller(seller)
                .total(total)
                .transactions(saleTransactions)
                .build();
        return super.add(sale);
    }

    @Transactional
    public ResponseEntity<ResponseDto<Sale>> update(
            Integer id,
            UpdateSaleDto updateSaleDto
    ) {
        Sale sale = lookupResource(id);
        Map<Integer, Product> productHashMap = new HashMap<>();
        Map<Integer, SaleTransaction> transictionHashMap = new HashMap<>();
        for (SaleTransaction saleTransaction : sale.getTransactions()) {
            productHashMap.put(saleTransaction.getProduct().getId(), saleTransaction.getProduct());
            transictionHashMap.put(saleTransaction.getProduct().getId(), saleTransaction);
        }
        Double total = 0D;
        for (SaleTransactionDto saleTransactionDto : updateSaleDto.getTransactions()) {
            Product product = productService.lookupResource(saleTransactionDto.getProductId());

            Product savedProduct = productHashMap.getOrDefault(product.getId(), null);
            if (savedProduct == null) {
                throw new CommonExceptions.BadRequestException(
                        "the product " + product.getName() + " does not exist in this sale"
                );
            }
            SaleTransaction savedSaleTransaction = transictionHashMap.get(product.getId());
            product.setQuantity(product.getQuantity() + savedSaleTransaction.getQuantity());

            if (saleTransactionDto.getQuantity() > product.getQuantity()) {
                throw new CommonExceptions.BadRequestException(
                        product.getQuantity() + " is the only available amount of " + product.getName()
                                + " is not enough quantity for " + saleTransactionDto.getQuantity()
                );
            }

            SaleTransaction saleTransaction = modelMapper.map(saleTransactionDto, SaleTransaction.class);


            double totalPrice = saleTransactionDto.getPrice() * saleTransactionDto.getQuantity();
            total += totalPrice;

            savedSaleTransaction.setProduct(product);
            savedSaleTransaction.setTotal(totalPrice);
            savedSaleTransaction.setQuantity(saleTransaction.getQuantity());

            product.setQuantity(product.getQuantity() - saleTransaction.getQuantity());
//            TODO: implement bulk update to improve performance
            productService.add(product);
        }
        sale.setTotal(total);
        return super.add(sale);
    }

    public ResponseEntity<ResponseDto<SalesReportDto>> report(Instant from, Instant to) {
        Long totalCount = saleRepository.countByCreatedAtBetween(from, to);
        Long totalRevenue = saleRepository.sumTotalByCreatedAtBetween(from, to);
        Map<Long, Long> mostSellingProducts = saleTransactionService.getTopTenMostRepeatedProducts(10);

        SalesReportDto salesResponseDto = SalesReportDto.builder()
                .totalNumber(totalCount)
                .totalRevenue(totalRevenue)
                .build();

        return ResponseDto.response(salesResponseDto);
    }
}
