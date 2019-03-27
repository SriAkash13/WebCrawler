package com.ge.crawler;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.ge.model.InputModel;
import com.ge.model.OutputModel;

/**
 * @author Akash
 *
 *This class is used to check whether each url is valid or not and then add
 *accordingly into success, skipped or error lists. 
 */
public class CrawlThread implements Runnable {
	private String Url;
	private OutputModel outputModel;
	private ConcurrentLinkedQueue<String> toBeVisitedQueue;
	private InputModel inputModel;
	private List<String> success;
	private List<String> skipped;
	private List<String> error;

	public CrawlThread(String url, OutputModel outputModel, ConcurrentLinkedQueue<String> toBeVisitedQueue,
			InputModel inputModel) {
		this.Url = url;
		this.outputModel = outputModel;
		this.toBeVisitedQueue = toBeVisitedQueue;
		this.inputModel = inputModel;
	}

	@Override
	public void run() {
		synchronized (toBeVisitedQueue) {
			success = outputModel.getSuccess();
			skipped = outputModel.getSkipped();
			error = outputModel.getError();
			if (inputModel.getMapOfAddressesAndLinks().containsKey(this.Url)) {
				if (!success.contains(this.Url)) {
					success.add(this.Url);
					List<Object> links = getLinks(this.Url);
					addUrl(links);
				} else if (!skipped.contains(this.Url)) {
					skipped.add(this.Url);
				}
			} else {
				error.add(this.Url);
			}
			outputModel.setSuccess(success);
			outputModel.setSkipped(skipped);
			outputModel.setError(error);
		}
	}

	private void addUrl(List<Object> obj) {
		synchronized (toBeVisitedQueue) {
			for (Object o : obj) {
				toBeVisitedQueue.offer(o.toString());
			}
			toBeVisitedQueue.notify();
		}
	}

	private List<Object> getLinks(String url) {
		List<Object> links = inputModel.getMapOfAddressesAndLinks().get(url);
		return links;
	}
}
