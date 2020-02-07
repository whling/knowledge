package whling.mybatis.custom;

public class Mapper {

    private String sql;

    private Class returnType;

    public Mapper() {
    }

    public Mapper(String sql, Class returnType) {
        this.sql = sql;
        this.returnType = returnType;
    }


    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public Class getReturnType() {
        return returnType;
    }

    public void setReturnType(Class returnType) {
        this.returnType = returnType;
    }
}
