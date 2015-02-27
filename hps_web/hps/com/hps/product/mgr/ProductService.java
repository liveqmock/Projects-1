package  com.hps.product.mgr;

import java.util.List;
import com.hps.product.mgr.Product;

public interface ProductService {

	public List<Product> getProductList();

	public Product getProductById(String id);

	public boolean addProduct(Product product) throws Exception;
	
	public boolean updateProduct(Product product) throws Exception;

	public boolean deleteProductById(String[] idArray) throws Exception;
}
