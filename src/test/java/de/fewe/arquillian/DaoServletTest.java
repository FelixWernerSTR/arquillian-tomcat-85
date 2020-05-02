package de.fewe.arquillian;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.management.ManagementFactory;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javax.management.ObjectName;

import org.jboss.arquillian.container.test.api.BeforeDeployment;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OperateOnDeployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.ScopeType;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;

import de.fewe.arquillian.spring.ApplicationContextProvider;
import de.fewe.arquillian.web.Data;
import de.fewe.arquillian.web.DaoServlet;
import de.fewe.arquillian.web.NumberServlet;

@RunWith(Arquillian.class)
public class DaoServletTest implements Serializable {
	
  
  private static final long serialVersionUID = -7386306336804828623L;
  
	@BeforeDeployment
	public static void copyContextXml() {
		File source = new File("src/main/resources/demo-web.xml");
		File dest = new File(System.getenv("CATALINA_HOME") + "/conf/Catalina/localhost/demo-web.xml");
		try {
			Files.copy(source.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("copy demo-web.xml");
	}
  
  @Deployment(name = "war", testable = true, managed = true)
  public static Archive<?> createTestArchive() {
	  copyContextXml();
    final WebArchive war = ShrinkWrap.create(WebArchive.class, "demo-web.war").setWebXML("web.xml").addAsWebResource("index.html")
        .addAsResource("log4j.properties").addAsResource("springApplicationContext.xml")
        .addClass(DaoServletTest.class)
        .addClass(DaoServlet.class);
    
    JavaArchive jar = ShrinkWrap.create(JavaArchive.class, "demo-lib.jar").addClass(DemoLib.class)
        .addClass(FailingHttpStatusCodeException.class).addClass(Data.class).addClass(ApplicationContextProvider.class);
    
    war.addAsLibrary(jar);
    
    File[] libs = Maven.configureResolver().loadPomFromFile("src/main/resources/pom.xml").importDependencies(ScopeType.COMPILE).resolve().withoutTransitivity()
        .asFile();
    war.addAsLibraries(libs);
    
    return war;
  }
  
  @Test
  @OperateOnDeployment("war")
  @RunAsClient
  public void testDaoServlet(@ArquillianResource URL context) throws Exception {
    {
      final WebClient webClient = new WebClient();
      final WebRequest requestSettings = new WebRequest(new URL(context + "dsquery"), HttpMethod.GET);
      Page page = webClient.getPage(requestSettings);
      Assert.assertEquals("MUST be OK.", page.getWebResponse().getStatusCode(), HttpURLConnection.HTTP_OK);
    }
  }
  
}
