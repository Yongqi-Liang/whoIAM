<template>
<div id="Login">
    <div class="login-container">
        <h1>登录到 <span>{{ appName }}</span></h1>
        <form @submit.prevent="handleSubmit">
            <div class="form-group">
                <label for="userId">登录名</label>
                <input 
                    type="text" 
                    id="userId" 
                    v-model="userId" 
                    placeholder="登录名" 
                    required
                    :disabled="isLoading"
                >
            </div>

            <div class="form-group">
                <label for="otp">一次性密码(One-Time Password,OTP)</label>
                <input 
                    type="text" 
                    id="otp" 
                    v-model="otp" 
                    placeholder="一次性密码(One-Time Password,OTP)" 
                    required
                    :disabled="isLoading"
                >
            </div>

            <button type="submit" :disabled="isLoading">
                <span v-if="!isLoading">登录到 {{ appName }}</span>
                <span v-else>登录中...</span>
            </button>
        </form>
        <div class="error-message" v-if="errorMessage">{{ errorMessage }}</div>
        <div class="app-info">
            <p><strong>应用 ID:</strong> {{ appId }}</p>
            <p><strong>描述:</strong> {{ appDescription }}</p>
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
    name: 'AppLogin',
    props: ['appId'],
    data() {
        return {
            userId: '',
            otp: '',
            errorMessage: '',
            appId: this.$route.params.appId || '',
            appName: '未知APP',
            appDescription: '这是一个示例应用程序',
            appUri: '',
            isLoading: false
        };
    },
    methods: {
        async fetchAppInfo() {
            try {
                const response = await apiClient.post('/appList', {});
                
                if (response.data.code === 'success') {
                    const appInfo = response.data.data[this.appId];
                    if (appInfo) {
                        this.appName = appInfo.name;
                        this.appDescription = appInfo.description;
                        this.appUri = appInfo.uri;
                    } else {
                        this.errorMessage = '未找到该应用信息';
                    }
                }
            } catch (error) {
                console.error('获取应用信息失败:', error);
                this.errorMessage = '获取应用信息失败';
            }
        },

        async handleSubmit() {
            if (this.isLoading) return;
            
            this.isLoading = true;
            this.errorMessage = '';

            try {
                const response = await apiClient.post('/AppLogin', {
                    userId: this.userId,
                    applicationId: this.appId,
                    otp: this.otp
                });

                if (response.data.code === 'success') {
                    localStorage.setItem('token', response.data.token);
                    window.location.href = this.appUri || response.data.URI;
                } else {
                    this.errorMessage = '登录失败：' + (response.data.message || '未知错误');
                }
            } catch (error) {
                console.error('登录失败:', error);
                this.errorMessage = '登录失败，请检查您的登录信息';
            } finally {
                this.isLoading = false;
            }
        }
    },
    async created() {
        await this.fetchAppInfo();
    }
}
</script>

<style>
#Login {
    font-family: 'Arial', sans-serif;
    background-image: url(../assets/redb.svg);
    color: #333;
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
    margin: 0;
}

.login-container {
    background-color: white;
    padding: 40px;
    border-radius: 8px;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    width: 100%;
    max-width: 500px;
}

.login-container h1 {
    font-size: 32px;
    color: #000000;
    margin-bottom: 20px;
}

.login-container .form-group {
    margin-bottom: 20px;
}

.login-container label {
    display: block;
    font-size: 14px;
    color: #555;
    margin-bottom: 5px;
}

.login-container input,
.login-container button {
    width: 100%;
    box-sizing: border-box;
    padding: 10px;
    font-size: 16px;
    border-radius: 5px;
}

.login-container input {
    border: 1px solid #ccc;
    background-color: #f9f9f9;
}

.login-container input:disabled {
    background-color: #e9ecef;
    cursor: not-allowed;
}

.login-container button {
    color: white;
    background-color: #007BFF;
    border: none;
    cursor: pointer;
    padding: 12px;
    transition: background-color 0.3s;
}

.login-container button:hover:not(:disabled) {
    background-color: #0056b3;
}

.login-container button:disabled {
    background-color: #6c757d;
    cursor: not-allowed;
}

.error-message {
    color: #dc3545;
    font-size: 14px;
    margin-top: 20px;
    text-align: center;
    padding: 10px;
    background-color: #f8d7da;
    border-radius: 4px;
    border: 1px solid #f5c6cb;
}

.app-info {
    margin-top: 30px;
    font-size: 14px;
    color: #777;
    padding: 15px;
    background-color: #f8f9fa;
    border-radius: 4px;
}
</style>