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
        logout(){
            localStorage.setItem('jwt_token','');
            this.token = "", this.status = '', this.username = '';
            location.reload();
        }
    },
    getters: {
    }
})