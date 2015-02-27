package  com.hps.product.mgr;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hps.product.mgr.Product;

@Service
public class ProductServiceImpl implements ProductService {
	private Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
	
	@Autowired
	private ProductDAO productDAO;
	
	public List<Product> getProductList(){
		try{
		     return productDAO.getProductList();
		}
		catch(Exception e){
			logger.error("Error when query Product list data",e);
		}
		return null;
	}

	public Product getProductById(String id){
	   try{
		 return productDAO.getProductById(id);
	    }
	    catch(Exception e){
		  logger.error("Error when query Product by id: "+id,e);		
		}
		return null;
	}

	public boolean addProduct(Product product) throws Exception {
		return productDAO.addProduct(product) > 0;
	}
	
	public boolean updateProduct(Product product) throws Exception {
		 return productDAO.updateProduct(product) > 0;	    
	}

	public boolean deleteProductById(String[] idArray) throws Exception {
		boolean result = true;
		for (String id : idArray) {
			if (id == null || id.trim().equals("")) {
				continue;
			}
			result = productDAO.deleteProductById(id) > 0;
			if (!result) {
				throw new RuntimeException("Force to rollback the transaction when failed to delete data");
			}
		}
		return result;
	}	
}
