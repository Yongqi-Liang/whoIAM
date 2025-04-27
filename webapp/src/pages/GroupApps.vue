<template>
    <div id="GroupApps" v-loading="loading" element-loading-text="加载中...">
        <h1>{{ groupName }} 的应用</h1>
        <div class="btn-table-add" @click="handleAdd">
            <i class="bi bi-plus-lg"></i>
            <span>添加应用</span>
        </div>

        <div class="tblayout">
            <el-table :data="tableData" style="width: 100%">
                <el-table-column prop="groupId" label="用户组ID" min-width="150"></el-table-column>
                <el-table-column prop="applicationId" label="应用ID" min-width="150"></el-table-column>
                <el-table-column label="操作" min-width="150">
                    <template #default="scope">
                        <el-button @click="handleDelete(scope.row)" type="danger" size="small">
                            <i class="bi bi-trash"></i>
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>

        <!-- 添加应用对话框 -->
        <el-dialog
            v-model="addDialogVisible"
            title="添加应用"
            width="30%"
            :close-on-click-modal="false"
        >
            <el-form :model="addForm" label-width="100px">
                <el-form-item label="应用ID">
                    <el-input v-model="addForm.applicationId" placeholder="请输入应用ID"></el-input>
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="addDialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="submitAdd">确定</el-button>
                </span>
            </template>
        </el-dialog>
    </div>
</template>

<script>
import { ElTable, ElTableColumn, ElButton, ElMessageBox, ElMessage, ElDialog, ElForm, ElFormItem, ElInput } from 'element-plus';
import 'element-plus/dist/index.css';
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
    name: 'GroupApps',
    components: {
        ElTable,
        ElTableColumn,
        ElButton,
        ElDialog,
        ElForm,
        ElFormItem,
        ElInput
    },
    data() {
        return {
            groupId: '',
            groupName: '',
            tableData: [],
            loading: true,
            addDialogVisible: false,
            addForm: {
                applicationId: ''
            }
        };
    },
    async created() {
        // 从 URL 路径中获取组 ID
        this.groupId = this.$route.path.split('/').pop();
        // 获取组信息和应用列表
        await this.fetchGroupInfo();
        await this.fetchAppsList();
    },
    methods: {
        async fetchGroupInfo() {
            try {
                const token = localStorage.getItem('token');
                const response = await apiClient.get('/UserGroupList', {
                    params: { token }
                });
                
                if (response.data.code === 'success') {
                    const groupInfo = response.data.data[this.groupId];
                    if (groupInfo) {
                        this.groupName = groupInfo.groupname;
                    }
                }
            } catch (error) {
                console.error('获取用户组信息失败:', error);
            }
        },
        async fetchAppsList() {
            if (!this.loading) {
                this.loading = true;
            }
            try {
                const token = localStorage.getItem('token');
                const response = await apiClient.post('/getUserGroupApplication', {
                    token,
                    groupId: this.groupId
                });
                
                if (response.data.status === 'success') {
                    this.tableData = response.data.data;
                }
            } catch (error) {
                console.error('获取用户组应用列表失败:', error);
                ElMessage.error('获取用户组应用列表失败');
            } finally {
                this.loading = false;
            }
        },

        handleAdd() {
            this.addForm = {
                applicationId: ''
            };
            this.addDialogVisible = true;
        },

        async submitAdd() {
            try {
                const token = localStorage.getItem('token');
                const response = await apiClient.post('/createUserGroupApplication', {
                    token,
                    groupId: this.groupId,
                    applicationId: this.addForm.applicationId
                });

                if (response.data.status === 'success') {
                    ElMessage.success('应用添加成功');
                    this.addDialogVisible = false;
                    await this.fetchAppsList();
                } else {
                    ElMessage.error(response.data.message || '添加失败');
                }
            } catch (error) {
                console.error('添加应用失败:', error);
                ElMessage.error('添加应用失败');
            }
        },

        async handleDelete(row) {
            try {
                await ElMessageBox.confirm(
                    `确定要删除应用 "${row.applicationId}" 吗？`,
                    '警告',
                    {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }
                );

                const token = localStorage.getItem('token');
                const response = await apiClient.post('/deleteUserGroupApplication', {
                    token,
                    groupId: this.groupId,
                    applicationId: row.applicationId
                });

                if (response.data.status === 'success') {
                    ElMessage.success('应用删除成功');
                    await this.fetchAppsList();
                } else {
                    ElMessage.error(response.data.message || '删除失败');
                }
            } catch (error) {
                if (error !== 'cancel') {
                    console.error('删除应用失败:', error);
                    ElMessage.error('删除应用失败');
                }
            }
        }
    }
};
</script>

<style scoped>
#GroupApps {
    background-color: white;
    padding: 3%;
}
.btn-table-add {
    margin-top: 40px;
    margin-bottom: 20px;
    width: 120px;
    height: 35px;
    background-color: rgb(14, 131, 234);
    transition: 1s;
    border-radius: 10px;
    display: flex;
    align-items: center;
    justify-content: center;
    color: white;
}
.btn-table-add:hover {
    cursor: pointer;
    background-color: #004dab;
}
.tblayout {
    border: 1px solid #ccc;
    border-radius: 10px;
    width: 100%;
    height: calc(100vh - 200px);
    padding: 20px;
    overflow: auto;
}

.el-table {
    height: 100%;
    overflow: hidden;
}

:deep(.el-table__inner-wrapper) {
    height: 100%;
}

:deep(.el-loading-mask) {
    background-color: rgba(255, 255, 255, 0.8);
}

.dialog-footer {
    display: flex;
    justify-content: flex-end;
    gap: 10px;
}
</style>