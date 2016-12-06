package Sessions;

import java.util.List;
import javax.ejb.Remote;

import Beans.ImageBean;

@Remote
public interface ImageRemote {

	public void create(ImageBean image) throws Exception;

	public void edit(ImageBean image) throws Exception;
	
	public void remove(ImageBean image) throws Exception;

	public ImageBean find(Object id);

	public List<ImageBean> findAll() throws Exception;

	public int getLastId();
	
}

