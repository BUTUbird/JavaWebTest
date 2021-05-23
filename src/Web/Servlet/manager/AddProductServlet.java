package Web.Servlet.manager;
import Domain.Product;
import Service.ProductService;
import Utils.FileUploadUtils;
import Utils.IdUtils;
import exception.AddProductException;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author BUTUbird
 */
@WebServlet(name = "AddProductServlet",value = "/AddProduct")
public class AddProductServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //创建Product对象，用于提交封装的数据
        Product p = new Product();
        Map<String,String> map = new HashMap<String,String>();
        //通过IdUtils工具类生成UUID,封装成商品id
        map.put("id", IdUtils.getUUID());
        DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
        //设置临时文件存储位置
        diskFileItemFactory.setRepository(new File(this.getServletContext().getRealPath("/temp")));
        //设置上传文件缓存为10m
        diskFileItemFactory.setSizeThreshold(1024*1024*10);
        //创建上传文件
        ServletFileUpload upload = new ServletFileUpload(diskFileItemFactory);
        //处理上传文件中文乱码
        upload.setHeaderEncoding("utf-8");
        try{
            //解析request得到所有的FileItem
            List<FileItem> items = upload.parseRequest(req);
            //遍历所有FileItem
            for (FileItem item : items){
                //判断当前是否上传组件
               if (item.isFormField()){
                   //不是上传组件
                   String fieldName = item.getFieldName();
                   //获取组件名称
                   String value = item.getString("utf-8");
                   //解决乱码问题
                   map.put(fieldName, value);
               }else{
                   //是上传组件
                   //得到上传组件的真实名称
                   String fileName = item.getName();
                   fileName = FileUploadUtils.subFileName(fileName);
                   //得到随机名称
                   String randomName = FileUploadUtils.generateRandonFileName(fileName);
                   //得到随机目录
                   Object key;
                   String randomDir = FileUploadUtils.generateRandomDir(randomName);
                   //图片存储父目录
                   String imgUrl_parent = "/productImg" +randomDir;
                   File parentDir = new File(this.getServletContext().getRealPath(imgUrl_parent));
                   //验证目录是否存在，如果没有就创建
                   if (!parentDir.exists()){
                       parentDir.mkdirs();
                   }
                   //拼接图片存放的地址
                   String imgUrl = imgUrl_parent + "/" + randomName;
                   map.put("imgUrl", imgUrl);
                   IOUtils.copy(item.getInputStream(), new FileOutputStream(new File(parentDir,randomName)));
                   item.delete();
               }
            }
        }catch (FileUploadException e){
            e.printStackTrace();
        }
        try {
            //通过BeanUtils工具populate()方法，将数据封装到javaBean中
            BeanUtils.populate(p, map);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        //创建ProductService类的对象
        ProductService service = new ProductService();
        try {
            //调用Service层方法完成添加商业操作
            service.addProduct(p);
            //将请求转发“/listProduct“路径，查询所有商品并显示商品管理页面
            resp.sendRedirect(req.getContextPath()+"/ListProductServlet");
            return;
        }catch (AddProductException e){
            e.printStackTrace();
            resp.getWriter().write("添加商品失败");
            return;
        }

    }
}
