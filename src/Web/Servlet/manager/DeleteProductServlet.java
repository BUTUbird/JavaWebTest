package Web.Servlet.manager;

import Domain.Product;
import Service.ProductService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

/**
 * @author BUTUbird
 */
@WebServlet(name = "DeleteProductServlet", value = "/DeleteProductServlet")
public class DeleteProductServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求参数的商品id
        String id = req.getParameter("id");
        // 创建ProductService对象
        ProductService service = new ProductService();
        //调用ProductService对象的deleteProduct()方法完成删除商品操作
         service.deleteProduct(id);
        //重定向
        resp.sendRedirect(req.getContextPath()+"/ListProductServlet");
    }
}
