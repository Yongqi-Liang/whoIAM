<template>
    <div id="Home">
        <NavBar
            v-model:username="username"
            :pagename="pagename"
            @navigate="navigateTo"
        />
        <router-view></router-view>
    </div>
</template>

<script>
import NavBar from '@/components/NavBar.vue';

export default {
    name: 'Home',
    components: {
        NavBar,
    },
    props: {
        role: String,
    },
    data() {
        return {
            pagename: localStorage.getItem('pagename') || '仪表板',
            username: ''
        };
    },
    methods: {
        navigateTo(component, name) {
            console.log(`Navigating to ${component} with name ${name}`); 
            this.pagename = name;
            localStorage.setItem('pagename', name);
            this.$router.push(component);
        }
    }
}
</script>

<style>
#Home {
    background: linear-gradient(to bottom, rgba(255, 255, 255, 0) 50%, rgba(255, 255, 255, 1) 80%), 
                url('../img/img01.jpeg');
    background-size: cover; /* 使背景图片覆盖整个容器 */
    background-position: center; /* 居中背景图片 */
    background-repeat: no-repeat; /* 防止背景图片重复 */
    margin-top: 50px;
}
</style>