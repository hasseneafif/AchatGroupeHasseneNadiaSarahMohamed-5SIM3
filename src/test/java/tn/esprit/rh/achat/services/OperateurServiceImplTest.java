package tn.esprit.rh.achat.services;

import tn.esprit.rh.achat.entities.Facture;
import tn.esprit.rh.achat.entities.Operateur;
import tn.esprit.rh.achat.entities.dto.OperateurDTO;
import tn.esprit.rh.achat.entities.OperateurRepository;
import tn.esprit.rh.achat.services.impl.OperateurServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
public class OperateurServiceImplTest {
    @Mock
    private OperateurRepository operateurRepository;

    @InjectMocks
    private OperateurServiceImpl operateurService;
    private Operateur o1;
    private Facture f1 ;
    private List<Facture> listFacture = new ArrayList<>();
    ModelMapper modelMapper;
    @BeforeEach
    public void init() {

        this.f1 = new Facture();
        this.f1.setIdFacture(0L);
        this.f1.setMontantRemise(20);
        this.f1.setMontantFacture(100);
        this.f1.setArchivee(false);
        this.f1.setDateCreationFacture(new Date(2022, 11, 12));

        this.o1 = new Operateur();
        this.o1.setIdOperateur(0L);
        this.o1.setNom("Nom");
        this.o1.setPrenom("Prenom");
        this.o1.setPassword("password");
        this.listFacture.add(f1);

        this.modelMapper = new ModelMapper();

    }
    @Test
    public void testAddOperateur() {
        Operateur operateur = Operateur.builder()
                //.idOperateur(1L)
                .nom("nom")
                .password("password")
                .prenom("prenom")
                .build();
        Mockito.when(operateurRepository.save(any(Operateur.class))).thenReturn(operateur);
        Operateur operateur1 = operateurService.addOperateur(operateur.toOperateurDTO());

        Assertions.assertThat(operateur1.getNom()).isEqualTo("nom");
        Mockito.verify(operateurRepository).save(any(Operateur.class));
    }

    @Test
    public void testRetrieveAllOperateur() {
        init();
        List<Operateur> list = new ArrayList<>();
        list.add(o1);
        when(operateurRepository.findAll()).thenReturn(list);
        List<Operateur> operateurs = operateurService.retrieveAllOperateurs();
        assertEquals(1, operateurs.size());
        assertNotNull(operateurs);
    }

    @Test
    public void testDeleteStock() {
        init();
        Long OperateurId = 0L;
        when(operateurRepository.findById(anyLong())).thenReturn(Optional.of(o1));
        doNothing().when(operateurRepository).deleteById(anyLong());
        operateurService.deleteOperateur(OperateurId);
        verify(operateurRepository, times(1)).deleteById(anyLong());
    }

    @Test
    public void testUpdateOperateur() {
        init();
        when(operateurRepository.findById(anyLong())).thenReturn(Optional.of(o1));
        when(operateurRepository.save(any(Operateur.class))).thenReturn(o1);
        o1.setPrenom("faza");
        OperateurDTO odt=modelMapper.map(o1, OperateurDTO.class);
        Operateur exisitingOperateur = operateurService.updateOperateur(odt);
        assertNotNull(exisitingOperateur);
        assertEquals("faza", exisitingOperateur.getPrenom());

    }

   @Test
   public void testRetrieveOperateur() {
       init();
       when(operateurRepository.save(any(Operateur.class))).thenReturn(o1);
       OperateurDTO srm=modelMapper.map(o1, OperateurDTO.class);
       Operateur snew=operateurService.addOperateur(srm);
       when(operateurRepository.findById(anyLong())).thenReturn(Optional.of(o1));
       Operateur existingOperateur = operateurService.retrieveOperateur(snew.getIdOperateur());
       assertNotNull(existingOperateur);
       assertThat(existingOperateur.getIdOperateur()).isNotNull();
   }
}
