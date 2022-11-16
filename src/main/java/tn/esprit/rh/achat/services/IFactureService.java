package tn.esprit.rh.achat.services;

import java.util.Date;
import java.util.List;
import tn.esprit.rh.achat.entities.Facture;
import tn.esprit.rh.achat.dto.FactureDTO;

public interface IFactureService {
	List<Facture> retrieveAllFactures();

	List<Facture> getFacturesByFournisseur(Long idFournisseur);

	Facture addFacture(FactureDTO f);

	void cancelFacture(Long id);

	Facture retrieveFacture(Long id);
	
	void assignOperateurToFacture(Long idOperateur, Long idFacture);

	float pourcentageRecouvrement(Date startDate, Date endDate);

}
