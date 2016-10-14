package validation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface InspectionPost
{
	void failed(String validation, String reasonCode, Map<String,Object> details);
	void successful(String validation, String resultCode, Map<String,Object> verified);
	void successful(String validation, Map<String,Object> verified);
}