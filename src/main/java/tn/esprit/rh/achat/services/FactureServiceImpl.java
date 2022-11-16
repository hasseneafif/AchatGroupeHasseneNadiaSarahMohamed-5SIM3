package tn.esprit.rh.achat.services;

import tn.esprit.rh.achat.entities.Facture;
import tn.esprit.rh.achat.entities.Fournisseur;
import tn.esprit.rh.achat.entities.Operateur;
import tn.esprit.rh.achat.dto.FactureDTO;
import tn.esprit.rh.achat.repositories.*;
import tn.esprit.rh.achat.services.IFactureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@Transactional
public class FactureServiceImpl implements IFactureService {

	@Autowired
	FactureRepository factureRepository;
	@Autowired
	OperateurRepository operateurRepository;
	@Autowired
	DetailFactureRepository detailFactureRepository;
	@Autowired
	FournisseurRepository fournisseurRepository;
	@Autowired
	ProduitRepository produitRepository;
    @Autowired
    ReglementServiceImpl reglementService;
	

	@Autowired
	ReglementRepository reglementRepository;

	@Override
	public List<Facture> retrieveAllFactures() {
		List<Facture> factures = factureRepository.findAll();
		for (Facture facture : factures) {
		}
		return factures;
	}

	public Facture addFacture(FactureDTO f) {

		return factureRepository.save(Facture.builder()
				.reglements(f.getReglements())
				.montantRemise(f.getMontantRemise())
				.montantFacture(f.getMontantFacture())
				.dateCreationFacture(f.getDateCreationFacture())
				.dateDerniereModificationFacture(f.getDateDerniereModificationFacture())
				.archivee(f.getArchivee())
				.detailsFacture(f.getDetailsFacture())
				.fournisseur(f.getFournisseur())
				.build());
	}

	@Override
	public void cancelFacture(Long factureId) {
		Facture facture = factureRepository.findById(factureId).orElse(new Facture());
		facture.setArchivee(true);
		factureRepository.save(facture);
		factureRepository.updateFacture(factureId);
	}

	@Override
	public Facture retrieveFacture(Long factureId) {

		Facture facture = factureRepository.findById(factureId).orElse(null);
		log.info("facture :" + facture);
		return facture;
	}

	@Override
	public List<Facture> getFacturesByFournisseur(Long idFournisseur) {
		return (List<Facture>) fournisseurRepository.findById(idFournisseur).orElse(new Fournisseur()).getFactures();
	}

	@Override
	public void assignOperateurToFacture(Long idOperateur, Long idFacture) {
		Facture facture = factureRepository.findById(idFacture).orElse(null);
		Operateur operateur = operateurRepository.findById(idOperateur).orElse(new Operateur());
		operateur.getFactures().add(facture);
		operateurRepository.save(operateur);
	}

	@Override
	public float pourcentageRecouvrement(Date startDate, Date endDate) {
		float totalFacturesEntreDeuxDates = factureRepository.getTotalFacturesEntreDeuxDates(startDate,endDate);
		float totalRecouvrementEntreDeuxDates =reglementRepository.getChiffreAffaireEntreDeuxDate(startDate,endDate);
		return (totalRecouvrementEntreDeuxDates/totalFacturesEntreDeuxDates)*100;
	}

}