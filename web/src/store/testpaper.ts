import { defineStore } from "pinia";
import $ from 'jquery';
import { useUserStore } from "./user";
import { ElMessage } from "element-plus";

export interface TestpaperInterface{
    title: string,
    memo: string
    problemCount: number,
}

export const useTestpaperStore = defineStore('testpaper', {
    state(){
        return{
            testpaperlist: [] as TestpaperInterface[],
            current_page: 1,
        }
    },
    actions: {
        submitTestPaper(testpaper:TestpaperInterface, problemString:string, isNeedAppendix:boolean, callback:(msg:string)=> void){
            $.ajax({
                url: 'http://localhost:3000/testpaper/addtestpaper/',
                type: 'post',
                dataType: 'text',
                contentType: 'application/x-www-form-urlencoded',   //   表示要发送的数据将被编码为 URL 查询字符串的形式
                headers: {
                    Accept: 'application/json',
                    Authorization: "Bearer " + useUserStore().token,
                },
                data: {
                    title: testpaper.title,
                    note: testpaper.memo,
                    problemCount: testpaper.problemCount,
                    problemString: problemString,
                    isNeedAppendix: isNeedAppendix
                },
                success: (result:string)=>{
                    const resp = JSON.parse(result)
                    callback(resp.error_message);
                },
                error:(resp:string)=>{
                    callback(resp);
                }
            });
        },
        getTestPaper(page:number, callback:(dataCount:number)=>void){
            $.ajax({
                url: 'http://127.0.0.1:3000/testpaper/gettestpaper/',
                type: 'get',
                headers:{
                    Authorization: "Bearer " + useUserStore().token,
                },
                data:{
                    page:page,
                },
                success: (resp:string)=>{
                    const result = JSON.parse(resp);
                    this.testpaperlist = result.testpaperList;
                    this.current_page = result.current_page;
                    this.fixTestPaperMsg();
                    callback(result.dataCount)
                },
                error: ()=>{
                    ElMessage.error("拉取试卷信息失败");
                }
            })
        },
        fixTestPaperMsg(){
            this.testpaperlist.forEach((item) => {
                const index = item.title.indexOf('}');
                if (index !== -1) {
                    item.title = item.title.slice(index + 1); // 保留'}'符号后的字符
                }
            });  
        }
    },
    getters: {
    }
}) 