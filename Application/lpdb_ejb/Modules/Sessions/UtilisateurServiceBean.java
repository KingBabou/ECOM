package Sessions;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.NoResultException;

import Beans.UtilisateurBean;

@Stateless
public class UtilisateurServiceBean implements UtilisateurLocal, UtilisateurRemote {

	private Logger log = Logger.getLogger(this.getClass().getName());

	@PersistenceContext(unitName = "LPDB_Database")
	private EntityManager entityManager;
	
	public void create(UtilisateurBean utilisateur) throws Exception {
		this.entityManager.persist(utilisateur);
	}

	public void edit(UtilisateurBean utilisateur) throws Exception {
		this.entityManager.merge(utilisateur);
	}
	
	public void remove(UtilisateurBean utilisateur) throws Exception {
		UtilisateurBean u = this.entityManager.merge(utilisateur);
		this.entityManager.remove(u);
	}

	public UtilisateurBean find(Object id) {
		return this.entityManager.find(UtilisateurBean.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<UtilisateurBean> findAll() throws Exception {
		return this.entityManager.createQuery(
			"SELECT u FROM UtilisateurBean u"
		).getResultList();
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

	public UtilisateurBean findUser(int id) {
		try {
			return (UtilisateurBean)this.entityManager.createQuery(
				"SELECT u FROM UtilisateurBean u WHERE u.id = :id"
			).setParameter("id", id).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public int getLastId() {
		return (int)this.entityManager.createNativeQuery(
			"SELECT MAX(ID) FROM UTILISATEUR"
		).getSingleResult() + 1;
	}

	public boolean pseudoIsAvailable(String pseudonyme) {
		return ((long)this.entityManager.createNativeQuery(
			"SELECT COUNT(*) FROM UTILISATEUR WHERE PSEUDONYME = ?1"
		).setParameter(1, pseudonyme).getSingleResult() == 0);
	}
	
}
