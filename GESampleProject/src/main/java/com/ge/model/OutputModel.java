package com.ge.model;

import java.util.List;

/**
 * @author Akash
 * 
 *         This class is used to store success, skipped and error urls.
 *
 */
public class OutputModel {

	private List<String> success;
	private List<String> skipped;
	private List<String> error;

	public OutputModel(List<String> success, List<String> skipped, List<String> error) {
		this.success = success;
		this.skipped = skipped;
		this.error = error;
	}

	public List<String> getSuccess() {
		return success;
	}

	public void setSuccess(List<String> success) {
		this.success = success;
	}

	public List<String> getSkipped() {
		return skipped;
	}

	public void setSkipped(List<String> skipped) {
		this.skipped = skipped;
	}

	public List<String> getError() {
		return error;
	}

	public void setError(List<String> error) {
		this.error = error;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OutputModel other = (OutputModel) obj;
		if (error == null) {
			if (other.error != null)
				return false;
		} else if (!error.equals(other.error))
			return false;
		if (skipped == null) {
			if (other.skipped != null)
				return false;
		} else if (!skipped.equals(other.skipped))
			return false;
		if (success == null) {
			if (other.success != null)
				return false;
		} else if (!success.equals(other.success))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "OutputModel [success=" + success + ", skipped=" + skipped + ", error=" + error + "]";
	}

}
