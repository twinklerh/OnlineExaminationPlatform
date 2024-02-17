import { defineStore } from "pinia"
import { useUserStore } from "./user";
import $ from 'jquery';

export const useGroupStore = defineStore('group', {
    state(){
        return {
            groupList: ['']
        }
    },
    actions: {
        getAllGroups(){
            $.ajax({
                url: 'http://localhost:3000/problems/getallgroups/',
                type: 'get',
                headers: {
                    Authorization: 'Bearer ' + useUserStore().getToken(),
                },// eslint-disable-next-line
                success: (resp:any)=>{
                    this.groupList = resp.stringList;
                },
                error: ()=>{
                    alert("failed! 没能拿到试题分组")
                }
            })
        }
    }
})