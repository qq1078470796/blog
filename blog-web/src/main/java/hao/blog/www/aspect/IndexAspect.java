package hao.blog.www.aspect;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Aspect
@Component
public class IndexAspect {
    @Pointcut(value="execution(* hao.blog.www.controller.IndexJspController.findAllList(..))")
    private void pointCut(){//定义一个切入点 后面的通知直接引入切入点方法pointCut即可            personServerImpl下面的所有方法
    }

    //环绕通知（连接到切入点开始执行，下一步进入前置通知，在下一步才是执行操作方法）
    @Around(value="pointCut()")
    public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable{
        System.out.println("@Around进入环绕通知...");
        Object object = pjp.proceed();//执行该方法
        System.out.println(pjp.getThis()+"  操作结束，退出方法;环绕[@Around]结束！...");
        return object;
    }

    //前置通知（进入环绕后执行，下一步执行方法）
    @Before(value="pointCut()")
    public void doAccessCheck(JoinPoint joinPoint){
        System.out.println("@Before前置通知:"+ Arrays.toString(joinPoint.getArgs()));
    }

}
