package liangyongqi.iam.Ability;

import liangyongqi.iam.Data.Entity.Faceid;
import liangyongqi.iam.Data.Entity.FaceidId;
import liangyongqi.iam.Data.Repository.FaceidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SetFaceID {
    @Autowired
    private FaceidRepository faceidRepository;

    /**
     * 设置人脸特征向量
     *
     * @param uid           用户 ID
     * @param featureVector 人脸特征向量
     * @return 操作结果
     */
    public String setFaceID(String uid, byte[] featureVector) {
        try {
            // 创建复合主键
            FaceidId faceidId = new FaceidId();
            faceidId.setUid(uid);
            faceidId.setId(1); // 示例：主键 ID 设置为 1，根据实际业务逻辑调整

            // 检查记录是否存在
            if (faceidRepository.existsById(faceidId)) {
                // 如果存在，更新记录
                Faceid existingFaceid = faceidRepository.findById(faceidId).orElse(null);
                if (existingFaceid != null) {
                    existingFaceid.setFeatureVector(featureVector);
                    faceidRepository.save(existingFaceid);
                }
                return "success";
            } else {
                // 如果不存在，新增记录
                Faceid newFaceid = new Faceid();
                newFaceid.setId(faceidId);
                newFaceid.setFeatureVector(featureVector);
                faceidRepository.save(newFaceid);
                return "success";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "操作失败: " + e.getMessage();
        }
    }
}
