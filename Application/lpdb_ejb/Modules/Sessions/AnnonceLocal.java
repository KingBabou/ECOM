package Sessions;

import java.util.List;
import javax.ejb.Local;

import Beans.AnnonceBean;

@Local
public interface AnnonceLocal {
	
	public void create(AnnonceBean annonce) throws Exception;

	public void edit(AnnonceBean annonce) throws Exception;
	
	public void remove(AnnonceBean annonce) throws Exception;

	public AnnonceBean find(Object id);

	public List<AnnonceBean> findAll() throws Exception;

	public int getLastId();

	public String getPseudoUtilisateur(int id);

	public List<AnnonceBean> recherche(String recherche);
	
}
