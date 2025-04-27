package liangyongqi.iam.Ability;

import liangyongqi.iam.Data.Entity.Faceid;
import liangyongqi.iam.Data.Repository.FaceidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthFaceID {
    @Autowired
    private FaceidRepository faceidRepository;

    /**
     * 验证人脸特征向量是否匹配
     *
     * @param featureVector 传入的人脸特征向量
     * @return 是否匹配
     */
    public Boolean authFaceID(byte[] featureVector) {
        try {
            // 查询所有人脸特征向量
            List<Faceid> faceidList = faceidRepository.findAll();

            for (Faceid faceid : faceidList) {
                byte[] storedFeatureVector = faceid.getFeatureVector();

                // 计算相似度
                double similarity = calculateCosineSimilarity(featureVector, storedFeatureVector);

                // 判断是否达到匹配阈值
                if (similarity > 0.8) {
                    return true;
                }
            }

            // 未找到匹配
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 计算两个特征向量的余弦相似度
     *
     * @param vector1 特征向量1
     * @param vector2 特征向量2
     * @return 余弦相似度
     */
    private double calculateCosineSimilarity(byte[] vector1, byte[] vector2) {
        if (vector1.length != vector2.length) {
            throw new IllegalArgumentException("两个向量的长度必须相等！");
        }

        double dotProduct = 0.0;
        double norm1 = 0.0;
        double norm2 = 0.0;

        for (int i = 0; i < vector1.length; i++) {
            dotProduct += vector1[i] * vector2[i];
            norm1 += vector1[i] * vector1[i];
            norm2 += vector2[i] * vector2[i];
        }

        return dotProduct / (Math.sqrt(norm1) * Math.sqrt(norm2));
    }
}
