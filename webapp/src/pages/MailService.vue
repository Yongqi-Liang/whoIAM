<template>
    <div id="MailService">
        <h2>邮件服务</h2>
        <div>
            <h3>SMTP 主机</h3>
            <div class="flexlayout mailservice-vertical-center">
                <input class="l paddingLAR" type="text" />
                <span>:</span>
                <input class="s paddingLAR" type="text" />
            </div>
        </div>
        <div>
            <h3>邮件地址</h3>
            <div class="flexlayout mailservice-vertical-center">
                <input class="xl paddingLAR" type="text" />
            </div>
        </div>
        <div>
            <h3>密码</h3>
            <div class="flexlayout mailservice-vertical-center">
                <input class="xl paddingLAR" type="text" />
            </div>
        </div>
    </div>
</template>
<script>
export default {
    name: 'MailService',
    data() {
        return {
            pagename: localStorage.getItem('pagename') || '邮件服务'
        }
    }
}
</script>
<style>
.flexlayout {
    display: flex;
}
.mailservice-vertical-center {
    align-items: center;
}
.paddingLAR {
    padding-left: 10px;
    padding-right: 10px;
}
.s {
    width: 100px;
}
.m {
    width: 150px;
}
.l {
    width: 200px;
}
.xl {
    width: 250px;
}
.xxl {
    width: 300px;
}
</style>
