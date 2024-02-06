import { defineStore } from "pinia"

interface user {
    username: string,
    token: string,
}

export const useUserStore = defineStore('user',{
    state(){
        return {

        }
    }
})