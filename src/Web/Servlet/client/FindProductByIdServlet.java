package Web.Servlet.client;

import Domain.Product;
import Service.ProductService;
import exception.FindProductByIdException;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.lang.module.FindException;

/**
 * @author BUTUbird
 */
@WebServlet(name = "FindProductByIdServlet", value = "/FindProductByIdServlet")
public class FindProductByIdServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
           doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
         //获取商品id
        String id = req.getParameter("id");
        //获取typ参数值，此处的type用来区分请求来自前台网站还是后台系统
        String type = req.getParameter("type");
        //创建Service对象
        ProductService service = new ProductService();
        try{
            //调用service层方法，通过id查找商品
            Product p = service.findProductById(id);
            req.setAttribute("p", p);
            //前台网站不传递type值，会跳转到前台网站的商品详细信息info.jsp;
            if (type == null){
                req.getRequestDispatcher("/client/info.jsp").forward(req, resp);
                return;
            }
            //如果请求来自后台系统，跳转到后台系统的商品编辑页面edit.jsp
            req.getRequestDispatcher("/admin/products/edit.jsp").forward(req, resp);
            return;
        }catch (FindProductByIdException e){
            e.printStackTrace();
        }

    }
}
