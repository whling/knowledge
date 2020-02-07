package whling.mybatis.custom.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

public class Converter {

    /**
     * 将结果集中的每一条数据封装到javaBean中，如果有多条，就封装多个javaBean
     *
     * @param resultSet
     * @param clazz
     * @param <E>
     * @return
     */
    public static <E> List<E> converList(ResultSet resultSet, Class clazz) {
        List<E> beans = new ArrayList<E>();
        //遍历结果集
        try {
            ResultSetMetaData metaData = resultSet.getMetaData();
            //获取总列数
            int columnCount = metaData.getColumnCount();
            while (resultSet.next()) {
                //每次遍历，遍历出一条数据就对应一个javaBean
                E o = (E) clazz.newInstance();
                //获取每一列数据，根据列名获取
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Object value = resultSet.getObject(columnName);
                    //将该列的值存放在javaBean中
                    //也就是调用javaBean的set方法，使用内省机制
                    PropertyDescriptor descriptor = new PropertyDescriptor(columnName, clazz);
                    Method writeMethod = descriptor.getWriteMethod();
                    //调用set方法
                    writeMethod.invoke(o, value);
                }
                beans.add(o);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return beans;
    }
}
