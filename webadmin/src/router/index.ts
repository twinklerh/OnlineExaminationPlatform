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
      children: [
        {
          path: 'register',
          name: 'register',
          component: () => import('@/views/RegisterView.vue')
        },
        {
          path: 'feedback',
          name: 'feedback',
          component: ()=>import('@/views/FeedbackView.vue')
        }
      ]
    }
  ]
})

export default router
