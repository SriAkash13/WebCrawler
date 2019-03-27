
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

import com.ge.model.InputModel;
import com.ge.model.OutputModel;
import com.ge.utility.Utility;

/**
 * @author Akash
 *         This class provides the testing methods for utility class.
 *         The expected results have been written taking 'internet_1.json'
 *         as a input file
 */
public class UtilityTest {


	@Test
	public void testCreateThread() {
		ConcurrentLinkedQueue<String> toBeVisitedQueue = new ConcurrentLinkedQueue<String>();
		Utility utility = new Utility();
		InputModel inputModel = new InputModel();
		Properties properties = new Properties();
		
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties");
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		HashMap<String, List<Object>> hm =utility.createMapFromInput(properties, toBeVisitedQueue);
		inputModel.setMapOfAddressesAndLinks(hm);
		ExecutorService executorService = Executors.newFixedThreadPool(5);
		OutputModel outputModel = new OutputModel(Collections.synchronizedList(new ArrayList<String>()),
				Collections.synchronizedList(new ArrayList<String>()),
				Collections.synchronizedList(new ArrayList<String>()));

		utility.createThreads(toBeVisitedQueue,outputModel, inputModel, executorService );
		String[] successArray = {"http://foo.bar.com/p1", "http://foo.bar.com/p2", "http://foo.bar.com/p4", "http://foo.bar.com/p5", "http://foo.bar.com/p6"};
		String[] skippedArray = {"http://foo.bar.com/p2", "http://foo.bar.com/p4", "http://foo.bar.com/p1", "http://foo.bar.com/p5"};
		String[] errorArray = {"http://foo.bar.com/p3", "http://foo.bar.com/p7"};
		
		
		OutputModel expectedOutput = new OutputModel(Arrays.asList(successArray),Arrays.asList(skippedArray), Arrays.asList(errorArray));
		assertEquals(expectedOutput, outputModel);
		
		
	}

	@Test
	public void testCreateMapFromInput() {
		Properties properties = new Properties();
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties");
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ConcurrentLinkedQueue<String> toBeVisitedQueue = new ConcurrentLinkedQueue<String>();
		Utility utility = new Utility();
		HashMap<String, List<Object>> hm =utility.createMapFromInput(properties, toBeVisitedQueue);
		assertEquals(5, hm.size());
		
	}

}
