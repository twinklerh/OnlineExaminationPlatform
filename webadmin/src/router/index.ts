import { useUserStore } from '@/store/user'
import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/:catchAll(.*)',
      redirect: '/notfound/',
    },
    {
      path: '/',
      redirect: '/home/register',
    },
    {
      path: '/home',
      name: 'home',
      component: () => import('@/views/HomeView.vue'),
      meta: { requestAuth: true },
      children: [
        {
          path: 'register',
          name: 'register',
          component: () => import('@/views/RegisterView.vue'),
          meta: { requestAuth: true },
        },
        {
          path: 'feedback',
          name: 'feedback',
          component: ()=>import('@/views/FeedbackView.vue'),
          meta: { requestAuth: true },
        },
        {
          path: 'notfound',
          name: 'notfound',
          component: ()=>import('@/views/NotFoundView.vue'),
          meta: { requestAuth: false },
        }
      ]
    },
    {
      path: '/admin/login',
      name: 'login',
      component: ()=>import("@/views/LoginView.vue"),
      meta: { requestAuth: false },
    }
  ]
})

router.beforeEach((to,from,next)=>{
  if(!to.meta.requestAuth) next();
  else {
    console.log("53", useUserStore().token)
    if(localStorage.getItem("jwt_token") && localStorage.getItem("jwt_token")!='')  next();
    else  next({name: 'login'})
  }
})

export default router
