package tn.esprit.rh.achat;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.rh.achat.entities.Stock;
import tn.esprit.rh.achat.repositories.StockRepository;
import tn.esprit.rh.achat.services.StockServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
 public class StockServiceMockTest {

    @InjectMocks
    StockServiceImpl stockService;


    @Mock
    StockRepository stockRepository;

    Stock s = new Stock("Medicaments",100,50);
    Stock s1 = new Stock("Vetements",500,120);
    Stock s2 = new Stock("Equipements",30,5);
    List<Stock> stock = new ArrayList<Stock>() {
        {
            add(s1);
            add(s2);
        }
    };

    

    @Test
    public void testaddStock() {
        Mockito.when(stockRepository.save(Mockito.any(Stock.class))).thenReturn(s);
        assertNotNull(stockService.addStock(s));
        verify(stockRepository).save(s);
    }

    @Test
    public void testdeleteStock() {
        stockService.deleteStock((long)3);
        verify(stockRepository).deleteById((long)3);
    }

    @Test
    public void testupdateStock() {
        Mockito.when(stockRepository.save(Mockito.any(Stock.class))).thenReturn(s);
        s.setQte(55);
        assertNotNull(stockService.updateStock(s));
        assertEquals(Optional.of(55), s.getQte());
    }
}