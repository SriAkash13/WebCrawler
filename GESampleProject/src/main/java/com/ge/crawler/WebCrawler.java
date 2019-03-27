package com.ge.crawler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.ge.model.InputModel;
import com.ge.model.OutputModel;
import com.ge.utility.Utility;

/**
 * @author Akash
 * 
 *         This class calls different methods from utility class to read
 *         properties file, reading all the addresses and links from the input
 *         file into hash map and create crawling threads for all the urls
 *         available in queue.
 */
public class WebCrawler {

	public static void main(String[] args) {

		InputModel inputModel = new InputModel();
		Utility utility = new Utility();
		ConcurrentLinkedQueue<String> toBeVisitedQueue = new ConcurrentLinkedQueue<String>();
		OutputModel outputModel = new OutputModel(Collections.synchronizedList(new ArrayList<String>()),
				Collections.synchronizedList(new ArrayList<String>()),
				Collections.synchronizedList(new ArrayList<String>()));

		Properties properties = utility.readProperties();
		
		//reads provided input file and store them in input model in the form of Hash map 
		inputModel.setMapOfAddressesAndLinks(utility.createMapFromInput(properties, toBeVisitedQueue));

		ExecutorService executorService = Executors
				.newFixedThreadPool(Integer.parseInt(properties.getProperty("no_Of_Threads")));
		
		//create thread for the urls available in queue
		outputModel = utility.createThreads(toBeVisitedQueue, outputModel, inputModel, executorService);
		System.out.println(outputModel);

	}

}
