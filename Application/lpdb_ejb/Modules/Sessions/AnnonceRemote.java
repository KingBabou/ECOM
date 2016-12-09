package Sessions;

import java.util.List;
import javax.ejb.Remote;

import Beans.AnnonceBean;

@Remote
public interface AnnonceRemote {
	
	public void create(AnnonceBean annonce) throws Exception;

	public void edit(AnnonceBean annonce) throws Exception;
	
	public void remove(AnnonceBean annonce) throws Exception;

	public AnnonceBean find(Object id);

	public List<AnnonceBean> findAll() throws Exception;

	public int getLastId();

	public String getPseudoUtilisateur(int id);
	
}
