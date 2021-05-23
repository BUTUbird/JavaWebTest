package Web.Servlet;
import Domain.Product;
import Service.ProductService;
import exception.ListProductException;

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
@WebServlet(name = "ListProductServlet",value = "/ListProductServlet")
public class ListProductServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //创建Servlet层的对象
        ProductService service = new ProductService();
        try{
            //调用Service层用于查询的所有商品的listAll方法
            List<Product> ps = service.listAll();

            //将所有查询到的商品放进request域中
            req.setAttribute("ps",ps);
            //将请求转发到list.jsp
            req.getRequestDispatcher("/admin/products/list.jsp").forward(req,resp);
            return;
        }catch (ListProductException e){
            e.printStackTrace();
            resp.getWriter().write(e.getMessage());
            return;
        }
    }
}
