import { createRouter, createWebHistory } from 'vue-router';
import LoginView from '@/views/account/LoginView.vue';
import RegisterView from '@/views/account/RegisterView.vue';
import ProblemDetailView from '@/views/enterprise/problemView/ProblemDetailView.vue';
import { useUserStore } from '@/store/user';

const router = createRouter({
    history: createWebHistory(),
    routes: [
        {
            path: '/:catchAll(.*)',
            redirect: '/notfound/',
        },
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
            meta:{ requestAuth: true, holder: 'enterprise'},
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
                    name: 'maketestpaper',
                    component: () => import('@/views/enterprise/maketestpaperView/MakeTestPaperView.vue'),
                    meta:{ title: '组卷', requestAuth: true, holder: 'enterprise'}
                },
                {
                    path: 'addexam',
                    name: 'addexam',
                    component: () => import('@/views/enterprise/releaseExamView/AddExamView.vue'),
                    meta:{ title: '发布考试', requestAuth: true, holder: 'enterprise'}              
                },
                {
                    path: 'release',
                    name: 'release',
                    component: () => import('@/views/enterprise/releaseExamView/ReleaseExamView.vue'),
                    meta:{ title: '发布考试', requestAuth: true, holder: 'enterprise'}
                },
                {
                    path: 'check',
                    name: 'check',
                    component: () => import('@/views/enterprise/checkExamView/CheckExamView.vue'),
                    meta: { title: '阅卷', requestAuth: true, holder: 'enterprise'},
                },
                {
                    path: 'grade',
                    name: 'grade',
                    component: () => import('@/views/enterprise/getGradeView/GetGradeView.vue'),
                    meta:{ title: '成绩查询', requestAuth: true, holder: 'enterprise'}              
                },
            ]
        },
        {
            path: '/candidate',
            name: 'candidate',
            component: () => import('@/views/candidate/HomeCandidateView.vue'),
            meta: { requestAuth: true, holder: 'candidate'},
            children: [
                {
                    path: 'exams',
                    name: 'exams',
                    component: () => import('@/views/candidate/ExamView.vue'),
                    meta: { requestAuth: true, holder: 'candidate'},
                },
                {
                    path: 'score',
                    name: 'score',
                    component: () => import('@/views/candidate/ScoreView.vue'),
                    meta: { requestAuth: true, holder: 'candidate'},
                },
            ]
        },
        {
            path: '/problem/detail/:problem_id',
            name: 'problemdetail',
            component: ProblemDetailView,
            meta: { requestAuth: true, holder: 'enterprise'}
        },
        {
            path: '/examination',
            name: 'examination',
            component: () => import('@/views/candidate/ExaminationPageView.vue'),
            meta: { requestAuth: true, holder: 'candidate'},
        },
        {
            path: '/checkdetail',
            name: 'checkdetail',
            component: () => import('@/views/enterprise/checkExamView/CheckDetailView.vue'),
            meta: { requestAuth: true, holder: 'enterprise' }
        },
        {
            path: '/notfound/',
            name: 'notfound',
            component: () => import('@/views/account/NotFoundView.vue'),
            meta: { requestAuth: false }
        }
    ]
});
router.beforeEach(async(to, from, next)=>{
    const userStore = useUserStore();
    userStore.token = localStorage.getItem("jwt_token") as string;

    if(to.name === 'login') {
        if(userStore.token){
            await userStore.getUserInfo();
            if(userStore.status === 'enterprise')   next({name: 'allproblems'});
            else if(userStore.status === 'candidate')   next({name: 'exams'});
            else    next({name: 'notfound'});
        }
        else    next();
    }
    else {
        if(to.meta.requestAuth) {
            if(userStore.token){
                await userStore.getUserInfo();
                if(userStore.status === to.meta.holder)   next();
                else    next({name: 'notfound'});
            }
            else next({name: 'login'});
        }
        else {
            next();
        }        
    }

})
export default router