package tn.esprit.rh.achat.entities.dto;

import java.util.Set;

import tn.esprit.rh.achat.entities.Produit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategorieProduitDTO  {
	private Long idCategorieProduit;
	private String codeCategorie;
	private String libelleCategorie;
	private Set<Produit> produits;
}
