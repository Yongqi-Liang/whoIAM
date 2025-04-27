<template>
    <div id="AuthLog" v-loading="loading" element-loading-text="加载中...">
        <h1>事务日志</h1>
        
        <div class="tblayout">
            <el-table :data="tableData" style="width: 100%">
                <el-table-column prop="id" label="ID" min-width="80"></el-table-column>
                <el-table-column prop="uid" label="用户ID" min-width="120"></el-table-column>
                <el-table-column prop="appid" label="应用ID" min-width="120"></el-table-column>
                <el-table-column prop="authdatetime" label="授权时间" min-width="180"></el-table-column>
            </el-table>
        </div>
    </div>
</template>

<script>
import { ElTable, ElTableColumn } from 'element-plus';
import 'element-plus/dist/index.css';
import axios from 'axios';

// 创建 axios 实例并配置
const apiClient = axios.create({
    baseURL: process.env.VUE_APP_API_BASE_URL,
    withCredentials: true,
    headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json'
    }
});

export default {
    name: 'AuthLog',
    components: {
        ElTable,
        ElTableColumn
    },
    data() {
        return {
            tableData: [],
            loading: true
        };
    },
    async created() {
        await this.fetchAuthLogList();
    },
    methods: {
        async fetchAuthLogList() {
            if (!this.loading) {
                this.loading = true;
            }
            try {
                const token = localStorage.getItem('token');
                await new Promise(resolve => setTimeout(resolve, 300));
                
                const response = await apiClient.post('/appauthlog/list', {
                    token
                });
                
                console.log('API Response:', response.data);

                if (response.data.code === 'success') {
                    this.tableData = response.data.data.map(log => ({
                        ...log,
                        authdatetime: new Date(log.authdatetime).toLocaleString()
                    }));
                    console.log('Processed tableData:', this.tableData);
                } else {
                    ElMessage.error(response.data.msg);
                    this.$router.push('/AppLogin/admin');
                }
            } catch (error) {
                console.error('获取授权日志列表失败:', error.response || error);
                ElMessage.error('获取授权日志列表失败');
            } finally {
                this.loading = false;
            }
        }
    }
};
</script>

<style scoped>
#AuthLog {
    background-color: white;
    padding: 3%;
}

.tblayout {
    border: 1px solid #ccc;
    border-radius: 10px;
    width: 100%;
    height: calc(100vh - 200px);
    padding: 20px;
    overflow: auto;
    margin-top: 20px;
}

/* 表格样式 */
.el-table {
    height: 100%;
    overflow: hidden;
}

/* 确保表格适应容器 */
:deep(.el-table__inner-wrapper) {
    height: 100%;
}

/* 加载动画样式 */
:deep(.el-loading-mask) {
    background-color: rgba(255, 255, 255, 0.8);
}
</style>