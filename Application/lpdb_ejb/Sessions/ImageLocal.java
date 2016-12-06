package Sessions;

import java.util.List;
import javax.ejb.Local;

import Beans.ImageBean;

@Local
public interface ImageLocal {

	public void create(ImageBean image) throws Exception;

	public void edit(ImageBean image) throws Exception;
	
	public void remove(ImageBean image) throws Exception;

	public ImageBean find(Object id);

	public List<ImageBean> findAll() throws Exception;

	public int getLastId();
	
}
