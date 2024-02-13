import { createRouter, createWebHistory } from 'vue-router';
import LoginView from '@/views/account/LoginView.vue';
import RegisterView from '@/views/account/RegisterView.vue';
import HomeEnterpriseView from '@/views/enterprise/HomeEnterpriseView.vue';

const router = createRouter({
    history: createWebHistory(),
    routes: [
        {
            path: '/',
            redirect: '/enterprise/'
        },
        {
            path: '/enterprise/',
            name: 'enterprise',
            component: HomeEnterpriseView,
        },
        {
            path: '/login/',
            name: 'login',
            component: LoginView,
        },
        {
            path: '/register/',
            name: 'register',
            component: RegisterView,
        },
    ]
})
export default router