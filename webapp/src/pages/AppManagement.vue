<template>
    <div id="AppManagement" v-loading="loading" element-loading-text="加载中...">
        <h1>应用管理</h1>
        <div class="btn-table-add" @click="handleAdd">
            <i class="bi bi-plus-lg"></i>
            <span>新应用</span>
        </div>

        <div class="tblayout">
            <el-table :data="tableData" style="width: 100%">
                <el-table-column prop="id" label="应用ID" min-width="120"></el-table-column>
                <el-table-column prop="name" label="应用名称" min-width="150"></el-table-column>
                <el-table-column prop="description" label="应用描述" min-width="200"></el-table-column>
                <el-table-column prop="uri" label="应用URI" min-width="200"></el-table-column>
                <el-table-column prop="created_at" label="创建时间" min-width="180"></el-table-column>
                <el-table-column prop="updated_at" label="最近更新时间" min-width="180"></el-table-column>
                <el-table-column label="操作" min-width="150">
                    <template #default="scope">
                        <el-button-group>
                            <el-button @click="handleEdit(scope.row)" type="primary" size="small">
                                <i class="bi bi-pencil"></i>
                            </el-button>
                            <el-button @click="handleDelete(scope.row)" type="danger" size="small">
                                <i class="bi bi-trash"></i>
                            </el-button>
                        </el-button-group>
                    </template>
                </el-table-column>
            </el-table>
        </div>

        <!-- 编辑应用对话框 -->
        <el-dialog
            v-model="editDialogVisible"
            title="编辑应用信息"
            width="40%"
            :close-on-click-modal="false"
        >
            <el-form :model="editForm" label-width="100px">
                <el-form-item label="应用名称">
                    <el-input v-model="editForm.name" placeholder="请输入应用名称"></el-input>
                </el-form-item>
                <el-form-item label="应用描述">
                    <el-input v-model="editForm.description" type="textarea" placeholder="请输入应用描述"></el-input>
                </el-form-item>
                <el-form-item label="应用URI">
                    <el-input v-model="editForm.uri" placeholder="请输入应用URI"></el-input>
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="editDialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="submitEdit">确定</el-button>
                </span>
            </template>
        </el-dialog>

        <!-- 添加应用对话框 -->
        <el-dialog
            v-model="addDialogVisible"
            title="添加新应用"
            width="40%"
            :close-on-click-modal="false"
        >
            <el-form :model="addForm" label-width="100px">
                <el-form-item label="应用ID">
                    <el-input v-model="addForm.id" placeholder="请输入应用ID"></el-input>
                </el-form-item>
                <el-form-item label="应用名称">
                    <el-input v-model="addForm.name" placeholder="请输入应用名称"></el-input>
                </el-form-item>
                <el-form-item label="应用描述">
                    <el-input v-model="addForm.description" type="textarea" placeholder="请输入应用描述"></el-input>
                </el-form-item>
                <el-form-item label="应用URI">
                    <el-input v-model="addForm.uri" placeholder="请输入应用URI"></el-input>
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
import { ElTable, ElTableColumn, ElButton, ElButtonGroup, ElMessageBox, ElMessage, ElDialog, ElForm, ElFormItem, ElInput } from 'element-plus';
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
    name: 'AppManagement',
    components: {
        ElTable,
        ElTableColumn,
        ElButton,
        ElButtonGroup,
        ElDialog,
        ElForm,
        ElFormItem,
        ElInput
    },
    data() {
        return {
            tableData: [],
            loading: true,
            editDialogVisible: false,
            editForm: {
                id: '',
                name: '',
                description: '',
                uri: ''
            },
            addDialogVisible: false,
            addForm: {
                id: '',
                name: '',
                description: '',
                uri: ''
            }
        };
    },
    async created() {
        await this.fetchAppList();
    },
    methods: {
        async fetchAppList() {
            if (!this.loading) {
                this.loading = true;
            }
            try {
                const token = localStorage.getItem('token');
                await new Promise(resolve => setTimeout(resolve, 300));
                
                const response = await apiClient.post('/appList', { token });
                
                if (response.data.code === 'success') {
                    const apps = response.data.data;
                    this.tableData = Object.entries(apps).map(([id, app]) => ({
                        id: id,
                        name: app.name || '',
                        description: app.description || '',
                        uri: app.uri || '',
                        created_at: app.create_time ? new Date(app.create_time).toLocaleString() : '',
                        updated_at: app.update_time ? new Date(app.update_time).toLocaleString() : ''
                    }));
                }
            } catch (error) {
                console.error('获取应用列表失败:', error);
                ElMessage.error('获取应用列表失败');
            } finally {
                this.loading = false;
            }
        },

        async handleDelete(row) {
            try {
                await ElMessageBox.confirm(
                    `确定要删除应用 "${row.name}" 吗？`,
                    '警告',
                    {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }
                );

                const token = localStorage.getItem('token');
                const response = await apiClient.post('/deleteApp', {
                    token,
                    id: row.id
                });

                if (response.data.status === 'success') {
                    ElMessage.success('应用删除成功');
                    await this.fetchAppList();
                } else {
                    ElMessage.error(response.data.message || '删除失败');
                }
            } catch (error) {
                if (error !== 'cancel') {
                    console.error('删除应用失败:', error);
                    ElMessage.error('删除应用失败');
                }
            }
        },

        handleEdit(row) {
            this.editForm = {
                id: row.id,
                name: row.name,
                description: row.description,
                uri: row.uri
            };
            this.editDialogVisible = true;
        },

        async submitEdit() {
            try {
                const token = localStorage.getItem('token');
                const response = await apiClient.post('/changeAppInfo', {
                    token,
                    appid: this.editForm.id,
                    name: this.editForm.name,
                    desc: this.editForm.description,
                    uri: this.editForm.uri
                });
                
                if (response.data.code === 'success') {
                    ElMessage.success('应用信息修改成功');
                    this.editDialogVisible = false;
                    await this.fetchAppList();
                } else {
                    ElMessage.error(response.data.message || '修改失败');
                }
            } catch (error) {
                console.error('修改应用信息失败:', error);
                ElMessage.error('修改应用信息失败');
            }
        },
        handleAdd() {
            this.addForm = {
                id: '',
                name: '',
                description: '',
                uri: ''
            };
            this.addDialogVisible = true;
        },

        async submitAdd() {
            try {
                const token = localStorage.getItem('token');
                const response = await apiClient.post('/createApp', {
                    token,
                    id: this.addForm.id,
                    name: this.addForm.name,
                    desc: this.addForm.description,
                    uri: this.addForm.uri
                });

                if (response.data.status === 'success') {
                    ElMessage.success('应用创建成功');
                    this.addDialogVisible = false;
                    await this.fetchAppList();
                } else {
                    ElMessage.error(response.data.message || '创建失败');
                }
            } catch (error) {
                console.error('创建应用失败:', error);
                ElMessage.error('创建应用失败');
            }
        }
    }
};
</script>

<style scoped>
#AppManagement {
    background-color: white;
    padding: 3%;
}
.btn-table-add {
    margin-top: 40px;
    margin-bottom: 20px;
    width: 100px;
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