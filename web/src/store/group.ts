import { defineStore } from "pinia"
import { useUserStore } from "./user";
import $ from 'jquery';
import { ElMessage } from "element-plus";

interface Group{
    group_name: string,
}

export const useGroupStore = defineStore('group', {
    state(){
        return {
            groupList: [] as Group[]
        }
    },
    actions: {
        getAllGroups(){
            $.ajax({
                url: 'http://localhost:3000/groups/getallgroups/',
                type: 'get',
                headers: {
                    Authorization: 'Bearer ' + useUserStore().token,
                },// eslint-disable-next-line
                success: (resp:string)=>{
                    this.groupList = JSON.parse(resp);
                },
                error: ()=>{
                    alert("failed! 没能拿到试题分组")
                }
            })
        },
        AddNewGroup(group_name:string){
            $.ajax({
                url: 'http://localhost:3000/groups/addnewgroup/',
                type: 'get',
                headers: {
                    Authorization: "Bearer " + useUserStore().token,
                },
                data: {
                    group_name: group_name,
                },
                success: (resp:string)=>{
                    this.getAllGroups();
                    ElMessage({
                        message: resp,
                        type: 'success',
                    })
                },
                error: (resp:string)=>{
                    ElMessage.error(resp)
                }
            })
        }
    }
})