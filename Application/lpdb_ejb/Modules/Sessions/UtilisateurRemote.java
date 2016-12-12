package Sessions;

import java.util.List;
import javax.ejb.Remote;

import Beans.UtilisateurBean;

@Remote
public interface UtilisateurRemote {
	
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
