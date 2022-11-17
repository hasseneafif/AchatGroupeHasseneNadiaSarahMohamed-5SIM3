package tn.esprit.rh.achat;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;




import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit4.SpringRunner;

import tn.esprit.rh.achat.entities.Stock;
import tn.esprit.rh.achat.repositories.StockRepository;
import tn.esprit.rh.achat.services.StockServiceImpl;



@RunWith(SpringRunner.class)
@SpringBootTest
public class StockServiceMockTest {
	
	
	@Mock
    StockRepository stockRepository;
	
    @InjectMocks
    StockServiceImpl stockService;
    
    
   
    
    
    @Test
	public void retrieveAllStockTest() {
		when(stockRepository.findAll()).thenReturn(Stream.of(
                new Stock("libelleStock2", 20, 5),
                new Stock("libelleStock3", 2055, 555),
                new Stock("libelleStock3", 2550, 5))
                .collect(Collectors.toList()));
		assertEquals(3,stockService.retrieveAllStocks().size());
		
	}
    
    @Test
	public void addStockTest() {
    	Stock ss = new Stock("libelleStock2", 20, 5);
		when(stockRepository.save(ss)).thenReturn(ss);
		assertEquals(ss, stockService.addStock(ss));
	}
    
    
    @Test
	public void deleteStockTest() {
    	Stock ss = new Stock("libelleStock2", 20, 5);
    	stockService.deleteStock((long) 1);
		verify(stockRepository).deleteById((long) 1);

	}
    
    @Test
	public void updatetStockTest() {
    	Stock ss = new Stock("libelleStock2", 20, 5) ;
		Mockito.when(stockRepository.save(Mockito.any(Stock.class))).thenReturn(ss);
		ss.setLibelleStock("mohamed");
		Stock exisitingOp= stockService.updateStock(ss) ;
		
		assertNotNull(exisitingOp);
		assertEquals("mohamed", ss.getLibelleStock());
	}
    
   
    

    

}