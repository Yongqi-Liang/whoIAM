package liangyongqi.iam.authenticator.Utils;

public class LogTool {

    public static String sp = "················································································";
    public static void mkSpLine(String msg) {
        System.out.println(sp + " " + msg + " " + sp);
    }
    // 分割线由30个圆点构成



    /**
     * 记录方法调用
     * @param className 类名
     * @param methodName 方法名
     * @param msg 操作信息
     * 使用方法：LogTool.writelog("类名", "方法名", "操作信息");
     */
    public static void writelog(String className, String methodName, String msg) {
        if(msg == null) {
            System.out.println("类" + className + "中的方法" + methodName + "被调用");
        }
        System.out.println("类" + className + "中的方法" + methodName + "执行'" + msg + "'操作");
    }

    /**
     * 记录系统级错误
     * @param msg 错误信息
     * 使用方法：LogTool.writeError(e.getMessage());
     */
    public static void writeError(String msg) {
        System.out.println("系统级错误：" + msg);
    }
}
