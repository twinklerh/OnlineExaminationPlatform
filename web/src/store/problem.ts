import { defineStore } from "pinia";
import $ from 'jquery';
import { useUserStore } from "./user";
import { ElMessage } from "element-plus";

export interface ProblemInterface{
    id: number,
    title: string,
    description: string,
    difficulty: string,
    checkBy: string,
    type: string,
    accurateTimes: number,
    finishedTimes: number,
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
                    if(resp==="数据为空"){
                        this.problemList = [];
                        return;
                    }   
                    this.problemList = JSON.parse(resp)
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

                if (problem.finishedTimes > 0) {
                    problem.accuracy = problem.accurateTimes.toString() + '/' + problem.finishedTimes.toString();
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
                        this.getProblemList();
                        ElMessage({message: "成功删除一个试题", type: 'success',});
                    }
                },
                error: ()=>{
                    ElMessage.error("失败");
                }
            })
        },
        getProblemByTitle(problem_title:string, callbackfunction: (problems: ProblemInterface[] | null) => void) {
            $.ajax({
                url: 'http://localhost:3000/problem/getproblembytitle/',
                type: 'get',
                headers: {
                    Authorization: 'Bearer ' + useUserStore().token,
                },
                data: {
                    'problem_title': problem_title
                },
                success: (resp:string)=>{
                    //返回值通过调用 callback 函数传递给外部
                    callbackfunction(JSON.parse(resp)); //  上面声明了回调函数的参数是ProblemInterface[]或空类型，返回值类型是void
                    //回调函数是把函数当参数供函数体调用的函数
                },
                error: ()=>{
                    ElMessage.error("失败");
                }
            })
        }
    },

    getters:{
    }
})