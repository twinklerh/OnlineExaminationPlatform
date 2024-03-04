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
        releaseExam(examId:number, callback : ()=>void){
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
                        callback();
                        ElMessage({type: "success", message: result.error_message});
                    }
                    else    ElMessage.error("发布失败");
                },
                error: ()=>{ ElMessage.error("失败"); }
            })
        }
    }
})