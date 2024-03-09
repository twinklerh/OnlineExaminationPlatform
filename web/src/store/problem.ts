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
    score: number,
    rightAnswer:string
}

export const useProblemStore = defineStore('problem', {
    state(){
        return{
            problemList: [] as ProblemInterface[]
        }
    },
    actions: {
        getProblemList(callback ? : (msg?:string) => void){
            $.ajax({
                url: 'http://localhost:3000/problems/getallproblems/',
                headers: {
                    Authorization: 'Bearer ' + useUserStore().token,
                },
                type: 'get',
                success: (resp:string)=>{
                    if(resp==="数据为空"){
                        this.problemList = [];
                        if(typeof callback !== 'undefined') callback('数据为空');
                        return;
                    }   
                    this.problemList = JSON.parse(resp)
                    if(typeof callback !== 'undefined') callback("成功删除一个试题");
                },
                error: ()=>{
                    alert('error');
                }
            })
        },
        deleteProblem(problem_id:number, callback ?:()=> void ){
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
                    if (JSON.parse(resp).error_message !== 'success') return;
                    this.getProblemList((msg)=>{
                        if(msg==='数据为空') ElMessage({message: msg, type: 'error'});
                        else ElMessage({message: msg, type: 'success'});
                        if(typeof callback !== 'undefined') callback();
                    });
                },
                error: ()=>{
                    ElMessage.error("失败");
                }
            })
        },
        getProblemByTitle(problem_title:string, callbackfunction: (problems: ProblemInterface[]) => void) {
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
                    if(resp==="数据为空")   {
                        ElMessage.error("数据为空")
                        callbackfunction([]);
                        return;
                    }
                    callbackfunction(JSON.parse(resp));
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