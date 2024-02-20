import { defineStore } from "pinia";
import $ from 'jquery';
import { useUserStore } from "./user";

export interface ProblemInterface{
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
            problemList: [] as ProblemInterface[]
        }
    },

    actions: {
        getProblemList(){
            $.ajax({
                url: 'http://localhost:3000/problems/getallproblems/',
                headers: {
                    Authorization: 'Bearer ' + useUserStore().token,
                },
                type: 'get',
                success: (resp:string)=>{
                    if(resp==="数据为空")   return;
                    this.problemList = JSON.parse(resp)
                    console.log(this.problemList);
                    this.calculateAccuracy();
                },
                error: ()=>{
                    alert('error');
                }
            })
        },
        calculateAccuracy() {
            let problem;
            for (let i = 0; i < this.problemList.length; i++) {
                problem = this.problemList[i];

                if(problem.difficulty==='noSet')             problem.difficulty = '未设置'
                else if(problem.difficulty==='easy')         problem.difficulty = '简单'
                else if(problem.difficulty==='average')      problem.difficulty = '一般'
                else if(problem.difficulty==='difficult')    problem.difficulty = '困难'

                if(problem.check_by==='mechine')    problem.check_by='自动批改'
                else    problem.check_by='人工批改'

                if (problem.finished_times > 0) {
                    problem.accuracy = problem.accurate_times.toString() + '/' + problem.finished_times.toString();
                } else {
                    problem.accuracy = "N/A";
                }
            }
        },
        deleteProblem(problem_id:number){
            $.ajax({
                url: 'http://localhost:3000/problems/deleteproblem/',
                type: 'post',
                headers: {
                    Authorization: "Bearer " + useUserStore().token,
                },
                data:{
                    "problem_id": problem_id,
                },
                success: (resp:string)=>{
                    if (JSON.parse(resp).error_message === 'success') {
                        alert("删除成功！");
                        this.problemList = this.problemList.filter((item:ProblemInterface)=>{ item.id !== problem_id })
                        console.log(this.problemList)
                    }
                },
                error: ()=>{
                    alert("删除失败！");
                }
            })
        }
    },

    getters:{
    }
})