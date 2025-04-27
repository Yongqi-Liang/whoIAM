<template>
    <div id="about">
        <h2>关于系统</h2>
        <p class="indented-text">基于Spring的企业数字身份管控平台（IAM）</p>
        <h2>摘要</h2>
        <p class="indented-text">基于Spring的企业数字身份管控平台（IAM）设计与实现是专为政企、军工和科研机构等信息安全要求极高的单位设计的解决方案。该平台不仅提供强大的身份认证与访问控制功能，还能确保系统的全面掌控，避免外部云服务带来的安全隐患。通过简洁的图形化操作界面，平台的部署、运行和维护变得更加便捷，使得技术要求较低的实施人员也能轻松上手。平台支持多因素认证，包括生物特征识别等无感认证方式，有效提升身份验证的安全性，同时保障用户体验。无论是在严格的线下物理环境，还是高标准的线上认证场景中，平台都能实现统一且高效的认证管理，确保信息的安全访问。凭借其高度的灵活性与定制化能力，该平台能适应不同场景的需求，进一步增强其在各类高安全保密环境中的应用价值。</p>
        <h2>联系我</h2>
        <p class="indented-text">梁涌祺 ｜ my@liangyongqi.top</p>
        <div id="sign">
            <img src="../assets/logo.png" alt="Logo" style="width: 100px; height:100px">
        </div>
    </div>
</template>
<script>
export default {
    name: 'About',
    data() {
        return {
            pagename: localStorage.getItem('pagename') || '关于'
        }
    }
}
</script>
<style>
.indented-text {
    text-indent: 2em; 
    line-height: 1.6;
}
#sign {
    margin-top: 80px;
}
</style>