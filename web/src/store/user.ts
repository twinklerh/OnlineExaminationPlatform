import { defineStore } from "pinia"
import $ from 'jquery'

export const useUserStore = defineStore('user',{
    state(){
        return {
            username: '',
            token: '',
            status: '',
        }
    },
    actions:{
        getToken(){
            return this.token;
        },
        async login(account_id:string, password:string):Promise<string>{
            let error_message = '';
            await $.ajax({
                url: 'http://localhost:3000/account/token/',
                type: "post",
                data: {
                    account_id: account_id,
                    password: password,
                },// eslint-disable-next-line
                success: (resp:any) => {
                    if(resp.error_message === 'success'){
                        localStorage.setItem('jwt_token', resp.token)
                        this.token = resp.token;
                        this.status = resp.status;
                        console.log("登陆成功", this.status, this.token);
                    }
                    error_message = resp.error_message;
                },// eslint-disable-next-line
                error: (resp:any) => {
                    console.log("登陆失败");
                    error_message = "failed";
                }
            })
            if(error_message==='success')   return error_message;
            return error_message;
        },
        async getUserInfo(){
            await $.ajax({
                url: 'http://localhost:3000/account/user/info/',
                type: 'post',
                headers: {
                    Authorization: "Bearer " + this.token,
                },
                // eslint-disable-next-line
                success: (resp:any)=>{
                    this.status = resp.status;
                    this.username = resp.name;
                }
            })
        }
    },
    getters: {
    }
})