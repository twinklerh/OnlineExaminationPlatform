import { defineStore } from "pinia";

export const usePageStore = defineStore('page', {
    state(){
        return {
            nowPage: 'problems'   //值为problems、addproblem、getgrade、boardtest
        }
    },
    actions:{
        setNowPage(nowpage:string){
            this.nowPage = nowpage;
        }
    }
})