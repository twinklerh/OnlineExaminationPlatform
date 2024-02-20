import { defineStore } from "pinia";

export const usePageStore = defineStore('page', {
    state(){
        return {
            nowPage: 'problems',   //值为problems、addproblem、getgrade、boardtest
            pageName: '试题库'
        }
    },
    actions:{
        setNowPage(page:string, name:string){
            this.nowPage = page;
            this.pageName = name;
        }
    }
})