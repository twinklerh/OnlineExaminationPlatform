import { defineStore } from "pinia"
import $ from 'jquery'
import { ElMessage } from "element-plus"

export const useUserStore = defineStore('user',{
    state(){
        return {
            username: '',
            token: '',
            status: '',
        }
    },
    actions:{
        login(account_id:string, password:string, callback:(msg:string)=>void){
            $.ajax({
                url: 'http://localhost:3000/account/token/',
                type: "post",
                data: {
                    account_id: account_id,
                    password: password,
                },
                success: (r:string) => {
                    const resp = JSON.parse(r);
                    if(resp.error_message === 'success'){
                        localStorage.setItem('jwt_token', resp.token)
                        this.token = resp.token;
                        this.status = resp.status;
                        this.username = resp.username;
                    }
                    callback(resp.error_message)
                },
                error: () => {
                    ElMessage.error("登录失败")
                }
            })
        },
        async getUserInfo(callback ? : ()=>void){
            await $.ajax({
                url: 'http://localhost:3000/account/user/info/',
                type: 'post',
                headers: {
                    Authorization: "Bearer " + this.token,
                },
                success: (ru:string)=>{
                    const resp = JSON.parse(ru);
                    this.status = resp.status;
                    this.username = resp.username;
                    if(typeof callback !== 'undefined' && resp.error_message==='success') callback();
                }
            })
        },
        getHeadImg(callback:(imgString:string)=>void) {
            $.ajax({
                url: 'http://127.0.0.1:3000/candidate/get/headImg/',
                type: 'post',
                headers: {
                    Authorization: "Bearer " + useUserStore().token,
                },
                success: (result:string)=>{
                    const resp = JSON.parse(result);
                    if(resp.error_message === 'success')    callback(resp.base64String);
                    else    ElMessage.error("头像获取失败");
                },
                error: ()=>{
                    ElMessage.error("头像获取失败");
                }
            })
        },
        base64toFile(base64String: string, fileName: string): File {
            // 将 base64 字符串转换为 Uint8Array
            const binaryString = window.atob(base64String);
            const len = binaryString.length;
            const bytes = new Uint8Array(len);
            for (let i = 0; i < len; ++i) {
                bytes[i] = binaryString.charCodeAt(i);
            }
            // 创建 Blob 对象
            const blob = new Blob([bytes], { type: 'application/octet-stream' });
        
            // 创建 File 对象
            const file = new File([blob], fileName, { type: 'application/octet-stream' });
        
            return file;
        },
        logout(){
            localStorage.setItem('jwt_token','');
            this.token = "", this.status = '', this.username = '';
            location.reload();
        }
    },
    getters: {
    }
})