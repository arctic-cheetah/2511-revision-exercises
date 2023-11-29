package unsw.business;

import java.util.Map;
import java.util.Set;

public interface BusinessRule {
    public boolean evaluate(Map<String, Object> values);
}
