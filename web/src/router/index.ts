import { createRouter, createWebHistory } from 'vue-router';
import LoginView from '@/views/account/LoginView.vue';
import RegisterView from '@/views/account/RegisterView.vue';
import HomeEnterpriseView from '@/views/enterprise/HomeEnterpriseView.vue';
import ProblemDetailView from '@/views/enterprise/ProblemDetailView.vue';

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
        {
            path: '/enterprise/',
            name: 'enterprise',
            component: HomeEnterpriseView,
        },
        {
            path: '/problem/detail/',
            name: 'problemdetail',
            component: ProblemDetailView,
        }
    ]
})
export default router