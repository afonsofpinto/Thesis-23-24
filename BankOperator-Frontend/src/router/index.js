import { createRouter, createWebHistory } from 'vue-router'
import Homepage from '../views/homepage.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'login',
      component: Homepage
    },
  ]
})

export default router
