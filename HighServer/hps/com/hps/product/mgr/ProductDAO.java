package  com.hps.product.mgr;

import java.util.List;

import com.newsoft.common.mybatis.BaseDAO;
import com.hps.product.mgr.Product;


public interface ProductDAO extends BaseDAO {
	public List<Product> getProductList();

	public Product getProductById(String id);

	public int addProduct(Product product) throws Exception;
	
	public int updateProduct(Product product) throws Exception;

	public int deleteProductById(String id) throws Exception;	
}
