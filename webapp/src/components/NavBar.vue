<template>
    <div class="NavBar unselectable">
        <img class="whoIAM_logo" src="../assets/logo.png" alt="whoIAM Logo">
        <div id="navsmallcontain" class="paddingLeftAndRight">
            <span id="whoIAM_pagetitle">whoIAM | 身份管理中心</span>
            <div class="pageitem" @click="navigateTo('/dashboard', '仪表板')">
                <i class="bsicon-l bi bi-grid-3x2-gap-fill"></i>
                <span>主页</span>
            </div>
            <span class="pageitem">{{ pagename }}</span>
            <div id="navsmallcontain-rightaligned">
                <div class="nav-sys paddingLeftAndRightLarge btn" @click="navigateTo('/help', '帮助')">
                    <i class="bi bi-question-octagon"></i>
                </div>
                <div class="nav-sys paddingLeftAndRightLarge btn" @click="navigateTo('/setting', '设置')">
                    <i class="bi bi-gear-wide-connected"></i>
                </div>
                <div class="paddingLeftAndRightLarge"></div>
                <div id="nav-username" @click="toggleDropdown">
                    <i class="bi bi-person-circle"></i>
                    <span class="paddingLeftAndRightSmall shortinfo">{{username}}</span>
                    <i class="bi bi-caret-down-fill"></i>
                    <div v-if="showDropdown" class="dropdown-menu">
                        <a @click="handleLogout">注销</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios';

const apiClient = axios.create({
    baseURL: process.env.VUE_APP_API_BASE_URL,
    withCredentials: true,
    headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json'
    }
});

export default {
    name: 'NavBar',
    props: {
        username: {
            type: String,
            default: ''
        },
        pagename: String,
    },
    emits: ['update:username', 'navigate'],  // 添加 navigate 事件声明
    data() {
        return {
            showDropdown: false,
            localUsername: this.username,
        };
    },
    async created() {
        try {
            const token = localStorage.getItem('token');
            if (token) {
                const response = await apiClient.post('/AdminHomeInfo', { token });
                if (response.data.code === 'success') {
                    this.localUsername = response.data.data.userName;
                    this.$emit('update:username', this.localUsername);
                }
            }
        } catch (error) {
            console.error('获取用户信息失败:', error);
        }
    },
    methods: {
        toggleDropdown() {
            this.showDropdown = !this.showDropdown;
        },
        navigateTo(path, name) {
            this.$emit('navigate', path, name);
        },
        handleLogout() {
            // 清除 token
            localStorage.removeItem('token');
            // 刷新整个应用
            window.location.reload();
        }
    }
}
</script>

<style>
.bsicon-l {
    font-size: 20px;
}
.pageitem {
    transition: color 0.15s;
    display: flex;
    align-items: center;
    justify-content: center;
    color: #000000;
    padding-left: 20px;
    padding-right: 20px;
}
.pageitem:hover {
    cursor: pointer;
    color: gray;
}
.unselectable {
    -webkit-user-select: none; /* Safari */
    user-select: none;
}
span .shortinfo {
    max-width: 120px;
    min-width: 100px;
}
.NavBar {
    top: 0;
    left: 0;
    width: 100%;
    max-height: 52px;
    position: fixed;
    display: flex;
    box-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);
    align-items: center;
    background-color: rgba(255, 255, 255, 0.893);
    z-index: 1000;
}
#navsmallcontain {
    width: 100%;
    display: flex;
    align-items: center; 
}
.whoIAM_logo {
    width: 50px;
    height: 50px;
}
#whoIAM_pagetitle {
    margin: 5px;
    padding: 5px;
    text-align: center;
    font-weight: bold;
}
#navsmallcontain-rightaligned {
    display: flex;
    padding: 5px;
    margin: 5px;
    margin-left: auto;
    align-items: center; /* 确保子元素垂直居中 */
}
.nav-sys {
    display: flex;
    align-items: center; /* 确保图标垂直居中 */
}
#nav-username {
    transition: background-color 0.15s;
    font-size: 12px;
    border-radius: 5px;
    background-color: rgb(221, 221, 221);
    padding: 5px;
    display: flex;
    align-items: center; /* 确保图标和文本垂直居中 */
}
#nav-username:hover {
    cursor: pointer;
    background-color: rgb(191, 191, 191);
}
.paddingLeftAndRightSmall {
    padding-left: 5px;
    padding-right: 5px;
}
.paddingLeftAndRightLarge {
    padding-left: 10px;
    padding-right: 10px;
}
i {
    padding: 5px;
    border-radius: 5px;
    display: flex;
    align-items: center; /* 确保图标自身垂直居中 */
}
.btn i {
    transition: background-color 0.15s;
}
.btn:hover i {
    cursor: pointer;
    background-color: #dbdadac0;
}
.dropdown-menu {
    position: absolute;
    background-color: white;
    width: 90px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    border: 1px solid #ccc;
    padding: 10px;
    margin-top: 100px;
    z-index: 1000;
}
.dropdown-menu a {
    transition: background-color 0.15s;
    display: block;
    padding: 5px 10px;
    text-decoration: none;
    color: black;
}
.dropdown-menu a:hover {
    background-color: #f0f0f0;
}
</style>
<style scoped>
    span {
        max-height: 22px;
        white-space: nowrap; /* 防止文字换行 */
        overflow: hidden; /* 隐藏溢出部分 */
        text-overflow: ellipsis; /* 使用省略号表示溢出的文字 */
    }
</style>