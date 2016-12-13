package Sessions;

import java.util.List;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.NoResultException;

import Beans.AnnonceBean;
import Beans.UtilisateurBean;

@Stateless
public class AnnonceServiceBean implements AnnonceLocal, AnnonceRemote {

	private Logger log = Logger.getLogger(this.getClass().getName());
	
	@PersistenceContext(unitName = "LPDB_Database")
	private EntityManager entityManager;
	
	public void create(AnnonceBean annonce) throws Exception {
		this.entityManager.persist(annonce);
	}

	public void edit(AnnonceBean annonce) throws Exception {
		this.entityManager.merge(annonce);
	}
	
	public void remove(AnnonceBean annonce) throws Exception {
		AnnonceBean a = this.entityManager.merge(annonce);
		this.entityManager.remove(a);
	}

	public AnnonceBean find(Object id) {
		return this.entityManager.find(AnnonceBean.class, id);
	}
	
	public List<AnnonceBean> findAnnoncesByPseudo(String pseudo){
		
		List<AnnonceBean> annonces = new ArrayList<AnnonceBean>(); 
		
		for(AnnonceBean annonce : this.findAll()){
			if(getPseudoUtilisateur(annonce.getIdUtilisateur()).equals(pseudo)){
				annonces.add(annonce);
			}
		}
		
		return annonces;		
	}

	public String getPseudoUtilisateur(int id){
		try {
			return ((UtilisateurBean)this.entityManager.createQuery(
				"SELECT u FROM UtilisateurBean u WHERE u.id = :id"
			).setParameter("id", id).getSingleResult()).getPseudonyme();
		} catch (NoResultException e) {
			return (String)null;
		}
	}
	
	public UtilisateurBean findUserByPseudo(String pseudonyme) {
		try {
			return (UtilisateurBean)this.entityManager.createQuery(
				"SELECT u FROM UtilisateurBean u WHERE u.pseudonyme = :pseudonyme"
			).setParameter("pseudonyme", pseudonyme).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
		

	@SuppressWarnings("unchecked")
	public List<AnnonceBean> findAll() {
		try {
			return this.entityManager.createQuery(
				"SELECT a FROM AnnonceBean a"
			).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	public int getLastId() {
		return (int)this.entityManager.createNativeQuery(
			"SELECT MAX(ID) FROM ANNONCE"
		).getSingleResult() + 1;
	}
	
}

