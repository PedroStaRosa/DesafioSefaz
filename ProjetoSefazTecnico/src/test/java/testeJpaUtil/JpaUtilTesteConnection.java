package testeJpaUtil;

import org.junit.Test;

import util.JpaUtil;

public class JpaUtilTesteConnection {

	@Test
	public void connectionTest() {
		
		JpaUtil.getEntityManager();
		
	}	
	
}
