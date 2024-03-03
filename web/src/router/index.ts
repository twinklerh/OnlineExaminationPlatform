import { createRouter, createWebHistory } from 'vue-router';
import LoginView from '@/views/account/LoginView.vue';
import RegisterView from '@/views/account/RegisterView.vue';
import ProblemDetailView from '@/views/enterprise/problemView/ProblemDetailView.vue';
import { useUserStore } from '@/store/user';

const router = createRouter({
    history: createWebHistory(),
    routes: [
        {
            path: '/',
            name: 'root',
            redirect: '/login',
        },
        {
            path: '/login',
            name: 'login',
            component: LoginView,
            meta: {
                requestAuth: false,
            }
        },
        {
            path: '/register',
            name: 'register',
            component: RegisterView,
            meta: {
                requestAuth: false,
            }
        },
        {
            path: '/enterprise',
            name: 'enterprise',
            component: () => import('@/views/enterprise/HomeEnterpriseView.vue'),
            children: [
                {
                    path: 'allproblems',
                    name: 'allproblems',
                    component: () => import('@/views/enterprise/problemView/ProblemView.vue'),
                    meta:{ title: '试题库', requestAuth: true, holder: 'enterprise'}
                },
                {
                    path: 'addsubproblem',
                    name: 'addsubproblem',
                    component: () => import('@/views/enterprise/addProblemView/AddSubProblemView.vue'),
                    meta:{ title: '添加试题', requestAuth: true, holder: 'enterprise'}
                },
                {
                    path: 'addobjproblem',
                    name: 'addobjproblem',
                    component: () => import('@/views/enterprise/addProblemView/AddObjProblemView.vue'),
                    meta:{ title: '添加试题', requestAuth: true, holder: 'enterprise'}
                },
                {
                    path: 'maketestpaper',
                    name: 'maketestepaper',
                    component: () => import('@/views/enterprise/maketestpaperView/MakeTestPaperView.vue'),
                    meta:{ title: '组卷', requestAuth: true, holder: 'enterprise'}
                },
                {
                    path: 'release',
                    name: 'release',
                    component: () => import('@/views/enterprise/releaseExamView/ReleaseExamView.vue'),
                    meta:{ title: '发布考试', requestAuth: true, holder: 'enterprise'}
                },
                {
                    path: 'exam',
                    name: 'exam',
                    component: () => import('@/views/enterprise/releaseExamView/ExamView.vue'),
                    meta:{ title: '发布考试', requestAuth: true, holder: 'enterprise'}              
                },
                {
                    path: 'grade',
                    name: 'grade',
                    component: () => import('@/views/enterprise/getGradeView/GetGradeView.vue'),
                    meta:{ title: '成绩查询', requestAuth: true, holder: 'enterprise'}              
                }
            ]
        },
        {
            path: '/candidate',
            name: 'candidate',
            component: () => import('@/views/candidate/HomeCandidateView.vue'),
            meta: { requestAuth: true, holder: 'candidate'}
        },
        {
            path: '/problem/detail',
            name: 'problemdetail',
            component: ProblemDetailView,
            meta: { requestAuth: true, holder: 'enterprise'}
        }
    ]
});
router.beforeEach(async(to, from, next)=>{
    const userStore = useUserStore();
    userStore.token = localStorage.getItem("jwt_token") as string;
    if(to.meta.requestAuth) {
        await userStore.getUserInfo();
        if(to.meta.holder === userStore.status) next();
        else    router.go(-1);
    }
    else next();
})
export default router