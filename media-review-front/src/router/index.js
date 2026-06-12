import { createRouter, createWebHistory } from 'vue-router'

import HomePage from '../pages/HomePage.vue'
import ReviewDetailPage from '../pages/ReviewDetailPage.vue'

const routes = [
    {
        path: '/',
        component: HomePage
    },
    {
        path: '/reviews/:id',
        component: ReviewDetailPage
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router