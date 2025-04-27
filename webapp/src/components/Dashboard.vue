<template>
    <div id="Dashboard">
        <div class="cardgroup">
            <div class="card-s">
                <Chart desc="本月访问" :value="monthlyVisits"/>
            </div>
            <div class="card-s">
                <Chart desc="用户" :value="userCount"/>
            </div>
            <div class="card-s">
                <Chart desc="用户组" :value="groupCount"/>
            </div>
            <div class="card-s">
                <Chart desc="应用" :value="appCount"/>
            </div>
        </div>
        <div class="cardgroup">
            <div class="card-l bg-pink" @click="navigateTo('/user-management', '用户管理')">
                <div class="carditem">
                    <i class="bi bi-person-bounding-box"></i>
                    <span>用户管理</span>
                </div>
            </div>
            <div class="card-l bg-blue" @click="navigateTo('/user-group-management', '用户组管理')">
                <div class="carditem">
                    <i class="bi bi-people-fill"></i>
                    <span>用户组管理</span>
                </div>
            </div>
            <div class="card-l bg-yellow" @click="navigateTo('/app-management', '应用管理')">
                <div class="carditem">
                    <i class="bi bi-app"></i>
                    <span>应用管理</span>
                </div>
            </div>
            <div class="card-l bg-purple" @click="navigateTo('/auth-log', '事务日志')">
                <div class="carditem">
                    <i class="bi bi-file-break-fill"></i>
                    <span>事务日志</span>
                </div>
            </div>
        </div>
    </div>
    </template>
    
<script>
import Chart from '@/components/Chart.vue';
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
    name: 'Dashboard',
    components: {
        Chart,
    },
    data() {
        return {
            userCount: 'N/A',
            groupCount: 'N/A',
            appCount: 'N/A',
            monthlyVisits: 'N/A',
            pagename: localStorage.getItem('pagename') || 'Dashboard',
        }
    },
    methods: {
        navigateTo(component, name) {
            console.log(`Navigating to ${component} with name ${name}`); 
            this.pagename = name;
            localStorage.setItem('pagename', name);
            this.$router.push(component);
        },
        async fetchData() {
            const token = localStorage.getItem('token');
            try {
                // 获取用户列表
                const userResponse = await apiClient.get('/UserListApi', {
                    params: { token }
                });
                if (userResponse.data.code === 'success') {
                    this.userCount = Object.keys(userResponse.data.data).length;
                }

                // 获取用户组列表
                const groupResponse = await apiClient.get('/UserGroupList', {
                    params: { token }
                });
                if (groupResponse.data.code === 'success') {
                    this.groupCount = Object.keys(groupResponse.data.data).length;
                }

                // 获取应用列表
                const appResponse = await apiClient.post('/appList');
                if (appResponse.data.code === 'success') {
                    this.appCount = Object.keys(appResponse.data.data).length;
                }

                // 获取本月访问记录
                const logResponse = await apiClient.post('/appauthlog/list', {
                    token
                });
                if (logResponse.data.code === 'success') {
                    const currentMonth = new Date().getMonth();
                    const currentYear = new Date().getFullYear();
                    const monthlyLogs = logResponse.data.data.filter(log => {
                        const logDate = new Date(log.authdatetime);
                        return logDate.getMonth() === currentMonth && 
                               logDate.getFullYear() === currentYear;
                    });
                    this.monthlyVisits = monthlyLogs.length;
                }
            } catch (error) {
                console.error('获取数据失败:', error);
            }
        }
    },
    async created() {
        await this.fetchData();
    },
}
</script>
<style>
    #Dashboard {
        padding: 20px;
    }
    .cardgroup {
        margin: 20px;
        width: auto;
        display: flex;
        flex-wrap: wrap;
        width: auto;
    }
    .card-s {
        background-color: rgba(255, 255, 255, 0.9);
        width: 150px;
        height: 120px;
        display: flex;
        justify-content: center; /* 水平居中 */
        align-items: center; /* 垂直居中 */
        margin: 10px;
        padding: 10px;
        border-radius: 10px;
    }
    .card-m {
        cursor: pointer;
        transition: background-color 0.15s;
        background-color: rgba(255, 255, 255, 0.9);
        display: flex;
        justify-content: center; /* 水平居中 */
        align-items: center; /* 垂直居中 */
        margin: 10px;
        padding: 10px;
        width: 340px;
        height: 150px;
        border-radius: 10px;
        box-shadow: 1px 1px 3px 3px rgba(0, 0, 0, 0.1);
    }
    .card-l {
        cursor: pointer;
        transition: background-color 0.15s;
        cursor: pointer;
        width: 530px;
        height: 200px;
        display: flex;
        justify-content: center; /* 水平居中 */
        align-items: center; /* 垂直居中 */
        margin: 10px;
        padding: 10px;
        border-radius: 10px;
        box-shadow: 1px 1px 3px 3px rgba(0, 0, 0, 0.1);
    }
    .carditem {
        display: flex;
        flex-direction: column;
        justify-content: center; /* 水平居中 */
        align-items: center; /* 垂直居中 */
    }
    .carditem span {
        font-size: 20px;
        color: white;
    }
    .carditem i {
        font-size: 70px;
        color: white;
    }

    .bg-pink {
        background-color: rgba(191, 23, 90, 0.9);
    }
    .bg-pink:hover {
        background-color: rgb(113, 14, 54);
    }
    .bg-blue {
        background-color: rgba(34, 128, 228, 0.9);
    }
    .bg-blue:hover {
        background-color: rgb(22, 86, 154);
    }
    .bg-yellow {
        background-color: rgba(214, 167, 24, 0.9);
    }
    .bg-yellow:hover {
        background-color: rgb(152, 119, 18);
    }
    .bg-purple {
        background-color: rgba(123, 31, 162, 0.9);
    }
    .bg-purple:hover {
        background-color: rgb(82, 16, 110);
    }
</style>