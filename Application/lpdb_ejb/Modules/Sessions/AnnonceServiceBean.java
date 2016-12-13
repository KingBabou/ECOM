package Sessions;

import java.util.List;
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

	public String getPseudoUtilisateur(int id){
		try {
			return ((UtilisateurBean)this.entityManager.createQuery(
				"SELECT u FROM UtilisateurBean u WHERE u.id = :id"
			).setParameter("id", id).getSingleResult()).getPseudonyme();
		} catch (NoResultException e) {
			return (String)null;
		}
	}
		

	@SuppressWarnings("unchecked")
	public List<AnnonceBean> findAll() throws Exception {
		return this.entityManager.createQuery(
			"SELECT a FROM AnnonceBean a"
		).getResultList();
	}

	public int getLastId() {
		return (int)this.entityManager.createNativeQuery(
			"SELECT MAX(ID) FROM ANNONCE"
		).getSingleResult() + 1;
	}

	public List<AnnonceBean> recherche(String recherche) {
		return this.entityManager.createQuery(
			"SELECT * FROM ANNONCE WHERE TITRE LIKE '%" + recherche + "%'"
		).getResultList();
	}
	
}

