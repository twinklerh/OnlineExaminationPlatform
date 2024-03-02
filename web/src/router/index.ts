import { createRouter, createWebHistory } from 'vue-router';
import LoginView from '@/views/account/LoginView.vue';
import RegisterView from '@/views/account/RegisterView.vue';
import HomeEnterpriseView from '@/views/enterprise/HomeEnterpriseView.vue';
import ProblemDetailView from '@/views/enterprise/problemView/ProblemDetailView.vue';
import ProblemView from '@/views/enterprise/problemView/ProblemView.vue';
import AddSubProblemView from '@/views/enterprise/addProblemView/AddSubProblemView.vue';
import GetGradeView from '@/views/enterprise/getGradeView/GetGradeView.vue';
import ReleaseExamView from '@/views/enterprise/releaseExamView/ReleaseExamView.vue';
import AddObjProblemView from '@/views/enterprise/addProblemView/AddObjProblemView.vue';
import MakeTestPaperView from '@/views/enterprise/maketestpaperView/MakeTestPaperView.vue';

const router = createRouter({
    history: createWebHistory(),
    routes: [
        {
            path: '/',
            redirect: '/enterprise/allproblems'
        },
        {
            path: '/login',
            name: 'login',
            component: LoginView,
        },
        {
            path: '/register',
            name: 'register',
            component: RegisterView,
        },
        {
            path: '/enterprise',
            name: 'enterprise',
            component: HomeEnterpriseView,
            children: [
                {
                    path: 'allproblems',
                    name: 'allproblems',
                    component: ProblemView,
                    meta:{ title: '试题库' }
                },
                {
                    path: 'addsubproblem',
                    name: 'addsubproblem',
                    component: AddSubProblemView,
                    meta:{ title: '添加试题' }
                },
                {
                    path: 'addobjproblem',
                    name: 'addobjproblem',
                    component: AddObjProblemView,
                    meta:{ title: '添加试题' }
                },
                {
                    path: 'maketestpaper',
                    name: 'maketestepaper',
                    component: MakeTestPaperView,
                    meta:{ title: '组卷' }
                },
                {
                    path: 'release',
                    name: 'release',
                    component: ReleaseExamView,
                    meta:{ title: '发布考试' }
                },
                {
                    path: 'grade',
                    name: 'grade',
                    component: GetGradeView,
                    meta:{ title: '成绩查询' }              
                }
            ]
        },
        {
            path: '/problem/detail',
            name: 'problemdetail',
            component: ProblemDetailView,
        }
    ]
})
export default router