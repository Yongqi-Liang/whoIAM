import { createRouter, createWebHistory } from 'vue-router';
import Home from '@/pages/Home.vue';
import AppLogin from '@/pages/AppLogin.vue';
import Dashboard from '@/components/Dashboard.vue';
import UserManagement from '@/pages/UserManagement.vue';
import UserGroupManagement from '@/pages/UserGroupManagement.vue';
import AppManagement from '@/pages/AppManagement.vue';
import AuthLog from '@/pages/AuthLog.vue';
import Setting from '@/pages/Setting.vue';
import ServiceConfig from '@/pages/ServiceConfig.vue';
import MailService from '@/pages/MailService.vue';
import SystemStatus from '@/pages/SystemStatus.vue';
import About from '@/pages/About.vue';
import Help from '@/pages/Help.vue';

const routes = [
    // 登录路由（顶层）
    {   path: '/AppLogin/:appId', 
        name: 'AppLogin', 
        component: () => import('@/pages/AppLogin.vue'),
        props: true 
    },
    
    // Home 路由及其子路由
    { 
        path: '/',
        component: Home,
        children: [
            { path: '', redirect: '/dashboard' },
            { path: 'dashboard', component: Dashboard },
            { path: 'user-management', component: UserManagement },
            { path: 'user-group-management', component: UserGroupManagement },
            { path: 'app-management', component: AppManagement },
            { path: 'auth-log', component: AuthLog },
            // Add GroupApps route here
            { 
                path: 'group-apps/:groupId',
                name: 'GroupApps',
                component: () => import('@/pages/GroupApps.vue'),
                props: true
            },
            { 
                path: 'setting',
                component: Setting,
                children: [
                    { path: 'service-config', component: ServiceConfig },
                    { path: 'mail-service', component: MailService },
                    { path: 'system-status', component: SystemStatus },
                    { path: 'about', component: About }
                ]
            },
            { path: 'help', component: Help }
        ]
    }
];


const router = createRouter({
    history: createWebHistory(),
    routes,
});

router.beforeEach(async (to, from, next) => {
    const token = localStorage.getItem('token');
    const isLoginPage = to.name === 'AppLogin';
  
    if (!token && !isLoginPage) {
      // 如果是访问登录页，保留原始 appId
      if (to.path.startsWith('/AppLogin/')) {
        next();
      } else {
        // 否则跳转到默认登录页（可改为动态获取目标 appId）
        next({ path: '/AppLogin/admin' });
      }
    } else if (token && isLoginPage) {
      next({ path: '/' }); // 已登录时跳转到首页
    } else {
      next(); // 其他情况正常放行
    }
  });


export default router;