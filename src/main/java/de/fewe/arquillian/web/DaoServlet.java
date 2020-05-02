package de.fewe.arquillian.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import de.fewe.arquillian.spring.ApplicationContextProvider;

@WebServlet(name = "DsServlet", urlPatterns = { "/dsquery", "/dsquery/*" })
public class DaoServlet extends HttpServlet {
  private static final long serialVersionUID = 34907831279934994L;
  private static final Logger logger = Logger.getLogger(DaoServlet.class.getName());
  
  private DataSource dataSource;
  {
    logger.info("initialized");
    dataSource = (DataSource) ApplicationContextProvider.getApplicationContext().getBean("myds");
  }
  
  @Override
  protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
    List<Data> empList = new ArrayList<Data>();
    
    // JDBC Code - Start
    //String query = "select messageid, jobid, action from IPL_MESSAGE";
    String query="select * from dual";
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    List<Map<String, Object>> empRows = jdbcTemplate.queryForList(query);
    
//    for (Map<String, Object> empRow : empRows) {
//      Data emp = new Data();
//      emp.setId(Integer.parseInt(String.valueOf(empRow.get("messageid"))));
//      emp.setField1(String.valueOf(empRow.get("jobid")));
//      emp.setField2(String.valueOf(empRow.get("action")));
//      empList.add(emp);
//      logger.info(emp);
//    }
    
    resp.setContentType("text/plain");
    resp.getWriter().print(empRows);
  }
}
