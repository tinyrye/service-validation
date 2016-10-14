package validation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class InspectionReport
{
	private static final long serialVersionUID = 1L;

	private List<String> validationMessages = new ArrayList<String>();
	private Map<String,Object> verifiables = new HashMap<String,Object>();
	private Set<String> completed = new HashSet<String>();
	private Set<String> allowed = new HashSet<String>();
	private Set<String> failed = new HashSet<String>();
	private Set<String> precluded = new HashSet<String>();

	public List<String> getValidationMessages() {
		return validationMessages;
	}

	public InspectionReport setValidationMessages(List<String> validationMessages) {
		this.validationMessages = validationMessages;
		return this;
	}

	public InspectionReport addValidationMessage(String validationMessage) {
		validationMessages.add(validationMessage);
		return this;
	}

	public Map<String,Object> getVerifiables() {
		return verifiables;
	}

	public Set<String> allowed() { return allowed; }
	public InspectionReport addAllowed(String validation) {
		completed.add(validation);
		allowed.add(validation);
		return this;
	}
	public boolean isAllowed(String validation) { return allowed.contains(validation); }
	
	public Set<String> failed() { return failed; }
	public InspectionReport addFailed(String validation) {
		completed.add(validation);
		failed.add(validation);
		return this;
	}
	public boolean isFailed(String validation) { return failed.contains(validation); }

	public Set<String> precluded() { return precluded; }
	public InspectionReport addPrecluded(String validation) {
		completed.add(validation);
		precluded.add(validation);
		return this;
	}
	public boolean isPrecluded(String validation) { return precluded.contains(validation); }
	
	public boolean isCompleted(String validation) {
		return completed.contains(validation);
	}
}