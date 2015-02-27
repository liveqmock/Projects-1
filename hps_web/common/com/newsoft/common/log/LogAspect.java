package com.newsoft.common.log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Date;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.ibatis.binding.BindingException;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newsoft.security.acegi.SessionUtil;
import com.newsoft.sysmanager.po.User;
import com.newsoft.utils.UUIDTool;

/**
 * 记录日志的切面
 * 
 * @author mengxw
 * 
 */
@Aspect
@Service
public class LogAspect {

	private static final Logger logger = Logger.getLogger(LogAspect.class);

	@Autowired
	private FrameLogOperateDAO frameLogOperateDAO;
	@Autowired
	private LogService logService;

	/**
	 * 记录操作类日志
	 * 
	 * @param jp
	 */
	@After("execution(* com.newsoft..dao.*.*(..))")
	public void addOperateLogFromDAO(JoinPoint jp) {
		LogParam logParam = null;
		String signature = jp.getSignature().toString();
		String targetMethodName = signature.substring(signature
				.lastIndexOf(".") + 1, signature.indexOf("("));
		Class<?> daoInterface = null;
		Object[] methodParams = jp.getArgs();

		// 对于myBatis的DAO接口的动态代理实例的拦截案例中，如果在DAO接口中定义的方法的参数参数为父类型的对象，而在调用dao方法时传递的是子类型的对象时，通过反射机制调用getMethod方法时无法获取参数
		// 需要修改业务逻辑的源代码
		@SuppressWarnings("rawtypes")
		Class[] methodParamTypes = new Class[methodParams.length];
		for (int i = 0; i < methodParams.length; i++) {
			// System.err.println("paramTypes:" + methodParams[i].getClass());
			if (methodParams[i] != null) {
				methodParamTypes[i] = methodParams[i].getClass();
			}

		}
		for (Class<?> iface : jp.getTarget().getClass().getInterfaces()) {
			try {
				// System.err.println("iface:" + iface);
				Method m = iface.getMethod(targetMethodName, methodParamTypes);

				if (daoInterface != null) {
					throw new BindingException(
							"Ambiguous method mapping.  Two mapper interfaces contain the identical method signature for "
									+ targetMethodName);
				} else if (m != null) {
					daoInterface = iface;
				}
			} catch (Exception e) {
				System.err.println(iface + "中没有目标方法(logAspect)");
				// logger.error("LogAspect的getMethod方法抛出异常"
				// + e.getStackTrace());
				//e.printStackTrace();
			}
		}
		// for (Class interFace : SuperInterfaces) {
		// System.err.println("super 接口：" + interFace);
		if (daoInterface != null) {
			Method[] methods = daoInterface.getMethods();
			for (Method method : methods) {
				if (method.getName().equals(targetMethodName)) {
					// System.err.println("进入logAspect if：" + targetMethodName +
					// ";"
					// + method.getAnnotations().length);
					if (method.isAnnotationPresent(LogParam.class)) {
						logParam = method.getAnnotation(LogParam.class);
					}
					break;
				}
			}
		}

		if (logParam != null) {
			User user = SessionUtil.getSessionUser();
			if (user==null) {
				user = new User();
				user.setUserId("");
				user.setUserName("系统后台");
			}
			String userId = user.getUserId();
			String userName = user.getUserName();
			// 切面方法参数
			Object[] paramList = jp.getArgs();
			String params = parseParames(paramList);
			String className = daoInterface.getName();
			String methodName = signature.substring(
					signature.lastIndexOf(".") + 1, signature.indexOf("("));
			String operateModule = logParam.operateModule();
			String logDesc = logParam.logDes();
			// for(Object param: paramList) {
			// String paramClassName = param.getClass().getName();
			// //如果参数的对象是框架内的业务类
			// if(paramClassNamel.indexOf("com.newsoft")!=-1) {
			//				
			// }
			// }

			String uuid = UUIDTool.getUUID();
			LogOperate logOperate = new LogOperate();
			logOperate.setLogId(uuid);
			logOperate.setUserId(userId);
			logOperate.setUserName(userName);
			logOperate.setOperateType(methodName);
			logOperate.setOperateModule(operateModule + ":" + className + "."
					+ methodName);
			logOperate.setLogDes(logDesc + params);
			logOperate.setLogDate(new Date());

			try {
				frameLogOperateDAO.addOperateLog(logOperate);
			} catch (Exception e) {
				logger.error("从DAO接口添加操作日志时出错！", e);
				e.printStackTrace();
			}
		}
	}

	/**
	 * 记录操作类日志
	 * 
	 * @param jp
	 * @param lp
	 * @throws Exception
	 */
	@After("within(com.newsoft..*) && @annotation(lp)")
	public void addOperateLog(JoinPoint jp, LogParam lp) {
		User user = SessionUtil.getSessionUser();
		String userId = user.getUserId();
		String userName = user.getUserName();
		// 切面方法参数
		Object[] paramList = jp.getArgs();
		String params = parseParames(paramList);
		String className = jp.getTarget().getClass().toString();
		String signature = jp.getSignature().toString();// 获取目标方法签名
		String methodName = signature.substring(signature.lastIndexOf(".") + 1,
				signature.indexOf("("));
		String operateModule = lp.operateModule();
		String logDesc = lp.logDes();
		// for(Object param: paramList) {
		// String paramClassName = param.getClass().getName();
		// //如果参数的对象是框架内的业务类
		// if(paramClassName.indexOf("com.newsoft")!=-1) {
		//				
		// }
		// }

		String uuid = UUIDTool.getUUID();
		LogOperate logOperate = new LogOperate();
		logOperate.setLogId(uuid);
		logOperate.setUserId(userId);
		logOperate.setUserName(userName);
		logOperate.setOperateType(methodName);
		logOperate.setOperateModule(operateModule + ":" + className + "."
				+ methodName);
		logOperate.setLogDes(logDesc + params);
		logOperate.setLogDate(new Date());

		try {
			frameLogOperateDAO.addOperateLog(logOperate);
		} catch (Exception e) {
			logger.error("从服务类添加操作日志时出错！", e);
			e.printStackTrace();
		}

	}

	/**
	 * 记录操作类日志
	 * 
	 * @param jp
	 * @param lp
	 * @throws Exception
	 */
	@AfterThrowing(throwing = "ex")
	@Pointcut("execution(* *.*(..))")
	public void testSystemLog(JoinPoint jp, Throwable ex) throws Exception {
		// 切面方法参数
		// Object[] paramList = jp.getArgs();
		// String params = parseParames(paramList);
		String className = jp.getTarget().getClass().toString();
		String signature = jp.getSignature().toString();// 获取目标方法签名
		String methodName = signature.substring(signature.lastIndexOf(".") + 1,
				signature.indexOf("("));

		String uuid = UUIDTool.getUUID();
		StringWriter sw = new StringWriter();
		PrintWriter pt = new PrintWriter(sw);
		if (ex != null) {
			ex.printStackTrace(pt);
		}

		LogSystem logSystem = new LogSystem();
		logSystem.setLogDate(new Date());
		logSystem.setOperateModule(className + "." + methodName);
		logSystem.setLogId(uuid);
		logSystem.setLogDes(ex.getStackTrace().toString() + sw.getBuffer()
				+ ex.getMessage());
		// logSystem.setLogThread(logThread);

		logService.addSystemLog(logSystem);

	}

	/**
	 * 解析方法参数
	 * 
	 * @param parames
	 *            方法参数
	 * @return 解析后的方法参数
	 */
	private String parseParames(Object[] parames) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < parames.length; i++) {
			if (parames[i].getClass().getName().indexOf("com.newsoft") != -1) {
				if (parames[i] instanceof Object[]
						|| parames[i] instanceof Collection) {
					JSONArray json = JSONArray.fromObject(parames[i]);
					if (i == parames.length - 1) {
						sb.append(json.toString());
					} else {
						sb.append(json.toString() + ",");
					}
				} else {
					JSONObject json = JSONObject.fromObject(parames[i]);
					if (i == parames.length - 1) {
						sb.append(json.toString());
					} else {
						sb.append(json.toString() + ",");
					}
				}

			} else if (parames[i] instanceof String) {
				if (i == parames.length - 1) {
					sb.append(parames[i]);
				} else {
					sb.append(parames[i] + ",");
				}
			} else if (parames[i] instanceof Object[]
					|| parames[i] instanceof Collection) {
				JSONArray json = JSONArray.fromObject(parames[i]);
				if (i == parames.length - 1) {
					sb.append(json.toString());
				} else {
					sb.append(json.toString() + ",");
				}
			}

		}
		String params = sb.toString();
		params = params.replaceAll("(\"\\w+\":\"\",)", "");
		params = params.replaceAll("(,\"\\w+\":\"\")", "");
		return params;
	}

}
