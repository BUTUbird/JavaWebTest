package Dao.impl;

import Dao.ProductDao;
import Domain.Product;
import Utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author BUTUbird
 */
public class ProductDaoImpl implements ProductDao {
    /**
     * 查询全部
     * @return
     * @throws SQLException
     */
    @Override
    public List<Product> listAll() throws SQLException{
        String sql = "select *from products";
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        return runner.query(sql,new BeanListHandler<Product>(Product.class));
    }

    /**
     * 多条件查询
     * @param id 编号
     * @param name 姓名
     * @param category 种类
     * @param minPrice 最小价格
     * @param maxPrice 最大价格
     * @return 无意
     * @throws SQLException
     */
    @Override
    public List<Product> findProductByManyCondition(String id, String name, String category, String minPrice, String maxPrice) throws  SQLException{
        List<Object> list = new ArrayList<Object>();
        String sql = "select *from products where 1=1 ";
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        //商品编号
        if (id != null && id.trim().length()>0){
            sql += "and id =?";
            list.add(id);
        }
        //商品名称
        if (name != null && name.trim().length()>0){
            sql += "and name =?";
            list.add(name);
        }
        //商品类别
        if (category != null && category.trim().length()>0){
            sql += "and category =?";
            list.add(category);
        }
        //价格区间
        if (minPrice != null && maxPrice != null && minPrice.trim().length()>0 && maxPrice.trim().length()>0){
            sql += "and price between ? and ?";
            list.add(minPrice);
            list.add(maxPrice);
        }
        //将集合转为对象数组类型
        Object[] params = list.toArray();
        return runner.query(sql,new BeanListHandler<Product>(Product.class),params);
    }

    /**
     *  添加
     * @param p
     * @throws SQLException
     */
    @Override
    public void addProduct(Product p)throws SQLException{
        String sql = "insert into products values(?,?,?,?,?,?,?)";
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        runner.update(sql,p.getId(),p.getName(),p.getPrice(),p.getCategory(),p.getPrice(),p.getImgurl(),p.getDescription());

    }

    /**
     *  修改
     * @param p
     * @throws SQLException
     */
    @Override
    public void editProduct(Product p) throws SQLException{
        //创建集合并将商品信息添加到集合中
        List<Object> obj = new ArrayList<Object>();
        obj.add(p.getName());
        obj.add(p.getPrice());
        obj.add(p.getCategory());
        obj.add(p.getPnum());
        obj.add(p.getDescription());
        //创建sql语句，并拼接sql
        String sql = "update products "+"set name =?,price=?,category=?,pnum=?,description=?";
        //判断是否有图片
        if (p.getImgurl() != null && p.getImgurl().trim().length()>0){
            sql += " ,imgurl=?";
            obj.add(p.getImgurl());
        }
        sql += " where id = ?";
        obj.add(p.getId());
        //创建QueryRunner对象
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        //使用QueryRunner对象的update方法更新数据
        runner.update(sql, obj.toArray());
    }


    /**
     * 根据id查找商品
     * @param id 编号
     * @return
     * @throws SQLException
     */
    @Override
    public Product findProductById(String id) throws SQLException {
        String sql = "select * from products where id=?";
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        return runner.query(sql, new BeanHandler<Product>(Product.class), id);
    }
    /**
     * 根据id删除商品信息
     * @param id 编号
     * @throws SQLException
     */
    @Override
    public void deleteProduct(String id)throws SQLException{
        String sql = "delete from products where id =?";
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        runner.update(sql, id);
    }
}
