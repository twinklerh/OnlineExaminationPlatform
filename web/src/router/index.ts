import { createRouter, createWebHistory } from 'vue-router';
import ProblemView from '@/views/enterprise/ProblemView.vue';

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
            component: ProblemView,
        },
    ]
})
export default router