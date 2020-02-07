package whling.mybatis.custom.session;

import javax.naming.OperationNotSupportedException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

public class MapperInvocationHandler implements InvocationHandler {

    private SqlSession sqlSession;

    public MapperInvocationHandler(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        String methodName = method.getName();
        Class<?> clazz = method.getDeclaringClass();
        String className = clazz.getName();

        String key = className + "." + methodName;

        Class<?> returnType = method.getReturnType();

        if (returnType == List.class) {
            return sqlSession.selectList(key);
        }

        throw new OperationNotSupportedException();
    }
}
