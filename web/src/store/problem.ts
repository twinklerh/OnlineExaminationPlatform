import { defineStore } from "pinia";
import $ from 'jquery';
import { useUserStore } from "./user";

interface Problem{
    id: number,
    title: string,
    description: string,
    difficulty: string,
    check_by: string,
    type: string,
    accurate_times: number,
    finished_times: number,
    accuracy: string
}

export const useProblemStore = defineStore('problem', {
    state(){
        return{
            problemList: [] as Problem[]
        }
    },

    actions: {
        getProblemList(){
            $.ajax({
                url: '',
                headers: {
                    Authorization: 'Bearer ' + useUserStore().token,
                },
                type: 'get',
                success: (resp:string)=>{
                    JSON.parse(resp)
                }
            })
        }
    },

    getters:{
        calculateAccuracy() {
            for (let i = 0; i < this.problemList.length; i++) {
                const problem = this.problemList[i];
                if (problem.finished_times > 0) {
                    problem.accuracy = problem.accurate_times.toString() + '/' + problem.finished_times.toString();
                } else {
                    problem.accuracy = "N/A";
                }
            }
        }
    }
})