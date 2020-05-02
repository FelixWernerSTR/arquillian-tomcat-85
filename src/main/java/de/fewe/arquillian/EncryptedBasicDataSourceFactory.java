package de.fewe.arquillian;

import java.util.Properties;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.apache.tomcat.dbcp.dbcp2.BasicDataSourceFactory;


public class EncryptedBasicDataSourceFactory extends BasicDataSourceFactory{

	private static final Log logger = LogFactory.getLog(EncryptedBasicDataSourceFactory.class);
	
	private static Encryptor encryptor = new Encryptor();
	
    public EncryptedBasicDataSourceFactory() {
        encryptor = new Encryptor(); // If you've used your own secret key, pass it in...

    }
	
	public static BasicDataSource createDataSource(final Properties properties) throws Exception {
		final BasicDataSource dataSource = BasicDataSourceFactory.createDataSource(properties);
		logger.info("createDataSource  properties:" +properties);
		System.out.println("createDataSource  properties:" +properties);
		String value = encryptor.decrypt(properties.getProperty("password"));
		logger.info("decrypted:" +value);
		System.out.println("decrypted:" +value);
		if (value != null) {
            dataSource.setPassword(value);
        }
		return dataSource;
		
	}
}
