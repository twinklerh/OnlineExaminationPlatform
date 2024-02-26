import { defineStore } from "pinia";
import $ from 'jquery';
import { useUserStore } from "./user";

export interface TestpaperInterface{
    title: string,
    memo: string
    problemCount: number,
}

export const useTestpaperStore = defineStore('testpaper', {
    state(){
        return{
            testpaperlist: [] as TestpaperInterface[],
        }
    },
    actions: {
        submitTestPaper(testpaper:TestpaperInterface, problemString:string, callback:(msg:string)=> void){
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
                    problemString: problemString
                },
                success: (resp:string)=>{
                    callback(resp);
                },
                error:(resp:string)=>{
                    callback(resp);
                }
            });
        }
    }
}) 