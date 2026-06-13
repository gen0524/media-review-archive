import { createRouter, createWebHistory } from 'vue-router'

import HomePage from '../pages/HomePage.vue'
import ReviewDetailPage from '../pages/ReviewDetailPage.vue'
import WantToWatchPage from '../pages/WantToWatchPage.vue'
import CompletedReviewsPage from '../pages/CompletedReviewsPage.vue'

const routes = [
    {
        path: '/',
        component: HomePage
    },
    {
        path: '/reviews/:id',
        component: ReviewDetailPage
    },
    {
        path: '/want-to-watch',
        component: WantToWatchPage
    },
    {
        path: '/completed',
        component: CompletedReviewsPage
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router