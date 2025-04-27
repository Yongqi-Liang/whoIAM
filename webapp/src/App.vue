<template>
  <router-view></router-view>
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
  name: 'App',
  data() {
    return {
      token: null,
      userId: null,
      userName: null,
      isAuthenticated: false,
    }
  },
  created() {
    
  },
  methods: {
    logout() {
      // 注销时跳转到当前应用的登录页
      const currentAppId = this.$route.path.split('/AppLogin/')[1] || 'admin';
      localStorage.removeItem('token');
      this.$router.push(`/AppLogin/${currentAppId}`);
    },
    toggleMenu() {
      this.isMenuVisible = !this.isMenuVisible;
    }
  }
}
</script>

<style>
#app {
  margin: 0;
  padding: 0;
}
</style>
