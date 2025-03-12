package school.hei.eventManagerDWBackend.repository.dao;


public class Criteria {
    private String column;
    private Object value;

    public String getColumn() {
        return column;
    }

    public Object getValue() {
        return value;
    }

    public Criteria(String column, Object value) {
        this.column = column;
        this.value = value;
    }
}
