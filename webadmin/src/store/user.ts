import { defineStore } from "pinia";

export interface Feedback {
    feedbackId: number,
    content: string,
    isRead: boolean,
}

export const useUserStore = defineStore('user', {
    state() {
        return {
            token: ''
        }
    }
})
