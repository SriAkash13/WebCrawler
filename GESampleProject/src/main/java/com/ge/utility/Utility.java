package com.ge.utility;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.ge.constants.WebCrawlerConstants;
import com.ge.crawler.CrawlThread;
import com.ge.model.InputModel;
import com.ge.model.OutputModel;

/**
 * @author Akash T
 * 		   This class provides methods for reading property file,
 *         converting input json to hash map and creating threads based on
 *         provided queue
 *
 */
public class Utility {

	public Properties readProperties() {
		Properties properties = new Properties();
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties");
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}

	public OutputModel createThreads(ConcurrentLinkedQueue<String> toBeVisitedQueue, OutputModel outputModel,
			InputModel inputModel, ExecutorService executorService) {
		while (!toBeVisitedQueue.isEmpty()) {
			executorService.submit(new CrawlThread(toBeVisitedQueue.poll(), outputModel, toBeVisitedQueue, inputModel));
			synchronized (toBeVisitedQueue) {
				try {
					toBeVisitedQueue.wait(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		return outputModel;
	}

	public HashMap<String, List<Object>> createMapFromInput(Properties properties,
			ConcurrentLinkedQueue<String> toBeVisitedQueue) {
		String rootUrl = null;
		String contentFromInputFile = null;
		try {
			contentFromInputFile = FileUtils.readFileToString(new File(properties.getProperty("input_File_Location")),
					"UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}

		JSONObject jsonObject = new JSONObject(contentFromInputFile);
		JSONArray pageArray = jsonObject.getJSONArray(WebCrawlerConstants.PAGES);
		JSONObject page = null;

		rootUrl = (String) (pageArray.getJSONObject(0)).get(WebCrawlerConstants.ADDRESS);
		// add the first address to the queue
		toBeVisitedQueue.offer(rootUrl);

		HashMap<String, List<Object>> mapOfAddressesAndLinks = new HashMap<String, List<Object>>();
		// converts the given input json array to hashmap
		for (int i = 0; i < pageArray.length(); i++) {
			page = pageArray.getJSONObject(i);
			String address = page.getString(WebCrawlerConstants.ADDRESS);
			List<Object> links = page.getJSONArray(WebCrawlerConstants.LINKS).toList();
			mapOfAddressesAndLinks.put(address, links);
		}
		return mapOfAddressesAndLinks;
	}
}
