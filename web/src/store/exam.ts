import { defineStore } from "pinia";
import $ from 'jquery'
import { useUserStore } from "./user";
import { ElMessage } from "element-plus";

export interface ExamInterface{
    examId: number,
    beginTime: Date,
    endTime: Date,
    note: string,
    testpaperTitle: string,
    state: string,
    announced: boolean
}


export const useExamStore = defineStore('exam',{
    state(){
        return {
            examList: [] as ExamInterface[]
        }
    },
    actions: {// eslint-disable-next-line
        getAllExam(current_page:number, callback : (dataCount:number)=>void) {
            $.ajax({
                url: 'http://127.0.0.1:3000/exam/getallexam/',
                type: 'post',
                headers: {
                    Authorization: 'Bearer ' + useUserStore().token,
                },
                data: {
                    current_page: current_page,
                },
                success: (resp:string)=>{
                    const result = JSON.parse(resp);
                    this.examList = JSON.parse(result.examList);
                    if(result.error_message != 'success')    {
                        ElMessage.error("获取数据失败")
                        return;
                    }
                    callback(parseInt(result.dataCount));
                },
                error: () => {
                    ElMessage.error("获取数据失败")
                }
            })
        },
        releaseExam(examId:number, callback : (inviteCode:string)=>void){
            $.ajax({
                url: 'http://127.0.0.1:3000/exam/release/',
                type: 'post',
                headers: {
                    Authorization: "Bearer " + useUserStore().token,
                },
                data: {
                    examId: examId,
                },
                success: (resp:string)=>{
                    const result = JSON.parse(resp);
                    if(result.error_message === "success"){
                        callback(result.inviteCode);
                    }
                    else    ElMessage.error("发布失败");
                },
                error: ()=>{ ElMessage.error("失败"); }
            })
        },
        joinExam(inviteCode:string, callback:()=>void){
            $.ajax({
                url: 'http://127.0.0.1:3000/candidate/join/exam/',
                type: 'post',
                headers: {
                    Authorization: 'Bearer ' + useUserStore().token,
                },
                data: {
                    inviteCode: inviteCode,
                },
                success: (result:string)=>{
                    const resp = JSON.parse(result);
                    if(resp.error_message === 'success')    callback();
                    else    ElMessage.error(resp.error_message);
                },
            })
        }
    }
})