import { createRouter, createWebHistory } from 'vue-router'
import LoginView from '../views/pin.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'login',
      component: LoginView
    },
    {
      path: '/homepage',
      name: 'homepage',
      component: () => import('../views/homepage.vue')
    },
    {
      path: '/homepage/send',
      name: 'send',
      component: () => import('../views/send.vue')
    }
  ]
})

export default router
