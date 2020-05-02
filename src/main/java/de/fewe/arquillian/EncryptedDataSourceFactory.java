package de.fewe.arquillian;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Properties;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.naming.Context;
import javax.sql.DataSource;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.tomcat.jdbc.pool.DataSourceFactory;
import org.apache.tomcat.jdbc.pool.PoolConfiguration;
import org.apache.tomcat.jdbc.pool.XADataSource;

public class EncryptedDataSourceFactory extends DataSourceFactory {
  
  private static final Log logger = LogFactory.getLog(EncryptedDataSourceFactory.class);
  
  private Encryptor encryptor = null;
  
  public EncryptedDataSourceFactory() {
    encryptor = new Encryptor(); // If you've used your own secret key, pass it in...
    
  }
  
  @Override
  public DataSource createDataSource(Properties properties, Context context, boolean XA)
      throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, SQLException, NoSuchAlgorithmException, NoSuchPaddingException {
    // Here we decrypt our password.
    logger.info("createDataSource  properties:" + properties);
    PoolConfiguration poolProperties = EncryptedDataSourceFactory.parsePoolProperties(properties);
    String value = encryptor.decrypt(poolProperties.getPassword());
    logger.info("decrypted:" + value); // natürlich nur als demo!!! Nicht im produktivem code
    poolProperties.setPassword(value);
    
    // The rest of the code is copied from Tomcat's DataSourceFactory.
    if (poolProperties.getDataSourceJNDI() != null && poolProperties.getDataSource() == null) {
      performJNDILookup(context, poolProperties);
    }
    org.apache.tomcat.jdbc.pool.DataSource dataSource = XA ? new XADataSource(poolProperties) : new org.apache.tomcat.jdbc.pool.DataSource(poolProperties);
    dataSource.createPool();
    
    return dataSource;
  }
  
}