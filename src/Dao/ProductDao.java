package Dao;

import Domain.Product;
import java.sql.SQLException;
import java.util.List;

/**
 * @author BUTUbird
 */
public interface ProductDao {
    /**
     * 查询全部
     * @return 无意
     * @throws SQLException sql
     */
    List<Product> listAll() throws SQLException;
    /**
     * 多条件查询
     * @param id 编号
     * @param name 姓名
     * @param category 种类
     * @param minPrice 最小价格
     * @param maxPrice 最大价格
     * @return 无意
     * @throws SQLException sql
     */
    List<Product> findProductByManyCondition(String id,String name,String category,String minPrice,String maxPrice) throws  SQLException;
    /**
     *  添加
     * @param p Product
     * @throws SQLException sql
     */
    void addProduct(Product p)throws SQLException;
    /**
     *  修改
     * @param p Product
     * @throws SQLException sql
     */
    void editProduct(Product p) throws SQLException;
    /**
     * 根据id查找商品
     * @param id 编号
     * @return 无意
     * @throws SQLException sql
     */
    Product findProductById(String id) throws SQLException ;
    /**
     * 根据id删除商品信息
     * @param id 编号
     * @throws SQLException sql
     */
    void deleteProduct(String id)throws SQLException;
}
