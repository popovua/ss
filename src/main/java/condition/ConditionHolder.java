package condition;

import java.util.ArrayList;
import java.util.List;

public class ConditionHolder {

    private static ConditionHolder conditionHolder;
    private static List<Condition> conditions;


    private ConditionHolder() {}

    public static ConditionHolder getInstance() {
        if (conditionHolder == null) {
            conditionHolder = new ConditionHolder();
            conditions = new ArrayList<>();
        }
        return conditionHolder;
    }

    public void addCondition(Condition condition) {
        conditions.add(condition);
    }

    public List<Condition> getConditions() {
        return conditions;
    }
}
