import { defineStore } from 'pinia'

export const useUserStore = defineStore('user', {
    state: () => ({
        username: 'guest',
        token: null
    }),

    actions: {
        setUser(username) {
            this.username = username
        },

        setToken(token) {
            this.token = token
        },

        logout() {
            this.username = 'guest'
            this.token = null
        }
    }
})