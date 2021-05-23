package Service;

import Dao.ProductDao;
import Dao.impl.ProductDaoImpl;
import Domain.Product;
import Utils.DataSourceUtils;
import exception.AddProductException;
import exception.FindProductByIdException;
import exception.ListProductException;
import org.apache.commons.dbutils.QueryRunner;


import java.sql.SQLException;
import java.util.List;

public class ProductService {

   private ProductDao productDao = new ProductDaoImpl();

    /**
     * 显示所有
     * @return
     * @throws ListProductException
     */
   public List<Product> listAll() throws ListProductException {
     try{
        return  productDao.listAll();
     } catch (SQLException e) {
        e.printStackTrace();
        throw new ListProductException("商品查询失败");
     }
   }

    /**
     * 按需查找
     * @param id
     * @param name
     * @param category
     * @param minPrice
     * @param maxPrice
     * @return
     */
   public List<Product> findProductByManyCondition(String id,String name,String category,String minPrice,String maxPrice){
       List<Product> ps = null;
       try{
           ps = productDao.findProductByManyCondition(id, name, category, minPrice, maxPrice);
       }catch (SQLException e){
           e.printStackTrace();
       }
       return ps;
   }

    /**
     * 添加
     * @param p
     * @throws AddProductException
     */
   public void addProduct(Product p) throws AddProductException{
       try{
           productDao.addProduct(p);
       }catch (SQLException e){
           throw new AddProductException("商品添加失败");
       }
   }

    /**
     * 编辑
     * @param p
     */
 public void editProduct(Product p){
       try{
         productDao.editProduct(p);
       }catch (SQLException e){
           e.printStackTrace();
       }
 }

    /**
     * 根据id查找商品
     * @param id 编号
     * @return
     * @throws FindProductByIdException
     */
    public Product findProductById(String id) throws FindProductByIdException {
        try {
            return productDao.findProductById(id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new FindProductByIdException("根据ID查找商品失败");
        }
    }

    /**
     * 根据id删除商品信息
     * @param id 编号
     * @throws SQLException
     */
    public void deleteProduct(String id){
        try{
            productDao.deleteProduct(id);
        }catch (SQLException e){
            throw new RuntimeException("后台系统根据id删除商品信息失败！");
        }
    }

}
