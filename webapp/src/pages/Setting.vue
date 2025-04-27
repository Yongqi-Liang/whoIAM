<template>
    <div id="Setting">
        <h1>系统首选项</h1>
        <div class="horizonital">
            <div class="aside">
                <div class="aside-item" :class="{ selected: selectedItem === 'service-config' }" @click="navigateTo('service-config')">
                    <i class="bi bi-globe-americas" style="font-size: 20px;"></i>
                    <span class="marginLAR">启动配置</span>
                </div>
                <div class="aside-item" :class="{ selected: selectedItem === 'mail-service' }" @click="navigateTo('mail-service')">
                    <i class="bi bi-envelope-at" style="font-size: 20px;"></i>
                    <span class="marginLAR">邮件服务</span>
                </div>
                <div class="aside-item" :class="{ selected: selectedItem === 'system-status' }" @click="navigateTo('system-status')">
                    <i class="icon-m bi bi-cpu" style="font-size: 20px;"></i>
                    <span class="marginLAR">系统状态</span>
                </div>
                <div class="aside-item" :class="{ selected: selectedItem === 'about' }" @click="navigateTo('about')">
                    <i class="icon-m bi bi-info-circle" style="font-size: 20px;"></i>
                    <span class="marginLAR">关于</span>
                </div>
            </div>
            <div class="settingcontent">
                <router-view></router-view>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: 'Setting',
    data() {
        return {
            settingId: this.$route.params.id || 'service-config',
            selectedItem: this.$route.params.id || 'service-config'
        };
    },
    watch: {
        '$route.params.id'(newId) {
            this.settingId = newId; // 监听路由参数变化
            this.selectedItem = newId; // 更新选中的项目
        }
    },
    methods: {
        navigateTo(id) {
            this.selectedItem = id; // 更新选中的项目
            this.$router.push(`/setting/${id}`);
        }
    }
}
</script>
<style>
    #Setting {
        background-color: white;
        padding: 3%;
    }
    .horizonital {
        display: flex;
        height: 100%;
        width: 100%;
    }
    .settingcontent {
        width: 80%;
        padding: 1%;
        margin-left: 100px;
    }
    .aside {
        width: 30%;
        height: 100%;
        border-radius: 10px;
        overflow: hidden;
    }
    .aside-item {
        transition: 0.5s;
        width: 100%;
        height: 40px;
        background-color: #f0f0f0;
        margin-top: 1px;
        margin-bottom: 1px;
        display: flex;
        padding-left: 20px;
        padding-right: 20px;
        align-items: center;
    }
    .aside-item:hover {
        background-color: #eaeaea;
    }
    .aside-item.selected {
        background-color: #d7d7d7; /* 选中项目的背景颜色 */
    }
    .marginLAR {
        margin-left: 10px;
        margin-right: 10px;
    }
</style>