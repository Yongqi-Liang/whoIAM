<template>
    <div id="SystemStatus">
        <h2>系统状态</h2>
        <div class="vertical-center">
            <span>系统服务 :</span>
            <Lamp :status="'green'" />
            <span>{{ server }}</span>
        </div>
    </div>
</template>

<script>
import Lamp from '@/components/Lamp.vue';

export default {
    name: 'SystemStatus',
    components: {
        Lamp,
    },
    data() {
        return {
            pagename: localStorage.getItem('pagename') || '系统状态',
            server: "运行中",
        }
    }
}
</script>

<style>
.vertical-center {
    display: flex;
    align-items: center;
    justify-content: center;
}
</style>