package PerformanceTest;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jy.dao.UserDao;
import com.jy.dao.UserLocationDao;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:test.xml" })
public class SqrtTest {

	@Autowired
	private UserLocationDao userAddressDao;
	
	@Autowired
	private UserDao userDao;
	
	@Test
	public void test() {
		/*
		for (int i = 0; i < 100000; i++) {
			UserAddress ua = new UserAddress();
			ua.setUserId(72L);
			ua.setLatitude(Double.parseDouble("36."+i));
			ua.setLongitude(Double.parseDouble("-121."+i));
			userAddressDao.create(ua);
		}
		*/
		long startTime = new Date().getTime();
//		userDao.getUserProfileList(21.282778, -157.829444, 11, 10);
		System.out.println((new Date().getTime() - startTime)/1000);
	}

}
