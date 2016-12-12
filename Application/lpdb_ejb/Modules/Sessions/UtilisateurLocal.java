package Sessions;

import java.util.List;
import javax.ejb.Local;

import Beans.UtilisateurBean;

@Local
public interface UtilisateurLocal {
	
	public void create(UtilisateurBean utilisateur) throws Exception;

	public void edit(UtilisateurBean utilisateur) throws Exception;
	
	public void remove(UtilisateurBean utilisateur) throws Exception;

	public UtilisateurBean find(Object id);

	public List<UtilisateurBean> findAll() throws Exception;
	
	public UtilisateurBean findUserByPseudo(String pseudonyme);
	
	public UtilisateurBean findUser(int id);
	
	public int getLastId();
	
	public boolean pseudoIsAvailable(String pseudonyme);
	
}
