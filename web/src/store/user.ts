import { defineStore } from "pinia"
import $ from 'jquery'

export const useUserStore = defineStore('user',{
    state(){
        return {
            username: '',
            token: '',
        }
    },
    actions:{

        getToken(){
            return this.token;
        },
        login(account_id:string, password:string){
            $.ajax({
                url: 'http://localhost:3000/account/token/',
                type: "post",
                data: {
                    account_id: account_id,
                    password: password,
                },// eslint-disable-next-line
                success(resp:any){
                    if(resp.error_message === 'success'){
                        localStorage.setItem('jwt_token', resp.token)
                        this.token=resp.token;
                        console.log("dsd",this.token)
                    }
                },
                error(){
                  console.log("")  
                }
            })
        }
    }
})