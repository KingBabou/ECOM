package Sessions;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.NoResultException;

import Beans.ImageBean;

@Stateless
public class ImageServiceBean implements ImageLocal, ImageRemote {

	private Logger log = Logger.getLogger(this.getClass().getName());

	@PersistenceContext(unitName = "LPDB_Database")
	private EntityManager entityManager;
	
	public void create(ImageBean image) throws Exception {
		this.entityManager.persist(image);
	}

	public void edit(ImageBean image) throws Exception {
		this.entityManager.merge(image);
	}
	
	public void remove(ImageBean image) throws Exception {
		ImageBean i = this.entityManager.merge(image);
		this.entityManager.remove(i);
	}

	public ImageBean find(Object id) {
		return this.entityManager.find(ImageBean.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<ImageBean> findAll() throws Exception {
		return this.entityManager.createQuery(
			"SELECT i FROM ImageBean i"
		).getResultList();
	}

	public int getLastId() {
		return (int)this.entityManager.createNativeQuery(
			"SELECT MAX(ID) FROM IMAGE"
		).getSingleResult() + 1;
	}

}	
