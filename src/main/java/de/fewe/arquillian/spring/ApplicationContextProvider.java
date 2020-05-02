package de.fewe.arquillian.spring;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextProvider implements ApplicationContextAware {
  private static final Logger logger = Logger.getLogger(ApplicationContextProvider.class.getName());
  
  private static ApplicationContext context;
  {
    logger.info("initialized");
  }
  
  public static ApplicationContext getApplicationContext() {
    return context;
  }
  
  @Override
  public void setApplicationContext(ApplicationContext ac) {
    logger.info("setApplicationContext");
    context = ac;
  }
}
