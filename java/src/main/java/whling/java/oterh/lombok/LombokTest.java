package whling.java.oterh.lombok;

import com.alibaba.fastjson.JSON;
import lombok.*;

public class LombokTest {

    public static void main(String[] args) {
        User u1 = User.builder().name("a").age(23).build();
        User u2 = User.builder().name("a").age(23).build();

        u1.setId(1);
        u1.setId(2);

        System.out.println(u1.equals(u2));

        System.out.println("=================");

        /**
         * 在类上标注了@Data和@Builder注解的时候，该类就没有了默认的构造方法，那么在某些组件反序列话的时候，拿不到默认构造方法就会报错。
         */
        String jsonStr = JSON.toJSONString(u1);
        System.out.println(jsonStr);

        System.out.println(JSON.parseObject(jsonStr, User.class));
    }

}

/**
 * 当使用@Data注解时，则有了@EqualsAndHashCode注解，那么就会在此类中存在equals(Object other) 和 hashCode()方法，且不会使用父类的属性，这就导致了可能的问题。 
 * 比如，有多个类有相同的部分属性，把它们定义到父类中，恰好id（数据库主键）也在父类中，那么就会存在部分对象在比较时，它们并不相等，
 * 却因为lombok自动生成的equals(Object other) 和 hashCode()方法判定为相等，从而导致出错。
 * <p>
 * 修复此问题的方法很简单： 
 * 1. 使用@Getter @Setter @ToString代替@Data并且自定义equals(Object other) 和 hashCode()方法，比如有些类只需要判断主键id是否相等即足矣。 
 * 2. 或者使用在使用@Data时同时加上@EqualsAndHashCode(callSuper=true)注解。
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data // 该注解相当于@Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode这5个注解的合集
class User extends BaseUser {
    private String name;
    private int age;
}

class BaseUser {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}