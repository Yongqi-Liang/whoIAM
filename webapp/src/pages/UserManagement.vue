<template>
    <div id="UserManagement" v-loading="loading" element-loading-text="加载中...">
        <h1>用户管理</h1>
        <!-- 修改新用户按钮，添加点击事件 -->
        <div class="btn-table-add" @click="handleAdd">
            <i class="bi bi-plus-lg"></i>
            <span>新用户</span>
        </div>

        <div class="tblayout">
            <el-table :data="tableData" style="width: 100%">
                <el-table-column prop="id" label="ID" min-width="50"></el-table-column>
                <el-table-column prop="username" label="用户名" min-width="150"></el-table-column>
                <el-table-column prop="usergroup" label="用户组" min-width="100"></el-table-column>
                <el-table-column prop="status" label="状态" min-width="100"></el-table-column>
                <el-table-column prop="email" label="电子邮箱" min-width="200"></el-table-column>
                <el-table-column prop="created_at" label="创建时间" min-width="180"></el-table-column>
                <el-table-column prop="updated_at" label="最近更新时间" min-width="180"></el-table-column>
                <el-table-column label="操作" min-width="150">
                    <template #default="scope">
                        <el-button-group>
                            <el-button @click="handleEdit(scope.row)" type="primary" size="small">
                                <i class="bi bi-pencil"></i>
                            </el-button>
                            <el-button @click="handleDelete(scope.row)" type="primary" size="small">
                                <i class="bi bi-power"></i>
                            </el-button>
                        </el-button-group>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        
        <!-- 添加编辑对话框 -->
        <el-dialog
            v-model="editDialogVisible"
            title="编辑用户信息"
            width="30%"
            :close-on-click-modal="false"
        >
            <el-form :model="editForm" label-width="100px">
                <el-form-item label="用户名">
                    <el-input v-model="editForm.username" placeholder="请输入新用户名"></el-input>
                </el-form-item>
                <el-form-item label="用户组">
                    <el-input v-model="editForm.groupid" placeholder="请输入新用户组"></el-input>
                </el-form-item>
                <el-form-item label="电子邮箱">
                    <el-input v-model="editForm.email" placeholder="请输入新邮箱"></el-input>
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="editDialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="submitEdit">确定</el-button>
                </span>
            </template>
        </el-dialog>

        <!-- 添加新用户对话框 -->
        <el-dialog
            v-model="addDialogVisible"
            title="添加新用户"
            width="30%"
            :close-on-click-modal="false"
        >
            <el-form :model="addForm" label-width="100px">
                <el-form-item label="用户ID">
                    <el-input v-model="addForm.id" placeholder="请输入用户ID"></el-input>
                </el-form-item>
                <el-form-item label="用户名">
                    <el-input v-model="addForm.username" placeholder="请输入用户名"></el-input>
                </el-form-item>
                <el-form-item label="用户组">
                    <el-input v-model="addForm.usergroup" placeholder="请输入用户组"></el-input>
                </el-form-item>
                <el-form-item label="电子邮箱">
                    <el-input v-model="addForm.email" placeholder="请输入邮箱"></el-input>
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
import { Edit, Share, Delete } from '@element-plus/icons-vue';
import axios from 'axios';

// 创建 axios 实例并配置
const apiClient = axios.create({
    baseURL: process.env.VUE_APP_API_BASE_URL,
    withCredentials: true,  // 允许跨域请求携带凭证
    headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json'
    }
});

export default {
    name: 'UserManagement',
    components: {
        ElTable,
        ElTableColumn,
        ElButton,
        ElButtonGroup,
        ElDialog,        // 添加对话框组件
        ElForm,          // 添加表单组件
        ElFormItem,      // 添加表单项组件
        ElInput,         // 添加输入框组件
        Edit,
        Share,
        Delete
    },
    data() {
        return {
            tableData: [],
            loading: true,  // 添加逗号
            editDialogVisible: false,
            editForm: {
                id: '',
                username: '',
                groupid: '',
                email: ''
            },
            // 添加新用户相关数据
            addDialogVisible: false,
            addForm: {
                id: '',
                username: '',
                usergroup: '',
                email: ''
            }
        };
    },
    async created() {  // 添加 async
        await this.fetchUserList();  // 等待数据加载完成
    },
    methods: {
        async fetchUserList() {
            if (!this.loading) {  // 如果不是首次加载，则设置 loading 状态
                this.loading = true;
            }
            try {
                const token = localStorage.getItem('token');
                // 添加延迟以确保加载动画显示（可选）
                await new Promise(resolve => setTimeout(resolve, 300));
                
                const response = await apiClient.get('/UserListApi', {
                    params: { token }
                });
                
                console.log('API Response:', response.data);

                if (response.data.code === 'success') {
                    const users = response.data.data;
                    this.tableData = Object.keys(users).map(id => ({
                        id: id,
                        username: users[id].username || '',
                        usergroup: users[id].ingroup || '',
                        status: users[id].status || '',
                        email: users[id].email || '',
                        created_at: users[id].create_time ? new Date(users[id].create_time).toLocaleString() : '',
                        updated_at: users[id].update_time ? new Date(users[id].update_time).toLocaleString() : ''
                    }));
                    console.log('Processed tableData:', this.tableData);
                } else {
                    console.error('获取用户列表失败:', response.data.message);
                    this.$router.push('/AppLogin/admin');
                }
            } catch (error) {
                console.error('获取用户列表失败:', error.response || error);
                ElMessage.error('获取用户列表失败');
                this.$router.push('/AppLogin/admin');
            } finally {
                this.loading = false;
            }
        },

        async handleDelete(row) {
            this.loading = true;  // 开始加载
            try {
                // 显示确认对话框
                await ElMessageBox.confirm(
                    `确定要禁用用户 "${row.username}" 吗？`,
                    '警告',
                    {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }
                );

                // 用户确认后，发送禁用请求
                const token = localStorage.getItem('token');
                const response = await apiClient.post('/disableUser', {
                    token,
                    id: row.id
                });

                if (response.data.code === 'success') {
                    ElMessage.success('用户禁用成功');
                    // 重新获取用户列表
                    await this.fetchUserList();
                } else {
                    ElMessage.error(response.data.message || '禁用失败');
                }
            } catch (error) {
                if (error !== 'cancel') {
                    console.error('禁用用户失败:', error);
                    ElMessage.error('禁用用户失败');
                }
            }
        },

        handleEdit(row) {
            this.editForm = {
                id: row.id,
                username: row.username,
                groupid: row.usergroup,
                email: row.email
            };
            this.editDialogVisible = true;
        },

        async submitEdit() {
            try {
                const token = localStorage.getItem('token');
                const updateData = { token, id: this.editForm.id };

                // 只发送已修改的字段
                if (this.editForm.username) updateData.username = this.editForm.username;
                if (this.editForm.groupid) updateData.groupid = this.editForm.groupid;
                if (this.editForm.email) updateData.email = this.editForm.email;

                const response = await apiClient.post('/ChangeUserInfo', updateData);

                if (response.data.code === 'success') {
                    ElMessage.success('用户信息修改成功');
                    this.editDialogVisible = false;
                    await this.fetchUserList(); // 刷新用户列表
                } else {
                    ElMessage.error(response.data.message || '修改失败');
                }
            } catch (error) {
                console.error('修改用户信息失败:', error);
                ElMessage.error('修改用户信息失败');
            }
        },

        // 添加新方法
        handleAdd() {
            this.addForm = {
                id: '',
                username: '',
                usergroup: '',
                email: ''
            };
            this.addDialogVisible = true;
        },

        async submitAdd() {
            try {
                const token = localStorage.getItem('token');
                const response = await apiClient.post('/AddUser', {
                    token,
                    id: this.addForm.id,
                    username: this.addForm.username,
                    email: this.addForm.email,
                    usergroup: this.addForm.usergroup
                });

                if (response.data.code === 'success') {
                    ElMessage.success('用户创建成功');
                    this.addDialogVisible = false;
                    await this.fetchUserList(); // 刷新用户列表
                } else {
                    ElMessage.error(response.data.message || '创建失败');
                }
            } catch (error) {
                console.error('创建用户失败:', error);
                ElMessage.error('创建用户失败');
            }
        }
    }
};
</script>

<style scoped>
#UserManagement {
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
    height: calc(100vh - 200px); /* Changed from 100vh to be more responsive */
    padding: 20px;
    overflow: auto; /* Add scroll when needed */
}

/* Add these new styles */
.el-table {
    height: 100%;
    overflow: hidden;
}

/* Ensure the table fits its container */
:deep(.el-table__inner-wrapper) {
    height: 100%;
}

/* 添加加载动画自定义样式 */
:deep(.el-loading-mask) {
    background-color: rgba(255, 255, 255, 0.8);
}

/* 添加对话框样式 */
.dialog-footer {
    display: flex;
    justify-content: flex-end;
    gap: 10px;
}
</style>