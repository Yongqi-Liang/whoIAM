package liangyongqi.iam.authenticator.Utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class OTPTool {
    /**
     * 生成字符串的 6 位特征数字
     *
     * @param input 输入字符串
     * @return 6 位特征数字字符串
     */
    public static String generateOtp(String input) {
        try {
            // 使用 SHA-256 哈希算法
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            // 将哈希值字节数组转换为正整数
            long hashInt = 0;
            for (byte b : hashBytes) {
                hashInt = (hashInt << 8) | (b & 0xFF);
            }
            hashInt = Math.abs(hashInt); // 确保为正数

            // 取模 10^6 以确保结果为 6 位数字
            long featureNumber = hashInt % 1000000;

            // 返回零填充的 6 位数字字符串
            return String.format("%06d", featureNumber);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }
}
