package de.fewe.arquillian;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

public class Encryptor {
  private static final Log logger = LogFactory.getLog(Encryptor.class);
  
  public String decrypt(String password) {
    // TODO hier kommt noch Jysypt zum Einsatz:
    // erstmal Dummy-Implementierung
    logger.info("decrypt :" + password);
    String decrypted = "sidney";
    
    logger.info("decrypted :" + decrypted); // natürlich nur als demo!!! Nicht im produktivem code
    return decrypted;
  }
  
}
