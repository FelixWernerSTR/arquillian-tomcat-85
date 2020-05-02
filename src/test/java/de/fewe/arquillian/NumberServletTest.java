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
public class NumberServletTest implements Serializable {
	
  private static final long serialVersionUID = -7386306336804828623L;
  
  
  @Deployment(name = "war", testable = true, managed = true)
  public static Archive<?> createTestArchive() {
    final WebArchive war = ShrinkWrap.create(WebArchive.class, "demo-web.war")
    		.setWebXML("no-spring-web.xml").addAsWebResource("index.html")
        .addAsResource("log4j.properties")
        .addClass(NumberServlet.class);
    
    JavaArchive jar = ShrinkWrap.create(JavaArchive.class, "demo-lib.jar").addClass(DemoLib.class).addClass(NumberServletTest.class)
        .addClass(FailingHttpStatusCodeException.class).addClass(ApplicationContextProvider.class);
    
    war.addAsLibrary(jar);
    
    File[] libs = Maven.configureResolver().loadPomFromFile("src/main/resources/pom.xml").importDependencies(ScopeType.COMPILE).resolve().withoutTransitivity()
        .asFile();
    war.addAsLibraries(libs);
    
    return war;
  }
  
  @Test
  @OperateOnDeployment("war")
  @RunAsClient
  public void testNumberServlet(@ArquillianResource URL context) throws Exception {
    {
      final WebClient webClient = new WebClient();
      final WebRequest requestSettings = new WebRequest(new URL(context + "number"), HttpMethod.GET);
      Page page = webClient.getPage(requestSettings);
      Assert.assertEquals("MUST be OK.", page.getWebResponse().getStatusCode(), HttpURLConnection.HTTP_OK);
      Assert.assertEquals("MUST be 0", Integer.parseInt(page.getWebResponse().getContentAsString()), 0);
      page = webClient.getPage(requestSettings);
      Assert.assertEquals("MUST be 1", Integer.parseInt(page.getWebResponse().getContentAsString()), 1);
    }
  }
  
  @Test
  public void checkSessionStore() throws Exception {
    final ObjectName on = new ObjectName("Catalina:type=Manager,context=/demo-web,host=localhost");
    Assert.assertEquals(ManagementFactory.getPlatformMBeanServer().getAttribute(on, "sessionCounter"), 1L);
  }
  
}
