<template>
    <div id="UserGroupManagement" v-loading="loading" element-loading-text="加载中...">
        <h1>用户组管理</h1>
        <div class="btn-table-add" @click="handleAdd">
            <i class="bi bi-plus-lg"></i>
            <span>新用户组</span>
        </div>

        <div class="tblayout">
            <el-table :data="tableData" style="width: 100%">
                <el-table-column prop="gid" label="组ID" min-width="150"></el-table-column>
                <el-table-column label="组名称" min-width="150">
                    <template #default="scope">
                        <router-link :to="{ 
                            name: 'GroupApps', 
                            params: { 
                                groupId: scope.row.gid,
                                groupName: scope.row.groupname 
                            }
                        }">
                            {{ scope.row.groupname }}
                        </router-link>
                    </template>
                </el-table-column>
                <el-table-column prop="description" label="描述" min-width="200"></el-table-column>
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

        <!-- 编辑用户组对话框 -->
        <el-dialog
            v-model="editDialogVisible"
            title="编辑用户组信息"
            width="30%"
            :close-on-click-modal="false"
        >
            <el-form :model="editForm" label-width="100px">
                <el-form-item label="组名称">
                    <el-input v-model="editForm.groupname" disabled></el-input>
                </el-form-item>
                <el-form-item label="描述">
                    <el-input v-model="editForm.description" placeholder="请输入组描述"></el-input>
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="editDialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="submitEdit">确定</el-button>
                </span>
            </template>
        </el-dialog>

        <!-- 添加用户组对话框 -->
        <el-dialog
            v-model="addDialogVisible"
            title="添加用户组"
            width="30%"
            :close-on-click-modal="false"
        >
            <el-form :model="addForm" label-width="100px">
                <el-form-item label="组ID">
                    <el-input v-model="addForm.gid" placeholder="请输入组ID"></el-input>
                </el-form-item>
                <el-form-item label="组名称">
                    <el-input v-model="addForm.gname" placeholder="请输入组名称"></el-input>
                </el-form-item>
                <el-form-item label="描述">
                    <el-input v-model="addForm.gdesc" placeholder="请输入组描述"></el-input>
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
import 'element-plus/dist/index.css';
import { Edit, Delete } from '@element-plus/icons-vue';
import { useRouter } from 'vue-router';  // 添加这行
import axios from 'axios';

// 创建 axios 实例
const apiClient = axios.create({
    baseURL: process.env.VUE_APP_API_BASE_URL,
    withCredentials: true,
    headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json'
    }
});

export default {
    name: 'UserGroupManagement',
    components: {
        ElTable,
        ElTableColumn,
        ElButton,
        ElButtonGroup,
        ElDialog,
        ElForm,
        ElFormItem,
        ElInput,
        Edit,
        Delete
    },
    data() {
        return {
            tableData: [],
            loading: true,
            editDialogVisible: false,
            editForm: {
                gid: '',
                groupname: '',
                description: ''
            },
            addDialogVisible: false,
            addForm: {
                gid: '',
                gname: '',
                gdesc: ''
            }
        };
    },
    async created() {
        await this.fetchGroupList();
    },
    // 在 methods 中添加跳转方法
    methods: {
        async fetchGroupList() {
            if (!this.loading) {
                this.loading = true;
            }
            try {
                const token = localStorage.getItem('token');
                await new Promise(resolve => setTimeout(resolve, 300));
                
                const response = await apiClient.get('/UserGroupList', {
                    params: { token }
                });
                
                if (response.data.code === 'success') {
                    const groups = response.data.data;
                    this.tableData = Object.keys(groups).map(gid => ({
                        gid: gid,
                        groupname: groups[gid].groupname || '',
                        description: groups[gid].description || '',
                        created_at: groups[gid].create_time ? new Date(groups[gid].create_time).toLocaleString() : '',
                        updated_at: groups[gid].update_time ? new Date(groups[gid].update_time).toLocaleString() : ''
                    }));
                } else {
                    console.error('失败:', response.data.message);
                    this.$router.push('/AppLogin/admin');
                }
            } catch (error) {
                console.error('获取用户组列表失败:', error);
                ElMessage.error('获取用户组列表失败');
                this.$router.push('/AppLogin/admin');
            } finally {
                this.loading = false;
            }
        },

        async handleDelete(row) {
            try {
                await ElMessageBox.confirm(
                    `确定要删除用户组 "${row.groupname}" 吗？`,
                    '警告',
                    {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }
                );

                const token = localStorage.getItem('token');
                const response = await apiClient.post('/DeleteUserGroup', {
                    token,
                    gid: row.gid
                });

                if (response.data.code === 'success') {
                    ElMessage.success('用户组删除成功');
                    await this.fetchGroupList();
                } else {
                    ElMessage.error(response.data.message || '删除失败');
                }
            } catch (error) {
                if (error !== 'cancel') {
                    console.error('删除用户组失败:', error);
                    ElMessage.error('删除用户组失败');
                }
            }
        },

        handleEdit(row) {
            this.editForm = {
                gid: row.gid,
                groupname: row.groupname,
                description: row.description
            };
            this.editDialogVisible = true;
        },

        async submitEdit() {
            try {
                const token = localStorage.getItem('token');
                const response = await apiClient.post('/ChangeUserGroupInfo', {
                    token,
                    gid: this.editForm.gid,
                    gdesc: this.editForm.description
                });

                if (response.data.code === 'success') {
                    ElMessage.success('用户组信息修改成功');
                    this.editDialogVisible = false;
                    await this.fetchGroupList();
                } else {
                    ElMessage.error(response.data.message || '修改失败');
                }
            } catch (error) {
                console.error('修改用户组信息失败:', error);
                ElMessage.error('修改用户组信息失败');
            }
        },

        handleAdd() {
            this.addForm = {
                gid: '',
                gname: '',
                gdesc: ''
            };
            this.addDialogVisible = true;
        },

        async submitAdd() {
            try {
                const token = localStorage.getItem('token');
                const response = await apiClient.post('/CreateUserGroup', {
                    token,
                    gid: this.addForm.gid,
                    gname: this.addForm.gname,
                    gdesc: this.addForm.gdesc
                });

                if (response.data.code === 'success') {
                    ElMessage.success('用户组创建成功');
                    this.addDialogVisible = false;
                    await this.fetchGroupList();
                } else {
                    ElMessage.error(response.data.message || '创建失败');
                }
            } catch (error) {
                console.error('创建用户组失败:', error);
                ElMessage.error('创建用户组失败');
            }
        }
    }
};
</script>

<style scoped>
#UserGroupManagement {
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