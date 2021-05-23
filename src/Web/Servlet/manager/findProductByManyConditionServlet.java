package Web.Servlet.manager;

import Domain.Product;
import Service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author BUTUbird
 */
@WebServlet(name = "findProductByManyConditionServlet",value = "/findProductByManyConditionServlet")
public class findProductByManyConditionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取表单数据
        String id = req.getParameter("id"); //商品id
        String name = req.getParameter("name"); //商品名称
        String category = req.getParameter("category");//商品类别
        String minPrice = req.getParameter("minPrice");//最小价格
        String maxPrice = req.getParameter("maxPrice");//最大价格
        //创建ProductService对象
        ProductService service = new ProductService();
        //调用Service层用于条件查询的方法
        List<Product> ps = service.findProductByManyCondition(id,name,category,minPrice,maxPrice);
        //将条件查询的结果放进req域中
        req.setAttribute("ps",ps);
        //请求重定向到商品管理首页list.jsp中
        req.getRequestDispatcher("/admin/products/list.jsp").forward(req,resp);
    }
}
