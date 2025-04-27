import { createRouter, createWebHashHistory } from 'vue-router'
import GroupApps from '../pages/GroupApps.vue'

const routes = [
    // ... 其他路由配置 ...
    {
        path: '/group-apps/:groupId',
        name: 'group-apps',
        component: GroupApps
    }
]

const router = createRouter({
    history: createWebHashHistory(),
    routes
})

export default router